package com.puyue.www.qiaoge.model;

public class InfoIsPayModel {

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
        private String msg;
        private Integer payFlag;
        private String shouldPayAmt;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Integer getPayFlag() {
            return payFlag;
        }

        public void setPayFlag(Integer payFlag) {
            this.payFlag = payFlag;
        }

        public String getShouldPayAmt() {
            return shouldPayAmt;
        }

        public void setShouldPayAmt(String shouldPayAmt) {
            this.shouldPayAmt = shouldPayAmt;
        }
    }
}
