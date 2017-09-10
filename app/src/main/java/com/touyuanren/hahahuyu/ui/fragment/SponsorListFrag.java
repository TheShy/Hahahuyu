package com.touyuanren.hahahuyu.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.ui.activity.account.OrganizerActivity;
import com.touyuanren.hahahuyu.ui.adapter.SponsorListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/7/13.
 * 主办方列表
 */
public class SponsorListFrag extends BaseFragment {
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
    private List sponsorList;

    @Override
    public View getShowView(LayoutInflater inflater) {
        mContentView = inflater.inflate(R.layout.frag_sponsor_list, null);
        lv_sponsor = (ListView) mContentView.findViewById(R.id.lv_sponsor_frag);
        initData();
        return mContentView;
    }

    //加载主办方数据
    private void initData() {
        sponsorList=new ArrayList();
        for (int i=0;i<19;i++){
            sponsorList.add("de");
        }
        lv_sponsor.setAdapter(new SponsorListAdapter(MyApplication.getContext(),sponsorList));
        lv_sponsor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent    mIntent = new Intent(getActivity(), OrganizerActivity.class);
                startActivity(mIntent);
            }
        });
    }
}
