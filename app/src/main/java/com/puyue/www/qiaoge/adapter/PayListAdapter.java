package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
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
    int selectionPosition = 0;
    ImageView iv_gou;
    TextView tv_title;
    ImageView iv_pic;
    public PayListAdapter(int layoutResId, @Nullable List<PayListModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayListModel.DataBean item) {
        iv_pic = helper.getView(R.id.iv_pic);
        iv_gou = helper.getView(R.id.iv_gou);
        tv_title = helper.getView(R.id.tv_title);
        if(item.getFlag().equals("0")) {
            tv_title.setText(item.getChannelName()+"("+UserInfoHelper.getUserWalletAccount(mContext)+")");
        }else {
            tv_title.setText(item.getChannelName());
        }

        if(helper.getAdapterPosition()==0) {
            helper.getView(R.id.iv_recomend).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.iv_recomend).setVisibility(View.GONE);
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
        if(selectionPosition==helper.getAdapterPosition()) {
            helper.setVisible(R.id.iv_gou,true);
        }else {
            helper.setVisible(R.id.iv_gou,false);
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

}
