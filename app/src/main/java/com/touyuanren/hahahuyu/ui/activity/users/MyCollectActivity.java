package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.SearchActivity;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/2/25.
 * 展示收藏的活动
 */
public class MyCollectActivity extends BaseActivity {
    //将收藏的活动传到后台，并将收藏的通过sp保存到本地数据库；进入界面时先判断是否有收藏，有则显示，没有则 显示寻找活动
    /**
     * 收藏列表
     */
    private ListView lv_collcet;
    /**
     * 收藏数据集合
     */
    private ArrayList<HuoDongInfo> mList = new ArrayList<>();
    /**
     * 请求列表的请求地址
     */
    private String listUrl = HttpApi.ROOT_PATH + HttpApi.USER_COLLECT;
    /**
     * 没有收藏时，进行搜索
     */
    private FrameLayout mFrameLayout;
    /**
     * 寻找活动
     */
    private Button btn_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycollect_activity);
        setTitleLeftBtn();
        setTitleName(getString(R.string.mycollect));
        initView();
        getCollectInfo();
    }
    private void initView() {
        lv_collcet = (ListView) findViewById(R.id.lv_collect_act);
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_no_collect);
        btn_search = (Button) findViewById(R.id.btn_search_hd);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MyCollectActivity.this, SearchActivity.class);
                startActivity(mIntent);
                finish();
            }
        });
    }
    //获取赛事信息
    private void getCollectInfo() {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act","list");
        FoHttp.getMsg(listUrl, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
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
                            if (mList.size() < 1) {
                                mFrameLayout.setVisibility(View.VISIBLE);
                            } else {
                                lv_collcet.setAdapter(new HuoDongAdapter(MyApplication.getContext(), mList));
                                mFrameLayout.setVisibility(View.GONE);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }
}
