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
import com.touyuanren.hahahuyu.bean.ZiMeiTiInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/24.
 * 自媒体适配器
 */
public class ZiMeiTiAdapter extends BaseAdapter{
    ArrayList<ZiMeiTiInfo> mList = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    private String user_id;

    public ZiMeiTiAdapter() {

    }

    public ZiMeiTiAdapter(ArrayList<ZiMeiTiInfo> mList, Context context) {
        this.mList = mList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<ZiMeiTiInfo> mList) {
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
            convertView = inflater.inflate(R.layout.item_zimeiti_list, null);
            viewHolder = new ViewHolder();
            viewHolder.newsIma = (ImageView) convertView.findViewById(R.id.ima_zimeiti_item);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_friend_title);
            viewHolder.fabuTime = (TextView) convertView.findViewById(R.id.tv_zimeiti_tiem_item);
            viewHolder.source = (TextView) convertView.findViewById(R.id.tv_source_zimeiti_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.fabuTime.setText(mistiming(mList.get(position).getAdd_time()));

        user_id = mList.get(position).getUser_id();//记录当前发布者id
        viewHolder.fabuTime.setText(mList.get(position).getAdd_time());
        viewHolder.source.setText(mList.get(position).getNick_name());

        if (mList.get(position).getTitle().length() > 40) {
            String title = mList.get(position).getTitle().substring(0, 40);
            viewHolder.title.setText(title);
        } else {
            viewHolder.title.setText(mList.get(position).getTitle());
        }
        Glide.with(context).load(HttpApi.ROOT_PATH + mList.get(position).getImg_path()).error(R.drawable.jiazaishibai).into(viewHolder.newsIma);

//        viewHolder.source.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent mIntent = new Intent(context, FriendActivity.class);
//                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                mIntent.putExtra("user_id",user_id);
//                context.startActivity(mIntent);
//            }
//        });

        return convertView;
    }

    public class ViewHolder {
        TextView title, fabuTime, source;
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
