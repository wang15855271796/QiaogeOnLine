package com.puyue.www.qiaoge.model;

import java.io.Serializable;

public class ToFriendModel {

    public int code;
    public String message;
    public boolean error;
    public boolean success;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * supplierName : 测试供应商-平台定价11
         * prodNum : 0
         * saleVolume : 0
         */
        private String orderId;
        private long systemTime;
        private long cancelTime;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public long getSystemTime() {
            return systemTime;
        }

        public void setSystemTime(long systemTime) {
            this.systemTime = systemTime;
        }

        public long getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(long cancelTime) {
            this.cancelTime = cancelTime;
        }
    }

}
