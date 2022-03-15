package com.bigfun.sdk.model;


import androidx.annotation.Keep;


public class ChannelConfigBean {

    /**
     * code : 0
     * msg : success
     * data : {"operation":"","serviceurl":"http://xxx.com/service","shareurl":"http://xxx.com/shareurl"}
     */

    private String code;
    private String msg;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * operation :
         * serviceurl : http://xxx.com/service
         * shareurl : http://xxx.com/shareurl
         */

        private String operation;
        private String serviceurl;
        private String shareurl;

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public String getServiceurl() {
            return serviceurl;
        }

        public void setServiceurl(String serviceurl) {
            this.serviceurl = serviceurl;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }
    }
}
