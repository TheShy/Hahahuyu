package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class FDZimeitiBean {

    /**
     * status : 1
     * data : [{"id":"746","title":"详解 Cookie 纪要","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=746","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1489567472","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"3","template":"article","img_path":"images/hd/170315/1489538672629423950.jpg","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"742","title":"为什么谷歌会从零开始构建一个全新的操作系统？","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=742","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1489550727","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"14","template":"article","img_path":"images/hd/170315/1489521927156075924.jpg","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"741","title":"想杀死安卓的CM团队 为何自己被反杀了？","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=741","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1489550542","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"1","template":"article","img_path":"","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"698","title":"用DIV+javascript实现首尾相连循环滚动效果","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=698","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1488540341","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"3","template":"article","img_path":"images/hd/170303/1488511541664545767.jpg","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"697","title":"PHP之选项卡","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=697","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1488540288","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"4","template":"article","img_path":"images/hd/170303/1488511488478937329.gif","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"692","title":"测试","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=692","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1488526453","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"48","template":"article","img_path":"images/hd/170303/1488497653462335920.jpg","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"628","title":"efwaegfwsr","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=628","user_id":"533","user_name":"wzy","is_check":"2","is_open":"1","add_time":"1484904557","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"1","template":"article","img_path":"","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"},{"id":"313","title":"习近平打破关于反腐的五种论调","cat_id":"1","content":"http://www.haha.com/hd/h5_activite_detail.php?id=313","user_id":"533","user_name":"wzy","is_check":"2","is_open":"0","add_time":"1477396312","admin_id":"0","admin_name":null,"keywords":"","author_email":"","article_type":"0","file_url":"","open_type":"0","link":"","description":null,"browse":"24","template":"article","img_path":"images/hd/161025/1477367512229686258.jpg","nick_name":"彬彬","add_sstime":"1970-01-01 08:00:00"}]
     * msg : 获取自媒体成功
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
         * id : 746
         * title : 详解 Cookie 纪要
         * cat_id : 1
         * content : http://www.haha.com/hd/h5_activite_detail.php?id=746
         * user_id : 533
         * user_name : wzy
         * is_check : 2
         * is_open : 0
         * add_time : 1489567472
         * admin_id : 0
         * admin_name : null
         * keywords :
         * author_email :
         * article_type : 0
         * file_url :
         * open_type : 0
         * link :
         * description : null
         * browse : 3
         * template : article
         * img_path : images/hd/170315/1489538672629423950.jpg
         * nick_name : 彬彬
         * add_sstime : 1970-01-01 08:00:00
         */

        private String id;
        private String title;
        private String cat_id;
        private String content;
        private String user_id;
        private String user_name;
        private String is_check;
        private String is_open;
        private String add_time;
        private String admin_id;
        private Object admin_name;
        private String keywords;
        private String author_email;
        private String article_type;
        private String file_url;
        private String open_type;
        private String link;
        private Object description;
        private String browse;
        private String template;
        private String img_path;
        private String nick_name;
        private String add_sstime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCat_id() {
            return cat_id;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getIs_check() {
            return is_check;
        }

        public void setIs_check(String is_check) {
            this.is_check = is_check;
        }

        public String getIs_open() {
            return is_open;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public Object getAdmin_name() {
            return admin_name;
        }

        public void setAdmin_name(Object admin_name) {
            this.admin_name = admin_name;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getAuthor_email() {
            return author_email;
        }

        public void setAuthor_email(String author_email) {
            this.author_email = author_email;
        }

        public String getArticle_type() {
            return article_type;
        }

        public void setArticle_type(String article_type) {
            this.article_type = article_type;
        }

        public String getFile_url() {
            return file_url;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public String getOpen_type() {
            return open_type;
        }

        public void setOpen_type(String open_type) {
            this.open_type = open_type;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getBrowse() {
            return browse;
        }

        public void setBrowse(String browse) {
            this.browse = browse;
        }

        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAdd_sstime() {
            return add_sstime;
        }

        public void setAdd_sstime(String add_sstime) {
            this.add_sstime = add_sstime;
        }
    }
}
