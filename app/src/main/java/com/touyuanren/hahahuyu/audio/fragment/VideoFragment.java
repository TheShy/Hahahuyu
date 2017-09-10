package com.touyuanren.hahahuyu.audio.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.activity.VedioListDetail;
import com.touyuanren.hahahuyu.audio.bean.VedioInfo;
import com.touyuanren.hahahuyu.audio.adapter.VedioListAdapter;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13/013.
 * 修改列表；
 */

public class VideoFragment extends BaseFragment {

    private static final String TAG = "VideoFragment";
    private View view;
    private ListView mListView;
    private ArrayList<VedioInfo> mList = new ArrayList<>();
    private VedioListAdapter mAdapter;
    private ArrayList<VedioInfo> totalList;

    @Override
    public View getShowView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.video_fragment,null);
        mListView = (ListView) view.findViewById(R.id.video_fragment_lv);
        initData();
        return view;
    }
    private void initData() {
        mList=new ArrayList<>();
        mAdapter = new VedioListAdapter(mList, getActivity());
        mListView.setAdapter(mAdapter);
        getAudioList();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                VedioInfo mInfo = (VedioInfo) adapter.getItemAtPosition(position);
                Intent mIntent = new Intent(getActivity(), VedioListDetail.class);
                mIntent.putExtra("id", mList.get(position).getId() + "");
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mInfo);
                mIntent.putExtras(bundle);
                startActivity(mIntent);
            }
        });
    }
    private void getAudioList() {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.VEDIO_LIST, formMap, new StringCallback() {
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

                            VedioInfo mInfo=new Gson().fromJson(jsonObject2.toString(),VedioInfo.class);
                            mList.add(mInfo);
                        }
                        mListView.setAdapter(new VedioListAdapter(mList,getActivity()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hideLoading();
                }
            }
        });
    }
}
