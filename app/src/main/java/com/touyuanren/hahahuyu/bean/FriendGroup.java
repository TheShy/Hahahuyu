package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2017/2/21.
 */

public class FriendGroup {
    //分组名称
    private String mGroupName;
    //分组项目
    private List<FriendGroupItem> mFriendGroupItems;

    public FriendGroup(String GroupName, List<FriendGroupItem> friendGroupItems)
    {
        this.mGroupName=GroupName;
        this.mFriendGroupItems = friendGroupItems;
    }

    public String getGroupName() {
        return mGroupName;
    }

    public void setGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
    }

    public List<FriendGroupItem> getGroupItems() {
        return mFriendGroupItems;
    }

    public void setGroupItems(List<FriendGroupItem> mItems) {
        this.mFriendGroupItems = mItems;
    }
}
