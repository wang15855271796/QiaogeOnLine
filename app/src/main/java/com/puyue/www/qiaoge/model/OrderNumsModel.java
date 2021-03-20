package com.puyue.www.qiaoge.model;

/**
 * Created by ${王涛} on 2020/11/18
 */
public class OrderNumsModel {

    /**
     * code : 1
     * message : 成功
     * data : {"sendOrderNum":27,"selfOrderNum":0}
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
         * sendOrderNum : 27
         * selfOrderNum : 0
         */

        private int sendOrderNum;
        private int selfOrderNum;

        public int getSendOrderNum() {
            return sendOrderNum;
        }

        public void setSendOrderNum(int sendOrderNum) {
            this.sendOrderNum = sendOrderNum;
        }

        public int getSelfOrderNum() {
            return selfOrderNum;
        }

        public void setSelfOrderNum(int selfOrderNum) {
            this.selfOrderNum = selfOrderNum;
        }
    }
}
