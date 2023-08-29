package com.puyue.www.qiaoge.model;

import java.io.Serializable;

public class ApplyInfoModel {


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

    public static class DataBean implements Serializable {
        private String checkNo;
        private String applyPhone;
        private String supplierName;
        private String address;
        private String contactName;
        private String contactPhone;
        private String companyProvinceCode;
        private String companyProvinceName;
        private String companyCityCode;
        private String companyCityName;
        private String companyAreaCode;
        private String companyAreaName;
        private String supplyProdType;
        private Integer basicFinish;
        private String licenseUrl;
        private String businessUrl;
        private String licenseNo;
        private String companyName;
        private String companyAddress;
        private Integer companyType;
        private Integer licenseLongTerm;
        private String licenseValidityStart;
        private String licenseValidityEnd;
        private String businessValidity;
        private Integer licenseFinish;
        private String corporateCardFront;
        private String corporateCardReverse;
        private String idNumber;
        private Integer corporateFinish;
        private Integer accountType;
        private String accountName;
        private String accountBank;
        private String provinceCode;
        private String provinceName;
        private String cityCode;
        private String cityName;
        private String siteNo;
        private String bankCardNo;
        private String phone;
        private String userName;
        private String pwd;
        private Integer settleFinish;
        private Integer checkStatus;

        public String getCheckNo() {
            return checkNo;
        }

        public void setCheckNo(String checkNo) {
            this.checkNo = checkNo;
        }

        public String getApplyPhone() {
            return applyPhone;
        }

        public void setApplyPhone(String applyPhone) {
            this.applyPhone = applyPhone;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactPhone() {
            return contactPhone;
        }

        public void setContactPhone(String contactPhone) {
            this.contactPhone = contactPhone;
        }

        public String getCompanyProvinceCode() {
            return companyProvinceCode;
        }

        public void setCompanyProvinceCode(String companyProvinceCode) {
            this.companyProvinceCode = companyProvinceCode;
        }

        public String getCompanyProvinceName() {
            return companyProvinceName;
        }

        public void setCompanyProvinceName(String companyProvinceName) {
            this.companyProvinceName = companyProvinceName;
        }

        public String getCompanyCityCode() {
            return companyCityCode;
        }

        public void setCompanyCityCode(String companyCityCode) {
            this.companyCityCode = companyCityCode;
        }

        public String getCompanyCityName() {
            return companyCityName;
        }

        public void setCompanyCityName(String companyCityName) {
            this.companyCityName = companyCityName;
        }

        public String getCompanyAreaCode() {
            return companyAreaCode;
        }

        public void setCompanyAreaCode(String companyAreaCode) {
            this.companyAreaCode = companyAreaCode;
        }

        public String getCompanyAreaName() {
            return companyAreaName;
        }

        public void setCompanyAreaName(String companyAreaName) {
            this.companyAreaName = companyAreaName;
        }

        public String getSupplyProdType() {
            return supplyProdType;
        }

        public void setSupplyProdType(String supplyProdType) {
            this.supplyProdType = supplyProdType;
        }

        public Integer getBasicFinish() {
            return basicFinish;
        }

        public void setBasicFinish(Integer basicFinish) {
            this.basicFinish = basicFinish;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getBusinessUrl() {
            return businessUrl;
        }

        public void setBusinessUrl(String businessUrl) {
            this.businessUrl = businessUrl;
        }

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getCompanyAddress() {
            return companyAddress;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
        }

        public Integer getCompanyType() {
            return companyType;
        }

        public void setCompanyType(Integer companyType) {
            this.companyType = companyType;
        }

        public Integer getLicenseLongTerm() {
            return licenseLongTerm;
        }

        public void setLicenseLongTerm(Integer licenseLongTerm) {
            this.licenseLongTerm = licenseLongTerm;
        }

        public String getLicenseValidityStart() {
            return licenseValidityStart;
        }

        public void setLicenseValidityStart(String licenseValidityStart) {
            this.licenseValidityStart = licenseValidityStart;
        }

        public String getLicenseValidityEnd() {
            return licenseValidityEnd;
        }

        public void setLicenseValidityEnd(String licenseValidityEnd) {
            this.licenseValidityEnd = licenseValidityEnd;
        }

        public String getBusinessValidity() {
            return businessValidity;
        }

        public void setBusinessValidity(String businessValidity) {
            this.businessValidity = businessValidity;
        }

        public Integer getLicenseFinish() {
            return licenseFinish;
        }

        public void setLicenseFinish(Integer licenseFinish) {
            this.licenseFinish = licenseFinish;
        }

        public String getCorporateCardFront() {
            return corporateCardFront;
        }

        public void setCorporateCardFront(String corporateCardFront) {
            this.corporateCardFront = corporateCardFront;
        }

        public String getCorporateCardReverse() {
            return corporateCardReverse;
        }

        public void setCorporateCardReverse(String corporateCardReverse) {
            this.corporateCardReverse = corporateCardReverse;
        }

        public String getIdNumber() {
            return idNumber;
        }

        public void setIdNumber(String idNumber) {
            this.idNumber = idNumber;
        }

        public Integer getCorporateFinish() {
            return corporateFinish;
        }

        public void setCorporateFinish(Integer corporateFinish) {
            this.corporateFinish = corporateFinish;
        }

        public Integer getAccountType() {
            return accountType;
        }

        public void setAccountType(Integer accountType) {
            this.accountType = accountType;
        }

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountBank() {
            return accountBank;
        }

        public void setAccountBank(String accountBank) {
            this.accountBank = accountBank;
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

        public String getSiteNo() {
            return siteNo;
        }

        public void setSiteNo(String siteNo) {
            this.siteNo = siteNo;
        }

        public String getBankCardNo() {
            return bankCardNo;
        }

        public void setBankCardNo(String bankCardNo) {
            this.bankCardNo = bankCardNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public Integer getSettleFinish() {
            return settleFinish;
        }

        public void setSettleFinish(Integer settleFinish) {
            this.settleFinish = settleFinish;
        }

        public Integer getCheckStatus() {
            return checkStatus;
        }

        public void setCheckStatus(Integer checkStatus) {
            this.checkStatus = checkStatus;
        }
    }
}
