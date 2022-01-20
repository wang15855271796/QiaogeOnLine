package com.puyue.www.qiaoge.model;

public class IsAuthModel {


    /**
     * code : 1
     * message : 成功
     * data : {"authorize":true,"authUrl":"https://open.huolala.cn/#/oauth/authorize?isSandbox=false&response_type=code&client_id=FuB0B8t9PFtZXnfo1RCjbFXB0lNfbYqR&redirect_uri=https%3A%2F%2Fshaokao.qoger.com%2Fapph5%2Fhtml%2Fhll_auth_back.html","authPhone":null}
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
         * authorize : true
         * authUrl : https://open.huolala.cn/#/oauth/authorize?isSandbox=false&response_type=code&client_id=FuB0B8t9PFtZXnfo1RCjbFXB0lNfbYqR&redirect_uri=https%3A%2F%2Fshaokao.qoger.com%2Fapph5%2Fhtml%2Fhll_auth_back.html
         * authPhone : null
         */

        private boolean authorize;
        private String authUrl;
        private String authPhone;

        public boolean isAuthorize() {
            return authorize;
        }

        public void setAuthorize(boolean authorize) {
            this.authorize = authorize;
        }

        public String getAuthUrl() {
            return authUrl;
        }

        public void setAuthUrl(String authUrl) {
            this.authUrl = authUrl;
        }

        public String getAuthPhone() {
            return authPhone;
        }

        public void setAuthPhone(String authPhone) {
            this.authPhone = authPhone;
        }
    }
}
