package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.CouponSearchActivity;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/24
 */
public class CouDanAdapter extends BaseQuickAdapter<UserChooseDeductModel.DataBean,BaseViewHolder> {

    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_role;
    private  TextView tv_amount;
    private ImageView iv_status;
    private Context context;
    TextView tv_desc;
    List<queryUserDeductByStateModel.DataBean.ListBean> list;
    RelativeLayout rl_grey;
    TextView tv_tip;
    TextView tv_put;
    boolean isOpen;
    RoleAdapter roleAdapter;
    public CouDanAdapter(int layoutResId, @Nullable List<UserChooseDeductModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserChooseDeductModel.DataBean item) {
        tv_put = helper.getView(R.id.tv_put);
        tv_put.setVisibility(View.VISIBLE);
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_status=helper.getView(R.id.iv_status);
        rl_grey = helper.getView(R.id.rl_grey);
        RecyclerView rv_role = helper.getView(R.id.rv_role);
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
        if(!TextUtils.isEmpty(item.getLimitAmtStr())) {
            tv_user_factor.setText(item.getLimitAmtStr());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());
        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());
        tv_role.setText(item.getReason());

        tv_amount.setText(item.getAmount());

        rv_role.setLayoutManager(new LinearLayoutManager(mContext));
        roleAdapter = new RoleAdapter(R.layout.item_text1,item.getGiftUseRole());
        rv_role.setAdapter(roleAdapter);

        iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
        ViewGroup.LayoutParams lp = rv_role.getLayoutParams();
        roleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(!isOpen) {
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_up);
                    lp.height = DensityUtil.dip2px(RecyclerView.LayoutParams.WRAP_CONTENT,mContext);
                    rv_role.setLayoutParams(lp);
                }else {
                    lp.height = DensityUtil.dip2px(15,mContext);
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                    rv_role.setLayoutParams(lp);
                }
                isOpen = !isOpen;
            }
        });

        lp.height = DensityUtil.dip2px(15,mContext);
        rv_role.setLayoutParams(lp);


        tv_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getGiftProdUseType().equals("0")) {
                    mContext.startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToMarketEvent());
                    EventBus.getDefault().unregister(this);
                }else {
                    Intent intent = new Intent(mContext,CouponSearchActivity.class);
                    intent.putExtra("giftName",item.getGiftName());
                    intent.putExtra("giftDetailNo",item.getGiftDetailNo());
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
