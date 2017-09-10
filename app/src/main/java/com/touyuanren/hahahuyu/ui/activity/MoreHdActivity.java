package com.touyuanren.hahahuyu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.activity.AudioListDetail;
import com.touyuanren.hahahuyu.audio.bean.AudioDetails;
import com.touyuanren.hahahuyu.audio.bean.AudioInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.audio.adapter.AudioListAdapter;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Jing on 2016/2/18.m
 * 按照类别进行展示的界面;暂时显示为敬请期待
 */
public class  MoreHdActivity extends BaseActivity {


    ListView mListView;

    private ArrayList<AudioInfo> mList = new ArrayList<>();
    private AudioListAdapter mAdapter;
    private ArrayList<AudioInfo> totalList;
    private AudioDetails mDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_more_hd);
        setTitleLeftBtn();
        setTitleName("音频列表");
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.act_more_hd_lv);
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                AudioInfo mInfo = (AudioInfo) adapter.getItemAtPosition(position);
                Intent mIntent = new Intent(MoreHdActivity.this, AudioListDetail.class);
                mIntent.putExtra("id", mList.get(position).getId() + "");
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mInfo);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });

    }

    private void initData() {
        mAdapter = new AudioListAdapter(mList, this);
        mListView.setAdapter(mAdapter);
        getAudioList();
    }

    private void getAudioList() {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.GET_ADIO_LIST, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }

            @Override
            public void onResponse(String response) {
                Log.e("respone", response);
                if (response != null) {
                    mList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonObject1.length();i++){
                            JSONObject  jsonObject2= (JSONObject) jsonObject1.get(i);
                            AudioInfo mInfo=new Gson().fromJson(jsonObject2.toString(),AudioInfo.class);
                            mList.add(mInfo);
                        }
                        mListView.setAdapter(new AudioListAdapter(mList,getApplicationContext()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hideLoading();

                }
            }
        });
    }


}
