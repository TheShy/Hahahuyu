package com.touyuanren.hahahuyu.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.adapter.SponsorListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/7/13.
 * 选手列表
 */
public class PlayerListFrag extends  BaseFragment {
    /**
     * 当前页面
     */
    private View mContentView;
    /**
     * 主办方集合
     */
    private ListView lv_sponsor;
    /**
     * 主办方数据集合
     */
    private List playerList;
    @Override
    public View getShowView(LayoutInflater inflater) {
        mContentView = inflater.inflate(R.layout.frag_player_list, null);
        lv_sponsor = (ListView) mContentView.findViewById(R.id.lv_player_frag);
        initData();
        return mContentView;
    }
    //加载主办方数据
    private void initData() {
        playerList=new ArrayList();
        for (int i=0;i<19;i++){
            playerList.add("de");
        }
        lv_sponsor.setAdapter(new SponsorListAdapter(MyApplication.getContext(), playerList));
    }
}
