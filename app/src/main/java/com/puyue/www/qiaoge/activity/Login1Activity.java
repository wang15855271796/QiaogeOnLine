package com.puyue.www.qiaoge.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Editable;
import android.text.TextUtils;
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
import android.widget.Toast;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CommonH5Activity;
import com.puyue.www.qiaoge.activity.ConfigUtils;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.GetCustomerPhoneAPI;
import com.puyue.www.qiaoge.api.home.OneRegisterModel;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.api.mine.login.RegisterAgreementAPI;
import com.puyue.www.qiaoge.api.mine.login.SendCodeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.PricacyDialog;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.mine.login.LoginModel;
import com.puyue.www.qiaoge.model.mine.login.RegisterAgreementModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/3.
 */

public class Login1Activity extends BaseSwipeActivity implements View.OnClickListener {

    private EditText mEditAccount;
    private EditText mEditPassword;
    private TextView tv_login;

    private RelativeLayout mRelative;
    private boolean showPassword = true;
    private ImageView mIvBack;
    private LoginModel mModelLogin;
    private LinearLayout linPsd;



    CheckBox cb_register;
    TextView tv_register_agreement;
    TextView tv_register_secret;

    TextView tv_yzm;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login1);
    }

    @Override
    public void findViewById() {
        tv_yzm = (TextView) findViewById(R.id.tv_yzm);
        tv_login = (TextView) findViewById(R.id.tv_login);
        cb_register = (CheckBox) findViewById(R.id.cb_register);
        tv_register_agreement = (TextView) findViewById(R.id.tv_register_agreement);
        tv_register_secret = (TextView) findViewById(R.id.tv_register_secret);

        mEditAccount = (EditText) findViewById(R.id.edit_login_account);
        mEditPassword = (EditText) findViewById(R.id.edit_login_password);

        mIvBack = (ImageView) findViewById(R.id.iv_back);
        linPsd = (LinearLayout) findViewById(R.id.linPsd);
        mRelative = (RelativeLayout) findViewById(R.id.relativeLayout);
        tv_login.setEnabled(false);

    }

    @Override
    public void setViewData() {
//        mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserCell(mContext))) {
            mEditAccount.setText(UserInfoHelper.getUserCell(mContext));
        }

        requestRegisterAgreement();
    }

    @Override
    public void setClickEvent() {

        mEditAccount.addTextChangedListener(textWatcher);
        mEditPassword.addTextChangedListener(textWatcher);

        mIvBack.setOnClickListener(noDoubleClickListener);
        tv_login.setOnClickListener(noDoubleClickListener);
        tv_register_secret.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_secret.getPaint().setAntiAlias(true);//抗锯齿
        tv_register_agreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register_agreement.getPaint().setAntiAlias(true);//抗锯齿
        tv_register_agreement.setOnClickListener(this);
        tv_register_secret.setOnClickListener(this);
        tv_yzm.setOnClickListener(noDoubleClickListener);
    }

    String phone;
    String phones;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == tv_yzm) {
                phone = mEditAccount.getText().toString();
                try {
                    phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int result = checkPhoneNum(phone);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    requestSendCode(phones);
                }

            } else if (view == tv_login) {
                //走登录流程
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
                hintKbTwo();
                requestLogin();
            } else if (view == mIvBack) {
//                Intent intent = new Intent(mContext, HomeActivity.class);
//                intent.putExtra("go_home", "");
//                startActivity(intent);

                finish();
            }
        }
    };

    /**
     * 发送验证码
     */
    BaseModel mModelSendCode;
    private void requestSendCode(String phone) {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            ToastUtil.showSuccessMsg(mContext, "网络不给力!");
        } else {
            SendCodeAPI.requestSendCode(mContext,phone,1)
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
                            mModelSendCode = baseModel;
                            if (mModelSendCode.success) {
                                ToastUtil.showSuccessMsg(mContext, "发送验证码成功!");
                                handleCountDown();

                            } else {
                                ToastUtil.showSuccessMsg(mContext, mModelSendCode.message);

                            }
                        }
                    });
        }
    }

    /**
     * 验证手机号码
     * @param username
     * @return
     */
    private int checkPhoneNum(String username){
        if (TextUtils.isEmpty(username)){
            return 2;
        }else if (!username.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }

    boolean isSendingCode;
    CountDownTimer countDownTimer;
    private void handleCountDown() {
        countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isSendingCode = true;
                tv_yzm.setEnabled(false);
                tv_yzm.setText(millisUntilFinished / 1000 + "秒后" + "重新获取");
                tv_yzm.setTextColor(Color.parseColor("#A7A7A7"));
//                tv_yzm.setBackgroundResource(R.drawable.shape_grey3);

            }

            @Override
            public void onFinish() {
                isSendingCode = false;
                tv_yzm.setText("获取验证码");
                tv_yzm.setBackgroundResource(R.drawable.pink);
                tv_yzm.setEnabled(true);
                tv_yzm.setTextColor(Color.parseColor("#FF3E20"));
            }
        }.start();
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
        long time = Time.getTime(end);
        getDatas(time);

    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,2,end)
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

    private void requestLogin() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            if(!cb_register.isChecked()) {
                ToastUtil.showSuccessMsg(mContext,"请勾选协议");
                return;
            }
            LoginAPI.requestLogin(mContext, mEditAccount.getText().toString(), "", mEditPassword.getText().toString(), 1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<LoginModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(LoginModel loginModel) {
                            mModelLogin = loginModel;

                            if (mModelLogin.success) {
                                updateLogin();
                            } else {
                                AppHelper.showMsg(mContext, mModelLogin.message);
                            }
                        }
                    });
        }
    }

    private void updateLogin() {
        AppHelper.showMsg(mContext, "登录成功");
        SharedPreferencesUtil.saveInt(mActivity,"wad",mModelLogin.data.wad);
        UserInfoHelper.saveChangeFlag(mActivity,"0");
        UserInfoHelper.saveUserId(mContext, mModelLogin.data.token);
        UserInfoHelper.saveUserCell(mContext, mModelLogin.data.userBaseInfoVO.phone);
        UserInfoHelper.saveUserType(mContext, String.valueOf(mModelLogin.data.userBaseInfoVO.type));
        SharedPreferencesUtil.saveString(mActivity,"index1","7");

        SharedPreferencesUtil.saveString(mContext,"userId",mModelLogin.data.userBaseInfoVO.id);
        isShow();
        //登录成功,登录状态有变化,需要让
        EventBus.getDefault().post(new LogoutsEvent());
        EventBus.getDefault().post(new LogoutEvent());
        EventBus.getDefault().post(new CityEvent());
        UserInfoHelper.saveUserHomeRefresh(mContext, "");
        UserInfoHelper.saveUserMarketRefresh(mContext, "");
        Intent intent = new Intent(mContext,HomeActivity.class);
        startActivity(intent);
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

//    public class MyLocationListener extends BDAbstractLocationListener {
//        @Override
//        public void onReceiveLocation(BDLocation location) {
//            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
//            //以下只列举部分获取地址相关的结果信息
//            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
//
//            UserInfoHelper.saveLocation(mContext,location.getAddrStr());
//
//            String country = location.getCountry();    //获取国家
//            String province = location.getProvince();    //获取省份
//            city = location.getCity();    //获取城市
//            String district = location.getDistrict();    //获取区县
//            String street = location.getStreet();    //获取街道信息
//            String streetNumber = location.getStreetNumber();
//
//
//
//            Log.i("city..............",location.getLocType()+"");
//
//
//        }
//    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //禁止空格输入
            if (s.toString().contains(" ")) {
                String[] str = s.toString().split(" ");
                String str1 = "";
                for (int i = 0; i < str.length; i++) {
                    str1 += str[i];
                }
                mEditPassword.setText(str1);
                mEditPassword.setSelection(start);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (StringHelper.notEmptyAndNull(mEditAccount.getText().toString())
                    && StringHelper.notEmptyAndNull(mEditPassword.getText().toString())) {
                //两个都输入了
                tv_login.setEnabled(true);
            } else {
                //数据输入不完全
                tv_login.setEnabled(false);
            }

            if (s.length() > 0) {
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (c >= 0x4e00 && c <= 0X9fff) { // 根据字节码判断
                        // 如果是中文，则清除输入的字符，否则保留
                        s.delete(i, i + 1);
                    }
                }

            }
        }
    };


    //此方法只是关闭软键盘
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hintKbTwo();
    }

    @Override
    public void onPause() {
        super.onPause();
        hintKbTwo();
    }

    String mUrlAgreement;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register_secret:
                String url = "https://shaokao.qoger.com/apph5/html/yszc.html";
                Intent intent = new Intent(mContext, NewWebViewActivity.class);
                intent.putExtra("URL", url);
                intent.putExtra("TYPE", 1);
                intent.putExtra("name", "协议");
                startActivity(intent);
                break;


            case R.id.tv_register_agreement:
                startActivity(CommonH5Activity.getIntent(mContext, CommonH5Activity.class, mUrlAgreement));
                break;

//            case R.id.tv_login:
//                Intent intent1 = new Intent(mContext, Login1Activity.class);
//                startActivity(intent1);
//                break;
        }
    }

    /**
     * 注册协议接口
     */
    RegisterAgreementModel mModelRegisterAgreement;
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

}
