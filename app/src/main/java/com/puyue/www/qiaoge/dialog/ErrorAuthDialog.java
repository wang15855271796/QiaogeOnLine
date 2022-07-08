package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.AuthModel;

import java.util.List;

public abstract class ErrorAuthDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView tv_ok;
    TextView tv_phone;
    TextView tv_desc;
    AuthModel.DataBean data;
    public ErrorAuthDialog(@NonNull Context context, AuthModel.DataBean data) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_error);
        mContext = context;
        this.data = data;
        initAction();
    }

    private void initAction() {
        tv_ok= (TextView) findViewById(R.id.tv_ok);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_ok.setOnClickListener(this);
        tv_desc.setText(data.getTip());
        tv_phone.setText("客服热线("+data.getOnlineTime()+")");
    }

    public abstract void Confirm(String amount);

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_ok:
                dismiss();
                break;
        }
    }

}
