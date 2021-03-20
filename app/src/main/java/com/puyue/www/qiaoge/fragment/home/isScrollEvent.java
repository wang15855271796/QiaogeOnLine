package com.puyue.www.qiaoge.fragment.home;

/**
 * Created by ${王涛} on 2021/2/24
 */
public class isScrollEvent {

    boolean isScroll;

    public isScrollEvent(boolean isScroll) {
        this.isScroll = isScroll;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
