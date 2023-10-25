package com.puyue.www.qiaoge.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class LookBusinessDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_business)
    RoundImageView iv_business;
    @BindView(R.id.tv_register_num)
    TextView tv_register_num;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.tv_place_business)
    TextView tv_place_business;
    @BindView(R.id.rb_personal)
    RadioButton rb_personal;
    @BindView(R.id.rb_company)
    RadioButton rb_company;
    @BindView(R.id.tv_regular)
    TextView tv_regular;
    @BindView(R.id.tv_long_term)
    TextView tv_long_term;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    String licensePath;
    int licenseLongTerm = -1;
    String licenseValidityStart;
    String licenseValidityEnd;
    List<String> pictureLists = new ArrayList<>();
    String checkNo;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        checkNo = getIntent().getStringExtra("checkNo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_business_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

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

                                if(data!=null) {
                                    if(!TextUtils.isEmpty(data.getLicenseUrl())) {
                                        Glide.with(mContext).load(data.getLicenseUrl()).into(iv_business);
                                        pictureLists.add(data.getLicenseUrl());
                                    }
                                    if(!TextUtils.isEmpty(data.getLicenseNo())) {
                                        tv_register_num.setText(data.getLicenseNo());
                                    }

                                    if(!TextUtils.isEmpty(data.getCompanyName())) {
                                        tv_company_name.setText(data.getCompanyName());
                                    }

                                    if(!TextUtils.isEmpty(data.getCompanyAddress())) {
                                        tv_place_business.setText(data.getCompanyAddress());
                                    }

                                    if(data.getCompanyType() == 0) {
                                        //企业
                                        rb_company.setChecked(true);
                                    }else {
                                        rb_personal.setChecked(true);
                                    }

                                    if(data.getLicenseLongTerm() == 0) {
                                        //否
                                        ll_time.setVisibility(View.VISIBLE);
                                        tv_regular.setBackgroundResource(R.drawable.shape_orange27);
                                        tv_long_term.setBackgroundResource(R.drawable.shape_grey3);
                                        tv_regular.setTextColor(Color.parseColor("#FF3D03"));
                                        tv_long_term.setTextColor(Color.parseColor("#414141"));
                                        tv_start_time.setText(data.getLicenseValidityStart());
                                        tv_end_time.setText(data.getLicenseValidityEnd());
                                    }else {
                                        ll_time.setVisibility(View.GONE);
                                        tv_regular.setBackgroundResource(R.drawable.shape_grey3);
                                        tv_long_term.setBackgroundResource(R.drawable.shape_orange27);
                                        tv_regular.setTextColor(Color.parseColor("#414141"));
                                        tv_long_term.setTextColor(Color.parseColor("#FF3D03"));

                                    }
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

            case R.id.tv_regular:
                licenseLongTerm = 0;
                ll_time.setVisibility(View.VISIBLE);
                tv_regular.setBackgroundResource(R.drawable.shape_orange27);
                tv_long_term.setBackgroundResource(R.drawable.shape_grey3);
                tv_regular.setTextColor(Color.parseColor("#FF3D03"));
                tv_long_term.setTextColor(Color.parseColor("#414141"));
                break;

            case R.id.tv_long_term:
                licenseLongTerm = 1;
                licenseValidityStart = "";
                licenseValidityEnd = "";
                ll_time.setVisibility(View.GONE);
                tv_regular.setBackgroundResource(R.drawable.shape_grey3);
                tv_long_term.setBackgroundResource(R.drawable.shape_orange27);
                tv_regular.setTextColor(Color.parseColor("#414141"));
                tv_long_term.setTextColor(Color.parseColor("#FF3D03"));
                break;
        }
    }
}
