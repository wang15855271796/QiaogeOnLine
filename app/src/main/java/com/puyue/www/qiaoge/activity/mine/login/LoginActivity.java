package com.puyue.www.qiaoge.activity.mine.login;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuanglan.shanyan_sdk.OneKeyLoginManager;
import com.chuanglan.shanyan_sdk.listener.OneKeyLoginListener;
import com.chuanglan.shanyan_sdk.listener.OpenLoginAuthListener;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.ConfigUtils;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.GetCustomerPhoneAPI;
import com.puyue.www.qiaoge.api.home.OneRegisterModel;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.mine.login.LoginModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/3.
 */

public class LoginActivity extends BaseSwipeActivity {

    private EditText mEditAccount;
    private EditText mEditPassword;
    private TextView tv_login;
    private TextView mTvForgetPassword;
    private TextView mTvRegister;
    private RelativeLayout mRelative;
    private boolean showPassword = true;
    private ImageView mIvBack;
    private LoginModel mModelLogin;
    private LinearLayout linPsd;
    ImageView iv_eye;
    TextView tv1;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void findViewById() {
        tv1 =  (TextView) findViewById(R.id.tv1);
        iv_eye = (ImageView) findViewById(R.id.iv_eye);
        mEditAccount = (EditText) findViewById(R.id.edit_login_account);
        mEditPassword = (EditText) findViewById(R.id.edit_login_password);
        tv_login = (TextView) findViewById(R.id.tv_login);
        mTvForgetPassword = (TextView) findViewById(R.id.tv_login_forget_password);
        mTvRegister = (TextView) findViewById(R.id.tv_login_register);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        linPsd = (LinearLayout) findViewById(R.id.linPsd);
        mRelative = (RelativeLayout) findViewById(R.id.relativeLayout);
        tv_login.setEnabled(false);
        String login = SharedPreferencesUtil.getString(mContext, "login");
        if(login.equals("0")) {
            tv1.setText("欢迎回来");
        }else {
            tv1.setText("欢迎登录");
        }
    }

    @Override
    public void setViewData() {
        mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserCell(mContext))) {
            mEditAccount.setText(UserInfoHelper.getUserCell(mContext));
        }
    }

    @Override
    public void setClickEvent() {
        iv_eye.setOnClickListener(noDoubleClickListener);
        mEditAccount.addTextChangedListener(textWatcher);
        mEditPassword.addTextChangedListener(textWatcher);
        tv_login.setOnClickListener(noDoubleClickListener);
        mTvForgetPassword.setOnClickListener(noDoubleClickListener);
        mTvRegister.setOnClickListener(noDoubleClickListener);
        mIvBack.setOnClickListener(noDoubleClickListener);
    }

    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
             if (view == iv_eye) {
                if (showPassword) {// 显示密码
                    iv_eye.setImageResource(R.mipmap.icon_eye_open);
                    mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mEditPassword.setSelection(mEditPassword.getText().toString().length());
                    showPassword = !showPassword;
                } else {// 隐藏密码
                    iv_eye.setImageResource(R.mipmap.icon_eye_close);
                    mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mEditPassword.setSelection(mEditPassword.getText().toString().length());
                    showPassword = !showPassword;
                }
            } else if (view == tv_login) {
                //走登录流程
                hintKbTwo();
                requestLogin();
            } else if (view == mTvForgetPassword) {
                //忘记密码
                Intent intent = new Intent(mContext,FindPhoneActivity.class);
                startActivity(intent);
            }
            else if (view == mTvRegister) {
                //走注册流程
//                startActivity(RegisterActivity.getIntent(mContext, RegisterActivity.class));
                OneKeyLoginManager.getInstance().setAuthThemeConfig(ConfigUtils.getCJSConfig(getApplicationContext()));
                openLoginActivity();

            }
            else if (view == mIvBack) {
                Intent intent = new Intent(mContext, HomeActivity.class);
                intent.putExtra("go_home", "");
                startActivity(intent);

                finish();
            }
        }
    };
    private String token1;

    private void openLoginActivity() {
        //开始拉取授权页
        OneKeyLoginManager.getInstance().openLoginAuth(false, new OpenLoginAuthListener() {
            @Override
            public void getOpenLoginAuthStatus(int code, String result) {

                if (1000 == code) {
                    Log.e("VVV", "拉起授权页成功： code==" + code + "   result==" + result);
                } else {
                    Log.e("VVV", "拉起授权页失败： code==" + code + "   result==" + result);
                    Intent intent = new Intent(LoginActivity.this,RegisterMessageActivity.class);
                    startActivity(intent);
                }
            }
        }, new OneKeyLoginListener() {
            @Override
            public void getOneKeyLoginStatus(int code, String result) {

                if (1011 == code) {
                    Log.e("VVV", "用户点击授权页返回： code==" + code + "   result==" + result);
                    return;
                } else if (1000 == code) {
                    Log.e("VVV", "用户点击登录获取token成功： code==" + code + "   result==" + result);
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(result);
                        token1 = jsonObject.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    checks(token1);
                } else {
                    Log.e("VVV", "用户点击登录获取token失败： code==" + code + "   result==" + result);
                }
            }
        });
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

    private void checks(String result) {
        GetCustomerPhoneAPI.getData(mContext,result)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<OneRegisterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(OneRegisterModel baseModel) {
                        if (baseModel.success) {
                            Intent intent = new Intent(LoginActivity.this,RegisterStep1Activity.class);
                            intent.putExtra("phone",baseModel.data);
                            intent.putExtra("token1",result);
                            startActivity(intent);
                            OneKeyLoginManager.getInstance().finishAuthActivity();
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                            OneKeyLoginManager.getInstance().finishAuthActivity();
                        }
                    }
                });
    }


    private void applyBlur() {

        // 获取壁纸管理器
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(mContext);
        // 获取当前壁纸
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        // 将Drawable,转成Bitmap
        Bitmap bmp = ((BitmapDrawable) wallpaperDrawable).getBitmap();

        blur(bmp);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg) {
        long startMs = System.currentTimeMillis();
        float radius = 20;

        bkg = small(bkg);
        Bitmap bitmap = bkg.copy(bkg.getConfig(), true);

        final RenderScript rs = RenderScript.create(mContext);
        final Allocation input = Allocation.createFromBitmap(rs, bkg, Allocation.MipmapControl.MIPMAP_NONE,
                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bitmap);

        bitmap = big(bitmap);
        mRelative.setBackground(new BitmapDrawable(getResources(), bitmap));
        rs.destroy();
        Log.d("zhangle", "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
    }

    private static Bitmap big(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(4f, 4f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.25f, 0.25f); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }


    private void requestLogin() {
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            AppHelper.showMsg(mContext, "网络不给力!");
        } else {
            LoginAPI.requestLogin(mContext, mEditAccount.getText().toString(), mEditPassword.getText().toString(), "", 2)
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
//        UserInfoHelper.saveCity(mContext, UserInfoHelper.getCity(mContext));
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


}
