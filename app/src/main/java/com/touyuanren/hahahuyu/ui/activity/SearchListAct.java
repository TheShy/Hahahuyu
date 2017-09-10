package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.shouye.HuoDongDetailAct;
import com.touyuanren.hahahuyu.ui.adapter.HuoDongAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.touyuanren.hahahuyu.utils.FoToast;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/18.
 * 搜索列表;根据传过来的关键字，显示列表
 */
public class SearchListAct extends BaseActivity {
    /**
     * 搜索列表
     */
    private ListView lv_hd;
    /**
     * 搜索关键字
     */
    private String keyword;
    /**
     * 每页的活动数据集合
     */
    ArrayList<HuoDongInfo> mList;
    private HuoDongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_searchlist);
        setTitleLeftBtn();
        setTitleName(R.string.search);
        keyword = getIntent().getStringExtra("keyword");
        lv_hd = (ListView) findViewById(R.id.lv_search_act);
        startQuary(keyword);
        lv_hd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mark = mList.get(position - 1).getMark();
                if ("0".equals(mark)) {
                    Intent mIntent = new Intent(SearchListAct.this, HuoDongDetailAct.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("huodongInfo", mList.get(position));
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(SearchListAct.this, GameDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("gameinfo", mList.get(position));
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                }
            }
        });
    }

    public void startQuary(String keywords) {
        showLoading();
        Map<String, String> formMap = new HashMap<>();
        formMap.put("keyword", keywords);
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.SEARCH_PATH, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("SearchListAct", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if ("1".equals(status)) {
                        HuoDongInfoBean mHuoDongInfoBean = new Gson().fromJson(response, HuoDongInfoBean.class);
                        mList = (ArrayList<HuoDongInfo>) mHuoDongInfoBean.getData().getList();
                        adapter = new HuoDongAdapter(MyApplication.getContext(), mList);
                        lv_hd.setAdapter(adapter);
                    } else {
                        FoToast.toast(MyApplication.getContext(), jsonObject.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideLoading();
            }
        });
    }

}
