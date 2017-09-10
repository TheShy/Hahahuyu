package com.touyuanren.hahahuyu.audio.bean;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/6/27/027.
 */

public class AudioDetails implements Serializable{
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

    private String url;

    private List<Comment> comment ;

    public AudioDetails(String id, String user_id, String cat_id, String title, String description, String content, String price, String add_time, String browse, String is_show, String name, String url, List<Comment> comment) {
        this.id = id;
        this.user_id = user_id;
        this.cat_id = cat_id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.price = price;
        this.add_time = add_time;
        this.browse = browse;
        this.is_show = is_show;
        this.name = name;
        this.url = url;
        this.comment = comment;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }
}
