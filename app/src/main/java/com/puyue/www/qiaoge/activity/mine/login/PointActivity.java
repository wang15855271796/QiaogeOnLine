package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2020/11/19
 */
public class PointActivity extends BaseSwipeActivity {
    @BindView(R.id.tv_no)
    TextView tv_no;
    @BindView(R.id.tv_yes)
    TextView tv_yes;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String phone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_point);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        phone = getIntent().getStringExtra("phone");
        tv_phone.setText("您的手机号"+phone+"现在能接收短信吗？");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,MessageCheckActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });

        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,TakeMessageActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {

    }
}
