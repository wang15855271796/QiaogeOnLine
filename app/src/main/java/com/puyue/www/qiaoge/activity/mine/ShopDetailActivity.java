package com.puyue.www.qiaoge.activity.mine;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.cart.ImageViewAdapter;
import com.puyue.www.qiaoge.api.home.InfoDetailModel;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.home.InfoListModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;

import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class ShopDetailActivity extends BaseSwipeActivity {
    @BindView(R.id.tv_call)
    TextView tv_call;
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
        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + lists.getContactPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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
                                tv_cate.setText(lists.getMsgTypeName());
                                tv_contact.setText(lists.getContactPhone());
                                tv_address.setText(lists.getDetailAddress());

                                ImageViewAdapter imageViewAdapter = new ImageViewAdapter(R.layout.item_image,lists.getPictureList());
                                recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
                                recyclerView.setAdapter(imageViewAdapter);

//                                imageViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
//
//                                    }
//                                });
                            }
                        } else {
                            AppHelper.showMsg(mActivity, infoListModel.getMessage());
                        }
                    }
                });
    }

}
