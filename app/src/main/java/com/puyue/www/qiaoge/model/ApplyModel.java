package com.puyue.www.qiaoge.model;

public class ApplyModel {

    Data1Bean dataBean;

    public class Data1Bean {
        int bill_type;
        String billTypeName;

        public int getBill_type() {
            return bill_type;
        }

        public void setBill_type(int bill_type) {
            this.bill_type = bill_type;
        }

        public String getBillTypeName() {
            return billTypeName;
        }

        public void setBillTypeName(String billTypeName) {
            this.billTypeName = billTypeName;
        }
    }

    public Data1Bean getDataBean() {
        return dataBean;
    }

    public void setDataBean(Data1Bean dataBean) {
        this.dataBean = dataBean;
    }
}
