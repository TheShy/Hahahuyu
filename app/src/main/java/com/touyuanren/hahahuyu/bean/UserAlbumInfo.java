package com.touyuanren.hahahuyu.bean;

import java.io.Serializable;

/**
 * Created by Jing on 2016/10/25.
 */
public class UserAlbumInfo implements Serializable{
    /**
     * album_id : 45
     * album_desc :
     * update_time : 2016-08-01 18:00
     * count : 3
     * create_time : 2016-08-01 18:00
     * pic : images/hd/201608/1470016827421739871.jpg
     * type : 1
     * album_name : 振亚裸奔
     */
    private String album_id;
    private String album_desc;
    private String update_time;
    private String count;
    private String create_time;
    private String pic;
    private String type;
    private String album_name;

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getCount() {
        return count;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public String getAlbum_name() {
        return album_name;
    }
}
