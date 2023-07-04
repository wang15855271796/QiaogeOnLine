package com.puyue.www.qiaoge.model.cart;

public class SendInfoModel {

    private Integer code;
    private String message;
    private DataBean data;
    private Object extData;
    private Boolean error;
    private Boolean success;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private String sendTimeStr;
        private String address;
        private String sendStartTime;
        private String sendEndTime;

        public String getSendTimeStr() {
            return sendTimeStr;
        }

        public void setSendTimeStr(String sendTimeStr) {
            this.sendTimeStr = sendTimeStr;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSendStartTime() {
            return sendStartTime;
        }

        public void setSendStartTime(String sendStartTime) {
            this.sendStartTime = sendStartTime;
        }

        public String getSendEndTime() {
            return sendEndTime;
        }

        public void setSendEndTime(String sendEndTime) {
            this.sendEndTime = sendEndTime;
        }
    }
}
