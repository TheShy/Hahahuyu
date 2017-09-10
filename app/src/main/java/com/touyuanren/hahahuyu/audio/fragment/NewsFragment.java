package com.touyuanren.hahahuyu.audio.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.activity.NewsListDetali;
import com.touyuanren.hahahuyu.audio.bean.NewsInfo;
import com.touyuanren.hahahuyu.audio.adapter.NewsListAdapter;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewsFragment extends BaseFragment implements LoadListView.ILoadListener {
    private static final String TAG = "NewsFragment";
    private View view;
    private LoadListView lv_log;
    private ArrayList<NewsInfo.DataBean.InfoBean> mList;
    private NewsListAdapter mAdapter;
    /**
     * 控制页数
     */
    private int page = 1;
    private ArrayList<NewsInfo.DataBean.InfoBean> totalList;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View getShowView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.txt_fragment, null);
        lv_log = (LoadListView) view.findViewById(R.id.txt_fragment_lv);
        lv_log.setInterface(this);
        initSwipeLayout();
        initData();
        return view;
    }

    private void initData() {
        mList = new ArrayList<>();
        mAdapter = new NewsListAdapter(mList, getActivity());
        lv_log.setAdapter(mAdapter);
        totalList = new ArrayList<>();
        getLogList(page);
        lv_log.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                //进入详情页
                //进入详情页
                NewsInfo.DataBean.InfoBean mInfoBean = (NewsInfo.DataBean.InfoBean) adapter.getItemAtPosition(position);
                Intent mIntent = new Intent(getActivity(), NewsListDetali.class);
//                mIntent.putExtra("id", mList.get(position).getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("info", (Serializable) mInfoBean);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.txt_fragment_swipe);
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

    public void getLogList(final int page) {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("page", page + "");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.NEWS_LIST, formMap, new StringCallback() {
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
                            mList.clear();
                            NewsInfo mNewsInfo = new Gson().fromJson(response, NewsInfo.class);
                            mList = (ArrayList<NewsInfo.DataBean.InfoBean>) mNewsInfo.getData().getInfo();
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(mList);
                                mAdapter.setList(mList);
                            } else {
                                totalList.addAll(mList);
                                mAdapter.setList(totalList);
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
