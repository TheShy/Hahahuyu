package com.touyuanren.hahahuyu.audio.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/18/018.
 */

public class VedioInfo implements Serializable{


    /**
     * id : 12
     * cat_id : 3
     * user_id : 42
     * title : 早期
     * description : 心情好
     * content : upload/mp4/VIDEO_9385320170713084000.mp4
     * price : 0.00
     * add_time : 2017-07-13 08:40:00
     * browse : 15
     * is_show : 1
     * is_delete : 0
     * name : 视频
     * url : /px/video_detail.php?id=12
     */

    private String id;
    private String cat_id;
    private String user_id;
    private String title;
    private String description;
    private String content;
    private String price;
    private String add_time;
    private String browse;
    private String is_show;
    private String is_delete;
    private String name;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
