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
import com.touyuanren.hahahuyu.bean.HuoDongInfo;
import com.touyuanren.hahahuyu.http.HttpApi;

import java.util.ArrayList;

/**
 * Created by liang on 2016/3/28.
 * 活动列表展示的adapter；相关界面都是用这一个adapter
 */
public class HuoDongAdapter extends BaseAdapter {

    ArrayList<HuoDongInfo> mList;
    LayoutInflater inflater;
    Context context;

    //控制数据从外部传入
    public HuoDongAdapter(Context context, ArrayList<HuoDongInfo> mList) {
        this.mList = mList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public void onDateChange(ArrayList<HuoDongInfo> mList) {
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
            convertView = inflater.inflate(R.layout.mb_list_activity, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.topic_activity_mb);
            viewHolder.startTime = (TextView) convertView.findViewById(R.id.start_time_activity_mb2);
            viewHolder.baomingEndTime = (TextView) convertView.findViewById(R.id.baoming_end_time_activity2);
            viewHolder.haibaoIma = (ImageView) convertView.findViewById(R.id.haibao_img_mb);
            viewHolder.isFreeTicket= (TextView) convertView.findViewById(R.id.lable_activity_ticket);
            viewHolder.address= (TextView) convertView.findViewById(R.id.address_activity_mb);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.address.setText(mList.get(position).getAddress());
        viewHolder.title.setText(mList.get(position).getTitle());
        viewHolder.startTime.setText(mList.get(position).getStarttime());
        viewHolder.baomingEndTime.setText(mList.get(position).getClose_time());
        String imaPath = HttpApi.ROOT_PATH + mList.get(position).getPoster();
//        Glide.with(context).load(imaPath).placeholder(R.mipmap.a1).error(R.mipmap.a1).into(viewHolder.haibaoIma);
        Glide.with(context).load(imaPath).error(R.drawable.a1).into(viewHolder.haibaoIma);

        //如果图片修改，则不能使用缓存，重新进行加加载；
//        Uri uri = Uri.parse("http://www.csdn.com/xxx,jpg");
//        Glide.with(context).invalidate(uri);
        if ("0".equals(mList.get(position).getTicket())){
            viewHolder.isFreeTicket.setTextColor(context.getResources().getColor(R.color.color_ceshi));
            viewHolder.isFreeTicket.setText(context.getString(R.string.free_activity));
        }else{
            viewHolder.isFreeTicket.setTextColor(context.getResources().getColor(R.color.red));
            viewHolder.isFreeTicket.setText(context.getString(R.string.charge_activity));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView title, startTime, baomingEndTime,isFreeTicket,address;
        ImageView haibaoIma;
    }

}
