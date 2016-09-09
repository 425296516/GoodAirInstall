package com.aigo.AigoPm25Map.goodairinstall.business.bean;

import java.util.List;

/**
 * Created by zhangcirui on 16/9/2.
 */
public class KT03StatusList {


    /**
     * result : true
     */

    private ResultBean result;
    /**
     * bind_id : 26506bdb-8efa-4c33-b765-cb6ff7f1b87e
     * ip : 192.168.10.166
     * sn : 0010786076
     * kt03Name : 我的空探狗1-卧室
     * version : 110
     * wifissid : OpenWrt_2.4G_PM
     * status : false
     * isAdmin : true
     * isBind : true
     */

    private List<Kt03StatusListBean> kt03StatusList;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<Kt03StatusListBean> getKt03StatusList() {
        return kt03StatusList;
    }

    public void setKt03StatusList(List<Kt03StatusListBean> kt03StatusList) {
        this.kt03StatusList = kt03StatusList;
    }

    public static class ResultBean {
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }

    public static class Kt03StatusListBean {
        private String bind_id;
        private String ip;
        private String sn;
        private String kt03Name;
        private String version;
        private String wifissid;
        private boolean status;
        private boolean isAdmin;
        private boolean isBind;

        public String getBind_id() {
            return bind_id;
        }

        public void setBind_id(String bind_id) {
            this.bind_id = bind_id;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getKt03Name() {
            return kt03Name;
        }

        public void setKt03Name(String kt03Name) {
            this.kt03Name = kt03Name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getWifissid() {
            return wifissid;
        }

        public void setWifissid(String wifissid) {
            this.wifissid = wifissid;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }

        public boolean isIsBind() {
            return isBind;
        }

        public void setIsBind(boolean isBind) {
            this.isBind = isBind;
        }
    }
}
