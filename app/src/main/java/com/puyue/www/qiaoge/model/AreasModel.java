package com.puyue.www.qiaoge.model;

import java.util.List;

public class AreasModel {
    private Integer code;
    private String message;
    private List<DataBean> data;
    private Object extData;
    private Boolean error;
    private Boolean success;

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
        private String title;
        private String detailAddress;
        private LatLonVoBean latLonVo;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public LatLonVoBean getLatLonVo() {
            return latLonVo;
        }

        public void setLatLonVo(LatLonVoBean latLonVo) {
            this.latLonVo = latLonVo;
        }

        public static class LatLonVoBean {
            private Double lat;
            private Double lng;

            public Double getLat() {
                return lat;
            }

            public void setLat(Double lat) {
                this.lat = lat;
            }

            public Double getLng() {
                return lng;
            }

            public void setLng(Double lng) {
                this.lng = lng;
            }
        }
    }
}
