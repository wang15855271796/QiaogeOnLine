package com.puyue.www.qiaoge.model;

import com.puyue.www.qiaoge.model.home.ProductNormalModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"roleDesc":"购买以下商品满1箱,赠（不限次数）：","sendGifts":[{"giftName":"优惠券测试-2规格-1","sendNum":"1箱","type":0,"poolNo":null},{"giftName":"不予秒杀同享优惠券","sendNum":"1张","type":1,"poolNo":"1002021070300000213"}],"prods":[{"type":"批发","activeId":null,"productMainId":8617,"buyFlag":"","productId":9137,"productName":"优惠券测试-2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"规格-1","salesVolume":null,"minMaxPrice":null,"specialOffer":"","prodSpecs":null,"inventory":"","prodPrices":null,"deductAmount":"","selfProd":"","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}],"tips":{"roleType":0,"tips":"提示:再买1箱可享受满赠","buyNum":"0","buyAmt":"￥0"}}
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
         * roleDesc : 购买以下商品满1箱,赠（不限次数）：
         * sendGifts : [{"giftName":"优惠券测试-2规格-1","sendNum":"1箱","type":0,"poolNo":null},{"giftName":"不予秒杀同享优惠券","sendNum":"1张","type":1,"poolNo":"1002021070300000213"}]
         * prods : [{"type":"批发","activeId":null,"productMainId":8617,"buyFlag":"","productId":9137,"productName":"优惠券测试-2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"规格-1","salesVolume":null,"minMaxPrice":null,"specialOffer":"","prodSpecs":null,"inventory":"","prodPrices":null,"deductAmount":"","selfProd":"","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}]
         * tips : {"roleType":0,"tips":"提示:再买1箱可享受满赠","buyNum":"0","buyAmt":"￥0"}
         */

        private String roleDesc;
        private TipsBean tips;
        private List<SendGiftsBean> sendGifts;
        private List<ProdsBean> prods;

        public String getRoleDesc() {
            return roleDesc;
        }

        public void setRoleDesc(String roleDesc) {
            this.roleDesc = roleDesc;
        }

        public TipsBean getTips() {
            return tips;
        }

        public void setTips(TipsBean tips) {
            this.tips = tips;
        }

        public List<SendGiftsBean> getSendGifts() {
            return sendGifts;
        }

        public void setSendGifts(List<SendGiftsBean> sendGifts) {
            this.sendGifts = sendGifts;
        }

        public List<ProdsBean> getProds() {
            return prods;
        }

        public void setProds(List<ProdsBean> prods) {
            this.prods = prods;
        }

        public static class TipsBean {
            /**
             * roleType : 0
             * tips : 提示:再买1箱可享受满赠
             * buyNum : 0
             * buyAmt : ￥0
             */

            private int roleType;
            private String tips;
            private String buyNum;
            private String buyAmt;


            public int getRoleType() {
                return roleType;
            }

            public void setRoleType(int roleType) {
                this.roleType = roleType;
            }

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getBuyAmt() {
                return buyAmt;
            }

            public void setBuyAmt(String buyAmt) {
                this.buyAmt = buyAmt;
            }
        }

        public static class SendGiftsBean implements Serializable {
            /**
             * giftName : 优惠券测试-2规格-1
             * sendNum : 1箱
             * type : 0
             * poolNo : null
             */

            private String giftName;
            private String sendNum;
            private int type;
            private String poolNo;
            int productMainId;
            String name;
            String spec;
            String picture;
            String amount;
            String useInfo;
            List<String> role;
            String giftProdUseType;
            String dateTime;
            List<String> giftUseRole;

            public List<String> getGiftUseRole() {
                return giftUseRole;
            }

            public void setGiftUseRole(List<String> giftUseRole) {
                this.giftUseRole = giftUseRole;
            }

            public List<String> getRole() {
                return role;
            }

            public void setRole(List<String> role) {
                this.role = role;
            }

            public String getUseInfo() {
                return useInfo;
            }

            public void setUseInfo(String useInfo) {
                this.useInfo = useInfo;
            }

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
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

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGiftProdUseType() {
                return giftProdUseType;
            }

            public void setGiftProdUseType(String giftProdUseType) {
                this.giftProdUseType = giftProdUseType;
            }

            public int getProductMainId() {
                return productMainId;
            }

            public void setProductMainId(int productMainId) {
                this.productMainId = productMainId;
            }
            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public String getSendNum() {
                return sendNum;
            }

            public void setSendNum(String sendNum) {
                this.sendNum = sendNum;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPoolNo() {
                return poolNo;
            }

            public void setPoolNo(String poolNo) {
                this.poolNo = poolNo;
            }
        }

        public static class ProdsBean {
            /**
             * type : 批发
             * activeId : null
             * productMainId : 8617
             * buyFlag :
             * productId : 9137
             * productName : 优惠券测试-2
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
             * flag : 1
             * businessType : 1
             * typeUrl : null
             * spec : 规格-1
             * salesVolume : null
             * minMaxPrice : null
             * specialOffer :
             * prodSpecs : null
             * inventory :
             * prodPrices : null
             * deductAmount :
             * selfProd :
             * sendTimeTpl :
             * companyId : null
             * notSend : 0
             * unitName :
             * amount : null
             * saleNum : null
             */

            private String type;
            private Object activeId;
            private int productMainId;
            private String buyFlag;
            private int productId;
            private String productName;
            private String defaultPic;
            private int flag;
            private int businessType;
            private Object typeUrl;
            private String spec;
            private Object salesVolume;
            private String minMaxPrice;
            private String specialOffer;
            private List<ProdSpecsBean> prodSpecs;
            private String inventory;
            private Object prodPrices;
            private String deductAmount;
            private String selfProd;
            private String sendTimeTpl;
            private Object companyId;
            private int notSend;
            private String unitName;
            private Object amount;
            private Object saleNum;

            public List<ProdSpecsBean> getProdSpecs() {
                return prodSpecs;
            }

            public void setProdSpecs(List<ProdSpecsBean> prodSpecs) {
                this.prodSpecs = prodSpecs;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public Object getActiveId() {
                return activeId;
            }

            public void setActiveId(Object activeId) {
                this.activeId = activeId;
            }

            public int getProductMainId() {
                return productMainId;
            }

            public void setProductMainId(int productMainId) {
                this.productMainId = productMainId;
            }

            public String getBuyFlag() {
                return buyFlag;
            }

            public void setBuyFlag(String buyFlag) {
                this.buyFlag = buyFlag;
            }

            public int getProductId() {
                return productId;
            }

            public void setProductId(int productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getBusinessType() {
                return businessType;
            }

            public void setBusinessType(int businessType) {
                this.businessType = businessType;
            }

            public Object getTypeUrl() {
                return typeUrl;
            }

            public void setTypeUrl(Object typeUrl) {
                this.typeUrl = typeUrl;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public Object getSalesVolume() {
                return salesVolume;
            }

            public void setSalesVolume(Object salesVolume) {
                this.salesVolume = salesVolume;
            }

            public String getMinMaxPrice() {
                return minMaxPrice;
            }

            public void setMinMaxPrice(String minMaxPrice) {
                this.minMaxPrice = minMaxPrice;
            }

            public String getSpecialOffer() {
                return specialOffer;
            }

            public void setSpecialOffer(String specialOffer) {
                this.specialOffer = specialOffer;
            }


            public String getInventory() {
                return inventory;
            }

            public void setInventory(String inventory) {
                this.inventory = inventory;
            }

            public Object getProdPrices() {
                return prodPrices;
            }

            public void setProdPrices(Object prodPrices) {
                this.prodPrices = prodPrices;
            }

            public String getDeductAmount() {
                return deductAmount;
            }

            public void setDeductAmount(String deductAmount) {
                this.deductAmount = deductAmount;
            }

            public String getSelfProd() {
                return selfProd;
            }

            public void setSelfProd(String selfProd) {
                this.selfProd = selfProd;
            }

            public String getSendTimeTpl() {
                return sendTimeTpl;
            }

            public void setSendTimeTpl(String sendTimeTpl) {
                this.sendTimeTpl = sendTimeTpl;
            }

            public Object getCompanyId() {
                return companyId;
            }

            public void setCompanyId(Object companyId) {
                this.companyId = companyId;
            }

            public int getNotSend() {
                return notSend;
            }

            public void setNotSend(int notSend) {
                this.notSend = notSend;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }

            public Object getAmount() {
                return amount;
            }

            public void setAmount(Object amount) {
                this.amount = amount;
            }

            public Object getSaleNum() {
                return saleNum;
            }

            public void setSaleNum(Object saleNum) {
                this.saleNum = saleNum;
            }

            public static class ProdSpecsBean {
                /**
                 * productId : 345
                 * spec : 12
                 */

                private int productId;
                private String spec;
                int prodDeduct;

                public int getProdDeduct() {
                    return prodDeduct;
                }

                public void setProdDeduct(int prodDeduct) {
                    this.prodDeduct = prodDeduct;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getSpec() {
                    return spec;
                }

                public void setSpec(String spec) {
                    this.spec = spec;
                }
            }

        }
    }
}
