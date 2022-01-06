package com.puyue.www.qiaoge.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CarStyleModel;

import java.util.List;

public class HuoOtherAdapter extends BaseQuickAdapter<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem, BaseViewHolder> {

    public HuoOtherAdapter(int layoutResId, @Nullable List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarStyleModel.DataBean.VehicleListBean.VehicleStdItem item) {
        helper.setText(R.id.tv_title,item.getName()+"("+item.getDesc()+")");
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    cb_choose.setChecked(true);
                }else {
                    cb_choose.setChecked(false);
                }
            }
        });
    }
}
