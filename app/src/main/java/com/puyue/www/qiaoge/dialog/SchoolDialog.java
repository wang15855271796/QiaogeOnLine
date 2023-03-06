package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;

public abstract class SchoolDialog extends Dialog {
    Context mContext;
    public ImageView iv_close;
    RelativeLayout ll_root;
    public SchoolDialog(@NonNull Context context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_school);
        mContext = context;
        initView();
        initAction();
    }

    private void initView() {
        iv_close= (ImageView) findViewById(R.id.iv_close);
        ll_root = (RelativeLayout) findViewById(R.id.ll_root);
    }


    private void initAction() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();

            }
        });

        ll_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToSchool();
            }
        });
    }
    public abstract void GoToSchool();
    public abstract void Confirm();
}
