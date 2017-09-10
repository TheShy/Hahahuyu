package com.touyuanren.hahahuyu.bean;

import java.util.List;

/**
 * Created by Jing on 2016/10/14.
 */
public class FenLeiInfoBean extends BaseBean {
    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public class DataEntity {
        private List<FenLeiInfo> feiyong;
        private List<FenLeiInfo> xingshi;
        private List<FenLeiInfo> fenlei;
        private List<FenLeiInfo> dizhi;
        private List<FenLeiInfo> shijian;
        private List<FenLeiInfo> zhuangtai;
        private List<FenLeiInfo> paixu;

        public void setFeiyong(List<FenLeiInfo> feiyong) {
            this.feiyong = feiyong;
        }

        public void setXingshi(List<FenLeiInfo> xingshi) {
            this.xingshi = xingshi;
        }

        public void setFenlei(List<FenLeiInfo> fenlei) {
            this.fenlei = fenlei;
        }

        public void setDizhi(List<FenLeiInfo> dizhi) {
            this.dizhi = dizhi;
        }

        public void setShijian(List<FenLeiInfo> shijian) {
            this.shijian = shijian;
        }

        public void setZhuangtai(List<FenLeiInfo> zhuangtai) {
            this.zhuangtai = zhuangtai;
        }

        public void setPaixu(List<FenLeiInfo> paixu) {
            this.paixu = paixu;
        }

        public List<FenLeiInfo> getFeiyong() {
            return feiyong;
        }

        public List<FenLeiInfo> getXingshi() {
            return xingshi;
        }

        public List<FenLeiInfo> getFenlei() {
            return fenlei;
        }

        public List<FenLeiInfo> getDizhi() {
            return dizhi;
        }

        public List<FenLeiInfo> getShijian() {
            return shijian;
        }

        public List<FenLeiInfo> getZhuangtai() {
            return zhuangtai;
        }

        public List<FenLeiInfo> getPaixu() {
            return paixu;
        }
    }
}
