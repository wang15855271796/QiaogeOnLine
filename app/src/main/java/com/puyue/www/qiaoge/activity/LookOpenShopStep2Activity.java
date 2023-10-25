package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.utils.ToastUtil;


import java.io.Serializable;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LookOpenShopStep2Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_license)
    TextView tv_license;
    @BindView(R.id.tv_allow)
    TextView tv_allow;
    @BindView(R.id.iv_up_business)
    ImageView iv_up_business;
    @BindView(R.id.iv_up_allow)
    ImageView iv_up_allow;
    @BindView(R.id.rl_business)
    RelativeLayout rl_business;
    @BindView(R.id.rl_allow)
    RelativeLayout rl_allow;
    ApplyInfoModel.DataBean detailInfo;
    String applyPhone;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        applyPhone = getIntent().getStringExtra("applyPhone");
        detailInfo = (ApplyInfoModel.DataBean)getIntent().getSerializableExtra("detailInfo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_open_shop_step2);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        QiaoGeApplication.getInstance().addActivity(this);
        if(detailInfo!=null) {
            if(detailInfo.getLicenseFinish() == 1) {
                tv_allow.setText("已完成");
            }else {
                tv_license.setText("已完成");
            }
        }
        getDetailInfo(detailInfo.getCheckNo());
    }

    ApplyInfoModel.DataBean data;
    private void getDetailInfo(String checkNo) {
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
                                if(data.getLicenseFinish() == 1) {
                                    iv_up_business.setVisibility(View.GONE);
                                    iv_up_allow.setVisibility(View.GONE);
                                    tv_license.setVisibility(View.VISIBLE);
                                    tv_allow.setVisibility(View.VISIBLE);
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
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        rl_business.setOnClickListener(this);
        rl_allow.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.tv_next:
                Intent intent = new Intent(mContext, LookOpenShopStep3Activity.class);
                intent.putExtra("applyPhone",applyPhone);
                intent.putExtra("detailInfo", (Serializable) detailInfo);
                startActivity(intent);
                break;

            case R.id.rl_business:
                Intent intent2 = new Intent(mContext,LookBusinessDetailActivity.class);
                intent2.putExtra("checkNo",data.getCheckNo());
                startActivity(intent2);
                break;

            case R.id.rl_allow:
                Intent intent3 = new Intent(mContext,LookAllowDetailActivity.class);
                intent3.putExtra("checkNo",data.getCheckNo());
                startActivity(intent3);
                break;

        }
    }





}
