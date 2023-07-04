package com.puyue.www.qiaoge.model;

/**
 * Created by ${王涛} on 2021/4/24
 */
public class SurpliModel {


    /**
     * code : 1
     * message : 成功
     * data : {"supplierName":"测试供应商-平台定价11","prodNum":0,"saleVolume":0}
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
         * supplierName : 测试供应商-平台定价11
         * prodNum : 0
         * saleVolume : 0
         */
        String businessStatus;
        private String supplierName;
        private int prodNum;
        private int saleVolume;

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public int getProdNum() {
            return prodNum;
        }

        public void setProdNum(int prodNum) {
            this.prodNum = prodNum;
        }

        public int getSaleVolume() {
            return saleVolume;
        }

        public void setSaleVolume(int saleVolume) {
            this.saleVolume = saleVolume;
        }
    }
}
