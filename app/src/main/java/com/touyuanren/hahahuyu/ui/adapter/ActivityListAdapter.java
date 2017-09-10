package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/1/28.
 * 活动列表的适配器
 */
public class ActivityListAdapter extends BaseAdapter {
    private List<HuoDongInfo> list = new ArrayList<HuoDongInfo>();
    private Context context;

    public ActivityListAdapter(List<HuoDongInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.mb_list_activity, null);
        return view;
    }
}
