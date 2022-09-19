package com.puyue.www.qiaoge.adapter.mine;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.RoleAdapter;
import com.puyue.www.qiaoge.api.mine.coupon.userChooseDeductAPI;
import com.puyue.www.qiaoge.dialog.CouponProdDialog;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author daff
 * @date 2018/9/23.
 * 备注 选择优惠券
 */
public class ChooseCouponsAdapter extends BaseQuickAdapter<UserChooseDeductModel.DataBean, BaseViewHolder> {

    private TextView tv_style;
    private  TextView tv_user_factor;
    private  TextView tv_time;
    private  TextView tv_amount;
    TextView tv_desc;
    List<UserChooseDeductModel.DataBean> list;
    TextView tv_tip;
    ImageOnclick imageOnclick;
    ImageView iv_select;
    boolean isOpen;
    RoleAdapter roleAdapter;
    public ChooseCouponsAdapter(int layoutResId, @Nullable List<UserChooseDeductModel.DataBean> data, ImageOnclick imageOnclick) {
        super(layoutResId, data);
        list=data;
        this.imageOnclick = imageOnclick;
    }

    public void setStat() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setFlags(false);
            iv_select.setImageResource(R.mipmap.checkbox_no);
        }
        notifyDataSetChanged();

    }

    @Override
    protected void convert(final BaseViewHolder helper, final UserChooseDeductModel.DataBean item) {
//        helper.setIsRecyclable(false);
        tv_tip=helper.getView(R.id.tv_tip);
        tv_style=helper.getView(R.id.tv_style);
        tv_desc=helper.getView(R.id.tv_desc);
        tv_user_factor=helper.getView(R.id.tv_user_factor);
        tv_time=helper.getView(R.id.tv_time);
        tv_amount=helper.getView(R.id.tv_amount);
        iv_select = helper.getView(R.id.iv_select);
        RecyclerView rv_role = helper.getView(R.id.rv_role);
        tv_time.setText(item.getDateTime());
        ImageView iv_arrow = helper.getView(R.id.iv_arrow);
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


        if(!TextUtils.isEmpty(item.getLimitAmtStr())) {
            tv_user_factor.setText(item.getLimitAmtStr());
            tv_user_factor.setVisibility(View.VISIBLE);
        }else {
            tv_user_factor.setVisibility(View.GONE);
        }
        tv_style.setText(item.getGiftName());

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageOnclick.Onclick(helper.getLayoutPosition(), item.getGiftDetailNo());
            }
        });


        iv_select.setImageResource(item.isFlags() ? R.mipmap.checkbax_yes : R.mipmap.checkbox_no);
        if(item.getState().equals("ENABLED")){  // State== ENABLED   可用使用的优惠卷
            tv_amount.setTextColor(Color.parseColor("#F54022"));
            tv_user_factor.setTextColor(Color.parseColor("#333333"));
            tv_style.setTextColor(Color.parseColor("#333333"));
            tv_tip.setTextColor(Color.parseColor("#F54022"));
            iv_select.setEnabled(true);
        }else  if (item.getState().equals("USED")){//USED 已使用
            tv_desc.setText(item.getUseInfo());
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("OVERTIME")){ //OVERTIME 过期
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }else if(item.getState().equals("UN_ENABLED")) {
            tv_amount.setTextColor(Color.parseColor("#A1A1A1"));
            tv_user_factor.setTextColor(Color.parseColor("#A1A1A1"));
            tv_style.setTextColor(Color.parseColor("#A1A1A1"));
            tv_tip.setTextColor(Color.parseColor("#A1A1A1"));
            iv_select.setEnabled(false);
        }
    }

    public interface ImageOnclick {
        void Onclick(int position, String giftDetailNo);
    }
}