package com.aigo.AigoPm25Map.goodairinstall.business;

import java.util.List;

/**
 * Created by zhangcirui on 16/7/25.
 */
public class ResultObject {
    /**
     * result : false
     */

    private ResultBean result;
    /**
     * code : 4
     * content : deviceId:空气设备编号已存在
     */

    private List<ErrInfoBean> err_info;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<ErrInfoBean> getErr_info() {
        return err_info;
    }

    public void setErr_info(List<ErrInfoBean> err_info) {
        this.err_info = err_info;
    }

    @Override
    public String toString() {
        return "ResultObject{" +
                "result=" + result +
                ", err_info=" + err_info +
                '}';
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

    public static class ErrInfoBean {
        private int code;
        private String content;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


        @Override
        public String toString() {
            return "ErrInfoBean{" +
                    "code=" + code +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
    /**
     * result : false
     */

   /* private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private boolean result;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }
    }*/





}
