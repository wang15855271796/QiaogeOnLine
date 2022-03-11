package com.puyue.www.qiaoge.model;

public class ModeModel {


    /**
     * code : 1
     * message : 成功
     * data : {"pickBtn":0,"hllBtn":0}
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean success;
    private boolean error;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * pickBtn : 0
         * hllBtn : 0
         */

        private int pickBtn;
        private int hllBtn;

        public int getPickBtn() {
            return pickBtn;
        }

        public void setPickBtn(int pickBtn) {
            this.pickBtn = pickBtn;
        }

        public int getHllBtn() {
            return hllBtn;
        }

        public void setHllBtn(int hllBtn) {
            this.hllBtn = hllBtn;
        }
    }
}
