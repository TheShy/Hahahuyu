package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.widget.LoadListView;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/2/26.
 * 我参加的活动与赛事
 */
public class MyJoinActivity extends BaseActivity implements LoadListView.ILoadListener {
    private LoadListView joinList;
    /**
     * 每页的活动数据集合
     */
    ArrayList<HuoDongInfo> mList;
    /**
     * 活动列表总得数据集合
     */
    ArrayList<HuoDongInfo> mTotalList = new ArrayList<>();
    private HuoDongAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myjoin_activity);
        setTitleLeftBtn();
        setTitleName(getString(R.string.myjoin_activity));
        initView();
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        adapter = new HuoDongAdapter(MyApplication.getContext(), mList);
        joinList.setAdapter(adapter);
        getListInfo(page);
    }

    private void initView() {
        joinList = (LoadListView) findViewById(R.id.lv_myjoin_activity);
    }

    //获取赛事信息
    private void getListInfo(final int page) {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("mark", "0");
        formMap.put("act", "sign_list");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                mTotalList.addAll(mList);
                if (response != null) {
                    if (page == 1) {
                        adapter.onDateChange(mList);
                    } else {
                        adapter.onDateChange(mTotalList);
                        joinList.loadComplete();
                    }
                }
                hideLoading();
            }
        });
    }

    @Override
    public void onLoad() {
        page++;
        getListInfo(page);
    }
}
