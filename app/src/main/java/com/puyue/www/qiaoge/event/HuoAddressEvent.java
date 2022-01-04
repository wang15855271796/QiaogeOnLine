package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.AddressListModel;

public class HuoAddressEvent {
    AddressListModel.DataBean dataBean;
    String name;
    String phone;
    public HuoAddressEvent(AddressListModel.DataBean dataBean, String name, String phone) {
        this.dataBean = dataBean;
        this.name = name;
        this.phone = phone;

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
}
