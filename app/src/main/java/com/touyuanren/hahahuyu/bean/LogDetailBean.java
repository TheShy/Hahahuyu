package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2017/2/21.
 */

public class LogDetailBean extends BaseBean {

    /**
     * msg : 查看成功
     * data : {"blog_list":{"nick_name":"haha","img_list":[{"img_path":"images/hd/app/album/161101/1477942530898987732.jpg"},{"img_path":"images/hd/app/album/161101/1477942530688716436.jpg"},{"img_path":"images/hd/app/album/161101/1477942531410468484.jpg"}],"id":"208","add_time":"2016-11-01 14:34","content":"aaaa"}}
     * status : 1
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
         * blog_list : {"nick_name":"haha","img_list":[{"img_path":"images/hd/app/album/161101/1477942530898987732.jpg"},{"img_path":"images/hd/app/album/161101/1477942530688716436.jpg"},{"img_path":"images/hd/app/album/161101/1477942531410468484.jpg"}],"id":"208","add_time":"2016-11-01 14:34","content":"aaaa"}
         */
        private LogDetail blog_list;

        public void setBlog_list(LogDetail blog_list) {
            this.blog_list = blog_list;
        }

        public LogDetail getBlog_list() {
            return blog_list;
        }
    }
}
