package com.puyue.www.qiaoge.event;

import java.util.List;

public class OtherSureEvent {
    List<String> list;
    List<Integer> listType;
    public OtherSureEvent(List<String> list,List<Integer> listType) {
        this.list = list;
        this.listType = listType;
    }

    public List<Integer> getListType() {
        return listType;
    }

    public void setListType(List<Integer> listType) {
        this.listType = listType;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
