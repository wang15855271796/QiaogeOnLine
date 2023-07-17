package com.puyue.www.qiaoge.adapter.cart;

import android.app.AlertDialog;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.AddMountChangeTwoAPI;
import com.puyue.www.qiaoge.event.RefreshCarNumEvent1;
import com.puyue.www.qiaoge.event.RefreshCarNumEvent2;
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
    List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX;
    int adapterPosition;
    CartGoodsAdapter cartGoodsAdapter;
    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> data;
    List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prodsBeans;
    public CartPriceAdapter(int layoutResId, CartTestModel.DataBean.ProdsBeanX.ProdsBean item
            , @Nullable List<CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean> data
            , CartAdapter cartAdapter, List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX, int adapterPosition
            , List<CartTestModel.DataBean.ProdsBeanX.ProdsBean> prodsBeans, CartGoodsAdapter cartGoodsAdapter) {
        super(layoutResId, data);
        this.prods = item;
        this.prodsBeans = prodsBeans;
        this.cartAdapter = cartAdapter;
        this.prodsBeanX = prodsBeanX;
        this.data = data;
        this.cartGoodsAdapter = cartGoodsAdapter;
        this.adapterPosition = adapterPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item) {
        LinearLayout ll_trend = helper.getView(R.id.ll_trend);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        TextView tv_unit = helper.getView(R.id.tv_unit);
        ImageView iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        ImageView iv_add = helper.getView(R.id.iv_add);
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_price.setText(item.getPriceStr());
        tv_unit.setText(item.getUnitDesc());

        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.ic_refresh_price);
        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }
        ll_trend.setVisibility(View.GONE);
        tv_num.setText(item.getProductNum()+"");
        int productCombinationPriceId = item.getProductCombinationPriceId();

        if(item.getOldPrice()!=null&&item.getOldPrice()!="") {
            tv_old_price.setText(item.getOldPrice());
            tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_old_price.getPaint().setAntiAlias(true);//抗锯齿
        }

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCartNum(helper,item,tv_num);
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num = num + 1;
                item.setProductNum1(num);
                item.setProductNum(num);
                changeCartNum(helper, prods,num,productCombinationPriceId,tv_num,prodsBeanX);

//                EventBus.getDefault().post(new RefreshCarNumEvent1(prods,num,productCombinationPriceId,tv_num,prodsBeanX));
            }
        });

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                if(num>0) {
                    num = num - 1;
                    item.setProductNum(num);
                    changeCartNum(helper,prods,num,productCombinationPriceId,tv_num,prodsBeanX);
//                    changeCartNum(prods,num,productCombinationPriceId,tv_num, prods);

                }else {
                    ToastUtil.showSuccessMsg(mContext,"最小数量为0");
                }
            }
        });
    }
    AlertDialog alertDialog;
    private void getCartNum(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.ProdsBean.ProductDescVOListBean item, TextView textView) {
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
                    item.setProductNum(num);
                    changeCartNum(helper, prods,num,item.getProductCombinationPriceId(),textView,prodsBeanX);
//                    changeCartNum(prods,num,item.getProductCombinationPriceId(),textView, prods);
                }
            }
        });
    }

    /**
     * 修改购物车数量
     * @param
     * @param
     * @param helper
     * @param item
     * @param num
     * @param prodsBeanX
     */
    private void changeCartNum(BaseViewHolder helper, CartTestModel.DataBean.ProdsBeanX.ProdsBean item, int num, int priceId, TextView textView, List<CartTestModel.DataBean.ProdsBeanX> prodsBeanX) {
        AddMountChangeTwoAPI.changeCartNum(mContext,item.getBusinessType(),item.getBusinessId(),num,priceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartAddModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("wdadawd....",e.getMessage()+"--");
                    }

                    @Override
                    public void onNext(CartAddModel cartAddModel) {
                        if (cartAddModel.getCode()==1) {
                            if(cartAddModel.getData()!=null) {
                                if(cartAddModel.getData().getAddFlag()==0) {
                                    //正常
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                                    textView.setText(num+"");
                                    EventBus.getDefault().post(new RefreshCarNumEvent2(prodsBeanX));
                                    if(alertDialog!=null) {
                                        alertDialog.dismiss();
                                    }

                                    if(num == 0) {
                                        data.remove(helper.getAdapterPosition());
                                        notifyItemRemoved(helper.getAdapterPosition());
                                        notifyItemRangeChanged(helper.getAdapterPosition(),data.size());
                                        notifyDataSetChanged();
                                        EventBus.getDefault().post(new RefreshCarNumEvent1(prodsBeanX,adapterPosition,prodsBeans,cartGoodsAdapter,data.size()));
                                    }

                                }else {
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getData().getMessage());
                                    textView.setText(cartAddModel.getData().getNum()+"");
                                    EventBus.getDefault().post(new RefreshCarNumEvent1(prodsBeanX,adapterPosition,prodsBeans,cartGoodsAdapter,data.size()));
                                    if(alertDialog!=null) {
                                        alertDialog.dismiss();
                                    }
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                        }
                    }
                });
    }
}
