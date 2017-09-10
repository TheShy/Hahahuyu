package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.ZuoPinInfo;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/9/27.
 * 作品排行adapter
 */
public class ZuoPinAdapter extends BaseAdapter {
    private ArrayList<ZuoPinInfo> list;
    private Context context;

    public ZuoPinAdapter() {

    }

    public void setList(ArrayList<ZuoPinInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ZuoPinAdapter(Context context, ArrayList<ZuoPinInfo> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_zuopin, null);
        return convertView;
    }
}
