package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19 0019.
 * 首页热门推荐
 */
public class HotBean {

    /**
     * status : 1
     * data : [{"user_id":"899","id":"510","cat_id":"15","poster":"images/hd/170415/1492215072876362809.png","title":"北天财商公开课第一期\u2014\u2014北京房地产泡泡","starttime":"2017-04-29 22:00","endtime":"2017-04-30 00:30","close_time":"2017-04-30 00:10","match_status":"0","address":"北京市通州区九棵树地铁站瑞都国际中心903A","content":"http://hahahuyu.com/hd/h5_activite_detail.php?id=510","ticket":"0","browse_num":"25","mark":"0","limit_num":"100","user_name":"15839379520","sign_num":"2","db_name":"","name":"课程","collection_num":"0"},{"user_id":"899","id":"499","cat_id":"3","poster":"images/hd/170407/1491501419072692507.png","title":"雄安机遇\u2014\u2014大视线产业分享会第三期","starttime":"2017-04-25 21:30","endtime":"2017-04-26 01:30","close_time":"2017-04-26 01:30","match_status":"0","address":"北京市通州区九棵树瑞都国际中心903A","content":"http://hahahuyu.com/hd/h5_activite_detail.php?id=499","ticket":"0","browse_num":"438","mark":"0","limit_num":"60","user_name":"15839379520","sign_num":"10","db_name":"","name":"会议","collection_num":"2"},{"user_id":"533","id":"495","cat_id":"3","poster":"images/hd/170225/1487962782476240880.png","title":"创投中国行科技金融扶贫高峰论坛暨 新三板华山论剑千人峰会","starttime":"2017-04-25 17:00","endtime":"2017-04-26 02:00","close_time":"2017-04-25 18:52","match_status":"0","address":"北京市歌华开元大酒店","content":"http://hahahuyu.com/hd/h5_activite_detail.php?id=495","ticket":"0","browse_num":"188","mark":"0","limit_num":"1000","user_name":"wzy","sign_num":"7","db_name":"","name":"会议","collection_num":"2"},{"user_id":"760","id":"385","cat_id":"4","poster":"images/hd/201609/1473209907363070647.jpg","title":"帝都人气实名单身派对-以结婚为目的！","starttime":"2016-09-11 02:30","endtime":"2017-10-01 05:30","close_time":"2017-10-01 05:30","match_status":"0","address":"   佳慧中心b座","content":"http://hahahuyu.com/hd/h5_activite_detail.php?id=385","ticket":"0","browse_num":"167","mark":"0","limit_num":"80","user_name":"beijingxiangqinhui","sign_num":"1","db_name":"","name":"聚会相亲","collection_num":"1"}]
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
         * user_id : 899
         * id : 510
         * cat_id : 15
         * poster : images/hd/170415/1492215072876362809.png
         * title : 北天财商公开课第一期——北京房地产泡泡
         * starttime : 2017-04-29 22:00
         * endtime : 2017-04-30 00:30
         * close_time : 2017-04-30 00:10
         * match_status : 0
         * address : 北京市通州区九棵树地铁站瑞都国际中心903A
         * content : http://hahahuyu.com/hd/h5_activite_detail.php?id=510
         * ticket : 0
         * browse_num : 25
         * mark : 0
         * limit_num : 100
         * user_name : 15839379520
         * sign_num : 2
         * db_name :
         * name : 课程
         * collection_num : 0
         */

        private String user_id;
        private String id;
        private String cat_id;
        private String poster;
        private String title;
        private String starttime;
        private String endtime;
        private String close_time;
        private String match_status;
        private String address;
        private String content;
        private String ticket;
        private String browse_num;
        private String mark;
        private String limit_num;
        private String user_name;
        private String sign_num;
        private String db_name;
        private String name;
        private String collection_num;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getPoster() {
            return poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getClose_time() {
            return close_time;
        }

        public void setClose_time(String close_time) {
            this.close_time = close_time;
        }

        public String getMatch_status() {
            return match_status;
        }

        public void setMatch_status(String match_status) {
            this.match_status = match_status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public String getBrowse_num() {
            return browse_num;
        }

        public void setBrowse_num(String browse_num) {
            this.browse_num = browse_num;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getLimit_num() {
            return limit_num;
        }

        public void setLimit_num(String limit_num) {
            this.limit_num = limit_num;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getSign_num() {
            return sign_num;
        }

        public void setSign_num(String sign_num) {
            this.sign_num = sign_num;
        }

        public String getDb_name() {
            return db_name;
        }

        public void setDb_name(String db_name) {
            this.db_name = db_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCollection_num() {
            return collection_num;
        }

        public void setCollection_num(String collection_num) {
            this.collection_num = collection_num;
        }
    }
}
