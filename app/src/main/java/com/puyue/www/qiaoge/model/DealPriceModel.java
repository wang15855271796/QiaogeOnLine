package com.puyue.www.qiaoge.model;

import java.io.Serializable;
import java.util.List;

public class DealPriceModel {

    /**
     * code : 1
     * message : 成功
     * data : {"distance_total":22038,"exceed_distance":17038,"total_price_fen":7930,"total_price":79.3,"calculate_price_info":[{"amount":79.3,"amount_fen":7930,"name":"基本路费","type":1},{"amount":0,"amount_fen":0,"name":"搬运","type":4},{"amount":0,"amount_fen":0,"name":"小推车","type":4}],"coupon_item":null,"price_calculate_id":"2066611207654891520"}
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
         * distance_total : 22038
         * exceed_distance : 17038
         * total_price_fen : 7930
         * total_price : 79.3
         * calculate_price_info : [{"amount":79.3,"amount_fen":7930,"name":"基本路费","type":1},{"amount":0,"amount_fen":0,"name":"搬运","type":4},{"amount":0,"amount_fen":0,"name":"小推车","type":4}]
         * coupon_item : null
         * price_calculate_id : 2066611207654891520
         */

        private int distance_total;
        private int exceed_distance;
        private int total_price_fen;
        private String total_price;
        private Object coupon_item;
        private String price_calculate_id;
        private List<CalculatePriceInfoBean> calculate_price_info;

        @Override
        public String toString() {
            return "DataBean{" +
                    "distance_total=" + distance_total +
                    ", exceed_distance=" + exceed_distance +
                    ", total_price_fen=" + total_price_fen +
                    ", total_price='" + total_price + '\'' +
                    ", coupon_item=" + coupon_item +
                    ", price_calculate_id='" + price_calculate_id + '\'' +
                    ", calculate_price_info=" + calculate_price_info +
                    '}';
        }

        public int getDistance_total() {
            return distance_total;
        }

        public void setDistance_total(int distance_total) {
            this.distance_total = distance_total;
        }

        public int getExceed_distance() {
            return exceed_distance;
        }

        public void setExceed_distance(int exceed_distance) {
            this.exceed_distance = exceed_distance;
        }

        public int getTotal_price_fen() {
            return total_price_fen;
        }

        public void setTotal_price_fen(int total_price_fen) {
            this.total_price_fen = total_price_fen;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public Object getCoupon_item() {
            return coupon_item;
        }

        public void setCoupon_item(Object coupon_item) {
            this.coupon_item = coupon_item;
        }

        public String getPrice_calculate_id() {
            return price_calculate_id;
        }

        public void setPrice_calculate_id(String price_calculate_id) {
            this.price_calculate_id = price_calculate_id;
        }

        public List<CalculatePriceInfoBean> getCalculate_price_info() {
            return calculate_price_info;
        }

        public void setCalculate_price_info(List<CalculatePriceInfoBean> calculate_price_info) {
            this.calculate_price_info = calculate_price_info;
        }

        public static class CalculatePriceInfoBean implements Serializable {
            /**
             * amount : 79.3
             * amount_fen : 7930
             * name : 基本路费
             * type : 1
             */

            private String amount;
            private int amount_fen;
            private String name;
            private int type;

            @Override
            public String toString() {
                return "CalculatePriceInfoBean{" +
                        "amount='" + amount + '\'' +
                        ", amount_fen=" + amount_fen +
                        ", name='" + name + '\'' +
                        ", type=" + type +
                        '}';
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public int getAmount_fen() {
                return amount_fen;
            }

            public void setAmount_fen(int amount_fen) {
                this.amount_fen = amount_fen;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
