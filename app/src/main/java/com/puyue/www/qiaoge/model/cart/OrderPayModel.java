package com.puyue.www.qiaoge.model.cart;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/26.
 */

public class OrderPayModel {


    /**
     * code : 1
     * message : 成功
     * data : {"outTradeNo":"8d36e99baa0d41829bbb04e06d492afe","title":"团购","payToken":"_input_charset=\"utf-8\"&body=\"团购\"&notify_url=\"null\"&out_trade_no=\"8d36e99baa0d41829bbb04e06d492afe\"&partner=\"2088721203069640\"&payment_type=\"1\"&seller_id=\"13024298999@163.com\"&service=\"mobile.securitypay.pay\"&subject=\"团购\"&total_fee=\"80.0\"&sign=\"J8g9fIaXUJbvN4HXKrMW4sLtaX4nZz0tTwlBtKmDHr1cCx%2BLmkXXgHNRtcNY77v6lI%2FsIvbuc9os0id%2Beu%2B72AxGyPzGOfQ6%2FiHFfxOxvmAJobMKvougn3bDS8RJv0I4KHP0ZuCMq0W7TyR2Ry8vN3RcSyv8%2FqzQib6gesedQHk%3D\"&sign_type=\"RSA\"","payType":2}
     * success : true
     * error : false
     */

    public int code;
    public String message;
    public DataBean data;
    public boolean success;
    public boolean error;

    public static class DataBean {
        /**
         * outTradeNo : 8d36e99baa0d41829bbb04e06d492afe
         * title : 团购
         * payToken : _input_charset="utf-8"&body="团购"&notify_url="null"&out_trade_no="8d36e99baa0d41829bbb04e06d492afe"&partner="2088721203069640"&payment_type="1"&seller_id="13024298999@163.com"&service="mobile.securitypay.pay"&subject="团购"&total_fee="80.0"&sign="J8g9fIaXUJbvN4HXKrMW4sLtaX4nZz0tTwlBtKmDHr1cCx%2BLmkXXgHNRtcNY77v6lI%2FsIvbuc9os0id%2Beu%2B72AxGyPzGOfQ6%2FiHFfxOxvmAJobMKvougn3bDS8RJv0I4KHP0ZuCMq0W7TyR2Ry8vN3RcSyv8%2FqzQib6gesedQHk%3D"&sign_type="RSA"
         * payType : 2
         */

        public String outTradeNo;
        public String title;
        public String payToken;
        public int payType;
        public String appPayRequest;
        public String businessCstNo;
        public String merchantNo;
        public String orderNoList;
        public int jumpWx;
        public PayDataBean payData;

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
            private PayDataBean.MiniprogramPayInfoVSPBean miniprogramPayInfo_VSP;

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

            public PayDataBean.MiniprogramPayInfoVSPBean getMiniprogramPayInfo_VSP() {
                return miniprogramPayInfo_VSP;
            }

            public void setMiniprogramPayInfo_VSP(PayDataBean.MiniprogramPayInfoVSPBean miniprogramPayInfo_VSP) {
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
