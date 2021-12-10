package com.puyue.www.qiaoge.model;

import java.util.List;

public class CartFullModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"deductInfo":"优惠一：满5.0减1.0","sendProds":null},{"deductInfo":"优惠二：满10.0减2.0","sendProds":null}]
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
         * deductInfo : 优惠一：满5.0减1.0
         * sendProds : null
         */

        private String deductInfo;
        private List<SendProdsBean> sendProds;

        public String getDeductInfo() {
            return deductInfo;
        }

        public void setDeductInfo(String deductInfo) {
            this.deductInfo = deductInfo;
        }

        public List<SendProdsBean> getSendProds() {
            return sendProds;
        }

        public void setSendProds(List<SendProdsBean> sendProds) {
            this.sendProds = sendProds;
        }

        public static class SendProdsBean {
            /**
             * type : 0
             * name : 特价鸡排308-1
             * defaultPic : https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/product/ba19ae087d064d5a9ccd5d6ea1516563.png
             * amount : null
             * spec : s23
             * sendNum : 1x
             * roles : null
             * dateTime : null
             */

            private int type;
            private String name;
            private String defaultPic;
            private String amount;
            private String spec;
            private String sendNum;
            private String roles;
            private String dateTime;
            private int productMainId;
            String giftProdUseType;
            String poolNo;

            public String getPoolNo() {
                return poolNo;
            }

            public void setPoolNo(String poolNo) {
                this.poolNo = poolNo;
            }

            public String getGiftProdUseType() {
                return giftProdUseType;
            }

            public void setGiftProdUseType(String giftProdUseType) {
                this.giftProdUseType = giftProdUseType;
            }

            public int getProductMainId() {
                return productMainId;
            }

            public void setProductMainId(int productMainId) {
                this.productMainId = productMainId;
            }

            public String getRoles() {
                return roles;
            }

            public void setRoles(String roles) {
                this.roles = roles;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDefaultPic() {
                return defaultPic;
            }

            public void setDefaultPic(String defaultPic) {
                this.defaultPic = defaultPic;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getSpec() {
                return spec;
            }

            public void setSpec(String spec) {
                this.spec = spec;
            }

            public String getSendNum() {
                return sendNum;
            }

            public void setSendNum(String sendNum) {
                this.sendNum = sendNum;
            }



            public String getDateTime() {
                return dateTime;
            }

            public void setDateTime(String dateTime) {
                this.dateTime = dateTime;
            }
        }
    }
}
