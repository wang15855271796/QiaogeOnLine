package com.puyue.www.qiaoge.adapter.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.cart.AddCartAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.CartAddModel;
import com.puyue.www.qiaoge.model.home.SeckillListModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.GlideModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王文博} on 2019/4/12
 */
public class SpikeActiveQueryAdapter extends BaseQuickAdapter<SeckillListModel.DataBean.KillsBean, BaseViewHolder> {

    private RoundImageView ivSpike;
    private TextView tvPrice;
    private TextView tvOldPrice;
    private TextView tvTitle;
    private TextView ivSoldOut;
    private TextView tvPsc;
    private TextView tvSale;
    private TextView tvSoldSale;
    private FrameLayout frameLayout;
    private ImageView ivSoldOutLeft;
    private ProgressBar mProgressBar;
    private int activedId;
    LinearLayout ll_root;
    TextView tv_add_remind;
    RelativeLayout rl_price;
    Onclick onclick;
    public SpikeActiveQueryAdapter(int layoutResId, @Nullable List<SeckillListModel.DataBean.KillsBean> data,int activedId,Onclick onclick) {
        super(layoutResId, data);
        this.activedId = activedId;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, SeckillListModel.DataBean.KillsBean item) {
        ImageView iv_send = helper.getView(R.id.iv_send);
        ImageView iv_flag = helper.getView(R.id.iv_flag);
        TextView tv_spread = helper.getView(R.id.tv_spread);
        ll_root = helper.getView(R.id.ll_root);
        rl_price = helper.getView(R.id.rl_price);
        tv_add_remind = helper.getView(R.id.tv_add_remind);
        ivSpike = helper.getView(R.id.iv_item_spike_img);
        tvTitle = helper.getView(R.id.tv_item_spike_title);
        tvPrice = helper.getView(R.id.tv_item_spike_price);
        tvOldPrice = helper.getView(R.id.tv_item_spike_old_price);
        ivSoldOut = helper.getView(R.id.tv_sold_out);
        tvPsc = helper.getView(R.id.tv_item_spike_specification);
        frameLayout = helper.getView(R.id.iv_bg);
        ivSoldOutLeft = helper.getView(R.id.iv_sold);
        tvSale = helper.getView(R.id.tv_item_spike_sales);
        mProgressBar = helper.getView(R.id.pb_item_spike);
        tvSoldSale = helper.getView(R.id.tv_item_spike_sales);
        tvTitle.setText(item.title);
        tvOldPrice.setText(item.oldPrice);
        tvPsc.setText(item.spec);
        tvSale.setText(item.sales);
        if(!item.spread.equals("")&& !TextUtils.isEmpty(item.spread)) {
            tv_spread.setVisibility(View.VISIBLE);
            tv_spread.setText(item.spread);
            tv_spread.setBackgroundResource(R.drawable.shape_orange24);
        }else {
            tv_spread.setVisibility(View.GONE);
            tv_spread.setBackgroundResource(R.drawable.shape_orange24);
        }
        mProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.seckill_progress));
        mProgressBar.setProgress(Integer.parseInt(item.progress));
        tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); //中划线
        tvSoldSale.setText("已抢购" + item.progress + "%");
        String spikeFlag = UserInfoHelper.getSpikePosition(mContext);
        GlideModel.disPlayError(mContext,item.pic,ivSpike);
        Glide.with(mContext).load(item.selfOrNot).into(iv_flag);
        if(item.notSend!=null) {
            if(item.notSend.equals("1")||item.notSend.equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
            if(Integer.parseInt(spikeFlag)==0) {
                //未开始
                tv_add_remind.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
                tvSale.setVisibility(View.GONE);
            }else {
                // 已开始
                ivSoldOut.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                tvSale.setVisibility(View.VISIBLE);
                if(item.soldOut==0) {
                    ivSoldOut.setVisibility(View.GONE);
                    tv_add_remind.setVisibility(View.VISIBLE);
                }else {
                    ivSoldOut.setVisibility(View.VISIBLE);
                    tv_add_remind.setVisibility(View.GONE);
                }
            }
            tvPrice.setText(item.price);
            rl_price.setVisibility(View.GONE);
        }else {
            if(Integer.parseInt(spikeFlag)==0) {
                //未开始
                tv_add_remind.setVisibility(View.GONE);
            }else {
                // 已开始
                ivSoldOut.setVisibility(View.GONE);
                if(item.soldOut==0) {
                    ivSoldOut.setVisibility(View.GONE);
                    tv_add_remind.setVisibility(View.VISIBLE);
                }else {
                    ivSoldOut.setVisibility(View.VISIBLE);
                    tv_add_remind.setVisibility(View.GONE);
                }
            }
            tvPrice.setText("价格授权后可见");
            ivSoldOut.setVisibility(View.GONE);
            tv_add_remind.setVisibility(View.GONE);
            rl_price.setVisibility(View.VISIBLE);
        }

        rl_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(onclick!=null) {
                        onclick.tipClick();
                    }
                }else {
                    Intent intent = new Intent(mContext,LoginActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
        tv_add_remind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    int activeId = item.activeId;
                    addCar(activeId,2, 1);
                    getDatas(1);
                } else {
                    AppHelper.showMsg(mContext, "请先登录");
                    mContext.startActivity(LoginActivity.getIntent(mContext, LoginActivity.class));
                }
            }
        });

        if (Integer.parseInt(spikeFlag) == 0) {
            //秒杀预告
            ivSoldOut.setVisibility(View.GONE);
            ivSoldOutLeft.setVisibility(View.GONE);
            frameLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
            ivSoldOut.setVisibility(View.GONE);
        } else if (Integer.parseInt(spikeFlag) == 1) {

            if (item.soldOut == 0) {
                ivSoldOut.setVisibility(View.GONE);
                ivSoldOutLeft.setVisibility(View.GONE);

            } else {
                ivSoldOut.setVisibility(View.VISIBLE);

                GlideModel.disPlayError(mContext,item.flagUrl,ivSoldOutLeft);
                ivSoldOutLeft.setVisibility(View.VISIBLE);

            }
        }
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

    private void addCar(int businessId,int businessType, int totalNum) {
        AddCartAPI.requestData(mContext,businessType, businessId, totalNum)
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

    public interface Onclick {
        void tipClick();
    }



}
