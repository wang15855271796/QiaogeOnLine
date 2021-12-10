package com.puyue.www.qiaoge.event;

public class SearchShopEvent {
    String keyWord;

    public SearchShopEvent(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
