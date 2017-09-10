package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserGroupBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.UserGroupAdapter;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.UrlHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserGroupActivity extends BaseActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    ImageView iv_ug_left;
    LinearLayout ll_ug_title, ll_ug_mine, ll_ug_yes;
//    private ViewPager viewPager_usergroup;
//    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
//
//    TextView textView_chengyuan,textView_guanzhu,textView_wenzhang,textView_xiangce;
    ListView listView_usergroup;

//    private UG_chengyuanF ug_chengyuanF;
//    private UG_guanzhuF ug_guanzhuF;
//    private UG_wenzhangF ug_wenzhangF;
//    private UG_xiangceF ug_xiangceF;

    UserGroupAdapter userGroupAdapter;
    ArrayList<UserGroupBean.DataBean.ListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usergroup);

        init();
        userGroup();
    }

    private void init() {
        iv_ug_left = (ImageView) findViewById(R.id.iv_ug_left);
        listView_usergroup = (ListView) findViewById(R.id.listView_usergroup);
        ll_ug_title = (LinearLayout) findViewById(R.id.ll_ug_title);
        ll_ug_mine = (LinearLayout) findViewById(R.id.ll_ug_mine);
        ll_ug_yes = (LinearLayout) findViewById(R.id.ll_ug_yes);

        iv_ug_left.setOnClickListener(this);
        ll_ug_mine.setOnClickListener(this);
        ll_ug_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ug_left:
                finish();
                break;
            case R.id.ll_ug_mine:
                ll_ug_title.setBackgroundResource(R.drawable.ug_mine);
                break;
            case R.id.ll_ug_yes:
                ll_ug_title.setBackgroundResource(R.drawable.ug_yes);
                break;
        }
    }

//    /***
//     * 加载侧边栏
//     */
//    public void sliding_init() {
//        View view_sliding = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.navigation_layout, null);
//
//        viewPager_usergroup = (ViewPager) view_sliding.findViewById(R.id.viewPager_usergroup);
//        textView_chengyuan = (TextView) view_sliding.findViewById(R.id.textView_chengyuan);
//        textView_guanzhu = (TextView) view_sliding.findViewById(R.id.textView_guanzhu);
//        textView_wenzhang = (TextView) view_sliding.findViewById(R.id.textView_wenzhang);
//        textView_xiangce = (TextView) view_sliding.findViewById(R.id.textView_xiangce);
//
//        ug_chengyuanF = new UG_chengyuanF();
//        ug_guanzhuF = new UG_guanzhuF();
//        ug_wenzhangF = new UG_wenzhangF();
//        ug_xiangceF = new UG_xiangceF();
//
//        mFragmentList.add(ug_chengyuanF);
//        mFragmentList.add(ug_guanzhuF);
//        mFragmentList.add(ug_wenzhangF);
//        mFragmentList.add(ug_xiangceF);
//
//        mFragmentAdapter = new FragmentAdapter(
//                this.getSupportFragmentManager(), mFragmentList);
//        viewPager_usergroup.setAdapter(mFragmentAdapter);
//        viewPager_usergroup.setCurrentItem(0);
//
//        viewPager_usergroup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            /**
//             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
//             */
//            @Override
//            public void onPageScrollStateChanged(int state) {}
//
//            /**
//             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
//             * offsetPixels:当前页面偏移的像素位置
//             */
//            @Override
//            public void onPageScrolled(int position, float offset,
//                                       int offsetPixels) {}
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position) {
//                    case 0:
//                        changeTextColor(0);
//                        break;
//                    case 1:
//                        changeTextColor(1);
//                        break;
//                    case 2:
//                        changeTextColor(2);
//                        break;
//                    case 3:
//                        changeTextColor(3);
//                        break;
//                }
//            }
//        });
//
//    }

//    public void changeTextColor(int num){
//        textView_chengyuan.setTextColor(this.getResources().getColor(R.color.color_app_text));
//        textView_guanzhu.setTextColor(this.getResources().getColor(R.color.color_app_text));
//        textView_wenzhang.setTextColor(this.getResources().getColor(R.color.color_app_text));
//        textView_xiangce.setTextColor(this.getResources().getColor(R.color.color_app_text));
//        switch (num){
//            case 0:
//                textView_chengyuan.setTextColor(getColor(R.color.zhusediao));
//                break;
//            case 1:
//                textView_guanzhu.setTextColor(getColor(R.color.zhusediao));
//                break;
//            case 2:
//                textView_wenzhang.setTextColor(getColor(R.color.zhusediao));
//                break;
//            case 3:
//                textView_xiangce.setTextColor(getColor(R.color.zhusediao));
//                break;
//        }
//    }

//    public class FragmentAdapter extends FragmentPagerAdapter {
//
//        List<Fragment> fragmentList = new ArrayList<Fragment>();
//
//        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
//            super(fm);
//            this.fragmentList = fragmentList;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//    }


    /**
     * 获取用户群组
     */
    public void userGroup() {
        String url = HttpApi.ROOT_PATH + HttpApi.USERG_ROUP;
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "u_group_list");
//        formMap.put("token", FoPreference.getString(FoContents.LOGIN_TOKEN));
        OkHttpUtils.post()
                .url(url)
                .params(UrlHelper.getInstance().getBaseParams(formMap))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        hideLoading();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.e("msg", response);
                        hideLoading();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if ("1".equals(status)) {

                                UserGroupBean bean = new Gson().fromJson(response,UserGroupBean.class);

                                mList = new ArrayList<UserGroupBean.DataBean.ListBean>();

                                for (int i = 0; i<bean.getData().getList().size(); i++){
                                    mList.add(bean.getData().getList().get(i));
                                }

                                userGroupAdapter = new UserGroupAdapter(mList,getBaseContext());

                                listView_usergroup.setAdapter(userGroupAdapter);

                            } else {
                                FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
