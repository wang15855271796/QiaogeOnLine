package com.puyue.www.qiaoge.activity.home;

import android.content.Intent;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.cart.AddCartAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.CartAddModel;
import com.puyue.www.qiaoge.model.home.TeamActiveQueryModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/1/17
 */
public class CouponsInnerAdapter extends BaseQuickAdapter<TeamActiveQueryModel.DataBean.ActivesBean,BaseViewHolder> {

    CouponsAdapter.Onclick onclick;
    private ImageView iv_pic;
    TextView tv_old_price;
    private TextView tv_total;
    ProgressBar pb;
    private TextView tv_add;
    private RelativeLayout rl_root;
    private RelativeLayout rl_coupon;
    private TextView tv_coupon;
    RelativeLayout rl_price;
    TextView tv_price;
    public CouponsInnerAdapter(int layoutResId, @Nullable List<TeamActiveQueryModel.DataBean.ActivesBean> data, CouponsAdapter.Onclick onclick) {
        super(layoutResId, data);
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamActiveQueryModel.DataBean.ActivesBean item) {
        ImageView iv_flag = helper.getView(R.id.iv_flag);
        TextView tv_send = helper.getView(R.id.tv_send);
        rl_price = helper.getView(R.id.rl_price);
        tv_price = helper.getView(R.id.tv_price);
        tv_old_price = helper.getView(R.id.tv_old_price);
        tv_coupon = helper.getView(R.id.tv_coupon);
        iv_pic = helper.getView(R.id.iv_pic);
        rl_root = helper.getView(R.id.rl_root);
        tv_add = helper.getView(R.id.tv_add);
        tv_total = helper.getView(R.id.tv_total);
        pb = helper.getView(R.id.pb);
        rl_coupon = helper.getView(R.id.rl_coupon);
        //得到字体库类型
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),
                "汉真广标.ttf");
        tv_coupon.setTypeface(typeface);

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                tv_send.setVisibility(View.VISIBLE);
            }else {
                tv_send.setVisibility(View.GONE);
            }
        }


        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getActiveName());
        helper.setText(R.id.tv_spec,item.getSpec());
        Glide.with(mContext).load(item.getSelfOrNot()).into(iv_flag);
        pb.setProgress(Integer.parseInt(item.getProgress()));
        pb.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.seckill_progress_yellow));
        tv_total.setText(item.getRemainNum());
        rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SpecialGoodDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getActiveId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });

        if(!item.getDiscount().equals("")) {
            helper.setText(R.id.tv_coupon,item.getDiscount());
            rl_coupon.setVisibility(View.VISIBLE);
        }else {
            rl_coupon.setVisibility(View.GONE);
        }
    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
        if (SharedPreferencesUtil.getString(mContext, "priceType").equals("1")) {
            rl_price.setVisibility(View.GONE);
            tv_add.setVisibility(View.VISIBLE);
            tv_price.setVisibility(View.VISIBLE);
            tv_price.setText(item.getPrice());
            tv_old_price.setVisibility(View.VISIBLE);
            tv_old_price.setText(item.getOldPrice());
            if (item.getSaleDone() == 0) {
                //已售完
                tv_add.setText("  已售罄  ");
                tv_add.setBackgroundResource(R.drawable.shape_detail_grey);
            } else {
                tv_add.setText("立即加购");
                tv_add.setBackgroundResource(R.drawable.shape_yellow6);
            }
        } else {
            rl_price.setVisibility(View.VISIBLE);
            tv_add.setVisibility(View.GONE);
            tv_price.setText("价格授权后可见");
            tv_old_price.setVisibility(View.INVISIBLE);
        }
    }else {
        tv_add.setVisibility(View.VISIBLE);
        tv_price.setText(item.getPrice());
        tv_add.setText("立即加购");
        tv_add.setBackgroundResource(R.drawable.shape_orange);

    }

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {

                }else {
                    if(onclick!=null) {
                        onclick.addDialog();
                    }
                }
            }
        });
        rl_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.addDialog();
                }
            }
        });
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_old_price.getPaint().setAntiAlias(true);//抗锯齿

        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    int activeId = item.getActiveId();
                    addCar(activeId, 11, 1);
                } else {
                    AppHelper.showMsg(mContext, "请先登录");
                    mContext.startActivity(LoginActivity.getIntent(mContext, LoginActivity.class));
                }
                getDatas(1);
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


    private void addCar(int businessId, int businessType, int totalNum) {
        AddCartAPI.requestData(mContext,businessType,businessId,totalNum)
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
                                    EventBus.getDefault().post(new ReduceNumEvent());
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                                }else {
                                    EventBus.getDefault().post(new ReduceNumEvent());
                                    ToastUtil.showSuccessMsg(mContext,cartAddModel.getData().getMessage());
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext,cartAddModel.getMessage());
                        }
                    }
                });
    }
}
