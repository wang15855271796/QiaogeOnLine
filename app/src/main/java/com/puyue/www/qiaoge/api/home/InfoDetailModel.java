package com.puyue.www.qiaoge.api.home;

import java.util.List;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class InfoDetailModel {

    /**
     * code : 1
     * message : 成功
     * data : {"msgId":"29c77c5efb3544bbbc469dc179a88591","content":"因要去外地发展，无暇打理店内事情，特将盈利中的店铺转让给有需要的老板、 转让包括品牌，门店，设备，生意，制作工艺包教会，无需添加任何东西，接受就盈利，一年可盈利50万左右、嘉兴外卖排名前十名，堂食外卖均可使用，2人就可以开店，成本低，4～6月回本、 以上信息绝无虚假，欢迎各位老板前来考察、","msgType":null,"msgTypeName":"店铺转让","pictureList":["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//05496c9332f04480afa82e075e61279b.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//6b9f6052ba03423396a21e213ef85aff.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//b9c43915c05c4ffdb83d36316496ed7a.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//e5572604234b4bb39029de6540cfe628.jpeg"],"userPhone":"186****6876","contactPhone":"18668366876","provinceCode":"330000","provinceName":"浙江省","cityCode":"330400","cityName":"嘉兴市","areaCode":"330402","areaName":"南湖区","detailAddress":"嘉兴市南湖区南杨路145号","cstatus":null,"reason":null,"createTime":null,"deleteFlag":false,"checkDate":null}
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
         * msgId : 29c77c5efb3544bbbc469dc179a88591
         * content : 因要去外地发展，无暇打理店内事情，特将盈利中的店铺转让给有需要的老板、 转让包括品牌，门店，设备，生意，制作工艺包教会，无需添加任何东西，接受就盈利，一年可盈利50万左右、嘉兴外卖排名前十名，堂食外卖均可使用，2人就可以开店，成本低，4～6月回本、 以上信息绝无虚假，欢迎各位老板前来考察、
         * msgType : null
         * msgTypeName : 店铺转让
         * pictureList : ["https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//05496c9332f04480afa82e075e61279b.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//6b9f6052ba03423396a21e213ef85aff.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//b9c43915c05c4ffdb83d36316496ed7a.jpeg","https://barbecue-img.oss-cn-hangzhou.aliyuncs.com/message//e5572604234b4bb39029de6540cfe628.jpeg"]
         * userPhone : 186****6876
         * contactPhone : 18668366876
         * provinceCode : 330000
         * provinceName : 浙江省
         * cityCode : 330400
         * cityName : 嘉兴市
         * areaCode : 330402
         * areaName : 南湖区
         * detailAddress : 嘉兴市南湖区南杨路145号
         * cstatus : null
         * reason : null
         * createTime : null
         * deleteFlag : false
         * checkDate : null
         */

        private String msgId;
        private String content;
        private Object msgType;
        private String msgTypeName;
        private String userPhone;
        private String contactPhone;
        private String provinceCode;
        private String provinceName;
        private String cityCode;
        private String cityName;
        private String areaCode;
        private String areaName;
        private String detailAddress;
        private Object cstatus;
        private Object reason;
        private Object createTime;
        private boolean deleteFlag;
        private Object checkDate;
        private List<String> pictureList;

        public String getMsgId() {
            return msgId;
        }

        public void setMsgId(String msgId) {
            this.msgId = msgId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getMsgType() {
            return msgType;
        }

        public void setMsgType(Object msgType) {
            this.msgType = msgType;
        }

        public String getMsgTypeName() {
            return msgTypeName;
        }

        public void setMsgTypeName(String msgTypeName) {
            this.msgTypeName = msgTypeName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getDetailAddress() {
            return detailAddress;
        }

        public void setDetailAddress(String detailAddress) {
            this.detailAddress = detailAddress;
        }

        public Object getCstatus() {
            return cstatus;
        }

        public void setCstatus(Object cstatus) {
            this.cstatus = cstatus;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public boolean isDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(boolean deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public Object getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(Object checkDate) {
            this.checkDate = checkDate;
        }

        public List<String> getPictureList() {
            return pictureList;
        }

        public void setPictureList(List<String> pictureList) {
            this.pictureList = pictureList;
        }
    }
}
