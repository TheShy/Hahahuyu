package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/9/30.
 * 日志列表item信息
 */
public class LogInfo {

    /**
     * nick_name : 亚
     * id : 217
     * content : 红枣补血
     * title : 红枣
     * img_list : [{"img_path":"images/hd/app/album/161110/1478743185265254193.jpg"}]
     * user_id : 533
     * is_delete : 0
     * add_time : 2016-11-10 17:59
     * type : 1
     */
    private String nick_name;
    private String id;
    private String content;
    private String title;
    private List<Img_listEntity> img_list;
    private String user_id;
    private String is_delete;
    private String add_time;
    private String type;

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImg_list(List<Img_listEntity> img_list) {
        this.img_list = img_list;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTitle() {
        return title;
    }

    public List<Img_listEntity> getImg_list() {
        return img_list;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public String getAdd_time() {
        return add_time;
    }

    public String getType() {
        return type;
    }
}
