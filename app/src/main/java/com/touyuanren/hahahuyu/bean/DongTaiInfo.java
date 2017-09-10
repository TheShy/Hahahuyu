package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/27.
 */
public class DongTaiInfo {
    /**
     * nick_name : 杰哥
     * id : 171
     * content : 呵呵哈哈哈哈哈哈哈哈哈哈;
     * user_id : 546
     * add_time : 2016-10-27 10:55:10
     * photo :
     */
    private String nick_name;
    private String id;
    private String content;
    private String user_id;
    private String add_time;
    private String photo;

    public List<Img_listEntity> getImg_list() {
        return img_list;
    }

    public void setImg_list(List<Img_listEntity> img_list) {
        this.img_list = img_list;
    }

    private List<Img_listEntity> img_list;
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getAdd_time() {
        return add_time;
    }

    public String getPhoto() {
        return photo;
    }
}
