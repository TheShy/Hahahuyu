package com.touyuanren.hahahuyu.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FDZimeitiBean;
import com.touyuanren.hahahuyu.bean.FriendBean;
import com.touyuanren.hahahuyu.bean.GiftBean;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.users.MyAlbumActivity;
import com.touyuanren.hahahuyu.ui.activity.users.MyLogAct;
import com.touyuanren.hahahuyu.ui.activity.users.UserDongTaiAct;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.ui.fragment.FriendItemFragment;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendActivity extends BaseActivity implements View.OnClickListener {
    private FloatingActionButton btn_fd_fab;
    private Dialog dialog;

    LinearLayout ll_fd_guanzhu, ll_fd_fensi, ll_fd_liwu, ll_fd_rizhi, ll_fd_photo, ll_fd_dongtai;
    TextView textView_fd_nianling, textView_fd_didian, textView_fd_zhiwu, textView_fd_daxue, textView_fd_rizhi, textView_fd_dongtai,
            textView_fd_name, textView_fd_gq, textView_fd_guanzhu, textView_fd_fensi, textView_fd_liwu, textView_fd_photo, textView_fd_huodongmore, textView_fd_zimeitimore;
    ImageView imageView_fd__headimg, iv_fd_xingbie, iv_fd_back, iv_fd_college;
    private ImageView simpleDraweeView;
    RelativeLayout rl_fd_zimeiti__null, rl_fd_zimeiti__sel;

    //用户id
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");

        init();
        initData();
        userimg_dig();
    }

    public void init() {
        vp_fd_zimeiti = (ViewPager) findViewById(R.id.vp_fd_zimeiti);

        btn_fd_fab = (FloatingActionButton) findViewById(R.id.btn_fd_fab);

        rl_fd_zimeiti__null = (RelativeLayout) findViewById(R.id.rl_fd_zimeiti__null);
        rl_fd_zimeiti__sel = (RelativeLayout) findViewById(R.id.rl_fd_zimeiti__sel);

        ll_fd_guanzhu = (LinearLayout) findViewById(R.id.ll_fd_guanzhu);
        ll_fd_fensi = (LinearLayout) findViewById(R.id.ll_fd_fensi);
        ll_fd_liwu = (LinearLayout) findViewById(R.id.ll_fd_liwu);
        ll_fd_rizhi = (LinearLayout) findViewById(R.id.ll_fd_rizhi);
        ll_fd_photo = (LinearLayout) findViewById(R.id.ll_fd_photo);
        ll_fd_dongtai = (LinearLayout) findViewById(R.id.ll_fd_dongtai);

        textView_fd_nianling = (TextView) findViewById(R.id.textView_fd_nianling);
        textView_fd_didian = (TextView) findViewById(R.id.textView_fd_didian);
        textView_fd_zhiwu = (TextView) findViewById(R.id.textView_fd_zhiwu);
        textView_fd_daxue = (TextView) findViewById(R.id.textView_fd_daxue);
        textView_fd_name = (TextView) findViewById(R.id.textView_fd_name);
        textView_fd_gq = (TextView) findViewById(R.id.textView_fd_gq);
        textView_fd_rizhi = (TextView) findViewById(R.id.textView_fd_rizhi);
        textView_fd_photo = (TextView) findViewById(R.id.textView_fd_photo);
        textView_fd_dongtai = (TextView) findViewById(R.id.textView_fd_dongtai);
        textView_fd_guanzhu = (TextView) findViewById(R.id.textView_fd_guanzhu);
        textView_fd_fensi = (TextView) findViewById(R.id.textView_fd_fensi);
        textView_fd_liwu = (TextView) findViewById(R.id.textView_fd_liwu);
        textView_fd_huodongmore = (TextView) findViewById(R.id.textView_fd_huodongmore);
        textView_fd_zimeitimore = (TextView) findViewById(R.id.textView_fd_zimeitimore);

//        imageView_fd__headimg = (ImageView) findViewById(R.id.imageView_fd__headimg);
        simpleDraweeView= (ImageView) findViewById(R.id.icon_img_friend);
        iv_fd_xingbie = (ImageView) findViewById(R.id.iv_fd_xingbie);
        iv_fd_back = (ImageView) findViewById(R.id.iv_fd_back);
        iv_fd_college = (ImageView) findViewById(R.id.iv_fd_college);

        btn_fd_fab.setOnClickListener(this);
        textView_fd_huodongmore.setOnClickListener(this);
        textView_fd_zimeitimore.setOnClickListener(this);
        iv_fd_back.setOnClickListener(this);
        iv_fd_college.setOnClickListener(this);
        ll_fd_rizhi.setOnClickListener(this);
        ll_fd_dongtai.setOnClickListener(this);
        ll_fd_photo.setOnClickListener(this);

        activityList = (ListView) findViewById(R.id.listView_fd_saishi);
    }

    /**
     * 初始化数据
     * http://www.hahahuyu.com/api/hd/men.php?user_id=533?act=ziliao
     */
    public void initData() {
        showLoading();
        String url = HttpApi.ROOT_PATH + HttpApi.FRIEND_MSG;
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "ziliao");
        formMap.put("user_id", user_id);
        FoHttp.getMsg(url, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(FriendActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("responseresponser", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FriendBean friendBean = new Gson().fromJson(response, FriendBean.class);

                            //获取礼物粉丝关注数量
                            initData_liwu();

                            //获取活动列表
//                            initData_huodong();
                            //获取比赛列表
//                            initData_saishi();

                            //自媒体列表
                            if (friendBean.getData().getZimeiti().equals("1")) {
                                rl_fd_zimeiti__null.setVisibility(View.GONE);
                                rl_fd_zimeiti__sel.setVisibility(View.VISIBLE);
                                vp_fd_zimeiti.setVisibility(View.VISIBLE);
                                initData_zimeiti();
                            } else {
                                rl_fd_zimeiti__null.setVisibility(View.VISIBLE);
                                rl_fd_zimeiti__sel.setVisibility(View.GONE);
                                vp_fd_zimeiti.setVisibility(View.GONE);
                            }
                            Glide.with(getBaseContext()).load(HttpApi.ROOT_PATH + friendBean.getData().getPhoto())
                                    .error(R.drawable.jiazaishibai).into(simpleDraweeView);
//                            simpleDraweeView.setImageURI(Uri.parse(HttpApi.ROOT_PATH + friendBean.getData().getPhoto()));
                            //赋值
//                            Glide.with(getBaseContext()).load(HttpApi.ROOT_PATH + friendBean.getData().getPhoto())
//                                    .error(R.drawable.jiazaishibai).into(imageView_fd__headimg);
                            Log.i("getPhoto", HttpApi.ROOT_PATH + friendBean.getData().getPhoto() + "");
                            if (!TextUtils.isEmpty(friendBean.getData().getPhoto())) {
                                imageView_fd__headimg.setImageURI(Uri.parse(HttpApi.ROOT_PATH + friendBean.getData().getPhoto()));
                            }


                            textView_fd_dongtai.setText(friendBean.getData().getDongtai_num());//动态
                            textView_fd_rizhi.setText(friendBean.getData().getRizhi_count());//日志
                            textView_fd_photo.setText(friendBean.getData().getPic_num());//照片
                            textView_fd_nianling.setText(friendBean.getData().getAge() == "0" ? friendBean.getData().getAge() : "未知");//年龄
                            textView_fd_didian.setText(friendBean.getData().getProvince1());//地址
                            textView_fd_zhiwu.setText(friendBean.getData().getZhiye());//职务
                            textView_fd_name.setText(friendBean.getData().getNick_name());//昵称
                            textView_fd_gq.setText(friendBean.getData().getIntro());//个性签名
                            textView_fd_daxue.setText(friendBean.getData().getSchool());//大学
                            iv_fd_xingbie.setImageResource(friendBean.getData().getSex() == "1" ? R.drawable.img_nan : R.drawable.img_nvxing);//性别
                        } else {
                            Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }

    //获取用户礼物，粉丝，关注
    public void initData_liwu() {
        String url = HttpApi.ROOT_PATH + HttpApi.FRIEND_MSG;
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "liwu");
        formMap.put("user_id", user_id);
        FoHttp.getMsg(url, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(FriendActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("responsennnnnnnnnnnnnn", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            GiftBean giftBean = new Gson().fromJson(response, GiftBean.class);

                            textView_fd_guanzhu.setText(giftBean.getData().getGuanzhu());
                            textView_fd_fensi.setText(giftBean.getData().getFensi());
                            textView_fd_liwu.setText(giftBean.getData().getLiwu());
                        } else {
                            Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //获取用户自媒体
    public void initData_zimeiti() {

        String url = HttpApi.ROOT_PATH + HttpApi.FRIEND_MSG;
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "zimeiti");
        formMap.put("user_id", user_id);
        FoHttp.getMsg(url, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(FriendActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response_zimeiti", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            FDZimeitiBean fdZimeitiBean = new Gson().fromJson(response, FDZimeitiBean.class);

                            for (int i = 0; i < fdZimeitiBean.getData().size(); i++) {
                                friendItemFragment = new FriendItemFragment().getInstance(
                                        fdZimeitiBean.getData().get(i).getId(),
                                        fdZimeitiBean.getData().get(i).getUser_id(),
                                        fdZimeitiBean.getData().get(i).getContent(),
                                        fdZimeitiBean.getData().get(i).getImg_path(),
                                        fdZimeitiBean.getData().get(i).getTitle(),
                                        fdZimeitiBean.getData().get(i).getAdd_time()
                                );
                                mFragmentList.add(friendItemFragment);
                            }
                            vp_init();
                        } else {
                            Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    /**
     * 活动列表
     */
    private ListView activityList;
    /**
     * 每页的活动数据集合
     */
    ArrayList<HuoDongInfo> mList;
    /**
     * 活动列表总得数据集合
     */
    ArrayList<HuoDongInfo> mTotalList = new ArrayList<>();
    private HuoDongAdapter adapter;


    //获取用户参与的活动
    public void initData_huodong() {
        String url = HttpApi.ROOT_PATH + HttpApi.FRIEND_MSG;
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "huodong");
        formMap.put("user_id", user_id);
        FoHttp.getMsg(url, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(FriendActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("ressssssssssssasponse", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                            mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                            adapter = new HuoDongAdapter(MyApplication.getContext(), mTotalList);
                            activityList.setAdapter(adapter);
                        } else {
                            Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    //获取用户参与的赛事
    public void initData_saishi() {
        String url = HttpApi.ROOT_PATH + HttpApi.FRIEND_MSG;
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "ziliao");
        formMap.put("user_id", user_id);
        FoHttp.getMsg(url, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(FriendActivity.this, R.string.intent_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                            mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                            adapter = new HuoDongAdapter(MyApplication.getContext(), mTotalList);
                            activityList.setAdapter(adapter);
                        } else {
                            Toast.makeText(FriendActivity.this, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    ImageView imageView_close;

    //底部弹框
    public void userimg_dig() {
        dialog = new Dialog(FriendActivity.this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(FriendActivity.this).inflate(R.layout.layout_friend_dialog, null);

        imageView_close = (ImageView) inflate.findViewById(R.id.imageView_close);

        imageView_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_fd_fab.show();
                dialog.hide();
            }
        });

        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        lp.width = (int) getResources().getDisplayMetrics().widthPixels - 30; // 宽度
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        Intent mIntent;
        switch (view.getId()) {
            case R.id.btn_fd_fab:
                btn_fd_fab.hide();
                dialog.show();
                break;
            case R.id.iv_fd_back:
                finish();
                break;
            case R.id.iv_fd_college:

                break;
            case R.id.textView_fd_huodongmore:
                Toast.makeText(FriendActivity.this, "活动列表页", Toast.LENGTH_LONG).show();
                break;
            case R.id.textView_fd_zimeitimore:
                Toast.makeText(FriendActivity.this, "自媒体列表页", Toast.LENGTH_LONG).show();
                break;
            case R.id.ll_fd_dongtai:
                mIntent = new Intent(FriendActivity.this, UserDongTaiAct.class);
                startActivity(mIntent);
                break;
            case R.id.ll_fd_rizhi:
                mIntent = new Intent(FriendActivity.this, MyLogAct.class);
                startActivity(mIntent);
                break;
            case R.id.ll_fd_photo:
                mIntent = new Intent(FriendActivity.this, MyAlbumActivity.class);
                startActivity(mIntent);
                break;
        }
    }


    private ViewPager vp_fd_zimeiti;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;

    private FriendItemFragment friendItemFragment;

    private void vp_init() {
        mFragmentAdapter = new FragmentAdapter(
                this.getSupportFragmentManager(), mFragmentList);
        vp_fd_zimeiti.setAdapter(mFragmentAdapter);
        vp_fd_zimeiti.setCurrentItem(0);

    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
