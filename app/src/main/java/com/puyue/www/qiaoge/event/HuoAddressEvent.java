package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.AddressListModel;

public class HuoAddressEvent {
    AddressListModel.DataBean dataBean;
    String name;
    String phone;
    int type;
    public HuoAddressEvent(AddressListModel.DataBean dataBean, String name, String phone, int type) {
        this.dataBean = dataBean;
        this.name = name;
        this.phone = phone;
        this.type = type;

    }

    public AddressListModel.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(AddressListModel.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
