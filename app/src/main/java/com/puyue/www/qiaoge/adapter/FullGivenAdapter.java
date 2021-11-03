package com.puyue.www.qiaoge.adapter;

import androidx.annotation.Nullable;

import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.FullDetailModel;

import java.util.List;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullGivenAdapter extends BaseQuickAdapter<FullDetailModel.DataBean.SendGiftsBean,BaseViewHolder> {

    public FullGivenAdapter(int layoutResId, @Nullable List<FullDetailModel.DataBean.SendGiftsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FullDetailModel.DataBean.SendGiftsBean item) {
        TextView tv_given = helper.getView(R.id.tv_given);
        ImageView iv_pic = helper.getView(R.id.iv_pic);
        tv_given.setText(item.getGiftName()+item.getSendNum());
        if(item.getType()==0) {
            //赠品
//            tv_given.setEnabled(false);
            iv_pic.setImageResource(R.mipmap.icon_zeng);
        }else {
            //优惠券
//            tv_given.setEnabled(true);
            iv_pic.setImageResource(R.mipmap.icon_quan);
        }

//        tv_given.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CouponFullListDialog couponFullListDialog = new CouponFullListDialog(mContext,item.getPoolNo());
//                couponFullListDialog.show();
//            }
//        });
    }
}
