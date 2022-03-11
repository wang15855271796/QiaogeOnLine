package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterMessageActivity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.fragment.home.NewAdapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.GetCompanyModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BannerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    String title;
    String bannerId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        bannerId = getIntent().getStringExtra("bannerId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_banner);
    }

    @Override
    public void findViewById() {

    }

    NewAdapter newAdapter;
    @Override
    public void setViewData() {
        getBanner(bannerId);
        newAdapter = new NewAdapter(R.layout.item_huo_pay, list, new NewAdapter.Onclick() {
            @Override
            public void addDialog() {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {

                }else {
                    initDialog();
                }
            }

            @Override
            public void tipClick() {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setAdapter(newAdapter);
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
                startActivity(RegisterActivity.getIntent(mActivity, RegisterMessageActivity.class));
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    List<ProductNormalModel.DataBean.ListBean> list = new ArrayList<>();
    private void getBanner(String bannerId) {
        IndexHomeAPI.getBanner(mActivity,bannerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductNormalModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ProductNormalModel productNormalModel) {
                        if(productNormalModel.getCode()==1) {
                            if(productNormalModel.getData()!=null) {
                                list.clear();
                                list.addAll(productNormalModel.getData().getList());
                                newAdapter.notifyDataSetChanged();
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,productNormalModel.getMessage());
                        }
                    }
                });
    }
}
