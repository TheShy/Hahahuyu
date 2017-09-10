package com.touyuanren.hahahuyu.audio.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.adapter.VideoAdapterRcy;
import com.touyuanren.hahahuyu.audio.bean.VedioInfo;
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

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * Created by Liang on 2017/8/4 0004.
 */

public class VideoFragment2 extends BaseFragment {
    private View  view;
    private RecyclerView  mRecyclerview;
    private VideoAdapterRcy  mVideoAdapter;
    private ArrayList<VedioInfo> mList = new ArrayList<>();
    private ArrayList<VedioInfo> totalList;
    private static final  String TAG="VideoFragment2";
    private  View importPanel;
    @Override
    public View getShowView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.fragment_video,null);
        mRecyclerview = (RecyclerView) view.findViewById(R.id.rcy_video_frag);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
        return view;
    }

    private void initData() {
        mList=new ArrayList<>();
        mVideoAdapter=new VideoAdapterRcy(getActivity(),R.layout.item_rcy_video,mList);
//        mRecyclerview.addItemDecoration(new MyDecoration(getActivity(),MyDecoration.VERTICAL_LIST));
        mRecyclerview.setAdapter(mVideoAdapter);

        getAudioList();
//
//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
//                VedioInfo mInfo = (VedioInfo) adapter.getItemAtPosition(position);
//                Intent mIntent = new Intent(getActivity(), VedioListDetail.class);
//                mIntent.putExtra("id", mList.get(position).getId() + "");
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("data", mInfo);
//                mIntent.putExtras(bundle);
//                startActivity(mIntent);
//            }
//        });
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
                Log.e(TAG, response);
                if (response != null) {
                    mList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonObject1 = jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonObject1.length();i++){
                            JSONObject  jsonObject2= (JSONObject) jsonObject1.get(i);

                            VedioInfo mInfo=new Gson().fromJson(jsonObject2.toString(),VedioInfo.class);
                            mList.add(mInfo);
                            if (mList.size()<1){
                                //显示无数据
                                 importPanel = ((ViewStub) view.findViewById(R.id.viewstub_msg)).inflate();
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

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
