package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/7/19.
 * 赛事的模板
 */
public class GameAdapter extends BaseAdapter {
    ArrayList<HuoDongInfo> mList;
    LayoutInflater inflater;
    Context context;

    public GameAdapter() {
    }

    public GameAdapter(ArrayList<HuoDongInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
         return mList == null ? 0 : mList.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mb_list_game, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title_game_mb);
            viewHolder.startTime = (TextView) convertView.findViewById(R.id.tv_time_game_mb);
            viewHolder.gameAddress = (TextView) convertView.findViewById(R.id.tv_address_game_mb);
            viewHolder.haibaoIma = (ImageView) convertView.findViewById(R.id.ima_haibao_game_mb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.startTime.setText(mList.get(position).getStarttime()+"~"+mList.get(position).getEndtime());
        viewHolder.gameAddress.setText(mList.get(position).getAddress());
        Glide.with(context).load(HttpApi.ROOT_PATH+mList.get(position).getPoster()).error(R.drawable.a1).into(viewHolder.haibaoIma);
        return convertView;
    }

    public class ViewHolder {
        TextView title, startTime, gameAddress;
        ImageView haibaoIma;
    }
}
