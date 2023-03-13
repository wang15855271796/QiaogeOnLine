package com.puyue.www.qiaoge.model;

import java.util.List;

public class BalanceDetailModel {

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
        private String dateTime;
        private String flowRecordTypeName;
        private String createDate;
        private String amount;
        private String walletRecordChannelType;
        private Integer recordType;
        private Integer userId;
        private Integer subUserId;
        private Integer type;
        private Integer id;
        private String iconUrl;

        @Override
        public String toString() {
            return "DataBean{" +
                    "dateTime='" + dateTime + '\'' +
                    ", flowRecordTypeName='" + flowRecordTypeName + '\'' +
                    ", createDate='" + createDate + '\'' +
                    ", amount='" + amount + '\'' +
                    ", walletRecordChannelType='" + walletRecordChannelType + '\'' +
                    ", recordType=" + recordType +
                    ", userId=" + userId +
                    ", subUserId=" + subUserId +
                    ", type=" + type +
                    ", id=" + id +
                    ", iconUrl='" + iconUrl + '\'' +
                    '}';
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getFlowRecordTypeName() {
            return flowRecordTypeName;
        }

        public void setFlowRecordTypeName(String flowRecordTypeName) {
            this.flowRecordTypeName = flowRecordTypeName;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getWalletRecordChannelType() {
            return walletRecordChannelType;
        }

        public void setWalletRecordChannelType(String walletRecordChannelType) {
            this.walletRecordChannelType = walletRecordChannelType;
        }

        public Integer getRecordType() {
            return recordType;
        }

        public void setRecordType(Integer recordType) {
            this.recordType = recordType;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getSubUserId() {
            return subUserId;
        }

        public void setSubUserId(Integer subUserId) {
            this.subUserId = subUserId;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }
    }
}
