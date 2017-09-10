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
import com.touyuanren.hahahuyu.audio.bean.SendNewsInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/21/021.
 */

public class SendNewsListAdatper extends BaseAdapter {

    ArrayList<SendNewsInfo> mList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    public SendNewsListAdatper() {

    }

    public SendNewsListAdatper(ArrayList<SendNewsInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<SendNewsInfo> mList) {
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
    public View getView(int position, View convertView, ViewGroup parent
    ) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.send_news_list_itme, null);
            viewHolder = new ViewHolder();
            viewHolder.newsIma = (ImageView) convertView.findViewById(R.id.send_news_list_item_iv);
            viewHolder.title = (TextView) convertView.findViewById(R.id.send_news_list_item_tv_title);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.send_news_list_item_tv_time);
            viewHolder.dele= (TextView) convertView.findViewById(R.id.send_news_list_item_tv_dele);
            viewHolder.edit = (TextView) convertView.findViewById(R.id.send_news_list_item_tv_edit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.fabuTime.setText(mList.get(position).getAdd_time());

        String imaPath = HttpApi.ROOT_PATH + mList.get(position).getImage();
        Glide.with(context).load(imaPath).error(R.drawable.a1).into(viewHolder.newsIma);

        return convertView;
    }





    public class ViewHolder {
        TextView title, fabuTime, dele,edit;
        ImageView newsIma;
    }
}
