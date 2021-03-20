package com.puyue.www.qiaoge.model;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/8
 */
public class OrdersModel {


    /**
     * code : 1
     * message : 成功
     * data : {"pageNum":1,"pageSize":10,"startRow":0,"pages":5,"total":44,"list":[{"selfOrNot":1,"orderId":"20200930155454-1c22","commonOrderNo":"20200930155454-255c","prodName":"【五丰冷食】猪肉馅等3件商品","orderStatus":7,"orderStatusName":"已取消","totalAmount":"22.02","orderTime":"2020-09-30 15:54:55","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png"]},{"selfOrNot":0,"orderId":"20200924091152-af20","commonOrderNo":"20200924091152-af20","prodName":"【骏豪】面筋串1","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"11.00","orderTime":"2020-09-24 09:11:52","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8329d2d4b883490c928cd02d1a7d5dba.png"]},{"selfOrNot":0,"orderId":"20200924090515-fbf8","commonOrderNo":"20200924090515-fbf8","prodName":"【翘歌】无骨鸡柳条1","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"10.00","orderTime":"2020-09-24 09:05:16","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//e4eb483429774ee08695a9e78061f29a.png"]},{"selfOrNot":0,"orderId":"20200923144117-2557","commonOrderNo":"20200923144117-2557","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:41:17","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200923142225-7c02","commonOrderNo":"20200923142225-7c02","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:22:26","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200923141717-03fe","commonOrderNo":"20200923141717-03fe","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:17:17","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200919154156-a38f","commonOrderNo":"20200919154156-a38f","prodName":"【五丰冷食】猪肉馅","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"0.01","orderTime":"2020-09-19 15:41:57","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"]},{"selfOrNot":0,"orderId":"20200919153454-223c","commonOrderNo":"20200919153454-223c","prodName":"【五丰冷食】香菇菜包","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"0.01","orderTime":"2020-09-19 15:34:55","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"]},{"selfOrNot":0,"orderId":"20200919105832-f2ce","commonOrderNo":"20200919105832-f2ce","prodName":"【三全快厨】香菇素菜包","orderStatus":3,"orderStatusName":"待收货","totalAmount":"316.68","orderTime":"2020-09-19 10:58:33","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/527624d6a6be4786a64beb61e3c2aa3d.jpg"]}],"hasPrePage":false,"hasNextPage":true}
     * error : false
     * success : true
     */

    public int code;
    public String message;
    public DataBean data;
    public boolean error;
    public boolean success;



    public static class DataBean {
        /**
         * pageNum : 1
         * pageSize : 10
         * startRow : 0
         * pages : 5
         * total : 44
         * list : [{"selfOrNot":1,"orderId":"20200930155454-1c22","commonOrderNo":"20200930155454-255c","prodName":"【五丰冷食】猪肉馅等3件商品","orderStatus":7,"orderStatusName":"已取消","totalAmount":"22.02","orderTime":"2020-09-30 15:54:55","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png"]},{"selfOrNot":0,"orderId":"20200924091152-af20","commonOrderNo":"20200924091152-af20","prodName":"【骏豪】面筋串1","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"11.00","orderTime":"2020-09-24 09:11:52","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8329d2d4b883490c928cd02d1a7d5dba.png"]},{"selfOrNot":0,"orderId":"20200924090515-fbf8","commonOrderNo":"20200924090515-fbf8","prodName":"【翘歌】无骨鸡柳条1","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"10.00","orderTime":"2020-09-24 09:05:16","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//e4eb483429774ee08695a9e78061f29a.png"]},{"selfOrNot":0,"orderId":"20200923144117-2557","commonOrderNo":"20200923144117-2557","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:41:17","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200923142225-7c02","commonOrderNo":"20200923142225-7c02","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:22:26","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200923141717-03fe","commonOrderNo":"20200923141717-03fe","prodName":"【百事】可乐-G1","orderStatus":7,"orderStatusName":"已取消","totalAmount":"0.01","orderTime":"2020-09-23 14:17:17","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//8cbd52a856f34d30b0f1a136c7a277d5.png"]},{"selfOrNot":0,"orderId":"20200919154156-a38f","commonOrderNo":"20200919154156-a38f","prodName":"【五丰冷食】猪肉馅","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"0.01","orderTime":"2020-09-19 15:41:57","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"]},{"selfOrNot":0,"orderId":"20200919153454-223c","commonOrderNo":"20200919153454-223c","prodName":"【五丰冷食】香菇菜包","orderStatus":2,"orderStatusName":"待发货-待接收","totalAmount":"0.01","orderTime":"2020-09-19 15:34:55","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png"]},{"selfOrNot":0,"orderId":"20200919105832-f2ce","commonOrderNo":"20200919105832-f2ce","prodName":"【三全快厨】香菇素菜包","orderStatus":3,"orderStatusName":"待收货","totalAmount":"316.68","orderTime":"2020-09-19 10:58:33","pics":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/527624d6a6be4786a64beb61e3c2aa3d.jpg"]}]
         * hasPrePage : false
         * hasNextPage : true
         */

        public int pageNum;
        public int pageSize;
        public int startRow;
        public int pages;
        public int total;
        public boolean hasPrePage;
        public boolean hasNextPage;
        public List<ListBean> list;



        public static class ListBean {
            /**
             * selfOrNot : 1
             * orderId : 20200930155454-1c22
             * commonOrderNo : 20200930155454-255c
             * prodName : 【五丰冷食】猪肉馅等3件商品
             * orderStatus : 7
             * orderStatusName : 已取消
             * totalAmount : 22.02
             * orderTime : 2020-09-30 15:54:55
             * pics : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/common-img/5103acef0de94f9b967e6220cbe754bf2.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product//ff21ee51bd8e4d4e93f114785fdf7a5a.png"]
             */

            public String selfOrNot;
            public String orderId;
            public String commonOrderNo;
            public String prodName;
            public int orderStatus;
            public String orderStatusName;
            public String totalAmount;
            public String orderTime;
            public List<String> pics;
            public String subBuyPhone;
            public String returnOrderStatus;
            public String returnOrderStatusStr;
            public String returnProductMainId;
        }
    }
}
