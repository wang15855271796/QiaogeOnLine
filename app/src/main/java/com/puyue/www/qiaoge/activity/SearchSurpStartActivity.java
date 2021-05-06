package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2021/4/24
 */
public class SearchSurpStartActivity extends BaseActivity {
    @BindView(R.id.search_btn_back)
    LinearLayout search_btn_back;
    @BindView(R.id.search_et_input)
    EditText search_et_input;
    @BindView(R.id.tv_search)
    TextView tv_search;
    String surplieId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_search_start);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);

        surplieId = getIntent().getStringExtra("surplieId");
        search_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_et_input.getText().toString().isEmpty()) {
                    ToastUtil.showSuccessMsg(mActivity,"请输入商品名称");
                    return;
                }
                Intent intent = new Intent(mActivity,SearchSurpActivity.class);
                intent.putExtra("search",search_et_input.getText().toString());
                intent.putExtra("surplieId",surplieId);
                startActivity(intent);
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
