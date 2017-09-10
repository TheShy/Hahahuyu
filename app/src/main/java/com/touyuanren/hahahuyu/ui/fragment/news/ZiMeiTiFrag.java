package com.touyuanren.hahahuyu.ui.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.ZiMeiTiInfo;
import com.touyuanren.hahahuyu.bean.ZiMeiTiInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.FriendActivity;
import com.touyuanren.hahahuyu.ui.activity.news.ZiMeiTiNewsAct;
import com.touyuanren.hahahuyu.ui.adapter.ZiMeiTiAdapter;
import com.touyuanren.hahahuyu.ui.fragment.LazyFragment;
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
 * Created by Jing on 2016/10/24.
 */
public class ZiMeiTiFrag extends LazyFragment implements LoadListView.ILoadListener {
    private static final String TAG = "ZiMeiTiFrag";
    /**
     * 当前的总布局
     */
    private View view;
    private LoadListView lv_news;
    private ArrayList<ZiMeiTiInfo> newsList = new ArrayList<>();
    /**
     * 控制页数
     */
    private int page = 1;
    private ArrayList<ZiMeiTiInfo> totalList = new ArrayList<>();
    /**
     * 资讯的适配器
     */
    private ZiMeiTiAdapter newsAdapter = new ZiMeiTiAdapter(totalList, MyApplication.getContext());
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_zimeiti, null);
        isPrepared = true;
        lv_news = (LoadListView) view.findViewById(R.id.llv_zimeiti_frag);
        lv_news.setAdapter(newsAdapter);
        lv_news.setInterface(this);
        initSwipeLayout();
        lazyLoad();
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos =  position;
                TextView textView = (TextView) view.findViewById(R.id.tv_source_zimeiti_item);
                ImageView imageView = (ImageView) view.findViewById(R.id.ima_zimeiti_item);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("xcvbn",pos+"       "+totalList.get(pos).getUser_id()+"      "+totalList.get(pos).getContent());

                        Intent mIntent = new Intent(getActivity(), FriendActivity.class);
                        mIntent.putExtra("user_id", totalList.get(pos).getUser_id());
                        startActivity(mIntent);
                    }
                });

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mIntent = new Intent(getActivity(), ZiMeiTiNewsAct.class);
                        mIntent.putExtra("content", totalList.get(pos).getContent());
                        mIntent.putExtra("user_id", totalList.get(pos).getUser_id());
                        mIntent.putExtra("content_id", totalList.get(pos).getId());
                        startActivity(mIntent);
                    }
                });
            }
        });

        return view;
    }

    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_list_zimeiti_frag);
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

    //获取赛事信息
    private void getNewsInfo(final int page) {

        swipeRefreshLayout.measure(0,0);
        swipeRefreshLayout.setRefreshing(true);

        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list");
        formMap.put("page", "" + page);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.ZIMEITI_NEWS, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getContext(), R.string.intent_error, Toast.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            newsList.clear();
                            ZiMeiTiInfoBean mZiMeiTiInfoBean = new Gson().fromJson(response, ZiMeiTiInfoBean.class);
                            newsList = (ArrayList<ZiMeiTiInfo>) mZiMeiTiInfoBean.getData().getList();
                            totalList.addAll(newsList);
                            if (newsList.size() < 1) {
                                FoToast.toast(MyApplication.getContext(), "没有更多了");
                                lv_news.loadComplete();
                            }
                            if (page == 1) {
                                totalList.clear();
                                totalList.addAll(newsList);
                                newsAdapter.setList(newsList);
                            } else {
                                totalList.addAll(newsList);
                                newsAdapter.setList(totalList);
                                lv_news.loadComplete();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
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
