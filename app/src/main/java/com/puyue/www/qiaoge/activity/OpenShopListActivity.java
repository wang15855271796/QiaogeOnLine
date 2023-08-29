package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.Serializable;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenShopListActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    IsApplyModel.DataBean applyData;
    String applyPhone;
    int basicFinish;
    int licenseFinish;
    int corporateFinish;
    int settleFinish;
    String checkNo;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        applyPhone = getIntent().getStringExtra("applyPhone");
        applyData = (IsApplyModel.DataBean) getIntent().getSerializableExtra("applyData");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop_list);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        tv_name.setText(applyData.getSupplierName());
        tv_time.setText("申请时间:"+applyData.getApplyTime());
//        0待完善 1待审核 2已通过 3未通过
        if(applyData.getStatus() == 0) {
            tv_status.setBackgroundResource(R.drawable.shape_orange);
            tv_status.setTextColor(Color.parseColor("#ffffff"));
        }else if(applyData.getStatus() == 1) {
            tv_status.setBackgroundResource(R.drawable.shape_grey3);
            tv_status.setTextColor(Color.parseColor("#414141"));
        }else if(applyData.getStatus() == 2) {
            tv_status.setBackgroundResource(R.drawable.shape_red_orders);
            tv_status.setTextColor(Color.parseColor("#ffffff"));
        }else if(applyData.getStatus() == 3) {
            tv_status.setBackgroundResource(R.drawable.shape_green1);
            tv_status.setTextColor(Color.parseColor("#ffffff"));
        }
        tv_status.setText(applyData.getStatusStr());
        getDetailInfo();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        rl_content.setOnClickListener(this);
    }

    private void getDetailInfo() {
        RecommendApI.getApplyInfo(mContext,applyData.getCheckNo())
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
                                ApplyInfoModel.DataBean data = applyInfoModel.getData();
                                basicFinish = data.getBasicFinish();
                                checkNo = data.getCheckNo();
                                licenseFinish = data.getLicenseFinish();
                                corporateFinish = data.getCorporateFinish();
                                settleFinish = data.getSettleFinish();

                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, applyInfoModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.rl_content:
                //0待完善 1待审核 2已通过 3未通过
                if(applyData.getStatus() == 0) {
                    //信息是否填写完整
                    if(basicFinish ==0) {
                        Intent intent = new Intent(mContext,OpenShopStep1Activity.class);
                        intent.putExtra("applyPhone", applyPhone);
                        intent.putExtra("checkNo",checkNo);
                        startActivity(intent);
                        break;
                    }else if(licenseFinish ==0) {
                        Intent intent = new Intent(mContext,OpenShopStep2Activity.class);
                        intent.putExtra("checkNo",checkNo);
                        intent.putExtra("applyPhone", applyPhone);
                        startActivity(intent);
                        break;
                    }else if(corporateFinish ==0) {
                        Intent intent = new Intent(mContext,OpenShopStep3Activity.class);
                        intent.putExtra("applyPhone", applyPhone);
                        intent.putExtra("checkNo",checkNo);
                        startActivity(intent);
                        break;
                    }else if(settleFinish ==0) {
                        Intent intent = new Intent(mContext,OpenShopStep4Activity.class);
                        intent.putExtra("applyPhone", applyPhone);
                        intent.putExtra("checkNo",checkNo);
                        startActivity(intent);
                        break;
                    }
                }else if(applyData.getStatus() == 1) {
                    Intent intent = new Intent(mContext,LookOpenShopStep1Activity.class);
                    intent.putExtra("applyData", (Serializable) applyData);
                    startActivity(intent);
                    break;
                }else if(applyData.getStatus() == 2) {
                    Intent intent = new Intent(mContext,LookOpenShopStep1Activity.class);
                    intent.putExtra("applyData", (Serializable) applyData);
                    startActivity(intent);
                    break;
                }else if(applyData.getStatus() == 3) {
                    Intent intent = new Intent(mContext,OpenShopStep1Activity.class);
                    intent.putExtra("applyPhone", applyPhone);
                    startActivity(intent);
                    break;
                }

                break;
        }
    }
}
