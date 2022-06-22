package com.puyue.www.qiaoge.activity.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.home.SearchResultAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.event.UpDateNumEvent7;
import com.puyue.www.qiaoge.event.UpDateNumEvent8;
import com.puyue.www.qiaoge.fragment.home.AllGoodsFragment;
import com.puyue.www.qiaoge.fragment.home.OperateFragment;
import com.puyue.www.qiaoge.fragment.home.UnOperateFragment;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/13.
 */

public class SearchReasultActivity extends BaseSwipeActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_activity_result)
    TextView tv_activity_result;
    @BindView(R.id.rl_num)
    RelativeLayout rl_num;
    @BindView(R.id.rb_all)
    RadioButton rb_all;
    @BindView(R.id.rb_operate)
    RadioButton rb_operate;
    @BindView(R.id.rb_unOperate)
    RadioButton rb_unOperate;
    @BindView(R.id.fl_container)
    FrameLayout fl_container;
    @BindView(R.id.rg_group)
    RadioGroup rg_group;
    @BindView(R.id.ll_recommend)
    LinearLayout ll_recommend;
    @BindView(R.id.ll_all)
    LinearLayout ll_all;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.ll_style)
    LinearLayout ll_style;
    String searchWord;
    int pageNum = 1;
    int pageSize = 10;
    public View view;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView lav_activity_loading;
    private SearchResultAdapter searchResultAdapter;
    SearchResultsModel searchResultsModel;
    //搜索集合
    private List<SearchResultsModel.DataBean.SearchProdBean.ListBean> searchList = new ArrayList<>();
    private String cell; // 客服电话
    private List<Fragment> mBaseFragment;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_seach_reasult);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        rl_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }

            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        tv_activity_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,SearchStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopWindow();
            }
        });
    }

    private void showPopWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popup, null, false);
        TextView tv_all_goods = (TextView) view.findViewById(R.id.tv_all_goods);
        TextView tv_operate = (TextView) view.findViewById(R.id.tv_operate);
        TextView tv_unOperate = (TextView) view.findViewById(R.id.tv_unOperate);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(ll_style, 1300, 0);
    }

    private void initFragment(String searchWord) {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(AllGoodsFragment.getInstance(searchWord));
        mBaseFragment.add(OperateFragment.getInstance(searchWord));
        mBaseFragment.add(UnOperateFragment.getInstance(searchWord));

    }


    private void setListener() {
        rg_group.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //设置默认选中框架页面
        rg_group.check(R.id.rb_all);
    }

    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
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
                UnicornManager.inToUnicorn(mContext);
                mDialog.dismiss();
            }
        });
    }



    @Override
    public void setViewData() {
        view = View.inflate(mContext, R.layout.item_head, null);
        searchWord = getIntent().getStringExtra(AppConstant.SEARCHWORD);
        tv_activity_result.setText(searchWord);
        getCartNum();
        getCustomerPhone();
        lav_activity_loading.setVisibility(View.VISIBLE);
        lav_activity_loading.show();
        getRecommendList(1,pageSize);
        initFragment(searchWord);
        setListener();
    }

    private void getCustomerPhone() {
        PublicRequestHelper.getCustomerPhone(mActivity, new OnHttpCallBack<GetCustomerPhoneModel>() {
            @Override
            public void onSuccessful(GetCustomerPhoneModel getCustomerPhoneModel) {
                if (getCustomerPhoneModel.isSuccess()) {
                    cell = getCustomerPhoneModel.getData();
                } else {
                    AppHelper.showMsg(mActivity, getCustomerPhoneModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(UpDateNumEvent8 upDateNumEvent) {
        getCartNum();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotals(UpDateNumEvent7 upDateNumEvent) {
        getCartNum();
    }


    /**
     * 获取角标数据
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {
                        if (getCartNumModel.isSuccess()) {
                            if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                                tv_num.setVisibility(View.VISIBLE);
                                tv_num.setText(getCartNumModel.getData().getNum());
                            } else {
                                tv_num.setVisibility(View.GONE);
                            }
                        } else {
                            AppHelper.showMsg(mActivity, getCartNumModel.getMessage());
                        }
                    }
                });
    }
    /**
     * 获取推荐列表
     */
    private void getRecommendList(int pageNum,int pageSize) {
        RecommendApI.requestData(mContext,searchWord,pageNum,pageSize,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SearchResultsModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SearchResultsModel recommendModel) {
                        if (recommendModel.isSuccess()) {
                            searchResultsModel = recommendModel;

                            if(recommendModel.getData().getSearchProd()!=null) {
                                if(recommendModel.getData().getSearchProd().getList().size()>0) {
                                    ll_all.setVisibility(View.GONE);
                                    lav_activity_loading.setVisibility(View.GONE);
                                }else {
                                    ll_all.setVisibility(View.GONE);
                                    lav_activity_loading.setVisibility(View.VISIBLE);
                                }
                            }

                            if(recommendModel.getData().getRecommendProd().size()!=0) {
                                searchResultAdapter = new SearchResultAdapter(R.layout.item_noresult_recommend, recommendModel.getData().getRecommendProd(), new SearchResultAdapter.Onclick() {
                                    @Override
                                    public void addDialog() {
                                        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(SearchReasultActivity.this))) {

                                        }else {
                                            initDialog();
                                        }
                                    }

                                    @Override
                                    public void getPrice() {
//                                        showPhoneDialog(cell);
                                        AppHelper.ShowAuthDialog(mActivity,cell);
                                    }
                                });
                                lav_activity_loading.setVisibility(View.GONE);
                                ll_recommend.setVisibility(View.VISIBLE);
                                searchResultAdapter.addHeaderView(view);
                                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                                recyclerView.setAdapter(searchResultAdapter);
                            }else {
                                ll_recommend.setVisibility(View.GONE);
                                lav_activity_loading.setVisibility(View.GONE);
                            }

                        } else {
                            AppHelper.showMsg(mContext, recommendModel.getMessage());
                            lav_activity_loading.setVisibility(View.GONE);
                        }
                    }
                });
    }
    CouponDialog couponDialog;
    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
                LoginUtil.initRegister(getContext());
                dismiss();
            }
        };
        couponDialog.show();
    }

    @Override
    public void setClickEvent() {

    }
    private int position;
    private class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_all:
                    position = 0;
                    rb_all.setTextColor(Color.parseColor("#FF5C00"));
                    rb_operate.setTextColor(Color.parseColor("#666666"));
                    rb_unOperate.setTextColor(Color.parseColor("#666666"));
                    break;
                case R.id.rb_operate:
                    position = 1;
                    rb_operate.setTextColor(Color.parseColor("#FF5C00"));
                    rb_all.setTextColor(Color.parseColor("#666666"));
                    rb_unOperate.setTextColor(Color.parseColor("#666666"));
                    break;
                case R.id.rb_unOperate:
                    position = 2;
                    rb_unOperate.setTextColor(Color.parseColor("#FF5C00"));
                    rb_operate.setTextColor(Color.parseColor("#666666"));
                    rb_all.setTextColor(Color.parseColor("#666666"));
                    break;
                default: //默认第一个(框架)
                    position = 0;
                    break;
            }

            //根据位置得到对应的Fragment
            Fragment to = getFragment();
            //替换到Fragment
            switchFrament(mContent,to);

        }
    }

    private Fragment getFragment() {
        Fragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private Fragment mContent;
    private void switchFrament(Fragment from,Fragment to) {
        if(from != to){ //才切换
            mContent = to;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction(); //开启事务
            //判断to有没有被添加
            if(!to.isAdded()){//to没有被添加
                //1.from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //2.添加to
                if(to != null){
                    ft.add(R.id.fl_container,to).commit();
                }
            }else{ //to已经被添加
                //1.from隐藏
                if(from != null){
                    ft.hide(from);
                }
                //2.显示to
                if(to != null){
                    ft.show(to).commit();
                }
            }
        }
    }

}
