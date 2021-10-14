package com.puyue.www.qiaoge.model.cart;

/**
 * Created by ${王涛} on 2021/10/12
 */
public class CartAddModel {
    /**
     * code : -1
     * message : 商品库存不足
     * data : null
     * error : true
     * success : false
     */

    private int code;
    private String message;
    public DataBean data;
    private boolean error;
    private boolean success;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int addFlag;
        private int num;
        private String message;

        public int getAddFlag() {
            return addFlag;
        }

        public void setAddFlag(int addFlag) {
            this.addFlag = addFlag;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

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

}
