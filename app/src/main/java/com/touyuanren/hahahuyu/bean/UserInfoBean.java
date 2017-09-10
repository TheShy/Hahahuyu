package com.touyuanren.hahahuyu.bean;

/**
 * Created by Jing on 2016/8/31.
 */
public class UserInfoBean {


    /**
     * status : 1
     * data : {"info":{"auth_status":"0","birthday":"564422400","sex":"1","auth_id":"0","rcode":"A3DGLU8","province2":"内蒙古自治区","city1":"市辖区","id_card":"","province1":"北京市","city2":"呼和浩特市","xuelii":"大专","intro":"我是一枚大帅哥","nick_name":"小石磊","real_phone":null,"auth_error_num":"1","area1":"朝阳区","real_name":"","age":"11","area2":"托克托县"}}
     * msg : 获取成功
     */
    private String status;
    private DataEntity data;
    private String msg;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public class DataEntity {
        /**
         * info : {"auth_status":"0","birthday":"564422400","sex":"1","auth_id":"0","rcode":"A3DGLU8","province2":"内蒙古自治区","city1":"市辖区","id_card":"","province1":"北京市","city2":"呼和浩特市","xuelii":"大专","intro":"我是一枚大帅哥","nick_name":"小石磊","real_phone":null,"auth_error_num":"1","area1":"朝阳区","real_name":"","age":"11","area2":"托克托县"}
         */
        private InfoEntity info;

        public void setInfo(InfoEntity info) {
            this.info = info;
        }

        public InfoEntity getInfo() {
            return info;
        }

        public class InfoEntity {
            /**
             * auth_status : 0
             * birthday : 564422400
             * sex : 1
             * auth_id : 0
             * rcode : A3DGLU8
             * province2 : 内蒙古自治区
             * city1 : 市辖区
             * id_card :
             * province1 : 北京市
             * city2 : 呼和浩特市
             * xuelii : 大专
             * intro : 我是一枚大帅哥
             * nick_name : 小石磊
             * real_phone : null
             * auth_error_num : 1
             * area1 : 朝阳区
             * real_name :
             * age : 11
             * area2 : 托克托县
             */
            private String auth_status;
            private String birthday;
            private String sex;
            private String auth_id;
            private String rcode;
            private String province2;
            private String city1;
            private String id_card;
            private String province1;
            private String city2;
            private String xuelii;
            private String intro;
            private String nick_name;
            private String real_phone;
            private String auth_error_num;
            private String area1;
            private String real_name;
            private String age;
            private String area2;
            private String mobile_phone;

            public String getMobile_phone() {
                return mobile_phone;
            }

            public void setMobile_phone(String mobile_phone) {
                this.mobile_phone = mobile_phone;
            }

            public void setAuth_status(String auth_status) {
                this.auth_status = auth_status;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setAuth_id(String auth_id) {
                this.auth_id = auth_id;
            }

            public void setRcode(String rcode) {
                this.rcode = rcode;
            }

            public void setProvince2(String province2) {
                this.province2 = province2;
            }

            public void setCity1(String city1) {
                this.city1 = city1;
            }

            public void setId_card(String id_card) {
                this.id_card = id_card;
            }

            public void setProvince1(String province1) {
                this.province1 = province1;
            }

            public void setCity2(String city2) {
                this.city2 = city2;
            }

            public void setXuelii(String xuelii) {
                this.xuelii = xuelii;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public void setReal_phone(String real_phone) {
                this.real_phone = real_phone;
            }

            public void setAuth_error_num(String auth_error_num) {
                this.auth_error_num = auth_error_num;
            }

            public void setArea1(String area1) {
                this.area1 = area1;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public void setArea2(String area2) {
                this.area2 = area2;
            }

            public String getAuth_status() {
                return auth_status;
            }

            public String getBirthday() {
                return birthday;
            }

            public String getSex() {
                return sex;
            }

            public String getAuth_id() {
                return auth_id;
            }

            public String getRcode() {
                return rcode;
            }

            public String getProvince2() {
                return province2;
            }

            public String getCity1() {
                return city1;
            }

            public String getId_card() {
                return id_card;
            }

            public String getProvince1() {
                return province1;
            }

            public String getCity2() {
                return city2;
            }

            public String getXuelii() {
                return xuelii;
            }

            public String getIntro() {
                return intro;
            }

            public String getNick_name() {
                return nick_name;
            }

            public String getReal_phone() {
                return real_phone;
            }

            public String getAuth_error_num() {
                return auth_error_num;
            }

            public String getArea1() {
                return area1;
            }

            public String getReal_name() {
                return real_name;
            }

            public String getAge() {
                return age;
            }

            public String getArea2() {
                return area2;
            }
        }
    }
}
