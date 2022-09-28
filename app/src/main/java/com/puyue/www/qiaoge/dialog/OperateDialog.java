package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/10/12
 */
public abstract class OperateDialog extends Dialog {

    Activity mContext;

    public TextView tv_sure,hint;
    public TextView title;
    public TextView tv_cancle;
    TextView tv_desc;
    String totalPrice;
    ImageView iv_cancel;
    List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate;
    public OperateDialog(@NonNull Activity context, List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate, String totalPrice) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_operate);
        mContext = context;
        this.listUnOperate = listUnOperate;
        this.totalPrice = totalPrice;
        initView();
        initAction();
    }

    private void initView() {
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_cancle= (TextView) findViewById(R.id.tv_cancle);
        tv_sure= (TextView) findViewById(R.id.tv_sure);
        hint = (TextView) findViewById(R.id.hint);
        title = findViewById(R.id.title);
        tv_cancle.setText("只结算可自提商品，支付" + totalPrice +"元");
        tv_desc.setText("当前订单有"+listUnOperate.size()+"件商品暂不支持自提，建议您使用订单配送服务。");
    }


    private void initAction() {
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Close();
            }
        });
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
    public abstract void Close();
}
