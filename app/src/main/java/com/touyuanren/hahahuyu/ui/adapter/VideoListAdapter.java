package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.AudioInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/13/013.
 */

public class VideoListAdapter extends BaseAdapter {

    ArrayList<AudioInfo> mList = new ArrayList<>();
    Context context;

    public VideoListAdapter(ArrayList<AudioInfo> list, Context context) {
        this.mList = list;
        this.context = context;
    }

    public void setList(ArrayList<AudioInfo> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.morehd_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.haibao_img_mb);
            viewHolder.time = (TextView) convertView.findViewById(R.id.lable_activity_ticket);
            viewHolder.title = (TextView) convertView.findViewById(R.id.topic_activity_mb);
            viewHolder.content = (TextView) convertView.findViewById(R.id.address_activity_mb);
            viewHolder.price = (TextView) convertView.findViewById(R.id.baoming_end_time_activity2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.content.setText(mList.get(position).getDescription());
        viewHolder.price.setText(mList.get(position).getPrice());
        viewHolder.time.setText(mList.get(position).getAdd_time());
//        String time = mistiming(mList.get(position).getAdd_time());
        viewHolder.img.setBackgroundResource(R.drawable.ic_launcher);
//        String imaPath = HttpApi.ROOT_PATH + mList.get(position).getGoods().getGoods_img();
//        Glide.with(context).load(imaPath).placeholder(R.drawable.moren).error(R.drawable.moren).into(viewHolder.img);

        return convertView;
    }


    public final class ViewHolder {
        public TextView title, content, price, time;
        public ImageView img;
    }

    //判断时间差：新闻发布时间与当前系统时间的差值：如果小于60分钟换算成分钟；大于60分钟，换算成小时；大于24小时，换算成天
    public String mistiming(String time) {
        long currentTimme = System.currentTimeMillis();
        long lcc_time = Long.valueOf(time);
        long shijianCha = currentTimme - lcc_time;
        int count = 0;
        //进行判断
        if (shijianCha > 24 * 60 * 60 * 1000) {
            count = (int) (shijianCha / (24 * 60 * 60 * 1000));
            return count + "天前";
        } else if (shijianCha > 60 * 60 * 1000) {
            count = (int) (shijianCha / (60 * 60 * 1000));
            return count + "小时前";
        } else {
            count = (int) (shijianCha / (60 * 1000));
            return shijianCha + "分钟前";
        }
    }
}
