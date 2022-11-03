package com.puyue.www.qiaoge.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.PrivacysDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

/**
 * Created by Administrator on 2018/5/21.
 */

public class SplashActivity extends BaseActivity {
    /**
     * 获取协议
     */
    PrivacysDialog privacyDialog;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void findViewById() {

    }


    @Override
    public void setViewData() {
        //是否点击了隐私权限
        SharedPreferencesUtil.getString(mContext,"once").equals("-1");
        privacyDialog = new PrivacysDialog(mActivity);
        privacyDialog.setCanceledOnTouchOutside(false);
        privacyDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == keyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });

        if (!SharedPreferencesUtil.getString(mActivity, "once").equals("0")) {
            privacyDialog.show();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String userId = UserInfoHelper.getUserId(mContext);
                    if(StringHelper.notEmptyAndNull(userId)) {
                        Intent intent = new Intent(mActivity,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent = new Intent(mActivity,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            },1000);
        }
    }

    @Override
    public void setClickEvent() {

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.d("fdsrgdffs....",KeyEvent.KEYCODE_BACK+"s");
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
//            //do something.
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }


    //    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.activity_splash);
//    }
//
//    @Override
//    public void findViewById() {
//
//    }
//
//    @Override
//    public void setViewData() {
////        SharedPreferencesUtil.saveString(mContext,"once","0");
//        //进行Android 6.0的动态权限申请
//        requestAndroidSixPermissions();
//        getPrivacys();
//
//    }
//
//
//
//    @Override
//    public void setClickEvent() {
//
//    }
//    private void requestAndroidSixPermissions() {
////        String[] params = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA};
////        if (EasyPermissions.hasPermissions(this, params)) {//检查是否获取该权限
////            //全部允许
////            handleSplash();
////        } else {//第二次请求
////            //存在不允许的权限  对话框为什么一会出来一会不出来
////            EasyPermissions.requestPermissions(this, "需要加载必要的权限。", 1, params);
////
////        }
////        String[] params = {Manifest.permission.READ_EXTERNAL_STORAGE,
////                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA};
////        if (EasyPermissions.hasPermissions(this, params)) {//检查是否获取该权限
////            //全部允许
////            handleSplash();
////        } else {//第二次请求
////            //存在不允许的权限  对话框为什么一会出来一会不出来
////            EasyPermissions.requestPermissions(this, "需要加载必要的权限。", 1, params);
////
////        }
//        handleSplash();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        handleSplash();
//
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
//
//    }
//    /**
//     * 闪屏处理
//     */
//    private void handleSplash() {
//        //对于银联支付没有回调的处理方式
//        // 闪屏的核心代码
//        SharedPreferencesUtil.getString(mContext,"once").equals("-1");
////        new Handler().postDelayed(new Runnable() {
////            @Override
////            public void run() {
////                Intent intent = new Intent(mContext, HomeActivity.class);
////                intent.putExtra("go_home", "goHome");
////                startActivity(intent);
////                finish();
////            }
////        },1000);
//    }
//
//    /**
//     * 获取权限
//     */
//    PrivacysDialog privacyDialog;
//    private void getPrivacys() {
//        IndexHomeAPI.getPrivacy(mActivity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<PrivacyModel>() {
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
////                        if(privacyModel.getData().getOpen().equals("0")) {
////                            privacyDialog.dismiss();
////                        }else {
////                            privacyDialog.show();
////                        }
//                    }
//
//                    @Override
//                    public void onNext(PrivacyModel privacyModel) {
//                        if (privacyModel.isSuccess()) {
//                            privacyDialog = new PrivacysDialog(mActivity,privacyModel.getData().getContent());
//                            if (!SharedPreferencesUtil.getString(mActivity, "once").equals("0")) {
//                                privacyDialog.show();
//                            } else {
//                                privacyDialog.dismiss();
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Intent intent = new Intent(mContext, HomeActivity.class);
//                                        intent.putExtra("go_home", "goHome");
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                },1000);
//                            }
//                        } else {
//                            AppHelper.showMsg(mActivity, privacyModel.getMessage());
//                        }
//                    }
//                });
//    }

}
