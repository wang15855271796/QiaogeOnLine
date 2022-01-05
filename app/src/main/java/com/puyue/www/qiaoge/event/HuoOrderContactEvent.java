package com.puyue.www.qiaoge.event;

public class HuoOrderContactEvent {
    String etName;
    String etPhone;
    public HuoOrderContactEvent(String etName, String etPhone) {
        this.etName = etName;
        this.etPhone = etPhone;
    }

    public String getEtName() {
        return etName;
    }

    public void setEtName(String etName) {
        this.etName = etName;
    }

    public String getEtPhone() {
        return etPhone;
    }

    public void setEtPhone(String etPhone) {
        this.etPhone = etPhone;
    }
}
