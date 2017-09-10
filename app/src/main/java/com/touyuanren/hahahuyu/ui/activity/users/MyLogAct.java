package com.touyuanren.hahahuyu.ui.activity.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.LogInfo;
import com.touyuanren.hahahuyu.bean.LogInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.activity.account.PublishMyLogAct;
import com.touyuanren.hahahuyu.ui.adapter.LogListAdapter;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/7/28.
 * 展示我的日志列表
 */
public class MyLogAct extends BaseActivity implements LoadListView.ILoadListener {
    private LoadListView lv_log;
    private ArrayList<LogInfo> logList;
    private LogListAdapter logListAdapter;
    /**
     * 控制页数
     */
    private int page = 1;
    private ArrayList<LogInfo> totalList;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mylog);
        setTitleName(R.string.mylog);
        setTitleLeftBtn();
        setTitleRightBtn(R.string.fabu, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进入发布状态界面
                Intent mIntent = new Intent(MyLogAct.this, PublishMyLogAct.class);
                startActivity(mIntent);
            }
        });
        lv_log = (LoadListView)findViewById(R.id.lv_mylog_act);
        lv_log.setInterface(this);
        initSwipeLayout();
        initData();
    }
    private void initData() {
        logList = new ArrayList<>();
        logListAdapter = new LogListAdapter(logList, this);
        lv_log.setAdapter(logListAdapter);
        totalList=new ArrayList<>();
        getLogList(page);
    }
    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srl_list_log_act);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(this, 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getLogList(page);
            }
        });
    }
    //获取我的日志列表
    public void getLogList(final int page) {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list");
        formMap.put("page", page + "");
        formMap.put("uid", "-1");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.PUBLISH_LOG, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("tag",response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            logList.clear();
                            LogInfoBean mLoginfoBean = new Gson().fromJson(response, LogInfoBean.class);
                            logList = (ArrayList<LogInfo>) mLoginfoBean.getData().getList();
                            totalList.addAll(logList);
                            Log.e("size", "" + totalList.size());
                            if (logList.size() < 1) {
                                hideLoading();
                                FoToast.toast(MyApplication.getContext(), "没用更多了");
                                lv_log.loadComplete();
                                return;
                            }
                            if (page == 1) {
                                logListAdapter.setList(logList);
                            } else {
                                logListAdapter.setList(totalList);
                                lv_log.loadComplete();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideLoading();
            }
        });
    }
    @Override
    public void onLoad() {
        page++;
        getLogList(page);
    }
}
