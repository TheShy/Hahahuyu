package com.touyuanren.hahahuyu.bean;

/**
 * Created by Administrator on 2017/4/14 0014.
 */
public class GiftBean {

    /**
     * status : 1
     * data : {"guanzhu":"11","fensi":"3","liwu":"2"}
     * msg : 获取人数，粉丝，礼物数量成功
     */

    private String status;
    private DataBean data;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * guanzhu : 11
         * fensi : 3
         * liwu : 2
         */

        private String guanzhu;
        private String fensi;
        private String liwu;

        public String getGuanzhu() {
            return guanzhu;
        }

        public void setGuanzhu(String guanzhu) {
            this.guanzhu = guanzhu;
        }

        public String getFensi() {
            return fensi;
        }

        public void setFensi(String fensi) {
            this.fensi = fensi;
        }

        public String getLiwu() {
            return liwu;
        }

        public void setLiwu(String liwu) {
            this.liwu = liwu;
        }
    }
}
