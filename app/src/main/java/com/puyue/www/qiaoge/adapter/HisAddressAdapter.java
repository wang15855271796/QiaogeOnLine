package com.puyue.www.qiaoge.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HisModel;

import java.util.List;

/**
 * Created by ${王涛} on 2020/11/20
 */
public class HisAddressAdapter extends BaseQuickAdapter<HisModel.DataBean,BaseViewHolder> {
    ImageView iv_choose;
    int selectionPosition = -1;
    public HisAddressAdapter(int layoutResId, @Nullable List<HisModel.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HisModel.DataBean item) {
        TextView tv_address = helper.getView(R.id.tv_address);
        iv_choose = helper.getView(R.id.iv_choose);
        tv_address.setText(item.getDetailAddress());
        if(helper.getAdapterPosition()==selectionPosition) {
            iv_choose.setBackgroundResource(R.mipmap.checkbax_yes);
        }else {
            iv_choose.setBackgroundResource(R.mipmap.checkbox_no);
        }
    }

    public void setNotify(int selectionPosition) {
       this.selectionPosition = selectionPosition;
    }
}
