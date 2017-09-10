package com.touyuanren.hahahuyu.ui.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.NewsItemBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.NewsDetailAct;
import com.touyuanren.hahahuyu.ui.adapter.NewsAdapter;
import com.touyuanren.hahahuyu.ui.fragment.LazyFragment;
import com.touyuanren.hahahuyu.utils.DensityUtil;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/9/19.
 * 资讯的唯一一个frag
 */
public class NewsMediaFrag extends LazyFragment implements LoadListView.ILoadListener {
    /**
     * 当前的总布局
     */
    private View view;
    private LoadListView lv_news;
    private ArrayList<NewsItemBean> newsList = new ArrayList<>();
    /**
     * 控制页数
     */
    private int page = 1;
    private ArrayList<NewsItemBean> totalList = new ArrayList<>();
    private String newsId;
    /**
     * 资讯的适配器
     */
    private NewsAdapter newsAdapter = new NewsAdapter(MyApplication.getContext());
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_newsmedia, null);
        isPrepared = true;
        lv_news = (LoadListView) view.findViewById(R.id.lv_news_frag);
        lv_news.setInterface(this);
        lv_news.setAdapter(newsAdapter);
        newsId = getArguments().getString("newsId");
        lazyLoad();
        initSwipeLayout();
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), NewsDetailAct.class);
                mIntent.putExtra("content_id", newsList.get(position).getContent_id());
                startActivity(mIntent);
            }
        });
        return view;
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_news);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(getActivity(), 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNewsInfo(page);
            }
        });
    }

    private void initData() {
        getNewsInfo(page);
    }

    public static final NewsMediaFrag newInstance(String newsId) {
        NewsMediaFrag f = new NewsMediaFrag();
        Bundle bdl = new Bundle();
        bdl.putString("newsId", newsId);
        f.setArguments(bdl);
        return f;
    }

    //获取赛事信息
    private void getNewsInfo(final int page) {
        OkHttpUtils.post().url(HttpApi.ROOTPATH_NEWS+"hd/Android/title").

                addParams("categoryId", newsId).
                addParams("siteId", "6").
                addParams("page", "" + page).
                addParams("pageSize","10").
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String response) {
                        try {
//                            Toast.makeText(getActivity(),HttpApi.ROOTPATH_NEWS+"hd/Android/title?"
//                                    +"categoryId="+newsId
//                                    +"&siteId=6"
//                                    +"&page="+page
//                                    +"&pageSize=10",Toast.LENGTH_LONG).show();
//                            Log.i("ToastToastToast",HttpApi.ROOTPATH_NEWS+"hd/Android/title?"
//                                    +"categoryId="+newsId
//                                    +"&siteId=6"
//                                    +"&page="+page
//                                    +"&pageSize=10");
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if ("1".equals(status)) {
                                newsList.clear();
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                JSONArray jsonArray = jsonObject1.getJSONArray("list");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONArray jsonArray1 = (JSONArray) jsonArray.get(i);
                                    String contentId = jsonArray1.getString(0);
                                    String title = jsonArray1.getString(1);
                                    String time = jsonArray1.getString(2);
                                    String source = jsonArray1.getString(3);
                                    String imapath = jsonArray1.getString(4);
                                    newsList.add(new NewsItemBean(contentId, title, time, source, imapath));
                                }
                                if (1 == page) {
                                    totalList.clear();
                                    totalList.addAll(newsList);
                                    newsAdapter.setList(totalList);
                                } else {
                                    totalList.addAll(newsList);
                                    newsAdapter.setList(totalList);
                                }
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        hideLoading();
                    }
                });
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initData();
    }

    @Override
    public void onLoad() {
        page++;
        getNewsInfo(page);
    }
}
