package com.puyue.www.qiaoge.model.cart;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/9
 */
public class CartTestModel {

    /**
     * code : 1
     * message : 成功
     * data : {"sendAmount":230,"prods":[{"fullId":248,"fullType":1,"tips":"已享受5次满赠，可继续加购","sendFullType":1,"valid":true,"additions":[{"type":0,"productId":9137,"productMainId":8617,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"优惠券测试-2","spec":"规格-1","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","sendNum":null,"sendNumDesc":"5箱","amount":null,"additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0},{"type":1,"productId":null,"productMainId":null,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"5元优惠券","spec":null,"picUrl":null,"sendNum":null,"sendNumDesc":"5张","amount":"5","additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}],"prods":[{"cartId":1664589,"productName":"优惠券测试-2","spec":"规格-1","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":1,"productMainId":8617,"businessId":9137,"selfOrNot":0,"productDescVOList":[{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}],"valid":true}]},{"fullId":null,"fullType":0,"tips":null,"sendFullType":0,"valid":false,"additions":null,"prods":[{"cartId":null,"productName":"优惠券测试-2","spec":null,"flagUrl":null,"defaultPic":null,"businessType":null,"productMainId":null,"businessId":null,"selfOrNot":0,"productDescVOList":null,"valid":false}]}]}
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
         * sendAmount : 230.0
         * prods : [{"fullId":248,"fullType":1,"tips":"已享受5次满赠，可继续加购","sendFullType":1,"valid":true,"additions":[{"type":0,"productId":9137,"productMainId":8617,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"优惠券测试-2","spec":"规格-1","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","sendNum":null,"sendNumDesc":"5箱","amount":null,"additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0},{"type":1,"productId":null,"productMainId":null,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"5元优惠券","spec":null,"picUrl":null,"sendNum":null,"sendNumDesc":"5张","amount":"5","additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}],"prods":[{"cartId":1664589,"productName":"优惠券测试-2","spec":"规格-1","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":1,"productMainId":8617,"businessId":9137,"selfOrNot":0,"productDescVOList":[{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}],"valid":true}]},{"fullId":null,"fullType":0,"tips":null,"sendFullType":0,"valid":false,"additions":null,"prods":[{"cartId":null,"productName":"优惠券测试-2","spec":null,"flagUrl":null,"defaultPic":null,"businessType":null,"productMainId":null,"businessId":null,"selfOrNot":0,"productDescVOList":null,"valid":false}]}]
         */

        private double sendAmount;
        private List<ProdsBeanX> prods;
//        private List<InValidProdBean> inValidProd;
        InValidProdBean inValidProd;

        @Override
        public String toString() {
            return "DataBean{" +
                    "sendAmount=" + sendAmount +
                    ", prods=" + prods +
                    ", inValidProdBean=" + inValidProd +
                    '}';
        }

        public InValidProdBean getInValidProdBean() {
            return inValidProd;
        }

        public void setInValidProdBean(InValidProdBean inValidProd) {
            this.inValidProd = inValidProd;
        }
//        public List<InValidProdBean> getInValidProd() {
//            return inValidProd;
//        }
//
//        public void setInValidProd(List<InValidProdBean> inValidProd) {
//            this.inValidProd = inValidProd;
//        }

        public double getSendAmount() {
            return sendAmount;
        }

        public void setSendAmount(double sendAmount) {
            this.sendAmount = sendAmount;
        }

        public List<ProdsBeanX> getProds() {
            return prods;
        }

        public void setProds(List<ProdsBeanX> prods) {
            this.prods = prods;
        }

        public static class ProdsBeanX {
            /**
             * fullId : 248
             * fullType : 1
             * tips : 已享受5次满赠，可继续加购
             * sendFullType : 1
             * valid : true
             * additions : [{"type":0,"productId":9137,"productMainId":8617,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"优惠券测试-2","spec":"规格-1","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","sendNum":null,"sendNumDesc":"5箱","amount":null,"additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0},{"type":1,"productId":null,"productMainId":null,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"5元优惠券","spec":null,"picUrl":null,"sendNum":null,"sendNumDesc":"5张","amount":"5","additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}]
             * prods : [{"cartId":1664589,"productName":"优惠券测试-2","spec":"规格-1","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":1,"productMainId":8617,"businessId":9137,"selfOrNot":0,"productDescVOList":[{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}],"valid":true}]
             */

            private int fullId;
            private int fullType;
            private String tips;
            private int sendFullType;
            private boolean valid;
            private List<AdditionsBean> additions;
            private List<ProdsBean> prods;
            boolean isSelect = true;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            @Override
            public String toString() {
                return "ProdsBeanX{" +
                        "fullId=" + fullId +
                        ", fullType=" + fullType +
                        ", tips='" + tips + '\'' +
                        ", sendFullType=" + sendFullType +
                        ", valid=" + valid +
                        ", additions=" + additions +
                        ", prods=" + prods +
                        ", isSelect=" + isSelect +
                        '}';
            }

            public int getFullId() {
                return fullId;
            }

            public void setFullId(int fullId) {
                this.fullId = fullId;
            }

            public int getFullType() {
                return fullType;
            }

            public void setFullType(int fullType) {
                this.fullType = fullType;
            }

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public int getSendFullType() {
                return sendFullType;
            }

            public void setSendFullType(int sendFullType) {
                this.sendFullType = sendFullType;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public List<AdditionsBean> getAdditions() {
                return additions;
            }

            public void setAdditions(List<AdditionsBean> additions) {
                this.additions = additions;
            }

            public List<ProdsBean> getProds() {
                return prods;
            }

            public void setProds(List<ProdsBean> prods) {
                this.prods = prods;
            }

            public static class AdditionsBean {
                @Override
                public String toString() {
                    return "AdditionsBean{" +
                            "type=" + type +
                            ", productId=" + productId +
                            ", productMainId=" + productMainId +
                            ", giftPoolNo=" + giftPoolNo +
                            ", warehouseId=" + warehouseId +
                            ", productUnit=" + productUnit +
                            ", name='" + name + '\'' +
                            ", spec='" + spec + '\'' +
                            ", picUrl='" + picUrl + '\'' +
                            ", sendNum=" + sendNum +
                            ", sendNumDesc='" + sendNumDesc + '\'' +
                            ", amount=" + amount +
                            ", additionFlag=" + additionFlag +
                            ", triggerNum=" + triggerNum +
                            ", flagUrl='" + flagUrl + '\'' +
                            ", finishUrl='" + finishUrl + '\'' +
                            ", supplierId=" + supplierId +
                            '}';
                }

                /**
                 * type : 0
                 * productId : 9137
                 * productMainId : 8617
                 * giftPoolNo : null
                 * warehouseId : null
                 * productUnit : null
                 * name : 优惠券测试-2
                 * spec : 规格-1
                 * picUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * sendNum : null
                 * sendNumDesc : 5箱
                 * amount : null
                 * additionFlag : 1
                 * triggerNum : null
                 * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png
                 * finishUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png
                 * supplierId : 0
                 */

                private int type;
                private int productId;
                private int productMainId;
                private String giftPoolNo;
                private Object warehouseId;
                private Object productUnit;
                private String name;
                private String spec;
                private String picUrl;
                private Object sendNum;
                private String sendNumDesc;
                private Object amount;
                private int additionFlag;
                private Object triggerNum;
                private String flagUrl;
                private String finishUrl;
                private int supplierId;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public String getGiftPoolNo() {
                    return giftPoolNo;
                }

                public void setGiftPoolNo(String giftPoolNo) {
                    this.giftPoolNo = giftPoolNo;
                }

                public Object getWarehouseId() {
                    return warehouseId;
                }

                public void setWarehouseId(Object warehouseId) {
                    this.warehouseId = warehouseId;
                }

                public Object getProductUnit() {
                    return productUnit;
                }

                public void setProductUnit(Object productUnit) {
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

                public Object getSendNum() {
                    return sendNum;
                }

                public void setSendNum(Object sendNum) {
                    this.sendNum = sendNum;
                }

                public String getSendNumDesc() {
                    return sendNumDesc;
                }

                public void setSendNumDesc(String sendNumDesc) {
                    this.sendNumDesc = sendNumDesc;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public int getAdditionFlag() {
                    return additionFlag;
                }

                public void setAdditionFlag(int additionFlag) {
                    this.additionFlag = additionFlag;
                }

                public Object getTriggerNum() {
                    return triggerNum;
                }

                public void setTriggerNum(Object triggerNum) {
                    this.triggerNum = triggerNum;
                }

                public String getFlagUrl() {
                    return flagUrl;
                }

                public void setFlagUrl(String flagUrl) {
                    this.flagUrl = flagUrl;
                }

                public String getFinishUrl() {
                    return finishUrl;
                }

                public void setFinishUrl(String finishUrl) {
                    this.finishUrl = finishUrl;
                }

                public int getSupplierId() {
                    return supplierId;
                }

                public void setSupplierId(int supplierId) {
                    this.supplierId = supplierId;
                }
            }

            public static class ProdsBean {
                /**
                 * cartId : 1664589
                 * productName : 优惠券测试-2
                 * spec : 规格-1
                 * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * businessType : 1
                 * productMainId : 8617
                 * businessId : 9137
                 * selfOrNot : 0
                 * productDescVOList : [{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}]
                 * valid : true
                 */

                private int cartId;
                private String productName;
                private String spec;
                private String flagUrl;
                private String defaultPic;
                private int businessType;
                private int productMainId;
                private int businessId;
                private int selfOrNot;
                private boolean valid;
                boolean isSelected = true;
                String notSend;
                String selfProd;
                String prodBuyTips;
                @Override
                public String toString() {
                    return "ProdsBean{" +
                            "cartId=" + cartId +
                            ", productName='" + productName + '\'' +
                            ", spec='" + spec + '\'' +
                            ", flagUrl='" + flagUrl + '\'' +
                            ", defaultPic='" + defaultPic + '\'' +
                            ", businessType=" + businessType +
                            ", productMainId=" + productMainId +
                            ", businessId=" + businessId +
                            ", selfOrNot=" + selfOrNot +
                            ", valid=" + valid +
                            ", isSelected=" + isSelected +
                            ", productDescVOList=" + productDescVOList +
                            '}';
                }

                public String getProdBuyTips() {
                    return prodBuyTips;
                }

                public void setProdBuyTips(String prodBuyTips) {
                    this.prodBuyTips = prodBuyTips;
                }

                public String getSelfProd() {
                    return selfProd;
                }

                public void setSelfProd(String selfProd) {
                    this.selfProd = selfProd;
                }

                public String getNotSend() {
                    return notSend;
                }

                public void setNotSend(String notSend) {
                    this.notSend = notSend;
                }

                private List<ProductDescVOListBean> productDescVOList;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public int getCartId() {
                    return cartId;
                }

                public void setCartId(int cartId) {
                    this.cartId = cartId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public String getFlagUrl() {
                    return flagUrl;
                }

                public void setFlagUrl(String flagUrl) {
                    this.flagUrl = flagUrl;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public int getBusinessType() {
                    return businessType;
                }

                public void setBusinessType(int businessType) {
                    this.businessType = businessType;
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public int getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(int businessId) {
                    this.businessId = businessId;
                }

                public int getSelfOrNot() {
                    return selfOrNot;
                }

                public void setSelfOrNot(int selfOrNot) {
                    this.selfOrNot = selfOrNot;
                }

                public boolean isValid() {
                    return valid;
                }

                public void setValid(boolean valid) {
                    this.valid = valid;
                }

                public List<ProductDescVOListBean> getProductDescVOList() {
                    return productDescVOList;
                }

                public void setProductDescVOList(List<ProductDescVOListBean> productDescVOList) {
                    this.productDescVOList = productDescVOList;
                }

                public static class ProductDescVOListBean {
                    /**
                     * price : 200.00
                     * priceStr : ￥200.00/箱
                     * unit : 18298
                     * highNum : 1
                     * productNum : 5
                     * unitDesc :
                     * productCombinationPriceId : 4500
                     * oldPrice : null
                     */

                    private String price;
                    private String priceStr;
                    private int unit;
                    private int highNum;
                    private int productNum;
                    private String unitDesc;
                    private int productCombinationPriceId;
                    private String oldPrice;

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPriceStr() {
                        return priceStr;
                    }

                    public void setPriceStr(String priceStr) {
                        this.priceStr = priceStr;
                    }

                    public int getUnit() {
                        return unit;
                    }

                    public void setUnit(int unit) {
                        this.unit = unit;
                    }

                    public int getHighNum() {
                        return highNum;
                    }

                    public void setHighNum(int highNum) {
                        this.highNum = highNum;
                    }

                    public int getProductNum() {
                        return productNum;
                    }

                    public void setProductNum(int productNum) {
                        this.productNum = productNum;
                    }

                    public String getUnitDesc() {
                        return unitDesc;
                    }

                    public void setUnitDesc(String unitDesc) {
                        this.unitDesc = unitDesc;
                    }

                    public int getProductCombinationPriceId() {
                        return productCombinationPriceId;
                    }

                    public void setProductCombinationPriceId(int productCombinationPriceId) {
                        this.productCombinationPriceId = productCombinationPriceId;
                    }

                    public String getOldPrice() {
                        return oldPrice;
                    }

                    public void setOldPrice(String oldPrice) {
                        this.oldPrice = oldPrice;
                    }
                }
            }
        }

        public static class InValidProdBean {
            /**
             * fullId : 248
             * fullType : 1
             * tips : 已享受5次满赠，可继续加购
             * sendFullType : 1
             * valid : true
             * additions : [{"type":0,"productId":9137,"productMainId":8617,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"优惠券测试-2","spec":"规格-1","picUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","sendNum":null,"sendNumDesc":"5箱","amount":null,"additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0},{"type":1,"productId":null,"productMainId":null,"giftPoolNo":null,"warehouseId":null,"productUnit":null,"name":"5元优惠券","spec":null,"picUrl":null,"sendNum":null,"sendNumDesc":"5张","amount":"5","additionFlag":1,"triggerNum":null,"flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png","finishUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png","supplierId":0}]
             * prods : [{"cartId":1664589,"productName":"优惠券测试-2","spec":"规格-1","flagUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","businessType":1,"productMainId":8617,"businessId":9137,"selfOrNot":0,"productDescVOList":[{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}],"valid":true}]
             */

            private int fullId;
            private int fullType;
            private String tips;
            private int sendFullType;
            private boolean valid;
            private List<AdditionsBean> additions;
            private List<ProdsBean> prods;
            boolean isSelect = true;

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            @Override
            public String toString() {
                return "ProdsBeanX{" +
                        "fullId=" + fullId +
                        ", fullType=" + fullType +
                        ", tips='" + tips + '\'' +
                        ", sendFullType=" + sendFullType +
                        ", valid=" + valid +
                        ", additions=" + additions +
                        ", prods=" + prods +
                        ", isSelect=" + isSelect +
                        '}';
            }

            public int getFullId() {
                return fullId;
            }

            public void setFullId(int fullId) {
                this.fullId = fullId;
            }

            public int getFullType() {
                return fullType;
            }

            public void setFullType(int fullType) {
                this.fullType = fullType;
            }

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public int getSendFullType() {
                return sendFullType;
            }

            public void setSendFullType(int sendFullType) {
                this.sendFullType = sendFullType;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public List<AdditionsBean> getAdditions() {
                return additions;
            }

            public void setAdditions(List<AdditionsBean> additions) {
                this.additions = additions;
            }

            public List<ProdsBean> getProds() {
                return prods;
            }

            public void setProds(List<ProdsBean> prods) {
                this.prods = prods;
            }

            public static class AdditionsBean {
                @Override
                public String toString() {
                    return "AdditionsBean{" +
                            "type=" + type +
                            ", productId=" + productId +
                            ", productMainId=" + productMainId +
                            ", giftPoolNo=" + giftPoolNo +
                            ", warehouseId=" + warehouseId +
                            ", productUnit=" + productUnit +
                            ", name='" + name + '\'' +
                            ", spec='" + spec + '\'' +
                            ", picUrl='" + picUrl + '\'' +
                            ", sendNum=" + sendNum +
                            ", sendNumDesc='" + sendNumDesc + '\'' +
                            ", amount=" + amount +
                            ", additionFlag=" + additionFlag +
                            ", triggerNum=" + triggerNum +
                            ", flagUrl='" + flagUrl + '\'' +
                            ", finishUrl='" + finishUrl + '\'' +
                            ", supplierId=" + supplierId +
                            '}';
                }

                /**
                 * type : 0
                 * productId : 9137
                 * productMainId : 8617
                 * giftPoolNo : null
                 * warehouseId : null
                 * productUnit : null
                 * name : 优惠券测试-2
                 * spec : 规格-1
                 * picUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * sendNum : null
                 * sendNumDesc : 5箱
                 * amount : null
                 * additionFlag : 1
                 * triggerNum : null
                 * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/229bf97481604d8db98e3e898e156016.png
                 * finishUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9af515c0365045d5873666237fd580ab.png
                 * supplierId : 0
                 */

                private int type;
                private int productId;
                private int productMainId;
                private Object giftPoolNo;
                private Object warehouseId;
                private Object productUnit;
                private String name;
                private String spec;
                private String picUrl;
                private Object sendNum;
                private String sendNumDesc;
                private Object amount;
                private int additionFlag;
                private Object triggerNum;
                private String flagUrl;
                private String finishUrl;
                private int supplierId;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public Object getGiftPoolNo() {
                    return giftPoolNo;
                }

                public void setGiftPoolNo(Object giftPoolNo) {
                    this.giftPoolNo = giftPoolNo;
                }

                public Object getWarehouseId() {
                    return warehouseId;
                }

                public void setWarehouseId(Object warehouseId) {
                    this.warehouseId = warehouseId;
                }

                public Object getProductUnit() {
                    return productUnit;
                }

                public void setProductUnit(Object productUnit) {
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

                public Object getSendNum() {
                    return sendNum;
                }

                public void setSendNum(Object sendNum) {
                    this.sendNum = sendNum;
                }

                public String getSendNumDesc() {
                    return sendNumDesc;
                }

                public void setSendNumDesc(String sendNumDesc) {
                    this.sendNumDesc = sendNumDesc;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public int getAdditionFlag() {
                    return additionFlag;
                }

                public void setAdditionFlag(int additionFlag) {
                    this.additionFlag = additionFlag;
                }

                public Object getTriggerNum() {
                    return triggerNum;
                }

                public void setTriggerNum(Object triggerNum) {
                    this.triggerNum = triggerNum;
                }

                public String getFlagUrl() {
                    return flagUrl;
                }

                public void setFlagUrl(String flagUrl) {
                    this.flagUrl = flagUrl;
                }

                public String getFinishUrl() {
                    return finishUrl;
                }

                public void setFinishUrl(String finishUrl) {
                    this.finishUrl = finishUrl;
                }

                public int getSupplierId() {
                    return supplierId;
                }

                public void setSupplierId(int supplierId) {
                    this.supplierId = supplierId;
                }
            }

            public static class ProdsBean {
                /**
                 * cartId : 1664589
                 * productName : 优惠券测试-2
                 * spec : 规格-1
                 * flagUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/hylApp/hylmz%402x.png
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
                 * businessType : 1
                 * productMainId : 8617
                 * businessId : 9137
                 * selfOrNot : 0
                 * productDescVOList : [{"price":"200.00","priceStr":"￥200.00/箱","unit":18298,"highNum":1,"productNum":5,"unitDesc":"","productCombinationPriceId":4500,"oldPrice":null}]
                 * valid : true
                 */

                private int cartId;
                private String productName;
                private String spec;
                private String flagUrl;
                private String defaultPic;
                private int businessType;
                private int productMainId;
                private int businessId;
                private int selfOrNot;
                private boolean valid;
                boolean isSelected = true;

                @Override
                public String toString() {
                    return "ProdsBean{" +
                            "cartId=" + cartId +
                            ", productName='" + productName + '\'' +
                            ", spec='" + spec + '\'' +
                            ", flagUrl='" + flagUrl + '\'' +
                            ", defaultPic='" + defaultPic + '\'' +
                            ", businessType=" + businessType +
                            ", productMainId=" + productMainId +
                            ", businessId=" + businessId +
                            ", selfOrNot=" + selfOrNot +
                            ", valid=" + valid +
                            ", isSelected=" + isSelected +
                            ", productDescVOList=" + productDescVOList +
                            '}';
                }

                private List<ProductDescVOListBean> productDescVOList;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

                public int getCartId() {
                    return cartId;
                }

                public void setCartId(int cartId) {
                    this.cartId = cartId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }

                public String getFlagUrl() {
                    return flagUrl;
                }

                public void setFlagUrl(String flagUrl) {
                    this.flagUrl = flagUrl;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public int getBusinessType() {
                    return businessType;
                }

                public void setBusinessType(int businessType) {
                    this.businessType = businessType;
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public int getBusinessId() {
                    return businessId;
                }

                public void setBusinessId(int businessId) {
                    this.businessId = businessId;
                }

                public int getSelfOrNot() {
                    return selfOrNot;
                }

                public void setSelfOrNot(int selfOrNot) {
                    this.selfOrNot = selfOrNot;
                }

                public boolean isValid() {
                    return valid;
                }

                public void setValid(boolean valid) {
                    this.valid = valid;
                }

                public List<ProductDescVOListBean> getProductDescVOList() {
                    return productDescVOList;
                }

                public void setProductDescVOList(List<ProductDescVOListBean> productDescVOList) {
                    this.productDescVOList = productDescVOList;
                }

                public static class ProductDescVOListBean {
                    /**
                     * price : 200.00
                     * priceStr : ￥200.00/箱
                     * unit : 18298
                     * highNum : 1
                     * productNum : 5
                     * unitDesc :
                     * productCombinationPriceId : 4500
                     * oldPrice : null
                     */

                    private String price;
                    private String priceStr;
                    private int unit;
                    private int highNum;
                    private int productNum;
                    private String unitDesc;
                    private int productCombinationPriceId;
                    private String oldPrice;

                    public String getPrice() {
                        return price;
                    }

                    public void setPrice(String price) {
                        this.price = price;
                    }

                    public String getPriceStr() {
                        return priceStr;
                    }

                    public void setPriceStr(String priceStr) {
                        this.priceStr = priceStr;
                    }

                    public int getUnit() {
                        return unit;
                    }

                    public void setUnit(int unit) {
                        this.unit = unit;
                    }

                    public int getHighNum() {
                        return highNum;
                    }

                    public void setHighNum(int highNum) {
                        this.highNum = highNum;
                    }

                    public int getProductNum() {
                        return productNum;
                    }

                    public void setProductNum(int productNum) {
                        this.productNum = productNum;
                    }

                    public String getUnitDesc() {
                        return unitDesc;
                    }

                    public void setUnitDesc(String unitDesc) {
                        this.unitDesc = unitDesc;
                    }

                    public int getProductCombinationPriceId() {
                        return productCombinationPriceId;
                    }

                    public void setProductCombinationPriceId(int productCombinationPriceId) {
                        this.productCombinationPriceId = productCombinationPriceId;
                    }

                    public String getOldPrice() {
                        return oldPrice;
                    }

                    public void setOldPrice(String oldPrice) {
                        this.oldPrice = oldPrice;
                    }
                }
            }
        }

    }
}
