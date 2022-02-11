package com.puyue.www.qiaoge.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.utils.ToastUtil;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CommonH7Activity extends BaseSwipeActivity {

    public static final String URL = "url";

    public static final String TAG = CommonH7Activity.class.getSimpleName();

    private TextView mTvTitle;
    private ProgressBar mProgress;
    private WebView mWv;

    private String mUrl;
    private FrameLayout mFram;
    private ImageView mIvBack;

    public static Intent getIntent(Context context, Class<?> cls, String url) {
        Intent intent = new Intent();
        intent.putExtra(URL, url);
        intent.setClass(context, cls);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(URL);
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString(URL);
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString(URL, mUrl);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_new_h7);
    }

    @Override
    public void findViewById() {
        mIvBack = (ImageView) findViewById(R.id.iv_h5_back);
        mTvTitle = (TextView) findViewById(R.id.tv_h5_title);
        //这个是h5的页面
        mFram = (FrameLayout) findViewById(R.id.frm_h5);
        mProgress = (ProgressBar) findViewById(R.id.progress_h5);
    }

    /**
     * WebView 的初始化设置
     */
    @Override
    public void setViewData() {
        mWv = new WebView(mContext.getApplicationContext());
        mFram.addView(mWv, 0);
        //返回键
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (mWv.canGoBack()) {
                    mWv.goBack();
                } else {
                    finish();
                }
            }
        });
        if (!NetWorkHelper.isNetworkAvailable(mContext)) {
            //没网的情况下,让webview消失
            AppHelper.showMsg(mContext, "网络链接不可用!");
            mWv.setVisibility(View.GONE);
        } else {
            mWv.setVisibility(View.VISIBLE);
            //before WebView setting
//            setCookie();
            //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
            mWv.getSettings().setJavaScriptEnabled(true);
            //
            mWv.getSettings().setDomStorageEnabled(true);
            mWv.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
            String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
            mWv.getSettings().setAppCachePath(appCachePath);
            mWv.getSettings().setAllowFileAccess(true);
            mWv.getSettings().setAppCacheEnabled(true);
//取消登录之后再次点击邀请好友出现弹窗（请先登录）


            mWv.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    super.onReceivedTitle(view, title);
//                    mTvTitle.setText(title);
                }

                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (newProgress != 100) {
                        mProgress.setProgress(newProgress);
                        mProgress.setVisibility(View.VISIBLE);
                    } else {
                        mProgress.setVisibility(View.GONE);
                    }
                    super.onProgressChanged(view, newProgress);
                }
            });
            mWv.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    //WebView被允许访问SSL证书网页
                    handler.proceed();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Uri parse = Uri.parse(url);
                    String code = parse.getQueryParameter("code");
                    String hllOrderId = parse.getQueryParameter("hllOrderId");
                    try{
                        if(url.startsWith("wushang://")){
                            if(!TextUtils.isEmpty(code)&&!code.equals("")) {
                                Intent intent = new Intent(mContext,HuoHomeActivity.class);
                                intent.putExtra("orderId",hllOrderId);
                                startActivity(intent);
                            }

                            if(!TextUtils.isEmpty(hllOrderId)&&!hllOrderId.equals("")) {
                                Intent intent = new Intent(mContext,HuoDetailActivity.class);
                                intent.putExtra("id",hllOrderId);
                                startActivity(intent);
                                finish();
                            }

                            return true;
                        }
                    }catch (Exception e){
                        return false;
                    }

                    mWv.loadUrl(url);
//                    getCode(code);
                    return true;
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }
            });
            mWv.loadUrl(mUrl);
//            Log.e(TAG, "setViewData: "+mUrl );
        }
    }

    private void getCode(String code) {
        HuolalaAPI.getCode(mActivity,code)
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
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }


    @Override
    public void setClickEvent() {

    }

    /**
     * 退出处理
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWv.canGoBack()) {
                mWv.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 这个地方是对Cookie进行管理。
     */
    @Override
    protected void onDestroy() {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
        mWv.onPause();
        mWv.destroy();
        mWv = null;
        mFram.removeAllViews();
        System.gc();
        super.onDestroy();
    }

}
