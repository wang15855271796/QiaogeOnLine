package com.puyue.www.qiaoge.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Looper;
import android.view.KeyEvent;

import com.gyf.barlibrary.ImmersionBar;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * BaseActivity
 * Created by GuoGai on 2016/6/29.
 */
public abstract class BaseActivity extends AppCompatActivity  {
    private long mExitTime = 0;
    private boolean mIsExit = false;
    protected Context mContext;
    protected Activity mActivity;
    protected Resources mResources;
    protected Bundle mBundle;
    private ImmersionBar mImmersionBar;
    TencentLocationManager instance;
    public static Intent getIntent(Context context, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(context, cls);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
//        instance = TencentLocationManager.getInstance(QiaoGeApplication.getContext());
//        instance.requestSingleFreshLocation(null, mLocationListener, Looper.getMainLooper());
        setContentView();

        mResources = this.getResources();
        mBundle = savedInstanceState;
        mImmersionBar = ImmersionBar.with(this).fullScreen(false)   ;
        mImmersionBar.init();   //所有子类都将继承这些相同的属性
        ButterKnife.bind(this);
        findViewById();
        setViewData();
        setClickEvent();
//        StatusBarCompat.setStatusBarColor(mActivity, Color.parseColor("#cccccc"), true);

//        //进行Android 6.0的动态权限申请
//        requestAndroidSixPermissions();

    }

//    TencentLocationListener mLocationListener = new TencentLocationListener() {
//        @Override
//        public void onLocationChanged(TencentLocation location, int i, String s) {
//            ToastUtil.showSuccessMsg(mContext,s);
//        }
//
//        @Override
//        public void onStatusUpdate(String s, int i, String s1) {
//        }
//    };
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;

    }

    private void requestAndroidSixPermissions() {
        String[] params = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA};

        if (EasyPermissions.hasPermissions(this, params)) {//检查是否获取该权限
            //全部允许
        } else {//第二次请求
            //存在不允许的权限  对话框为什么一会出来一会不出来
            EasyPermissions.requestPermissions(this, "需要加载必要的权限。", 1, params);

        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions. onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//    }
//
//
//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
//
//    }


    public abstract boolean handleExtra(Bundle savedInstanceState);

    /**
     * 设置ContentView
     */
    public abstract void setContentView();

    /**
     * 初始化Views
     */
    public abstract void findViewById();

    /**
     * 填充View数据
     */
    public abstract void setViewData();

    /**
     * 设置View点击事件监听器
     */
    public abstract void setClickEvent();

    /**
     * 是否允许退出App，默认false
     *
     * @param isExit
     */
    protected void keyBackExitApp(boolean isExit) {
        mIsExit = isExit;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mIsExit) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    AppHelper.showMsg(this, "再按一次退出程序！");
                    mExitTime = System.currentTimeMillis();
                    return true;
                } else {
                    finish();
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }


}