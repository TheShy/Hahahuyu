package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/11/2.
 * 相册照片信息
 */
public class UserPicInfo {


    /**
     * upload_time : 2016-11-01 20:17
     * title : 添加照片描述
     * picid : 1825
     * path : images/hd/app/album/161101/1477973821948552310.jpg
     */
    private String upload_time;
    private String title;
    private String picid;
    private String path;

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicid(String picid) {
        this.picid = picid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public String getTitle() {
        return title;
    }

    public String getPicid() {
        return picid;
    }

    public String getPath() {
        return path;
    }
}
