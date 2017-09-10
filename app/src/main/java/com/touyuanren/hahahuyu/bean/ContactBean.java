package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/7/14.
 *  先定义一个实体类，用来保存联系人信息
 */
public class ContactBean {
    private int contactId; //id
    private String desplayName;//姓名
    private String phoneNum; // 电话号码
    private String sortKey; // 排序用的
    private Long photoId; // 图片id
    private String lookUpKey;
    private int selected = 0;
    private String formattedNumber;
    private String pinyin; // 姓名拼音

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getDesplayName() {
        return desplayName;
    }

    public void setDesplayName(String desplayName) {
        this.desplayName = desplayName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getLookUpKey() {
        return lookUpKey;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getFormattedNumber() {
        return formattedNumber;
    }

    public void setFormattedNumber(String formattedNumber) {
        this.formattedNumber = formattedNumber;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "contactId=" + contactId +
                ", desplayName='" + desplayName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", sortKey='" + sortKey + '\'' +
                ", photoId=" + photoId +
                ", lookUpKey='" + lookUpKey + '\'' +
                ", selected=" + selected +
                ", formattedNumber='" + formattedNumber + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
