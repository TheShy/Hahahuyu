package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.Img_listEntity;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.CommonUtils;
import com.touyuanren.hahahuyu.utils.FoContents;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/11/11.
 * 日志照片适配器
 */
public class LogPicAdapter extends BaseAdapter {
    private ArrayList<Img_listEntity> list;
    private Context context;
    LayoutInflater inflater;

    public LogPicAdapter() {

    }

    public void setList(ArrayList<Img_listEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public LogPicAdapter(Context context, ArrayList<Img_listEntity> list) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return FoContents.ADD_TYPE;
        } else {
            return FoContents.IMAGE_TYPE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_gv_log_pic, null);
            viewHolder.ima = (ImageView) convertView.findViewById(R.id.ima_log_pic_item);
            ViewGroup.LayoutParams para = viewHolder.ima.getLayoutParams();
            para.width = (int) ((MyApplication.screenWidth - CommonUtils.dp2px(context, 25)) / 4);//一屏显示两列
            para.height = para.width;
            viewHolder.ima.setLayoutParams(para);
            viewHolder.ima.setScaleType(ImageView.ScaleType.CENTER_CROP);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Glide.with(context).load(HttpApi.ROOT_PATH + list.get(position).getImg_path()).placeholder(R.drawable.album_user).error(R.drawable.album_user).into(viewHolder.ima);
        return convertView;
    }

    public class ViewHolder {
        ImageView ima;
    }
}
