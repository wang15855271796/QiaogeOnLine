package com.puyue.www.qiaoge.activity.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.VipAdapter;
import com.puyue.www.qiaoge.api.mine.order.VipPayAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.VipCenterModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VipActivity extends BaseActivity {
    Unbinder bind;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    VipAdapter vipAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_vip);
    }

    int pos = 0;
    @Override
    public void findViewById() {
        bind = ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity,3));

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, IntegralPayActivity.class);
                intent.putExtra("vipPackageId", vipPackages.get(pos).getPackageId()+"");
                startActivity(intent);
            }
        });

    }

    @Override
    public void setViewData() {
        getVipCenter();
    }

    @Override
    public void setClickEvent() {

    }

    VipCenterModel.DataBean data;
    List<VipCenterModel.DataBean.VipPackagesBean> vipPackages = new ArrayList<>();
    private void getVipCenter() {
        VipPayAPI.getVipCenter(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<VipCenterModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VipCenterModel vipListModel) {
                        if (vipListModel.getCode()==1) {
                            if(vipListModel.getData()!=null) {
                                vipPackages.addAll(vipListModel.getData().getVipPackages());
                                data = vipListModel.getData();
                                if(data.getVipDeductUrl()!=null && !data.getVipDeductUrl().equals("")) {
                                    Glide.with(mContext).load(data.getVipDeductUrl()).into(iv_pic);
                                }
                                if(data.getState().equals("NONE")) {
                                    //未开通
                                    tv_title.setVisibility(View.VISIBLE);
                                    tv_state.setText("立即开通");
                                    tv_desc.setText("翘歌VIP会员可与优惠券叠加使用");
                                    tv_phone.setVisibility(View.GONE);
                                    tv_date.setVisibility(View.GONE);
                                    tv_desc.setVisibility(View.VISIBLE);
                                }else if(data.getState().equals("OVER")) {
                                    //过期
                                    tv_title.setVisibility(View.GONE);
                                    tv_state.setText("立即续费");
                                    tv_date.setText(data.getEndTimeDesc());
                                    String price = "已购买商品并会员满减"+data.getSaveAmountDesc()+"元";
                                    SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(price, 9,
                                            data.getSaveAmountDesc().length(), Color.parseColor("#FFE7A2"));
                                    tv_desc.setText(spannableStringBuilder+"");
                                    tv_desc.setVisibility(View.VISIBLE);
                                    tv_phone.setText(data.getPhone().substring(0,3)+"****"+data.getPhone().substring(7,11));
                                    tv_phone.setVisibility(View.VISIBLE);
                                    tv_date.setVisibility(View.VISIBLE);
                                }else {
                                    //ENABLED（已开通）
                                    tv_title.setVisibility(View.GONE);
                                    tv_state.setText("立即续费");
                                    tv_date.setText(data.getEndTimeDesc());
                                    String price = "已购买商品并会员满减"+data.getSaveAmountDesc()+"元";
                                    SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(price, 9,
                                            data.getSaveAmountDesc().length(), Color.parseColor("#FFE7A2"));
                                    tv_desc.setText(spannableStringBuilder+"");
                                    tv_desc.setVisibility(View.VISIBLE);
                                    tv_phone.setText(data.getPhone().substring(0,3)+"****"+data.getPhone().substring(7,11));
                                    tv_phone.setVisibility(View.VISIBLE);
                                    tv_date.setVisibility(View.VISIBLE);
                                }

                                vipAdapter = new VipAdapter(R.layout.item_vip1,vipPackages,data.getState());
                                recyclerView.setAdapter(vipAdapter);
                                vipAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        vipAdapter.setPos(position);
                                        pos = position;
                                        vipAdapter.notifyDataSetChanged();
                                    }
                                });
                                vipAdapter.notifyDataSetChanged();
                            }

                        } else {

                        }
                    }
                });
    }

}
