package com.puyue.www.qiaoge.adapter;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.event.AccountEvent;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.List;

public class MyAccountAdapter extends BaseQuickAdapter<HuoDetailModel.DataBean.BillAppealBean, BaseViewHolder> {

    String beforeText;
    public MyAccountAdapter(int layoutResId, @Nullable List<HuoDetailModel.DataBean.BillAppealBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HuoDetailModel.DataBean.BillAppealBean item) {
        ImageView checkbox = helper.getView(R.id.checkbox);
        TextView tv_name = helper.getView(R.id.tv_name);
        EditText editText = helper.getView(R.id.et_money);

        if (editText.getTag() instanceof TextWatcher){
            editText.removeTextChangedListener((TextWatcher) editText.getTag());
        }
        if (!TextUtils.isEmpty(item.getNum())){
            editText.setText(item.getNum()+"");
        }else {
            editText.setHint("请输入您期望的数值");
            checkbox.setImageResource(R.mipmap.icon_un_choose);
        }

        tv_name.setText(item.getBilTypeName()+"("+item.getAmount()+")");
        if (item.isCheck()){
            checkbox.setImageResource(R.mipmap.icon_choose);
        }else {
            checkbox.setImageResource(R.mipmap.icon_un_choose);
        }

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")) {
                    ToastUtil.showSuccessMsg(mContext,"请填写金额");
                    return;
                }

             if (item.isCheck()){
                 item.setCheck(false);
                 checkbox.setImageResource(R.mipmap.icon_un_choose);
             }else {
                 item.setCheck(true);
                 checkbox.setImageResource(R.mipmap.icon_choose);
             }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence arg0, int start, int count, int after) {
                if (!TextUtils.isEmpty(arg0.toString())
                        && !arg0.toString().equals(beforeText)
                        && !arg0.toString().equals(".")) {
                }
            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                beforeText = arg0.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkbox.setImageResource(R.mipmap.icon_un_choose);
                item.setCheck(false);
                String editStr = editable.toString().trim();
                item.setNum(editText.getText().toString());
                int posDot = editStr.indexOf(".");
                if (posDot < 0) {
                    return;
                }else if (posDot == 0) {
                    //首位是点，去掉点
                    editable.delete(posDot, posDot + 1);
                }
                //不允许输入3位小数,超过三位就删掉
                if (editStr.length() - posDot - 1 > 2) {
                    editable.delete(posDot + 3, posDot + 4);
                }
                //这边具体的判断逻辑我就不写了就是把符合的金额赋值给num就行了

            }
        };


        editText.addTextChangedListener(textWatcher);
        editText.setTag(textWatcher);

    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, boolean hasFocus,double amount);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
