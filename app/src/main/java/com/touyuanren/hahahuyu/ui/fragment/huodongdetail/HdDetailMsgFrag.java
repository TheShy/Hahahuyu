package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HDDetailInfoBean;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.FriendActivity;
import com.touyuanren.hahahuyu.ui.activity.LargeImageActivity;
import com.touyuanren.hahahuyu.ui.activity.MoreMsgAct;
import com.touyuanren.hahahuyu.ui.activity.shouye.OrderConfirmAct;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/6/24.
 * 活动详情fragment
 */
public class HdDetailMsgFrag extends BaseFragment implements View.OnClickListener {
    //将活动对象传入
    private HuoDongInfo mHuoDongInfo;
    /**
     * 活动海报
     */
    private ImageView ima_haibao;
    /**
     * 活动标题
     */
    private TextView tv_title;
    /**
     * 活动时间
     */
    private TextView tv_time;
    /**
     * 报名截止时间
     */
    private TextView tv_baoming_endTime;
    /**
     * 活动地址
     */
    private TextView tv_address;
    /**
     * 主办方
     */
    private TextView tv_zhubanfang;
    /**
     * 收藏
     */
    private TextView tv_collect;
    /**
     * 浏览次数
     */
    private TextView tv_browse_num;
    /**
     * 更多详情
     */
    private TextView tv_moermag;
    /**
     * 记录是否收藏，如果收藏为1，不收藏为0；默认为0
     */
    private int isCollect = 0;
    /**
     * 收藏
     */
    private TextView collectText;
    /**
     * 点击购票
     */
    private TextView buyTicket;
    /**
     * 活动是否过期
     */
    private boolean isOverdue;
    /**
     * 活动详情
     */
    private HDDetailInfoBean hdInfo;
    //当前页面总布局
    private View mView;
    /**
     * 主办方头像
     */
    private ImageView ima_zhubanfang;
    /**
     * 主办方关注数
     */
    private TextView tv_watchNumber;
    /**
     * 发布数
     */
    private TextView tv_fabu_number;
    /**
     * 报名人数
     */
    private TextView  tv_number_baoming;

    /**
     * 发布者id
     */
    private String user_id;

    private static final String INDEX = "index";
    private static final String TOTAL = "total";
    private static final String IMGS = "imgs";
    final ArrayList<String> mPathList = new ArrayList<String>();

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_detailmsg_huodong, null);
        Bundle mBundle = getArguments();
        mHuoDongInfo = (HuoDongInfo) mBundle.getSerializable("huodongInfo");
        initView();
        initData();
        return mView;
    }

    private void initView() {
        ima_haibao = (ImageView) mView.findViewById(R.id.ima_haibao_detatil_frag);
        tv_title = (TextView) mView.findViewById(R.id.title_huodong_frag);
        tv_time = (TextView) mView.findViewById(R.id.tv_huodongtime_frag);
        tv_baoming_endTime = (TextView) mView.findViewById(R.id.tv_baoming_endtime_frag);
        tv_address = (TextView) mView.findViewById(R.id.tv_address_detail_frag);
        tv_zhubanfang = (TextView) mView.findViewById(R.id.zhubanfang_huodong_detail_frag);
        tv_collect = (TextView) mView.findViewById(R.id.collect_count_detail);
        tv_browse_num = (TextView) mView.findViewById(R.id.browse_activity_detail);
        tv_zhubanfang = (TextView) mView.findViewById(R.id.zhubanfang_huodong_detail_frag);
        tv_moermag = (TextView) mView.findViewById(R.id.moredetail_msg_frag);
        collectText = (TextView) mView.findViewById(R.id.collect_detail_activity);
        buyTicket = (TextView) mView.findViewById(R.id.buy_ticket_detail);
        tv_number_baoming= (TextView) mView.findViewById(R.id.tv_number_people);
        //主办方
        ima_zhubanfang = (ImageView) mView.findViewById(R.id.ima_zhubanfang_detail);
        tv_watchNumber = (TextView) mView.findViewById(R.id.tv_watch_zhubanf_detail);
        tv_fabu_number = (TextView) mView.findViewById(R.id.tv_fabu_number);
        //绑定监听
        ima_haibao.setOnClickListener(this);
        ima_zhubanfang.setOnClickListener(this);
        collectText.setOnClickListener(this);
        buyTicket.setOnClickListener(this);
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        long time = date.getTime();
        //字符串转时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try {
            d = sdf.parse(mHuoDongInfo.getClose_time());
            long closrTime = d.getTime();
            Log.e("time", closrTime + "&" + time);
            if (closrTime > time) {
                isOverdue = false;
            } else {
                isOverdue = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (isOverdue) {
            buyTicket.setText(R.string.is_over);
            buyTicket.setBackgroundColor(Color.GRAY);
        }
        tv_moermag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), MoreMsgAct.class);
                mIntent.putExtra("moremsg", mHuoDongInfo.getContent());
                startActivity(mIntent);
            }
        });

    }

    private void initData() {
        getAboutMsg();
    }

    public static final HdDetailMsgFrag newInstance(HuoDongInfo mHuoDongInfo) {
        HdDetailMsgFrag f = new HdDetailMsgFrag();
        Bundle bdl = new Bundle();
        bdl.putSerializable("huodongInfo", mHuoDongInfo);
        f.setArguments(bdl);
        return f;
    }


    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            case R.id.collect_detail_activity:
                if (isCollect == 0) {
                    toCollcet("add");
                    collectText.setSelected(true);
                    isCollect = 1;
                } else {
                    collectText.setSelected(false);
                    toCollcet("del");
                    isCollect = 0;
                }
                break;
            case R.id.ima_haibao_detatil_frag:
                mIntent =new Intent(getActivity(), LargeImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TOTAL, 1);
                bundle.putInt(INDEX, 0);
                bundle.putStringArrayList(IMGS, mPathList);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
                break;
            case R.id.ima_zhubanfang_detail:
                mIntent = new Intent(getActivity(), FriendActivity.class);
                mIntent.putExtra("user_id", user_id);
                startActivity(mIntent);
                break;
            case R.id.buy_ticket_detail:
                if (isOverdue) {
                    //已过期
                } else {
                    mIntent = new Intent(getActivity(), OrderConfirmAct.class);
                    mIntent.putExtra("huodong_id", mHuoDongInfo.getId());
                    mIntent.putExtra("huodong_title", mHuoDongInfo.getTitle());

                    mIntent.putExtra("huodong_img", HttpApi.ROOT_PATH + hdInfo.getPoster());
                    mIntent.putExtra("huodong_count", hdInfo.getDescription());
                    startActivity(mIntent);
                }
                break;
        }
    }
    /**
     * 获取活动详情
     */
    public void getAboutMsg() {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("hd_id", mHuoDongInfo.getId());
        formMap.put("act", "info");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.DETAIL_HD, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("icon", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        String json = jsonObject.getJSONObject("data").getString("info");
                        hdInfo = new Gson().fromJson(json, HDDetailInfoBean.class);
                        isCollect = Integer.parseInt(hdInfo.getIs_collection());
                        Log.e("isCollect", "" + isCollect);
                        if (isCollect == 0) {
                            //未收藏
                        } else {
                            collectText.setSelected(true);
                        }
                        //进行赋值
                        user_id = hdInfo.getUser_id();

                        String imaPath = HttpApi.ROOT_PATH + hdInfo.getPoster();
                        Glide.with(MyApplication.getContext()).load(imaPath).error(R.drawable.a1).into(ima_haibao);

                        mPathList.add(imaPath);

                        tv_title.setText(hdInfo.getTitle());
                        tv_time.setText(hdInfo.getStarttime());
                        tv_baoming_endTime.setText(hdInfo.getEndtime());
                        tv_address.setText(hdInfo.getAddress());
                        tv_collect.setText("收藏" + hdInfo.getRes1() + "次");
                        tv_browse_num.setText("浏览" + hdInfo.getBrowse_num() + "次");
                        tv_zhubanfang.setText(hdInfo.getNick_name());
//                        ima_zhubanfang.setImageURI(Uri.parse(HttpApi.ROOT_PATH + hdInfo.getPhoto()));

                        Glide.with(getActivity()).load(HttpApi.ROOT_PATH + hdInfo.getPhoto())
                                .error(R.drawable.jiazaishibai).into(ima_zhubanfang);
                        Log.e("icon", HttpApi.ROOT_PATH + hdInfo.getPhoto());
                        tv_watchNumber.setText(hdInfo.getAt_count());
                        tv_fabu_number.setText(hdInfo.getFabu());
                        tv_number_baoming.setText("报名人数：" + hdInfo.getSign_counts() + "/" + hdInfo.getLimit_num());

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //收藏或者取消收藏
    public void toCollcet(String type) {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("hd_id", mHuoDongInfo.getId());
        formMap.put("act", type);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_COLLECT, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
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
