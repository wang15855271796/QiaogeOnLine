package com.puyue.www.qiaoge.model;

import java.util.List;

public class PriceTrendModel {


    /**
     * code : 1
     * message : 成功
     * data : {"productName":"商品名称:【昶烨】德式肉排","spec":"规格：15串*64g*10包/箱-960g*10包/箱","dateTime":"首次上架日期：2018-05-26","salePrice":"当前售价：1箱=145.00元","upOrDown":0,"quoteChange":"+0.00%","trends":[{"dateTime":"2020-12-01","price":145},{"dateTime":"2021-12-01","price":145}]}
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
         * productName : 商品名称:【昶烨】德式肉排
         * spec : 规格：15串*64g*10包/箱-960g*10包/箱
         * dateTime : 首次上架日期：2018-05-26
         * salePrice : 当前售价：1箱=145.00元
         * upOrDown : 0
         * quoteChange : +0.00%
         * trends : [{"dateTime":"2020-12-01","price":145},{"dateTime":"2021-12-01","price":145}]
         */

        private String productName;
        private String spec;
        private String dateTime;
        private String salePrice;
        private int upOrDown;
        private String quoteChange;
        private List<TrendsBean> trends;

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

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public int getUpOrDown() {
            return upOrDown;
        }

        public void setUpOrDown(int upOrDown) {
            this.upOrDown = upOrDown;
        }

        public String getQuoteChange() {
            return quoteChange;
        }

        public void setQuoteChange(String quoteChange) {
            this.quoteChange = quoteChange;
        }

        public List<TrendsBean> getTrends() {
            return trends;
        }

        public void setTrends(List<TrendsBean> trends) {
            this.trends = trends;
        }

        public static class TrendsBean {
            /**
             * dateTime : 2020-12-01
             * price : 145
             */

            private String dateTime;
            private String price;

            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }
        }
    }
}
