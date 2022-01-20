package com.puyue.www.qiaoge.model;

public class HuoDriverPayModel {


    /**
     * code : 1
     * message : 成功
     * data : {"order_display_id":"1473468645798588514","pay_no":"","price_fen":1400,"pay_notify_url":"","pay_time_expire":0,"cashier_url":"https://hpay-charge-h5-pre.huolala.cn?payToken=100010C3D8D043B1814B6715A7C1AAEC5601D&hlang=zh_cn","pay_amount_fen":1400,"pay_token":"100010C3D8D043B1814B6715A7C1AAEC5601D"}
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
         * order_display_id : 1473468645798588514
         * pay_no :
         * price_fen : 1400
         * pay_notify_url :
         * pay_time_expire : 0
         * cashier_url : https://hpay-charge-h5-pre.huolala.cn?payToken=100010C3D8D043B1814B6715A7C1AAEC5601D&hlang=zh_cn
         * pay_amount_fen : 1400
         * pay_token : 100010C3D8D043B1814B6715A7C1AAEC5601D
         */

        private String order_display_id;
        private String pay_no;
        private int price_fen;
        private String pay_notify_url;
        private int pay_time_expire;
        private String cashier_url;
        private int pay_amount_fen;
        private String pay_token;

        public String getOrder_display_id() {
            return order_display_id;
        }

        public void setOrder_display_id(String order_display_id) {
            this.order_display_id = order_display_id;
        }

        public String getPay_no() {
            return pay_no;
        }

        public void setPay_no(String pay_no) {
            this.pay_no = pay_no;
        }

        public int getPrice_fen() {
            return price_fen;
        }

        public void setPrice_fen(int price_fen) {
            this.price_fen = price_fen;
        }

        public String getPay_notify_url() {
            return pay_notify_url;
        }

        public void setPay_notify_url(String pay_notify_url) {
            this.pay_notify_url = pay_notify_url;
        }

        public int getPay_time_expire() {
            return pay_time_expire;
        }

        public void setPay_time_expire(int pay_time_expire) {
            this.pay_time_expire = pay_time_expire;
        }

        public String getCashier_url() {
            return cashier_url;
        }

        public void setCashier_url(String cashier_url) {
            this.cashier_url = cashier_url;
        }

        public int getPay_amount_fen() {
            return pay_amount_fen;
        }

        public void setPay_amount_fen(int pay_amount_fen) {
            this.pay_amount_fen = pay_amount_fen;
        }

        public String getPay_token() {
            return pay_token;
        }

        public void setPay_token(String pay_token) {
            this.pay_token = pay_token;
        }
    }
}
