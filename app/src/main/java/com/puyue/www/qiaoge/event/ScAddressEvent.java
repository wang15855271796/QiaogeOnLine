package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.mine.address.AddressModel;

public class ScAddressEvent {
    AddressModel.DataBean dataBean;
    public ScAddressEvent(AddressModel.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public AddressModel.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(AddressModel.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
