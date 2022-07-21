package com.puyue.www.qiaoge.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.FullDescDialogAdapter;
import com.puyue.www.qiaoge.adapter.market.PhotoViewAdapter;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.dialog.CartFullDialog;
import com.puyue.www.qiaoge.dialog.ErrorAuthDialog;
import com.puyue.www.qiaoge.event.LogoutEvent;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.model.AuthModel;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.CartFullsModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.PhotoViewPager;
import com.puyue.www.qiaoge.view.datepicker.FingerFrameLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

//import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by GuoGai on 2016/7/26.
 */
public class AppHelper {
    /**
     * 获取包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        String packageName;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getPackageName", e.toString());
            return "com.puyue.www.qiaoge";
        }
        return packageName;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        String version;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (Exception e) {
            Log.e("getVersion", e.toString());
            return "1.0.0";
        }
        return version;
    }

    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenDispaly(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 检测某ActivityUpdate是否在当前Task的栈顶
     *
     * @param context
     * @return
     */
    public static boolean isTopActivy(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
        String cmpNameTemp = null;
        if (null != runningTaskInfos) {
            cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
        }

        if (null == cmpNameTemp) {
            return false;
        }

        return cmpNameTemp.equals(AppHelper.getPackageName(context));

    }

    /**
     * 判断Android应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getPackageName();
        List<ActivityManager.RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);

        if (appTask == null) {
            return false;
        }

        if (appTask.get(0).baseIntent.toString().contains(packageName)) {
            return true;
        }
        return false;
    }

    public static void UserLogout(Context context, int mStateCode, int type) {
        if (mStateCode == -10000 || mStateCode == -10001) {
            //异地登录了,需要清除应用内部的userId,让用户重新登录
            UserInfoHelper.saveUserId(context, "");
            UserInfoHelper.saveUserType(context, "");
            UserInfoHelper.saveUserHomeRefresh(context, "");
            UserInfoHelper.saveUserMarketRefresh(context, "");
            if (type == 1) {
                EventBus.getDefault().post(new LogoutEvent());
            }
        }
    }

    public static void ShowAuthDialog(Activity context,String cell) {
        AlertDialog mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        Window window = mDialog.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mDialog.getWindow().setContentView(R.layout.dialog_authorize);
        TextView tv_sure = mDialog.getWindow().findViewById(R.id.tv_sure);
        ImageView iv_cancel = mDialog.getWindow().findViewById(R.id.iv_cancel);
        TextView tv_get = mDialog.getWindow().findViewById(R.id.tv_get);
        EditText et_authprize = mDialog.getWindow().findViewById(R.id.et_authprize);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        tv_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhoneDialog(context,cell);
            }
        });

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode(et_authprize.getText().toString(),context,mDialog);
            }
        });
    }

    private static void getCode(String code, Context context, AlertDialog mDialog) {
        IndexHomeAPI.getCode(context,code)
                .subscribeOn(Schedulers.io())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AuthModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(AuthModel indexInfoModel) {

                        if (indexInfoModel.getCode()==1) {
                            ToastUtil.showSuccessMsg(context,indexInfoModel.getMessage());
                            Intent intent = new Intent(context,HomeActivity.class);//跳回首页
                            context.startActivity(intent);
                            EventBus.getDefault().post(new CityEvent());
                            mDialog.dismiss();
                        }else if(indexInfoModel.getCode()==100005) {
                            ErrorAuthDialog errorAuthDialog = new ErrorAuthDialog(context,indexInfoModel.getData()) {
                                @Override
                                public void Confirm(String amount) {

                                }
                            };
                            errorAuthDialog.show();
                        }else {
                            ToastUtil.showSuccessMsg(context,indexInfoModel.getMessage());
                        }
                    }
                });
    }

    /**
     * 弹出电话号码
     */

    public static void showPhoneDialog(final Context context, final String cell) {
        final AlertDialog mDialog = new AlertDialog.Builder(context).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_call_phone);
        TextView tv_phones = mDialog.getWindow().findViewById(R.id.tv_phone);
        TextView tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
        tv_phones.setText("客服热线 ("+cell+")");


        tv_phones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                mDialog.dismiss();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(context);
                mDialog.dismiss();
            }
        });

    }
    public static String AuthorizationCode;
    public static AlertDialog mDialogAuth;
    /**
     * 显示授权弹窗
     */
    public static void showAuthorizationDialog(final Context context, final String cell, View.OnClickListener onClickListener) {
        mDialogAuth = new AlertDialog.Builder(context).create();
        mDialogAuth.show();
        mDialogAuth.getWindow().setContentView(R.layout.dialog_authorization_account);
        mDialogAuth.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        mDialogAuth.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        TextView mTvCell = (TextView) mDialogAuth.getWindow().findViewById(R.id.tv_dialog_cell);
        mTvCell.setText(cell);
        mTvCell.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvCell.getPaint().setAntiAlias(true);//抗锯齿
        final EditText mEtCode = mDialogAuth.getWindow().findViewById(R.id.et_dialog_code);
        mEtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                AuthorizationCode = mEtCode.getText().toString();
            }
        });
        mDialogAuth.getWindow().findViewById(R.id.tv_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogAuth.dismiss();

            }
        });
        mTvCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTablet(context)) {
                    AppHelper.showMsg(context, "当前设备不具备拨号功能");
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                mDialogAuth.dismiss();
            }
        });
        mDialogAuth.getWindow().findViewById(R.id.tv_dialog_sure).setOnClickListener(onClickListener);
    }

    //显示满减弹窗
    public static void showFullDialog(final Activity context) {
        getFullDetail(context);
    }

    private static void getFullDetail(Context context) {
        CartListAPI.getFullDetails(context,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartFullsModel>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CartFullsModel cartFullModel) {
                        if(cartFullModel.getCode()==1) {
                            if(cartFullModel.getData()!=null&&cartFullModel.getData()!=null) {
                                CartFullsModel.DataBean data = cartFullModel.getData();
                                CartFullDialog cartFullDialog = new CartFullDialog(context,data) {
                                    @Override
                                    public void close() {
                                        dismiss();
                                    }
                                };
                                cartFullDialog.show();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(context,cartFullModel.getMessage());
                        }

                    }
                });
    }

    public static void hideAuthorizationDialog() {
        mDialogAuth.dismiss();
    }

    /**
     * 获取授权码
     */
    public static String getAuthorizationCode() {
        return AuthorizationCode;
    }

    public static void setAuthorizationCode(String authorizationCode) {
        AuthorizationCode = authorizationCode;
    }

    /**
     * 平板返回 True，手机返回 False
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * //在不加载图片情况下获取图片大小
     */
    public static int[] getImageWidthHeight(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth, options.outHeight};
    }

    public static Dialog dialog;
    public static View dialogView;
    public static boolean isShow = false;

    /**
     * 查看大图
     */
    public static void showPhotoDetailDialog(Context mContext, final List<String> mListUrl, int position) {
        dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_photo, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
       // window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final TextView mTv = dialog.findViewById(R.id.tv_dialog_photo);
        PhotoViewPager mVp = dialog.findViewById(R.id.vp_dialog_photo);
        FingerFrameLayout mFl = dialog.findViewById(R.id.ffl_dialog_photo);
        mFl.setOnAlphaChangeListener(new FingerFrameLayout.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                Log.e("fengan", "[onAlphaChanged]:alpha=" + alpha);
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                Log.e("fengan", "[onTranslationYChanged]:translationY=" + translationY);
            }

            @Override
            public void onFinishAction() {
                hidePhotoDetailDialog();
            }
        });
        PhotoViewAdapter photoViewAdapter = new PhotoViewAdapter(mListUrl, mContext);
        mVp.setAdapter(photoViewAdapter);
        mVp.setCurrentItem(position);
        mTv.setText(position  + 1+"/" + mListUrl.size());
        photoViewAdapter.setPhotoListener(new PhotoViewAdapter.OnPhotoListener() {
            @Override
            public void onPhotoListenter() {
                if (dialog!=null){
                    dialog.dismiss();
                }
            }
        });

        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTv.setText(position + 1 + "/" + mListUrl.size());
                mTv.getBackground().setAlpha(100);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        dialog.show();
        isShow = true;
    }

    /**
     * 查看大图1
     */
    public static void showPhotoDetailDialog1(Context mContext, final String mListUrl) {
        dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_photo1, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        // window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        final ImageView iv_pic = dialog.findViewById(R.id.iv_pic);
        FingerFrameLayout mFl = dialog.findViewById(R.id.ffl_dialog_photo);
        Glide.with(mContext).load(mListUrl).into(iv_pic);
        mFl.setOnAlphaChangeListener(new FingerFrameLayout.onAlphaChangedListener() {
            @Override
            public void onAlphaChanged(float alpha) {
                Log.e("fengan", "[onAlphaChanged]:alpha=" + alpha);
            }

            @Override
            public void onTranslationYChanged(float translationY) {
                Log.e("fengan", "[onTranslationYChanged]:translationY=" + translationY);
            }

            @Override
            public void onFinishAction() {
                hidePhotoDetailDialog();
            }
        });

        dialog.show();
    }

    /**
     * 隐藏查看大图
     */
    public static void hidePhotoDetailDialog() {
        if (isShow) {
            dialog.dismiss();
            isShow = false;
        }
    }

    /**
     * 显示吐司
     */
    public static Toast mToast;

    public static void showMsg(Context context, String Msg) {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), Msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setText(Msg);
        }
        mToast.show();
    }

    /**
     * 设置缺省页
     */
    public static View getEmptyView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_nodata, null, false);
        return view;
    }

    /**
     * 设置错误页
     */
    public static View getErrorView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_nodata, null, false);
        return view;
    }

    /**
     * 设置加载动画
     */
    public static View getLoadingView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_loading, null, false);
        return view;
    }

    /**
     * 隐藏加载动画
     **/
    public static void cancleLottieAnimation(View view) {
      LottieAnimationView lav = view.findViewById(R.id.lav_loading);
       lav.cancelAnimation();
    }



    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

}
