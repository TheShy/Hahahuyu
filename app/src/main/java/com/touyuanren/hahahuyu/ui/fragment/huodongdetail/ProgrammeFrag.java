package com.touyuanren.hahahuyu.ui.fragment.huodongdetail;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TimeLineModel;
import com.touyuanren.hahahuyu.ui.adapter.ProgrammeTimeLineAdapter;
import com.touyuanren.hahahuyu.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/6/24.
 * 展示节目详情
 */
public class ProgrammeFrag extends BaseFragment {
    /**
     * 节目单时间轴列表
     */
    private ListView programmeTimeLIne;
    private ProgrammeTimeLineAdapter adapter;
    private List<TimeLineModel> list;
    @Override
    public View getShowView(LayoutInflater inflater) {
        View  mView=inflater.inflate(R.layout.frag_programme,null);
        programmeTimeLIne= (ListView) mView.findViewById(R.id.lv_programme_frag);
        initData();
        initView();
        return mView;
    }
    private void initView() {
        adapter=new ProgrammeTimeLineAdapter(getActivity(), list);
        programmeTimeLIne.setAdapter(adapter);
    }

    private void initData() {
        list=new ArrayList<TimeLineModel>();
        for (int i=0;i<8;i++){
            list.add(new TimeLineModel(getString(R.string.time_activity_detail),getString(R.string.programme_title),getString(R.string.programme_name),R.drawable.round_red));
        }

    }
}
