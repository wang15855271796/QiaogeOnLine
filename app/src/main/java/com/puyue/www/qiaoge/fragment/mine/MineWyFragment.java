package com.puyue.www.qiaoge.fragment.mine;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.mine.FeedBackActivity;
import com.puyue.www.qiaoge.activity.mine.MessageCenterActivity;
import com.puyue.www.qiaoge.activity.mine.MyCollectionActivity;
import com.puyue.www.qiaoge.activity.mine.SubAccountActivity;
import com.puyue.www.qiaoge.activity.mine.VipActivity;
import com.puyue.www.qiaoge.activity.mine.account.AccountCenterActivity;
import com.puyue.www.qiaoge.activity.mine.account.AddressListActivity;
import com.puyue.www.qiaoge.activity.mine.coupons.MyCouponsActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.activity.mine.order.MyOrdersActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MinerIntegralActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletDetailActivity;
import com.puyue.www.qiaoge.activity.mine.wallet.MyWalletNewActivity;
import com.puyue.www.qiaoge.adapter.Must2Adapter;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.mine.AccountCenterAPI;
import com.puyue.www.qiaoge.api.mine.UpdateAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderListAPI;
import com.puyue.www.qiaoge.api.mine.order.MyOrderNumAPI;
import com.puyue.www.qiaoge.api.mine.order.VipPayAPI;
import com.puyue.www.qiaoge.api.mine.subaccount.MineAccountAPI;
import com.puyue.www.qiaoge.base.BaseFragment;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.CouponEvent;
import com.puyue.www.qiaoge.event.GoToMineEvent;
import com.puyue.www.qiaoge.event.MessageEvent;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.HomeStyleModel;
import com.puyue.www.qiaoge.model.ModeModel;
import com.puyue.www.qiaoge.model.OrderNumsModel;
import com.puyue.www.qiaoge.model.VipCenterModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.AccountCenterModel;
import com.puyue.www.qiaoge.model.mine.UpdateModel;
import com.puyue.www.qiaoge.model.mine.order.MineCenterModel;
import com.puyue.www.qiaoge.model.mine.order.MyOrderNumModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.SuperTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MineWyFragment extends BaseFragment {
    private static final String TAG = MineWyFragment.class.getSimpleName();
    private ImageView mIvAvatar;
    private TextView mTvPhone;
    private RelativeLayout rl_return_order;
    private RelativeLayout mRlCollection;
    private RelativeLayout mRlContact;
    private RelativeLayout mRlFeedback;
    private RelativeLayout mRlVersion;
    private TextView mTvVersion;
    NestedScrollView scrollView;
    private TextView mViewVersionPoint;
    private AccountCenterModel mModelAccountCenter;
    String onlineTime;
    private int mStateCode;
    TextView tv_phone;
    ImageView iv_back;
    NestedScrollView nestedScrollView;
    CoordinatorLayout coordinator;
    private TextView mViewCollectionNum;
    RelativeLayout rl_zizhi1;
    RecyclerView rv1;
    private AlertDialog mDialog;
    private String mCustomerPhone;
    private SuperTextView mViewMessageNum;
    private UpdateModel mModelUpdate;
    private boolean update;
    private RelativeLayout accountAddress;
    private RelativeLayout accountManagement;
    private RelativeLayout relativeLayoutVip; // 会员中心
    private TextView vipDesc; //会员中心角标
    private String urlVIP;
    private String commissionUrl;
    Must2Adapter mustAdapter;
    SmartRefreshLayout refreshLayout;
    //    private ImageView iv_vip;
    private TextView tv_amount;
    private TextView tv_point;//积分
    private TextView tv_deductNum;//优惠券数量
    private TextView tv_expiredInfo;//优惠券到期通知
    private RelativeLayout ll_expiredInfo;
    private ImageView iv_setting;//设置
    private TextView tv_use_deduct;//使用优惠券
    private ImageView iv_use_deduct;//使用优惠券
    RelativeLayout rl_zizhi;
    //    TextView tv_tip;
    private int day;
    private String giftNo;
    private LinearLayout ll_amount;//余额
    private LinearLayout ll_account;//积分
    private LinearLayout ll_deduct;//优惠券
    private TextView tv_vip;//会员更多权益
    TextView tv_number1;
    TextView tv_number2;
    private boolean isChecked;
    RecyclerView rv2;
    private ImageView iv_message;
    TextView tv_company;
    RelativeLayout rl_zizhi2;
    private List<MyOrderNumModel.DataBean> mListData = new ArrayList<>();
    private RelativeLayout ll_self_sufficiency;
    private RelativeLayout ll_deliver_order;
    TextView tv_number;
    RelativeLayout rl_un_vip;
    ImageView iv_vip_state;
    RelativeLayout rl_vip;
    TextView tv_save;
    RelativeLayout rl_order;
    RelativeLayout rl_vip1;
    ImageView iv_my_bg;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine_wy;
    }

    @Override
    public void initViews(View view) {
    }

    long start;
    @Override
    public void onStop() {
        super.onStop();
        if(SharedPreferencesUtil.getString(mActivity,"index").equals("5")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }
    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mActivity,12,end)
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void findViewById(View view) {
        EventBus.getDefault().register(this);
        iv_my_bg = (view.findViewById(R.id.iv_my_bg));
        tv_save = (view.findViewById(R.id.tv_save));
        rl_vip1 = (view.findViewById(R.id.rl_vip1));
        rl_order = (view.findViewById(R.id.rl_order));
        nestedScrollView = (view.findViewById(R.id.nestedScrollView ));
        rl_vip =  (view.findViewById(R.id.rl_vip ));
        iv_vip_state = (view.findViewById(R.id.iv_vip_state));
        rl_zizhi2 = (view.findViewById(R.id.rl_zizhi2));
        rl_un_vip =  (view.findViewById(R.id.rl_un_vip));
        tv_company =  (view.findViewById(R.id.tv_company));
        rl_zizhi1 = (view.findViewById(R.id.rl_zizhi1));
        refreshLayout = (view.findViewById(R.id.smart));
        coordinator = (view.findViewById(R.id.coordinator));
        rv2 =  (view.findViewById(R.id.rv2));
        scrollView = (view.findViewById(R.id.scrollView));
        iv_back = (view.findViewById(R.id.iv_back));
        rv1 = (view.findViewById(R.id.rv1));
        rl_zizhi = (view.findViewById(R.id.rl_zizhi));
        tv_number1 = (view.findViewById(R.id.tv_number1));
        tv_number2 = (view.findViewById(R.id.tv_number2));
        mIvAvatar = (view.findViewById(R.id.iv_mine_avatar));//头像
        mTvPhone = (view.findViewById(R.id.tv_mine_phone));
        rl_return_order = (view.findViewById(R.id.rl_return_order));
        tv_number = (view.findViewById(R.id.tv_number));
        mRlCollection = (view.findViewById(R.id.rl_mine_collection));//我的收藏
        mRlContact = (view.findViewById(R.id.rl_mine_contact));//联系客服
        mRlFeedback = (view.findViewById(R.id.rl_mine_feedback));//意见反馈
        mRlVersion = (view.findViewById(R.id.rl_mine_version));//版本信息
        mTvVersion = (view.findViewById(R.id.tv_mine_version));
        mViewVersionPoint = (view.findViewById(R.id.view_mine_version_point));
        mViewCollectionNum = (view.findViewById(R.id.textCollectionMount));//我的收藏数量
        mViewMessageNum = (view.findViewById(R.id.view_mine_message_num));//消息数量
        accountAddress = (view.findViewById(R.id.accountAddress));//我的地址
        accountManagement = (view.findViewById(R.id.accountManagement));//子账号管理
        relativeLayoutVip = (view.findViewById(R.id.relativeLayoutVip)); // 会员中心
        vipDesc = (view.findViewById(R.id.vipDesc));// 会员中心角标
//        iv_vip = (view.findViewById(R.id.iv_vip));
        tv_amount = (view.findViewById(R.id.tv_amount));
        tv_point = (view.findViewById(R.id.tv_point));
        tv_deductNum = (view.findViewById(R.id.tv_deductNum));
        tv_expiredInfo = (view.findViewById(R.id.tv_expiredInfo));
        ll_expiredInfo = (view.findViewById(R.id.ll_expiredInfo));
        iv_setting = (view.findViewById(R.id.iv_setting));
        tv_use_deduct = (view.findViewById(R.id.tv_use_deduct));
        iv_use_deduct = (view.findViewById(R.id.iv_use_deduct));
        ll_amount = (view.findViewById(R.id.ll_amount));
        ll_account = (view.findViewById(R.id.ll_account));
        ll_deduct = (view.findViewById(R.id.ll_deduct));
        tv_vip = (view.findViewById(R.id.tv_vip));
        iv_message = (view.findViewById(R.id.iv_message));
        ll_deliver_order = (view.findViewById(R.id.ll_deliver_order));
        ll_self_sufficiency = (view.findViewById(R.id.ll_self_sufficiency));
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedScrollView.smoothScrollTo(0,0);
            }
        });


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNum = 1;
                list.clear();
                getProductsList();
                refreshLayout.finishRefresh();
                refreshLayout.setNoMoreData(false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (productModels.getData()!=null) {
                    if(productModels.getData().isHasNextPage()) {
                        pageNum++;
                        getProductsList();
                        refreshLayout.finishLoadMore();

                    }else {
                        refreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
                refreshLayout.finishLoadMore();
            }
        });
        tv_company.getBackground().setAlpha(30);
    }


    @Override
    public void setViewData() {
        mViewVersionPoint.setVisibility(View.GONE);
        mTvVersion.setText(getString(R.string.textVersion) + AppHelper.getVersion(getContext()));

        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getContext()))) {
            //有userId,显示userId,
            requestOrderNum();

        } else {
            //没有就显示"请登录"
            mTvPhone.setText("请登录");
            mTvPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(LoginActivity.getIntent(getContext(), LoginActivity.class));
                }
            });
            mViewCollectionNum.setVisibility(View.GONE);
            if (mViewMessageNum != null) {
                mViewMessageNum.setVisibility(View.GONE);
            }
        }


        mustAdapter = new Must2Adapter(R.layout.item_team_list, list, new Must2Adapter.Onclick() {
            @Override
            public void addDialog() {
            }

            @Override
            public void tipClick() {
//                showPhoneDialog(cell);
                AppHelper.ShowAuthDialog(mActivity,SharedPreferencesUtil.getString(mActivity,"mobile"));
            }
        });

        rv1.setLayoutManager(new GridLayoutManager(mActivity,2));
        rv1.setAdapter(mustAdapter);
        requestUpdate();
        getProductsList();
        getVipCenter();
        getHomeStyle();
    }

    @Override
    public void setClickEvent() {
        rl_zizhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,IntelliGencyActivity.class);
                startActivity(intent);
            }
        });

        rl_zizhi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,IntelliGencyActivity.class);
                startActivity(intent);
            }
        });

        rl_order.setOnClickListener(noDoubleClickListener);
        mIvAvatar.setOnClickListener(noDoubleClickListener);
        rl_return_order.setOnClickListener(noDoubleClickListener);//售后
        mRlCollection.setOnClickListener(noDoubleClickListener);//我的收藏
        rl_vip1.setOnClickListener(noDoubleClickListener);
        mRlContact.setOnClickListener(noDoubleClickListener);//联系客服
        mRlFeedback.setOnClickListener(noDoubleClickListener);
        mRlVersion.setOnClickListener(noDoubleClickListener);//关于版本
        accountAddress.setOnClickListener(noDoubleClickListener);//我的地址
        accountManagement.setOnClickListener(noDoubleClickListener);//子账号管理
        relativeLayoutVip.setOnClickListener(noDoubleClickListener);
        iv_setting.setOnClickListener(noDoubleClickListener);
        //  vipDay.setOnClickListener(noDoubleClickListener);
        tv_use_deduct.setOnClickListener(noDoubleClickListener);
        iv_use_deduct.setOnClickListener(noDoubleClickListener);
        ll_amount.setOnClickListener(noDoubleClickListener);
        ll_account.setOnClickListener(noDoubleClickListener);
        ll_deduct.setOnClickListener(noDoubleClickListener);
        tv_vip.setOnClickListener(noDoubleClickListener);
        rl_zizhi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,IntelliGencyActivity.class);
                startActivity(intent);
            }
        });

        ll_deliver_order.setOnClickListener(noDoubleClickListener);
        ll_self_sufficiency.setOnClickListener(noDoubleClickListener);

        iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getActivity()))) {
//                    startActivity(MessageCenterActivity.getIntent(getContext(), MessageCenterActivity.class));
                    //写一个携带返回结果的跳转
                    Intent intent = new Intent(getActivity(), MessageCenterActivity.class);
                    startActivityForResult(intent, 101);
//                    this.startActivityForResult()
                } else {
                    AppHelper.showMsg(getActivity(), "请先登录");
                    startActivity(LoginActivity.getIntent(getActivity(), LoginActivity.class));
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == 102) {
                int newPosition = data.getIntExtra("NewPosition", 5);//NewPosition
//                setNewPosition(newPosition);
                Log.e(TAG, "onActivityResult: " + newPosition);
                if (newPosition > 0) {
                    mViewMessageNum.setVisibility(View.VISIBLE);
                    mViewMessageNum.setText("  " + newPosition + "  ");
                } else {
                    mViewMessageNum.setVisibility(View.GONE);
                }


            }

        }
    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == mIvAvatar) {
                //头像
            } else if (view == iv_setting) {
                //我的账户
                if (mStateCode == -10000) {
                    //异地登录了,需要清除应用内部的userId,让用户重新登录

                    startActivity(LoginActivity.getIntent(getContext(), LoginActivity.class));
                } else if (mStateCode == -10001) {
                    //用户userId过期,也是需要清除userId,让用户重新登录
                    startActivity(LoginActivity.getIntent(getContext(), LoginActivity.class));
                } else {
                    if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(getContext()))) {
                        //有userId,跳转个人中心
                        startActivity(AccountCenterActivity.getIntent(getContext(), AccountCenterActivity.class));
                    } else {
                        //没有userId
                        //这个项目登录和输入密码在一个界面,不用在这里判断用户是否登录过,在登录界面判断有没有存过userCell即可
                        //所以跳转登录界面

                        startActivity(LoginActivity.getIntent(getContext(), LoginActivity.class));
                    }
                }
            } else if (view == ll_deliver_order) {
                //到家订单

                Intent intent = MyOrdersActivity.getIntent(getContext(), MyOrdersActivity.class, AppConstant.ALL);
                intent.putExtra("orderDeliveryType",0);
                startActivity(intent);
            }else if(view == rl_order) {
                Intent intent = MyOrdersActivity.getIntent(getContext(), MyOrdersActivity.class, AppConstant.ALL);
                intent.putExtra("orderDeliveryType",0);
                startActivity(intent);
            } else if (view == ll_self_sufficiency) {
                //自提订单
                Intent intent1 = MyOrdersActivity.getIntent(getContext(), MyOrdersActivity.class, AppConstant.ALL);
                intent1.putExtra("orderDeliveryType",1);
                startActivity(intent1);
            }   else if (view == rl_return_order) {
                //我的账单
                Intent intent =new Intent(mActivity, MyWalletDetailActivity.class);

                intent.putExtra("showType",1);
                startActivity(intent);
            } else if (view == mRlCollection) {
                //我的收藏
                startActivity(MyCollectionActivity.getIntent(getContext(), MyCollectionActivity.class));
            } else if (view == mRlContact) {
                //联系客服
                if (StringHelper.notEmptyAndNull(mCustomerPhone)) {
                    showPhoneDialog(mCustomerPhone);
                }
            } else if (view == mRlFeedback) {
                //建议反馈
                startActivity(FeedBackActivity.getIntent(getContext(), FeedBackActivity.class));
            } else if (view == mRlVersion) {
                //版本
                //后面做版本更新的功能,需要重新对接
                if (update) {
                    UserInfoHelper.saveGuide(mActivity, "");
                } else {
                    //已经是最新版本
                    final AlertDialog mDialog = new AlertDialog.Builder(getContext()).create();
                    mDialog.setTitle("已经是最新版本");
                    mDialog.show();
                    mDialog.getWindow().setContentView(R.layout.enddate_dialog);
                    mDialog.setCanceledOnTouchOutside(true);
                    LinearLayout mVersionDialog = mDialog.getWindow().findViewById(R.id.linearLayout_version_dialog);
                    mVersionDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                        }
                    });
                }
            }
            else if (view == accountAddress) {
                Intent intent =new Intent(mActivity, AddressListActivity.class);
                intent.putExtra("mineAddress","mineAddress");
                startActivity(intent);

            } else if (view == accountManagement) {

                //子账户
                Intent intent = new Intent(getContext(), SubAccountActivity.class);
                startActivity(intent);

            }  else if (view == ll_account)

            {//积分
                startActivity(CommonH5Activity.getIntent(getContext(), MinerIntegralActivity.class));
            }  else if (view == relativeLayoutVip || view == tv_vip) { //会员中心
                Intent intent = new Intent(getContext(), VipActivity.class);
//                intent.putExtra("URL", urlVIP);
//                intent.putExtra("TYPE", 1);
//                intent.putExtra("name", "");
                startActivity(intent);

            }  else if (view == tv_use_deduct)

            {
                ll_expiredInfo.setVisibility(View.GONE);
                isChecked = true;
                useAccount();
            } else if (view == iv_use_deduct)

            {
                ll_expiredInfo.setVisibility(View.GONE);
                isChecked = false;
                useAccount();
            } else if (view == ll_amount)

            {
                String num = "0";
                Intent intent = new Intent(mActivity, MyWalletNewActivity.class);
                UserInfoHelper.saveUserWalletNum(getContext(), num);
                startActivity(intent);
            } else if (view == ll_deduct) {
//                startActivity(MyCouponsActivity.getIntent(getContext(), MyCouponsActivity.class));
                Intent intent = new Intent(getContext(), MyCouponsActivity.class);
                startActivity(intent);
            }else if(view == rl_vip1) {
                Intent intent = new Intent(getContext(),VipActivity.class);
                startActivity(intent);
            }
        }

    };

    /**
     * 获取首页风格
     */
    HomeStyleModel.DataBean styleData;
    private void getHomeStyle() {
        IndexHomeAPI.getStyle(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeStyleModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HomeStyleModel homeStyleModel) {
                        if(homeStyleModel.getCode()==1) {
                            if(homeStyleModel.getData()!=null) {
                                styleData = homeStyleModel.getData();
                                if(styleData.getAppMyUrl()!=null&& !styleData.getAppMyUrl().equals("")) {
                                    Glide.with(mActivity).load(styleData.getAppMyUrl()).into(iv_my_bg);
                                }
//                                normal常规，black_white黑白，may_day五一，national_day国庆，spring_festival春节

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mActivity,homeStyleModel.getMessage());
                        }
                    }
                });
    }

    private void useAccount() {
        MineAccountAPI.requestMineAccount(mActivity, day, giftNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MineCenterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MineCenterModel mineCenterModel) {

                        if (mineCenterModel.isSuccess()) {

                            if (isChecked) {
                                startActivity(MyCouponsActivity.getIntent(getContext(), MyCouponsActivity.class));
                            }


                        } else {
                            AppHelper.showMsg(mActivity, mineCenterModel.getMessage());
                        }


                    }
                });
    }


    /**
     * 待付款订单数
     * @param
     */
    private void getOrderNum() {
        MyOrderListAPI.getNum(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OrderNumsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(OrderNumsModel numsModel) {
                        if(numsModel.isSuccess()) {
                            if(numsModel.getData().getSendOrderNum()>0) {
                                tv_number1.setText(numsModel.getData().getSendOrderNum()+"");
                                tv_number1.setVisibility(View.VISIBLE);
                            }else {
                                tv_number1.setVisibility(View.GONE);
                            }

                            if(numsModel.getData().getSelfOrderNum()>0) {
                                tv_number2.setText(numsModel.getData().getSelfOrderNum()+"");
                                tv_number2.setVisibility(View.VISIBLE);
                            }else {
                                tv_number2.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden) {
            requestUserInfo();
        }
        if(hidden&&SharedPreferencesUtil.getString(mActivity,"index1").equals("1")) {
            long end = (System.currentTimeMillis()-start)/1000;
            long time = Time.getTime(end);
            getDatas(time);
        }else {
            start = System.currentTimeMillis();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderNum();
        getMode();
        requestUserInfo();
        requestOrderNum();
        start = System.currentTimeMillis();
    }

    private void requestUpdate() {
        UpdateAPI.requestUpdate(getContext(), AppHelper.getVersion(getContext()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateModel updateModel) {
                        mModelUpdate = updateModel;
                        if (mModelUpdate.success) {
                            updateUpdate();
                        } else {
                            AppHelper.showMsg(mActivity, mModelUpdate.message);
                        }
                    }
                });
    }

    private void updateUpdate() {
        update = mModelUpdate.data.update;
        if (update) {
            //因为服务器上面的是2.0.6，所以才会出现新版本和提示框的字样，只要上架之后重新上传一个2.0.7就可以了。
            //有更新
            mTvVersion.setText(getString(R.string.textVersion) + AppHelper.getVersion(getContext()));
            mViewVersionPoint.setVisibility(View.VISIBLE);
        } else {
            mTvVersion.setText(getString(R.string.textVersion) + AppHelper.getVersion(getContext()));
            //没更新
            mViewVersionPoint.setVisibility(View.GONE);
        }
    }

    private void requestOrderNum() {
        MyOrderNumAPI.requestOrderNum(getContext(),SharedPreferencesUtil.getInt(mActivity,"wad"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyOrderNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyOrderNumModel myOrderNumModel) {
                        mListData.clear();
                        if (myOrderNumModel.success) {
                            if(myOrderNumModel.getData().getCompanyName()!=null) {
                                tv_company.setText(myOrderNumModel.getData().getCompanyName());
                                tv_company.setVisibility(View.VISIBLE);
//                                iv_vip_state.setVisibility(View.GONE);
                            }else {
//                                iv_vip_state.setVisibility(View.VISIBLE);
                                tv_company.setVisibility(View.GONE);
                            }

                            mListData.add(myOrderNumModel.getData());
                            day = myOrderNumModel.getData().getDay();
                            giftNo = myOrderNumModel.getData().getGiftNo();
                            commissionUrl = myOrderNumModel.getData().getCommissionUrl();
                            mTvPhone.setVisibility(View.VISIBLE);

                            mTvPhone.setText(myOrderNumModel.getData().getPhone());
                            if (myOrderNumModel.getData().getBalance() != null) {
                                tv_amount.setText(myOrderNumModel.getData().getBalance());
                            } else {
                                tv_amount.setText("0.00");
                            }

                            tv_point.setText(String.valueOf(myOrderNumModel.getData().getPoint()));
                            tv_deductNum.setText(String.valueOf(myOrderNumModel.getData().getDeductNum()));

                            if (myOrderNumModel.getData().getCollectNum() > 0) {
                                mViewCollectionNum.setVisibility(View.VISIBLE);
                                mViewCollectionNum.setText(myOrderNumModel.getData().getCollectNum()+"");
                            } else {
                                mViewCollectionNum.setVisibility(View.GONE);
                            }

                            if (myOrderNumModel.getData().getExpiredInfo() != null && StringHelper.notEmptyAndNull(myOrderNumModel.getData().getExpiredInfo())) {
                                ll_expiredInfo.setVisibility(View.VISIBLE);
                                tv_expiredInfo.setText(myOrderNumModel.getData().getExpiredInfo());
                            } else {
                                ll_expiredInfo.setVisibility(View.GONE);
                            }

                            // 会员中心 url
                            if (!TextUtils.isEmpty(myOrderNumModel.getData().getVipCenter())) {
                                urlVIP = myOrderNumModel.getData().getVipCenter();
                                relativeLayoutVip.setEnabled(true);

                            } else {
                                relativeLayoutVip.setEnabled(false);
                            }
                            if (!TextUtils.isEmpty((myOrderNumModel.getData().getVipDesc()))) {
                                vipDesc.setText(myOrderNumModel.getData().getVipDesc());
                                vipDesc.setVisibility(View.VISIBLE);
                            } else {
                                vipDesc.setVisibility(View.GONE);
                            }

                            //消息中心
                            if (myOrderNumModel.getData().getNotice() > 0) {
                                mViewMessageNum.setVisibility(View.VISIBLE);
                                mViewMessageNum.setText(myOrderNumModel.getData().getNotice() + "");
                            } else {
                                mViewMessageNum.setVisibility(View.GONE);
                            }

                            if(myOrderNumModel.getData().isVipUser()) {
                                //会员
                                iv_vip_state.setImageResource(R.mipmap.icon_vip_orange);
                            }else {
                                iv_vip_state.setImageResource(R.mipmap.icon_vip_grey);
                            }

                            if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
                                //企业版
                                ll_amount.setVisibility(View.GONE);
                                accountManagement.setVisibility(View.GONE);

                                if(myOrderNumModel.getData().getShowVip()==0) {
                                    //非会员 不展示
                                    rl_zizhi2.setVisibility(View.GONE);
                                    rl_vip.setVisibility(View.GONE);
                                    rl_un_vip.setVisibility(View.VISIBLE);
                                    rl_vip1.setVisibility(View.GONE);
                                    relativeLayoutVip.setVisibility(View.GONE);
                                }else {
                                    if(myOrderNumModel.getData().isVipUser()) {
                                        //已开通
                                        rl_vip.setVisibility(View.VISIBLE);
                                        rl_un_vip.setVisibility(View.GONE);
                                        rl_vip1.setVisibility(View.GONE);
                                    }else {
                                        //未开通
                                        rl_vip.setVisibility(View.GONE);
                                        rl_un_vip.setVisibility(View.GONE);
                                        rl_vip1.setVisibility(View.VISIBLE);
                                    }

                                    rl_zizhi2.setVisibility(View.GONE);
                                    relativeLayoutVip.setVisibility(View.VISIBLE);
                                }

                            }else {
                                //城市版
                                ll_amount.setVisibility(View.VISIBLE);
                                rl_order.setVisibility(View.GONE);
                                accountManagement.setVisibility(View.VISIBLE);
                                if(myOrderNumModel.getData().getShowVip()==0) {
                                    //非会员 不展示
                                    rl_zizhi2.setVisibility(View.GONE);
                                    rl_vip.setVisibility(View.GONE);
                                    rl_un_vip.setVisibility(View.VISIBLE);
                                    relativeLayoutVip.setVisibility(View.GONE);
                                    rl_vip1.setVisibility(View.GONE);
                                }else {
                                    if(myOrderNumModel.getData().isVipUser()) {
                                        //已开通
                                        rl_vip.setVisibility(View.VISIBLE);
                                        rl_un_vip.setVisibility(View.GONE);
                                        rl_vip1.setVisibility(View.GONE);
                                    }else {
                                        //未开通
                                        rl_vip.setVisibility(View.GONE);
                                        rl_un_vip.setVisibility(View.GONE);
                                        rl_vip1.setVisibility(View.VISIBLE);
                                    }
                                    rl_zizhi2.setVisibility(View.INVISIBLE);
                                    relativeLayoutVip.setVisibility(View.VISIBLE);
                                }
                            }

                        } else {
                            AppHelper.showMsg(mActivity, myOrderNumModel.message);
                        }
                    }
                });
    }

    /**
     * 是否显示内容
     */
    ModeModel modeModel1;
    public void getMode() {
        IndexHomeAPI.getMode(mActivity, SharedPreferencesUtil.getInt(mActivity,"wad"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ModeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ModeModel modeModel) {
                        if(modeModel.getCode()==1) {
                            if(modeModel.getData()!=null) {
                                modeModel1 = modeModel;
                                if(modeModel1.getData().getPickBtn()==1) {
                                    //关闭
                                    ll_self_sufficiency.setVisibility(View.GONE);
                                }else {
                                    //0开启
                                    ll_self_sufficiency.setVisibility(View.VISIBLE);
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,modeModel.getMessage());
                        }
                    }
                });
    }

    private void getVipCenter() {
        VipPayAPI.getVipCenter(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipCenterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipCenterModel vipListModel) {
                        if (vipListModel.getCode()==1) {
                            if(vipListModel.getData()!=null) {
                                String saveAmountDesc = vipListModel.getData().getSaveAmountDesc();
                                if(saveAmountDesc.equals("")) {
                                    tv_save.setText("已用翘歌会员省下"+0+"元");
                                }else {
                                    tv_save.setText("已用翘歌会员省下"+saveAmountDesc+"元");
                                }
                            }
                        }
                    }
                });
    }

    private void requestUserInfo() {
        if (!NetWorkHelper.isNetworkAvailable(getContext())) {
            AppHelper.showMsg(getContext(), "网络不给力!");
        } else {
            AccountCenterAPI.requestAccountCenter(getContext())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<AccountCenterModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(AccountCenterModel accountCenterModel) {
                            mModelAccountCenter = accountCenterModel;
                            mStateCode = mModelAccountCenter.code;
                            AppHelper.UserLogout(getContext(), mStateCode, 1);
                            if (mModelAccountCenter.success) {
                                updateAccountCenter();
                                if(accountCenterModel.code==-10001) {
                                    Intent intent = new Intent(mActivity,LoginActivity.class);
                                    startActivity(intent);
                                }
                            } else {
                                mTvPhone.setText("请登录");
                                AppHelper.showMsg(getContext(), mModelAccountCenter.message);
                            }
                        }
                    });
        }
    }

    private void updateAccountCenter() {
        onlineTime = mModelAccountCenter.data.onlineTime;
        mCustomerPhone = mModelAccountCenter.data.customerPhone;
    }

    /**
     * 弹出电话号码
     */
    TextView tv_time;
    private void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(getActivity()).create();
        mDialog = new AlertDialog.Builder(getActivity(), R.style.CommonDialogStyle).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_call_phone);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
        tv_time.setText("在线客服"+ "("+onlineTime+")");
        tv_phone.setText("客服热线 ("+cell+")");
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(getContext());
                mDialog.dismiss();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void message(MessageEvent messageEvent) {
        tv_number.setVisibility(View.GONE);
        requestOrderNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginEvent(LogoutsEvent event) {
        requestOrderNum();
        requestUserInfo();
        useAccount();
        requestUpdate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageEvent(GoToMineEvent goToMineEvent) {
        requestOrderNum();
        requestUserInfo();
        useAccount();
        requestUpdate();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCoupon(CouponEvent couponEvent) {
        requestOrderNum();
        requestUserInfo();
        useAccount();
        requestUpdate();

    }

    /**
     * 必买列表(王涛)
     * @param
     */
    int pageNum = 1;
    int pageSize = 10;
    ProductNormalModel productModels;
    private List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
    private void getProductsList() {
        IndexHomeAPI.getMust2(mActivity,pageNum,pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductNormalModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductNormalModel getCommonProductModel) {
                        if (getCommonProductModel.isSuccess()) {
                            productModels = getCommonProductModel;
                            mustAdapter.notifyDataSetChanged();

                            if(getCommonProductModel.getData().getList().size()>0) {
                                list.addAll(getCommonProductModel.getData().getList());
                                mustAdapter.notifyDataSetChanged();
                            }
                            refreshLayout.setEnableLoadMore(true);
                        } else {
                            AppHelper.showMsg(mActivity, getCommonProductModel.getMessage());
                        }
                    }
                });
    }

    //新改
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changes(CityEvent cityEvent) {
        list.clear();
//        requestOrderNum();
        requestUserInfo();
//        requestUpdate();
        getProductsList();
        requestOrderNum();
        getVipCenter();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changess(AddressEvent event) {
        list.clear();
        requestOrderNum();
        getVipCenter();
        //新改
        getProductsList();
        requestUserInfo();
        useAccount();
        requestUpdate();
        requestOrderNum();
    }

}
