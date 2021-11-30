package com.puyue.www.qiaoge.adapter;

import android.app.AlertDialog;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.AddMountChangeTwoAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.UpDateNumEvent1;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.CartAddModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/6/2
 */
public class MustItemAdapter extends BaseQuickAdapter<ExchangeProductModel.DataBean.ProdPricesBean,BaseViewHolder> {
    private ImageView iv_cut;
    private ImageView iv_add;
    int productId;
    private TextView tv_price;
    int businessType;
    List<ExchangeProductModel.DataBean.ProdPricesBean> data;
    AlertDialog alertDialog;
    public MustItemAdapter(int businessType, int productId,int layoutResId, @Nullable List<ExchangeProductModel.DataBean.ProdPricesBean> data) {
        super(layoutResId, data);
        this.productId = productId;
        this.businessType = businessType;
        this.data = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, ExchangeProductModel.DataBean.ProdPricesBean item) {
        ImageView iv_reduce = helper.getView(R.id.iv_reduce);
        tv_price = helper.getView(R.id.tv_price);
        tv_price.setText(item.getPrice());
        helper.setText(R.id.tv_unit, item.getUnitDesc());
        TextView tv_old_price = helper.getView(R.id.tv_old_price);
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.setText(R.id.tv_old_price,item.getOldPrice());
        TextView tv_num = helper.getView(R.id.tv_num);
        tv_num.setText(item.getCartNum()+"");
        iv_cut = helper.getView(R.id.iv_cut);
        iv_add = helper.getView(R.id.iv_add);

        if(!item.getOldPrice().equals("")&&item.getOldPrice()!=null) {
            iv_reduce.setImageResource(R.mipmap.icon_jiangjia);
            iv_reduce.setVisibility(View.VISIBLE);
        }else {
            iv_reduce.setVisibility(View.GONE);
        }
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num++;
                changeCartNum(num,item.getPriceId(),tv_num);
                getDatas(1);
            }
        });

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                if (num > 0) {
                    num--;
                    changeCartNum(num,item.getPriceId(),tv_num);
                }
            }
        });

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        getDatas(1);
                        if (et_num.getText().toString() != null && StringHelper.notEmptyAndNull(et_num.getText().toString())) {
                            changeCartNum(Integer.parseInt(et_num.getText().toString()), item.getPriceId(), tv_num);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, "请输入数量");
                        }
                    }
                });

            }

        });
    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,16,end)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {

                    }
                });
    }
    /**
     * 添加购物车
     */
    private void changeCartNum(int num, int priceId, TextView textView) {
        AddMountChangeTwoAPI.changeCartNum(mContext,businessType,productId,num,priceId)
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
                                    EventBus.getDefault().post(new UpDateNumEvent1());
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                                    EventBus.getDefault().post(new ReduceNumEvent());
                                    textView.setText(num+"");
                                    alertDialog.dismiss();
                                }else {
                                    textView.setText(cartAddModel.getData().getNum()+"");
                                    EventBus.getDefault().post(new UpDateNumEvent1());
                                    EventBus.getDefault().post(new ReduceNumEvent());
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
