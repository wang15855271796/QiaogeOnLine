package com.puyue.www.qiaoge.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;
import com.puyue.www.qiaoge.model.mine.coupons.queryUserDeductByStateModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/24
 */
public class CouDanUnAdapter extends BaseQuickAdapter<UserChooseDeductModel.DataBean,BaseViewHolder> {
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

    public CouDanUnAdapter(int layoutResId, @Nullable List<UserChooseDeductModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, UserChooseDeductModel.DataBean item) {
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_role=helper.getView(R.id.tv_role);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_status=helper.getView(R.id.iv_status);
        rl_grey = helper.getView(R.id.rl_grey);
        if(!TextUtils.isEmpty(item.getLimitAmtStr())){
            tv_style.setText(item.getLimitAmtStr());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        //item.getGiftType()+"   "+
        tv_user_factor.setText(item.getGiftName());
        tv_time.setText(item.getDateTime());
        tv_amount.setText(item.getAmount());
        tv_role.setText(item.getReason());
//        if (item.getRole().size()>0){
//            tv_role.setText(item.getRole().get(0));
//            tv_role.setVisibility(View.VISIBLE);
//
//        }else {
//            tv_role.setText("");
//            tv_role.setVisibility(View.INVISIBLE);
//        }

        tv_role.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                queryProd(item.getGiftDetailNo());
            }
        });
    }
}
