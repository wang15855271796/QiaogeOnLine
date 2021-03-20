package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/18
 */
public class OrderModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"id":392314,"orderId":"20200908164737-2697","totalAmount":32,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-09-08 16:47:37","payDate":"2020-09-08 16:59:36","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":392318,"orderId":"20200908170023-bb8d","totalAmount":30.37,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-09-08 17:00:24","payDate":"2020-09-08 17:06:17","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":392393,"orderId":"20200911100818-d4a3","totalAmount":489,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-09-11 10:08:19","payDate":"2020-09-11 10:08:32","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":392644,"orderId":"20201008163307-a48b","totalAmount":160,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-10-08 16:33:08","payDate":"2020-10-08 16:33:17","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-10-19 10:37:36"},{"id":392892,"orderId":"20201019091559-03b8","totalAmount":288.94,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-10-19 09:16:00","payDate":"2020-10-19 09:16:11","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":392907,"orderId":"20201019142324-b2d7","totalAmount":184,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-10-19 14:23:24","payDate":"2020-10-19 14:25:38","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":392909,"orderId":"20201019152507-7998","totalAmount":403,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-10-19 15:25:08","payDate":"2020-10-19 15:25:17","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":393147,"orderId":"20201022171249-6337","totalAmount":2.1,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-10-22 17:12:50","payDate":"2020-10-22 17:13:16","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":393307,"orderId":"20201102091958-c340","totalAmount":44.62,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-02 09:19:59","payDate":"2020-11-02 09:27:54","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":393308,"orderId":"20201102091958-bf18","totalAmount":160,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-02 09:19:59","payDate":"2020-11-02 09:27:54","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-07 14:17:38"},{"id":393309,"orderId":"20201102093854-2d61","totalAmount":49.8,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-11-02 09:38:55","payDate":"2020-11-02 09:39:10","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":393313,"orderId":"20201102094327-f29b","totalAmount":49.8,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-11-02 09:43:28","payDate":"2020-11-02 09:43:43","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":393390,"orderId":"20201103111942-208b","totalAmount":51.9,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-03 11:19:43","payDate":"2020-11-03 11:19:51","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":393721,"orderId":"20201106164239-87e3","totalAmount":9,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-06 16:42:39","payDate":"2020-11-06 16:42:48","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":393725,"orderId":"20201109084732-28de","totalAmount":11,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-09 08:47:33","payDate":"2020-11-09 08:47:53","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":394966,"orderId":"20201116100944-47eb","totalAmount":25.5,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-16 10:09:45","payDate":"2020-11-16 10:09:55","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":394977,"orderId":"20201117111525-b520","totalAmount":12.95,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-11-17 11:15:25","payDate":"2020-11-17 11:15:40","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":394978,"orderId":"20201117111614-a8e8","totalAmount":6.7,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-11-17 11:16:15","payDate":"2020-11-17 11:16:23","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":394979,"orderId":"20201117111723-9454","totalAmount":6.7,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-11-17 11:17:23","payDate":"2020-11-17 11:17:31","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":396719,"orderId":"20201204091649-68de","totalAmount":70,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2020-12-04 09:16:49","payDate":"2020-12-04 09:17:00","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":397826,"orderId":"20201216125717-087c","totalAmount":350,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-12-16 12:57:18","payDate":"2020-12-16 12:57:26","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":397830,"orderId":"20201216141837-29fd","totalAmount":13,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-12-16 14:18:37","payDate":"2020-12-16 14:18:46","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":397832,"orderId":"20201216145746-9072","totalAmount":65.59,"orderStatus":14,"orderStatusStr":"待发货-已接收","gmtCreate":"2020-12-16 14:57:46","payDate":"2020-12-16 14:57:54","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2020-12-21 14:04:19"},{"id":398043,"orderId":"20210104142303-a991","totalAmount":1032.51,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2021-01-04 14:23:03","payDate":"2021-01-04 14:23:13","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"},{"id":398044,"orderId":"20210104142303-9dd6","totalAmount":43,"orderStatus":2,"orderStatusStr":"待发货-待接收","gmtCreate":"2021-01-04 14:23:03","payDate":"2021-01-04 14:23:13","confirmDate":"2021-01-18 14:18:59","waitSendReceiveTime":"2021-01-18 14:18:59"}]
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
         * id : 392314
         * orderId : 20200908164737-2697
         * totalAmount : 32.0
         * orderStatus : 2
         * orderStatusStr : 待发货-待接收
         * gmtCreate : 2020-09-08 16:47:37
         * payDate : 2020-09-08 16:59:36
         * confirmDate : 2021-01-18 14:18:59
         * waitSendReceiveTime : 2021-01-18 14:18:59
         */

        private int id;
        private String orderId;
        private double totalAmount;
        private int orderStatus;
        private String orderStatusStr;
        private String gmtCreate;
        private String payDate;
        private String confirmDate;
        private String waitSendReceiveTime;
        String orderDeliveryType;
        public long sysCurrentTime;
        public long orderOverTime;

        public long getSysCurrentTime() {
            return sysCurrentTime;
        }

        public void setSysCurrentTime(long sysCurrentTime) {
            this.sysCurrentTime = sysCurrentTime;
        }

        public long getOrderOverTime() {
            return orderOverTime;
        }

        public void setOrderOverTime(long orderOverTime) {
            this.orderOverTime = orderOverTime;
        }

        public String getOrderDeliveryType() {
            return orderDeliveryType;
        }

        public void setOrderDeliveryType(String orderDeliveryType) {
            this.orderDeliveryType = orderDeliveryType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getOrderStatusStr() {
            return orderStatusStr;
        }

        public void setOrderStatusStr(String orderStatusStr) {
            this.orderStatusStr = orderStatusStr;
        }

        public String getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtCreate(String gmtCreate) {
            this.gmtCreate = gmtCreate;
        }

        public String getPayDate() {
            return payDate;
        }

        public void setPayDate(String payDate) {
            this.payDate = payDate;
        }

        public String getConfirmDate() {
            return confirmDate;
        }

        public void setConfirmDate(String confirmDate) {
            this.confirmDate = confirmDate;
        }

        public String getWaitSendReceiveTime() {
            return waitSendReceiveTime;
        }

        public void setWaitSendReceiveTime(String waitSendReceiveTime) {
            this.waitSendReceiveTime = waitSendReceiveTime;
        }
    }
}
