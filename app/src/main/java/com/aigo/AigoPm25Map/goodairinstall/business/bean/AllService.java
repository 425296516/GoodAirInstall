package com.aigo.AigoPm25Map.goodairinstall.business.bean;

import java.util.List;

/**
 * Created by zhangcirui on 16/9/7.
 */
public class AllService {


    /**
     * result : true
     */

    private ResultBean result;
    /**
     * serviceId : 1
     * serviceName : 提供WiFi
     */

    private List<ServiceListBean> serviceList;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<ServiceListBean> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<ServiceListBean> serviceList) {
        this.serviceList = serviceList;
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

    public static class ServiceListBean {
        private String serviceId;
        private String serviceName;

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }
    }
}
