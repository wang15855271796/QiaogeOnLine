package com.puyue.www.qiaoge.model.mine.order;

import java.util.List;

/**
 * Created by ${daff}
 * on 2018/10/26
 * 备注
 */
public class CartGetReductModel {


    /**
     * code : 1
     * message : 成功
     * data : [{"type":0,"deductInfo":"满减：满5.0减1.0;满10.0减2.0;"},{"type":1,"deductInfo":"满赠：满100赠商品和优惠券;"}]
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
         * type : 0
         * deductInfo : 满减：满5.0减1.0;满10.0减2.0;
         */

        private int type;
        private String deductInfo;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDeductInfo() {
            return deductInfo;
        }

        public void setDeductInfo(String deductInfo) {
            this.deductInfo = deductInfo;
        }
    }
}
