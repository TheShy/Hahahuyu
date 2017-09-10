package com.touyuanren.hahahuyu.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.TimeLineModel;
import com.touyuanren.hahahuyu.utils.FoToast;

import java.util.List;


public class ProgrammeTimeLineAdapter extends BaseAdapter {

    Context context;
    List<TimeLineModel> list;

    public ProgrammeTimeLineAdapter(Context context, List<TimeLineModel> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold hold;
        if (convertView == null) {
            hold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_programme_timeline, null);
            convertView.setTag(hold);
        } else {
            hold = (ViewHold) convertView.getTag();
        }
        hold.imageView = (ImageView) convertView.findViewById(R.id.left_imageview);
        hold.pgTitle = (TextView) convertView.findViewById(R.id.right_textview);
        hold.pgPeople = (TextView) convertView.findViewById(R.id.tv_name_pgShow);
        hold.pgTime = (TextView) convertView.findViewById(R.id.tv_time_pg);
        hold.pgDaShang = (TextView) convertView.findViewById(R.id.tv_dashang_pg);
        //赋值
        hold.imageView.setImageResource(list.get(position).getProgrammeImg());
        hold.pgTitle.setText(list.get(position).getProgrammeTitle());
        hold.pgTime.setText(list.get(position).getProgrammeTime());
        hold.pgPeople.setText(list.get(position).getProgrammePeople());
        //打赏监听事件
        hold.pgDaShang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //进行打赏
                FoToast.toast(context,"打赏成功");
            }
        });
        return convertView;
    }

    static class ViewHold {
        public TextView pgTime, pgTitle, pgPeople, pgDaShang;
        public ImageView imageView;
    }

}
