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
import com.touyuanren.hahahuyu.bean.UserAlbumInfo;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.utils.CommonUtils;
import com.touyuanren.hahahuyu.utils.FoContents;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/20.
 * 相册适配器
 */
public class MyAlbumAdapter extends BaseAdapter {
    private ArrayList<Object> list;
    private Context context;
    LayoutInflater inflater;

    public MyAlbumAdapter() {

    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyAlbumAdapter(Context context, ArrayList<Object> list) {
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
//            if (list.get(position).equals("add")) {
//                convertView = inflater.inflate(R.layout.item_album_add, null);
//            } else {
                convertView = inflater.inflate(R.layout.item_gv_album, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_name_album_item);
                viewHolder.count = (TextView) convertView.findViewById(R.id.tv_count_album_item);
                viewHolder.fengmian = (ImageView) convertView.findViewById(R.id.ima_album_fengmian_item);
                ViewGroup.LayoutParams para = viewHolder.fengmian.getLayoutParams();
                para.width = (int) ((MyApplication.screenWidth - CommonUtils.dp2px(context, 30)) / 2);//一屏显示两列
                para.height = para.width;
                viewHolder.fengmian.setLayoutParams(para);
//            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).equals("add")) {

        } else {
            UserAlbumInfo userAlbumInfo = (UserAlbumInfo) list.get(position);
            viewHolder.title.setText(userAlbumInfo.getAlbum_name());
            viewHolder.count.setText(userAlbumInfo.getCount() + "张");
            Glide.with(context).load(HttpApi.ROOT_PATH + userAlbumInfo.getPic()).placeholder(R.drawable.album_user).error(R.drawable.album_user).into(viewHolder.fengmian);
        }
        return convertView;
    }

    public class ViewHolder {
        TextView title, count;
        ImageView fengmian;
    }
}
