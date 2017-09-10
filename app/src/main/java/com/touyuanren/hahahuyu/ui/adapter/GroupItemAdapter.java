package com.touyuanren.hahahuyu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FriendGroupItem;

import java.util.List;

/**
 * Created by Jing on 2017/2/21.
 */

public class GroupItemAdapter extends BaseAdapter {
    private Context mContext;
    private List<FriendGroupItem> mItems;

    public GroupItemAdapter(Context mContext, List<FriendGroupItem> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int Index) {
        return mItems.get(Index);
    }

    @Override
    public long getItemId(int Index) {
        return Index;
    }

    @Override
    public View getView(int Index, View mView, ViewGroup mParent) {
        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_group_item, null);
        //绑定好友结构中的Title
        ((TextView) mView.findViewById(R.id.Group_Item_Title)).setText(mItems.get(Index).getTitle());
        //绑定好友结构中的Content
        ((TextView) mView.findViewById(R.id.Group_Item_Content)).setText(mItems.get(Index).getContent());
        return mView;
    }

}
