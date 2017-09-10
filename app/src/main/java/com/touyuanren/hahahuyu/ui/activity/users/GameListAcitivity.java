package com.touyuanren.hahahuyu.ui.activity.users;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.BaomListInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.BaseActivity;
import com.touyuanren.hahahuyu.ui.adapter.BaomListAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/1/001.
 */

public class GameListAcitivity extends BaseActivity {
    private ListView play_lv;
    private ArrayList<BaomListInfo.DataBean> mList = new ArrayList<>();
    private BaomListAdapter adapter;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_player);
        setTitleLeftBtn();
        setTitleName("观众报名列表");
        gameId = getIntent().getStringExtra("gameid");
        play_lv = (ListView) findViewById(R.id.play_lv);
        initData();
    }

    private void initData() {
        mList=new ArrayList<>();
        adapter = new BaomListAdapter(mList, this);
        play_lv.setAdapter(adapter);
        getMathList();
    }

    private void getMathList() {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("hd_id",gameId);
        formMap.put("type","2");

        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.MATH_LIST, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }
            @Override
            public void onResponse(String response) {
                Log.e("response", response);
                if (response != null) {
                    mList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonObject1.length();i++){
                            JSONObject  jsonObject2= (JSONObject) jsonObject1.get(i);
                            BaomListInfo.DataBean mInfo=new Gson().fromJson(jsonObject2.toString(),BaomListInfo.DataBean.class);
                            mList.add(mInfo);
                        }
                        play_lv.setAdapter(new BaomListAdapter(mList,getApplicationContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hideLoading();

                }
            }
        });

    }
}
