package com.puyue.www.qiaoge.model;

import java.util.List;

public class VipCenterModel {

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
        private String phone;
        private String endTimeDesc;
        private String saveAmountDesc;
        private String state;
        private List<VipPackagesBean> vipPackages;
        private String vipDeductUrl;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEndTimeDesc() {
            return endTimeDesc;
        }

        public void setEndTimeDesc(String endTimeDesc) {
            this.endTimeDesc = endTimeDesc;
        }

        public String getSaveAmountDesc() {
            return saveAmountDesc;
        }

        public void setSaveAmountDesc(String saveAmountDesc) {
            this.saveAmountDesc = saveAmountDesc;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<VipPackagesBean> getVipPackages() {
            return vipPackages;
        }

        public void setVipPackages(List<VipPackagesBean> vipPackages) {
            this.vipPackages = vipPackages;
        }

        public String getVipDeductUrl() {
            return vipDeductUrl;
        }

        public void setVipDeductUrl(String vipDeductUrl) {
            this.vipDeductUrl = vipDeductUrl;
        }

        public static class VipPackagesBean {
            private Object oriPrice;
            private String price;
            private String title;
            private Integer packageId;

            public Object getOriPrice() {
                return oriPrice;
            }

            public void setOriPrice(Object oriPrice) {
                this.oriPrice = oriPrice;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Integer getPackageId() {
                return packageId;
            }

            public void setPackageId(Integer packageId) {
                this.packageId = packageId;
            }
        }
    }
}
