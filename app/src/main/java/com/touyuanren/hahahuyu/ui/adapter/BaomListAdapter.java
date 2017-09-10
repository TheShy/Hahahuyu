package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.BaomListInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/31/031.
 */

public class BaomListAdapter extends BaseAdapter {


    ArrayList<BaomListInfo.DataBean> mList = new ArrayList<>();
    Context context;

    //控制数据从外部传入
    public BaomListAdapter(ArrayList<BaomListInfo.DataBean> list, Context context) {
        this.mList = list;
        this.context = context;
    }


    public void setList(ArrayList<BaomListInfo.DataBean> mList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.baom_itme, null);
            viewHolder = new ViewHolder();

            viewHolder.user_name= (TextView) convertView.findViewById(R.id.baom_tv_use_name);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.baom_id_user_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.user_name.setText(mList.get(position).getNick_name());
        String imaPath = HttpApi.ROOT_PATH + mList.get(position).getPhoto();
        Glide.with(context).load(imaPath).error(R.drawable.a1).into(viewHolder.img);


        return convertView;
    }

    public class ViewHolder {
        TextView user_name;
        ImageView img;
    }
}
