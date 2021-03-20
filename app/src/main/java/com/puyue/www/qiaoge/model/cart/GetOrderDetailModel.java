package com.puyue.www.qiaoge.model.cart;

import com.puyue.www.qiaoge.base.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/24.
 */

public class GetOrderDetailModel {


    /**
     * code : 1
     * message : 成功
     * data : {"orderStatusName":"已取消","orderStatus":7,"orderDeliveryType":0,"sysCurrentTime":null,"orderOverTime":null,"addressVO":{"id":371928,"orderId":392609,"userId":562,"userName":"王家豪","contactPhone":"18368493783","provinceCode":"330000","provinceName":"浙江省","cityCode":"330100","cityName":"杭州市","areaCode":"330106","areaName":"西湖区","detailAddress":"三墩镇中旅紫金名门","isDefault":null,"shopName":"紫金名门","sendType":null},"driverName":null,"driverPhone":null,"deliverTimeName":"","deliverTimeStart":"","deliverTimeEnd":"","pickUserName":null,"pickPhone":null,"wareAddress":null,"sendStartTime":null,"orderProds":[{"selfOrNot":0,"sendTimeTpl":"次日达","orderId":"20200930155454-255c","sendTimeStr":"现在下单，预计明天(10月07日)开始配送","products":[{"selfOrNot":0,"businessType":1,"productMainId":7185,"productId":7544,"prodTypeUrl":null,"productName":"【五丰冷食】猪肉馅","spec":"1.02kg*6包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"0.02","oldPrice":null,"specPrices":["0.02/1包*1"]}]},{"selfOrNot":1,"sendTimeTpl":"隔日送","orderId":"20200930155454-1c22","sendTimeStr":"现在下单，预计后天(10月08日)开始配送","products":[{"selfOrNot":1,"businessType":11,"productMainId":4400,"productId":4400,"prodTypeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/afa2f351e7d74757bd59b740901440d1.png","productName":"【膳陈坊】黑椒肉柳1","spec":"20kg*4包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","price":"2.00","oldPrice":null,"specPrices":["1.00/3箱*2"]},{"selfOrNot":1,"businessType":1,"productMainId":7288,"productId":7662,"prodTypeUrl":null,"productName":"【膳陈坊】黑椒肉柳1","spec":"20kg*4包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","price":"20.00","oldPrice":null,"specPrices":["20.00/1箱*1"]}]}],"sendGiftInfo":null,"prodNum":2,"totalAmount":"22.02","prodAmount":"22.02","deliveryFee":"0","sendAmountStr":"","vipReduct":"0","vipReductStr":"","normalReduct":"0","normalReductStr":"","giftAmount":"0","giftName":"","orderId":"20200930155454-255c","gmtCreate":"2020-09-30 15:54:55","payDate":"","waitSendReceiveTime":"","confirmDate":"","finishDate":"","remark":"","payChannel":"","payChannelType":0,"connectOrderId":null}
     * error : false
     * success : true
     */

    public int code;
    public String message;
    public DataBean data;
    public boolean error;
    public boolean success;

    public static class DataBean {
        /**
         * orderStatusName : 已取消
         * orderStatus : 7
         * orderDeliveryType : 0
         * sysCurrentTime : null
         * orderOverTime : null
         * addressVO : {"id":371928,"orderId":392609,"userId":562,"userName":"王家豪","contactPhone":"18368493783","provinceCode":"330000","provinceName":"浙江省","cityCode":"330100","cityName":"杭州市","areaCode":"330106","areaName":"西湖区","detailAddress":"三墩镇中旅紫金名门","isDefault":null,"shopName":"紫金名门","sendType":null}
         * driverName : null
         * driverPhone : null
         * deliverTimeName :
         * deliverTimeStart :
         * deliverTimeEnd :
         * pickUserName : null
         * pickPhone : null
         * wareAddress : null
         * sendStartTime : null
         * orderProds : [{"selfOrNot":0,"sendTimeTpl":"次日达","orderId":"20200930155454-255c","sendTimeStr":"现在下单，预计明天(10月07日)开始配送","products":[{"selfOrNot":0,"businessType":1,"productMainId":7185,"productId":7544,"prodTypeUrl":null,"productName":"【五丰冷食】猪肉馅","spec":"1.02kg*6包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"0.02","oldPrice":null,"specPrices":["0.02/1包*1"]}]},{"selfOrNot":1,"sendTimeTpl":"隔日送","orderId":"20200930155454-1c22","sendTimeStr":"现在下单，预计后天(10月08日)开始配送","products":[{"selfOrNot":1,"businessType":11,"productMainId":4400,"productId":4400,"prodTypeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/afa2f351e7d74757bd59b740901440d1.png","productName":"【膳陈坊】黑椒肉柳1","spec":"20kg*4包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","price":"2.00","oldPrice":null,"specPrices":["1.00/3箱*2"]},{"selfOrNot":1,"businessType":1,"productMainId":7288,"productId":7662,"prodTypeUrl":null,"productName":"【膳陈坊】黑椒肉柳1","spec":"20kg*4包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","price":"20.00","oldPrice":null,"specPrices":["20.00/1箱*1"]}]}]
         * sendGiftInfo : null
         * prodNum : 2
         * totalAmount : 22.02
         * prodAmount : 22.02
         * deliveryFee : 0
         * sendAmountStr :
         * vipReduct : 0
         * vipReductStr :
         * normalReduct : 0
         * normalReductStr :
         * giftAmount : 0
         * giftName :
         * orderId : 20200930155454-255c
         * gmtCreate : 2020-09-30 15:54:55
         * payDate :
         * waitSendReceiveTime :
         * confirmDate :
         * finishDate :
         * remark :
         * payChannel :
         * payChannelType : 0
         * connectOrderId : null
         */
        public String cannotReturnGoodsMsg;
        public boolean canReturnGoods;
        public String orderStatusName;
        public int orderStatus;
        public int orderDeliveryType;
        public long sysCurrentTime;
        public long orderOverTime;
        public AddressVOBean addressVO;
        public String driverName;
        public String driverPhone;
        public String deliverTimeName;
        public String deliverTimeStart;
        public String deliverTimeEnd;
        public String pickUserName;
        public String pickPhone;
        public String customerPhone;
        public String wareAddress;
        public String wareName;
        public String sendStartTime;
        public List<SendGiftInfo> sendGiftInfo;
        public int prodNum;
        public String totalAmount;
        public String prodAmount;
        public String deliveryFee;
        public String sendAmountStr;
        public String vipReduct;
        public String vipReductStr;
        public String normalReduct;
        public String normalReductStr;
        public String giftAmount;
        public String giftName;
        public String orderId;
        public String gmtCreate;
        public String payDate;
        public String waitSendReceiveTime;
        public String confirmDate;
        public String finishDate;
        public String remark;
        public String payChannel;
        public int payChannelType;
        public String connectOrderId;
        public List<OrderProdsBean> orderProds;

        public static class SendGiftInfo {
            private String type;
            private String productId;
            private String giftPoolNo;
            private String warehouseId;
            private String productUnit;
            private String name;
            private String spec;
            private String picUrl;
            private String sendNum;
            private String sendNumDesc;
            private String additionFlag;

            @Override
            public String toString() {
                return "AdditionVOList{" +
                        "type='" + type + '\'' +
                        ", productId='" + productId + '\'' +
                        ", giftPoolNo='" + giftPoolNo + '\'' +
                        ", warehouseId='" + warehouseId + '\'' +
                        ", productUnit='" + productUnit + '\'' +
                        ", name='" + name + '\'' +
                        ", spec='" + spec + '\'' +
                        ", picUrl='" + picUrl + '\'' +
                        ", sendNum='" + sendNum + '\'' +
                        ", sendNumDesc='" + sendNumDesc + '\'' +
                        ", additionFlag='" + additionFlag + '\'' +
                        '}';
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getGiftPoolNo() {
                return giftPoolNo;
            }

            public void setGiftPoolNo(String giftPoolNo) {
                this.giftPoolNo = giftPoolNo;
            }

            public String getWarehouseId() {
                return warehouseId;
            }

            public void setWarehouseId(String warehouseId) {
                this.warehouseId = warehouseId;
            }

            public String getProductUnit() {
                return productUnit;
            }

            public void setProductUnit(String productUnit) {
                this.productUnit = productUnit;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getSendNum() {
                return sendNum;
            }

            public void setSendNum(String sendNum) {
                this.sendNum = sendNum;
            }

            public String getSendNumDesc() {
                return sendNumDesc;
            }

            public void setSendNumDesc(String sendNumDesc) {
                this.sendNumDesc = sendNumDesc;
            }

            public String getAdditionFlag() {
                return additionFlag;
            }

            public void setAdditionFlag(String additionFlag) {
                this.additionFlag = additionFlag;
            }
        }

        public static class AddressVOBean {
            /**
             * id : 371928
             * orderId : 392609
             * userId : 562
             * userName : 王家豪
             * contactPhone : 18368493783
             * provinceCode : 330000
             * provinceName : 浙江省
             * cityCode : 330100
             * cityName : 杭州市
             * areaCode : 330106
             * areaName : 西湖区
             * detailAddress : 三墩镇中旅紫金名门
             * isDefault : null
             * shopName : 紫金名门
             * sendType : null
             */

            public int id;
            public int orderId;
            public int userId;
            public String userName;
            public String contactPhone;
            public String provinceCode;
            public String provinceName;
            public String cityCode;
            public String cityName;
            public String areaCode;
            public String areaName;
            public String detailAddress;
            public Object isDefault;
            public String shopName;
            public Object sendType;

        }

        public static class OrderProdsBean {
            /**
             * selfOrNot : 0
             * sendTimeTpl : 次日达
             * orderId : 20200930155454-255c
             * sendTimeStr : 现在下单，预计明天(10月07日)开始配送
             * products : [{"selfOrNot":0,"businessType":1,"productMainId":7185,"productId":7544,"prodTypeUrl":null,"productName":"【五丰冷食】猪肉馅","spec":"1.02kg*6包/箱","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","price":"0.02","oldPrice":null,"specPrices":["0.02/1包*1"]}]
             */

            public String selfOrNot;
            public String sendTimeTpl;
            public String orderId;
            public String sendTimeStr;
            public List<ProductsBean> products;
            public String title;

            public static class ProductsBean {
                /**
                 * selfOrNot : 0
                 * businessType : 1
                 * productMainId : 7185
                 * productId : 7544
                 * prodTypeUrl : null
                 * productName : 【五丰冷食】猪肉馅
                 * spec : 1.02kg*6包/箱
                 * picUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * price : 0.02
                 * oldPrice : null
                 * specPrices : ["0.02/1包*1"]
                 */

                public int selfOrNot;
                public int businessType;
                public int productMainId;
                public int productId;
                public String prodTypeUrl;
                public String productName;
                public String spec;
                public String picUrl;
                public String price;
                public String oldPrice;
                public List<String> specPrices;
                public String afterPrice;

            }
        }
    }
}
