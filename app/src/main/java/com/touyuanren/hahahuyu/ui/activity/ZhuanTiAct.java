package com.touyuanren.hahahuyu.ui.activity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.SwipeLvAdapter;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.touyuanren.hahahuyu.utils.OkHttpClientManager;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/8/15.
 * 专题页面；暂时设为敬请期待;暂时隐藏掉左滑删除
 */
public class ZhuanTiAct extends BaseActivity {
    /**
     * 保存所有的list
     */
    private ArrayList<HuoDongInfo> totalList = new ArrayList<HuoDongInfo>();
    /**
     * 活动列表，有接口时从网络获取数据
     */
    private ArrayList<HuoDongInfo> list = new ArrayList<>();
    /**
     * 请求的页数
     */
    private int page = 1;
    /**
     * 请求列表的请求地址
     */
    private String listUrl = HttpApi.ROOT_PATH + HttpApi.SEARCH_PATH + "?" + "cat_id=2&mark=0" + "&page=" + page;
    private SwipeLvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_zhuanti);
        setTitleLeftBtn();
        setTitleName(R.string.zhuanti);
        initData();
    }

    private void initData() {
        getData(listUrl);
    }

    //初始化数据,获取数据并解析
    public void getData(String address) {
        final Gson gson = new Gson();
        OkHttpClientManager.getAsyn(address, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                FoToast.toast(MyApplication.getContext(), "请求失败");

            }

            @Override
            public void onResponse(String response) {
                Log.e("a", response);
                if (response != null) {
                    list.clear();
                    HuoDongInfoBean mHuoDongInfoBean = gson.fromJson(response, HuoDongInfoBean.class);
                    list = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                    showListView(list);
                } else {
                    FoToast.toast(MyApplication.getContext(), "数据加载完毕");
                }
            }
        });
    }

    //listview 绑定适配器
    private void showListView(ArrayList<HuoDongInfo> mList) {
        //通过page来判断是否初始化了适配器
        if (page == 1) {
            totalList.clear();
            totalList.addAll(mList);
            Log.e("totalList", "" + totalList.size());
            adapter = new SwipeLvAdapter(MyApplication.getContext(), totalList);
        } else {
            totalList.addAll(mList);
            adapter.onDateChange(totalList);
        }
    }
}
