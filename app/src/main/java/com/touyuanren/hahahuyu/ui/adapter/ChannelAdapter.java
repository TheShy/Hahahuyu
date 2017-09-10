package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.ChannelBean;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/9/26.
 */
public class ChannelAdapter extends BaseAdapter {
    private ArrayList<ChannelBean> channelList;
    private Context context;

    public ChannelAdapter() {
    }

    public ChannelAdapter(Context context, ArrayList<ChannelBean> channelList) {
        this.channelList = channelList;
        this.context = context;
    }

    public void setList(ArrayList<ChannelBean> channelList) {
        this.channelList = channelList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return channelList.size();
    }

    @Override
    public Object getItem(int position) {
        return channelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
            viewHolder = new ViewHolder();
            viewHolder.channel_name = (TextView) convertView.findViewById(R.id.tv_item_channel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.channel_name.setText(channelList.get(position).getChannel_name());
        return convertView;
    }

    public class ViewHolder {
        TextView channel_name;
    }
}
