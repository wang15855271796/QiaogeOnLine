package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/5/31
 */
public class CouponModels {

    /**
     * code : 1
     * message : 成功
     * data : {"spike":{"title":"秒杀专区","desc":"秒到就是赚到","currentTime":1622078270099,"startTime":1619193600000,"endTime":1622390400000,"startTimeStr":"2021-04-24 00:00","actives":[{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4862,"activeType":2,"activeName":"太上老君","price":"￥1/箱","oldPrice":"￥357.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4861,"activeType":2,"activeName":"三尺长剑","price":"￥1/箱","oldPrice":"￥100.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4860,"activeType":2,"activeName":"一棒定乾坤","price":"￥1/袋","oldPrice":"￥478.00/袋","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/891af922f91742fdad215ea3a8d63549.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4859,"activeType":2,"activeName":"【孙先生】关联服务商的供应商1","price":"￥1/箱","oldPrice":"￥189.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/45a23a1637004149ac93a07761d5d9c6.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4858,"activeType":2,"activeName":"金木水火土","price":"￥1/箱","oldPrice":"￥177.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4857,"activeType":2,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]},"special":{"title":"折扣专区","desc":"最低可享一折优惠","currentTime":null,"startTime":null,"endTime":null,"startTimeStr":null,"actives":[{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4680,"activeType":11,"activeName":"【丹飞】凤爪","price":"￥1/箱","oldPrice":"￥210.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.04折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4813,"activeType":11,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.06折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4841,"activeType":11,"activeName":"一般商品1","price":"￥211/箱","oldPrice":"￥267.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.03折"}]},"team":null,"fullGift":{"title":"满赠活动","desc":"满赠钜惠，赠品停不下来！","currentTime":null,"startTime":null,"endTime":null,"startTimeStr":null,"actives":[{"productMainId":8481,"productId":8989,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//c87075e2bc754a89abff4703b29a6848.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":990,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":7284,"productId":7653,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//7bdde3e9f19945c98749d6653c86ff15.png","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"送券","minMaxPrice":"￥***","inventory":82,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8159,"productId":8640,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":30,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8320,"productId":8827,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":550,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]}}
     * error : false
     * success : true
     */

    private int code;
    private String message;
    private DataBean data;
    private boolean error;
    private boolean success;

    @Override
    public String toString() {
        return "CouponModels{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", error=" + error +
                ", success=" + success +
                '}';
    }

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
        @Override
        public String toString() {
            return "DataBean{" +
                    "spike=" + spike +
                    ", special=" + special +
                    ", team=" + team +
                    ", fullGift=" + fullGift +
                    '}';
        }

        /**
         * spike : {"title":"秒杀专区","desc":"秒到就是赚到","currentTime":1622078270099,"startTime":1619193600000,"endTime":1622390400000,"startTimeStr":"2021-04-24 00:00","actives":[{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4862,"activeType":2,"activeName":"太上老君","price":"￥1/箱","oldPrice":"￥357.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4861,"activeType":2,"activeName":"三尺长剑","price":"￥1/箱","oldPrice":"￥100.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4860,"activeType":2,"activeName":"一棒定乾坤","price":"￥1/袋","oldPrice":"￥478.00/袋","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/891af922f91742fdad215ea3a8d63549.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4859,"activeType":2,"activeName":"【孙先生】关联服务商的供应商1","price":"￥1/箱","oldPrice":"￥189.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/45a23a1637004149ac93a07761d5d9c6.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4858,"activeType":2,"activeName":"金木水火土","price":"￥1/箱","oldPrice":"￥177.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4857,"activeType":2,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]}
         * special : {"title":"折扣专区","desc":"最低可享一折优惠","currentTime":null,"startTime":null,"endTime":null,"startTimeStr":null,"actives":[{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4680,"activeType":11,"activeName":"【丹飞】凤爪","price":"￥1/箱","oldPrice":"￥210.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.04折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4813,"activeType":11,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.06折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4841,"activeType":11,"activeName":"一般商品1","price":"￥211/箱","oldPrice":"￥267.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.03折"}]}
         * team : null
         * fullGift : {"title":"满赠活动","desc":"满赠钜惠，赠品停不下来！","currentTime":null,"startTime":null,"endTime":null,"startTimeStr":null,"actives":[{"productMainId":8481,"productId":8989,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//c87075e2bc754a89abff4703b29a6848.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":990,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":7284,"productId":7653,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//7bdde3e9f19945c98749d6653c86ff15.png","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"送券","minMaxPrice":"￥***","inventory":82,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8159,"productId":8640,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":30,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8320,"productId":8827,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":550,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]}
         */

        private SpikeBean spike;
        private SpecialBean special;
        private TeamBean team;
        private FullGiftBean fullGift;

        public SpikeBean getSpike() {
            return spike;
        }

        public void setSpike(SpikeBean spike) {
            this.spike = spike;
        }

        public SpecialBean getSpecial() {
            return special;
        }

        public void setSpecial(SpecialBean special) {
            this.special = special;
        }

        public TeamBean getTeam() {
            return team;
        }

        public void setTeam(TeamBean team) {
            this.team = team;
        }

        public FullGiftBean getFullGift() {
            return fullGift;
        }

        public void setFullGift(FullGiftBean fullGift) {
            this.fullGift = fullGift;
        }

        public static class SpikeBean {
            @Override
            public String toString() {
                return "SpikeBean{" +
                        "title='" + title + '\'' +
                        ", desc='" + desc + '\'' +
                        ", currentTime=" + currentTime +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", startTimeStr='" + startTimeStr + '\'' +
                        ", actives=" + actives +
                        '}';
            }

            /**
             * title : 秒杀专区
             * desc : 秒到就是赚到
             * currentTime : 1622078270099
             * startTime : 1619193600000
             * endTime : 1622390400000
             * startTimeStr : 2021-04-24 00:00
             * actives : [{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4862,"activeType":2,"activeName":"太上老君","price":"￥1/箱","oldPrice":"￥357.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/a67773e4486b4961aa8efade944914fe.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4861,"activeType":2,"activeName":"三尺长剑","price":"￥1/箱","oldPrice":"￥100.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4860,"activeType":2,"activeName":"一棒定乾坤","price":"￥1/袋","oldPrice":"￥478.00/袋","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/891af922f91742fdad215ea3a8d63549.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4859,"activeType":2,"activeName":"【孙先生】关联服务商的供应商1","price":"￥1/箱","oldPrice":"￥189.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/45a23a1637004149ac93a07761d5d9c6.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4858,"activeType":2,"activeName":"金木水火土","price":"￥1/箱","oldPrice":"￥177.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4857,"activeType":2,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]
             */

            private String title;
            private String desc;
            private long currentTime;
            private long startTime;
            private long endTime;
            private String startTimeStr;
            private List<ActivesBean> actives;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public long getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(long currentTime) {
                this.currentTime = currentTime;
            }

            public long getStartTime() {
                return startTime;
            }

            public void setStartTime(long startTime) {
                this.startTime = startTime;
            }

            public long getEndTime() {
                return endTime;
            }

            public void setEndTime(long endTime) {
                this.endTime = endTime;
            }

            public String getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(String startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public List<ActivesBean> getActives() {
                return actives;
            }

            public void setActives(List<ActivesBean> actives) {
                this.actives = actives;
            }

            public static class ActivesBean {
                @Override
                public String toString() {
                    return "ActivesBean{" +
                            "productMainId=" + productMainId +
                            ", productId=" + productId +
                            ", defaultPic='" + defaultPic + '\'' +
                            ", productName=" + productName +
                            ", spec=" + spec +
                            ", sendGiftInfo=" + sendGiftInfo +
                            ", sendGiftType=" + sendGiftType +
                            ", minMaxPrice=" + minMaxPrice +
                            ", inventory=" + inventory +
                            ", roleAmount='" + roleAmount + '\'' +
                            ", sendGiftPic=" + sendGiftPic +
                            ", activeId=" + activeId +
                            ", activeType=" + activeType +
                            ", activeName='" + activeName + '\'' +
                            ", price='" + price + '\'' +
                            ", oldPrice='" + oldPrice + '\'' +
                            ", flag=" + flag +
                            ", soldOutPic='" + soldOutPic + '\'' +
                            ", discount='" + discount + '\'' +
                            '}';
                }

                /**
                 * productMainId : null
                 * productId : null
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/2cd45ac5288e42aabc85df361f544e80.jpg
                 * productName : null
                 * spec : null
                 * sendGiftInfo : null
                 * sendGiftType : null
                 * minMaxPrice : null
                 * inventory : 0
                 * roleAmount :
                 * sendGiftPic : null
                 * activeId : 4862
                 * activeType : 2
                 * activeName : 太上老君
                 * price : ￥1/箱
                 * oldPrice : ￥357.00/箱
                 * flag : 1
                 * soldOutPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png
                 * discount : null
                 */

                private Object productMainId;
                private Object productId;
                private String defaultPic;
                private Object productName;
                private Object spec;
                private Object sendGiftInfo;
                private Object sendGiftType;
                private Object minMaxPrice;
                private int inventory;
                private String roleAmount;
                private Object sendGiftPic;
                private int activeId;
                private int activeType;
                private String activeName;
                private String price;
                private String oldPrice;
                private int flag;
                private String soldOutPic;
                private String discount;

                public Object getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(Object productMainId) {
                    this.productMainId = productMainId;
                }

                public Object getProductId() {
                    return productId;
                }

                public void setProductId(Object productId) {
                    this.productId = productId;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public Object getProductName() {
                    return productName;
                }

                public void setProductName(Object productName) {
                    this.productName = productName;
                }

                public Object getSpec() {
                    return spec;
                }

                public void setSpec(Object spec) {
                    this.spec = spec;
                }

                public Object getSendGiftInfo() {
                    return sendGiftInfo;
                }

                public void setSendGiftInfo(Object sendGiftInfo) {
                    this.sendGiftInfo = sendGiftInfo;
                }

                public Object getSendGiftType() {
                    return sendGiftType;
                }

                public void setSendGiftType(Object sendGiftType) {
                    this.sendGiftType = sendGiftType;
                }

                public Object getMinMaxPrice() {
                    return minMaxPrice;
                }

                public void setMinMaxPrice(Object minMaxPrice) {
                    this.minMaxPrice = minMaxPrice;
                }

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public String getRoleAmount() {
                    return roleAmount;
                }

                public void setRoleAmount(String roleAmount) {
                    this.roleAmount = roleAmount;
                }

                public Object getSendGiftPic() {
                    return sendGiftPic;
                }

                public void setSendGiftPic(Object sendGiftPic) {
                    this.sendGiftPic = sendGiftPic;
                }

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public String getActiveName() {
                    return activeName;
                }

                public void setActiveName(String activeName) {
                    this.activeName = activeName;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(String oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public String getSoldOutPic() {
                    return soldOutPic;
                }

                public void setSoldOutPic(String soldOutPic) {
                    this.soldOutPic = soldOutPic;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }
            }
        }

        public static class SpecialBean {
            /**
             * title : 折扣专区
             * desc : 最低可享一折优惠
             * currentTime : null
             * startTime : null
             * endTime : null
             * startTimeStr : null
             * actives : [{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4680,"activeType":11,"activeName":"【丹飞】凤爪","price":"￥1/箱","oldPrice":"￥210.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.04折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4813,"activeType":11,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.06折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4841,"activeType":11,"activeName":"一般商品1","price":"￥211/箱","oldPrice":"￥267.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.03折"}]
             */

            private String title;
            private String desc;
            private Object currentTime;
            private Object startTime;
            private Object endTime;
            private Object startTimeStr;
            private List<ActivesBeanX> actives;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Object getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(Object currentTime) {
                this.currentTime = currentTime;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(Object startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public List<ActivesBeanX> getActives() {
                return actives;
            }

            public void setActives(List<ActivesBeanX> actives) {
                this.actives = actives;
            }

            public static class ActivesBeanX {
                /**
                 * productMainId : null
                 * productId : null
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg
                 * productName : null
                 * spec : null
                 * sendGiftInfo : null
                 * sendGiftType : null
                 * minMaxPrice : null
                 * inventory : 0
                 * roleAmount :
                 * sendGiftPic : null
                 * activeId : 4680
                 * activeType : 11
                 * activeName : 【丹飞】凤爪
                 * price : ￥1/箱
                 * oldPrice : ￥210.00/箱
                 * flag : 1
                 * soldOutPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png
                 * discount : 0.04折
                 */

                private Object productMainId;
                private Object productId;
                private String defaultPic;
                private Object productName;
                private Object spec;
                private Object sendGiftInfo;
                private Object sendGiftType;
                private Object minMaxPrice;
                private int inventory;
                private String roleAmount;
                private Object sendGiftPic;
                private int activeId;
                private int activeType;
                private String activeName;
                private String price;
                private String oldPrice;
                private int flag;
                private String soldOutPic;
                private String discount;

                public Object getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(Object productMainId) {
                    this.productMainId = productMainId;
                }

                public Object getProductId() {
                    return productId;
                }

                public void setProductId(Object productId) {
                    this.productId = productId;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public Object getProductName() {
                    return productName;
                }

                public void setProductName(Object productName) {
                    this.productName = productName;
                }

                public Object getSpec() {
                    return spec;
                }

                public void setSpec(Object spec) {
                    this.spec = spec;
                }

                public Object getSendGiftInfo() {
                    return sendGiftInfo;
                }

                public void setSendGiftInfo(Object sendGiftInfo) {
                    this.sendGiftInfo = sendGiftInfo;
                }

                public Object getSendGiftType() {
                    return sendGiftType;
                }

                public void setSendGiftType(Object sendGiftType) {
                    this.sendGiftType = sendGiftType;
                }

                public Object getMinMaxPrice() {
                    return minMaxPrice;
                }

                public void setMinMaxPrice(Object minMaxPrice) {
                    this.minMaxPrice = minMaxPrice;
                }

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public String getRoleAmount() {
                    return roleAmount;
                }

                public void setRoleAmount(String roleAmount) {
                    this.roleAmount = roleAmount;
                }

                public Object getSendGiftPic() {
                    return sendGiftPic;
                }

                public void setSendGiftPic(Object sendGiftPic) {
                    this.sendGiftPic = sendGiftPic;
                }

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public String getActiveName() {
                    return activeName;
                }

                public void setActiveName(String activeName) {
                    this.activeName = activeName;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(String oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public String getSoldOutPic() {
                    return soldOutPic;
                }

                public void setSoldOutPic(String soldOutPic) {
                    this.soldOutPic = soldOutPic;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }
            }
        }

        public static class FullGiftBean {
            /**
             * title : 满赠活动
             * desc : 满赠钜惠，赠品停不下来！
             * currentTime : null
             * startTime : null
             * endTime : null
             * startTimeStr : null
             * actives : [{"productMainId":8481,"productId":8989,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//c87075e2bc754a89abff4703b29a6848.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":990,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":7284,"productId":7653,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//7bdde3e9f19945c98749d6653c86ff15.png","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"送券","minMaxPrice":"￥***","inventory":82,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8159,"productId":8640,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/3452291e93a14725aaed43dd442ef9db.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":30,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null},{"productMainId":8320,"productId":8827,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":"赠品","minMaxPrice":"￥***","inventory":550,"roleAmount":"","sendGiftPic":null,"activeId":null,"activeType":null,"activeName":null,"price":null,"oldPrice":"","flag":0,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":null}]
             */

            private String title;
            private String desc;
            private Object currentTime;
            private Object startTime;
            private Object endTime;
            private Object startTimeStr;
            private List<ActivesBeanXX> actives;

            @Override
            public String toString() {
                return "FullGiftBean{" +
                        "title='" + title + '\'' +
                        ", desc='" + desc + '\'' +
                        ", currentTime=" + currentTime +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", startTimeStr=" + startTimeStr +
                        ", actives=" + actives +
                        '}';
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Object getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(Object currentTime) {
                this.currentTime = currentTime;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(Object startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public List<ActivesBeanXX> getActives() {
                return actives;
            }

            public void setActives(List<ActivesBeanXX> actives) {
                this.actives = actives;
            }

            public static class ActivesBeanXX {
                /**
                 * productMainId : 8481
                 * productId : 8989
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//c87075e2bc754a89abff4703b29a6848.jpg
                 * productName : null
                 * spec : null
                 * sendGiftInfo : null
                 * sendGiftType : 赠品
                 * minMaxPrice : ￥***
                 * inventory : 990
                 * roleAmount :
                 * sendGiftPic : null
                 * activeId : null
                 * activeType : null
                 * activeName : null
                 * price : null
                 * oldPrice :
                 * flag : 0
                 * soldOutPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png
                 * discount : null
                 */

                private int productMainId;
                private int productId;
                private String defaultPic;
                private String productName;
                private Object spec;
                private Object sendGiftInfo;
                private String sendGiftType;
                private String minMaxPrice;
                private int inventory;
                private String roleAmount;
                private Object sendGiftPic;
                private Object activeId;
                private Object activeType;
                private Object activeName;
                private Object price;
                private String oldPrice;
                private int flag;
                private String soldOutPic;
                private Object discount;

                @Override
                public String toString() {
                    return "ActivesBeanXX{" +
                            "productMainId=" + productMainId +
                            ", productId=" + productId +
                            ", defaultPic='" + defaultPic + '\'' +
                            ", productName='" + productName + '\'' +
                            ", spec=" + spec +
                            ", sendGiftInfo=" + sendGiftInfo +
                            ", sendGiftType='" + sendGiftType + '\'' +
                            ", minMaxPrice='" + minMaxPrice + '\'' +
                            ", inventory=" + inventory +
                            ", roleAmount='" + roleAmount + '\'' +
                            ", sendGiftPic=" + sendGiftPic +
                            ", activeId=" + activeId +
                            ", activeType=" + activeType +
                            ", activeName=" + activeName +
                            ", price=" + price +
                            ", oldPrice='" + oldPrice + '\'' +
                            ", flag=" + flag +
                            ", soldOutPic='" + soldOutPic + '\'' +
                            ", discount=" + discount +
                            '}';
                }

                public int getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(int productMainId) {
                    this.productMainId = productMainId;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public Object getSpec() {
                    return spec;
                }

                public void setSpec(Object spec) {
                    this.spec = spec;
                }

                public Object getSendGiftInfo() {
                    return sendGiftInfo;
                }

                public void setSendGiftInfo(Object sendGiftInfo) {
                    this.sendGiftInfo = sendGiftInfo;
                }

                public String getSendGiftType() {
                    return sendGiftType;
                }

                public void setSendGiftType(String sendGiftType) {
                    this.sendGiftType = sendGiftType;
                }

                public String getMinMaxPrice() {
                    return minMaxPrice;
                }

                public void setMinMaxPrice(String minMaxPrice) {
                    this.minMaxPrice = minMaxPrice;
                }

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public String getRoleAmount() {
                    return roleAmount;
                }

                public void setRoleAmount(String roleAmount) {
                    this.roleAmount = roleAmount;
                }

                public Object getSendGiftPic() {
                    return sendGiftPic;
                }

                public void setSendGiftPic(Object sendGiftPic) {
                    this.sendGiftPic = sendGiftPic;
                }

                public Object getActiveId() {
                    return activeId;
                }

                public void setActiveId(Object activeId) {
                    this.activeId = activeId;
                }

                public Object getActiveType() {
                    return activeType;
                }

                public void setActiveType(Object activeType) {
                    this.activeType = activeType;
                }

                public Object getActiveName() {
                    return activeName;
                }

                public void setActiveName(Object activeName) {
                    this.activeName = activeName;
                }

                public Object getPrice() {
                    return price;
                }

                public void setPrice(Object price) {
                    this.price = price;
                }

                public String getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(String oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public String getSoldOutPic() {
                    return soldOutPic;
                }

                public void setSoldOutPic(String soldOutPic) {
                    this.soldOutPic = soldOutPic;
                }

                public Object getDiscount() {
                    return discount;
                }

                public void setDiscount(Object discount) {
                    this.discount = discount;
                }
            }
        }

        public static class TeamBean {
            /**
             * title : 折扣专区
             * desc : 最低可享一折优惠
             * currentTime : null
             * startTime : null
             * endTime : null
             * startTimeStr : null
             * actives : [{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4680,"activeType":11,"activeName":"【丹飞】凤爪","price":"￥1/箱","oldPrice":"￥210.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.04折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/f71acec5c4ff4956bec2683a1d6934d8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4813,"activeType":11,"activeName":"鱼排","price":"￥1/箱","oldPrice":"￥150.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.06折"},{"productMainId":null,"productId":null,"defaultPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/fd6e0584ee5b4ea4b81687a1a349ede8.jpg","productName":null,"spec":null,"sendGiftInfo":null,"sendGiftType":null,"minMaxPrice":null,"inventory":0,"roleAmount":"","sendGiftPic":null,"activeId":4841,"activeType":11,"activeName":"一般商品1","price":"￥211/箱","oldPrice":"￥267.00/箱","flag":1,"soldOutPic":"https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png","discount":"0.03折"}]
             */

            private String title;
            private String desc;
            private Object currentTime;
            private Object startTime;
            private Object endTime;
            private Object startTimeStr;
            private List<ActivesBeanX> actives;

            @Override
            public String toString() {
                return "TeamBean{" +
                        "title='" + title + '\'' +
                        ", desc='" + desc + '\'' +
                        ", currentTime=" + currentTime +
                        ", startTime=" + startTime +
                        ", endTime=" + endTime +
                        ", startTimeStr=" + startTimeStr +
                        ", actives=" + actives +
                        '}';
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public Object getCurrentTime() {
                return currentTime;
            }

            public void setCurrentTime(Object currentTime) {
                this.currentTime = currentTime;
            }

            public Object getStartTime() {
                return startTime;
            }

            public void setStartTime(Object startTime) {
                this.startTime = startTime;
            }

            public Object getEndTime() {
                return endTime;
            }

            public void setEndTime(Object endTime) {
                this.endTime = endTime;
            }

            public Object getStartTimeStr() {
                return startTimeStr;
            }

            public void setStartTimeStr(Object startTimeStr) {
                this.startTimeStr = startTimeStr;
            }

            public List<ActivesBeanX> getActives() {
                return actives;
            }

            public void setActives(List<ActivesBeanX> actives) {
                this.actives = actives;
            }

            public static class ActivesBeanX {
                /**
                 * productMainId : null
                 * productId : null
                 * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//f66d3e4241224717aae72dcca9831cbc.jpg
                 * productName : null
                 * spec : null
                 * sendGiftInfo : null
                 * sendGiftType : null
                 * minMaxPrice : null
                 * inventory : 0
                 * roleAmount :
                 * sendGiftPic : null
                 * activeId : 4680
                 * activeType : 11
                 * activeName : 【丹飞】凤爪
                 * price : ￥1/箱
                 * oldPrice : ￥210.00/箱
                 * flag : 1
                 * soldOutPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/image//f03a8af175424e33a0cd42b36cc3fab7.png
                 * discount : 0.04折
                 */

                private Object productMainId;
                private Object productId;
                private String defaultPic;
                private Object productName;
                private Object spec;
                private Object sendGiftInfo;
                private Object sendGiftType;
                private Object minMaxPrice;
                private int inventory;
                private String roleAmount;
                private Object sendGiftPic;
                private int activeId;
                private int activeType;
                private String activeName;
                private String price;
                private String oldPrice;
                private int flag;
                private String soldOutPic;
                private String discount;

                public Object getProductMainId() {
                    return productMainId;
                }

                public void setProductMainId(Object productMainId) {
                    this.productMainId = productMainId;
                }

                public Object getProductId() {
                    return productId;
                }

                public void setProductId(Object productId) {
                    this.productId = productId;
                }

                public String getDefaultPic() {
                    return defaultPic;
                }

                public void setDefaultPic(String defaultPic) {
                    this.defaultPic = defaultPic;
                }

                public Object getProductName() {
                    return productName;
                }

                public void setProductName(Object productName) {
                    this.productName = productName;
                }

                public Object getSpec() {
                    return spec;
                }

                public void setSpec(Object spec) {
                    this.spec = spec;
                }

                public Object getSendGiftInfo() {
                    return sendGiftInfo;
                }

                public void setSendGiftInfo(Object sendGiftInfo) {
                    this.sendGiftInfo = sendGiftInfo;
                }

                public Object getSendGiftType() {
                    return sendGiftType;
                }

                public void setSendGiftType(Object sendGiftType) {
                    this.sendGiftType = sendGiftType;
                }

                public Object getMinMaxPrice() {
                    return minMaxPrice;
                }

                public void setMinMaxPrice(Object minMaxPrice) {
                    this.minMaxPrice = minMaxPrice;
                }

                public int getInventory() {
                    return inventory;
                }

                public void setInventory(int inventory) {
                    this.inventory = inventory;
                }

                public String getRoleAmount() {
                    return roleAmount;
                }

                public void setRoleAmount(String roleAmount) {
                    this.roleAmount = roleAmount;
                }

                public Object getSendGiftPic() {
                    return sendGiftPic;
                }

                public void setSendGiftPic(Object sendGiftPic) {
                    this.sendGiftPic = sendGiftPic;
                }

                public int getActiveId() {
                    return activeId;
                }

                public void setActiveId(int activeId) {
                    this.activeId = activeId;
                }

                public int getActiveType() {
                    return activeType;
                }

                public void setActiveType(int activeType) {
                    this.activeType = activeType;
                }

                public String getActiveName() {
                    return activeName;
                }

                public void setActiveName(String activeName) {
                    this.activeName = activeName;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getOldPrice() {
                    return oldPrice;
                }

                public void setOldPrice(String oldPrice) {
                    this.oldPrice = oldPrice;
                }

                public int getFlag() {
                    return flag;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public String getSoldOutPic() {
                    return soldOutPic;
                }

                public void setSoldOutPic(String soldOutPic) {
                    this.soldOutPic = soldOutPic;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }
            }
        }
    }
}
