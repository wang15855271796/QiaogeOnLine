package com.puyue.www.qiaoge.api.mine;

import java.util.List;

public class PointShopModel {

    private Integer code;
    private String message;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
        private List<BannerBean> banner;
        private List<DeductsBean> deducts;
        private Object realGoods;
        private String userPoint;

        @Override
        public String toString() {
            return "DataBean{" +
                    "banner=" + banner +
                    ", deducts=" + deducts +
                    ", realGoods=" + realGoods +
                    ", userPoint='" + userPoint + '\'' +
                    '}';
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<DeductsBean> getDeducts() {
            return deducts;
        }

        public void setDeducts(List<DeductsBean> deducts) {
            this.deducts = deducts;
        }

        public Object getRealGoods() {
            return realGoods;
        }

        public void setRealGoods(Object realGoods) {
            this.realGoods = realGoods;
        }

        public String getUserPoint() {
            return userPoint;
        }

        public void setUserPoint(String userPoint) {
            this.userPoint = userPoint;
        }

        public static class BannerBean {
            private String bannerUrl;
            private String bannerDetailUrl;

            public String getBannerUrl() {
                return bannerUrl;
            }

            public void setBannerUrl(String bannerUrl) {
                this.bannerUrl = bannerUrl;
            }

            public String getBannerDetailUrl() {
                return bannerDetailUrl;
            }

            public void setBannerDetailUrl(String bannerDetailUrl) {
                this.bannerDetailUrl = bannerDetailUrl;
            }
        }

        public static class DeductsBean {
            private Integer mallGiftId;
            private String poolNo;
            private String giftName;
            private String otherName;
            private String amount;
            private String giftRole;
            private String appRoleDesc;
            private String appActiveRole;
            private String point;
            private String effectTime;
            private Integer sort;
            private String state;
            private Integer exchangeNum;

            public Integer getMallGiftId() {
                return mallGiftId;
            }

            public void setMallGiftId(Integer mallGiftId) {
                this.mallGiftId = mallGiftId;
            }

            public String getPoolNo() {
                return poolNo;
            }

            public void setPoolNo(String poolNo) {
                this.poolNo = poolNo;
            }

            public String getGiftName() {
                return giftName;
            }

            public void setGiftName(String giftName) {
                this.giftName = giftName;
            }

            public String getOtherName() {
                return otherName;
            }

            public void setOtherName(String otherName) {
                this.otherName = otherName;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getGiftRole() {
                return giftRole;
            }

            public void setGiftRole(String giftRole) {
                this.giftRole = giftRole;
            }

            public String getAppRoleDesc() {
                return appRoleDesc;
            }

            public void setAppRoleDesc(String appRoleDesc) {
                this.appRoleDesc = appRoleDesc;
            }

            public String getAppActiveRole() {
                return appActiveRole;
            }

            public void setAppActiveRole(String appActiveRole) {
                this.appActiveRole = appActiveRole;
            }

            public String getPoint() {
                return point;
            }

            public void setPoint(String point) {
                this.point = point;
            }

            public String getEffectTime() {
                return effectTime;
            }

            public void setEffectTime(String effectTime) {
                this.effectTime = effectTime;
            }

            public Integer getSort() {
                return sort;
            }

            public void setSort(Integer sort) {
                this.sort = sort;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public Integer getExchangeNum() {
                return exchangeNum;
            }

            public void setExchangeNum(Integer exchangeNum) {
                this.exchangeNum = exchangeNum;
            }
        }
    }

    @Override
    public String toString() {
        return "PointShopModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", extData=" + extData +
                ", error=" + error +
                ", success=" + success +
                '}';
    }
}
