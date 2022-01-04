package com.puyue.www.qiaoge.model;

import java.util.List;

public class CancelReasonModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"id":1001,"name":"自己原因","sub_reason":[{"id":1001001,"name":"临时不用车"},{"id":1001002,"name":"下单时间有误"},{"id":1001003,"name":"下单地址有误"}]},{"id":1002,"name":"货物或车辆原因","sub_reason":[{"id":1002001,"name":"车辆装不下货物"},{"id":1002002,"name":"不是我想要的车型"},{"id":1002003,"name":"车辆限行"}]},{"id":1003,"name":"费用原因","sub_reason":[{"id":1003001,"name":"费用没有谈拢（高速费、搬运费、等候费）"},{"id":1003002,"name":"未按平台规定收费"}]},{"id":1004,"name":"司机原因","sub_reason":[{"id":1004001,"name":"联系不上司机"},{"id":1004002,"name":"司机推诿不来"},{"id":1004003,"name":"司机态度恶劣"},{"id":1004004,"name":"司机迟到"},{"id":1004005,"name":"司机要求线下交易"},{"id":1004006,"name":"不是订单显示车辆或司机"}]},{"id":1005,"name":"平台原因","sub_reason":[{"id":1005001,"name":"司机距离太远，不愿意等待"}]}]
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
         * id : 1001
         * name : 自己原因
         * sub_reason : [{"id":1001001,"name":"临时不用车"},{"id":1001002,"name":"下单时间有误"},{"id":1001003,"name":"下单地址有误"}]
         */

        private int id;
        private String name;
        private List<SubReasonBean> sub_reason;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<SubReasonBean> getSub_reason() {
            return sub_reason;
        }

        public void setSub_reason(List<SubReasonBean> sub_reason) {
            this.sub_reason = sub_reason;
        }

        public static class SubReasonBean {
            /**
             * id : 1001001
             * name : 临时不用车
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
