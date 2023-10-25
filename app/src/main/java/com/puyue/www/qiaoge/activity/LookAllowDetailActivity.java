package com.puyue.www.qiaoge.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LookAllowDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_business)
    RoundImageView iv_business;
    @BindView(R.id.tv_valid_time)
    TextView tv_valid_time;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_upload)
    TextView tv_upload;
    String checkNo;
    List<String> pictureLists = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        checkNo = getIntent().getStringExtra("checkNo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_allow_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        tv_upload.setVisibility(View.GONE);
        getDetailInfo();
    }

    ApplyInfoModel.DataBean data;
    private void getDetailInfo() {
        RecommendApI.getApplyInfo(mContext,checkNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApplyInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApplyInfoModel applyInfoModel) {
                        if (applyInfoModel.getCode()==1) {
                            if(applyInfoModel.getData()!=null) {
                                data = applyInfoModel.getData();
                                if(!TextUtils.isEmpty(data.getBusinessUrl())) {
                                    Glide.with(mContext).load(data.getBusinessUrl()).into(iv_business);
                                    pictureLists.add(data.getBusinessUrl());
                                }

                                if(!TextUtils.isEmpty(data.getBusinessValidity())) {
                                    tv_valid_time.setText(data.getBusinessValidity());
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, applyInfoModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        iv_business.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:


            case R.id.tv_ok:
                finish();
                break;

            case R.id.iv_business:
                if(pictureLists.size()>0) {
                    AppHelper.showIssueDetailDialog(mActivity, pictureLists, 0);
                }
                break;
        }
    }
}
