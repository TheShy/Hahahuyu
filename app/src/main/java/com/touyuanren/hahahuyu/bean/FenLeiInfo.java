package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/10/14.
 * 活动分类bean类
 */
public class FenLeiInfo {

    /**
     * id : 1
     * name : 收费
     */
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FenLeiInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
