package com.puyue.www.qiaoge.fragment.order;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.BeizhuActivity;

import com.puyue.www.qiaoge.activity.ChooseConfirmAddressActivity;
import com.puyue.www.qiaoge.activity.flow.FlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagFlowLayout;
import com.puyue.www.qiaoge.activity.flow.TagsFlowLayout;
import com.puyue.www.qiaoge.activity.home.ChooseAddressActivity;
import com.puyue.www.qiaoge.activity.mine.account.AddressListActivity;
import com.puyue.www.qiaoge.activity.mine.account.AddressListsActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.ChooseCouponsActivity;
import com.puyue.www.qiaoge.adapter.ConfirmCouponAdapter;
import com.puyue.www.qiaoge.adapter.ConfirmGivenAdapter;
import com.puyue.www.qiaoge.adapter.FullConfirmAdapter;
import com.puyue.www.qiaoge.adapter.FullGivenConfirmAdapter;
import com.puyue.www.qiaoge.adapter.TagsAdapter;
import com.puyue.www.qiaoge.adapter.UnOperate1Adapter;
import com.puyue.www.qiaoge.adapter.UnOperateAdapter;
import com.puyue.www.qiaoge.adapter.mine.ChooseCouponsAdapter;
import com.puyue.www.qiaoge.adapter.mine.ConfirmOrderNewAdapter;
import com.puyue.www.qiaoge.api.cart.CartBalanceAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.GetDeliverTimeAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.mine.GetWalletAmountAPI;
import com.puyue.www.qiaoge.api.mine.order.GenerateOrderAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.ChooseAddressDialog;
import com.puyue.www.qiaoge.dialog.DisDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.BeizhuEvent;
import com.puyue.www.qiaoge.event.ChangeDeliverEvent;
import com.puyue.www.qiaoge.event.ChooseCoupon1Event;
import com.puyue.www.qiaoge.event.ChooseCouponEvent;
import com.puyue.www.qiaoge.event.DisTribution1Event;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.fragment.mine.coupons.PaymentFragment;
import com.puyue.www.qiaoge.helper.ActivityResultHelper;
import com.puyue.www.qiaoge.helper.AlwaysMarqueeTextViewHelper;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.ModeModel;
import com.puyue.www.qiaoge.model.StatModel;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.home.GetDeliverTimeModel;
import com.puyue.www.qiaoge.model.mine.GetWalletAmountModel;
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

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfirmOrderDeliverFragment extends BaseFragment {
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
    private AVLoadingIndicatorView lav_activity_loading;
    private LinearLayout linearLayoutCoupons;// 优惠券xml
    private String orderId;
    TextView tv_beizhu;
    //自营
    private ConfirmOrderNewAdapter adapter;
    //非自营
//    private TagsAdapter unOperateAdapter;
    //自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> list = new ArrayList<>();
    //非自营
    private List<CartBalanceModel.DataBean.ProductVOListBean> listUnOperate = new ArrayList<>();
    // 获取想去的参数 和获取订单的参数
    private String normalProductBalanceVOStr;
    private String activityBalanceVOStr;
    private String equipmentBalanceVOStr;
    private String cartListStr;
    private String NewgiftDetailNo = "";
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
    //请求次数
    private int requestCount = 0;

    private String areaContent;
    private String deliverTimeStart = "";
    private String deliverTimeEnd = "";

    private String deliverTimeName = "";
    List<String> mlist = new ArrayList<>();
    List<CartBalanceModel.DataBean.SystemAdditionBean> systemList1 = new ArrayList<>();
    List<CartBalanceModel.DataBean.SystemAdditionBean> systemList2 = new ArrayList<>();
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
    RecyclerView rv_given;
    TextView tv_open;
    ImageView iv_open;
    RecyclerView rv_unAddress;
    RecyclerView rv_coupon;
    RelativeLayout rl_distribution;
    TextView tv_distribution;
    TextView tv_title;
    int disType = 0;
    RelativeLayout rl_arrow;
    ImageView iv_deliver;
    TextView tv_send_time;
    UnOperate1Adapter unOperate1Adapter;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_confirm_deliver_order;
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void findViewById(View view) {
        tv_send_time = (TextView) view.findViewById(R.id.tv_send_time);
        iv_deliver = (ImageView) view.findViewById(R.id.iv_deliver);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_open = (TextView) view.findViewById(R.id.tv_open);
        iv_open = (ImageView) view.findViewById(R.id.iv_open);
        rl_arrow = (RelativeLayout) view.findViewById(R.id.rl_arrow);
        tv_distribution = (TextView) view.findViewById(R.id.tv_distribution);
        rl_distribution = (RelativeLayout) view.findViewById(R.id.rl_distribution);
        rv_given = (RecyclerView) view.findViewById(R.id.rv_given);
        rv_coupon = (RecyclerView) view.findViewById(R.id.rv_coupon);
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
        linearLayoutCoupons = (LinearLayout) view.findViewById(R.id.linearLayoutCoupons);
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        requestCartBalance(NewgiftDetailNo, 0,disType);
        requestCartBalance1(NewgiftDetailNo, 0,disType);
    }

    boolean isOpen = false;
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
        rl_distribution.setOnClickListener(noDoubleClickListener);
        rl_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isOpen) {
                    tv_open.setText("收起");
                    iv_open.setImageResource(R.mipmap.icon_arrow_up);
                    unOperate1Adapter.setOpenList(openList);
                }else {
                    tv_open.setText("展开");
                    unOperate1Adapter.setHideList(hideList);
                    iv_open.setImageResource(R.mipmap.icon_arrow_down);
                }
                unOperate1Adapter.notifyDataSetChanged();
                isOpen = !isOpen;
            }
        });
    }

    DisDialog disDialog;
    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            switch (view.getId()) {

                case R.id.rl_distribution:
                    if(cModel!=null && cModel.getData()!=null) {
                        if(disDialog==null) {
                            disDialog = new DisDialog(mActivity,cModel.getData().getSendAmount(),1);
                        }
                        disDialog.show();
                    }
                    break;

                case R.id.ll_beizhu:
                    Intent intents = new Intent(mActivity,BeizhuActivity.class);
                    intents.putExtra("beizhu",tv_beizhu.getText().toString()+"");
                    startActivity(intents);
                    break;
                case R.id.linearLayoutAddressHead: // 地址切换
//                    ChooseAddressDialog chooseAddressDialog = new ChooseAddressDialog(getActivity(),orderId);
//                    chooseAddressDialog.show();
                    Intent intentsss = new Intent(mActivity, ChooseConfirmAddressActivity.class);
                    startActivityForResult(intentsss,1);
                    break;
                case R.id.LinearLayoutAddress: // 添加地址
                    ChooseAddressDialog chooseAddressDialog1 = new ChooseAddressDialog(getActivity(),orderId);
                    chooseAddressDialog1.show();
//                    Intent intent1 = AddressListActivity.getIntent(mActivity, AddressListsActivity.class);
//                    intent1.putExtra("mineAddress", "mineAddress");
//                    startActivityForResult(intent1, 32);

                    break;
                case R.id.buttonPay:// 去支付
                    buttonPay.setEnabled(false);
                    getDatas(1);
                    lav_activity_loading.show();
                    lav_activity_loading.setVisibility(View.VISIBLE);
                    if(tv_distribution.getText().toString().equals("买家自己呼叫货拉拉") && list.size()==0) {
                        lav_activity_loading.hide();
                        buttonPay.setEnabled(true);
                        ToastUtil.showSuccessMsg(mActivity,"无可结算的商品");
                        return;
                    }
                    if(tv_distribution.getText().toString().equals("")) {
                        AppHelper.showMsg(mActivity, "请选择配送服务");
                        buttonPay.setEnabled(true);
                        lav_activity_loading.hide();
                        if(cModel!=null && cModel.getData()!=null) {
                            if(disDialog==null) {
                                disDialog = new DisDialog(mActivity,cModel.getData().getSendAmount(),1);
                            }
                            disDialog.show();
                        }
                        return;
                    }

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
                    Intent intent2 = new Intent(getContext(), ChooseCouponsActivity.class);
                    intent2.putExtra("statModel",statModel.isSelects());
                    intent2.putExtra("activityBalanceVOStr", activityBalanceVOStr);
                    intent2.putExtra("normalProductBalanceVOStr", normalProductBalanceVOStr);
                    intent2.putExtra("giftDetailNo", NewgiftDetailNo);
                    intent2.putExtra("deliveryModel",disType);
                    startActivityForResult(intent2, ActivityResultHelper.ChOOSE_COUPONS_REQUESR_CODE);

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

    private void getDatas(long end) {
        RecommendApI.getDatas(mActivity,17,end)
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

                    }
                });
    }

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
    List<CartBalanceModel.DataBean.ProductVOListBean> openList = new ArrayList<>();
    List<CartBalanceModel.DataBean.ProductVOListBean> hideList = new ArrayList<>();
    private void requestCartBalance(String giftDetailNo, int type,int disType) {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, giftDetailNo, type, 0,disType)
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

                            CartBalanceModel.DataBean data = cartBalanceModel.getData();
                            toRechargeAmount = cModel.getData().getToRechargeAmount();
                            toRecharge = cModel.getData().isToRecharge();
                            totalAmount = Double.valueOf(cartBalanceModel.getData().getTotalAmount());
                            systemList1.clear();
                            systemList2.clear();
                            if(data.getSystemAddition()!=null&& data.getSystemAddition().size()>0) {
                                for (int i = 0; i < data.getSystemAddition().size(); i++) {
                                    if(data.getSystemAddition().get(i).getType()==0) {
                                        systemList1.add(cartBalanceModel.getData().getSystemAddition().get(i));
                                    }else {
                                        systemList2.add(cartBalanceModel.getData().getSystemAddition().get(i));
                                    }
                                }
                            }

                            if(!TextUtils.isEmpty(data.getSelfSendTimeStr())) {
                                tv_send_time.setText(data.getSelfSendTimeStr());
                            }
                            if(systemList1.size()>0) {
                                rv_given.setVisibility(View.VISIBLE);
                                rv_given.setLayoutManager(new LinearLayoutManager(mActivity));
                                ConfirmGivenAdapter confirmGivenAdapter = new ConfirmGivenAdapter(R.layout.item_given,systemList1);
                                rv_given.setAdapter(confirmGivenAdapter);
                            }else {
                                rv_given.setVisibility(View.GONE);
                            }

                            if(systemList2.size()>0) {
                                rv_coupon.setVisibility(View.VISIBLE);
                                rv_coupon.setLayoutManager(new LinearLayoutManager(mActivity));
                                ConfirmCouponAdapter confirmCouponAdapter = new ConfirmCouponAdapter(R.layout.item_full,systemList2);
                                rv_coupon.setAdapter(confirmCouponAdapter);
                            }else {
                                rv_coupon.setVisibility(View.GONE);
                            }
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

                                openList.clear();
                                hideList.clear();
                                if(listUnOperate.size()>3) {
                                    for (int i = 0; i < listUnOperate.size(); i++) {
                                        openList.add(listUnOperate.get(i));
                                    }

                                    for (int i = 0; i < 3; i++) {
                                        hideList.add(listUnOperate.get(i));
                                    }
                                    unOperate1Adapter = new UnOperate1Adapter(mActivity);
                                    recyclerView1.setAdapter(unOperate1Adapter);
                                    recyclerView1.setLayoutManager(new LinearLayoutManager(mActivity));
                                    unOperate1Adapter.setHideList(hideList);
                                }else {
                                    unOperate1Adapter = new UnOperate1Adapter(mActivity);
                                    recyclerView1.setAdapter(unOperate1Adapter);
                                    recyclerView1.setLayoutManager(new LinearLayoutManager(mActivity));
                                    unOperate1Adapter.setRealList(listUnOperate);
                                }


                                if(list.size() > 0) {
                                    ll_operate.setVisibility(View.VISIBLE);
                                }else {
                                    ll_operate.setVisibility(View.GONE);
                                }

                                if(listUnOperate.size() > 0) {
                                    if(tv_distribution.getText().toString().equals("买家自己呼叫货拉拉")) {
                                        tv_title.setVisibility(View.VISIBLE);
                                    }else {
                                        tv_title.setVisibility(View.GONE);
                                    }

                                    if(listUnOperate.size()>3) {
                                        rl_arrow.setVisibility(View.VISIBLE);
                                    }else {
                                        rl_arrow.setVisibility(View.GONE);
                                    }
                                    ll_unOperate.setVisibility(View.VISIBLE);
                                }else {
                                    ll_unOperate.setVisibility(View.GONE);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            AppHelper.showMsg(mActivity, cartBalanceModel.message);
                        }
                    }
                });
    }

    private void requestCartBalance1(String giftDetailNo, int type,int disType) {
        CartBalanceAPI.requestCartBalance(mActivity, normalProductBalanceVOStr, activityBalanceVOStr, cartListStr, giftDetailNo, type, 0,disType)
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
                            if(disDialog==null) {
                                disDialog = new DisDialog(mActivity,cModel.getData().getSendAmount(),1);
                            }
                            disDialog.show();
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
        if(NewgiftDetailNo.equals("")) {
            if(cartBalanceModel.getData().getDeductDesc().equals("无优惠券")) {
                textCoupons.setText("无优惠券");
                textCoupons.setTextColor(Color.parseColor("#999999"));
                linearLayoutCoupons.setEnabled(false);
            }else if(cartBalanceModel.getData().getDeductDesc().equals("暂无可用优惠券")) {
                textCoupons.setText("暂无可用优惠券");
                textCoupons.setTextColor(Color.parseColor("#999999"));
                linearLayoutCoupons.setEnabled(true);
            } else {
                textCoupons.setText(cartBalanceModel.getData().getDeductDesc());
                textCoupons.setTextColor(Color.parseColor("#F25E0E"));
                linearLayoutCoupons.setEnabled(true);
            }
        }else {
            textCoupons.setText(cartBalanceModel.getData().getDeductDetail().getGiftName());
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

    // 获取订单号
    private void requestOrderNum() {
        GenerateOrderAPI.requestGenerateOrder(mActivity, activityBalanceVOStr, normalProductBalanceVOStr, cartListStr, NewgiftDetailNo, tv_beizhu.getText().toString(),
                deliverTimeStart, deliverTimeEnd, deliverTimeName, 0, "", "", "",disType)
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
        listUnOperate.clear();
        requestCartBalance(NewgiftDetailNo, 0,disType);////NewgiftDetailNo
    }

    //配送方式
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDistribution(DisTributionEvent disTributionEvent) {
        tv_distribution.setText(disTributionEvent.getDesc());
        disType = disTributionEvent.getType();
        NewgiftDetailNo = "";
        iv_deliver.setImageResource(R.mipmap.iv_deliver_arrow);
        requestCartBalance(NewgiftDetailNo, 0,disType);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    /**
     * 选中某一个优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupon(ChooseCouponEvent chooseCouponEvent) {
        list.clear();
        NewgiftDetailNo = chooseCouponEvent.getGiftDetailNo();

        requestCartBalance(NewgiftDetailNo,0,disType);
        statModel.setSelects(false);
    }
    /**
     * 未选优惠券
     * @param chooseCouponEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupons(ChooseCoupon1Event chooseCouponEvent) {
        list.clear();
        requestCartBalance("",0,disType);
        NewgiftDetailNo = "";
        statModel.setSelects(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getDeliver(ChangeDeliverEvent changeDeliverEvent) {
        if(cModel!=null && cModel.getData()!=null) {
            if(disDialog==null) {
                disDialog = new DisDialog(mActivity,cModel.getData().getSendAmount(),1);
            }
            disDialog.show();
        }
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
