package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2017/2/21.
 */

public class FriendGroupItem {
    private String Title;
    private String Content;

    public FriendGroupItem(String mTitle, String mContent)
    {
        this.Title=mTitle;
        this.Content=mContent;
    }


    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }
    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }
}
