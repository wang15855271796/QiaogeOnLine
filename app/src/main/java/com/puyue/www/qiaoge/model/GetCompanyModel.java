package com.puyue.www.qiaoge.model;

public class GetCompanyModel {

    /**
     * code : 1
     * message : 成功
     * data : {"wad":1,"companyName":"代仓代配简称"}
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
         * wad : 1
         * companyName : 代仓代配简称
         */

        private int wad;
        private String companyName;

        public int getWad() {
            return wad;
        }

        public void setWad(int wad) {
            this.wad = wad;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
