package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.FullDescDialogAdapter;
import com.puyue.www.qiaoge.model.CartFullsModel;

import java.util.List;

public abstract class CartFullDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView tv_num;
    RecyclerView recyclerView;
    TextView tv_time;
    ImageView iv_ok;
    ImageView iv_close;
    CartFullsModel.DataBean data;
    public CartFullDialog(@NonNull Context context, CartFullsModel.DataBean data) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_cart_full);
        mContext = context;
        this.data = data;
        initView();
        initAction();
    }

    private void initAction() {
        iv_close = (ImageView) findViewById(R.id.iv_close);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_num = (TextView) findViewById(R.id.tv_num);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        iv_ok = (ImageView) findViewById(R.id.iv_ok);
        iv_ok.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        tv_time.setText(data.getStartTime()+"至"+data.getEndTime());
        tv_num.setText(data.getLimitInfo());
        FullDescDialogAdapter fullDescDialogAdapter = new FullDescDialogAdapter(R.layout.item_full_cart,data.getDeductDetail());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(fullDescDialogAdapter);
        fullDescDialogAdapter.notifyDataSetChanged();

    }

    private void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_ok:
                close();
                break;

            case R.id.iv_close:
                close();
                break;

        }

    }
    public abstract void close();
}
