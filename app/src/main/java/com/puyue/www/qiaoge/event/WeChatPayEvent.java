package com.puyue.www.qiaoge.event;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/5/10.
 */

public class WeChatPayEvent {

    String code;
    public WeChatPayEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
