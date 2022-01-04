package com.puyue.www.qiaoge.model;

import java.util.List;

public class HuoDetailModel {


    /**
     * code : 1
     * message : 成功
     * data : {"sendAddr":{"name":"起梦科创园","memo":"地址备注","addrInfo":"杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角","contactName":"wjh","contactPhone":"18368493783"},"receiveAddr":{"name":"雅居乐国际花园","memo":"地址备注","addrInfo":"杭州市余杭区天目山西路258号","contactName":"wjh","contactPhone":"18368493783"},"orderDisplayId":"1475382986156290072","orderStatusName":"确认完成","vehicleName":"测试测-new","orderTime":"","contactPhone":"18757104843","orderRemark":"订单备注","createTime":"","totalPrice":59,"payPrice":59,"unpaidPrice":0,"priceInfo":[{"amount":35,"billTypeName":"基本路费","payStatusName":"已支付"},{"amount":24,"billTypeName":"额外需求","payStatusName":"已支付"}],"canOrderCancel":0,"needToCancelReason":0,"needToPay":0,"canAddTips":0,"appealEnabledStatus":0,"driverInfo":{"name":"戴师傅","vehicleName":"面包车","phoneType":3,"phone":"10103636","bindPhone":"","latLon":{"lat":22.6458664,"lon":113.788322}},"billAppeal":null}
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
         * sendAddr : {"name":"起梦科创园","memo":"地址备注","addrInfo":"杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角","contactName":"wjh","contactPhone":"18368493783"}
         * receiveAddr : {"name":"雅居乐国际花园","memo":"地址备注","addrInfo":"杭州市余杭区天目山西路258号","contactName":"wjh","contactPhone":"18368493783"}
         * orderDisplayId : 1475382986156290072
         * orderStatusName : 确认完成
         * vehicleName : 测试测-new
         * orderTime :
         * contactPhone : 18757104843
         * orderRemark : 订单备注
         * createTime :
         * totalPrice : 59
         * payPrice : 59
         * unpaidPrice : 0
         * priceInfo : [{"amount":35,"billTypeName":"基本路费","payStatusName":"已支付"},{"amount":24,"billTypeName":"额外需求","payStatusName":"已支付"}]
         * canOrderCancel : 0
         * needToCancelReason : 0
         * needToPay : 0
         * canAddTips : 0
         * appealEnabledStatus : 0
         * driverInfo : {"name":"戴师傅","vehicleName":"面包车","phoneType":3,"phone":"10103636","bindPhone":"","latLon":{"lat":22.6458664,"lon":113.788322}}
         * billAppeal : null
         */

        private SendAddrBean sendAddr;
        private ReceiveAddrBean receiveAddr;
        private String orderDisplayId;
        private String orderStatusName;
        private String vehicleName;
        private String orderTime;
        private String contactPhone;
        private String orderRemark;
        private String createTime;
        private int totalPrice;
        private int payPrice;
        private int unpaidPrice;
        private int canOrderCancel;
        private int needToCancelReason;
        private int needToPay;
        private int canAddTips;
        private int appealEnabledStatus;
        private DriverInfoBean driverInfo;
        private Object billAppeal;
        private List<PriceInfoBean> priceInfo;

        public SendAddrBean getSendAddr() {
            return sendAddr;
        }

        public void setSendAddr(SendAddrBean sendAddr) {
            this.sendAddr = sendAddr;
        }

        public ReceiveAddrBean getReceiveAddr() {
            return receiveAddr;
        }

        public void setReceiveAddr(ReceiveAddrBean receiveAddr) {
            this.receiveAddr = receiveAddr;
        }

        public String getOrderDisplayId() {
            return orderDisplayId;
        }

        public void setOrderDisplayId(String orderDisplayId) {
            this.orderDisplayId = orderDisplayId;
        }

        public String getOrderStatusName() {
            return orderStatusName;
        }

        public void setOrderStatusName(String orderStatusName) {
            this.orderStatusName = orderStatusName;
        }

        public String getVehicleName() {
            return vehicleName;
        }

        public void setVehicleName(String vehicleName) {
            this.vehicleName = vehicleName;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getOrderRemark() {
            return orderRemark;
        }

        public void setOrderRemark(String orderRemark) {
            this.orderRemark = orderRemark;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(int payPrice) {
            this.payPrice = payPrice;
        }

        public int getUnpaidPrice() {
            return unpaidPrice;
        }

        public void setUnpaidPrice(int unpaidPrice) {
            this.unpaidPrice = unpaidPrice;
        }

        public int getCanOrderCancel() {
            return canOrderCancel;
        }

        public void setCanOrderCancel(int canOrderCancel) {
            this.canOrderCancel = canOrderCancel;
        }

        public int getNeedToCancelReason() {
            return needToCancelReason;
        }

        public void setNeedToCancelReason(int needToCancelReason) {
            this.needToCancelReason = needToCancelReason;
        }

        public int getNeedToPay() {
            return needToPay;
        }

        public void setNeedToPay(int needToPay) {
            this.needToPay = needToPay;
        }

        public int getCanAddTips() {
            return canAddTips;
        }

        public void setCanAddTips(int canAddTips) {
            this.canAddTips = canAddTips;
        }

        public int getAppealEnabledStatus() {
            return appealEnabledStatus;
        }

        public void setAppealEnabledStatus(int appealEnabledStatus) {
            this.appealEnabledStatus = appealEnabledStatus;
        }

        public DriverInfoBean getDriverInfo() {
            return driverInfo;
        }

        public void setDriverInfo(DriverInfoBean driverInfo) {
            this.driverInfo = driverInfo;
        }

        public Object getBillAppeal() {
            return billAppeal;
        }

        public void setBillAppeal(Object billAppeal) {
            this.billAppeal = billAppeal;
        }

        public List<PriceInfoBean> getPriceInfo() {
            return priceInfo;
        }

        public void setPriceInfo(List<PriceInfoBean> priceInfo) {
            this.priceInfo = priceInfo;
        }

        public static class SendAddrBean {
            /**
             * name : 起梦科创园
             * memo : 地址备注
             * addrInfo : 杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角
             * contactName : wjh
             * contactPhone : 18368493783
             */

            private String name;
            private String memo;
            private String addrInfo;
            private String contactName;
            private String contactPhone;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getAddrInfo() {
                return addrInfo;
            }

            public void setAddrInfo(String addrInfo) {
                this.addrInfo = addrInfo;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getContactPhone() {
                return contactPhone;
            }

            public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
            }
        }

        public static class ReceiveAddrBean {
            /**
             * name : 雅居乐国际花园
             * memo : 地址备注
             * addrInfo : 杭州市余杭区天目山西路258号
             * contactName : wjh
             * contactPhone : 18368493783
             */

            private String name;
            private String memo;
            private String addrInfo;
            private String contactName;
            private String contactPhone;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getAddrInfo() {
                return addrInfo;
            }

            public void setAddrInfo(String addrInfo) {
                this.addrInfo = addrInfo;
            }

            public String getContactName() {
                return contactName;
            }

            public void setContactName(String contactName) {
                this.contactName = contactName;
            }

            public String getContactPhone() {
                return contactPhone;
            }

            public void setContactPhone(String contactPhone) {
                this.contactPhone = contactPhone;
            }
        }

        public static class DriverInfoBean {
            /**
             * name : 戴师傅
             * vehicleName : 面包车
             * phoneType : 3
             * phone : 10103636
             * bindPhone :
             * latLon : {"lat":22.6458664,"lon":113.788322}
             */

            private String name;
            private String vehicleName;
            private int phoneType;
            private String phone;
            private String bindPhone;
            private LatLonBean latLon;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getVehicleName() {
                return vehicleName;
            }

            public void setVehicleName(String vehicleName) {
                this.vehicleName = vehicleName;
            }

            public int getPhoneType() {
                return phoneType;
            }

            public void setPhoneType(int phoneType) {
                this.phoneType = phoneType;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getBindPhone() {
                return bindPhone;
            }

            public void setBindPhone(String bindPhone) {
                this.bindPhone = bindPhone;
            }

            public LatLonBean getLatLon() {
                return latLon;
            }

            public void setLatLon(LatLonBean latLon) {
                this.latLon = latLon;
            }

            public static class LatLonBean {
                /**
                 * lat : 22.6458664
                 * lon : 113.788322
                 */

                private double lat;
                private double lon;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLon() {
                    return lon;
                }

                public void setLon(double lon) {
                    this.lon = lon;
                }
            }
        }

        public static class PriceInfoBean {
            /**
             * amount : 35
             * billTypeName : 基本路费
             * payStatusName : 已支付
             */

            private int amount;
            private String billTypeName;
            private String payStatusName;
            private int payStatus;

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getBillTypeName() {
                return billTypeName;
            }

            public void setBillTypeName(String billTypeName) {
                this.billTypeName = billTypeName;
            }

            public String getPayStatusName() {
                return payStatusName;
            }

            public void setPayStatusName(String payStatusName) {
                this.payStatusName = payStatusName;
            }
        }
    }
}
