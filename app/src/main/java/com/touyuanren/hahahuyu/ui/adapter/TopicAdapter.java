package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TopicInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.List;

/**
 * Created by Jing on 2016/11/7.
 * 话题适配器
 */
public class TopicAdapter extends BaseAdapter {
    private Context mContext;
    private List<TopicInfo> commentList;
    LayoutInflater inflater;

    public TopicAdapter() {
        inflater = LayoutInflater.from(MyApplication.getContext());
    }

    public TopicAdapter(Context mContext, List<TopicInfo> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
        inflater = LayoutInflater.from(MyApplication.getContext());
    }

    public void setList(List<TopicInfo> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comment_lv, null);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_comment_item);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.tv_title_time_item);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_title_content_item);
            viewHolder.ima= (ImageView) convertView.findViewById(R.id.icon_img_comment_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(commentList.get(position).getNick_name());
        viewHolder.fabuTime.setText(commentList.get(position).getAdd_time());
        viewHolder.content.setText(commentList.get(position).getContent());
//        viewHolder.ima.setImageURI(Uri.parse(HttpApi.ROOT_PATH + commentList.get(position).getPhoto()));
        Glide.with(mContext).load(HttpApi.ROOT_PATH+commentList.get(position).getPhoto()).error(R.drawable.icon_user).into(viewHolder.ima);
        return convertView;
    }

    public class ViewHolder {
        TextView name, fabuTime, content;
        ImageView ima;
    }

}
