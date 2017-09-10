package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2017/2/21.
 * 日志详情
 */

public class LogDetail {


    /**
     * nick_name : haha
     * img_list : [{"img_path":"images/hd/app/album/161101/1477942530898987732.jpg"},{"img_path":"images/hd/app/album/161101/1477942530688716436.jpg"},{"img_path":"images/hd/app/album/161101/1477942531410468484.jpg"}]
     * id : 208
     * add_time : 2016-11-01 14:34
     * content : aaaa
     */
    private String nick_name;
    private List<Img_listEntity> img_list;
    private String id;
    private String add_time;
    private String content;
    private String title;
    private String user_name;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setImg_list(List<Img_listEntity> img_list) {
        this.img_list = img_list;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNick_name() {
        return nick_name;
    }

    public List<Img_listEntity> getImg_list() {
        return img_list;
    }

    public String getId() {
        return id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public String getContent() {
        return content;
    }


}
