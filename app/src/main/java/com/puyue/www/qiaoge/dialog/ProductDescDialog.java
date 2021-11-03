package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.cart.SearchSpecAdapter;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2021/4/23
 */
public class ProductDescDialog extends Dialog implements View.OnClickListener{

    Context context;
    public View view;
    public Unbinder binder;

    @BindView(R.id.tv_close)
    TextView tv_close;
    @BindView(R.id.tv_content)
    TextView tv_content;
    private SearchSpecAdapter searchSpecAdapter;
    ExchangeProductModel exchangeProductModels;
    GetProductDetailModel model;
    String desc;
    public List<GetProductDetailModel.DataBean.ProdSpecsBean> prodSpecs;
    public ProductDescDialog(Context context,String desc) {
        super(context, R.style.dialog);
        this.context = context;
        this.desc = desc;
        init();
    }

    @Override
    public void show() {
        super.show();


    }
    @Override
    public void cancel() {
        super.cancel();
    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_product_desc, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        tv_content.setText(desc);
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        tv_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_close:
                dismiss();
                break;
            default:
                break;

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



}
