package com.puyue.www.qiaoge.model;

public class HasConnectModel {
    /**
     * code : 1
     * message : 成功
     * data : {"result":false,"message":null,"otherMessage":"","vo":null,"deliverModel":0,"connectHllOrder":0,"hllOrderId":null,"orderId":null}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean error;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * result : false
         * message : null
         * otherMessage :
         * vo : null
         * deliverModel : 0
         * connectHllOrder : 0
         * hllOrderId : null
         * orderId : null
         */

        private boolean result;
        private Object message;
        private String otherMessage;
        private Object vo;
        private int deliverModel;
        private int connectHllOrder;
        private Object hllOrderId;
        private Object orderId;

        public boolean isResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public Object getMessage() {
            return message;
        }

        public void setMessage(Object message) {
            this.message = message;
        }

        public String getOtherMessage() {
            return otherMessage;
        }

        public void setOtherMessage(String otherMessage) {
            this.otherMessage = otherMessage;
        }

        public Object getVo() {
            return vo;
        }

        public void setVo(Object vo) {
            this.vo = vo;
        }

        public int getDeliverModel() {
            return deliverModel;
        }

        public void setDeliverModel(int deliverModel) {
            this.deliverModel = deliverModel;
        }

        public int getConnectHllOrder() {
            return connectHllOrder;
        }

        public void setConnectHllOrder(int connectHllOrder) {
            this.connectHllOrder = connectHllOrder;
        }

        public Object getHllOrderId() {
            return hllOrderId;
        }

        public void setHllOrderId(Object hllOrderId) {
            this.hllOrderId = hllOrderId;
        }

        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }
    }
}
