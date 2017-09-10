package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/11/2.
 */
public class UserPicInfoBean extends BaseBean {


    /**
     * status : 1
     * data : {"list":[{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1825","path":"images/hd/app/album/161101/1477973821948552310.jpg"},{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1823","path":"images/hd/app/album/161101/1477973820539812240.jpg"},{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1824","path":"images/hd/app/album/161101/1477973820205228408.jpg"}]}
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
         * list : [{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1825","path":"images/hd/app/album/161101/1477973821948552310.jpg"},{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1823","path":"images/hd/app/album/161101/1477973820539812240.jpg"},{"upload_time":"2016-11-01 20:17","title":"添加照片描述","picid":"1824","path":"images/hd/app/album/161101/1477973820205228408.jpg"}]
         */
        private List<UserPicInfo> list;

        public void setList(List<UserPicInfo> list) {
            this.list = list;
        }

        public List<UserPicInfo> getList() {
            return list;
        }
    }
}
