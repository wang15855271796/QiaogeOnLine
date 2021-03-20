package com.puyue.www.qiaoge.activity.mine.login;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HisAddressAdapter;
import com.puyue.www.qiaoge.api.mine.login.LoginAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.AddressMessageModel;
import com.puyue.www.qiaoge.model.HisModel;
import com.puyue.www.qiaoge.utils.EnCodeUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/11/19
 */
public class TakeMessageActivity extends BaseSwipeActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    String phone;
    String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTykrDv1TEKVjDeE29kVLo5M7mctlE65WlHSMN8RVL1iA9jXsF9SMNH1AErs2lqxpv18fd3TOAw0pBaG+cXOxApKdvRDKgxyuHnONOBzxr6EyWOQlRZt94auL1ESVbLdvYa7+cISkVe+MphfQh7uI/64tGQ34aRNmvFKv9PEeBTQIDAQAB";
    String phones;
    List<HisModel.DataBean> lists = new ArrayList<>();
    HisAddressAdapter hisAddressAdapter;
    String id;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_take);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        hisAddressAdapter = new HisAddressAdapter(R.layout.item_his_address,lists);
        recyclerView.setAdapter(hisAddressAdapter);

        hisAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                id = lists.get(position).getId();
                hisAddressAdapter.setNotify(position);
                hisAddressAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setViewData() {
        phone = getIntent().getStringExtra("phone");
        if(!TextUtils.isEmpty(phone)) {
            try {
                phones = EnCodeUtil.encryptByPublicKey(phone, publicKeyStr);
                getCheckAdddress(phones);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAdddress(id);
            }
        });
    }

    @Override
    public void setClickEvent() {

    }

    //获取历史收货地址
    private void getCheckAdddress(String phones) {
        LoginAPI.getCheckAddress(mContext,phones)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HisModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HisModel hisModel) {
                        if (hisModel.isSuccess()) {
                            if(hisModel.getData()!=null) {
                                lists.clear();
                                List<HisModel.DataBean> data = hisModel.getData();
                                lists.addAll(data);
                                hisAddressAdapter.notifyDataSetChanged();
                                hisAddressAdapter.setNotify(-1);
                                tv_next.setEnabled(true);
                            }else {
                                tv_next.setEnabled(false);
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext,hisModel.getMessage());
                            tv_next.setEnabled(false);
                        }
                    }
                });
    }

    //验证收货地址
    private void checkAdddress(String id) {
        LoginAPI.checkAddress(mContext,phones,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressMessageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressMessageModel addressMessageModel) {
                        if (addressMessageModel.isSuccess()) {
                            if(addressMessageModel.getData().getIsTrue().equals("1")) {
                                Intent intent = new Intent(mContext,CommonContactActivity.class);
                                intent.putExtra("phone",phone);
                                intent.putExtra("id",id);
                                startActivity(intent);
                                finish();
                                ToastUtil.showSuccessMsg(mContext,"认证成功");
                            }else {
                                if(addressMessageModel.getData().getTimes()==0) {
                                    ToastUtil.showSuccessMsg(mContext,"您机会已用完,如需帮助,请联系客服处理");
                                }else {
                                    ToastUtil.showSuccessMsg(mContext,"验证失败,您今日还剩"+addressMessageModel.getData().getTimes()+"次");
                                    getCheckAdddress(phones);
                                }

                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext,addressMessageModel.getMessage());
                        }
                    }
                });
    }

}
