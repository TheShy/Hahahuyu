package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31/031.
 */

public class BaomListInfo  {

    /**
     * status : 1
     * data : [{"user_id":"533","nick_name":"彬彬111","user_name":"wzy","photo":"images/hd/app/photo/170731/1501437700234296016.jpg","tick_num":"","number":""}]
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
         * user_id : 533
         * nick_name : 彬彬111
         * user_name : wzy
         * photo : images/hd/app/photo/170731/1501437700234296016.jpg
         * tick_num :
         * number :
         */

        private String user_id;
        private String nick_name;
        private String user_name;
        private String photo;
        private String tick_num;
        private String number;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getTick_num() {
            return tick_num;
        }

        public void setTick_num(String tick_num) {
            this.tick_num = tick_num;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
