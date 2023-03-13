package com.puyue.www.qiaoge.model;

public class HomeStyleModel {


    private Integer code;
    private String message;
    private DataBean data;
    private Object extData;
    private Boolean success;
    private Boolean error;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public static class DataBean {
        private Integer id;
        private String templateStyle;
        private String appInitUrl;
        private String appHomeUrl;
        private String appMyUrl;
        private String bannerUrl;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", templateStyle='" + templateStyle + '\'' +
                    ", appInitUrl='" + appInitUrl + '\'' +
                    ", appHomeUrl='" + appHomeUrl + '\'' +
                    ", appMyUrl='" + appMyUrl + '\'' +
                    ", bannerUrl='" + bannerUrl + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTemplateStyle() {
            return templateStyle;
        }

        public void setTemplateStyle(String templateStyle) {
            this.templateStyle = templateStyle;
        }

        public String getAppInitUrl() {
            return appInitUrl;
        }

        public void setAppInitUrl(String appInitUrl) {
            this.appInitUrl = appInitUrl;
        }

        public String getAppHomeUrl() {
            return appHomeUrl;
        }

        public void setAppHomeUrl(String appHomeUrl) {
            this.appHomeUrl = appHomeUrl;
        }

        public String getAppMyUrl() {
            return appMyUrl;
        }

        public void setAppMyUrl(String appMyUrl) {
            this.appMyUrl = appMyUrl;
        }

        public String getBannerUrl() {
            return bannerUrl;
        }

        public void setBannerUrl(String bannerUrl) {
            this.bannerUrl = bannerUrl;
        }
    }

    @Override
    public String toString() {
        return "HomeStyleModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extData=" + extData +
                ", success=" + success +
                ", error=" + error +
                '}';
    }
}
