package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.Line;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.HuoReasonDialog;
import com.puyue.www.qiaoge.dialog.HuoReasonDialog1;
import com.puyue.www.qiaoge.event.HuoReason1Event;
import com.puyue.www.qiaoge.event.HuoReasonEvent;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.model.CancelReasonModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoCancelActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rl_reason)
    RelativeLayout rl_reason;
    @BindView(R.id.rl_reason1)
    RelativeLayout rl_reason1;
    @BindView(R.id.tv_reason1)
    TextView tv_reason1;
    @BindView(R.id.tv_reason2)
    TextView tv_reason2;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.ll_box1)
    LinearLayout ll_box1;
    @BindView(R.id.ll_box2)
    LinearLayout ll_box2;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    String displayId;
    int type;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        displayId = getIntent().getStringExtra("displayId");
        type = getIntent().getIntExtra("type",1);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_cancel);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        cancelOrder();
        if(type==1) {
            ll_box1.setVisibility(View.VISIBLE);
            ll_box2.setVisibility(View.GONE);
        }else {

            ll_box1.setVisibility(View.GONE);
            ll_box2.setVisibility(View.VISIBLE);
        }
    }

    List<CancelReasonModel.DataBean> list = new ArrayList<>();
    private void cancelOrder() {
        HuolalaAPI.cancelReason(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CancelReasonModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CancelReasonModel cancelReasonModel) {
                        if(cancelReasonModel.getCode()==1) {
                            list.clear();
                            if(cancelReasonModel.getData()!=null&&cancelReasonModel.getData().size()>0) {
                                list.addAll(cancelReasonModel.getData());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,cancelReasonModel.getMessage());
                        }
                    }
                });
    }


    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        rl_reason.setOnClickListener(this);
        rl_reason1.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_back:
                finish();
                break;

            case R.id.rl_reason:
                HuoReasonDialog huoReasonDialog = new HuoReasonDialog(mContext,list);
                huoReasonDialog.show();
                break;

            case R.id.rl_reason1:
                if(tv_reason1.getText().toString().equals("")|| TextUtils.isEmpty(tv_reason1.getText().toString())) {
                    ToastUtil.showErroMsg(mContext,"请先选择第一个原因");
                }else {
                    HuoReasonDialog1 huoReasonDialog1 = new HuoReasonDialog1(mContext,sub_reason_list);
                    huoReasonDialog1.show();
                }

                break;

            case R.id.tv_cancel:
                if(!name.equals("")&&!TextUtils.isEmpty(name)) {
                    cancelOrder(displayId,name);
                }else {
                    ToastUtil.showSuccessMsg(mContext,"请选择原因");
                }

                break;

            case R.id.tv_sure:
                cancelOrder(displayId,et_reason.getText().toString());
                break;


        }
    }

    /**
     * 取消订单
     * @param orderDisplayId
     * @param reason
     */
    private void cancelOrder(String orderDisplayId,String reason) {
        HuolalaAPI.cancelOrder(mActivity,orderDisplayId,reason)
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
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }


    List<CancelReasonModel.DataBean.SubReasonBean> sub_reason_list;
    String name = "";
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReason(HuoReasonEvent huoReasonEvent) {
        tv_reason1.setText(huoReasonEvent.getName());
        sub_reason_list = huoReasonEvent.getSub_reason();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getReason1(HuoReason1Event huoReasonEvent) {
        name = huoReasonEvent.getName();
        tv_reason2.setText(huoReasonEvent.getName());
    }
}
