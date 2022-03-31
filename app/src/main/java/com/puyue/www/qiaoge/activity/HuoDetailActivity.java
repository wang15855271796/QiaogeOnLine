package com.puyue.www.qiaoge.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.puyue.www.qiaoge.activity.flow.FlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagAdapter;
import com.puyue.www.qiaoge.activity.flow.TagFlowLayout;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.adapter.HuoPayAdapter;
import com.puyue.www.qiaoge.adapter.HuoPayedAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.AddTipDialog;
import com.puyue.www.qiaoge.dialog.XieShangDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoDriverPayModel;
import com.puyue.www.qiaoge.model.HuoListModel;
import com.puyue.www.qiaoge.model.HuoPriceModel;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.Arith;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @BindView(R.id.rv_unPay)
    RecyclerView rv_unPay;
    @BindView(R.id.rv_success)
    RecyclerView rv_success;
    @BindView(R.id.rv_payFailed)
    RecyclerView rv_payFailed;
    @BindView(R.id.rv_backing)
    RecyclerView rv_backing;
    @BindView(R.id.rv_paying)
    RecyclerView rv_paying;
    @BindView(R.id.rv_apply)
    RecyclerView rv_apply;
    @BindView(R.id.tv_payed)
    TextView tv_payed;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.tv_payed_money)
    TextView tv_payed_money;
    @BindView(R.id.tv_add)
    TextView tv_add;
    @BindView(R.id.rl_foot)
    RelativeLayout rl_foot;
    @BindView(R.id.rl_foot1)
    RelativeLayout rl_foot1;
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
    @BindView(R.id.tv_backing)
    TextView tv_backing;
    @BindView(R.id.tv_paySuccess)
    TextView tv_paySuccess;
    @BindView(R.id.tv_paying)
    TextView tv_paying;
    @BindView(R.id.tv_apply)
    TextView tv_apply;
    @BindView(R.id.tv_payFailed)
    TextView tv_payFailed;
    @BindView(R.id.tv_unPay)
    TextView tv_unPay;
    @BindView(R.id.tv_unPay_money)
    TextView tv_unPay_money;
    @BindView(R.id.tv_payFailed_money)
    TextView tv_payFailed_money;
    @BindView(R.id.tv_backing_money)
    TextView tv_backing_money;
    @BindView(R.id.tv_success_money)
    TextView tv_success_money;
    @BindView(R.id.tv_paying_money)
    TextView tv_paying_money;
    @BindView(R.id.tv_apply_money)
    TextView tv_apply_money;
    @BindView(R.id.ll_payed)
    LinearLayout ll_payed;
    @BindView(R.id.ll_paying)
    LinearLayout ll_paying;
    @BindView(R.id.ll_unPay)
    LinearLayout ll_unPay;
    @BindView(R.id.ll_backing)
    LinearLayout ll_backing;
    @BindView(R.id.ll_apply)
    LinearLayout ll_apply;
    @BindView(R.id.ll_success)
    LinearLayout ll_success;
    @BindView(R.id.ll_failed)
    LinearLayout ll_failed;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.iv_wen)
    ImageView iv_wen;
    @BindView(R.id.ll_driver)
    LinearLayout ll_driver;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.rv_huo)
    TagFlowLayout rv_huo;
    @BindView(R.id.rl_open)
    RelativeLayout rl_open;
    @BindView(R.id.tv_open)
    TextView tv_open;
    @BindView(R.id.iv_open)
    ImageView iv_open;
    @BindView(R.id.ll_order)
    LinearLayout ll_order;
    String id;
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
        tv_paying.setOnClickListener(this);
        tv_unPay.setOnClickListener(this);
        tv_backing.setOnClickListener(this);
        tv_paySuccess.setOnClickListener(this);
        tv_apply.setOnClickListener(this);
        tv_payFailed.setOnClickListener(this);
        tv_desc.setOnClickListener(this);
        tv_pay.setOnClickListener(this);
        iv_wen.setOnClickListener(this);
        rl_open.setOnClickListener(this);
    }


    HuoDetailModel.DataBean data;
    List<Integer> payPriceList = new ArrayList<>();
    List<HuoDetailModel.DataBean.PriceInfoBean> priceInfoList = new ArrayList<>();
    List<HuoPriceModel> list1 = new ArrayList<>();
    List<HuoPriceModel> list2 = new ArrayList<>();
    List<HuoPriceModel> list3 = new ArrayList<>();
    List<HuoPriceModel> list4 = new ArrayList<>();
    List<HuoPriceModel> list5 = new ArrayList<>();
    List<HuoPriceModel> list6 = new ArrayList<>();
    List<HuoPriceModel> list7 = new ArrayList<>();
    List<HuoDetailModel.DataBean.ConnectOrdersBean> hllConnectOrders = new ArrayList<>();
    TagAdapter unAbleAdapter;
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
                                tv_xie_address.setText(data.getReceiveAddr().getName());
                                tv_x_phone.setText(data.getReceiveAddr().getContactPhone());
                                tv_x_contact.setText(data.getReceiveAddr().getContactName());
                                tv_x_desc.setText(data.getReceiveAddr().getAddrInfo());
                                tv_order_id.setText(data.getOrderDisplayId());
                                tv_state.setText(data.getOrderStatusName());
                                tv_car_style.setText(data.getVehicleName());
                                tv_time.setText(data.getOrderTime());
                                tv_contact.setText(data.getContactPhone());
                                tv_beizhu.setText(data.getOrderRemark());
                                tv_create_time.setText(data.getCreateTime());
                                tv_price.setText(data.getTotalPrice()+"");
                                tv_total.setText(data.getUnpaidPrice());
                                if(data.getDriverInfo()!=null) {
                                    HuoDetailModel.DataBean.DriverInfoBean driverInfo = data.getDriverInfo();
                                    tv_d_name.setText(driverInfo.getName());
                                    tv_d_car.setText(driverInfo.getVehicleName());
                                    tv_d_phone.setText(driverInfo.getPhone());
                                    ll_driver.setVisibility(View.VISIBLE);
                                }else {
                                    ll_driver.setVisibility(View.GONE);
                                }

                                //关联订单
                                hllConnectOrders.clear();
                                if(data.getConnectOrders()!=null&&data.getConnectOrders().size()>0) {
                                    if(data.getConnectOrders().size()>2) {
                                        rl_open.setVisibility(View.VISIBLE);
                                    }else {
                                        rl_open.setVisibility(View.GONE);
                                    }
                                    ll_order.setVisibility(View.VISIBLE);
                                    List<HuoDetailModel.DataBean.ConnectOrdersBean> connectOrders = data.getConnectOrders();
                                    hllConnectOrders.addAll(connectOrders);
                                    rv_huo.setVisibility(View.VISIBLE);

                                    unAbleAdapter = new TagAdapter<HuoDetailModel.DataBean.ConnectOrdersBean>(hllConnectOrders){
                                        @Override
                                        public View getView(FlowLayout parent, int position, HuoDetailModel.DataBean.ConnectOrdersBean connectOrdersBean) {
                                            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_huo_connection,rv_huo, false);
                                            TextView tv_connect = view.findViewById(R.id.tv_connect);
                                            tv_connect.setText(connectOrdersBean.getOrderId()+"("+connectOrdersBean.getState()+")");

                                            tv_connect.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(mActivity, NewOrderDetailActivity.class);
                                                    intent.putExtra(AppConstant.ORDERID,connectOrdersBean.getOrderId());
                                                    startActivity(intent);
//                                                    getHuoDetail(connectOrdersBean.getOrderId());
                                                }
                                            });
                                            return view;
                                        }
                                    };
                                    rv_huo.setAdapter(unAbleAdapter);
                                    unAbleAdapter.notifyDataChanged();
                                }else {
                                    ll_order.setVisibility(View.GONE);
                                }

                                if(data.getCanOrderCancel()==1) {
                                    tv_cancel.setVisibility(View.VISIBLE);
                                }else {
                                    tv_cancel.setVisibility(View.GONE);
                                }
                                if(data.getCanAddTips()==1) {
                                    rl_foot1.setVisibility(View.GONE);
                                    rl_foot.setVisibility(View.VISIBLE);

                                }else {
                                    rl_foot.setVisibility(View.GONE);
                                    if(data.getNeedToPay()==0||data.getNeedToPay()==2) {
                                        rl_foot1.setVisibility(View.GONE);
                                    }else if(data.getNeedToPay()==1) {
                                        rl_foot1.setVisibility(View.VISIBLE);
                                        if(data.getAppealEnabledStatus()==1) {
                                            //费用协商
                                            tv_desc.setVisibility(View.VISIBLE);
                                            tv_desc.setText("费用协商");
                                            iv_wen.setVisibility(View.VISIBLE);
                                        }else if(data.getAppealEnabledStatus()==2) {
                                            //申诉
                                            tv_desc.setText("账单申诉");
                                            tv_desc.setVisibility(View.VISIBLE);
                                            iv_wen.setVisibility(View.VISIBLE);
                                        }else{
                                            tv_desc.setVisibility(View.GONE);
                                            iv_wen.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                priceInfoList.addAll(data.getPriceInfo());
                                for (int i = 0; i < priceInfoList.size(); i++) {
                                    getState(priceInfoList.get(i),priceInfoList.get(i).getPayStatus());
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoDetailModel.getMessage());
                        }
                    }
                });
    }

    BigDecimal amount00 = new BigDecimal("0.00");
    BigDecimal amount11 = new BigDecimal("0.00");
    BigDecimal amount22 = new BigDecimal("0.00");
    BigDecimal amount33 = new BigDecimal("0.00");
    BigDecimal amount44 = new BigDecimal("0.00");
    BigDecimal amount55 = new BigDecimal("0.00");
    BigDecimal amount77 = new BigDecimal("0.00");
    private void getState(HuoDetailModel.DataBean.PriceInfoBean priceInfoList, int payStatus) {
        String billTypeName = priceInfoList.getBillTypeName();

        switch (payStatus) {
            case 0:
                ll_unPay.setVisibility(View.VISIBLE);
                //"未支付"
                BigDecimal amount0 = new BigDecimal(priceInfoList.getAmount());
                amount00 = amount00.add(amount0);
                BigDecimal amount000 = amount00.setScale(2, RoundingMode.FLOOR);
                tv_unPay_money.setText(amount000.doubleValue()+"");
                HuoPriceModel huoPriceModel0 = new HuoPriceModel();
                huoPriceModel0.setAmount(priceInfoList.getAmount());
                huoPriceModel0.setBillTypeName(billTypeName);
                huoPriceModel0.setImgUrl(priceInfoList.getImgUrl());
                list2.add(huoPriceModel0);

                rv_unPay.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list2);
                rv_unPay.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 1:
                //"已支付"
                ll_payed.setVisibility(View.VISIBLE);
                BigDecimal amount1 = new BigDecimal(priceInfoList.getAmount());
                amount11 = amount11.add(amount1);
                BigDecimal amount111 = amount11.setScale(2, RoundingMode.HALF_UP);
                tv_payed_money.setText(amount111.doubleValue()+"");
                HuoPriceModel huoPriceModel = new HuoPriceModel();
                huoPriceModel.setAmount(priceInfoList.getAmount());
                huoPriceModel.setBillTypeName(billTypeName);
                huoPriceModel.setImgUrl(priceInfoList.getImgUrl());
                list1.add(huoPriceModel);

                rv_payed.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list1);
                rv_payed.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 2:
                //"支付失败"
                ll_failed.setVisibility(View.VISIBLE);
                BigDecimal amount2 = new BigDecimal(priceInfoList.getAmount());
                amount22 = amount22.add(amount2);
                BigDecimal amount222 = amount22.setScale(2, RoundingMode.HALF_UP);
                tv_payFailed_money.setText(amount222.doubleValue()+"");
                HuoPriceModel huoPriceModel2 = new HuoPriceModel();
                huoPriceModel2.setAmount(priceInfoList.getAmount());
                huoPriceModel2.setImgUrl(priceInfoList.getImgUrl());
                huoPriceModel2.setBillTypeName(billTypeName);
                list3.add(huoPriceModel2);

                rv_payFailed.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list3);
                rv_payFailed.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 3:
                //"退款中"
                ll_backing.setVisibility(View.VISIBLE);
                BigDecimal amount3 = new BigDecimal(priceInfoList.getAmount());
                amount33 = amount33.add(amount3);
                BigDecimal amount333 = amount33.setScale(2, RoundingMode.HALF_UP);
                tv_backing_money.setText(amount333.doubleValue()+"");
                HuoPriceModel huoPriceModel3 = new HuoPriceModel();
                huoPriceModel3.setAmount(priceInfoList.getAmount());
                huoPriceModel3.setImgUrl(priceInfoList.getImgUrl());
                huoPriceModel3.setBillTypeName(billTypeName);
                list4.add(huoPriceModel3);

                rv_backing.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list4);
                rv_backing.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 4:
                //"退款成功"
                ll_success.setVisibility(View.VISIBLE);
                BigDecimal amount4 = new BigDecimal(priceInfoList.getAmount());
                amount44 = amount44.add(amount4);
                BigDecimal amount444 = amount44.setScale(2, RoundingMode.HALF_UP);
                tv_success_money.setText(amount444.doubleValue()+"");
                HuoPriceModel huoPriceModel4 = new HuoPriceModel();
                huoPriceModel4.setAmount(priceInfoList.getAmount());
                huoPriceModel4.setBillTypeName(billTypeName);
                huoPriceModel4.setImgUrl(priceInfoList.getImgUrl());
                list5.add(huoPriceModel4);

                rv_success.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list5);
                rv_success.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 5:
                //"支付中"
                ll_paying.setVisibility(View.VISIBLE);
                BigDecimal amount5 = new BigDecimal(priceInfoList.getAmount());
                amount55 = amount55.add(amount5);
                BigDecimal amount555 = amount55.setScale(2, RoundingMode.HALF_UP);
                tv_paying_money.setText(amount555.doubleValue()+"");
                HuoPriceModel huoPriceModel5 = new HuoPriceModel();
                huoPriceModel5.setAmount(priceInfoList.getAmount());
                huoPriceModel5.setBillTypeName(billTypeName);
                huoPriceModel5.setImgUrl(priceInfoList.getImgUrl());
                list6.add(huoPriceModel5);

                rv_paying.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list6);
                rv_paying.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

            case 7:
//                "申诉中"
                ll_apply.setVisibility(View.VISIBLE);
                BigDecimal amount7 = new BigDecimal(priceInfoList.getAmount());
                amount77 = amount77.add(amount7);
                BigDecimal amount777 = amount77.setScale(2, RoundingMode.HALF_UP);
                tv_apply_money.setText(amount777.doubleValue()+"");
                HuoPriceModel huoPriceModel6 = new HuoPriceModel();
                huoPriceModel6.setImgUrl(priceInfoList.getImgUrl());
                huoPriceModel6.setAmount(priceInfoList.getAmount());
                huoPriceModel6.setBillTypeName(billTypeName);
                list7.add(huoPriceModel6);

                rv_apply.setLayoutManager(new LinearLayoutManager(mContext));
                huoPayedAdapter = new HuoPayedAdapter(R.layout.item_huo_pay,list7);
                rv_apply.setAdapter(huoPayedAdapter);
                huoPayedAdapter.notifyDataSetChanged();
                break;

        }
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
                            getHuoDetail(id);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }



    boolean isPayed = true;
    boolean isPaying = true;
    boolean isBacking = true;
    boolean isFailed = true;
    boolean isSuccess = true;
    boolean isApply = true;
    boolean isUnPay = true;

    AddTipDialog addTipDialog;
    boolean isOpen;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl_open:
                if(isOpen) {
                    isOpen = false;
                    tv_open.setText("展开全部订单");
                    iv_open.setImageResource(R.mipmap.icon_arrow_light_down);
                    rv_huo.setLimit(true);
                }else {
                    tv_open.setText("收起全部订单");
                    isOpen = true;
                    iv_open.setImageResource(R.mipmap.icon_arrow_light_up);
                    rv_huo.setLimit(false);
                }
                unAbleAdapter.notifyDataChanged();
                break;

            case R.id.tv_pay:
                getDriver();

                break;

            case R.id.iv_wen:
                if(data.getAppealEnabledStatus()==1) {
                    //协商
                    XieShangDialog xieShangDialog = new XieShangDialog(mContext,1);
                    xieShangDialog.show();
                }else {
                    XieShangDialog xieShangDialog = new XieShangDialog(mContext,2);
                    xieShangDialog.show();
                }
                break;

            case R.id.tv_desc:
                Intent intent1 = new Intent(mContext,AccountActivity.class);
                intent1.putExtra("billList", (Serializable) data.getBillAppeal());
                intent1.putExtra("orderDisPlayId", data.getOrderDisplayId());
                startActivity(intent1);

                break;

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
                if(!isPayed) {
                    rv_payed.setVisibility(View.VISIBLE);
                    isPayed = true;
                }else {
                    isPayed = false;
                    rv_payed.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_paying:
                if(!isPaying) {
                    rv_paying.setVisibility(View.VISIBLE);
                    isPaying = true;
                }else {
                    isPaying = false;
                    rv_paying.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_unPay:
                if(!isUnPay) {
                    rv_unPay.setVisibility(View.VISIBLE);
                    isUnPay = true;
                }else {
                    isUnPay = false;
                    rv_unPay.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_backing:
                if(!isBacking) {
                    rv_backing.setVisibility(View.VISIBLE);
                    isBacking = true;
                }else {
                    isBacking = false;
                    rv_backing.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_paySuccess:
                if(!isSuccess) {
                    rv_success.setVisibility(View.VISIBLE);
                    isSuccess = true;
                }else {
                    isSuccess = false;
                    rv_success.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_apply:
                if(!isApply) {
                    rv_apply.setVisibility(View.VISIBLE);
                    isApply = true;
                }else {
                    isApply = false;
                    rv_apply.setVisibility(View.GONE);
                }
                break;

            case R.id.tv_payFailed:
                if(!isFailed) {
                    rv_payFailed.setVisibility(View.VISIBLE);
                    isFailed = true;
                }else {
                    isFailed = false;
                    rv_payFailed.setVisibility(View.GONE);
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
                if(data!=null) {
                    Intent intent2 = new Intent(mContext,HuoDriverActivity.class);
                    intent2.putExtra("orderDisplayId",data.getOrderDisplayId());
                    intent2.putExtra("sendAddress",data.getSendAddr());
                    intent2.putExtra("receiveAddress",data.getReceiveAddr());
                    intent2.putExtra("latLon",data.getDriverInfo().getLatLon());
                    startActivity(intent2);
                }

                break;

            case R.id.tv_cancel:
                if(data!=null) {
                    if(data.getNeedToCancelReason()==1) {
                        Intent intent3 = new Intent(mContext,HuoCancelActivity.class);
                        intent3.putExtra("displayId",data.getOrderDisplayId());
                        intent3.putExtra("type",1);
                        startActivity(intent3);
                    }else {
                        Intent intents = new Intent(mContext,HuoCancelActivity.class);
                        intents.putExtra("displayId",data.getOrderDisplayId());
                        intents.putExtra("type",2);
                        startActivity(intents);
                    }
                }

                break;
        }
    }

    private void getDriver() {
        HuolalaAPI.getDriverPay(mActivity,data.getOrderDisplayId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HuoDriverPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HuoDriverPayModel huoDriverPayModel) {
                        if(huoDriverPayModel.getCode()==1) {
                            if(huoDriverPayModel.getData()!=null) {
                                String cashier_url = huoDriverPayModel.getData().getCashier_url();
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(cashier_url));
                                if (intent.resolveActivity(mActivity.getPackageManager()) != null) {
                                    intent.resolveActivity(mActivity.getPackageManager());
                                    mActivity.startActivity(Intent.createChooser(intent, "请选择浏览器"));
                                }
//                                startActivity(CommonH7Activity.getIntent(mContext, CommonH7Activity.class, cashier_url));
                                finish();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,huoDriverPayModel.getMessage());
                        }
                    }
                });
    }
}
