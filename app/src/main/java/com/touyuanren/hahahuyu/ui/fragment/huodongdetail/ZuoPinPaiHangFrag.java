package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.bean.ZuoPinInfo;
import com.touyuanren.hahahuyu.bean.ZuoPinInfoBean;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.adapter.ZuoPinAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;
import com.touyuanren.hahahuyu.utils.FoHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jing on 2016/9/27.
 * 有作品的实现作品排行，没有的置空
 */
public class ZuoPinPaiHangFrag extends BaseFragment {
    /**
     * 总布局
     */
    private View mView;
    private ZuoPinAdapter adapter;
    private GridView gv_zuopin;
    private ArrayList<String> zuopinList;
    private static final String TAG = "ZuoPinPaiHangFrag";
    //将活动对象传入
    private HuoDongInfo mHuoDongInfo;
    private ArrayList<ZuoPinInfo> mList;
    private ArrayList<ZuoPinInfo> mTotalList;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_zuopinpaihang, null);
        gv_zuopin = (GridView) mView.findViewById(R.id.gv_zuopin_game);
        Bundle mBundle = getArguments();
        mHuoDongInfo = (HuoDongInfo) mBundle.getSerializable("huodongInfo");
        initData();
        return mView;
    }

    private void initData() {
        mList = new ArrayList<>();
        mTotalList = new ArrayList<>();
        adapter = new ZuoPinAdapter(MyApplication.getContext(), mList);
        gv_zuopin.setAdapter(adapter);
        getZuoPinInfo(1);
    }

    //获取赛事信息
    private void getZuoPinInfo(final int page) {
        //提交网络请求
        Map<String, String> formMap = new HashMap<>();
        formMap.put("page", page + "");
        formMap.put("act", "zplb");
        formMap.put("hd_id", mHuoDongInfo.getId());
        Log.e("id", mHuoDongInfo.getId());
        FoHttp.getMsg(HttpApi.ROOT_PATH + HttpApi.UPLOAD_INFO, formMap, new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(),"网络错误，请检查您的网络连接！",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    ZuoPinInfoBean mZuoPinInfoBean = new Gson().fromJson(response, ZuoPinInfoBean.class);
                    mList = (ArrayList<ZuoPinInfo>) mZuoPinInfoBean.getData().getList();
                    mTotalList.addAll(mList);
                    if (page == 1) {
                        adapter.setList(mList);
                    } else {
                        adapter.setList(mTotalList);
                    }
                }
            }
        });
    }

    public static final ZuoPinPaiHangFrag newInstance(HuoDongInfo mHuoDongInfo) {
        ZuoPinPaiHangFrag f = new ZuoPinPaiHangFrag();
        Bundle bdl = new Bundle();
        bdl.putSerializable("huodongInfo", mHuoDongInfo);
        f.setArguments(bdl);
        return f;
    }
}
