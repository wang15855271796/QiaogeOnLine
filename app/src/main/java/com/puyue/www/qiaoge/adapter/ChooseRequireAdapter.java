package com.puyue.www.qiaoge.adapter;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.pictureselectordemo.ShopImageViewAdapter;

import java.util.List;

public class ChooseRequireAdapter extends BaseQuickAdapter<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem, BaseViewHolder> {

    public ChooseRequireAdapter(int layoutResId, @Nullable List<CarStyleModel.DataBean.VehicleListBean.VehicleStdItem> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarStyleModel.DataBean.VehicleListBean.VehicleStdItem item) {
        helper.setText(R.id.tv_req,item.getName()+"("+item.getDesc()+")");
        CheckBox checkbox = helper.getView(R.id.checkbox);
        Log.d("csdfwefsdf.....",item.getName()+"ss");
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mItemClickListener.onItemClick(helper.getAdapterPosition(),isChecked);
            }
        });
        checkbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }


    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, boolean hasFocus);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
