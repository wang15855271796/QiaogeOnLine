package com.puyue.www.qiaoge.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.PrivacysDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/21.
 */

public class SplashActivity extends BaseActivity {
    /**
     * 获取协议
     */
    PrivacysDialog privacyDialog;
    ImageView iv_pic;
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
        Log.d("wxSdqwda.....","12");
//        Intent intent = getIntent();
//        Uri uri = intent.getData();
//        Log.d("swdaswdasd....",uri+"--");
//        if (uri != null) {
//            String pid = uri.getQueryParameter("pid");
//        }
        iv_pic = findViewById(R.id.iv_pic);
        SharedPreferencesUtil.getString(mContext,"once").equals("-1");
        Date time = Calendar.getInstance().getTime();
        int month =time.getMonth();
        int day = time.getDate();

        if(month+1 == 5 && day == 1) {
            iv_pic.setImageResource(R.mipmap.iv_wu_yi);
        }else {
            iv_pic.setImageResource(R.mipmap.ic_splash_three);
        }

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
}
