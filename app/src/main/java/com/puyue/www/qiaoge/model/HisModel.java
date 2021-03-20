package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/19
 */
public class HisModel {

    /**
     * code : 1
     * message : 成功
     * data : []
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private boolean error;
    private boolean success;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String detailAddress;
        private String id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "detailAddress='" + detailAddress + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
