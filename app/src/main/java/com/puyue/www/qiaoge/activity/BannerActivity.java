package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterMessageActivity;
import com.puyue.www.qiaoge.adapter.HomeBannerAdapter;
import com.puyue.www.qiaoge.api.home.BannerModel;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.fragment.home.NewAdapter;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.GetCompanyModel;
import com.puyue.www.qiaoge.model.HomeBannerModel;
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
    @BindView(R.id.iv_banner)
    ImageView iv_banner;
    String title;
    String bannerId;
    String defaultPic;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        bannerId = getIntent().getStringExtra("bannerId");
        defaultPic = getIntent().getStringExtra("defaultPic");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_banner);
    }

    @Override
    public void findViewById() {

    }

    HomeBannerAdapter newAdapter;
    @Override
    public void setViewData() {
        getBanner(bannerId);
        tv_title.setText(title);
        Glide.with(mActivity).load(defaultPic).into(iv_banner);
        newAdapter = new HomeBannerAdapter(R.layout.item_team_list, list, new HomeBannerAdapter.Onclick() {
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
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,2));
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

    List<HomeBannerModel.DataBean> list = new ArrayList<>();
    private void getBanner(String bannerId) {
        IndexHomeAPI.getBanner(mActivity,bannerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeBannerModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(HomeBannerModel homeBannerModel) {
                        if(homeBannerModel.getCode()==1) {

                            if(homeBannerModel.getData()!=null) {
                                list.clear();
                                for (int i = 0; i < homeBannerModel.getData().size(); i++) {
                                    list.add(homeBannerModel.getData().get(i));
                                }

                                newAdapter.notifyDataSetChanged();

                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,homeBannerModel.getMessage());
                        }
                    }
                });
    }
}
