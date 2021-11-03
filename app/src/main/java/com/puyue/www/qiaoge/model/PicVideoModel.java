package com.puyue.www.qiaoge.model;

import java.util.ArrayList;
import java.util.List;

public class PicVideoModel {

    DatasBean datasBean;

    public static class DatasBean {
        String url;
        int type;

        public DatasBean(String url, int type) {
            this.url = url;
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public DatasBean getDatasBean() {
        return datasBean;
    }

    public void setDatasBean(DatasBean datasBean) {
        this.datasBean = datasBean;
    }
}
