package com.puyue.www.qiaoge.activity.mine.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.home.RegisterShopAdapterTwo;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.GetCustomerPhoneAPI;
import com.puyue.www.qiaoge.api.home.GetRegisterShopAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.mine.login.RegisterAPI;
import com.puyue.www.qiaoge.api.mine.login.RegisterAgreementAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.PricacyDialog;
import com.puyue.www.qiaoge.dialog.ShopDialog;
import com.puyue.www.qiaoge.dialog.ShopStyleDialog;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.GoToMineEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.GetCompanyModel;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.GetRegisterShopModel;
import com.puyue.www.qiaoge.model.mine.login.RegisterAgreementModel;
import com.puyue.www.qiaoge.model.mine.login.RegisterModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

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
 * Created by ${王涛} on 2019/11/22
 * 设置登录密码界面
 */
public class RegisterStep1Activity extends BaseSwipeActivity implements View.OnClickListener {
    @BindView(R.id.rl_shop_style)
    RelativeLayout rl_shop_style;
    @BindView(R.id.rl_company_name)
    RelativeLayout rl_company_name;
    @BindView(R.id.tv_company_name)
    TextView tv_company_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_password_sure)
    EditText et_password_sure;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.cb_register)
    CheckBox cb_register;
    @BindView(R.id.iv_close1)
    ImageView iv_close1;
    @BindView(R.id.iv_close2)
    ImageView iv_close2;
    @BindView(R.id.tv_register_agreement)
    TextView tv_register_agreement;
    @BindView(R.id.tv_register_secret)
    TextView tv_register_secret;
    @BindView(R.id.et_author)
    EditText et_author;
    @BindView(R.id.tv_shop_style)
    TextView tv_shop_style;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ll_xieyi)
    LinearLayout ll_xieyi;
    private String phone;
    private String yzm;
    String token1;
    private boolean isStarFirst = true;
    private boolean isStarSecond = true;
    private String password;
    private String passwordSure;
    private String etAuthor;
    private RegisterModel mModelRegister;
    private String mUrlAgreement;
    private RegisterAgreementModel mModelRegisterAgreement;
    int shopTypeId;
    //默认不显示
    int isDirect = 0;
    private AlertDialog mTypedialog;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        isDirect = getIntent().getIntExtra("isDirect",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_registers);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    long start;
    @Override
    protected void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        long end = (System.currentTimeMillis()-start)/1000;
        getDatas(end);
    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,1,end)
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
    public void findViewById() {
        ButterKnife.bind(this);
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        tv_register.setOnClickListener(this);
        cb_register.setOnClickListener(this);
        iv_close1.setOnClickListener(this);
        iv_close2.setOnClickListener(this);
        tv_register_agreement.setOnClickListener(this);
        tv_register_secret.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_shop_style.setOnClickListener(this);
//        et_author.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(editable.toString().length()==6) {
//                    getCompanyName(editable.toString());
//                }else {
//                    rl_company_name.setVisibility(View.GONE);
//                    rl_shop_style.setVisibility(View.GONE);
//                }
//            }
//        });
    }


    private void checkNum(String sqm) {
        GetCustomerPhoneAPI.checkCode(mContext,sqm)
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
                        if (baseModel.success) {
                            showSelectType(sqm);
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    List<GetRegisterShopModel.DataBean> mList = new ArrayList<>();
    ShopDialog shopStyleDialog;
    private void showSelectType(String authorizationCode) {
        GetRegisterShopAPI.requestData(mActivity, authorizationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetRegisterShopModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetRegisterShopModel getRegisterShopModel) {
                        if(getRegisterShopModel.getCode()==1) {
                            if(getRegisterShopModel.getData()!=null && getRegisterShopModel.getData().size()>0) {
                                mList.clear();
                                mList.addAll(getRegisterShopModel.getData());
                                if(shopStyleDialog == null) {
                                    shopStyleDialog = new ShopDialog(mContext, mList) {
                                        @Override
                                        public void confirm(String name,int id) {
                                            shopTypeId = id;
                                            tv_shop_style.setText(name);
                                            tv_shop_style.setTextColor(Color.parseColor("#FF3B00"));
                                            iv_arrow.setImageResource(R.mipmap.icon_shop_arrow);
                                            tv_shop_style.setBackgroundResource(R.drawable.shape_red7);
                                            dismiss();
                                        }
                                    };
                                }
                                shopStyleDialog.show();
                            }
                        }
                    }
                });
    }

    @Override
    public void setViewData() {
        if(getIntent().getStringExtra("phone")!=null) {
            phone = getIntent().getStringExtra("phone");

        }

        if(getIntent().getStringExtra("yzm")!=null) {
            yzm = getIntent().getStringExtra("yzm");

        }

        if(getIntent().getStringExtra("token1")!=null) {
            token1 = getIntent().getStringExtra("token1");
        }

        requestRegisterAgreement();
        tv_register_secret.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_secret.getPaint().setAntiAlias(true);//抗锯齿
        tv_register_agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_agreement.getPaint().setAntiAlias(true);//抗锯齿
        getCustomerPhone();
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);

        if(isDirect == 0) {
            ll_xieyi.setVisibility(View.GONE);
        }else {
            ll_xieyi.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 注册协议接口
     */
    private void requestRegisterAgreement() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            RegisterAgreementAPI.requestRegisterAgreement(mContext)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<RegisterAgreementModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(RegisterAgreementModel registerAgreementModel) {
                            mModelRegisterAgreement = registerAgreementModel;
                            if (mModelRegisterAgreement.success) {
                                mUrlAgreement = mModelRegisterAgreement.data;
                            } else {
                                AppHelper.showMsg(mContext, mModelRegisterAgreement.message);
                            }
                        }
                    });
        }
    }


    @Override
    public void setClickEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_register:
                password = et_password.getText().toString();
                passwordSure = et_password_sure.getText().toString();
                etAuthor = et_author.getText().toString();
                if(tv_shop_style.getText().toString().equals("")) {
                    AppHelper.showMsg(mContext, "请选择店铺类型");
                    return;
                }
//                if(wad == 0) {
//                    if(tv_shop_style.getText().toString().equals("")) {
//                        AppHelper.showMsg(mContext, "请选择店铺类型");
//                        return;
//                    }
//                }

                hintKbTwo();
                if(isDirect == 1) {
                    if(!cb_register.isChecked()) {
                        PricacyDialog pricacyDialog = new PricacyDialog(mContext) {
                            @Override
                            public void Confirm() {
                                cb_register.setChecked(true);
                                dismiss();
                            }
                        };

                        pricacyDialog.show();
                        return;
                    }
                }


                if(password !=null && passwordSure !=null) {
                    if(password.equals(passwordSure)) {
                        if(password.length()>=6&& passwordSure.length()>=6) {
//                            if(!etAuthor.equals("")) {
//                                getCompanyNames(etAuthor);
//                            }else {
//                                AppHelper.showMsg(mContext, "授权码不能为空");
//                            }
                            if(!etAuthor.equals("")) {
                                getCompanyNames(etAuthor);
                            }else {
                                if(!tv_shop_style.getText().toString().equals("")&&!tv_shop_style.getText().toString().equals("0")) {
                                    updateCheckCode();
                                }else {
                                    AppHelper.showMsg(mContext, "店铺类型不能为空");
                                }
                            }
                        } else {
                            AppHelper.showMsg(mContext, "密码位数不足!");
                        }
                    }else {
                        AppHelper.showMsg(mContext, "两次密码不一致!");
                    }
                }else {
                    AppHelper.showMsg(mContext, "密码不能为空");
                }

                break;

            case R.id.tv_shop_style:
                String sqm = et_author.getText().toString();
                if(sqm!=null && !sqm.equals("")) {
                    checkNum(sqm);
                }else {
                    showSelectType(sqm);
                }

                //店铺类型

//                if(et_author.getText().toString().length()!=0) {
//                    String sqm = et_author.getText().toString();
//                    checkNum(sqm);
//
//                }else {
//                    AppHelper.showMsg(mContext, "授权码不能为空");
//                }

                break;

            case R.id.iv_close1:
                //显示为星号或者显示数字
                if (isStarFirst) {
                    //现在显示的星星,点击变成数字
                    isStarFirst = false;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                    iv_close1.setImageResource(R.mipmap.icon_eye_open);
                } else {
                    //现在不是星星,点击变成星星
                    isStarFirst = true;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().length());
                    iv_close1.setImageResource(R.mipmap.icon_eye_close);
                }
                break;

            case R.id.tv_register_secret:
                String url = "https://shaokao.qoger.com/apph5/html/yszc.html";
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", 1);
                intent.putExtra("name", "协议");
                startActivity(intent);
                break;

            case R.id.iv_close2:
                //显示为星号或者显示数字
                if (isStarSecond) {
                    //现在显示的星星,点击变成数字
                    isStarSecond = false;
                    et_password_sure.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().length());
                    iv_close2.setImageResource(R.mipmap.icon_eye_open);
                } else {
                    //现在不是星星,点击变成星星
                    isStarSecond = true;
                    et_password_sure.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password_sure.setSelection(et_password_sure.getText().length());
                    iv_close2.setImageResource(R.mipmap.icon_eye_close);
                }
                break;

            case R.id.tv_register_agreement:
                startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, mUrlAgreement));
                break;

        }

    }

    int wad = -1;
    private void getCompanyName(String invitationCode) {
        IndexHomeAPI.getCompanyName(mActivity,invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCompanyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCompanyModel getCompanyModel) {
                        if(getCompanyModel.getCode()==1) {
                            if(getCompanyModel.getData()!=null) {
                                tv_company_name.setText(getCompanyModel.getData().getCompanyName());
                                wad = getCompanyModel.getData().getWad();
                                if(getCompanyModel.getData().getWad()==1) {
                                    //代配
                                    rl_company_name.setVisibility(View.VISIBLE);
                                    rl_shop_style.setVisibility(View.GONE);
                                }else {
                                    //翘歌
                                    rl_company_name.setVisibility(View.GONE);
                                    rl_shop_style.setVisibility(View.VISIBLE);
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,getCompanyModel.getMessage());
                        }
                    }
                });
    }

    private void getCompanyNames(String invitationCode) {
        IndexHomeAPI.getCompanyName(mActivity,invitationCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCompanyModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCompanyModel getCompanyModel) {
                        if(getCompanyModel.getCode()==1) {
                            if(getCompanyModel.getData()!=null) {
                                if(wad==1) {
                                    //代配
                                    updateCheckCode();
                                }else {
                                    //翘歌
                                    if(!tv_shop_style.getText().toString().equals("")&&!tv_shop_style.getText().toString().equals("0")) {
                                        updateCheckCode();
                                    }else {
                                        AppHelper.showMsg(mContext, "店铺类型不能为空");
                                    }
                                }
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,getCompanyModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 获取客服电话
     */
    private void getCustomerPhone() {
        GetCustomerPhoneAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCustomerPhoneModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCustomerPhoneModel getCustomerPhoneModel) {
                        if (getCustomerPhoneModel.isSuccess()) {
                            tv_phone.setText(getCustomerPhoneModel.getData());
                        } else {
                            AppHelper.showMsg(mContext, getCustomerPhoneModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 注册
     */
    private void updateCheckCode() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            //这里请求注册成功之后直接登录成功,返回的token存储下来,就代表着用户已经登录了
            if(yzm==null) {
                if(wad==1) {
                    //代配
                    RegisterAPI.requestRegister(mContext, phone,token1,passwordSure, "000000", et_author.getText().toString(),"", String.valueOf(0))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegisterModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(RegisterModel registerModel) {

                                    if (registerModel.code==1) {
                                        if(registerModel.data!=null) {
                                            mModelRegister = registerModel;
                                            updateRegister();
                                        }
                                    } else {
                                        AppHelper.showMsg(mContext, mModelRegister.message);
                                    }
                                }
                            });
                }else {
                    //翘歌
                    RegisterAPI.requestRegister(mContext, phone,token1,passwordSure, "000000", et_author.getText().toString(),"", String.valueOf(shopTypeId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegisterModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                }

                                @Override
                                public void onNext(RegisterModel registerModel) {
                                    if (registerModel.code==1) {
                                        if(registerModel.data!=null) {
                                            mModelRegister = registerModel;
                                            updateRegister();
                                        }
                                    } else {
                                        AppHelper.showMsg(mContext, mModelRegister.message);
                                    }
                                }
                            });
                }

            }else {
                if(wad==1) {
                    //代配
                    RegisterAPI.requestRegister(mContext, phone,"",passwordSure, yzm, et_author.getText().toString(),"", String.valueOf(0))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegisterModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    //  dialog.dismiss();
                                }

                                @Override
                                public void onNext(RegisterModel registerModel) {
                                    if (registerModel.code==1) {
                                        if(registerModel.data!=null) {
                                            mModelRegister = registerModel;
                                            //这里注册完成也就直接登录成功,本地存储token
                                            updateRegister();
                                        }
                                    } else {
                                        //  dialog.dismiss();
                                        AppHelper.showMsg(mContext, registerModel.message);
                                    }
                                }
                            });
                }else {
                    //翘歌
                    RegisterAPI.requestRegister(mContext, phone,"",passwordSure, yzm, et_author.getText().toString(),"", String.valueOf(shopTypeId))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<RegisterModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {
                                    //  dialog.dismiss();
                                }

                                @Override
                                public void onNext(RegisterModel registerModel) {
                                    if (registerModel.code==1) {
                                        if(registerModel.data!=null) {
                                            mModelRegister = registerModel;
                                            //这里注册完成也就直接登录成功,本地存储token
                                            updateRegister();
                                        }
                                    } else {
                                        //  dialog.dismiss();
                                        AppHelper.showMsg(mContext, registerModel.message);
                                    }
                                }
                            });
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(ShopEvent event) {
//        tv_shop_style.setText(event.name);
//        shopTypeId = event.id;
    }


    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 注册成功
     */
    private void updateRegister() {
        AppHelper.showMsg(mContext, "注册成功");
        UserInfoHelper.saveUserId(mContext, mModelRegister.data.token);
        UserInfoHelper.saveChangeFlag(mActivity,"0");
        UserInfoHelper.saveUserCell(mContext, mModelRegister.data.userBaseInfoVO.phone);
        UserInfoHelper.saveUserType(mContext, String.valueOf(mModelRegister.data.userBaseInfoVO.type));
        SharedPreferencesUtil.saveString(mContext,"userId",mModelRegister.data.userBaseInfoVO.id);
        SharedPreferencesUtil.saveInt(mActivity,"wad",mModelRegister.data.wad);
        isShow();
        //注册成功同时登录成功,需要首页和市场页刷新数据
        UserInfoHelper.saveUserHomeRefresh(mContext, "");
        UserInfoHelper.saveUserMarketRefresh(mContext, "");
        startActivity(HomeActivity.getIntent(mContext, HomeActivity.class));
        SharedPreferencesUtil.saveString(mActivity,"index1","6");
        EventBus.getDefault().post(new GoToMineEvent());
        EventBus.getDefault().post(new AddressEvent());
        finish();
    }

    /**
     * 是否展示授权
     */
    private void isShow() {
        CityChangeAPI.isShow(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<IsShowModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsShowModel isShowModel) {
                        if(isShowModel.isSuccess()) {
                            if(isShowModel.data!=null) {
                                SharedPreferencesUtil.saveString(mContext,"priceType",isShowModel.getData().enjoyProduct);
                            }
                        }else {
                            AppHelper.showMsg(mContext,isShowModel.getMessage());
                        }
                    }
                });
    }


}
