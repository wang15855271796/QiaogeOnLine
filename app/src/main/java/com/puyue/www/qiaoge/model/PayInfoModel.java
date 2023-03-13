package com.puyue.www.qiaoge.model;

public class PayInfoModel {

    private Integer code;
    private String message;
    private DataBean data;
    private Object extData;
    private Boolean error;
    private Boolean success;

    @Override
    public String toString() {
        return "PayInfoModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extData=" + extData +
                ", error=" + error +
                ", success=" + success +
                '}';
    }

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
        private String outTradeNo;
        private String title;
        private String payToken;
        private Integer payType;

        @Override
        public String toString() {
            return "DataBean{" +
                    "outTradeNo='" + outTradeNo + '\'' +
                    ", title='" + title + '\'' +
                    ", payToken='" + payToken + '\'' +
                    ", payType=" + payType +
                    '}';
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPayToken() {
            return payToken;
        }

        public void setPayToken(String payToken) {
            this.payToken = payToken;
        }

        public Integer getPayType() {
            return payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }
    }
}
