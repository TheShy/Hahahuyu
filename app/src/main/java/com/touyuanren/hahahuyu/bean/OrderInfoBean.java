package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/6.
 */
public class OrderInfoBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        private List<OrderInfo> list;

        public void setList(List<OrderInfo> list) {
            this.list = list;
        }

        public List<OrderInfo> getList() {
            return list;
        }
    }

}
