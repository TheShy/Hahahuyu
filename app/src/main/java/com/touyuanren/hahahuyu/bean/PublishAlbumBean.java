package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/10/25.
 */
public class PublishAlbumBean extends  BaseBean {

    /**
     * status : 1
     * data : {"album_id":"161"}
     * msg : 新建成功
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }
    public DataEntity getData() {
        return data;
    }
    public class DataEntity {
        /**
         * album_id : 161
         */
        private String album_id;

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getAlbum_id() {
            return album_id;
        }
    }
}
