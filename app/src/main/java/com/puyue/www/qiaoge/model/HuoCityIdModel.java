package com.puyue.www.qiaoge.model;

public class HuoCityIdModel {


    /**
     * code : 1
     * message : 成功
     * data : {"adcode":"640100","is_hot":0,"name":"银川","lat_lon":{"lat":38.486471,"lon":106.232471},"name_en":"yinchuan","city_id":1108,"first_en":null}
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
         * adcode : 640100
         * is_hot : 0
         * name : 银川
         * lat_lon : {"lat":38.486471,"lon":106.232471}
         * name_en : yinchuan
         * city_id : 1108
         * first_en : null
         */

        private String adcode;
        private int is_hot;
        private String name;
        private LatLonBean lat_lon;
        private String name_en;
        private String city_id;
        private Object first_en;

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LatLonBean getLat_lon() {
            return lat_lon;
        }

        public void setLat_lon(LatLonBean lat_lon) {
            this.lat_lon = lat_lon;
        }

        public String getName_en() {
            return name_en;
        }

        public void setName_en(String name_en) {
            this.name_en = name_en;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public Object getFirst_en() {
            return first_en;
        }

        public void setFirst_en(Object first_en) {
            this.first_en = first_en;
        }

        public static class LatLonBean {
            /**
             * lat : 38.486471
             * lon : 106.232471
             */

            private double lat;
            private double lon;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }
        }
    }
}
