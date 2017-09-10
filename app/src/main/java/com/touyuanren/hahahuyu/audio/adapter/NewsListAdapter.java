package com.touyuanren.hahahuyu.audio.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.audio.bean.NewsInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/14/014.
 */

public class NewsListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<NewsInfo.DataBean.InfoBean> mList = new ArrayList<>();
    Context context;
    public NewsListAdapter() {
    }

    public NewsListAdapter(ArrayList<NewsInfo.DataBean.InfoBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setList(ArrayList<NewsInfo.DataBean.InfoBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.news_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.news_list_item_tv_title);
            viewHolder.send_name = (TextView) convertView.findViewById(R.id.news_list_item_tv_send_name);
            viewHolder.look= (TextView) convertView.findViewById(R.id.news_list_item_tv_look);
            viewHolder.img= (ImageView) convertView.findViewById(R.id.news_list_itme_iv_img);
            viewHolder.send= (ImageView) convertView.findViewById(R.id.news_list_item_iv_send);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.send_name.setText(mList.get(position).getName());
        viewHolder.look.setText(mList.get(position).getBrowse());
        String imaPath = HttpApi.ROOT_PATH + mList.get(position).getImage();
        Glide.with(context).load(imaPath).error(R.drawable.a1).into(viewHolder.img);

        return convertView;
    }


    public class ViewHolder {
        TextView title, send_name, look;
        ImageView img,send;
    }
}
