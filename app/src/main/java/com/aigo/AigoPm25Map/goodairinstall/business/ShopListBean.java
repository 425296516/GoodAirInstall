package com.aigo.AigoPm25Map.goodairinstall.business;

import java.util.List;

/**
 * Created by zhangcirui on 16/9/1.
 */
public class ShopListBean {

    public List<ShopInfoBean> shopsList;

    public List<ShopInfoBean> getShopsList() {
        return shopsList;
    }

    public void setShopsList(List<ShopInfoBean> shopsList) {
        this.shopsList = shopsList;
    }

  /*  public static class ShopBean {
        private String shopId;
        private String name;
        private String address;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }*/

    /*public static class ResultBean {
        private boolean result;
        private String reason;

        public String getReason(){
            return reason;
        }

        public void setReason(String reason){
            this.reason = reason;
        }

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }*/
}
