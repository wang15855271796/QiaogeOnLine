package com.puyue.www.qiaoge.event;

public class UpdateAddressEvent {
    String detailAddress;
    public UpdateAddressEvent(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }
}
