package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.PayListModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/8/31
 */
public class PayListAdapter extends BaseQuickAdapter<PayListModel.DataBean,BaseViewHolder> {
    int selectionPosition = -1;
    TextView tv_title;
    ImageView iv_pic;
    String payAmount;
    public PayListAdapter(int layoutResId, @Nullable List<PayListModel.DataBean> data, String payAmount) {
        super(layoutResId, data);
        this.payAmount = payAmount;
    }

    @Override
    protected void convert(BaseViewHolder helper, PayListModel.DataBean item) {
        iv_pic = helper.getView(R.id.iv_pic);
        ImageView iv_gou = helper.getView(R.id.iv_gou);
        tv_title = helper.getView(R.id.tv_title);
        if(item.getFlag().equals("0")) {
            tv_title.setText(item.getChannelName()+"("+item.getWalletAmt()+")");
            //余额
            if(item.getWalletEnabled()==0) {
                //支付金额 > 用户余额(余额不足)
                iv_gou.setImageResource(R.mipmap.ic_enable_select_oval);
                iv_gou.setClickable(false);
            }else {
                iv_gou.setImageResource(R.mipmap.ic_un_select_oval);
                iv_gou.setClickable(true);
            }
        }else {
            iv_gou.setClickable(true);
            tv_title.setText(item.getChannelName());
            iv_gou.setImageResource(R.mipmap.ic_un_select_oval);
        }

        if(helper.getAdapterPosition()==0) {
            getStat(item);
        }else if(helper.getAdapterPosition()==1){
            getStat(item);
        } else if(helper.getAdapterPosition()==2){
            getStat(item);
        } else if(helper.getAdapterPosition()==3){
            getStat(item);
        }

        iv_gou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onCheckItemListener!=null) {
                    onCheckItemListener.onItemClick(helper.getAdapterPosition());
                }
            }
        });


        if(selectionPosition == helper.getAdapterPosition()) {
            if(item.getFlag().equals("0")) {
                if(item.getWalletEnabled() ==1) {
                    //支付金额 < 用户余额(余额充足)
                    iv_gou.setImageResource(R.mipmap.ic_circle_selected);
                    iv_gou.setClickable(true);
                }else {
                    iv_gou.setImageResource(R.mipmap.ic_enable_select_oval);
                    iv_gou.setClickable(false);
                }
            }else {
                iv_gou.setImageResource(R.mipmap.ic_circle_selected);
                iv_gou.setClickable(true);
            }
        }else {
            if(item.getFlag().equals("0")) {
                if(item.getWalletEnabled() ==1) {
                    //支付金额 < 用户余额(余额充足)
                    iv_gou.setImageResource(R.mipmap.ic_un_select_oval);
                    iv_gou.setClickable(true);
                }else {
                    iv_gou.setImageResource(R.mipmap.ic_enable_select_oval);
                    iv_gou.setClickable(false);
                }

            }else {
                iv_gou.setClickable(true);
                iv_gou.setImageResource(R.mipmap.ic_un_select_oval);
            }
        }
    }

    private void getStat(PayListModel.DataBean item) {
        if(item.getFlag().equals("0")) {
            iv_pic.setImageResource(R.mipmap.ic_balance_pay);
        }else if(item.getFlag().equals("1")) {
            iv_pic.setImageResource(R.mipmap.ic_pay_alipay);
        }else if(item.getFlag().equals("2")) {

            iv_pic.setImageResource(R.mipmap.ic_pay_wechar);
        }else if(item.getFlag().equals("3")) {
            iv_pic.setImageResource(R.mipmap.icon_yun);
        }
    }

    public void selectionPosition(int position){
        this.selectionPosition  = position;
    }

    OnCheckItemListener onCheckItemListener;
    public void setOnCheckItemListener(OnCheckItemListener onCheckItemListener) {
        this.onCheckItemListener = onCheckItemListener;
    }

    public interface OnCheckItemListener {
        void onItemClick(int pos);
    }
}
