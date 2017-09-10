package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/3/11.
 * 存储选手的展示信息，照片，名字，
 */
public class PlayerMessage {
    private String  img;
    private String title;

    public PlayerMessage() {

    }

    public PlayerMessage(String img, String title) {
        this.img = img;
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
