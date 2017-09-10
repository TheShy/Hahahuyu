package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TicketInfo;

import java.util.List;

/**
 * Created by Jing on 2016/8/9.
 * 活动票券列表的adapter
 */
public class TicketAdapter extends BaseAdapter {
    private Context mContext;
    private List<TicketInfo> ticketList;
    LayoutInflater inflater;

    public TicketAdapter() {

    }

    public TicketAdapter(Context mContext, List ticketList) {
        this.mContext = mContext;
        this.ticketList = ticketList;
        this.inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_ticket, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title_ticket);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price_ticket);
            viewHolder.hd_name = (TextView) convertView.findViewById(R.id.hd_name);
            viewHolder.iv_state = (ImageView) convertView.findViewById(R.id.iv_state_ticket);
            viewHolder.tv_introduce = (TextView) convertView.findViewById(R.id.tv_introduce_ticket);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.hd_name.setText(ticketList.get(position).getHd_name());
        viewHolder.tv_title.setText(ticketList.get(position).getTicket_name());
        String introduce = ticketList.get(position).getShuoming();
        if (introduce!=null){
            if (introduce.length() > 40) {
                introduce = introduce.substring(0, 40);
            }
        }
        viewHolder.tv_introduce.setText(introduce);
        viewHolder.iv_state.setVisibility(View.GONE);
        viewHolder.tv_price.setText("￥"+ticketList.get(position).getPrice());
        return convertView;
    }

    public class ViewHolder {
        TextView tv_title, tv_price,tv_introduce,hd_name;
        ImageView iv_state;
    }
}
