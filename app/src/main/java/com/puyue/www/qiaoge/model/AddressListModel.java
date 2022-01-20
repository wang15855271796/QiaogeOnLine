package com.puyue.www.qiaoge.model;

import java.io.Serializable;
import java.util.List;

public class AddressListModel {

    /**
     * code : 1
     * message : 成功
     * data : [{"district_name":"余杭区","place_id":"20017918901077815936","addr":"浙江省杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角","name":"起梦科创园","distance":"902m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.357762,"lon":120.05528},"children":[]},{"district_name":"余杭区","place_id":"20088713197107839256","addr":"浙江省杭州市余杭区","name":"起梦科创园(停车场)","distance":"918m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.35789,"lon":120.054916},"children":[]},{"district_name":"余杭区","place_id":"B0H2HOL4CG","addr":"古墩路1899号","name":"起梦科创园物业","distance":"854m","city_name":"杭州市","city_id":1011,"tag":"公司企业，公司，公司","adcode":"330110","lat_lon":{"lat":30.358272,"lon":120.05541},"children":[]},{"district_name":"余杭区","place_id":"20088712778646323576","addr":"浙江省杭州市余杭区","name":"起梦科创园(地下停车场)","distance":"892m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.358225,"lon":120.054933},"children":[]},{"district_name":"余杭区","place_id":"20088712634316125566","addr":"浙江省杭州市余杭区","name":"起梦科创园停车场(出入口)","distance":"889m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.358415,"lon":120.054783},"children":[]},{"district_name":"余杭区","place_id":"20088712777258012986","addr":"浙江省杭州市余杭区","name":"起梦科创园地下停车场(入口)","distance":"882m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.358326,"lon":120.054969},"children":[]},{"district_name":"余杭区","place_id":"20088713069521301685","addr":"浙江省杭州市余杭区","name":"起梦科创园地下停车场(出口)","distance":"885m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.35805,"lon":120.055204},"children":[]},{"district_name":"余杭区","place_id":"20056116239667306878","addr":"浙江省杭州市余杭区起梦科创园A1栋14层","name":"翘歌集团","distance":"902m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.357763,"lon":120.055286},"children":[]},{"district_name":"余杭区","place_id":"20080915621725901079","addr":"浙江省杭州市余杭区良渚街道起梦科创园2栋701室","name":"杭州承信洁邦环境工程有限公司","distance":"873m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.357805,"lon":120.055664},"children":[]},{"district_name":"余杭区","place_id":"20017919469447946591","addr":"浙江省杭州市余杭区古墩路起梦科创园1栋17楼","name":"导行供应链管理有限公司","distance":"854m","city_name":"杭州市","city_id":1011,"tag":"","adcode":"330110","lat_lon":{"lat":30.358498,"lon":120.05517},"children":[]}]
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

    public static class DataBean implements Serializable {
        /**
         * district_name : 余杭区
         * place_id : 20017918901077815936
         * addr : 浙江省杭州市余杭区良渚街道古墩路与疏港公路交汇处西南角
         * name : 起梦科创园
         * distance : 902m
         * city_name : 杭州市
         * city_id : 1011
         * tag :
         * adcode : 330110
         * lat_lon : {"lat":30.357762,"lon":120.05528}
         * children : []
         */

        private String district_name;
        private String place_id;
        private String addr;
        private String name;
        private String distance;
        private String city_name;
        private String city_id;
        private String tag;
        private String adcode;
        private LatLonBean lat_lon;

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public LatLonBean getLat_lon() {
            return lat_lon;
        }

        public void setLat_lon(LatLonBean lat_lon) {
            this.lat_lon = lat_lon;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "district_name='" + district_name + '\'' +
                    ", place_id='" + place_id + '\'' +
                    ", addr='" + addr + '\'' +
                    ", name='" + name + '\'' +
                    ", distance='" + distance + '\'' +
                    ", city_name='" + city_name + '\'' +
                    ", city_id='" + city_id + '\'' +
                    ", tag='" + tag + '\'' +
                    ", adcode='" + adcode + '\'' +
                    ", lat_lon=" + lat_lon +
                    '}';
        }

        public static class LatLonBean implements Serializable{
            /**
             * lat : 30.357762
             * lon : 120.05528
             */

            private String lat;
            private String lon;

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }

            @Override
            public String toString() {
                return "LatLonBean{" +
                        "lat=" + lat +
                        ", lon=" + lon +
                        '}';
            }
        }
    }
}
