package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.AddressListModel;

public class HuoAddress1Event {
    AddressListModel.DataBean dataBean;
    public HuoAddress1Event(AddressListModel.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public AddressListModel.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(AddressListModel.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
