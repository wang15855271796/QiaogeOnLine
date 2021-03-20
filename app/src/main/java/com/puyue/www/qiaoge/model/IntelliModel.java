package com.puyue.www.qiaoge.model;

import com.puyue.www.qiaoge.model.home.GetProductDetailModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/9/29
 */
public class IntelliModel {
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
        String companyName;
        String licensePic;
        String licenseNo;
        String address;
        String licenseValidity;
        String businessUrl;
        String businessValidity;

        @Override
        public String toString() {
            return "DataBean{" +
                    "companyName='" + companyName + '\'' +
                    ", licensePic='" + licensePic + '\'' +
                    ", licenseNo='" + licenseNo + '\'' +
                    ", address='" + address + '\'' +
                    ", licenseValidity='" + licenseValidity + '\'' +
                    ", businessUrl='" + businessUrl + '\'' +
                    ", businessValidity='" + businessValidity + '\'' +
                    '}';
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLicensePic() {
            return licensePic;
        }

        public void setLicensePic(String licensePic) {
            this.licensePic = licensePic;
        }

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLicenseValidity() {
            return licenseValidity;
        }

        public void setLicenseValidity(String licenseValidity) {
            this.licenseValidity = licenseValidity;
        }

        public String getBusinessUrl() {
            return businessUrl;
        }

        public void setBusinessUrl(String businessUrl) {
            this.businessUrl = businessUrl;
        }

        public String getBusinessValidity() {
            return businessValidity;
        }

        public void setBusinessValidity(String businessValidity) {
            this.businessValidity = businessValidity;
        }
    }
}
