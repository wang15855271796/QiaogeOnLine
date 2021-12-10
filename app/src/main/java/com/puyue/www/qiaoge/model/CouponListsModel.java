package com.puyue.www.qiaoge.model;

import java.util.List;

public class CouponListsModel {

    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":1,"list":[{"type":"批发","activeId":null,"productMainId":8706,"buyFlag":"","productId":9220,"productName":"冷冻鸡腿2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"10包/箱","salesVolume":"销量：320","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":9220,"spec":"10包/箱","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：134箱","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/icon_operate.png","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}],"hasPrePage":false,"hasNextPage":false}
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
         * pageNum : 1
         * pageSize : 10
         * startRow : 0
         * pages : 1
         * total : 1
         * list : [{"type":"批发","activeId":null,"productMainId":8706,"buyFlag":"","productId":9220,"productName":"冷冻鸡腿2","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","flag":1,"businessType":1,"typeUrl":null,"spec":"10包/箱","salesVolume":"销量：320","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":9220,"spec":"10包/箱","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：134箱","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/icon_operate.png","sendTimeTpl":"","companyId":null,"notSend":0,"unitName":"","amount":null,"saleNum":null}]
         * hasPrePage : false
         * hasNextPage : false
         */

        private int pageNum;
        private int pageSize;
        private int startRow;
        private int pages;
        private int total;
        private boolean hasPrePage;
        private boolean hasNextPage;
        private List<ListBean> list;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public boolean isHasPrePage() {
            return hasPrePage;
        }

        public void setHasPrePage(boolean hasPrePage) {
            this.hasPrePage = hasPrePage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * type : 批发
             * activeId : null
             * productMainId : 8706
             * buyFlag :
             * productId : 9220
             * productName : 冷冻鸡腿2
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png
             * flag : 1
             * businessType : 1
             * typeUrl : null
             * spec : 10包/箱
             * salesVolume : 销量：320
             * minMaxPrice : ￥***
             * specialOffer :
             * prodSpecs : [{"productId":9220,"spec":"10包/箱","prodDeduct":0,"prodFullGift":0}]
             * inventory : 库存：134箱
             * prodPrices : null
             * deductAmount :
             * selfProd : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/icon_operate.png
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
            private String salesVolume;
            private String minMaxPrice;
            private String specialOffer;
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
            private List<ProdSpecsBean> prodSpecs;

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

            public String getSalesVolume() {
                return salesVolume;
            }

            public void setSalesVolume(String salesVolume) {
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

            public List<ProdSpecsBean> getProdSpecs() {
                return prodSpecs;
            }

            public void setProdSpecs(List<ProdSpecsBean> prodSpecs) {
                this.prodSpecs = prodSpecs;
            }

            public static class ProdSpecsBean {
                /**
                 * productId : 9220
                 * spec : 10包/箱
                 * prodDeduct : 0
                 * prodFullGift : 0
                 */

                private int productId;
                private String spec;
                private int prodDeduct;
                private int prodFullGift;

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

                public int getProdDeduct() {
                    return prodDeduct;
                }

                public void setProdDeduct(int prodDeduct) {
                    this.prodDeduct = prodDeduct;
                }

                public int getProdFullGift() {
                    return prodFullGift;
                }

                public void setProdFullGift(int prodFullGift) {
                    this.prodFullGift = prodFullGift;
                }
            }
        }
    }
}
