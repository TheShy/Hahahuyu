package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.touyuanren.hahahuyu.Application.MyApplication;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserPicInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2016/11/2.
 * 用户相册适配器
 */
public class UserAlbumAdapter extends BaseAdapter {

    private List<UserPicInfo> list = new ArrayList<>();
    private Context context;

    public UserAlbumAdapter(List<UserPicInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void UserAlbumAdapter(List<UserPicInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setList(List<UserPicInfo> list) {
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

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_my_info_pic, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.setting_image_view);
//            viewHolder.imageView.setLayoutParams(new GridView.LayoutParams((720 - 10) / 4, (720 - 10) / 4));
//            viewHolder. imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams para = viewHolder.imageView.getLayoutParams();
            para.width = (int) ((MyApplication.screenWidth - 10) / 4);//一屏显示两列
            para.height = para.width;
            viewHolder.imageView.setLayoutParams(para);
//            imageview.setPadding(8, 8, 8, 8);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageURI(Uri.parse(HttpApi.ROOT_PATH + list.get(position).getPath()));
//        imageview.setImageURI(HttpApi.ROOT_PATH + list.get(position).getPath());
        return convertView;
    }
    public class ViewHolder {
        ImageView imageView;
    }
}
