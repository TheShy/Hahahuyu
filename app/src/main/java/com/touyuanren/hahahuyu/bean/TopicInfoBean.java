package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/11/7.
 */
public class TopicInfoBean extends BaseBean {

    /**
     * status : 1
     * data : {"count":"12","list":[{"nick_name":"haha","id_value":"491","content":"安安生生放的地方","comment_rank":"0","comment_id":"434","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:25","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"是方法反反复复飞","comment_rank":"0","comment_id":"433","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:14","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"我反反复复凤飞飞","comment_rank":"0","comment_id":"432","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:03","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"付费鹅鹅鹅鹅鹅鹅","comment_rank":"0","comment_id":"431","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:35:43","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"啊大大发达","comment_rank":"0","comment_id":"425","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:34:24","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"}]}
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
        /**
         * count : 12
         * list : [{"nick_name":"haha","id_value":"491","content":"安安生生放的地方","comment_rank":"0","comment_id":"434","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:25","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"是方法反反复复飞","comment_rank":"0","comment_id":"433","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:14","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"我反反复复凤飞飞","comment_rank":"0","comment_id":"432","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:36:03","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"付费鹅鹅鹅鹅鹅鹅","comment_rank":"0","comment_id":"431","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:35:43","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"},{"nick_name":"haha","id_value":"491","content":"啊大大发达","comment_rank":"0","comment_id":"425","ip_address":"111.198.56.168","status":"1","email":"casseit@1.com","comment_type":"5","add_time":"2016-11-07 06:34:24","photo":"images/hd/161006/1475707953733721091.jpg","parent_id":"0"}]
         */
        private String count;
        private List<TopicInfo> list;

        public void setCount(String count) {
            this.count = count;
        }

        public void setList(List<TopicInfo> list) {
            this.list = list;
        }

        public String getCount() {
            return count;
        }

        public List<TopicInfo> getList() {
            return list;
        }
    }
}
