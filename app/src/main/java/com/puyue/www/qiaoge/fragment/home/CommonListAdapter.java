package com.puyue.www.qiaoge.fragment.home;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CommonListDialog;
import com.puyue.www.qiaoge.dialog.MustDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by ${王涛} on 2020/3/13
 */
public class CommonListAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {
    private ImageView iv_pic;
    List<ProductNormalModel.DataBean.ListBean> activesBean;
    private ImageView iv_add;
    Onclick onclick;
    private CommonListDialog commonListDialog;
    private RelativeLayout rl_group;
    private TextView tv_sale;
    ImageView iv_flag;
    private TextView tv_desc;
    TextView tv_price;
    ImageView iv_operate;
    ImageView iv_next;
    public CommonListAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> activeList, Onclick onclick) {
        super(layoutResId, activeList);
        this.activesBean = activeList;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        iv_next = helper.getView(R.id.iv_next);
        iv_operate = helper.getView(R.id.iv_operate);
        tv_desc = helper.getView(R.id.tv_desc);
        tv_price = helper.getView(R.id.tv_price);
        iv_pic = helper.getView(R.id.iv_pic);
        iv_flag = helper.getView(R.id.iv_flag);
        iv_add = helper.getView(R.id.iv_add);
        rl_group = helper.getView(R.id.rl_group);
        tv_sale = helper.getView(R.id.tv_sale);
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getProductName());
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        Glide.with(mContext).load(item.getSendTimeTpl()).into(iv_next);
        ImageView iv_send = helper.getView(R.id.iv_send);
        if(item.getNotSend().equals("1")) {
            iv_send.setImageResource(R.mipmap.icon_not_send);
            iv_send.setVisibility(View.VISIBLE);
        }else {
            iv_send.setVisibility(View.GONE);
        }
        tv_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.tipClick();
                }
            }
        });

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setVisibility(View.VISIBLE);
                tv_desc.setVisibility(View.GONE);
                tv_price.setText(item.getMinMaxPrice());
            }else {
                tv_price.setVisibility(View.GONE);
                tv_desc.setVisibility(View.VISIBLE);
            }
        }else {
            tv_price.setText(item.getMinMaxPrice());
            tv_price.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }




        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getProductMainId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
//                    if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                        onclick.addDialog();
//                        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//                            commonListDialog = new CommonListDialog(mContext,item.getProductId(),item);
//                            commonListDialog.show();
//                        }
//                    }else {
//                        onclick.tipClick();
//                    }

                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                            commonListDialog = new CommonListDialog(mContext,item.getProductId(),item);
                            commonListDialog.show();
                        }else {
                            onclick.tipClick();
                        }
                    }else {
                        onclick.addDialog();
                    }

                }
            }
        });

    }

    public interface Onclick {
        void addDialog();
        void tipClick();
    }

}
