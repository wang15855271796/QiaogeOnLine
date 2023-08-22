package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.HelpPayDialog;

import butterknife.BindView;

public class HelpPayActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_next:
                HelpPayDialog helpPayDialog = new HelpPayDialog(mActivity) {
                    @Override
                    public void sure() {
                        dismiss();
                    }
                };
                helpPayDialog.show();
//                Intent intent = new Intent(mContext);
//                startActivity(intent);
                break;
        }
    }
}
