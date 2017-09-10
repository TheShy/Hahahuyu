package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.touyuanren.hahahuyu.R;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/17.
 */
public class PlayerSortAdapter extends BaseAdapter {
    private ArrayList<String> list;
    private Context context;

    public PlayerSortAdapter() {
    }

    public PlayerSortAdapter(Context context, ArrayList<String> list) {
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
        convertView = LayoutInflater.from(context).inflate(R.layout.item_gv_player, null);
        return convertView;
    }
}
