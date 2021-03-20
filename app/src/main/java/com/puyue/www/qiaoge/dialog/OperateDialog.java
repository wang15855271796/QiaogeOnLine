package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/12
 */
public abstract class OperateDialog extends Dialog {

    Context mContext;

    public TextView tv_sure,hint;
    public TextView title;
    public TextView tv_cancle;
    TextView tv_desc;
    List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate;
    public OperateDialog(@NonNull Context context, List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_operate);
        mContext = context;
        this.listUnOperate = listUnOperate;
        initView();
        initAction();
    }

    private void initView() {
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_cancle= (TextView) findViewById(R.id.tv_cancle);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        hint = (TextView) findViewById(R.id.hint);
        title = findViewById(R.id.title);

        tv_desc.setText("当前订单有"+listUnOperate.size()+"件商品暂不支持自提，建议您使用订单配送服务。");
    }


    private void initAction() {
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirm();
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cancle();
            }
        });

    }

    public abstract void Confirm();
    public abstract void Cancle();
}
