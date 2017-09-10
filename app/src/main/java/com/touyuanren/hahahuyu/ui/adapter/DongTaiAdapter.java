package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.DongTaiInfo;
import com.touyuanren.hahahuyu.bean.Img_listEntity;
import com.touyuanren.hahahuyu.http.HttpApi;
import com.touyuanren.hahahuyu.ui.activity.LargeImageActivity;
import com.touyuanren.hahahuyu.widget.GridviewForScrollView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jing on 2016/10/27.
 */
public class DongTaiAdapter extends BaseAdapter {
    ArrayList<DongTaiInfo> mList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;
    private static final String INDEX = "index";
    private static final String TOTAL = "total";
    private static final String IMGS = "imgs";
    public DongTaiAdapter(ArrayList<DongTaiInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public void setList(ArrayList<DongTaiInfo> mList) {
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
            convertView = inflater.inflate(R.layout.item_lv_dongtai, null);
            viewHolder = new ViewHolder();
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content_dongtai_item);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.tv_dongtai_tiem_item);
            viewHolder.source= (TextView) convertView.findViewById(R.id.tv_source_dongtai_item);
            viewHolder.icon= (ImageView) convertView.findViewById(R.id.icon_dongtai_user_item);
            viewHolder.gv_pic= (GridviewForScrollView) convertView.findViewById(R.id.gv_pic_dongtai_item);
            viewHolder.ima_comment= (ImageView) convertView.findViewById(R.id.ima_comment_dongtai_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.fabuTime.setText(mistiming(mList.get(position).getAdd_time()));
        viewHolder.content.setText(mList.get(position).getContent());
        viewHolder.source.setText(mList.get(position).getNick_name());
        Glide.with(context).load(HttpApi.ROOT_PATH + mList.get(position).getPhoto()).placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher).into(viewHolder.icon);
        final ArrayList<Img_listEntity>  picList= (ArrayList<Img_listEntity>) mList.get(position).getImg_list();
        viewHolder.gv_pic.setAdapter(new LogPicAdapter(context,picList));
        final ArrayList<String> mPathList = new ArrayList<String>();
        for (int i = 0; i < picList.size(); i++) {
            mPathList.add(HttpApi.ROOT_PATH + picList.get(i).getImg_path());
        }
        viewHolder.gv_pic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, LargeImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(TOTAL, picList.size());
                bundle.putInt(INDEX, position);
                bundle.putStringArrayList(IMGS, mPathList);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    public class ViewHolder {
        TextView fabuTime,content,source;
        GridviewForScrollView gv_pic;
        ImageView icon;
        ImageView  ima_comment;
    }


    //判断时间差：新闻发布时间与当前系统时间的差值：如果小于60分钟换算成分钟；大于60分钟，换算成小时；大于24小时，换算成天
    public String mistiming(String time) {
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeStemp = 0;
        try {
            Date   date = simpleDateFormat.parse(time);
            timeStemp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentTimme = System.currentTimeMillis();
        Log.e("dd",timeStemp+"/"+currentTimme);
        long shijianCha = currentTimme - timeStemp;
        Log.e("dd",shijianCha+"/"+currentTimme);
        int count = 0;
        //进行判断

        if (shijianCha > 24 * 60 * 60 * 1000) {
            count = (int) (shijianCha / (24 * 60 * 60 * 1000));
            return count + "天前";
        } else if (shijianCha > 60 * 60 * 1000) {
            count = (int) (shijianCha / (60 * 60 * 1000));
            return count + "小时前";
        } else  {
            count = (int) (shijianCha / (60 * 1000));
            return count + "分钟前";
        }
    }
}
