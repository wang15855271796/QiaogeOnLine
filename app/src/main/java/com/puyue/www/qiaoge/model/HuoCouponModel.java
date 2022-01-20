package com.puyue.www.qiaoge.model;

import java.util.List;

public class HuoCouponModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"available_city_str":"全国","available_move_packages_str":"","available_time_periods_str":"","available_week_str":"","bill_perquisite_fee":0,"business_type":"4","business_type_str":"货运券","business_type_tag":"货运券","client_type":"0","client_type_str":"无限制","coupon_code":"JGDTB3PSXC","coupon_id":2067202933078130700,"discount_amount":"500","discount_rate":"100","discount_str":"立减5元","discount_type":"1","ext_remark":"此券不适用于大车车型，不适用于搬运费","mileage_max":0,"mileage_min":0,"mileage_type":0,"order_vehicles_str":"限5米2以下车型可用","packet_id":"47519","packet_name":"开放平台货运券","pay_type":"1","pay_type_str":"下单时支付","preferential":"下单立减","purpose_type":"1","purpose_type_str":"","reach_fen":"0","ref":"","status":"0","tag":"新领券","valid_date":"2021.12.21 12:43~2022.03.20 23:59"},{"available_city_str":"全国","available_move_packages_str":"","available_time_periods_str":"","available_week_str":"","bill_perquisite_fee":0,"business_type":"4","business_type_str":"货运券","business_type_tag":"货运券","client_type":"0","client_type_str":"无限制","coupon_code":"K7O8I07UCT","coupon_id":2067202933078130700,"discount_amount":"500","discount_rate":"100","discount_str":"立减5元","discount_type":"1","ext_remark":"此券不适用于大车车型，不适用于搬运费","mileage_max":0,"mileage_min":0,"mileage_type":0,"order_vehicles_str":"限5米2以下车型可用","packet_id":"47519","packet_name":"开放平台货运券","pay_type":"1","pay_type_str":"下单时支付","preferential":"下单立减","purpose_type":"1","purpose_type_str":"","reach_fen":"0","ref":"","status":"0","tag":"新领券","valid_date":"2021.12.21 12:43~2022.03.20 23:59"},{"available_city_str":"全国","available_move_packages_str":"","available_time_periods_str":"","available_week_str":"","bill_perquisite_fee":0,"business_type":"4","business_type_str":"货运券","business_type_tag":"货运券","client_type":"0","client_type_str":"无限制","coupon_code":"GAXW36X8PA","coupon_id":2067202933078130700,"discount_amount":"500","discount_rate":"100","discount_str":"立减5元","discount_type":"1","ext_remark":"此券不适用于大车车型，不适用于搬运费","mileage_max":0,"mileage_min":0,"mileage_type":0,"order_vehicles_str":"限5米2以下车型可用","packet_id":"47519","packet_name":"开放平台货运券","pay_type":"1","pay_type_str":"下单时支付","preferential":"下单立减","purpose_type":"1","purpose_type_str":"","reach_fen":"0","ref":"","status":"0","tag":"新领券","valid_date":"2021.12.21 12:43~2022.03.20 23:59"},{"available_city_str":"全国","available_move_packages_str":"","available_time_periods_str":"","available_week_str":"","bill_perquisite_fee":0,"business_type":"4","business_type_str":"货运券","business_type_tag":"货运券","client_type":"0","client_type_str":"无限制","coupon_code":"E7YM99KCZ8","coupon_id":2067202933078130700,"discount_amount":"500","discount_rate":"100","discount_str":"立减5元","discount_type":"1","ext_remark":"此券不适用于大车车型，不适用于搬运费","mileage_max":0,"mileage_min":0,"mileage_type":0,"order_vehicles_str":"限5米2以下车型可用","packet_id":"47519","packet_name":"开放平台货运券","pay_type":"1","pay_type_str":"下单时支付","preferential":"下单立减","purpose_type":"1","purpose_type_str":"","reach_fen":"0","ref":"","status":"0","tag":"新领券","valid_date":"2021.12.21 12:43~2022.03.20 23:59"},{"available_city_str":"全国","available_move_packages_str":"","available_time_periods_str":"","available_week_str":"","bill_perquisite_fee":0,"business_type":"4","business_type_str":"货运券","business_type_tag":"货运券","client_type":"0","client_type_str":"无限制","coupon_code":"Z6E7C0I6XE","coupon_id":2067202933078130700,"discount_amount":"500","discount_rate":"100","discount_str":"立减5元","discount_type":"1","ext_remark":"此券不适用于大车车型，不适用于搬运费","mileage_max":0,"mileage_min":0,"mileage_type":0,"order_vehicles_str":"限5米2以下车型可用","packet_id":"47519","packet_name":"开放平台货运券","pay_type":"1","pay_type_str":"下单时支付","preferential":"下单立减","purpose_type":"1","purpose_type_str":"","reach_fen":"0","ref":"","status":"0","tag":"新领券","valid_date":"2021.12.21 12:43~2022.03.20 23:59"}]
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
         * available_city_str : 全国
         * available_move_packages_str :
         * available_time_periods_str :
         * available_week_str :
         * bill_perquisite_fee : 0
         * business_type : 4
         * business_type_str : 货运券
         * business_type_tag : 货运券
         * client_type : 0
         * client_type_str : 无限制
         * coupon_code : JGDTB3PSXC
         * coupon_id : 2067202933078130700
         * discount_amount : 500
         * discount_rate : 100
         * discount_str : 立减5元
         * discount_type : 1
         * ext_remark : 此券不适用于大车车型，不适用于搬运费
         * mileage_max : 0
         * mileage_min : 0
         * mileage_type : 0
         * order_vehicles_str : 限5米2以下车型可用
         * packet_id : 47519
         * packet_name : 开放平台货运券
         * pay_type : 1
         * pay_type_str : 下单时支付
         * preferential : 下单立减
         * purpose_type : 1
         * purpose_type_str :
         * reach_fen : 0
         * ref :
         * status : 0
         * tag : 新领券
         * valid_date : 2021.12.21 12:43~2022.03.20 23:59
         */

        private String available_city_str;
        private String available_move_packages_str;
        private String available_time_periods_str;
        private String available_week_str;
        private int bill_perquisite_fee;
        private String business_type;
        private String business_type_str;
        private String business_type_tag;
        private String client_type;
        private String client_type_str;
        private String coupon_code;
        private String coupon_id;
        private String discount_amount;
        private String discount_rate;
        private String discount_str;
        private int discount_type;
        private String ext_remark;
        private int mileage_max;
        private int mileage_min;
        private int mileage_type;
        private String order_vehicles_str;
        private String packet_id;
        private String packet_name;
        private String pay_type;
        private String pay_type_str;
        private String preferential;
        private String purpose_type;
        private String purpose_type_str;
        private String reach_fen;
        private String ref;
        private String status;
        private String tag;
        private String valid_date;

        public String getAvailable_city_str() {
            return available_city_str;
        }

        public void setAvailable_city_str(String available_city_str) {
            this.available_city_str = available_city_str;
        }

        public String getAvailable_move_packages_str() {
            return available_move_packages_str;
        }

        public void setAvailable_move_packages_str(String available_move_packages_str) {
            this.available_move_packages_str = available_move_packages_str;
        }

        public String getAvailable_time_periods_str() {
            return available_time_periods_str;
        }

        public void setAvailable_time_periods_str(String available_time_periods_str) {
            this.available_time_periods_str = available_time_periods_str;
        }

        public String getAvailable_week_str() {
            return available_week_str;
        }

        public void setAvailable_week_str(String available_week_str) {
            this.available_week_str = available_week_str;
        }

        public int getBill_perquisite_fee() {
            return bill_perquisite_fee;
        }

        public void setBill_perquisite_fee(int bill_perquisite_fee) {
            this.bill_perquisite_fee = bill_perquisite_fee;
        }

        public String getBusiness_type() {
            return business_type;
        }

        public void setBusiness_type(String business_type) {
            this.business_type = business_type;
        }

        public String getBusiness_type_str() {
            return business_type_str;
        }

        public void setBusiness_type_str(String business_type_str) {
            this.business_type_str = business_type_str;
        }

        public String getBusiness_type_tag() {
            return business_type_tag;
        }

        public void setBusiness_type_tag(String business_type_tag) {
            this.business_type_tag = business_type_tag;
        }

        public String getClient_type() {
            return client_type;
        }

        public void setClient_type(String client_type) {
            this.client_type = client_type;
        }

        public String getClient_type_str() {
            return client_type_str;
        }

        public void setClient_type_str(String client_type_str) {
            this.client_type_str = client_type_str;
        }

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public String getDiscount_rate() {
            return discount_rate;
        }

        public void setDiscount_rate(String discount_rate) {
            this.discount_rate = discount_rate;
        }

        public String getDiscount_str() {
            return discount_str;
        }

        public void setDiscount_str(String discount_str) {
            this.discount_str = discount_str;
        }

        public int getDiscount_type() {
            return discount_type;
        }

        public void setDiscount_type(int discount_type) {
            this.discount_type = discount_type;
        }

        public String getExt_remark() {
            return ext_remark;
        }

        public void setExt_remark(String ext_remark) {
            this.ext_remark = ext_remark;
        }

        public int getMileage_max() {
            return mileage_max;
        }

        public void setMileage_max(int mileage_max) {
            this.mileage_max = mileage_max;
        }

        public int getMileage_min() {
            return mileage_min;
        }

        public void setMileage_min(int mileage_min) {
            this.mileage_min = mileage_min;
        }

        public int getMileage_type() {
            return mileage_type;
        }

        public void setMileage_type(int mileage_type) {
            this.mileage_type = mileage_type;
        }

        public String getOrder_vehicles_str() {
            return order_vehicles_str;
        }

        public void setOrder_vehicles_str(String order_vehicles_str) {
            this.order_vehicles_str = order_vehicles_str;
        }

        public String getPacket_id() {
            return packet_id;
        }

        public void setPacket_id(String packet_id) {
            this.packet_id = packet_id;
        }

        public String getPacket_name() {
            return packet_name;
        }

        public void setPacket_name(String packet_name) {
            this.packet_name = packet_name;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_type_str() {
            return pay_type_str;
        }

        public void setPay_type_str(String pay_type_str) {
            this.pay_type_str = pay_type_str;
        }

        public String getPreferential() {
            return preferential;
        }

        public void setPreferential(String preferential) {
            this.preferential = preferential;
        }

        public String getPurpose_type() {
            return purpose_type;
        }

        public void setPurpose_type(String purpose_type) {
            this.purpose_type = purpose_type;
        }

        public String getPurpose_type_str() {
            return purpose_type_str;
        }

        public void setPurpose_type_str(String purpose_type_str) {
            this.purpose_type_str = purpose_type_str;
        }

        public String getReach_fen() {
            return reach_fen;
        }

        public void setReach_fen(String reach_fen) {
            this.reach_fen = reach_fen;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getValid_date() {
            return valid_date;
        }

        public void setValid_date(String valid_date) {
            this.valid_date = valid_date;
        }
    }
}
