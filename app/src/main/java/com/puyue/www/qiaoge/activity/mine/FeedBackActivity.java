package com.puyue.www.qiaoge.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.api.mine.FeedbackAPI;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/4/3.
 */

public class FeedBackActivity extends BaseSwipeActivity {

    private ImageView mIvBack;
    private EditText mEditText;
    private TextView mBtnNext;

    private BaseModel mModelFeedback;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_feedback);
    }


    @Override
    public void findViewById() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEditText = (EditText) findViewById(R.id.edit_feedback);
        mBtnNext = (TextView) findViewById(R.id.btn_feedback);
    }

    @Override
    public void setViewData() {
        mBtnNext.setEnabled(false);
//        mBtnNext.setTextColor(getResources().getColor(R.color.app_btn_unable));
    }

    @Override
    public void setClickEvent() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringHelper.notEmptyAndNull(mEditText.getText().toString())) {
                    mBtnNext.setEnabled(true);
                    mBtnNext.setTextColor(getResources().getColor(R.color.app_color_white));
                } else {
                    mBtnNext.setEnabled(false);
                    mBtnNext.setTextColor(getResources().getColor(R.color.app_btn_unable));
                }
            }
        });

        mBtnNext.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(mEditText.getText().toString())) {
                    requestFeedback();
                    finish();
                } else {
                    AppHelper.showMsg(mContext, "请先输入意见");
                }
            }
        });
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });
    }

    //这个接口现在其他四个参数，直接传空是否可以？如果不成功就要改需求。反馈成功。
    private void requestFeedback() {
        FeedbackAPI.requestFeedback(mContext,
                mEditText.getText().toString(),
                "",
                "",
                "",
                "")
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
                        logoutAndToHome(mContext, baseModel.code);
                        mModelFeedback = baseModel;
                        if (mModelFeedback.success) {
                            //反馈信息成功
                            AppHelper.showMsg(mContext, "反馈成功");
                            finish();
                        } else {
                          AppHelper.showMsg(mContext, mModelFeedback.message);
                        }
                    }
                });
    }
}
