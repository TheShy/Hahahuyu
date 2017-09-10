package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/3/3.
 * 票种信息,设置票种信息通过对话框实现
 */
public class TicketInfo {

    /**
     * id : 275
     * hd_name : GIEC2016第二届全球互联网经济大会
     * hd_id : 277
     * price : 0
     * ticket_name : 免费票
     * shuoming : 积攒30赠门票 [报名此票种需要经过主办人审核]  - 说明：报名后将此活动转发朋友圈集30个赞并截图，发送截图至liuqian@dianshangjie.com，或添加最后一页的微信进行验证。验证时注明报名填写的姓名。现场报道时需出示名片或其他有效证件，信息需与报名信息一致。
     * ticket_total : 10000
     * status : 0
     * type : 2
     * is_apply : 1
     * is_free : 1
     */
    private String id;
    private String hd_name;
    private String hd_id;
    private String price;
    private String ticket_name;
    private String shuoming;
    private String ticket_total;
    private String status;
    private String type;
    private String is_apply;
    private String is_free;

    public void setId(String id) {
        this.id = id;
    }

    public void setHd_name(String hd_name) {
        this.hd_name = hd_name;
    }

    public void setHd_id(String hd_id) {
        this.hd_id = hd_id;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTicket_name(String ticket_name) {
        this.ticket_name = ticket_name;
    }

    public void setShuoming(String shuoming) {
        this.shuoming = shuoming;
    }

    public void setTicket_total(String ticket_total) {
        this.ticket_total = ticket_total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIs_apply(String is_apply) {
        this.is_apply = is_apply;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getId() {
        return id;
    }

    public String getHd_name() {
        return hd_name;
    }

    public String getHd_id() {
        return hd_id;
    }

    public String getPrice() {
        return price;
    }

    public String getTicket_name() {
        return ticket_name;
    }

    public String getShuoming() {
        return shuoming;
    }

    public String getTicket_total() {
        return ticket_total;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getIs_apply() {
        return is_apply;
    }

    public String getIs_free() {
        return is_free;
    }
}
