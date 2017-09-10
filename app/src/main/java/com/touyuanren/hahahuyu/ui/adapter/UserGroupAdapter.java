package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.UserGroupBean;

import java.util.ArrayList;

/**
 * Created by Jing on 2016/10/1.
 * 订单适配器
 */
public class UserGroupAdapter extends BaseAdapter {
    ArrayList<UserGroupBean.DataBean.ListBean> mList = new ArrayList<>();
    Context context;

    public UserGroupAdapter(ArrayList<UserGroupBean.DataBean.ListBean> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    public void setList(ArrayList<UserGroupBean.DataBean.ListBean> mList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_group, null);
            viewHolder = new ViewHolder();
            viewHolder.ug_title = (TextView) convertView.findViewById(R.id.textView_ug_name);
            viewHolder.ug_num = (TextView) convertView.findViewById(R.id.textView_ug_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.ug_title.setText(mList.get(position).getName());
        viewHolder.ug_num.setText(mList.get(position).getU_count());

        return convertView;
    }

    public class ViewHolder {
        TextView ug_title, ug_num;
    }
}
