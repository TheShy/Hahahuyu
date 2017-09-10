package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.NewsItemBean;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/9/20.
 * 新闻适配器
 */
public class NewsAdapter extends BaseAdapter {
    ArrayList<NewsItemBean> mList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public NewsAdapter() {
    }

    public NewsAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public NewsAdapter(ArrayList<NewsItemBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<NewsItemBean> mList) {
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
            convertView = inflater.inflate(R.layout.item_news_list, null);
            viewHolder = new ViewHolder();
            viewHolder.newsIma = (ImageView) convertView.findViewById(R.id.ima_news_item);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title_news);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.tv_fabu_tiem_item);
            viewHolder.source = (TextView) convertView.findViewById(R.id.tv_source_news_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String fabutime=mistiming(mList.get(position).getTime());
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.fabuTime.setText(fabutime);
        viewHolder.source.setText(mList.get(position).getSource());
        Log.e("ima", mList.get(position).getImapath());
        Glide.with(context).load("http://hdoms.momohudong.com/"+ mList.get(position).getImapath()).error(R.drawable.jiazaishibai).into(viewHolder.newsIma);
        return convertView;
    }

    public class ViewHolder {
        TextView title, fabuTime,source;
        ImageView newsIma;
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
