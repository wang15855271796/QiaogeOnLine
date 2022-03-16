package com.puyue.www.qiaoge.model;

import java.util.List;

public class HuoListModel {


    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":1,"total":1,"list":[{"order_time":"12月21日 12:00","order_status_name":"订单逾时","send_address":"杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角起梦科创园","receive_address":"杭州市余杭区天目山西路258号雅居乐国际花园","order_display_id":"1473132059932241999"}],"hasPrePage":false,"hasNextPage":false}
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
         * list : [{"order_time":"12月21日 12:00","order_status_name":"订单逾时","send_address":"杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角起梦科创园","receive_address":"杭州市余杭区天目山西路258号雅居乐国际花园","order_display_id":"1473132059932241999"}]
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
             * order_time : 12月21日 12:00
             * order_status_name : 订单逾时
             * send_address : 杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角起梦科创园
             * receive_address : 杭州市余杭区天目山西路258号雅居乐国际花园
             * order_display_id : 1473132059932241999
             */

            private String order_time;
            private String order_status_name;
            private String send_address;
            private String receive_address;
            private String order_display_id;
            private String totalPrice;
            private int flag;

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getOrder_time() {
                return order_time;
            }

            public void setOrder_time(String order_time) {
                this.order_time = order_time;
            }

            public String getOrder_status_name() {
                return order_status_name;
            }

            public void setOrder_status_name(String order_status_name) {
                this.order_status_name = order_status_name;
            }

            public String getSend_address() {
                return send_address;
            }

            public void setSend_address(String send_address) {
                this.send_address = send_address;
            }

            public String getReceive_address() {
                return receive_address;
            }

            public void setReceive_address(String receive_address) {
                this.receive_address = receive_address;
            }

            public String getOrder_display_id() {
                return order_display_id;
            }

            public void setOrder_display_id(String order_display_id) {
                this.order_display_id = order_display_id;
            }
        }
    }
}
