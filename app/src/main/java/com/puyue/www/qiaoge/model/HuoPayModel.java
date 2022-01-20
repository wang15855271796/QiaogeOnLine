package com.puyue.www.qiaoge.model;

public class HuoPayModel {


    /**
     * code : 1
     * message : 成功
     * data : {"cashier_url":"https://hpay-charge-h5.huolala.cn?payToken=1000107ADDCE14227138F8B2779A6A352D58E&hlang=zh_cn","expire_at_time":1639992609,"pay_amount_fen":7930,"pay_amount":79.3}
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
         * cashier_url : https://hpay-charge-h5.huolala.cn?payToken=1000107ADDCE14227138F8B2779A6A352D58E&hlang=zh_cn
         * expire_at_time : 1639992609
         * pay_amount_fen : 7930
         * pay_amount : 79.3
         */

        private String cashier_url;
        private int expire_at_time;
        private int pay_amount_fen;
        private double pay_amount;

        public String getCashier_url() {
            return cashier_url;
        }

        public void setCashier_url(String cashier_url) {
            this.cashier_url = cashier_url;
        }

        public int getExpire_at_time() {
            return expire_at_time;
        }

        public void setExpire_at_time(int expire_at_time) {
            this.expire_at_time = expire_at_time;
        }

        public int getPay_amount_fen() {
            return pay_amount_fen;
        }

        public void setPay_amount_fen(int pay_amount_fen) {
            this.pay_amount_fen = pay_amount_fen;
        }

        public double getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(double pay_amount) {
            this.pay_amount = pay_amount;
        }
    }
}
