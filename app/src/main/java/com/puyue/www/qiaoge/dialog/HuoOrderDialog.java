package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.HuoHomeActivity;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.QueryHomePropupAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoOrderDialog extends Dialog {

    Context mContext;
    public RecyclerView recyclerView;
    ImageView iv_close;
    TextView tv_deal;
    TextView tv_content;
    IndexInfoModel.DataBean dataBean;
    public HuoOrderDialog(@NonNull Context context, IndexInfoModel.DataBean dataBean) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_huo_order);
        mContext = context;
        this.dataBean = dataBean;
        initView();
        initAction();

    }

    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_deal = (TextView) findViewById(R.id.tv_deal);
        iv_close = (ImageView) findViewById(R.id.iv_close);

        String content = dataBean.getHllTip().getContent();
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(content, 2,
                1, Color.parseColor("#FF3F3C"));
        tv_content.setText(spannableStringBuilder);
    }


    private void initAction() {
        tv_deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCloses();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getClose();
            }
        });
    }

    private void getCloses() {
        IndexHomeAPI.getCouponClose(mContext,dataBean.getHllTip().getId()+"")
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
                        if(baseModel.success) {
                            Intent intent = new Intent(mContext, HuoHomeActivity.class);
                            intent.putExtra("orderId","");
                            intent.putExtra("intFlag",1);
                            mContext.startActivity(intent);
                            dismiss();
                        }else {
                            AppHelper.showMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    private void getClose() {
        IndexHomeAPI.getCouponClose(mContext,dataBean.getHllTip().getId()+"")
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
                        if(baseModel.success) {
                            dismiss();
                        }else {
                            AppHelper.showMsg(mContext,baseModel.message);
                        }
                    }
                });
    }


}
