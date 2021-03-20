package com.puyue.www.qiaoge.model;

/**
 * Created by ${王涛} on 2020/11/20
 */
public class AddressMessageModel {

    /**
     * code : -1
     * message : 请输入有效的手机号码
     * data : null
     * error : true
     * success : false
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
         * subId : 23
         * name : wjh
         * phone : 18123564251
         * inPoint : 1
         * inBalance : 1
         * inGift : 1
         */

        private String isTrue;
        private int times;

        public String getIsTrue() {
            return isTrue;
        }

        public void setIsTrue(String isTrue) {
            this.isTrue = isTrue;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }

}
