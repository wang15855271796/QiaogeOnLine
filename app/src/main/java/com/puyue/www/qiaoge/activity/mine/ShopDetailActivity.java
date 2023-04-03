package com.puyue.www.qiaoge.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ImageVideoViewAdapter;
import com.puyue.www.qiaoge.adapter.cart.ImageViewAdapter;
import com.puyue.www.qiaoge.api.home.InfoDetailModel;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class ShopDetailActivity extends BaseSwipeActivity {
//    @BindView(R.id.tv_call)
//    TextView tv_call;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String msgId;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.tv_contact)
    TextView tv_contact;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_pay)
    RelativeLayout rl_pay;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.rl_pay_time)
    RelativeLayout rl_pay_time;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.rl_return_account)
    RelativeLayout rl_return_account;
    @BindView(R.id.tv_return_account)
    TextView tv_return_account;
    @BindView(R.id.rl_return_time)
    RelativeLayout rl_return_time;
    @BindView(R.id.tv_return_time)
    TextView tv_return_time;
    @BindView(R.id.ll_info)
    LinearLayout ll_info;
    InfoDetailModel.DataBean lists;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_shop_detail);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        msgId = getIntent().getStringExtra("msgId");
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCityList();


    }

    @Override
    public void setClickEvent() {

    }

    private void getCityList() {
        InfoListAPI.getDetail(mActivity,msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoDetailModel infoListModel) {
                        if (infoListModel.isSuccess()) {
                            if(infoListModel.getData()!=null) {
                                lists = infoListModel.getData();
                                tv_phone.setText(lists.getUserPhone());
                                tv_content.setText(lists.getContent());
                                tv_cate.setText("#"+lists.getMsgTypeName());
                                tv_contact.setText(lists.getContactPhone());
                                tv_address.setText(lists.getDetailAddress());

                                if(lists.getPayFlag() ==0) {
                                    ll_info.setVisibility(View.GONE);
                                }else if(lists.getPayFlag() ==1) {
                                    ll_info.setVisibility(View.VISIBLE);
                                    rl_pay.setVisibility(View.VISIBLE);
                                    rl_pay_time.setVisibility(View.VISIBLE);
                                    rl_return_account.setVisibility(View.GONE);
                                    rl_return_time.setVisibility(View.GONE);
                                    tv_pay.setText(lists.getPayAmt());
                                    tv_pay_time.setText(lists.getPayTime());
                                }else {
                                    ll_info.setVisibility(View.VISIBLE);
                                    rl_pay.setVisibility(View.GONE);
                                    rl_pay_time.setVisibility(View.GONE);
                                    rl_return_account.setVisibility(View.VISIBLE);
                                    rl_return_time.setVisibility(View.VISIBLE);
                                    tv_return_time.setText(lists.getReturnTime());
                                    tv_return_account.setText(lists.getReturnAmt());
                                }

                                List<String> pictureList = lists.getPictureList();

                                if(lists.getVideoUrl()!=null) {
                                    pictureList.add(lists.getVideoUrl());
                                }


                                ImageVideoViewAdapter imageViewAdapter = new ImageVideoViewAdapter(R.layout.item_image,pictureList);
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
                                recyclerView.setAdapter(imageViewAdapter);

                                imageViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if(!pictureList.get(position).contains(".mp4")) {
                                            List<String> result = Arrays.asList(pictureList.get(position).split(","));
                                            AppHelper.showIssueDetailDialog(mActivity, pictureList, position);
                                        }else {
                                            PictureSelector.create(ShopDetailActivity.this).externalPictureVideo(pictureList.get(position));
                                        }
                                    }
                                });

                            }
                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
                        }
                    }
                });
    }

}
