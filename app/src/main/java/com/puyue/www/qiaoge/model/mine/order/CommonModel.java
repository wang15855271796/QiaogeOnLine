package com.puyue.www.qiaoge.model.mine.order;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/9
 */
public class CommonModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"productMainId":null,"productId":914,"picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/12c1c2772cc342ee89df3c7518772026.jpg","name":"【翘歌】经典培根","spec":"1.5kg*8 包/箱","costPrice":"","averagePrice":"","amount":"19.00","actualAmount":"","reductAmount":"","productNum":0,"businessType":1,"checkStatus":null,"productDescVOList":[{"detailDesc":null,"totalNum":"X1","productNum":1,"productCombinationPriceId":91660,"productUnit":"3203","price":"19.00","afterPrice":"","unit":null,"newDesc":null,"totalPrice":null,"costPrice":null}],"teamProdList":null,"startTime":"","endTime":"","days":0,"price":null,"returnProductMainId":null,"oldPrice":null,"prodTypeUrl":null,"flagUrl":null,"inventory":null,"productPl":null,"warehouseName":"--","returnState":"","returnNum":"","returnLowNum":null,"locationNo":"","cartAdditionProductVOList":null,"additionVOList":null,"onShelves":null,"companyId":null,"selfOrNot":0,"sendTimeTpl":"","prodFrom":null}]
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private boolean error;
    private boolean success;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * productMainId : null
         * productId : 914
         * picUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/12c1c2772cc342ee89df3c7518772026.jpg
         * name : 【翘歌】经典培根
         * spec : 1.5kg*8 包/箱
         * costPrice :
         * averagePrice :
         * amount : 19.00
         * actualAmount :
         * reductAmount :
         * productNum : 0
         * businessType : 1
         * checkStatus : null
         * productDescVOList : [{"detailDesc":null,"totalNum":"X1","productNum":1,"productCombinationPriceId":91660,"productUnit":"3203","price":"19.00","afterPrice":"","unit":null,"newDesc":null,"totalPrice":null,"costPrice":null}]
         * teamProdList : null
         * startTime :
         * endTime :
         * days : 0
         * price : null
         * returnProductMainId : null
         * oldPrice : null
         * prodTypeUrl : null
         * flagUrl : null
         * inventory : null
         * productPl : null
         * warehouseName : --
         * returnState :
         * returnNum :
         * returnLowNum : null
         * locationNo :
         * cartAdditionProductVOList : null
         * additionVOList : null
         * onShelves : null
         * companyId : null
         * selfOrNot : 0
         * sendTimeTpl :
         * prodFrom : null
         */

        private Object productMainId;
        private int productId;
        private String picUrl;
        private String name;
        private String spec;
        private String costPrice;
        private String averagePrice;
        private String amount;
        private String actualAmount;
        private String reductAmount;
        private int productNum;
        private int businessType;
        private Object checkStatus;
        private Object teamProdList;
        private String startTime;
        private String endTime;
        private int days;
        private Object price;
        private Object returnProductMainId;
        private Object oldPrice;
        private Object prodTypeUrl;
        private Object flagUrl;
        private Object inventory;
        private Object productPl;
        private String warehouseName;
        private String returnState;
        private String returnNum;
        private Object returnLowNum;
        private String locationNo;
        private Object cartAdditionProductVOList;
        private Object additionVOList;
        private Object onShelves;
        private Object companyId;
        private int selfOrNot;
        private String sendTimeTpl;
        private Object prodFrom;
        private List<ProductDescVOListBean> productDescVOList;

        public Object getProductMainId() {
            return productMainId;
        }

        public void setProductMainId(Object productMainId) {
            this.productMainId = productMainId;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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

        public String getCostPrice() {
            return costPrice;
        }

        public void setCostPrice(String costPrice) {
            this.costPrice = costPrice;
        }

        public String getAveragePrice() {
            return averagePrice;
        }

        public void setAveragePrice(String averagePrice) {
            this.averagePrice = averagePrice;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getActualAmount() {
            return actualAmount;
        }

        public void setActualAmount(String actualAmount) {
            this.actualAmount = actualAmount;
        }

        public String getReductAmount() {
            return reductAmount;
        }

        public void setReductAmount(String reductAmount) {
            this.reductAmount = reductAmount;
        }

        public int getProductNum() {
            return productNum;
        }

        public void setProductNum(int productNum) {
            this.productNum = productNum;
        }

        public int getBusinessType() {
            return businessType;
        }

        public void setBusinessType(int businessType) {
            this.businessType = businessType;
        }

        public Object getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(Object checkStatus) {
            this.checkStatus = checkStatus;
        }

        public Object getTeamProdList() {
            return teamProdList;
        }

        public void setTeamProdList(Object teamProdList) {
            this.teamProdList = teamProdList;
        }

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

        public int getDays() {
            return days;
        }

        public void setDays(int days) {
            this.days = days;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public Object getReturnProductMainId() {
            return returnProductMainId;
        }

        public void setReturnProductMainId(Object returnProductMainId) {
            this.returnProductMainId = returnProductMainId;
        }

        public Object getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(Object oldPrice) {
            this.oldPrice = oldPrice;
        }

        public Object getProdTypeUrl() {
            return prodTypeUrl;
        }

        public void setProdTypeUrl(Object prodTypeUrl) {
            this.prodTypeUrl = prodTypeUrl;
        }

        public Object getFlagUrl() {
            return flagUrl;
        }

        public void setFlagUrl(Object flagUrl) {
            this.flagUrl = flagUrl;
        }

        public Object getInventory() {
            return inventory;
        }

        public void setInventory(Object inventory) {
            this.inventory = inventory;
        }

        public Object getProductPl() {
            return productPl;
        }

        public void setProductPl(Object productPl) {
            this.productPl = productPl;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public String getReturnState() {
            return returnState;
        }

        public void setReturnState(String returnState) {
            this.returnState = returnState;
        }

        public String getReturnNum() {
            return returnNum;
        }

        public void setReturnNum(String returnNum) {
            this.returnNum = returnNum;
        }

        public Object getReturnLowNum() {
            return returnLowNum;
        }

        public void setReturnLowNum(Object returnLowNum) {
            this.returnLowNum = returnLowNum;
        }

        public String getLocationNo() {
            return locationNo;
        }

        public void setLocationNo(String locationNo) {
            this.locationNo = locationNo;
        }

        public Object getCartAdditionProductVOList() {
            return cartAdditionProductVOList;
        }

        public void setCartAdditionProductVOList(Object cartAdditionProductVOList) {
            this.cartAdditionProductVOList = cartAdditionProductVOList;
        }

        public Object getAdditionVOList() {
            return additionVOList;
        }

        public void setAdditionVOList(Object additionVOList) {
            this.additionVOList = additionVOList;
        }

        public Object getOnShelves() {
            return onShelves;
        }

        public void setOnShelves(Object onShelves) {
            this.onShelves = onShelves;
        }

        public Object getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Object companyId) {
            this.companyId = companyId;
        }

        public int getSelfOrNot() {
            return selfOrNot;
        }

        public void setSelfOrNot(int selfOrNot) {
            this.selfOrNot = selfOrNot;
        }

        public String getSendTimeTpl() {
            return sendTimeTpl;
        }

        public void setSendTimeTpl(String sendTimeTpl) {
            this.sendTimeTpl = sendTimeTpl;
        }

        public Object getProdFrom() {
            return prodFrom;
        }

        public void setProdFrom(Object prodFrom) {
            this.prodFrom = prodFrom;
        }

        public List<ProductDescVOListBean> getProductDescVOList() {
            return productDescVOList;
        }

        public void setProductDescVOList(List<ProductDescVOListBean> productDescVOList) {
            this.productDescVOList = productDescVOList;
        }

        public static class ProductDescVOListBean {
            /**
             * detailDesc : null
             * totalNum : X1
             * productNum : 1
             * productCombinationPriceId : 91660
             * productUnit : 3203
             * price : 19.00
             * afterPrice :
             * unit : null
             * newDesc : null
             * totalPrice : null
             * costPrice : null
             */

            private Object detailDesc;
            private String totalNum;
            private int productNum;
            private int productCombinationPriceId;
            private String productUnit;
            private String price;
            private String afterPrice;
            private Object unit;
            private Object newDesc;
            private Object totalPrice;
            private Object costPrice;

            public Object getDetailDesc() {
                return detailDesc;
            }

            public void setDetailDesc(Object detailDesc) {
                this.detailDesc = detailDesc;
            }

            public String getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(String totalNum) {
                this.totalNum = totalNum;
            }

            public int getProductNum() {
                return productNum;
            }

            public void setProductNum(int productNum) {
                this.productNum = productNum;
            }

            public int getProductCombinationPriceId() {
                return productCombinationPriceId;
            }

            public void setProductCombinationPriceId(int productCombinationPriceId) {
                this.productCombinationPriceId = productCombinationPriceId;
            }

            public String getProductUnit() {
                return productUnit;
            }

            public void setProductUnit(String productUnit) {
                this.productUnit = productUnit;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getAfterPrice() {
                return afterPrice;
            }

            public void setAfterPrice(String afterPrice) {
                this.afterPrice = afterPrice;
            }

            public Object getUnit() {
                return unit;
            }

            public void setUnit(Object unit) {
                this.unit = unit;
            }

            public Object getNewDesc() {
                return newDesc;
            }

            public void setNewDesc(Object newDesc) {
                this.newDesc = newDesc;
            }

            public Object getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(Object totalPrice) {
                this.totalPrice = totalPrice;
            }

            public Object getCostPrice() {
                return costPrice;
            }

            public void setCostPrice(Object costPrice) {
                this.costPrice = costPrice;
            }
        }
    }
}
