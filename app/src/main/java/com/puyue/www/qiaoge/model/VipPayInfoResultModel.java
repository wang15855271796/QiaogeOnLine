package com.puyue.www.qiaoge.model;

import com.puyue.www.qiaoge.model.mine.order.VipPayResultModel;

public class VipPayInfoResultModel {
    /**
     * code : 1
     * message : 成功
     * data : {"tradeStatus":"TRADE_SUCCESS","payMsg":"支付成功","errorMsg":"已为您开通12个月VIP会员","vo":{"bannerUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/ef3eae9b65cc488cac6c07ca1ba5adf5.png","bannerDetailUrl":"http://116.62.67.230:8082/apph5/html/invite.html"}}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean error;
    private boolean success;


    public static class DataBean {
        private String status;
        private String message;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' +
                    '}';
        }
    }


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


}
