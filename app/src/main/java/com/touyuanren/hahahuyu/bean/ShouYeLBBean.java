package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/25 0025.
 */
public class ShouYeLBBean {


    /**
     * status : 1
     * data : [{"id":"2","pic_url":"/userfiles/1/images/images/lunbo/2017/01/cddbfabab37a88e79175bbce31ef41b0.jpg"},{"id":"3","pic_url":"/userfiles/1/images/images/lunbo/2017/01/5f31e9e07a4946b09b29ef651d245237.jpg"},{"id":"4","pic_url":"/userfiles/1/images/images/lunbo/2017/01/2b876d8068b0fa3d30b38c231b858e00.jpg"},{"id":"5","pic_url":"/userfiles/1/images/images/lunbo/2017/01/5c24b8006e6bdf953376df77b6c05263.jpg"},{"id":"6","pic_url":"/userfiles/1/images/images/lunbo/2017/01/5f31e9e07a4946b09b29ef651d245237.jpg"},{"id":"7","pic_url":"/userfiles/1/images/images/lunbo/2017/02/589a9f51N666b632b.jpg"},{"id":"8","pic_url":"/userfiles/1/images/images/lunbo/2017/01/01.jpg"},{"id":"9","pic_url":"/userfiles/1/images/images/lunbo/2017/01/ee0bcbd632af14341fd04f94d24363eb.jpg"}]
     * msg : 请求成功
     */

    private String status;
    private String msg;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 2
         * pic_url : /userfiles/1/images/images/lunbo/2017/01/cddbfabab37a88e79175bbce31ef41b0.jpg
         */

        private String id;
        private String pic_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPic_url() {
            return pic_url;
        }

        public void setPic_url(String pic_url) {
            this.pic_url = pic_url;
        }
    }
}
