package com.puyue.www.qiaoge.model;

import java.util.List;

public class CarPriceModel {


    /**
     * code : 1
     * message : 成功
     * data : {"name":"杭州","vehicle_name":"测试测-new","image_url_high_light":"https://oimg.huolala.cn/ops2/rs/dist/images/van_big_v2.png","vehicle_volume_text":"13.0方","vehicle_weight_text":"1.0吨","vehicle_size":"3.6*1.8*2.0","text_desc":"xc","basicFee":[{"name":"起步价(19公里)","desc":"15元"},{"name":"分段价(20公里及以上)","desc":"5元/公里"}],"stopFee":[{"name":"高速费","desc":"凭票据实收"},{"name":"停车费","desc":"凭票据实收"}],"carSpec":null,"otherReq":[{"name":"搬运","desc":"司机另议"},{"name":"返程","desc":"附加40%路费"},{"name":"小推车","desc":"附加50"},{"name":"拍照回单","desc":"附加60"},{"name":"纸质回单","desc":"附加70"}]}
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
         * name : 杭州
         * vehicle_name : 测试测-new
         * image_url_high_light : https://oimg.huolala.cn/ops2/rs/dist/images/van_big_v2.png
         * vehicle_volume_text : 13.0方
         * vehicle_weight_text : 1.0吨
         * vehicle_size : 3.6*1.8*2.0
         * text_desc : xc
         * basicFee : [{"name":"起步价(19公里)","desc":"15元"},{"name":"分段价(20公里及以上)","desc":"5元/公里"}]
         * stopFee : [{"name":"高速费","desc":"凭票据实收"},{"name":"停车费","desc":"凭票据实收"}]
         * carSpec : null
         * otherReq : [{"name":"搬运","desc":"司机另议"},{"name":"返程","desc":"附加40%路费"},{"name":"小推车","desc":"附加50"},{"name":"拍照回单","desc":"附加60"},{"name":"纸质回单","desc":"附加70"}]
         */

        private String name;
        private String vehicle_name;
        private String image_url_high_light;
        private String vehicle_volume_text;
        private String vehicle_weight_text;
        private String vehicle_size;
        private String text_desc;
        private List<CarSpecBean> carSpec;
        private List<BasicFeeBean> basicFee;
        private List<StopFeeBean> stopFee;
        private List<OtherReqBean> otherReq;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVehicle_name() {
            return vehicle_name;
        }

        public void setVehicle_name(String vehicle_name) {
            this.vehicle_name = vehicle_name;
        }

        public String getImage_url_high_light() {
            return image_url_high_light;
        }

        public void setImage_url_high_light(String image_url_high_light) {
            this.image_url_high_light = image_url_high_light;
        }

        public String getVehicle_volume_text() {
            return vehicle_volume_text;
        }

        public void setVehicle_volume_text(String vehicle_volume_text) {
            this.vehicle_volume_text = vehicle_volume_text;
        }

        public String getVehicle_weight_text() {
            return vehicle_weight_text;
        }

        public void setVehicle_weight_text(String vehicle_weight_text) {
            this.vehicle_weight_text = vehicle_weight_text;
        }

        public String getVehicle_size() {
            return vehicle_size;
        }

        public void setVehicle_size(String vehicle_size) {
            this.vehicle_size = vehicle_size;
        }

        public String getText_desc() {
            return text_desc;
        }

        public void setText_desc(String text_desc) {
            this.text_desc = text_desc;
        }

        public List<CarSpecBean> getCarSpec() {
            return carSpec;
        }

        public void setCarSpec(List<CarSpecBean> carSpec) {
            this.carSpec = carSpec;
        }

        public List<BasicFeeBean> getBasicFee() {
            return basicFee;
        }

        public void setBasicFee(List<BasicFeeBean> basicFee) {
            this.basicFee = basicFee;
        }

        public List<StopFeeBean> getStopFee() {
            return stopFee;
        }

        public void setStopFee(List<StopFeeBean> stopFee) {
            this.stopFee = stopFee;
        }

        public List<OtherReqBean> getOtherReq() {
            return otherReq;
        }

        public void setOtherReq(List<OtherReqBean> otherReq) {
            this.otherReq = otherReq;
        }

        public static class CarSpecBean {
            /**
             * name : 起步价(19公里)
             * desc : 15元
             */

            private String name;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class BasicFeeBean {
            /**
             * name : 起步价(19公里)
             * desc : 15元
             */

            private String name;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class StopFeeBean {
            /**
             * name : 高速费
             * desc : 凭票据实收
             */

            private String name;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }

        public static class OtherReqBean {
            /**
             * name : 搬运
             * desc : 司机另议
             */

            private String name;
            private String desc;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
    }
}
