package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/27.
 */
public class DongTaiInfoBean extends  BaseBean {

    /**
     * status : 1
     * data : {"list":[{"nick_name":"杰哥","id":"171","content":"呵呵哈哈哈哈哈哈哈哈哈哈;","user_id":"546","add_time":"2016-10-27 10:55:10","photo":""}]}
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
        private List<DongTaiInfo> list;

        public void setList(List<DongTaiInfo> list) {
            this.list = list;
        }
        public List<DongTaiInfo> getList() {
            return list;
        }
    }
}
