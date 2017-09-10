package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.touyuanren.hahahuyu.R;

import java.util.List;

/**
 * Created by Jing on 2016/7/1.
 * 红包墙适配器
 */
public class HongBaoWallAdapter extends BaseAdapter {
    private Context context;
    private List mList;

    public HongBaoWallAdapter() {
    }

    public HongBaoWallAdapter(Context context, List mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);

    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = LayoutInflater.from(context).inflate(R.layout.item_hongbao_wall, null);
        return mView;
    }
}
