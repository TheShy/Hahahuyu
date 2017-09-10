package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/9/30.
 * 日志信息
 */
public class LogInfoBean extends BaseBean {

    /**
     * data : {"count":"3","list":[{"id":"159","content":"'hi可口可乐了哈哈:-)","title":"还好还好","user_id":"644","add_time":"2016-09-29 01:32"},{"id":"157","content":"王还好还好号哈哈哈","title":"王文斌","user_id":"644","add_time":"2016-09-29 01:22"},{"id":"145","content":"你好","title":"2222","user_id":"533","add_time":"2016-09-23 00:37"}]}
     */
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }


    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        private List<LogInfo> list;

        public void setList(List<LogInfo> list) {
            this.list = list;
        }

        public List<LogInfo> getList() {
            return list;
        }
    }
}
