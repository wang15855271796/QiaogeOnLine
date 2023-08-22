package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.mine.AreaAdapter;
import com.puyue.www.qiaoge.model.home.CityChangeModel;

import java.util.List;

public abstract class HelpPayDialog extends Dialog implements View.OnClickListener {
    Activity mContext;
    TextView tv_sure;
    TextView tv_cancel;
    TextView tv_desc;
    TextView tv_address;

    public HelpPayDialog(Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_help_pay);
        this.mContext = context;
        initView();
    }

    private void initView() {
        tv_desc = findViewById(R.id.tv_desc);
        tv_sure = findViewById(R.id.tv_sure);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_address = findViewById(R.id.tv_address);
        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    public abstract void sure();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                sure();
                break;

            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
