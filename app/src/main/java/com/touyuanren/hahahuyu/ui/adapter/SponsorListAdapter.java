package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;

import java.util.List;

/**
 * Created by Jing on 2016/7/13.
 */
public class SponsorListAdapter extends BaseAdapter {
    private Context mContext;
    private List mList;

    public SponsorListAdapter() {
    }

    public SponsorListAdapter(Context mContext, List mList) {
        this.mContext = mContext;
        this.mList = mList;
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_list_sponsor, parent, false);
//            holder.line = (View) convertView.findViewById(R.id.v_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.content.setText(list.get(position).getText());
        return convertView;
    }

    public static class ViewHolder {
        RelativeLayout title;
        View line;
        TextView date;
        TextView content;
    }
}
