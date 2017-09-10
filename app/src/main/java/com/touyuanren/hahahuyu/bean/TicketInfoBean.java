package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/9/8.
 */
public class TicketInfoBean extends BaseBean {

    /**
     * status : 1
     * data : {"list":[{"group_name":null,"id":"275","hd_name":"GIEC2016第二届全球互联网经济大会","hd_id":"277","price":"0","shuoming":"积攒30赠门票 [报名此票种需要经过主办人审核]  - 说明：报名后将此活动转发朋友圈集30个赞并截图，发送截图至liuqian@dianshangjie.com，或添加最后一页的微信进行验证。验证时注明报名填写的姓名。现场报道时需出示名片或其他有效证件，信息需与报名信息一致。","ticket_name":"免费票","status":"0","ticket_total":"10000","type":"2","is_apply":"1","is_free":"1"},{"group_name":null,"id":"276","hd_name":"GIEC2016第二届全球互联网经济大会","hd_id":"277","price":"1980","shuoming":"含大会全套会议资料，大会主会场A区入场券，大会期间两日自助午餐。","ticket_name":"vip门票","status":"0","ticket_total":"5000","type":"2","is_apply":"0","is_free":"1"}]}
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
         * list : [{"group_name":null,"id":"275","hd_name":"GIEC2016第二届全球互联网经济大会","hd_id":"277","price":"0","shuoming":"积攒30赠门票 [报名此票种需要经过主办人审核]  - 说明：报名后将此活动转发朋友圈集30个赞并截图，发送截图至liuqian@dianshangjie.com，或添加最后一页的微信进行验证。验证时注明报名填写的姓名。现场报道时需出示名片或其他有效证件，信息需与报名信息一致。","ticket_name":"免费票","status":"0","ticket_total":"10000","type":"2","is_apply":"1","is_free":"1"},{"group_name":null,"id":"276","hd_name":"GIEC2016第二届全球互联网经济大会","hd_id":"277","price":"1980","shuoming":"含大会全套会议资料，大会主会场A区入场券，大会期间两日自助午餐。","ticket_name":"vip门票","status":"0","ticket_total":"5000","type":"2","is_apply":"0","is_free":"1"}]
         */
        private List<TicketInfo> list;
        public void setList(List<TicketInfo> list) {
            this.list = list;
        }
        public List<TicketInfo> getList() {
            return list;
        }
    }
}
