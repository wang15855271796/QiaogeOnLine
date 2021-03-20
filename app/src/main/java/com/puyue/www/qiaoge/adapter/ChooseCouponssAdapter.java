package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.dialog.CouponProdDialog;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/9/11
 */
public class ChooseCouponssAdapter extends BaseQuickAdapter<UserChooseDeductModel.DataBean, BaseViewHolder> {
    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    private Context context;
    TextView tv_desc;
    List<UserChooseDeductModel.DataBean> list;
    TextView tv_tip;
    ImageOnclick imageOnclick;
    ImageView iv_select;
    public ChooseCouponssAdapter(int layoutResId, @Nullable List<UserChooseDeductModel.DataBean> data, ImageOnclick imageOnclick) {
        super(layoutResId, data);
        list=data;
        this.context=context;
        this.imageOnclick = imageOnclick;
    }

    public void setStat() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFlags(false);
            iv_select.setBackgroundResource(R.mipmap.ic_pay_no);

        }
        notifyDataSetChanged();

    }

    @Override
    protected void convert(final BaseViewHolder helper, final UserChooseDeductModel.DataBean item) {
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_select = helper.getView(R.id.iv_select);
        if(!TextUtils.isEmpty(item.getApplyFrom())){
            tv_style.setText(item.getApplyFrom());
        }
        //item.getGiftType()+"   "+
        tv_user_factor.setText(item.getGiftName());
        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());

        if (item.getRole().size()>0){
            tv_role.setText(item.getRole().get(0));
            tv_role.setVisibility(View.VISIBLE);

        }else {
            tv_role.setText("");
            tv_role.setVisibility(View.INVISIBLE);
        }

        tv_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("1")||item.getGiftProdUseType().equals("2")) {
                    queryProd(item.getGiftDetailNo());
                }else {

                }

            }
        });
        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnclick.Onclick(helper.getLayoutPosition(), item.getGiftDetailNo());
            }
        });

        iv_select.setBackgroundResource(item.isFlags() ? R.mipmap.ic_pay_ok : R.mipmap.ic_pay_no);
        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));
            iv_select.setEnabled(true);
        }else  if (item.getState().equals("USED")){//USED 已使用
            tv_desc.setText(item.getUseInfo());
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("UN_ENABLED")) {
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }
//
//        couponsTitle = helper.getView(R.id.couponsTitle);
//        couponsCount = helper.getView(R.id.couponsCount);
//        LinearLayoutCoupon = helper.getView(R.id.LinearLayoutCoupon);
//        time = helper.getView(R.id.time);
//        note = helper.getView(R.id.note);
//        priceNum = helper.getView(R.id.priceNum);
//        LinearLayoutOne = helper.getView(R.id.LinearLayoutOne);
//        LinearLayoutView=helper.getView(R.id.LinearLayoutView);
//        unCouponsTitle = helper.getView(R.id.unCouponsTitle);
//        unCouponsCount = helper.getView(R.id.unCouponsCount);
//        unTime = helper.getView(R.id.unTime);
//        unNote = helper.getView(R.id.unNote);
//        unPriceNum = helper.getView(R.id.unPriceNum);
//        LinearLayoutTwo = helper.getView(R.id.LinearLayoutTwo);
//        LinearLayoutUnview=helper.getView(R.id.LinearLayoutUnview);
//
//
//
//
//        if (item.getState().equals("ENABLED")) {
//            couponsTitle.setText(item.getApplyFrom());
//            couponsCount.setText(item.getGiftType()+item.getGiftName());
//            time.setText(item.getDateTime());
//            priceNum.setText(item.getAmount() + "");
//            LinearLayoutCoupon.setBackgroundResource(item.isFlag() ? R.mipmap.ic_coupon_ : R.mipmap.ic_coupon_new);
//            Log.e(WeiyiTAG, "convert: "+item.isFlag() );
//            LinearLayoutTwo.setVisibility(View.GONE);
//            LinearLayoutOne.setVisibility(View.VISIBLE);
//            if (item.getRole().size() > 0) {
//                note.setText(item.getRole().get(0));
//                LinearLayoutView.setVisibility(View.VISIBLE);
//            }else {
//                note.setText("");
//                LinearLayoutView.setVisibility(View.GONE);
//            }
//
//
//        } else if (item.getState().equals("UN_ENABLED")){
//            unCouponsTitle.setText(item.getApplyFrom());
//            unCouponsCount.setText(item.getGiftType()+item.getGiftName());
//            unTime.setText(item.getDateTime()+"");
//            unPriceNum.setText(item.getAmount() + "");
//            LinearLayoutTwo.setVisibility(View.VISIBLE);
//          LinearLayoutOne.setVisibility(View.GONE);
//            if (item.getRole().size() > 0) {
//                LinearLayoutUnview.setVisibility(View.VISIBLE);
//                unNote.setText(item.getRole().get(0));
//            }else {
//                LinearLayoutUnview.setVisibility(View.GONE);
//                unNote.setText("");
//
//            }
//        }
//        LinearLayoutOne.setOnClickListener(new NoDoubleClickListener() {
//            @Override
//             public void onNoDoubleClick(View view) {
//                onclick.Onclick(helper.getLayoutPosition(), item.getGiftDetailNo());
//              }
//           }
//        );

    }
    String datas;
    private void queryProd(String giftDetailNo) {
        userChooseDeductAPI.queryProd(mContext,giftDetailNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<QueryProdModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(QueryProdModel model) {
                        if (model.isSuccess()) {

                            if(model.getData()!=null) {
                                datas = model.getData();

                                CouponProdDialog couponProdDialog = new CouponProdDialog(mContext,datas) {
                                    @Override
                                    public void Confirm() {
                                        dismiss();
                                    }
                                };
                                couponProdDialog.show();
                            }


                        } else {
//                            AppHelper.showMsg(mContext, model.getMessage());
                        }

                    }
                });
    }

    public interface ImageOnclick {
        void Onclick(int position, String giftDetailNo);
    }
}
