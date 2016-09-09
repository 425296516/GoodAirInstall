package com.aigo.AigoPm25Map.goodairinstall.business.bean;

import java.util.List;

/**
 * Created by zhangcirui on 16/9/7.
 */
public class TypeList {


    /**
     * result : true
     */

    private ResultBean result;
    /**
     * typeId : 1
     * typeName : 健身
     */

    private List<TypeListBean> typeList;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<TypeListBean> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<TypeListBean> typeList) {
        this.typeList = typeList;
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

    public static class TypeListBean {
        private String typeId;
        private String typeName;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }
    }
}
