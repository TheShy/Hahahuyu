package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/6/13.
 * 图片adapter
 */
public class ImageAdapter extends BaseAdapter {
    private List<String> list = new ArrayList<>();
    private Context context;

    public ImageAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setImaList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
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
        ImageView imageview;
        if (convertView == null) {
            imageview = new ImageView(context);
            imageview.setLayoutParams(new GridView.LayoutParams((720-10)/4, (720-10)/4));
            imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageview.setPadding(8, 8, 8, 8);
        } else {
            imageview = (ImageView) convertView;
        }
        imageview.setImageURI(Uri.parse(list.get(position)));
        return imageview;
    }
}
