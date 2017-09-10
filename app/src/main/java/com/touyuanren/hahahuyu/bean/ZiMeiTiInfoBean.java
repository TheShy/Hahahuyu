package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/24.
 * 自媒体bean类
 */
public class ZiMeiTiInfoBean extends  BaseBean {

    /**
     * status : 1
     * data : {"count":"13","list":[{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"我说的","id":"312","user_name":"wzy","title":"回家房东","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-24 19:12"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"的帅哥发","id":"311","user_name":"wzy","title":"回家","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-24 19:04"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","file_url":"","cat_id":"1","content":"dfsgfd","id":"296","user_name":"wzy","title":"fdsgd ","author_email":"","user_id":"533","admin_id":"0","add_time":"2016-10-22 02:10"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"地方","id":"309","user_name":"wzy","title":"的v","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-22 01:08"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"wwww","id":"303","user_name":"wzy","title":"www","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-21 22:31"}]}
     * msg : 日志列表
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
         * count : 13
         * list : [{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"我说的","id":"312","user_name":"wzy","title":"回家房东","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-24 19:12"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"的帅哥发","id":"311","user_name":"wzy","title":"回家","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-24 19:04"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","file_url":"","cat_id":"1","content":"dfsgfd","id":"296","user_name":"wzy","title":"fdsgd ","author_email":"","user_id":"533","admin_id":"0","add_time":"2016-10-22 02:10"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"地方","id":"309","user_name":"wzy","title":"的v","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-22 01:08"},{"template":"article","is_check":"0","keywords":"","link":"","is_open":"0","article_type":"0","open_type":"0","admin_name":"","file_url":"","cat_id":"1","content":"wwww","id":"303","user_name":"wzy","title":"www","author_email":"","description":"","user_id":"533","admin_id":"0","add_time":"2016-10-21 22:31"}]
         */
        private String count;
        private List<ZiMeiTiInfo> list;

        public void setCount(String count) {
            this.count = count;
        }
        public void setList(List<ZiMeiTiInfo> list) {
            this.list = list;
        }
        public String getCount() {
            return count;
        }
        public List<ZiMeiTiInfo> getList() {
            return list;
        }
    }
}
