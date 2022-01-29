package com.puyue.www.qiaoge.adapter;

import android.app.AlertDialog;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.AddMountChangeTwoAPI;
import com.puyue.www.qiaoge.api.cart.CartPostChangeOrderDetailAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.fragment.cart.UpdateEvent;
import com.puyue.www.qiaoge.fragment.market.Test2Adapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.AddCartGoodModel;
import com.puyue.www.qiaoge.model.cart.CartAddReduceModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/10/7
 */
public class Choose2CartPriceAdapter extends BaseQuickAdapter<CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean,BaseViewHolder> {
    @Nullable
    List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> data;
    Test2Adapter testAdapter;
    int businessId;
    int businessType;
    OnCartClickListener onCartClickListener;
    private int num;
    private int adapterPosition;

    public Choose2CartPriceAdapter(int layoutResId, @Nullable List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> data,
                                  Test2Adapter testAdapter, int businessId, int businessType) {
        super(layoutResId, data);
        this.data = data;
        this.testAdapter = testAdapter;
        this.businessId = businessId;
        this.businessType = businessType;
    }

    public void setOnItemClickListener(OnCartClickListener onCartClickListener) {
        this.onCartClickListener = onCartClickListener;
    }


    @Override
    protected void convert(BaseViewHolder helper, CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean item) {
        helper.setText(R.id.tv_unit,item.getUnitDesc());
        TextView tv_num = helper.getView(R.id.tv_num);
        helper.setText(R.id.tv_num, item.getProductNum() + "");
        helper.setText(R.id.tv_price, item.getPriceStr());
        ImageView iv_cut = helper.getView(R.id.iv_cut);
        ImageView iv_add = helper.getView(R.id.iv_add);
        
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(tv_num.getText().toString());
                num++;
                adapterPosition = helper.getAdapterPosition();
                addCart(item,num,item.getProductCombinationPriceId(),businessId,businessType,tv_num,item.getCartNum());
                getDatas(1);
            }
        });

        iv_cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = Integer.parseInt(tv_num.getText().toString());
                if(num ==1) {
                    final androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext, R.style.DialogStyle).create();
                    alertDialog.setCanceledOnTouchOutside(true);
                    alertDialog.show();
                    Window window = alertDialog.getWindow();
                    window.setContentView(R.layout.dialog_delete_cart);
                    TextView mTvTitle = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_title);
                    TextView mTvCancel = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_cancel);
                    TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_dialog_delete_cart_confirm);

                    mTvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    mTvConfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CartPostChangeOrderDetailAPI.requestCartPostChangeOrderDetail(mContext, businessType, businessId, "O", item.getProductCombinationPriceId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<CartAddReduceModel>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onNext(CartAddReduceModel cartAddReduceModel) {
                                            EventBus.getDefault().post(new ReduceNumEvent());
                                        }
                                    });
                            alertDialog.dismiss();
                        }
                    });
                }else {
                    num--;
                    data.get(helper.getAdapterPosition()).setProductNum(num);
                    addCarts(item,num,item.getProductCombinationPriceId(),businessId,businessType,tv_num,item.getCartNum());
                }

            }
        });

        tv_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.DialogStyle).create();
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

                            AddMountChangeTwoAPI.AddMountChangeServices(mContext, businessType, businessId, Integer.parseInt(et_num.getText().toString()), item.getProductCombinationPriceId())
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Subscriber<AddCartGoodModel>() {
                                        @Override
                                        public void onCompleted() {

                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }


                                        @Override
                                        public void onNext(AddCartGoodModel addMountReduceModel) {

                                            if (addMountReduceModel.isSuccess()) {
                                                tv_num.setText(et_num.getText().toString());
                                                alertDialog.dismiss();
                                                String num = et_num.getText().toString();
                                                item.setProductNum(Integer.parseInt(num));
                                                EventBus.getDefault().post(new UpdateEvent(testAdapter.getAllPrice()));
                                                EventBus.getDefault().post(new ReduceNumEvent());
                                                alertDialog.dismiss();
                                            } else {
                                                ToastUtil.showSuccessMsg(mContext, addMountReduceModel.getMessage());
                                                tv_num.setText(addMountReduceModel.data.toString());
                                                String data = (String) addMountReduceModel.data;
                                                item.setProductNum(Integer.parseInt(data));
                                                EventBus.getDefault().post(new UpdateEvent(testAdapter.getAllPrice()));
                                                EventBus.getDefault().post(new ReduceNumEvent());
                                                alertDialog.dismiss();
                                            }
                                        }
                                    });

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
    private void addCart(CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean item,int num, int id, int businessId, int productType, TextView tv_num,int cartNum) {
        AddMountChangeTwoAPI.AddMountChangeServices(mContext,productType,businessId,num,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddCartGoodModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddCartGoodModel addMountReduceModel) {
                        if (addMountReduceModel.isSuccess()) {
                            data.get(adapterPosition).setProductNum(num);
                            tv_num.setText(num + "");
                            ToastUtil.showSuccessMsg(mContext,"添加购物车成功");
                            EventBus.getDefault().post(new UpdateEvent(testAdapter.getAllPrice()));
                            EventBus.getDefault().post(new ReduceNumEvent());

                        } else {
                            ToastUtil.showSuccessMsg(mContext,addMountReduceModel.getMessage());
                        }
                    }
                });
    }


    private void addCarts(CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean item,int num, int id, int businessId, int productType, TextView tv_num,int cartNum) {
        AddMountChangeTwoAPI.AddMountChangeServices(mContext,productType,businessId,num,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddCartGoodModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddCartGoodModel addMountReduceModel) {
                        if (addMountReduceModel.isSuccess()) {
                            data.get(adapterPosition).setProductNum(num);
                            tv_num.setText(num + "");
                            EventBus.getDefault().post(new UpdateEvent(testAdapter.getAllPrice()));
                            EventBus.getDefault().post(new ReduceNumEvent());
                        } else {
                            ToastUtil.showSuccessMsg(mContext,addMountReduceModel.getMessage());
                        }
                    }
                });
    }

    public interface OnCartClickListener {

        void refreshMarket();

    }

}
