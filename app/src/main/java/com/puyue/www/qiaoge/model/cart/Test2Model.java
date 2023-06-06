package com.puyue.www.qiaoge.model.cart;

public class Test2Model {

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
        private String outTradeNo;
        private String title;
        private Object payToken;
        private Integer payType;
        private PayDataBean payData;

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getPayToken() {
            return payToken;
        }

        public void setPayToken(Object payToken) {
            this.payToken = payToken;
        }

        public Integer getPayType() {
            return payType;
        }

        public void setPayType(Integer payType) {
            this.payType = payType;
        }

        public PayDataBean getPayData() {
            return payData;
        }

        public void setPayData(PayDataBean payData) {
            this.payData = payData;
        }

        public static class PayDataBean {
            private String tlAppId;
            private String cusid;
            private Object orgid;
            private String appid;
            private String version;
            private Integer trxamt;
            private String reqsn;
            private String notify_url;
            private String body;
            private Object remark;
            private Object validtime;
            private String paytype;
            private Object limit_pay;
            private Object asinfo;
            private String randomstr;
            private String signtype;
            private Object sign;
            private MiniprogramPayInfoVSPBean miniprogramPayInfo_VSP;

            public String getTlAppId() {
                return tlAppId;
            }

            public void setTlAppId(String tlAppId) {
                this.tlAppId = tlAppId;
            }

            public String getCusid() {
                return cusid;
            }

            public void setCusid(String cusid) {
                this.cusid = cusid;
            }

            public Object getOrgid() {
                return orgid;
            }

            public void setOrgid(Object orgid) {
                this.orgid = orgid;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public Integer getTrxamt() {
                return trxamt;
            }

            public void setTrxamt(Integer trxamt) {
                this.trxamt = trxamt;
            }

            public String getReqsn() {
                return reqsn;
            }

            public void setReqsn(String reqsn) {
                this.reqsn = reqsn;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public Object getValidtime() {
                return validtime;
            }

            public void setValidtime(Object validtime) {
                this.validtime = validtime;
            }

            public String getPaytype() {
                return paytype;
            }

            public void setPaytype(String paytype) {
                this.paytype = paytype;
            }

            public Object getLimit_pay() {
                return limit_pay;
            }

            public void setLimit_pay(Object limit_pay) {
                this.limit_pay = limit_pay;
            }

            public Object getAsinfo() {
                return asinfo;
            }

            public void setAsinfo(Object asinfo) {
                this.asinfo = asinfo;
            }

            public String getRandomstr() {
                return randomstr;
            }

            public void setRandomstr(String randomstr) {
                this.randomstr = randomstr;
            }

            public String getSigntype() {
                return signtype;
            }

            public void setSigntype(String signtype) {
                this.signtype = signtype;
            }

            public Object getSign() {
                return sign;
            }

            public void setSign(Object sign) {
                this.sign = sign;
            }

            public MiniprogramPayInfoVSPBean getMiniprogramPayInfo_VSP() {
                return miniprogramPayInfo_VSP;
            }

            public void setMiniprogramPayInfo_VSP(MiniprogramPayInfoVSPBean miniprogramPayInfo_VSP) {
                this.miniprogramPayInfo_VSP = miniprogramPayInfo_VSP;
            }

            public static class MiniprogramPayInfoVSPBean {
                private String randomstr;
                private String cusid;
                private String appid;
                private String innerappid;
                private String sign;
                private String signtype;
                private String body;
                private String notify_url;
                private String version;
                private String paytype;
                private String trxamt;
                private String reqsn;

                public String getRandomstr() {
                    return randomstr;
                }

                public void setRandomstr(String randomstr) {
                    this.randomstr = randomstr;
                }

                public String getCusid() {
                    return cusid;
                }

                public void setCusid(String cusid) {
                    this.cusid = cusid;
                }

                public String getAppid() {
                    return appid;
                }

                public void setAppid(String appid) {
                    this.appid = appid;
                }

                public String getInnerappid() {
                    return innerappid;
                }

                public void setInnerappid(String innerappid) {
                    this.innerappid = innerappid;
                }

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public String getSigntype() {
                    return signtype;
                }

                public void setSigntype(String signtype) {
                    this.signtype = signtype;
                }

                public String getBody() {
                    return body;
                }

                public void setBody(String body) {
                    this.body = body;
                }

                public String getNotify_url() {
                    return notify_url;
                }

                public void setNotify_url(String notify_url) {
                    this.notify_url = notify_url;
                }

                public String getVersion() {
                    return version;
                }

                public void setVersion(String version) {
                    this.version = version;
                }

                public String getPaytype() {
                    return paytype;
                }

                public void setPaytype(String paytype) {
                    this.paytype = paytype;
                }

                public String getTrxamt() {
                    return trxamt;
                }

                public void setTrxamt(String trxamt) {
                    this.trxamt = trxamt;
                }

                public String getReqsn() {
                    return reqsn;
                }

                public void setReqsn(String reqsn) {
                    this.reqsn = reqsn;
                }
            }
        }
    }
}
