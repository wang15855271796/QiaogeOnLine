package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.SetAmountEvent;
import com.puyue.www.qiaoge.event.SetAmountsEvent;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

public abstract class AddTipDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView tv_ok;
    ImageView iv_close;
    EditText et_amount;

    public AddTipDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.add_tip);
        mContext = context;
        initView();
        initAction();
    }

    private void initAction() {
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        et_amount = (EditText) findViewById(R.id.et_amount);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_ok.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    private void initView() {

    }


    public abstract void Confirm(String amount);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_ok:
                Confirm(et_amount.getText().toString());
                break;
        }
    }
}
