package com.puyue.www.qiaoge.fragment.order;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.BeizhuActivity;

import com.puyue.www.qiaoge.activity.mine.account.AddressListActivity;
import com.puyue.www.qiaoge.activity.mine.account.AddressListsActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponsActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponssActivity;
import com.puyue.www.qiaoge.activity.mine.order.MyConfireOrdersActivity;
import com.puyue.www.qiaoge.adapter.PayListAdapter;
import com.puyue.www.qiaoge.adapter.UnOperateAdapter;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ConfirmOrderNewAdapter;
import com.puyue.www.qiaoge.api.cart.CartBalanceAPI;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.home.GetDeliverTimeAPI;
import com.puyue.www.qiaoge.api.mine.GetWalletAmountAPI;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.api.mine.order.GenerateOrderAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseAddressDialog;
import com.puyue.www.qiaoge.dialog.ListViewDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BeizhuEvent;
import com.puyue.www.qiaoge.event.ChooseCoupon1Event;
import com.puyue.www.qiaoge.event.ChooseCouponEvent;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.PayListEvent;
import com.puyue.www.qiaoge.fragment.mine.coupons.PaymentFragment;
import com.puyue.www.qiaoge.helper.ActivityResultHelper;
import com.puyue.www.qiaoge.helper.AlwaysMarqueeTextViewHelper;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.PayListModel;
import com.puyue.www.qiaoge.model.StatModel;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.home.GetDeliverTimeModel;
import com.puyue.www.qiaoge.model.mine.GetWalletAmountModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.order.GenerateOrderModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderDeliverFragment extends BaseFragment {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private LinearLayout linearLayoutAddressHead;
    private TextView userName;
    private TextView userPhone;
    private TextView address;
    public LinearLayout ll_info;
    private TextView firmName; // 店名
    private LinearLayout LinearLayoutAddress;// 没地址的xml
    private TextView textViewNum; // 几件商品
    private TextView commodityAmount; // 商品总额
    private TextView distributionFee; // 配送活动
    private TextView distributionFeePrice; // 配送费
    private TextView textCoupons; //优惠劵
    private EditText messageEditText; // 买家留言
    private TextView totalPrice;  // 总价
    private TextView textViewDiscount; // 已优惠
    private TextView buttonPay; // 去支付
    private TextView commodity;
    private TextView payMoney; // 支付金额
    private LinearLayout LinearLayoutStoreName; // 店名xml
    private AVLoadingIndicatorView lav_activity_loading;
    private LinearLayout linearLayoutCoupons;// 优惠券xml
    private String orderId;
    TextView tv_beizhu;
    //自营
    private ConfirmOrderNewAdapter adapter;
    //非自营
    private UnOperateAdapter unOperateAdapter;
    //自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> list = new ArrayList<>();
    //非自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate = new ArrayList<>();
    // 获取想去的参数 和获取订单的参数
    private String normalProductBalanceVOStr;
    private String activityBalanceVOStr;
    private String equipmentBalanceVOStr;
    private String cartListStr;
    private String NewgiftDetailNo = "";//NewgiftDetailNo
    // 获取选择优惠券的参数
    private String proActAmount = "";
    private String teamAmount = "";
    private String killAmount = "";
    private String prodAmount = "";
    private String couponId = "";
    private int giftNum;
    private String payAmount = "";
    // 判断是否匹配优惠券，0否1是，默认1
    CartBalanceModel cModel;
    //  优惠卷
    private RecyclerView couponsRecyclerView;
    private ChooseCouponsAdapter couponsAdapter;
//    private List<UserChooseDeductModel.DataBean.AllBean> couponsList = new ArrayList<>();
    private TextView noData;

    private int currentDay;
    private RelativeLayout relativeLayoutVIP; //
    private LinearLayout vipSubtractionLinearLayout;
    private LinearLayout subtractionActivitiesLinearLayout;
    private TextView textViewVipTitle;
    private ImageView imVipButton;
    private TextView vipSubtraction;
    private TextView subtractionActivities;
    private TextView vipSubtractionPrice;
    private TextView subtractionActivitiesPrice;
    private String VipURl;
    private String deductDetail = "";
    //记录图片的点击次数，设置一开始为0.
//    private int j=0;
    //请求次数
    private int requestCount = 0;

    private String areaContent;
    private String deliverTimeStart = "";
    private String deliverTimeEnd = "";

    private String deliverTimeName = "";
    List<String> mlist = new ArrayList<>();
    private LinearLayout ll_collect_bills;
    private LinearLayout ll_go_market;
    private TextView tv_amount_spec;
    private TextView tv_vip_content_one;
    private TextView tv_vip_content_two;
    private TextView tv_go;
    private Double toRechargeAmount;
    private boolean toRecharge;
    private Double totalAmount;
    RelativeLayout ll_beizhu;
    private PopupWindow popWin; // 弹出窗口
    private View popView; // 保存弹出窗口布局
    private WheelView wheelView;
    private TextView textView1;//取消
    private TextView textView2;//确定
    AlwaysMarqueeTextViewHelper sc;
    StatModel statModel;
    TextView tv_full_price;
    LinearLayout ll_operate;
    LinearLayout ll_unOperate;
    TextView tv_unOperate;
    TextView tv_operate;
    ImageView iv_operate_pic;
    ImageView iv_pic;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_confirm_deliver_order;
    }

    @Override
    public void initViews(View view) {
    }

    @Override
    public void findViewById(View view) {
        //  toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        iv_operate_pic = (ImageView) view.findViewById(R.id.iv_operate_pic);
        iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
        ll_operate = (LinearLayout) view.findViewById(R.id.ll_operate);
        ll_unOperate = (LinearLayout) view.findViewById(R.id.ll_unOperate);
        tv_operate = (TextView) view.findViewById(R.id.tv_operate);
        tv_unOperate = (TextView) view.findViewById(R.id.tv_unOperate);
        tv_full_price = (TextView) view.findViewById(R.id.tv_full_price);
        ll_beizhu = (RelativeLayout) view.findViewById(R.id.ll_beizhu);
        ll_info = (LinearLayout) view.findViewById(R.id.ll_info);
        lav_activity_loading = (AVLoadingIndicatorView) view.findViewById(R.id.lav_activity_loading);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerView1);
        userName = (TextView) view.findViewById(R.id.userName);
        userPhone = (TextView) view.findViewById(R.id.userPhone);
        address = (TextView) view.findViewById(R.id.address);
        firmName = (TextView) view.findViewById(R.id.firmName);
        linearLayoutAddressHead = (LinearLayout) view.findViewById(R.id.linearLayoutAddressHead);
        LinearLayoutAddress = (LinearLayout) view.findViewById(R.id.LinearLayoutAddress);
        textViewNum = (TextView) view.findViewById(R.id.textViewNum);
        commodityAmount = (TextView) view.findViewById(R.id.commodityAmount);
        distributionFee = (TextView) view.findViewById(R.id.distributionFee);
        distributionFeePrice = (TextView) view.findViewById(R.id.distributionFeePrice);
        textCoupons = (TextView) view.findViewById(R.id.textCoupons);
        messageEditText = (EditText) view.findViewById(R.id.messageEditText);
        totalPrice = (TextView) view.findViewById(R.id.totalPrice);
        textViewDiscount = (TextView) view.findViewById(R.id.textViewDiscount);

        buttonPay = (TextView) view.findViewById(R.id.buttonPay);
        commodity = (TextView) view.findViewById(R.id.commodity);
        tv_beizhu = (TextView) view.findViewById(R.id.tv_beizhu);
        payMoney = (TextView) view.findViewById(R.id.payMoney);
        LinearLayoutStoreName = (LinearLayout) view.findViewById(R.id.LinearLayoutStoreName);
        linearLayoutCoupons = (LinearLayout) view.findViewById(R.id.linearLayoutCoupons);
        couponsRecyclerView = (RecyclerView) view.findViewById(R.id.couponsRecyclerView);

        relativeLayoutVIP = (RelativeLayout) view.findViewById(R.id.relativeLayoutVIP);
        vipSubtractionLinearLayout = (LinearLayout) view.findViewById(R.id.vipSubtractionLinearLayout);
        subtractionActivitiesLinearLayout = (LinearLayout) view.findViewById(R.id.subtractionActivitiesLinearLayout);
        textViewVipTitle = (TextView) view.findViewById(R.id.textViewVipTitle);
        imVipButton = (ImageView) view.findViewById(R.id.imVipButton);
        vipSubtraction = (TextView) view.findViewById(R.id.vipSubtraction);
        subtractionActivities = (TextView) view.findViewById(R.id.subtractionActivities);
        vipSubtractionPrice = (TextView) view.findViewById(R.id.vipSubtractionPrice);
        subtractionActivitiesPrice = (TextView) view.findViewById(R.id.subtractionActivitiesPrice);
        noData = (TextView) view.findViewById(R.id.noData);
        ll_collect_bills = (LinearLayout) view.findViewById(R.id.ll_collect_bills);
        ll_go_market = (LinearLayout) view.findViewById(R.id.ll_go_market);
        tv_amount_spec = (TextView) view.findViewById(R.id.tv_amount_spec);
        tv_vip_content_one = (TextView) view.findViewById(R.id.tv_vip_content_one);
        tv_vip_content_two = (TextView) view.findViewById(R.id.tv_tv_vip_content_two);
        tv_go = (TextView) view.findViewById(R.id.tv_go);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(),R.color.white));
        }
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        final Calendar mCalendar = Calendar.getInstance();
        long time = System.currentTimeMillis();
        mCalendar.setTimeInMillis(time);
        currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        getWalletAmount();
        normalProductBalanceVOStr = mActivity.getIntent().getStringExtra("normalProductBalanceVOStr");
        activityBalanceVOStr = mActivity.getIntent().getStringExtra("activityBalanceVOStr");
        equipmentBalanceVOStr = mActivity.getIntent().getStringExtra("equipmentBalanceVOStr");
        cartListStr = mActivity.getIntent().getStringExtra("cartListStr");
        adapter = new ConfirmOrderNewAdapter(R.layout.item_confirm_order_new, list);
        unOperateAdapter = new UnOperateAdapter(R.layout.item_confirm_order_new, listUnOperate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        //非自营
        recyclerView1.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView1.setAdapter(unOperateAdapter);
        requestCartBalance(NewgiftDetailNo, 0);//NewgiftDetailNo
    }

    @Override
    public void setClickEvent() {
        statModel = new StatModel();
        linearLayoutAddressHead.setOnClickListener(noDoubleClickListener);
        LinearLayoutAddress.setOnClickListener(noDoubleClickListener);
        buttonPay.setOnClickListener(noDoubleClickListener);
        linearLayoutCoupons.setOnClickListener(noDoubleClickListener);
        imVipButton.setOnClickListener(noDoubleClickListener);
        ll_go_market.setOnClickListener(noDoubleClickListener);
        ll_beizhu.setOnClickListener(noDoubleClickListener);
    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {
                case R.id.ll_beizhu:
                    Intent intents = new Intent(mActivity,BeizhuActivity.class);
                    intents.putExtra("beizhu",tv_beizhu.getText().toString()+"");
                    startActivity(intents);
                    break;
                case R.id.linearLayoutAddressHead: // 地址切换
                    ChooseAddressDialog chooseAddressDialog = new ChooseAddressDialog(getContext(),orderId);
                    chooseAddressDialog.show();

                    break;
                case R.id.LinearLayoutAddress: // 添加地址
                    Intent intent1 = AddressListActivity.getIntent(mActivity, AddressListsActivity.class);
                    intent1.putExtra("mineAddress", "mineAddress");
                    startActivityForResult(intent1, 32);

                    break;
                case R.id.buttonPay:// 去支付
                    buttonPay.setEnabled(false);
                    lav_activity_loading.show();
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    if (LinearLayoutAddress.getVisibility() == View.VISIBLE) { // 没有地址
                        AppHelper.showMsg(mActivity, "请填写地址");
                        lav_activity_loading.hide();
                        buttonPay.setEnabled(true);
                    } else {
                        GetDeliverTimeAPI.requestDeliverTime(mActivity, areaContent)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<GetDeliverTimeModel>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        lav_activity_loading.hide();
                                        buttonPay.setEnabled(true);
                                    }

                                    @Override
                                    public void onNext(GetDeliverTimeModel getDeliverTimeModel) {

                                        if (getDeliverTimeModel.success) {
                                            if (getDeliverTimeModel.data != null) {
                                                mlist.clear();
                                                try {
                                                    JSONArray jsonArray = new JSONArray(getDeliverTimeModel.data);
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                        mlist.add(jsonObject.getString("name") + " " + jsonObject.getString("start") + "-" + jsonObject.getString("end"));
                                                    }
                                                    popView = View.inflate( getActivity(), R.layout.cztest_popwin, null);
                                                    popWin = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true); //实例化PopupWindow

                                                    //设置背景灰掉
                                                    backgroundAlpha(0.4f);
                                                    // 设置PopupWindow的弹出和消失效果
//                                                    popWin.setAnimationStyle(R.style.popupAnimation);
                                                    //显示弹出窗口
                                                    popWin.showAtLocation(buttonPay, Gravity.BOTTOM, 0, 0);
                                                    //pop关闭时变回原来
                                                    popWin.setOnDismissListener(new poponDismissListener());
                                                    //取消
                                                    textView1 = (TextView) popView.findViewById(R.id.textView1);
                                                    textView1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            popWin.dismiss();//关闭
                                                        }
                                                    });
                                                    //确定
                                                    textView2 = (TextView) popView.findViewById(R.id.textView2);
                                                    wheelView = (WheelView) popView.findViewById(R.id.wheelView);
                                                    wheelView.setItems(mlist);
                                                    textView2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            JSONObject jsonObjects = null;
                                                            try {
                                                                jsonObjects = jsonArray.getJSONObject(wheelView.pos-1);
                                                                deliverTimeStart = jsonObjects.getString("start");
                                                                deliverTimeName = jsonObjects.getString("name");
                                                                deliverTimeEnd = jsonObjects.getString("end");
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            popWin.dismiss();//关闭
                                                            requestOrderNum();
                                                        }
                                                    });

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } else {
                                                requestOrderNum();
                                            }
                                            buttonPay.setEnabled(true);
                                            lav_activity_loading.hide();
                                        } else {
                                            AppHelper.showMsg(mActivity, getDeliverTimeModel.message);
                                            lav_activity_loading.hide();
                                            buttonPay.setEnabled(true);
                                        }


                                    }
                                });
                    }
                    break;
                case R.id.linearLayoutCoupons: // 优惠券
                    if(giftNum>0) {
                        Intent intent2 = new Intent(getContext(), ChooseCouponsActivity.class);
                        intent2.putExtra("statModel",statModel.isSelects());
                        intent2.putExtra("activityBalanceVOStr", activityBalanceVOStr);
                        intent2.putExtra("normalProductBalanceVOStr", normalProductBalanceVOStr);
                        intent2.putExtra("giftDetailNo", NewgiftDetailNo);
                        startActivityForResult(intent2, ActivityResultHelper.ChOOSE_COUPONS_REQUESR_CODE);
                    }else {
                    }


                    break;
                case R.id.ll_go_market:

                    Intent intent = new Intent(mActivity, NewWebViewActivity.class);
                    intent.putExtra("URL", VipURl);
                    intent.putExtra("name", "");

                    startActivity(intent);

                    break;
            }
        }
    };

    /**
     * 获取账户余额
     */
    private void getWalletAmount() {
        GetWalletAmountAPI.requestData(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetWalletAmountModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetWalletAmountModel getWalletAmountModel) {
                        if (getWalletAmountModel.success) {
                            UserInfoHelper.saveUserWallet(mActivity, getWalletAmountModel.data);
                        } else {
                            AppHelper.showMsg(mActivity, getWalletAmountModel.message);
                        }

                    }
                });
    }

    /**
     * 结算接口
     */
    private void requestCartBalance(String giftDetailNo, int type) {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, giftDetailNo, type, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartBalanceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CartBalanceModel cartBalanceModel) {
                        if (cartBalanceModel.success) {
                            cModel = cartBalanceModel;
                            toRechargeAmount = cModel.getData().getToRechargeAmount();
                            toRecharge = cModel.getData().isToRecharge();
                            totalAmount = Double.valueOf(cartBalanceModel.getData().getTotalAmount());
                            if (cartBalanceModel != null) {
                                setText(cartBalanceModel);
                                if (requestCount == 0) {
                                    requestCount++;
                                }

                                list.clear();
                                listUnOperate.clear();
                                if (cartBalanceModel.getData().getProductVOList().size() > 0) {
                                    for (int i = 0; i < cartBalanceModel.getData().getProductVOList().size(); i++) {
                                        if(cartBalanceModel.getData().getProductVOList().get(i).getSelfOrNot()==0) {
                                            list.add(cartBalanceModel.getData().getProductVOList().get(i));
                                            tv_operate.setText(cartBalanceModel.getData().getSelfSendTimeStr());
                                        }else {
                                            listUnOperate.add(cartBalanceModel.getData().getProductVOList().get(i));
                                            tv_unOperate.setText(cartBalanceModel.getData().getUnSelfSendTimeStr());

                                        }
                                    }
                                }

                                if(list.size() > 0) {
                                    ll_operate.setVisibility(View.VISIBLE);
                                }else {
                                    ll_operate.setVisibility(View.GONE);
                                }

                                if(listUnOperate.size() > 0) {
                                    ll_unOperate.setVisibility(View.VISIBLE);
                                }else {
                                    ll_unOperate.setVisibility(View.GONE);
                                }
                            }
                            adapter.notifyDataSetChanged();
                            unOperateAdapter.notifyDataSetChanged();
                        } else {
                            AppHelper.showMsg(mActivity, cartBalanceModel.message);
                        }
                    }
                });
    }

    /**
     * 接收来自接口的数据进行数据设置。
     *
     * @param cartBalanceModel
     */
    CartBalanceModel.DataBean info;
    private void setText(CartBalanceModel cartBalanceModel) {
        info = cartBalanceModel.getData();
        proActAmount = info.getProActAmount();
        teamAmount = info.getTeamAmount();
        killAmount = info.getKillAmount();
        prodAmount = info.getNormalAmount();
        giftNum = info.getGiftNum();

        areaContent = info.getAddressVO().getAreaCode();

        if (info.getDeductDetail() != null) {
            deductDetail = info.getDeductDetail().getGiftDetailNo();
        } else {
            deductDetail = "";
        }

        textViewNum.setText("共" + info.getTotalNum() + "" + "件商品");
        distributionFeePrice.setText("¥" + info.getDeliveryFee());
        payMoney.setText("¥" + info.getTotalAmount());
        commodity.setText("共" + info.getTotalNum() + "件商品");
        totalPrice.setText("¥" + info.getTotalAmount());
        payAmount = info.getTotalAmount();
        commodityAmount.setText("¥" + info.getProdAmount() + "");

        distributionFee.setText("满" + info.getSendAmount() + "元免配送费");
        for (int i = 0; i < info.getProductVOList().size(); i++) {
            if(info.getProductVOList().get(i).getSelfOrNot()==0) {
                Glide.with(mActivity).load(info.getProductVOList().get(i).getSendTimeTpl()).into(iv_operate_pic);
            }else {
                Glide.with(mActivity).load(info.getProductVOList().get(i).getSendTimeTpl()).into(iv_pic);
            }
        }

        if (info.getAddressVO().getContactPhone() != null && info.getAddressVO().getUserName() != null &&
                info.getAddressVO().getProvinceName() != null && info.getAddressVO().getCityName() != null &&
                info.getAddressVO().getAreaName() != null && info.getAddressVO().getDetailAddress() != null) {
            LinearLayoutAddress.setVisibility(View.GONE);
            linearLayoutAddressHead.setVisibility(View.VISIBLE);
            userName.setText(info.getAddressVO().getUserName());
            userPhone.setText(info.getAddressVO().getContactPhone());
            if(info.isAddressOk()) {
                address.setText(info.getAddressVO().getProvinceName() + info.getAddressVO().getCityName()
                        + info.getAddressVO().getAreaName() + info.getAddressVO().getDetailAddress());
                ll_info.setVisibility(View.VISIBLE);
            }else {
                address.setText("请选择收货地址");
                ll_info.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(info.getAddressVO().getShopName())) {
                firmName.setText(info.getAddressVO().getShopName());
                // LinearLayoutStoreName.setVisibility(View.VISIBLE);
            } else {
                // LinearLayoutStoreName.setVisibility(View.GONE);
            }
        } else {
            linearLayoutAddressHead.setVisibility(View.GONE);
            LinearLayoutAddress.setVisibility(View.VISIBLE);
        }
        if (cartBalanceModel.getData().getOfferAmount() != null) {
            textViewDiscount.setText("已优惠¥" + cartBalanceModel.getData().getOfferAmount());
            textViewDiscount.setVisibility(View.VISIBLE);
        } else {
            textViewDiscount.setVisibility(View.GONE);

        }
        if (cartBalanceModel.getData().getDeductDetail() != null) {
            if (!TextUtils.isEmpty(cartBalanceModel.getData().getDeductDetail().getAmountStr())) {
                couponId = cartBalanceModel.getData().getDeductDetail().getGiftDetailNo();
                NewgiftDetailNo = cartBalanceModel.getData().getDeductDetail().getGiftDetailNo();////NewgiftDetailNo

            }
        }
        if(giftNum>0) {
            textCoupons.setText(cartBalanceModel.getData().getDeductDesc());
            textCoupons.setTextColor(Color.parseColor("#F25E0E"));
            linearLayoutCoupons.setEnabled(true);
        }else {
            textCoupons.setText("暂无优惠券可使用");
            textCoupons.setTextColor(Color.parseColor("#999999"));
            linearLayoutCoupons.setEnabled(false);

        }


        VipURl = cartBalanceModel.getData().getVipCenterUrl();
        vipSubtractionPrice.setText("¥" + cartBalanceModel.getData().getVipReduct());

        if (!TextUtils.isEmpty(cartBalanceModel.getData().getVipReductDesc())) {

            vipSubtraction.setText(cartBalanceModel.getData().getVipReductDesc());
            vipSubtraction.setVisibility(View.VISIBLE);
        } else {
            vipSubtraction.setVisibility(View.GONE);
        }
        if (cartBalanceModel.getData().isVip()) { // 是否开通vip
            ll_collect_bills.setVisibility(View.GONE);

            vipSubtractionLinearLayout.setVisibility(View.VISIBLE);
            relativeLayoutVIP.setVisibility(View.GONE);
            //  vipSubtractionLinearLayout.setVisibility(View.GONE);
            textViewVipTitle.setText(cartBalanceModel.getData().getNotVipDesc());
        } else {
            vipSubtractionLinearLayout.setVisibility(View.GONE);
            //ll_collect_bills.setVisibility(View.VISIBLE);
            if (!cartBalanceModel.getData().isOpenVip()) {
                if (cartBalanceModel.getData().getVipDesc() > 0) {
                    ll_collect_bills.setVisibility(View.VISIBLE);
                    tv_vip_content_one.setText("续费会员本单立减");
                    tv_vip_content_two.setText("，随后享受单单满减优惠");
                    tv_go.setText("去续费");

                    tv_amount_spec.setText(cartBalanceModel.getData().getVipDesc().toString() + "" + "元");
                } else {
                    ll_collect_bills.setVisibility(View.GONE);
                }


            } else {


                ll_collect_bills.setVisibility(View.VISIBLE);
                tv_amount_spec.setText(cartBalanceModel.getData().getVipDesc().toString() + "" + "元");
                tv_vip_content_one.setText("开通会员本单立减");
                tv_vip_content_two.setText("，随后享受单单满减优惠");
                tv_go.setText("去开通");
            }
            relativeLayoutVIP.setVisibility(View.GONE);

        }
        subtractionActivitiesPrice.setText(cartBalanceModel.getData().getTotalNum()+"");
        tv_full_price.setText("¥" + cartBalanceModel.getData().getNormalReduct());
        if (cartBalanceModel.getData().isOfferIsOpen()) { // 活动满减
            subtractionActivitiesLinearLayout.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(cartBalanceModel.getData().getNormalReductDesc())) {
                subtractionActivities.setVisibility(View.VISIBLE);
                subtractionActivities.setText(cartBalanceModel.getData().getNormalReductDesc());
            } else {
                subtractionActivities.setVisibility(View.GONE);
            }

        } else {
            subtractionActivitiesLinearLayout.setVisibility(View.GONE);
            subtractionActivities.setVisibility(View.GONE);
        }
    }


//           address.setText(info.getAddressVO().getProvinceName() + info.getAddressVO().getCityName()
//                    + info.getAddressVO().getAreaName() + info.getAddressVO().getDetailAddress());
    // 获取订单号
    private void requestOrderNum() {

        GenerateOrderAPI.requestGenerateOrder(mActivity, activityBalanceVOStr, normalProductBalanceVOStr, cartListStr, NewgiftDetailNo, tv_beizhu.getText().toString(),
                deliverTimeStart, deliverTimeEnd, deliverTimeName, 0, "", "", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GenerateOrderModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        lav_activity_loading.hide();
                    }

                    @Override
                    public void onNext(GenerateOrderModel generateOrderModel) {

                        if (generateOrderModel.success) {
                            if(generateOrderModel.getData()!=null) {
                                orderId = generateOrderModel.getData();
                                PaymentFragment paymentFragment = new PaymentFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("remark", tv_beizhu.getText().toString());
                                bundle.putString("total", info.getTotalAmount());
                                bundle.putString("payAmount",payAmount);
                                bundle.putString("orderId",orderId);
                                bundle.putString("orderDeliveryType","0");
                                paymentFragment.setArguments(bundle);
                                paymentFragment.show(getFragmentManager(),"paymentFragment");
                                paymentFragment.setCancelable(false);
//                                getDialog().setCanceledOnTouchOutside(false);

                            }
                            lav_activity_loading.hide();
                            lav_activity_loading.setVisibility(View.GONE);
                            buttonPay.setEnabled(true);
                        } else {
                            AppHelper.showMsg(mActivity, generateOrderModel.message);
                            lav_activity_loading.setVisibility(View.GONE);
                            lav_activity_loading.hide();
                            buttonPay.setEnabled(true);
                        }
                    }
                });
    }


    @Subscribe
    public void onEventMainThread(AddressEvent event) {
        list.clear();
        requestCartBalance(NewgiftDetailNo, 0);////NewgiftDetailNo
//        userChooseDeduct();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取备注内容
     * @param beizhuEvent
     */
    BeizhuEvent beizhuEvent;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getBeizhu(BeizhuEvent beizhuEvent) {
        this.beizhuEvent = beizhuEvent;
        tv_beizhu.setText(beizhuEvent.getBeizhu());
    }

    /**
     * 选中某一个优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupon(ChooseCouponEvent chooseCouponEvent) {

        list.clear();
        requestCartBalance(chooseCouponEvent.getGiftDetailNo(), 0);
        statModel.setSelects(false);
    }
//      subtractionActivitiesPrice.setText("¥" + cartBalanceModel.getData().getNormalReduct());
    /**
     * 未选优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupons(ChooseCoupon1Event chooseCouponEvent) {
        list.clear();
        requestCartBalance("",0);
        NewgiftDetailNo = "";
        statModel.setSelects(true);
    }

    @Override
    public void onResume() {
        super.onResume();


    }
    //设置添加屏幕的背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    //设置弹框关闭时背景颜色改回来
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

}
