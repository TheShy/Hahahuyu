package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/25.
 */
public class UserAlbumInfoBean extends  BaseBean{

    /**
     * status : 1
     * data : {"list":[{"album_id":"45","album_desc":"","update_time":"2016-08-01 18:00","count":"3","create_time":"2016-08-01 18:00","pic":"images/hd/201608/1470016827421739871.jpg","type":"1","album_name":"振亚裸奔"}]}
     * msg : 请求成功
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }
    public DataEntity getData() {
        return data;
    }
    public class DataEntity {
        private List<UserAlbumInfo> list;

        public void setList(List<UserAlbumInfo> list) {
            this.list = list;
        }

        public List<UserAlbumInfo> getList() {
            return list;
        }
    }
}
