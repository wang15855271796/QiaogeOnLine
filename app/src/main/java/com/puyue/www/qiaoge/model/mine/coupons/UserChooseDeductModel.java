package com.puyue.www.qiaoge.model.mine.coupons;

import com.puyue.www.qiaoge.base.BaseModel;

import java.util.List;

/**
 * @author daff
 * @date 2018/9/23.
 * 备注 选择优惠券
 */
public class UserChooseDeductModel extends BaseModel {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * giftDetailNo : 112020081300116014
         * giftName : 无限期
         * applyFrom : 人工积分兑换
         * amount : 50
         * limitAmtStr : 无门槛
         * state : ENABLED
         * dateTime : 永久有效
         * role : ["全场通用","仅杭州市(上城区;下城区;江干区;拱墅区;西湖区;滨江区;萧山区;余杭区;桐庐县;淳安县;建德市;富阳市;)可用"]
         */

        private String giftDetailNo;
        private String giftName;
        private String applyFrom;
        private String amount;
        private String limitAmtStr;
        private String state;
        private String dateTime;
        private List<String> role;
        private boolean flags;
        String giftFlag;
        String useInfo;
        String giftProdUseType;
        String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getGiftFlag() {
            return giftFlag;
        }

        public void setGiftFlag(String giftFlag) {
            this.giftFlag = giftFlag;
        }

        public String getGiftProdUseType() {
            return giftProdUseType;
        }

        public void setGiftProdUseType(String giftProdUseType) {
            this.giftProdUseType = giftProdUseType;
        }

        public String getUseInfo() {
            return useInfo;
        }

        public void setUseInfo(String useInfo) {
            this.useInfo = useInfo;
        }

        public boolean isFlags() {
                return flags;
            }

            public void setFlags(boolean flags) {
                this.flags = flags;
            }


        public String getGiftDetailNo() {
            return giftDetailNo;
        }

        public void setGiftDetailNo(String giftDetailNo) {
            this.giftDetailNo = giftDetailNo;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getApplyFrom() {
            return applyFrom;
        }

        public void setApplyFrom(String applyFrom) {
            this.applyFrom = applyFrom;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getLimitAmtStr() {
            return limitAmtStr;
        }

        public void setLimitAmtStr(String limitAmtStr) {
            this.limitAmtStr = limitAmtStr;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public List<String> getRole() {
            return role;
        }

        public void setRole(List<String> role) {
            this.role = role;
        }
    }
}
