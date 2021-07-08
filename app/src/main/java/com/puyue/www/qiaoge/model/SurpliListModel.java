package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/4/24
 */
public class SurpliListModel {


    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":6,"list":[{"type":"批发","activeId":null,"productMainId":8155,"buyFlag":"","productId":8636,"productName":"三尺长剑","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：1036","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8636,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：68箱5包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8172,"buyFlag":"","productId":8653,"productName":"金木水火土","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/45a23a1637004149ac93a07761d5d9c6.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：1010","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8653,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：17箱6个","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8159,"buyFlag":"","productId":8640,"productName":"一棒定乾坤","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：846","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8640,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：10袋","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8154,"buyFlag":"","productId":8635,"productName":"【孙先生】关联服务商的供应商1","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/891af922f91742fdad215ea3a8d63549.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：697","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8635,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：68箱9包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8281,"buyFlag":"","productId":8777,"productName":"鱼排","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1*10kg、1*15kg","salesVolume":"销量：594","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8777,"spec":"1*10kg","prodDeduct":0,"prodFullGift":0},{"productId":8778,"spec":"1*15kg","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：48箱10包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8199,"buyFlag":"","productId":8680,"productName":"太上老君","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：293","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8680,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：1箱","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null}],"hasPrePage":false,"hasNextPage":false}
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
         * total : 6
         * list : [{"type":"批发","activeId":null,"productMainId":8155,"buyFlag":"","productId":8636,"productName":"三尺长剑","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：1036","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8636,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：68箱5包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8172,"buyFlag":"","productId":8653,"productName":"金木水火土","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/45a23a1637004149ac93a07761d5d9c6.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：1010","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8653,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：17箱6个","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8159,"buyFlag":"","productId":8640,"productName":"一棒定乾坤","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：846","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8640,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：10袋","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8154,"buyFlag":"","productId":8635,"productName":"【孙先生】关联服务商的供应商1","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/891af922f91742fdad215ea3a8d63549.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：697","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8635,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：68箱9包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8281,"buyFlag":"","productId":8777,"productName":"鱼排","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1*10kg、1*15kg","salesVolume":"销量：594","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8777,"spec":"1*10kg","prodDeduct":0,"prodFullGift":0},{"productId":8778,"spec":"1*15kg","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：48箱10包","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null},{"type":"批发","activeId":null,"productMainId":8199,"buyFlag":"","productId":8680,"productName":"太上老君","defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg","flag":1,"businessType":1,"typeUrl":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png","spec":"1kg*10包","salesVolume":"销量：293","minMaxPrice":"￥***","specialOffer":"","prodSpecs":[{"productId":8680,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}],"inventory":"库存：1箱","prodPrices":null,"deductAmount":"","selfProd":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png","sendTimeTpl":"","companyId":26218,"notSend":0,"unitName":"","amount":null,"saleNum":null}]
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
             * productMainId : 8155
             * buyFlag :
             * productId : 8636
             * productName : 三尺长剑
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg
             * flag : 1
             * businessType : 1
             * typeUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//2df7b84f572148c299aa45d1c30c82d8.png
             * spec : 1kg*10包
             * salesVolume : 销量：1036
             * minMaxPrice : ￥***
             * specialOffer :
             * prodSpecs : [{"productId":8636,"spec":"1kg*10包","prodDeduct":0,"prodFullGift":0}]
             * inventory : 库存：68箱5包
             * prodPrices : null
             * deductAmount :
             * selfProd : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/icon/feiziying.png
             * sendTimeTpl :
             * companyId : 26218
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
            private String typeUrl;
            private String spec;
            private String salesVolume;
            private String minMaxPrice;
            private String specialOffer;
            private String inventory;
            private Object prodPrices;
            private String deductAmount;
            private String selfProd;
            private String sendTimeTpl;
            private int companyId;
            private String notSend;
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

            public String getTypeUrl() {
                return typeUrl;
            }

            public void setTypeUrl(String typeUrl) {
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

            public int getCompanyId() {
                return companyId;
            }

            public void setCompanyId(int companyId) {
                this.companyId = companyId;
            }

            public String getNotSend() {
                return notSend;
            }

            public void setNotSend(String notSend) {
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
                 * productId : 8636
                 * spec : 1kg*10包
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
