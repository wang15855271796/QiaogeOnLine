package com.puyue.www.qiaoge.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.api.home.GetSpecialDetailAPI;
import com.puyue.www.qiaoge.banner.BannerConfig;
import com.puyue.www.qiaoge.banner.GlideImageLoader;
import com.puyue.www.qiaoge.banner.Transformer;
import com.puyue.www.qiaoge.banner.listener.OnBannerListener;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.IntelliModel;
import com.puyue.www.qiaoge.model.home.SpecialGoodModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.utils.Utils;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/9/22
 */
public class IntelliGencyInfoActivity extends BaseSwipeActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.iv_licence)
    ImageView iv_licence;
    @BindView(R.id.tv_business_date)
    TextView tv_business_date;
    @BindView(R.id.iv_food_licence)
    ImageView iv_food_licence;
    String id;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_intelligency_info);
        id = getIntent().getStringExtra("id");
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void setClickEvent() {
        getIntelliInfo(id);
    }

    private void getIntelliInfo(String id) {
        GetSpecialDetailAPI.getIntelliInfo(mContext, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IntelliModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }


                    @Override
                    public void onNext(IntelliModel model) {
                        if (model.isSuccess()) {
                            IntelliModel.DataBean data = model.getData();
                            tv_area.setText(data.getAddress());
                            tv_date.setText(data.getLicenseValidity());
                            Glide.with(mContext).load(data.getLicensePic()).into(iv_licence);
                            tv_business_date.setText(data.getBusinessValidity());
                            Glide.with(mContext).load(data.getBusinessUrl()).into(iv_food_licence);
                        } else {
                            AppHelper.showMsg(mContext, model.getMessage());
                        }

                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
