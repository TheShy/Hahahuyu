package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FenLeiInfo;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/14.
 * 活动分类
 */
public class HDFenLeiAdapter extends BaseAdapter {
    private ArrayList<FenLeiInfo> fenleiList;
    private Context context;

    public HDFenLeiAdapter(ArrayList<FenLeiInfo> fenleiList, Context context) {
        this.fenleiList = fenleiList;
        this.context = context;
    }

    public void setList(ArrayList<FenLeiInfo> fenleiList) {
        this.fenleiList = fenleiList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fenleiList.size();
    }

    @Override
    public Object getItem(int position) {
        return fenleiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_type_hd, null);
            viewHolder = new ViewHolder();
            viewHolder.fenlei_name = (TextView) convertView.findViewById(R.id.tv_hd_type_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fenlei_name.setText(fenleiList.get(position).getName());
        return convertView;
    }

    public class ViewHolder {
        TextView fenlei_name;
    }
}
