package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.BeizhuEvent;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ${王涛} on 2020/8/8
 */
public class BeizhuActivity extends BaseSwipeActivity {

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_beizhu);

        TextView tv_finish = findViewById(R.id.tv_finish);
        EditText et = findViewById(R.id.et);
        String beizhu = getIntent().getStringExtra("beizhu");
        et.setText(beizhu);
        ImageView iv_back = findViewById(R.id.iv_back);
        tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new BeizhuEvent(et.getText().toString()));
                finish();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new BeizhuEvent(et.getText().toString()));
                finish();
            }
        });
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
