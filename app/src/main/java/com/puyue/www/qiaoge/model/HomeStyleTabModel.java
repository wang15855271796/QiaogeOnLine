package com.puyue.www.qiaoge.model;

public class HomeStyleTabModel {

    private Integer code;
    private String message;
    private DataBean data;
    private Object extData;
    private Boolean error;
    private Boolean success;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public Object getExtData() {
        return extData;
    }

    public void setExtData(Object extData) {
        this.extData = extData;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public static class DataBean {
        private Integer changeFlag;
        private Tab1Bean tab1;
        private Tab2Bean tab2;
        private Tab3Bean tab3;
        private Tab4Bean tab4;
        private Tab5Bean tab5;
        private String rentName;

        @Override
        public String toString() {
            return "DataBean{" +
                    "changeFlag=" + changeFlag +
                    ", tab1=" + tab1 +
                    ", tab2=" + tab2 +
                    ", tab3=" + tab3 +
                    ", tab4=" + tab4 +
                    ", tab5=" + tab5 +
                    ", rentName='" + rentName + '\'' +
                    '}';
        }

        public Integer getChangeFlag() {
            return changeFlag;
        }

        public void setChangeFlag(Integer changeFlag) {
            this.changeFlag = changeFlag;
        }

        public Tab1Bean getTab1() {
            return tab1;
        }

        public void setTab1(Tab1Bean tab1) {
            this.tab1 = tab1;
        }

        public Tab2Bean getTab2() {
            return tab2;
        }

        public void setTab2(Tab2Bean tab2) {
            this.tab2 = tab2;
        }

        public Tab3Bean getTab3() {
            return tab3;
        }

        public void setTab3(Tab3Bean tab3) {
            this.tab3 = tab3;
        }

        public Tab4Bean getTab4() {
            return tab4;
        }

        public void setTab4(Tab4Bean tab4) {
            this.tab4 = tab4;
        }

        public Tab5Bean getTab5() {
            return tab5;
        }

        public void setTab5(Tab5Bean tab5) {
            this.tab5 = tab5;
        }

        public String getRentName() {
            return rentName;
        }

        public void setRentName(String rentName) {
            this.rentName = rentName;
        }

        public static class Tab1Bean {
            private String picUrl;
            private String choosePicUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getChoosePicUrl() {
                return choosePicUrl;
            }

            public void setChoosePicUrl(String choosePicUrl) {
                this.choosePicUrl = choosePicUrl;
            }
        }

        public static class Tab2Bean {
            private String picUrl;
            private String choosePicUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getChoosePicUrl() {
                return choosePicUrl;
            }

            public void setChoosePicUrl(String choosePicUrl) {
                this.choosePicUrl = choosePicUrl;
            }
        }

        public static class Tab3Bean {
            private String picUrl;
            private String choosePicUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getChoosePicUrl() {
                return choosePicUrl;
            }

            public void setChoosePicUrl(String choosePicUrl) {
                this.choosePicUrl = choosePicUrl;
            }
        }

        public static class Tab4Bean {
            private String picUrl;
            private String choosePicUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getChoosePicUrl() {
                return choosePicUrl;
            }

            public void setChoosePicUrl(String choosePicUrl) {
                this.choosePicUrl = choosePicUrl;
            }
        }

        public static class Tab5Bean {
            private String picUrl;
            private String choosePicUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getChoosePicUrl() {
                return choosePicUrl;
            }

            public void setChoosePicUrl(String choosePicUrl) {
                this.choosePicUrl = choosePicUrl;
            }
        }
    }
}

