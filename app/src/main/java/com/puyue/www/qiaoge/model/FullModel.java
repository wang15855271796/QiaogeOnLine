package com.puyue.www.qiaoge.model;

import com.puyue.www.qiaoge.model.cart.CartsListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/1
 */
public class FullModel {
    private List<AdditionVOList> additionVOList;


    public List<AdditionVOList> getAdditionVOList() {
        return additionVOList;
    }

    public void setAdditionVOList(List<AdditionVOList> additionVOList) {
        this.additionVOList = additionVOList;
    }

    public static class AdditionVOList {
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
}
