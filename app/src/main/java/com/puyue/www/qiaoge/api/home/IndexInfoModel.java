package com.puyue.www.qiaoge.api.home;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/1/4
 */
public class IndexInfoModel {


    /**
     * code : 1
     * message : 成功
     * data : {"address":"杭州市","noticeNum":1,"banners":[{"showType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//14b6db4b97c24089b481a8b511a5460d.jpg","linkSrc":"www.baidu.com","detailPic":null,"prodPage":null,"businessId":null,"businessType":null},{"showType":4,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//af841f2d7faf4f9d870eee55f0f935ec.jpg","linkSrc":null,"detailPic":null,"prodPage":null,"businessId":4694,"businessType":1},{"showType":3,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//a500348e2c2843bd811512c29fca592d.jpg","linkSrc":null,"detailPic":null,"prodPage":"killProd","businessId":null,"businessType":null},{"showType":2,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//12a43a3775cc49a3bdc6b63391c176cc.jpg","linkSrc":null,"detailPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//74175d85bcec47a8ab8a9a885a00eaf3.jpg","prodPage":null,"businessId":null,"businessType":null}],"icons":[{"configCode":"commonBuy","configDesc":"常用清单","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_listed%402x.png","type":null},{"configCode":"hotProd","configDesc":"热卖商品","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_hot%402x.png","type":null},{"configCode":"reductProd","configDesc":"降价商品","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon__kill%402x.png","type":null},{"configCode":"messageNews","configDesc":"行业资讯","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_NEW%402x.png","type":null}],"spikeNum":4,"teamNum":2,"specialNum":4,"classifyTitle":"精选分类","classifyDesc":"Selection Classification","otherInfo":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/image/otherinfo.png","classifyList":[{"title":"js123","img":"","id":100},{"title":"炸鸡汉堡区","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/bd7e9e4601a54c1487d5b31ddc277955.png","id":1},{"title":"炸鸡汉堡","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/a7e3ca2788f448559d2e3b2b46fa81bf.png","id":4},{"title":"麻辣烫","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/5c73c11646ba452db6ec8ebfca7d837c.png","id":6},{"title":"早餐","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/3ca63bcef18649bf86c608eda2336062.png","id":8},{"title":"便当","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/1abee5c3009e4ff39d84b53266709713.png","id":10}],"addAddress":0,"userIsBuy":0}
     * success : true
     * error : false
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean success;
    private boolean error;

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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public static class DataBean {
        /**
         * address : 杭州市
         * noticeNum : 1
         * banners : [{"showType":1,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//14b6db4b97c24089b481a8b511a5460d.jpg","linkSrc":"www.baidu.com","detailPic":null,"prodPage":null,"businessId":null,"businessType":null},{"showType":4,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//af841f2d7faf4f9d870eee55f0f935ec.jpg","linkSrc":null,"detailPic":null,"prodPage":null,"businessId":4694,"businessType":1},{"showType":3,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//a500348e2c2843bd811512c29fca592d.jpg","linkSrc":null,"detailPic":null,"prodPage":"killProd","businessId":null,"businessType":null},{"showType":2,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//12a43a3775cc49a3bdc6b63391c176cc.jpg","linkSrc":null,"detailPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//74175d85bcec47a8ab8a9a885a00eaf3.jpg","prodPage":null,"businessId":null,"businessType":null}]
         * icons : [{"configCode":"commonBuy","configDesc":"常用清单","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_listed%402x.png","type":null},{"configCode":"hotProd","configDesc":"热卖商品","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_hot%402x.png","type":null},{"configCode":"reductProd","configDesc":"降价商品","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon__kill%402x.png","type":null},{"configCode":"messageNews","configDesc":"行业资讯","remark":null,"url":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_NEW%402x.png","type":null}]
         * spikeNum : 4
         * teamNum : 2
         * specialNum : 4
         * classifyTitle : 精选分类
         * classifyDesc : Selection Classification
         * otherInfo : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/image/otherinfo.png
         * classifyList : [{"title":"js123","img":"","id":100},{"title":"炸鸡汉堡区","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/bd7e9e4601a54c1487d5b31ddc277955.png","id":1},{"title":"炸鸡汉堡","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/a7e3ca2788f448559d2e3b2b46fa81bf.png","id":4},{"title":"麻辣烫","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/5c73c11646ba452db6ec8ebfca7d837c.png","id":6},{"title":"早餐","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/3ca63bcef18649bf86c608eda2336062.png","id":8},{"title":"便当","img":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/scenicspot/1abee5c3009e4ff39d84b53266709713.png","id":10}]
         * addAddress : 0
         * userIsBuy : 0
         */
        private String homeBackPic;
        private String address;
        private int noticeNum;
        private int spikeNum;
        private int teamNum;
        private int specialNum;
        private String classifyTitle;
        private String classifyDesc;
        private String otherInfo;
        private int addAddress;
        private int userIsBuy;
        String questUrl;
        String areaName;
        String cityName;
        String provinceName;
        String deductAmountStr;
        String offerStr;
        String sendTime;
        String sendAmount;
        int fullGiftNum;
        String returnAmountTime;
        boolean addressIsInArea;
        private List<BannersBean> banners;
        private List<IconsBean> icons;
        private List<ClassifyListBean> classifyList;
        private DataBean.SendOrderBean sendOrder;
        private List<String> hotKey;
        String giftReceiveBtn;
        private UserPopup userPopup;
        private HomePopup homePopup;
        int hllOrderCallNum;
        String orderId;
        private HllTip hllTip;
        String companyName;
        private List<NoticeInfoBean> noticeInfo;
        boolean showQgSchool;

        public DataBean.SendOrderBean getSendOrder() {
            return sendOrder;
        }

        public void setSendOrder(DataBean.SendOrderBean sendOrder) {
            this.sendOrder = sendOrder;
        }

        public boolean isShowQgSchool() {
            return showQgSchool;
        }

        public void setShowQgSchool(boolean showQgSchool) {
            this.showQgSchool = showQgSchool;
        }

        public List<NoticeInfoBean> getNoticeInfo() {
            return noticeInfo;
        }

        public void setNoticeInfo(List<NoticeInfoBean> noticeInfo) {
            this.noticeInfo = noticeInfo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
        public HllTip getHllTip() {
            return hllTip;
        }

        public void setHllTip(HllTip hllTip) {
            this.hllTip = hllTip;
        }

        public int getHllOrderCallNum() {
            return hllOrderCallNum;
        }

        public void setHllOrderCallNum(int hllOrderCallNum) {
            this.hllOrderCallNum = hllOrderCallNum;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public static class SendOrderBean {
            private String orderId;
            private String orderAmt;
            private String sendPhone;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getOrderAmt() {
                return orderAmt;
            }

            public void setOrderAmt(String orderAmt) {
                this.orderAmt = orderAmt;
            }

            public String getSendPhone() {
                return sendPhone;
            }

            public void setSendPhone(String sendPhone) {
                this.sendPhone = sendPhone;
            }
        }

        public static class NoticeInfoBean {
            /**
             * id : 141
             * title : 的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶
             * dateTime : 2021-08-19
             * readFlag : 0
             */

            private int id;
            private String title;
            private String gmtCreateTime;
            private String content;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getGmtCreateTime() {
                return gmtCreateTime;
            }

            public void setGmtCreateTime(String gmtCreateTime) {
                this.gmtCreateTime = gmtCreateTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "homeBackPic='" + homeBackPic + '\'' +
                    ", address='" + address + '\'' +
                    ", noticeNum=" + noticeNum +
                    ", spikeNum=" + spikeNum +
                    ", teamNum=" + teamNum +
                    ", specialNum=" + specialNum +
                    ", classifyTitle='" + classifyTitle + '\'' +
                    ", classifyDesc='" + classifyDesc + '\'' +
                    ", otherInfo='" + otherInfo + '\'' +
                    ", addAddress=" + addAddress +
                    ", userIsBuy=" + userIsBuy +
                    ", questUrl='" + questUrl + '\'' +
                    ", areaName='" + areaName + '\'' +
                    ", cityName='" + cityName + '\'' +
                    ", provinceName='" + provinceName + '\'' +
                    ", deductAmountStr='" + deductAmountStr + '\'' +
                    ", offerStr='" + offerStr + '\'' +
                    ", sendTime='" + sendTime + '\'' +
                    ", sendAmount='" + sendAmount + '\'' +
                    ", fullGiftNum=" + fullGiftNum +
                    ", returnAmountTime='" + returnAmountTime + '\'' +
                    ", addressIsInArea=" + addressIsInArea +
                    ", banners=" + banners +
                    ", icons=" + icons +
                    ", classifyList=" + classifyList +
                    ", hotKey=" + hotKey +
                    ", giftReceiveBtn='" + giftReceiveBtn + '\'' +
                    ", userPopup=" + userPopup +
                    ", homePopup=" + homePopup +
                    '}';
        }

        public HomePopup getHomePopup() {
            return homePopup;
        }

        public void setHomePopup(HomePopup homePopup) {
            this.homePopup = homePopup;
        }

        public UserPopup getUserPopup() {
            return userPopup;
        }

        public void setUserPopup(UserPopup userPopup) {
            this.userPopup = userPopup;
        }

        public String getGiftReceiveBtn() {
            return giftReceiveBtn;
        }

        public void setGiftReceiveBtn(String giftReceiveBtn) {
            this.giftReceiveBtn = giftReceiveBtn;
        }

        public List<String> getHotKey() {
            return hotKey;
        }

        public void setHotKey(List<String> hotKey) {
            this.hotKey = hotKey;
        }

        public boolean isAddressIsInArea() {
            return addressIsInArea;
        }

        public void setAddressIsInArea(boolean addressIsInArea) {
            this.addressIsInArea = addressIsInArea;
        }

        public String getSendTime() {
            return sendTime;
        }

        public String getReturnAmountTime() {
            return returnAmountTime;
        }

        public void setReturnAmountTime(String returnAmountTime) {
            this.returnAmountTime = returnAmountTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getSendAmount() {
            return sendAmount;
        }

        public void setSendAmount(String sendAmount) {
            this.sendAmount = sendAmount;
        }

        public int getFullGiftNum() {
            return fullGiftNum;
        }

        public void setFullGiftNum(int fullGiftNum) {
            this.fullGiftNum = fullGiftNum;
        }

        public String getDeductAmountStr() {
            return deductAmountStr;
        }

        public String getHomeBackPic() {
            return homeBackPic;
        }

        public void setHomeBasicInfo(String homeBackPic) {
            this.homeBackPic = homeBackPic;
        }

        public String getOfferStr() {
            return offerStr;
        }

        public void setOfferStr(String offerStr) {
            this.offerStr = offerStr;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getQuestUrl() {
            return questUrl;
        }

        public void setQuestUrl(String questUrl) {
            this.questUrl = questUrl;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getNoticeNum() {
            return noticeNum;
        }

        public void setNoticeNum(int noticeNum) {
            this.noticeNum = noticeNum;
        }

        public int getSpikeNum() {
            return spikeNum;
        }

        public void setSpikeNum(int spikeNum) {
            this.spikeNum = spikeNum;
        }

        public int getTeamNum() {
            return teamNum;
        }

        public void setTeamNum(int teamNum) {
            this.teamNum = teamNum;
        }

        public int getSpecialNum() {
            return specialNum;
        }

        public void setSpecialNum(int specialNum) {
            this.specialNum = specialNum;
        }

        public String getClassifyTitle() {
            return classifyTitle;
        }

        public void setClassifyTitle(String classifyTitle) {
            this.classifyTitle = classifyTitle;
        }

        public String getClassifyDesc() {
            return classifyDesc;
        }

        public void setClassifyDesc(String classifyDesc) {
            this.classifyDesc = classifyDesc;
        }

        public String getOtherInfo() {
            return otherInfo;
        }

        public void setOtherInfo(String otherInfo) {
            this.otherInfo = otherInfo;
        }

        public int getAddAddress() {
            return addAddress;
        }

        public void setAddAddress(int addAddress) {
            this.addAddress = addAddress;
        }

        public int getUserIsBuy() {
            return userIsBuy;
        }

        public void setUserIsBuy(int userIsBuy) {
            this.userIsBuy = userIsBuy;
        }

        public List<BannersBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannersBean> banners) {
            this.banners = banners;
        }

        public List<IconsBean> getIcons() {
            return icons;
        }

        public void setIcons(List<IconsBean> icons) {
            this.icons = icons;
        }

        public List<ClassifyListBean> getClassifyList() {
            return classifyList;
        }

        public void setClassifyList(List<ClassifyListBean> classifyList) {
            this.classifyList = classifyList;
        }

//        private boolean propup;
//        private HomePropupBean homePropup;
//
//        public boolean isPropup() {
//            return propup;
//        }
//
//        public void setPropup(boolean propup) {
//            this.propup = propup;
//        }
//
//        public HomePropupBean getHomePropup() {
//            return homePropup;
//        }
//
//        public void setHomePropup(HomePropupBean homePropup) {
//            this.homePropup = homePropup;
//        }

        public static class HomePopup {
            /**
             * id : 7
             * title : 首页弹窗
             * effectStartTime : 1539248067000
             * effectEndTime : 1540448505000
             * showUrl : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image/9aa521923a6c4d528fa5d08cc6d2e940.png
             * toPage : self
             * detailUrl : null
             * pageUrl : http://116.62.67.230:8082/apph5/html/purchaseRecord.html
             * deleteFlag : false
             * gmtCreate : 1540275972000
             * gmtModify : 1540275971000
             * createUserId : 1
             * updateUserId : null
             * memo : null
             */

            private int id;
            private String title;
            private long effectStartTime;
            private long effectEndTime;
            private String showUrl;
            private String toPage;
            private Object detailUrl;
            private String pageUrl;
            private boolean deleteFlag;
            private long gmtCreate;
            private long gmtModify;
            private int createUserId;
            private Object updateUserId;
            private Object memo;
            boolean isPropup;


            public boolean isPropup() {
                return isPropup;
            }

            public void setPropup(boolean propup) {
                isPropup = propup;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getEffectStartTime() {
                return effectStartTime;
            }

            public void setEffectStartTime(long effectStartTime) {
                this.effectStartTime = effectStartTime;
            }

            public long getEffectEndTime() {
                return effectEndTime;
            }

            public void setEffectEndTime(long effectEndTime) {
                this.effectEndTime = effectEndTime;
            }

            public String getShowUrl() {
                return showUrl;
            }

            public void setShowUrl(String showUrl) {
                this.showUrl = showUrl;
            }

            public String getToPage() {
                return toPage;
            }

            public void setToPage(String toPage) {
                this.toPage = toPage;
            }

            public Object getDetailUrl() {
                return detailUrl;
            }

            public void setDetailUrl(Object detailUrl) {
                this.detailUrl = detailUrl;
            }

            public String getPageUrl() {
                return pageUrl;
            }

            public void setPageUrl(String pageUrl) {
                this.pageUrl = pageUrl;
            }

            public boolean isDeleteFlag() {
                return deleteFlag;
            }

            public void setDeleteFlag(boolean deleteFlag) {
                this.deleteFlag = deleteFlag;
            }

            public long getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(long gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public long getGmtModify() {
                return gmtModify;
            }

            public void setGmtModify(long gmtModify) {
                this.gmtModify = gmtModify;
            }

            public int getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(int createUserId) {
                this.createUserId = createUserId;
            }

            public Object getUpdateUserId() {
                return updateUserId;
            }

            public void setUpdateUserId(Object updateUserId) {
                this.updateUserId = updateUserId;
            }

            public Object getMemo() {
                return memo;
            }

            public void setMemo(Object memo) {
                this.memo = memo;
            }
        }

        public static class HllTip {
            /**
             * id : 1
             * title : 恭喜您获得翘歌优惠券
             * content : 优惠券到账通知
             * gifts : [{"giftName":"6元无门槛消费券","giftType":null,"amount":null,"amountStr":"6","limitAmtStr":"满0元可用","limitAmt":null,"applyFrom":null,"dateTime":"有效期至2020-10-15","role":["全场通用"],"overTimePic":null,"usedPic":null,"unAblePic":null,"state":null,"giftDetailNo":null,"orderId":null}]
             * jumpType : 0
             * jumpUrl : null
             */

            private int id;
            private String title;
            private String content;
            private int jumpType;
            private Object jumpUrl;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public Object getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(Object jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public static class GiftsBean {
                /**
                 * giftName : 6元无门槛消费券
                 * giftType : null
                 * amount : null
                 * amountStr : 6
                 * limitAmtStr : 满0元可用
                 * limitAmt : null
                 * applyFrom : null
                 * dateTime : 有效期至2020-10-15
                 * role : ["全场通用"]
                 * overTimePic : null
                 * usedPic : null
                 * unAblePic : null
                 * state : null
                 * giftDetailNo : null
                 * orderId : null
                 */

                private String giftName;
                private Object giftType;
                private Object amount;
                private String amountStr;
                private String limitAmtStr;
                private Object limitAmt;
                private Object applyFrom;
                private String dateTime;
                private Object overTimePic;
                private Object usedPic;
                private Object unAblePic;
                private Object state;
                private Object giftDetailNo;
                private String orderId;
                private List<String> role;

                public String getGiftName() {
                    return giftName;
                }

                public void setGiftName(String giftName) {
                    this.giftName = giftName;
                }

                public Object getGiftType() {
                    return giftType;
                }

                public void setGiftType(Object giftType) {
                    this.giftType = giftType;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public String getAmountStr() {
                    return amountStr;
                }

                public void setAmountStr(String amountStr) {
                    this.amountStr = amountStr;
                }

                public String getLimitAmtStr() {
                    return limitAmtStr;
                }

                public void setLimitAmtStr(String limitAmtStr) {
                    this.limitAmtStr = limitAmtStr;
                }

                public Object getLimitAmt() {
                    return limitAmt;
                }

                public void setLimitAmt(Object limitAmt) {
                    this.limitAmt = limitAmt;
                }

                public Object getApplyFrom() {
                    return applyFrom;
                }

                public void setApplyFrom(Object applyFrom) {
                    this.applyFrom = applyFrom;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public Object getOverTimePic() {
                    return overTimePic;
                }

                public void setOverTimePic(Object overTimePic) {
                    this.overTimePic = overTimePic;
                }

                public Object getUsedPic() {
                    return usedPic;
                }

                public void setUsedPic(Object usedPic) {
                    this.usedPic = usedPic;
                }

                public Object getUnAblePic() {
                    return unAblePic;
                }

                public void setUnAblePic(Object unAblePic) {
                    this.unAblePic = unAblePic;
                }

                public Object getState() {
                    return state;
                }

                public void setState(Object state) {
                    this.state = state;
                }

                public Object getGiftDetailNo() {
                    return giftDetailNo;
                }

                public void setGiftDetailNo(Object giftDetailNo) {
                    this.giftDetailNo = giftDetailNo;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public List<String> getRole() {
                    return role;
                }

                public void setRole(List<String> role) {
                    this.role = role;
                }
            }
        }

        public static class UserPopup {
            /**
             * id : 1
             * title : 恭喜您获得翘歌优惠券
             * content : 优惠券到账通知
             * gifts : [{"giftName":"6元无门槛消费券","giftType":null,"amount":null,"amountStr":"6","limitAmtStr":"满0元可用","limitAmt":null,"applyFrom":null,"dateTime":"有效期至2020-10-15","role":["全场通用"],"overTimePic":null,"usedPic":null,"unAblePic":null,"state":null,"giftDetailNo":null,"orderId":null}]
             * jumpType : 0
             * jumpUrl : null
             */

            private int id;
            private String title;
            private String content;
            private int jumpType;
            private Object jumpUrl;
            private List<CouponListModel.DataBean.GiftsBean> gifts;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getJumpType() {
                return jumpType;
            }

            public void setJumpType(int jumpType) {
                this.jumpType = jumpType;
            }

            public Object getJumpUrl() {
                return jumpUrl;
            }

            public void setJumpUrl(Object jumpUrl) {
                this.jumpUrl = jumpUrl;
            }

            public List<CouponListModel.DataBean.GiftsBean> getGifts() {
                return gifts;
            }

            public void setGifts(List<CouponListModel.DataBean.GiftsBean> gifts) {
                this.gifts = gifts;
            }

            public static class GiftsBean {
                /**
                 * giftName : 6元无门槛消费券
                 * giftType : null
                 * amount : null
                 * amountStr : 6
                 * limitAmtStr : 满0元可用
                 * limitAmt : null
                 * applyFrom : null
                 * dateTime : 有效期至2020-10-15
                 * role : ["全场通用"]
                 * overTimePic : null
                 * usedPic : null
                 * unAblePic : null
                 * state : null
                 * giftDetailNo : null
                 * orderId : null
                 */

                private String giftName;
                private Object giftType;
                private Object amount;
                private String amountStr;
                private String limitAmtStr;
                private Object limitAmt;
                private Object applyFrom;
                private String dateTime;
                private Object overTimePic;
                private Object usedPic;
                private Object unAblePic;
                private Object state;
                private Object giftDetailNo;
                private String orderId;
                private List<String> role;

                public String getGiftName() {
                    return giftName;
                }

                public void setGiftName(String giftName) {
                    this.giftName = giftName;
                }

                public Object getGiftType() {
                    return giftType;
                }

                public void setGiftType(Object giftType) {
                    this.giftType = giftType;
                }

                public Object getAmount() {
                    return amount;
                }

                public void setAmount(Object amount) {
                    this.amount = amount;
                }

                public String getAmountStr() {
                    return amountStr;
                }

                public void setAmountStr(String amountStr) {
                    this.amountStr = amountStr;
                }

                public String getLimitAmtStr() {
                    return limitAmtStr;
                }

                public void setLimitAmtStr(String limitAmtStr) {
                    this.limitAmtStr = limitAmtStr;
                }

                public Object getLimitAmt() {
                    return limitAmt;
                }

                public void setLimitAmt(Object limitAmt) {
                    this.limitAmt = limitAmt;
                }

                public Object getApplyFrom() {
                    return applyFrom;
                }

                public void setApplyFrom(Object applyFrom) {
                    this.applyFrom = applyFrom;
                }

                public String getDateTime() {
                    return dateTime;
                }

                public void setDateTime(String dateTime) {
                    this.dateTime = dateTime;
                }

                public Object getOverTimePic() {
                    return overTimePic;
                }

                public void setOverTimePic(Object overTimePic) {
                    this.overTimePic = overTimePic;
                }

                public Object getUsedPic() {
                    return usedPic;
                }

                public void setUsedPic(Object usedPic) {
                    this.usedPic = usedPic;
                }

                public Object getUnAblePic() {
                    return unAblePic;
                }

                public void setUnAblePic(Object unAblePic) {
                    this.unAblePic = unAblePic;
                }

                public Object getState() {
                    return state;
                }

                public void setState(Object state) {
                    this.state = state;
                }

                public Object getGiftDetailNo() {
                    return giftDetailNo;
                }

                public void setGiftDetailNo(Object giftDetailNo) {
                    this.giftDetailNo = giftDetailNo;
                }

                public String getOrderId() {
                    return orderId;
                }

                public void setOrderId(String orderId) {
                    this.orderId = orderId;
                }

                public List<String> getRole() {
                    return role;
                }

                public void setRole(List<String> role) {
                    this.role = role;
                }
            }
        }

        public static class BannersBean {
            /**
             * showType : 1
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//14b6db4b97c24089b481a8b511a5460d.jpg
             * linkSrc : www.baidu.com
             * detailPic : null
             * prodPage : null
             * businessId : null
             * businessType : null
             */
            String rgbColor;
            private int showType;
            private String defaultPic;
            private String linkSrc;
            private String detailPic;
            private String prodPage;
            private String businessId;
            private String businessType;
            private int businessNum;
            private String bannerId;
            private String title;
            public String getRgbColor() {
                return rgbColor;
            }

            public String getBannerId() {
                return bannerId;
            }

            public void setBannerId(String bannerId) {
                this.bannerId = bannerId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getBusinessNum() {
                return businessNum;
            }

            public void setBusinessNum(int businessNum) {
                this.businessNum = businessNum;
            }

            public void setRgbColor(String rgbColor) {
                this.rgbColor = rgbColor;
            }

            public int getShowType() {
                return showType;
            }

            public void setShowType(int showType) {
                this.showType = showType;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public String getLinkSrc() {
                return linkSrc;
            }

            public void setLinkSrc(String linkSrc) {
                this.linkSrc = linkSrc;
            }

            public String getDetailPic() {
                return detailPic;
            }

            public void setDetailPic(String detailPic) {
                this.detailPic = detailPic;
            }

            public String getProdPage() {
                return prodPage;
            }

            public void setProdPage(String prodPage) {
                this.prodPage = prodPage;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessType() {
                return businessType;
            }

            public void setBusinessType(String businessType) {
                this.businessType = businessType;
            }
        }

        public static class IconsBean {
            /**
             * configCode : commonBuy
             * configDesc : 常用清单
             * remark : null
             * url : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/apps/icon/icon_listed%402x.png
             * type : null
             */

            private String configCode;
            private String configDesc;
            private String remark;
            private String url;
            private Object type;

            public String getConfigCode() {
                return configCode;
            }

            public void setConfigCode(String configCode) {
                this.configCode = configCode;
            }

            public String getConfigDesc() {
                return configDesc;
            }

            public void setConfigDesc(String configDesc) {
                this.configDesc = configDesc;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }
        }

        public static class ClassifyListBean implements MultiItemEntity {
            /**
             * title : js123
             * img :
             * id : 100
             */
            public static final int SHORT = 0;
            public static final int LONG = 1;
            private String title;
            private String img;
            private int id;
            private int spanSize;
            private String secTitle;
            private List<String> prodPics;
            private int itemType;
            private String floatText;

            public String getFloatText() {
                return floatText;
            }

            public void setFloatText(String floatText) {
                this.floatText = floatText;
            }

            //
            public String getSecTitle() {
                return secTitle;
            }

            public void setSecTitle(String secTitle) {
                this.secTitle = secTitle;
            }

            public List<String> getProdPics() {
                return prodPics;
            }

            public void setProdPics(List<String> prodPics) {
                this.prodPics = prodPics;
            }

            public int getSpanSize() {
                return spanSize;
            }

            public void setSpanSize(int spanSize) {
                this.spanSize = spanSize;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setItemType(int itemType) {
                this.itemType = itemType;
            }
            @Override
            public int getItemType() {
                return itemType;
            }
        }
    }
}
