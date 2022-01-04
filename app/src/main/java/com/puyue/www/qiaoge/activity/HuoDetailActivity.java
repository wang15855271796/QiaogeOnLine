package com.puyue.www.qiaoge.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HuoPayAdapter;
import com.puyue.www.qiaoge.adapter.HuoPayedAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.AddTipDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoListModel;
import com.puyue.www.qiaoge.model.HuoPriceModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_zhuang_address)
    TextView tv_zhuang_address;
    @BindView(R.id.tv_zhuang_desc)
    TextView tv_zhuang_desc;
    @BindView(R.id.tv_xie_address)
    TextView tv_xie_address;
    @BindView(R.id.tv_z_contact)
    TextView tv_z_contact;
    @BindView(R.id.tv_z_phone)
    TextView tv_z_phone;
    @BindView(R.id.tv_x_desc)
    TextView tv_x_desc;
    @BindView(R.id.tv_x_contact)
    TextView tv_x_contact;
    @BindView(R.id.tv_x_phone)
    TextView tv_x_phone;
    @BindView(R.id.tv_order_id)
    TextView tv_order_id;
    @BindView(R.id.tv_copy)
    TextView tv_copy;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_car_style)
    TextView tv_car_style;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_beizhu)
    TextView tv_beizhu;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_payed)
    TextView tv_payed;
    @BindView(R.id.tv_payed_money)
    TextView tv_payed_money;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.rl_foot)
    RelativeLayout rl_foot;
    @BindView(R.id.tv_d_name)
    TextView tv_d_name;
    @BindView(R.id.rl_location)
    RelativeLayout rl_location;
    @BindView(R.id.tv_d_car)
    TextView tv_d_car;
    @BindView(R.id.tv_d_phone)
    TextView tv_d_phone;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;
    @BindView(R.id.ll_order_state)
    LinearLayout ll_order_state;
    @BindView(R.id.rv_payed)
    RecyclerView rv_payed;
    @BindView(R.id.rl_phone)
    RelativeLayout rl_phone;
    String id;
    HuoPayAdapter huoPayAdapter;
    HuoPayedAdapter huoPayedAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_huo_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        getHuoDetail(id);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        huoPayAdapter = new HuoPayAdapter(R.layout.item_huo_pay,priceInfoList);
        recyclerView.setAdapter(huoPayAdapter);


    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_copy.setOnClickListener(this);
        tv_payed.setOnClickListener(this);
        tv_add.setOnClickListener(this);
        rl_location.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        ll_order_state.setOnClickListener(this);
        rl_phone.setOnClickListener(this);
    }


    HuoDetailModel.DataBean data;
    List<Integer> payPriceList = new ArrayList<>();
    List<HuoDetailModel.DataBean.PriceInfoBean> priceInfoList = new ArrayList<>();
    HuoPriceModel huoPriceModel = new HuoPriceModel();
    private void getHuoDetail(String orderDisplayId) {
        HuolalaAPI.getHuoDetail(mActivity,orderDisplayId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoDetailModel huoDetailModel) {
                        if(huoDetailModel.getCode()==1) {
                            if(huoDetailModel.getData()!=null) {
                                data = huoDetailModel.getData();
                                priceInfoList.clear();
                                payPriceList.clear();
                                tv_zhuang_address.setText(data.getSendAddr().getName());
                                tv_zhuang_desc.setText(data.getSendAddr().getAddrInfo());
                                tv_z_contact.setText(data.getSendAddr().getContactName());
                                tv_z_phone.setText(data.getSendAddr().getContactPhone());
                                tv_xie_address.setText(data.getSendAddr().getName());
                                tv_x_phone.setText(data.getSendAddr().getContactPhone());
                                tv_x_contact.setText(data.getSendAddr().getContactName());
                                tv_x_desc.setText(data.getSendAddr().getAddrInfo());
                                tv_order_id.setText(data.getOrderDisplayId());
                                tv_state.setText(data.getOrderStatusName());
                                if(data.getDriverInfo()!=null) {
                                    tv_car_style.setText(data.getDriverInfo().getVehicleName());
                                }

                                tv_time.setText(data.getOrderTime());
                                tv_contact.setText(data.getContactPhone());
                                tv_beizhu.setText(data.getOrderRemark());
                                tv_create_time.setText(data.getCreateTime());
                                tv_price.setText(data.getTotalPrice()+"");
                                tv_payed_money.setText(data.getPayPrice()+"");
                                if(data.getDriverInfo()!=null) {
                                    HuoDetailModel.DataBean.DriverInfoBean driverInfo = data.getDriverInfo();
                                    tv_d_name.setText(driverInfo.getName());
                                    tv_d_car.setText(driverInfo.getVehicleName());
                                    tv_d_phone.setText(driverInfo.getPhone());
                                }

                                if(data.getCanAddTips()==1) {
                                    rl_foot.setVisibility(View.VISIBLE);
                                }else {
                                    rl_foot.setVisibility(View.GONE);
                                }

                                for (int i = 0; i < priceInfoList.size(); i++) {
                                    HuoDetailModel.DataBean.PriceInfoBean priceInfoBean = priceInfoList.get(i);
                                    if(priceInfoList.get(i).getPayStatus()==4) {
                                        huoPriceModel.setAmount(priceInfoBean.getAmount());
                                        huoPriceModel.setBillTypeName(priceInfoBean.getBillTypeName());
                                        huoPriceModel.setAmount(priceInfoBean.getAmount());
                                        huoPriceModel.setAmount(priceInfoBean.getAmount());
                                    }
                                }

                                rv_payed.setLayoutManager(new LinearLayoutManager(mContext));
                                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_payed,priceInfoList,payPriceList);
                                rv_payed.setAdapter(huoPayedAdapter);

                                huoPayedAdapter.notifyDataSetChanged();
                                huoPayAdapter.notifyDataSetChanged();

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoDetailModel.getMessage());
                        }
                    }
                });
    }

    private void getAddTip(String orderDisplayId,String tips) {
        HuolalaAPI.getTips(mActivity,orderDisplayId,tips)
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
                            addTipDialog.dismiss();
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }



    boolean isOpen = false;
    AddTipDialog addTipDialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + data.getDriverInfo().getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.ll_order_state:

                break;

            case R.id.iv_back:
                finish();
                break;

            case  R.id.tv_copy:
                //获取剪贴板管理器：
                ClipboardManager cm1 = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData1 = ClipData.newPlainText("Label",data.getOrderDisplayId());
                // 将ClipData内容放到系统剪贴板里。
                cm1.setPrimaryClip(mClipData1);
                AppHelper.showMsg(mContext, "复制成功");

                break;
            case R.id.tv_payed:
                if(!isOpen) {
                    recyclerView.setVisibility(View.VISIBLE);
                    isOpen = true;
                }else {
                    isOpen = false;
                    recyclerView.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_add:
                addTipDialog = new AddTipDialog(mContext) {
                    @Override
                    public void Confirm(String amount) {
                        getAddTip(data.getOrderDisplayId(),amount);

                    }
                };
                addTipDialog.show();
                break;

            case R.id.rl_location:

                break;

            case R.id.tv_cancel:
                if(data.getNeedToCancelReason()==1) {
                    Intent intent1 = new Intent(mContext,HuoCancelActivity.class);
                    intent1.putExtra("displayId",data.getOrderDisplayId());
                    intent1.putExtra("type",1);
                    startActivity(intent1);
                }else {
                    Intent intents = new Intent(mContext,HuoCancelActivity.class);
                    intents.putExtra("displayId",data.getOrderDisplayId());
                    intents.putExtra("type",2);
                    startActivity(intents);
                }

                finish();
                break;
        }
    }

}
