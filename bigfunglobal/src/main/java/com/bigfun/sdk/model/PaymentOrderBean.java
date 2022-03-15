package com.bigfun.sdk.model;


import androidx.annotation.Keep;


public class PaymentOrderBean {

    /**
     * code : 0
     * msg : success
     * data : {"orderId":"xxxxx","commodityId":"xxx","outPayAmount":1,"outUserId":"xxxx","outOrderNo":"xxxx","jumpUrl":"xxxxx","openType":"1"}
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
         * orderId : xxxxx
         * commodityId : xxx
         * outPayAmount : 1.0
         * outUserId : xxxx
         * outOrderNo : xxxx
         * jumpUrl : xxxxx
         * openType : 1
         */

        private String orderId;
        private String commodityId;
        private double outPayAmount;
        private String outUserId;
        private String outOrderNo;
        private String jumpUrl;
        private String openType;
        private String paymentChannel;


        public String getPaymentChannel() {
            return paymentChannel;
        }

        public void setPaymentChannel(String paymentChannel) {
            this.paymentChannel = paymentChannel;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(String commodityId) {
            this.commodityId = commodityId;
        }

        public double getOutPayAmount() {
            return outPayAmount;
        }

        public void setOutPayAmount(double outPayAmount) {
            this.outPayAmount = outPayAmount;
        }

        public String getOutUserId() {
            return outUserId;
        }

        public void setOutUserId(String outUserId) {
            this.outUserId = outUserId;
        }

        public String getOutOrderNo() {
            return outOrderNo;
        }

        public void setOutOrderNo(String outOrderNo) {
            this.outOrderNo = outOrderNo;
        }

        public String getJumpUrl() {
            return jumpUrl;
        }

        public void setJumpUrl(String jumpUrl) {
            this.jumpUrl = jumpUrl;
        }

        public String getOpenType() {
            return openType;
        }

        public void setOpenType(String openType) {
            this.openType = openType;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "orderId='" + orderId + '\'' +
                    ", commodityId='" + commodityId + '\'' +
                    ", outPayAmount=" + outPayAmount +
                    ", outUserId='" + outUserId + '\'' +
                    ", outOrderNo='" + outOrderNo + '\'' +
                    ", jumpUrl='" + jumpUrl + '\'' +
                    ", openType='" + openType + '\'' +
                    ", paymentChannel='" + paymentChannel + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PaymentOrderBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
