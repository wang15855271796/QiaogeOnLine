package com.puyue.www.qiaoge.adapter.cart;

import android.app.AlertDialog;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.AddMountChangeTwoAPI;
import com.puyue.www.qiaoge.event.ReduceNumEvent1;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.model.cart.CartAddModel;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/10/9
 */
public class CartPriceAdapter extends BaseQuickAdapter<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean,BaseViewHolder> {

    CartTestModel.DataBean.ProdsBeanX.ProdsBean prods;
    CartAdapter cartAdapter;
    int position;
    int outPosition;
    public CartPriceAdapter(int layoutResId, CartTestModel.DataBean.ProdsBeanX.ProdsBean item
            , @Nullable List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> data, CartAdapter cartAdapter, int position, int outPosition) {
        super(layoutResId, data);
        this.prods = item;
        this.position = position;
        this.outPosition = outPosition;
        this.cartAdapter = cartAdapter;
    }


    @Override
    protected void convert(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item) {
        LinearLayout ll_trend = helper.getView(R.id.ll_trend);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        TextView tv_unit = helper.getView(R.id.tv_unit);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_price.setText(item.getPriceStr());
        tv_unit.setText(item.getUnitDesc());

        ll_trend.setVisibility(View.GONE);
        tv_num.setText(item.getProductNum()+"");
        int productCombinationPriceId = item.getProductCombinationPriceId();

        ImageView iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);
        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }

        if(item.getOldPrice()!=null&&item.getOldPrice()!="") {
            tv_old_price.setText(item.getOldPrice());
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        }

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCartNum(item,tv_num);
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num = num + 1;
                changeCartNum(prods,num,productCombinationPriceId,tv_num,item);
            }
        });

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                if(num>0) {
                    num = num - 1;
                    changeCartNum(prods,num,productCombinationPriceId,tv_num, item);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"最小数量为0");
                }
            }
        });
    }
    AlertDialog alertDialog;
    private void getCartNum(CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item,TextView textView) {
        alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        window.setContentView(R.layout.dialog_cart_num_set);
        EditText et_num = window.findViewById(R.id.et_num);
        TextView tv_ok = window.findViewById(R.id.tv_ok);
        TextView tv_cancel = window.findViewById(R.id.tv_cancel);
        window.setGravity(Gravity.CENTER);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_num.getText().toString()!=null && !et_num.getText().toString().equals("")) {
                    int num = Integer.parseInt(et_num.getText().toString());
                    changeCartNum(prods,num,item.getProductCombinationPriceId(),textView, item);
                }
            }
        });
    }

    /**
     * 修改购物车数量
     * @param item1
     * @param num
     * @param item
     */
    private void changeCartNum(CartTestModel.DataBean.ProdsBeanX.ProdsBean item1, int num, int priceId, TextView textView, CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item) {
        AddMountChangeTwoAPI.changeCartNum(mContext,item1.getBusinessType(),item1.getBusinessId(),num,priceId,item.getFreshPriceFlag())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartAddModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CartAddModel cartAddModel) {
                        if (cartAddModel.getCode()==1) {
                            if(cartAddModel.getData()!=null) {
                                if(cartAddModel.getData().getAddFlag()==0) {
                                    //正常
                                    EventBus.getDefault().post(new ReduceNumEvent1());
                                    EventBus.getDefault().post(new ReduceNumEvent(item1.getCartId(),item1.getCheckFlag()));
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                                    textView.setText(num+"");
                                    alertDialog.dismiss();
                                }else {
                                    textView.setText(cartAddModel.getData().getNum()+"");
                                    EventBus.getDefault().post(new ReduceNumEvent1());
                                    EventBus.getDefault().post(new ReduceNumEvent(item1.getCartId(),item1.getCheckFlag()));
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getData().getMessage());
                                    alertDialog.dismiss();
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                        }
                    }
                });
    }
}
