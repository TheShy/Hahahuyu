package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/18.
 */
public class ZuoPinInfoBean extends BaseBean {
    /**
     * status : 1
     * data : {"list":[{"upload_time":"2016-09-14","id":"40","createtime":"2016-09-14","xs_id":"42","title":"","hd_id":"432","tick_num":"13","is_cover":"0","status":"0","path":"images/hd/201609/2016091416562764609.jpg","line":"-1","number":"10001","zbf_id":"735","pic_id":"1099"},{"upload_time":"2016-09-14","id":"41","createtime":"2016-09-14","xs_id":"42","title":"","hd_id":"432","tick_num":"12","is_cover":"0","status":"0","path":"images/hd/201609/2016091416562797723.jpg","line":"-1","number":"10002","zbf_id":"735","pic_id":"1100"}]}
     * msg : 数据获取成功
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
         * list : [{"upload_time":"2016-09-14","id":"40","createtime":"2016-09-14","xs_id":"42","title":"","hd_id":"432","tick_num":"13","is_cover":"0","status":"0","path":"images/hd/201609/2016091416562764609.jpg","line":"-1","number":"10001","zbf_id":"735","pic_id":"1099"},{"upload_time":"2016-09-14","id":"41","createtime":"2016-09-14","xs_id":"42","title":"","hd_id":"432","tick_num":"12","is_cover":"0","status":"0","path":"images/hd/201609/2016091416562797723.jpg","line":"-1","number":"10002","zbf_id":"735","pic_id":"1100"}]
         */
        private List<ZuoPinInfo> list;

        public void setList(List<ZuoPinInfo> list) {
            this.list = list;
        }
        public List<ZuoPinInfo> getList() {
            return list;
        }
    }
}
