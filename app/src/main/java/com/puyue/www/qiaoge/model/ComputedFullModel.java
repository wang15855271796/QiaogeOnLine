package com.puyue.www.qiaoge.model;

public class ComputedFullModel {


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
        private String needBuyAmt;
        private String offerAmt;
        private String nextOfferAmt;
        private String deductAmt;
        private String tips;

        @Override
        public String toString() {
            return "DataBean{" +
                    "needBuyAmt='" + needBuyAmt + '\'' +
                    ", offerAmt='" + offerAmt + '\'' +
                    ", nextOfferAmt='" + nextOfferAmt + '\'' +
                    ", tips='" + tips + '\'' +
                    '}';
        }

        public String getDeductAmt() {
            return deductAmt;
        }

        public void setDeductAmt(String deductAmt) {
            this.deductAmt = deductAmt;
        }

        public String getNeedBuyAmt() {
            return needBuyAmt;
        }

        public void setNeedBuyAmt(String needBuyAmt) {
            this.needBuyAmt = needBuyAmt;
        }

        public String getOfferAmt() {
            return offerAmt;
        }

        public void setOfferAmt(String offerAmt) {
            this.offerAmt = offerAmt;
        }

        public String getNextOfferAmt() {
            return nextOfferAmt;
        }

        public void setNextOfferAmt(String nextOfferAmt) {
            this.nextOfferAmt = nextOfferAmt;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }
}
