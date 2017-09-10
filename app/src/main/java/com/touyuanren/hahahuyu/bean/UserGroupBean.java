package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29 0029.
 */
public class UserGroupBean {


    /**
     * status : 1
     * data : {"list":[{"id":"1","name":"未分组好友","u_count":"1"},{"id":"2","name":"特别好友","u_count":"0"},{"id":"3","name":"同事","u_count":"0"},{"id":"4","name":"黑名单","u_count":"0"}]}
     * msg : 请求成功
     */

    private String status;
    private DataBean data;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 1
             * name : 未分组好友
             * u_count : 1
             */

            private String id;
            private String name;
            private String u_count;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getU_count() {
                return u_count;
            }

            public void setU_count(String u_count) {
                this.u_count = u_count;
            }
        }
    }
}
