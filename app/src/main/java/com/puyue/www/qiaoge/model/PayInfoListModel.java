package com.puyue.www.qiaoge.model;

import java.util.List;

public class PayInfoListModel {

    private Integer code;
    private String message;
    private List<DataBean> data;
    private Object extData;
    private Boolean error;
    private Boolean success;

    @Override
    public String toString() {
        return "PayInfoListModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extData=" + extData +
                ", error=" + error +
                ", success=" + success +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private String channelName;
        private Integer flag;
        private Integer jumpWx;
        private String userName;
        private String path;
        private Object type;

        @Override
        public String toString() {
            return "DataBean{" +
                    "channelName='" + channelName + '\'' +
                    ", flag=" + flag +
                    ", jumpWx=" + jumpWx +
                    ", userName='" + userName + '\'' +
                    ", path=" + path +
                    ", type=" + type +
                    '}';
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public Integer getFlag() {
            return flag;
        }

        public void setFlag(Integer flag) {
            this.flag = flag;
        }

        public Integer getJumpWx() {
            return jumpWx;
        }

        public void setJumpWx(Integer jumpWx) {
            this.jumpWx = jumpWx;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }
    }
}
