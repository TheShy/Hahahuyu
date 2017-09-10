package com.touyuanren.hahahuyu.audio.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/27/027.
 */

public class AudioInfo implements Serializable{

    /**
     * id : 1
     * user_id : 522
     * cat_id : 1
     * title : CC
     * description : CC
     * content : 22222
     * price : 0.01
     * add_time : 54321
     * browse : 0
     * is_show : 1
     * name : 音频
     * sort_order : 50
     * is_use : 1
     */

    private String id;
    private String user_id;
    private String cat_id;
    private String title;
    private String description;
    private String content;
    private String price;
    private String add_time;
    private String browse;
    private String is_show;
    private String name;
    private String sort_order;
    private String is_use;
    private String url;

    public String getUrl() {
        return url;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }

    public String getIs_use() {
        return is_use;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setIs_use(String is_use) {
        this.is_use = is_use;
    }

    @Override
    public String toString() {
        return "AudioInfo{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", cat_id='" + cat_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", price='" + price + '\'' +
                ", add_time='" + add_time + '\'' +
                ", browse='" + browse + '\'' +
                ", is_show='" + is_show + '\'' +
                ", name='" + name + '\'' +
                ", sort_order='" + sort_order + '\'' +
                ", is_use='" + is_use + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
