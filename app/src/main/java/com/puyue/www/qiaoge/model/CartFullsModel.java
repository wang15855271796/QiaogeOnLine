package com.puyue.www.qiaoge.model;

import java.util.List;

public class CartFullsModel {


    private Integer code;
    private String message;
    private DataBean data;
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
        private String startTime;
        private String endTime;
        private String limitInfo;
        private List<DeductDetailBean> deductDetail;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getLimitInfo() {
            return limitInfo;
        }

        public void setLimitInfo(String limitInfo) {
            this.limitInfo = limitInfo;
        }

        public List<DeductDetailBean> getDeductDetail() {
            return deductDetail;
        }

        public void setDeductDetail(List<DeductDetailBean> deductDetail) {
            this.deductDetail = deductDetail;
        }

        public static class DeductDetailBean {
            private String deductInfo;
            private String limitInfo;
            private List<SendProdsBean> sendProds;

            public String getDeductInfo() {
                return deductInfo;
            }

            public void setDeductInfo(String deductInfo) {
                this.deductInfo = deductInfo;
            }

            public String getLimitInfo() {
                return limitInfo;
            }

            public void setLimitInfo(String limitInfo) {
                this.limitInfo = limitInfo;
            }

            public List<SendProdsBean> getSendProds() {
                return sendProds;
            }

            public void setSendProds(List<SendProdsBean> sendProds) {
                this.sendProds = sendProds;
            }

            public static class SendProdsBean {
                private Integer type;
                private String name;
                private Object defaultPic;
                private String amount;
                private String spec;
                private String sendNum;
                private String roles;
                private String dateTime;
                private int productMainId;
                private Integer giftProdUseType;
                private String poolNo;
                private List<String> giftUseRole;

                public List<String> getGiftUseRole() {
                    return giftUseRole;
                }

                public void setGiftUseRole(List<String> giftUseRole) {
                    this.giftUseRole = giftUseRole;
                }

                public Integer getType() {
                    return type;
                }

                public void setType(Integer type) {
                    this.type = type;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(Object defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public String getAmount() {
                    return amount;
                }

                public void setAmount(String amount) {
                    this.amount = amount;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public String getSendNum() {
                    return sendNum;
                }

                public void setSendNum(String sendNum) {
                    this.sendNum = sendNum;
                }

                public String getRoles() {
                    return roles;
                }

                public void setRoles(String roles) {
                    this.roles = roles;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public Integer getGiftProdUseType() {
                    return giftProdUseType;
                }

                public void setGiftProdUseType(Integer giftProdUseType) {
                    this.giftProdUseType = giftProdUseType;
                }

                public String getPoolNo() {
                    return poolNo;
                }

                public void setPoolNo(String poolNo) {
                    this.poolNo = poolNo;
                }
            }
        }
    }
}
