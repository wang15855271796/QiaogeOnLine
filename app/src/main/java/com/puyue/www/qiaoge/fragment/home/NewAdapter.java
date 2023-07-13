package com.puyue.www.qiaoge.fragment.home;

import android.content.Intent;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.NewDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import java.util.List;

public class NewAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean,BaseViewHolder> {

    private ImageView iv_pic;
    List<ProductNormalModel.DataBean.ListBean> activesBean;
    private ImageView iv_add;
    Onclick onclick;
    private NewDialog newDialog;
    private RelativeLayout rl_group;
    private TextView tv_sale;
    ImageView iv_flag;
    String enjoyProduct;
    private TextView tv_desc;
    TextView tv_price;
    ImageView iv_operate;
    ImageView iv_next;
    View v_champion;
    TextView tv_champion;
    LinearLayout ll_champion;
    ImageView iv_fresh_price;
    public NewAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> activeList, Onclick onclick) {
        super(layoutResId, activeList);
        this.activesBean = activeList;
        this.onclick = onclick;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        ll_champion = helper.getView(R.id.ll_champion);
        iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        tv_champion = helper.getView(R.id.tv_champion);
        v_champion = helper.getView(R.id.v_champion);
        iv_next = helper.getView(R.id.iv_next);
        iv_operate = helper.getView(R.id.iv_operate);
        tv_desc = helper.getView(R.id.tv_desc);
        tv_price = helper.getView(R.id.tv_price);
        iv_pic = helper.getView(R.id.iv_pic);
        iv_flag = helper.getView(R.id.iv_flag);
        iv_add = helper.getView(R.id.iv_add);
        rl_group = helper.getView(R.id.rl_group);
        tv_sale = helper.getView(R.id.tv_sale);
        ImageView iv_send = helper.getView(R.id.iv_send);
        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        if(item.getHotProdFlag()==1) {
            //1热销
            ll_champion.setVisibility(View.VISIBLE);
            v_champion.setVisibility(View.GONE);
        }else {
            ll_champion.setVisibility(View.GONE);
            v_champion.setVisibility(View.GONE);
        }
        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.ic_refresh_price);
        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }

        ll_champion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HotProductActivity.class);
                mContext.startActivity(intent);
            }
        });

        Glide.with(mContext).load(item.getDefaultPic()).into(iv_pic);
        helper.setText(R.id.tv_name,item.getProductName());
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        Glide.with(mContext).load(item.getSendTimeTpl()).into(iv_next);
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



        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                        if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                            newDialog = new NewDialog(mContext,item.getProductId(),item);
                            newDialog.show();
                        }else {
                            onclick.tipClick();
                        }
                    }else {
                        onclick.addDialog();
                    }
//                    if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
//                        onclick.addDialog();
//                        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
//                            newDialog = new NewDialog(mContext,item.getProductId(),item);
//                            newDialog.show();
//                        }
//                    }else {
//                        onclick.tipClick();
//                    }
                }
            }
        });
        rl_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                intent.putExtra(AppConstant.ACTIVEID,item.getProductMainId());
                intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                mContext.startActivity(intent);
            }
        });

    }

    public interface Onclick {
        void addDialog();
        void tipClick();
    }

}
