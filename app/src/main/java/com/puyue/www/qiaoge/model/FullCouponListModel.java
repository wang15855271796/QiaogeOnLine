package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullCouponListModel {


    /**
     * code : 1
     * message : 成功
     * data : {"giftName":"800减10","giftType":null,"amount":null,"amountStr":"10","limitAmtStr":"800","limitAmt":null,"applyFrom":null,"dateTime":"2021-10-09 00:00-2021-11-09 00:00","role":["全场通用;仅杭州市可用[淳安县][建德市][临安市]除外;与活动同享"],"state":null,"giftDetailNo":null,"orderId":null,"useInfo":"满800元可用","giftProdUseType":null,"jumpFlag":1,"giftArea":null,"giftFlag":null}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean error;
    private boolean success;

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

    public static class DataBean {
        /**
         * giftName : 800减10
         * giftType : null
         * amount : null
         * amountStr : 10
         * limitAmtStr : 800
         * limitAmt : null
         * applyFrom : null
         * dateTime : 2021-10-09 00:00-2021-11-09 00:00
         * role : ["全场通用;仅杭州市可用[淳安县][建德市][临安市]除外;与活动同享"]
         * state : null
         * giftDetailNo : null
         * orderId : null
         * useInfo : 满800元可用
         * giftProdUseType : null
         * jumpFlag : 1
         * giftArea : null
         * giftFlag : null
         */

        private String giftName;
        private Object giftType;
        private Object amount;
        private String amountStr;
        private String limitAmtStr;
        private Object limitAmt;
        private Object applyFrom;
        private String dateTime;
        private Object state;
        private Object giftDetailNo;
        private Object orderId;
        private String useInfo;
        private Object giftProdUseType;
        private int jumpFlag;
        private Object giftArea;
        private Object giftFlag;
        private List<String> role;
        private List<String> giftUseRole;

        public List<String> getGiftUseRole() {
            return giftUseRole;
        }

        public void setGiftUseRole(List<String> giftUseRole) {
            this.giftUseRole = giftUseRole;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public Object getGiftType() {
            return giftType;
        }

        public void setGiftType(Object giftType) {
            this.giftType = giftType;
        }

        public Object getAmount() {
            return amount;
        }

        public void setAmount(Object amount) {
            this.amount = amount;
        }

        public String getAmountStr() {
            return amountStr;
        }

        public void setAmountStr(String amountStr) {
            this.amountStr = amountStr;
        }

        public String getLimitAmtStr() {
            return limitAmtStr;
        }

        public void setLimitAmtStr(String limitAmtStr) {
            this.limitAmtStr = limitAmtStr;
        }

        public Object getLimitAmt() {
            return limitAmt;
        }

        public void setLimitAmt(Object limitAmt) {
            this.limitAmt = limitAmt;
        }

        public Object getApplyFrom() {
            return applyFrom;
        }

        public void setApplyFrom(Object applyFrom) {
            this.applyFrom = applyFrom;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public Object getState() {
            return state;
        }

        public void setState(Object state) {
            this.state = state;
        }

        public Object getGiftDetailNo() {
            return giftDetailNo;
        }

        public void setGiftDetailNo(Object giftDetailNo) {
            this.giftDetailNo = giftDetailNo;
        }

        public Object getOrderId() {
            return orderId;
        }

        public void setOrderId(Object orderId) {
            this.orderId = orderId;
        }

        public String getUseInfo() {
            return useInfo;
        }

        public void setUseInfo(String useInfo) {
            this.useInfo = useInfo;
        }

        public Object getGiftProdUseType() {
            return giftProdUseType;
        }

        public void setGiftProdUseType(Object giftProdUseType) {
            this.giftProdUseType = giftProdUseType;
        }

        public int getJumpFlag() {
            return jumpFlag;
        }

        public void setJumpFlag(int jumpFlag) {
            this.jumpFlag = jumpFlag;
        }

        public Object getGiftArea() {
            return giftArea;
        }

        public void setGiftArea(Object giftArea) {
            this.giftArea = giftArea;
        }

        public Object getGiftFlag() {
            return giftFlag;
        }

        public void setGiftFlag(Object giftFlag) {
            this.giftFlag = giftFlag;
        }

        public List<String> getRole() {
            return role;
        }

        public void setRole(List<String> role) {
            this.role = role;
        }
    }
}
