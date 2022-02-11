package com.puyue.www.qiaoge.event;

public class AccountEvent {
    int adapterPosition;
    boolean flag;
    double num;
    public AccountEvent(int adapterPosition, boolean flag, double num) {
        this.adapterPosition =adapterPosition;
        this.flag = flag;
        this.num = num;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public void setAdapterPosition(int adapterPosition) {
        this.adapterPosition = adapterPosition;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public double getNum() {
        return num;
    }

    public void setNum(double num) {
        this.num = num;
    }
}
