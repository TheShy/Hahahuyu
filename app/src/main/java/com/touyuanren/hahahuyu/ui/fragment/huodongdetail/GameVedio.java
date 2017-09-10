package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.GameVideoInfo;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.GameVideoListAdapter;
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
 * Created by Administrator on 2017/8/4/004.
 */

public class GameVedio extends BaseFragment {
    private View  view;
    private RecyclerView mRecyclerview;
    private GameVideoListAdapter mVideoAdapter;
    private ArrayList<GameVideoInfo> mList = new ArrayList<>();
    private HuoDongInfo mHuoDongInfo;
    private  View importPanel;
    private static final  String TAG="GameVedio";

    @Override
    public View getShowView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.game_vedio_layout,null);
        Bundle mBundle = getArguments();
        mHuoDongInfo = (HuoDongInfo) mBundle.getSerializable("huodongInfo");
        mRecyclerview = (RecyclerView) view.findViewById(R.id.game_vedio_recy);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        return view;
    }

    private void initData() {

        mList=new ArrayList<>();
        mVideoAdapter=new GameVideoListAdapter(getActivity(),R.layout.item_rcy_video,mList);
        mRecyclerview.setAdapter(mVideoAdapter);

        getGameVideoList();
    }

    private void getGameVideoList() {
        showLoading();
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("hd_id", mHuoDongInfo.getId());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.GAME_VEDIO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                hideLoading();
            }
            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    mList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonObject1.length();i++){
                            JSONObject  jsonObject2= (JSONObject) jsonObject1.get(i);

                            GameVideoInfo mInfo=new Gson().fromJson(jsonObject2.toString(),GameVideoInfo.class);
                            mList.add(mInfo);
                            if (mList.size()<1){
                                //显示无数据
                                importPanel = ((ViewStub) view.findViewById(R.id.game_vedio_view_tag)).inflate();
                            }else{
                                mVideoAdapter.notifyDataSetChanged();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hideLoading();
                }
            }
        });
    }


    public static final GameVedio newInstance(HuoDongInfo mHuoDongInfo) {
        GameVedio f = new GameVedio();
        Bundle bdl = new Bundle();
        bdl.putSerializable("huodongInfo", mHuoDongInfo);
        f.setArguments(bdl);
        return f;
    }
}
