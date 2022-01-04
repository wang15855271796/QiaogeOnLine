package com.puyue.www.qiaoge.event;

import com.puyue.www.qiaoge.model.CancelReasonModel;

import java.util.List;

public class HuoReasonEvent {
    List<CancelReasonModel.DataBean.SubReasonBean> sub_reason;
    String name;
    public HuoReasonEvent(List<CancelReasonModel.DataBean.SubReasonBean> sub_reason, String name) {
        this.sub_reason = sub_reason;
        this.name = name;
    }

    public List<CancelReasonModel.DataBean.SubReasonBean> getSub_reason() {
        return sub_reason;
    }

    public void setSub_reason(List<CancelReasonModel.DataBean.SubReasonBean> sub_reason) {
        this.sub_reason = sub_reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
