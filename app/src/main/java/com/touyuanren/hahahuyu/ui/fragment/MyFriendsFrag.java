package com.touyuanren.hahahuyu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.touyuanren.hahahuyu.R;
import com.touyuanren.hahahuyu.bean.FriendGroup;
import com.touyuanren.hahahuyu.bean.FriendGroupItem;
import com.touyuanren.hahahuyu.ui.adapter.FriendViewAdapter;
import com.touyuanren.hahahuyu.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jing on 2017/2/27.
 * 个人中心我的好友
 */

public class MyFriendsFrag  extends  BaseFragment {
    /**
     * frag的总布局
     */
    private View mView;
    private ListViewForScrollView listView;
    List<FriendGroup> mFriendGroups;
    @Override
    public View getShowView(LayoutInflater inflater) {
        mView = inflater.inflate(R.layout.frag_myfriends, null);
        listView = (ListViewForScrollView) mView.findViewById(R.id.lv_myfriend_frag);
        mFriendGroups =new ArrayList<>();
        initData();
        return mView;
    }
    public static final MyFriendsFrag newInstance(String newsId) {
        MyFriendsFrag f = new MyFriendsFrag();
        Bundle bdl = new Bundle();
        bdl.putString("newsId", newsId);
        f.setArguments(bdl);
        return f;
    }

    private void initData() {
        List<FriendGroupItem> mFriendGroupItems =new ArrayList<>();
//        GroupItem  grop=new GroupItem("标题","ddddddddddddd");
        for (int i=0;i<8;i++){
            FriendGroupItem grop=new FriendGroupItem("标题"+i,"内容"+i);
            mFriendGroupItems.add(grop);
        }
        FriendGroup friendGroup =new FriendGroup("jia", mFriendGroupItems);
        mFriendGroups.add(friendGroup);
        listView.setAdapter(new FriendViewAdapter(getContext(), mFriendGroups));
    }
}
