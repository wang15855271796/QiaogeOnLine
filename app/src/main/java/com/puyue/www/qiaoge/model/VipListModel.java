package com.puyue.www.qiaoge.model;

import java.util.List;

public class VipListModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"channelName":"支付宝","flag":2,"jumpWx":0},{"channelName":"微信支付","flag":3,"jumpWx":1}]
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private boolean success;
    private boolean error;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * channelName : 支付宝
         * flag : 2
         * jumpWx : 0
         */

        private String channelName;
        private int flag;
        private int jumpWx;

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getJumpWx() {
            return jumpWx;
        }

        public void setJumpWx(int jumpWx) {
            this.jumpWx = jumpWx;
        }
    }
}
