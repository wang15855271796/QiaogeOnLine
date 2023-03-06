package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.TimeAdapter;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InfoPayDialog extends Dialog {
    Context context;
    View view;
    Unbinder binder;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    public InfoPayDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
        init();

    }

    private void init() {
        view = View.inflate(context, R.layout.dialog_pay, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
//        InfoPayAdapter infoPayAdapter = new InfoPayAdapter(R.layout.item_pay,list);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(infoPayAdapter);
    }
}
