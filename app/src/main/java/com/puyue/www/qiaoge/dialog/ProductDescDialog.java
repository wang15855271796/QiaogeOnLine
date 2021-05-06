package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.cart.ItemChooseAdapter;
import com.puyue.www.qiaoge.adapter.cart.SearchSpecAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent10;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.puyue.www.qiaoge.view.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
