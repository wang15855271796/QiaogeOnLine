package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;

import butterknife.BindView;

public class CommonH7Activity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.common_h);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
