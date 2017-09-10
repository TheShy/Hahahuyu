package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.content.Intent;
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
import com.touyuanren.hahahuyu.ui.activity.GameBaomingAct;
import com.touyuanren.hahahuyu.ui.activity.PlayerActivity;
import com.touyuanren.hahahuyu.ui.activity.users.GameListAcitivity;
import com.touyuanren.hahahuyu.ui.activity.users.GamePersonAct;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/22.
 * 赛事详情
 */
public class GameDetailFrag extends BaseFragment implements View.OnClickListener {

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
     * 主办方名称
     */
    private TextView tv_zhubanfang;
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
     * 收藏次数
     */
    private TextView tv_collect_count;
    /**
     * 浏览次数
     */
    private TextView tv_browse_num;
    /**
     * 更多详情
     */
    private TextView tv_moremsg;
    /**
     * 记录是否收藏，如果收藏为1，不收藏为0；默认为0
     */
    private int isCollect = 0;
//    /**
//     * 比赛进程
//     */
//    private TextView gameProcess;
//    /**
//     * 赛事报道
//     */
//    private TextView gameReport;
    /**
     * 选手报名
     */
    private TextView baoming;
    /**
     * 围观报名
     */
    private TextView tv_audience_baoming;
    /**
     * 暂时使用一个头像，点击进入选手也
     */
    private TextView ima_icon,ima_gamer;
    /**
     * 传递过来的活动信息
     */
    private HuoDongInfo mHuoDongInfo;
    /**
     * 判断是否报名
     */
    private String isBaoMing;
    /**
     * 页面总布局
     */
    private View view;
    /**
     * 比赛详情
     */
    private HDDetailInfoBean hdInfo;
    /**
     * 点击收藏
     */
    private TextView tv_collect;

    @Override
    public View getShowView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_game_detail, null);
        Bundle mBundle = getArguments();
        mHuoDongInfo = (HuoDongInfo) mBundle.getSerializable("huodongInfo");
        initView();
        initData();
        return view;
    }

    private void initData() {
        getAboutMsg();
    }

    private void initView() {
        ima_haibao = (ImageView) view.findViewById(R.id.ima_haibao_game_frag);
        tv_title = (TextView) view.findViewById(R.id.title_game_detail_frag);
        tv_time = (TextView) view.findViewById(R.id.tv_gametime_frag);
        tv_baoming_endTime = (TextView) view.findViewById(R.id.tv_baoming_endtime_game);
        tv_address = (TextView) view.findViewById(R.id.tv_address_game_detail_frag);
        tv_zhubanfang = (TextView) view.findViewById(R.id.zhubanfang_game_detail_frag);
        ima_zhubanfang = (ImageView) view.findViewById(R.id.ima_zhubanfang_game_detail);
        tv_watchNumber = (TextView) view.findViewById(R.id.tv_watch_zhubanf_game_detail);
        tv_fabu_number = (TextView) view.findViewById(R.id.tv_fabu_game_number);
        tv_browse_num = (TextView) view.findViewById(R.id.tv_browse_game_detail);
        tv_collect_count = (TextView) view.findViewById(R.id.tv_collect_count_game_detail);
        tv_moremsg = (TextView) view.findViewById(R.id.tv_moredetail_game_frag);
        tv_collect = (TextView) view.findViewById(R.id.tv_collect_game_detail_act);
        //
        ima_icon = (TextView) view.findViewById(R.id.ima_icon_player_gamedetail);
        ima_gamer= (TextView) view.findViewById(R.id.ima_gamer_detail);
//        gameProcess = (TextView) view.findViewById(R.id.process_game_detail_act);
//        gameReport = (TextView) view.findViewById(R.id.report_game_detail_act);
        baoming = (TextView) view.findViewById(R.id.baoming_game_detail_act);
        tv_audience_baoming = (TextView) view.findViewById(R.id.baoming_audience_game_act);
        //绑定监听
        ima_zhubanfang.setOnClickListener(this);
        tv_moremsg.setOnClickListener(this);
//        gameProcess.setOnClickListener(this);
//        gameReport.setOnClickListener(this);
        baoming.setOnClickListener(this);
        ima_icon.setOnClickListener(this);
        ima_gamer.setOnClickListener(this);
        tv_audience_baoming.setOnClickListener(this);
    }

    public static final GameDetailFrag newInstance(HuoDongInfo mHuoDongInfo) {
        GameDetailFrag f = new GameDetailFrag();
        Bundle bdl = new Bundle();
        bdl.putSerializable("huodongInfo", mHuoDongInfo);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public void onClick(View v) {
        Intent mIntent;
        switch (v.getId()) {
            //报名
            case R.id.baoming_game_detail_act:
                //点击进行跳转到报名界面;点击后报名成功，然后跳转到提交作品界面
                mIntent = new Intent(getActivity(), GameBaomingAct.class);
                mIntent.putExtra("gameid", mHuoDongInfo.getId());
                mIntent.putExtra("title", mHuoDongInfo.getTitle());
                startActivity(mIntent);
                break;
            //围观报名
            case R.id.baoming_audience_game_act:
                mIntent = new Intent(getActivity(), GamePersonAct.class);
                mIntent.putExtra("gameid", mHuoDongInfo.getId());
                mIntent.putExtra("title", mHuoDongInfo.getTitle());
                startActivity(mIntent);
                break;
            //比赛进程
//            case R.id.process_game_detail_act:
//                mIntent = new Intent(getActivity(), GameProcessActivity.class);
//                startActivity(mIntent);
//                break;
//            //点击进入赛事报道
//            case R.id.report_game_detail_act:
//                mIntent = new Intent(getActivity(), GameReportActivity.class);
//                startActivity(mIntent);
//                break;
            //进入选手详情页
            case R.id.ima_icon_player_gamedetail:
                mIntent = new Intent(getActivity(), PlayerActivity.class);
                mIntent.putExtra("gameid", mHuoDongInfo.getId());
                startActivity(mIntent);
                break;
            case R.id.ima_gamer_detail:
                mIntent = new Intent(getActivity(), GameListAcitivity.class);
                mIntent.putExtra("gameid", mHuoDongInfo.getId());
                startActivity(mIntent);
                break;
            case R.id.ima_zhubanfang_game_detail:
                mIntent = new Intent(getActivity(), FriendActivity.class);
                mIntent.putExtra("user_id", mHuoDongInfo.getUser_id());
                startActivity(mIntent);
                break;
        }
    }

    /**
     * 获取详情
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
                Log.e("response", response);
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
                            tv_collect.setSelected(true);
                        }
                        //进行赋值
                        String imaPath = HttpApi.ROOT_PATH + hdInfo.getPoster();
                        Glide.with(MyApplication.getContext()).load(imaPath).error(R.drawable.a1).into(ima_haibao);
                        tv_title.setText(hdInfo.getTitle());
                        tv_time.setText(hdInfo.getStarttime());
                        tv_baoming_endTime.setText(hdInfo.getEndtime());
                        tv_address.setText(hdInfo.getAddress());
                        tv_collect_count.setText("收藏" + hdInfo.getRes1() + "次");
                        tv_browse_num.setText("浏览" + hdInfo.getBrowse_num() + "次");
                        tv_zhubanfang.setText(hdInfo.getNick_name());
//                        ima_zhubanfang.setImageURI(Uri.parse(HttpApi.ROOT_PATH + hdInfo.getPhoto()));
                        Glide.with(getContext()).load(HttpApi.ROOT_PATH + hdInfo.getPhoto())
                                .error(R.drawable.icon_user).into(ima_zhubanfang);
                        tv_watchNumber.setText(hdInfo.getAt_count());
                        tv_fabu_number.setText(hdInfo.getFabu());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
