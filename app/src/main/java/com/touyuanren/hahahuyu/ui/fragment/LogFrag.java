package com.touyuanren.hahahuyu.ui.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.LogInfo;
import com.touyuanren.hahahuyu.bean.LogInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.LogDetailAct;
import com.touyuanren.hahahuyu.ui.adapter.LogListAdapter;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/21.
 * 好友日志列表;
 */
public class LogFrag extends BaseFragment implements LoadListView.ILoadListener {
    private static final String TAG = "LogFrag";
    private View view;
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
    public View getShowView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.frag_log, null);
        lv_log = (LoadListView) view.findViewById(R.id.lv_loglist);
        lv_log.setInterface(this);
        initSwipeLayout();
        initData();
        return view;
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_log_frag);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(getActivity(), 25);
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

    private void initData() {
        logList = new ArrayList<>();
        logListAdapter = new LogListAdapter(logList, getActivity());
        lv_log.setAdapter(logListAdapter);
        totalList = new ArrayList<>();
        getLogList(page);
        lv_log.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //进入详情页
                Intent mIntent = new Intent(getActivity(), LogDetailAct.class);
                mIntent.putExtra("id", logList.get(i).getId() + "");
                startActivity(mIntent);
            }
        });
    }

    public void getLogList(final int page) {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list");
        formMap.put("page", page + "");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.PUBLISH_LOG, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            logList.clear();
                            LogInfoBean mLoginfoBean = new Gson().fromJson(response, LogInfoBean.class);
                            logList = (ArrayList<LogInfo>) mLoginfoBean.getData().getList();
//                            if(logList.size()<1){
//                                hideLoading();
//                                FoToast.toast(MyApplication.getContext(), "没用更多了");
//                                logListAdapter.setList(totalList);
//                                lv_log.loadComplete();
//                                return;
//                            }
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(logList);
                                if (totalList.size() < 1) {
                                    ((ViewStub) view.findViewById(R.id.vs_log)).inflate();
                                } else {
                                    logListAdapter.setList(logList);
                                }
                            } else {
                                totalList.addAll(logList);
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

    //加载更多
    @Override
    public void onLoad() {
        page++;
        getLogList(page);
    }

}
