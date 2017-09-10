package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/9/20.
 * 资讯bean类
 */
public class NewsItemBean {
    private String content_id;
    private String title;
    private String time;
    private String source;
    private String imapath;

    public NewsItemBean() {
    }

    public String getTime() {
        return time;
    }

    public String getSource() {
        return source;
    }

    public String getImapath() {
        return imapath;
    }

    public NewsItemBean(String content_id, String title, String time, String source, String imapath) {
        this.content_id = content_id;
        this.title = title;
        this.time = time;
        this.source = source;
        this.imapath = imapath;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImapath(String imapath) {
        this.imapath = imapath;
    }
    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
