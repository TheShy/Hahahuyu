package com.touyuanren.hahahuyu.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.DongTaiInfo;
import com.touyuanren.hahahuyu.bean.DongTaiInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.DongTaiAdapter;
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
 * Created by Jing on 2016/10/27.
 * 好友动态
 */
public class DongTaiFrag extends  BaseFragment implements LoadListView.ILoadListener {
    private static final  String TAG="DongTaiFrag";
    private View  view;
    private LoadListView lv_dongtai;
    private ArrayList<DongTaiInfo> dongtaiList = new ArrayList<>();
    /**
     * 控制页数
     */
    private int page = 1;
    private DongTaiAdapter adapter;
    private ArrayList<DongTaiInfo> totalList = new ArrayList<>();
    /**
     * 刷新控件
     */
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View getShowView(LayoutInflater inflater) {
        view=inflater.inflate(R.layout.frag_dongtai,null);
        lv_dongtai = (LoadListView) view.findViewById(R.id.llv_dongtai_frag);
        lv_dongtai.setInterface(this);
        adapter = new DongTaiAdapter(dongtaiList, getActivity());
        lv_dongtai.setAdapter(adapter);
        initSwipeLayout();
        getDongtaiList(page);
        return view;
    }
    private void initSwipeLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_dongtai_frag);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        //swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        int size = DensityUtil.dip2pxComm(getActivity(), 25);
        // 第一次进入页面的时候显示加载进度条
        swipeRefreshLayout.setProgressViewOffset(false, 0, size);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getDongtaiList(page);
            }
        });
    }
    public void getDongtaiList(final int page) {
        Map<String, String> formMap = new HashMap<>();
        formMap.put("act", "list_dt");
        formMap.put("page", page + "");
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.USER_DONGTAI, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getContext(),R.string.intent_error,Toast.LENGTH_LONG).show();
            }


            @Override
            public void onResponse(String response) {
                Log.e(TAG, response);
                if (response != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equals("1")) {
                            dongtaiList.clear();
                            DongTaiInfoBean dongTaiInfoBean = new Gson().fromJson(response, DongTaiInfoBean.class);
                            dongtaiList = (ArrayList<DongTaiInfo>) dongTaiInfoBean.getData().getList();
//                            if(dongtaiList.size()<1){
//                                hideLoading();
//                                swipeRefreshLayout.setRefreshing(false);
//                                lv_dongtai.loadComplete();
//                                FoToast.toast(MyApplication.getContext(), "没用更多了");
//                                return;
//                            }
                            if (page == 1) {
                                totalList.clear();
                                adapter.setList(dongtaiList);
                                totalList.addAll(dongtaiList);
                            } else {
                                if (dongtaiList.size() < 1) {
                                    FoToast.toast(MyApplication.getContext(), "没用更多了");
                                }
                                totalList.addAll(dongtaiList);
                                adapter.setList(totalList);
                                lv_dongtai.loadComplete();
                            }
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onLoad() {
        page++;
        getDongtaiList(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
