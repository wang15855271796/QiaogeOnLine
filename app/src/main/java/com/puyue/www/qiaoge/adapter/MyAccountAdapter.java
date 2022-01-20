package com.puyue.www.qiaoge.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.model.HuoDetailModel;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyAccountAdapter extends BaseQuickAdapter<HuoDetailModel.DataBean.BillAppealBean, BaseViewHolder> {

    public MyAccountAdapter(int layoutResId, @Nullable List<HuoDetailModel.DataBean.BillAppealBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoDetailModel.DataBean.BillAppealBean item) {
        CheckBox checkbox = helper.getView(R.id.checkbox);
        TextView tv_name = helper.getView(R.id.tv_name);
        EditText editText = helper.getView(R.id.et_money);

        tv_name.setText(item.getBilTypeName()+"("+item.getAmount()+")");

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(editText.getText().toString().equals("")) {
                    mItemClickListener.onItemClick(helper.getAdapterPosition(),isChecked,Double.parseDouble("0.0"));
                }else {
                    mItemClickListener.onItemClick(helper.getAdapterPosition(),isChecked,Double.parseDouble(editText.getText().toString()));
                }

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, boolean hasFocus,double amount);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
