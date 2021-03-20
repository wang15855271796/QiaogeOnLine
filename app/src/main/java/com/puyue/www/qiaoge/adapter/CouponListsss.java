package com.puyue.www.qiaoge.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.MoneyInputFilter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.dialog.ExCouponDialog;
import com.puyue.www.qiaoge.dialog.ExCouponsDialog;
import com.puyue.www.qiaoge.helper.BigDecimalUtils;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.model.cart.ItemModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by ${王涛} on 2020/8/10
 */
public class CouponListsss extends BaseQuickAdapter<ItemModel,BaseViewHolder> {
    private EditText et;
    List<ItemModel> data;
    ImageView iv_clear;
    Activity mActivity;
    private BigDecimal bigDecimal;
    Double currentTotal = 0.0;
    Double total = 0.0;
    public CouponListsss(int layoutResId, @Nullable List<ItemModel> data, Activity mActivity) {
        super(layoutResId, data);
        this.data = data;
        this.mActivity = mActivity;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemModel item) {
        et = helper.getView(R.id.et);
        iv_clear = helper.getView(R.id.iv_clear);
        helper.addOnClickListener(R.id.iv_clear);
        setRole();
        if (helper.getAdapterPosition() == 0) {
            iv_clear.setVisibility(View.GONE);
        } else {
            iv_clear.setVisibility(View.VISIBLE);
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData().get(helper.getAdapterPosition()).setNum(String.valueOf(s));
                if (onArticleClickListener != null) {
                    onArticleClickListener.onArticleOnItemClick(et);
                }
            }
        };
        et.addTextChangedListener(textWatcher);

        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    et.addTextChangedListener(textWatcher);
                } else {
                    et.removeTextChangedListener(textWatcher);
                }
            }
        });

        if (et instanceof TextWatcher) {
            et.removeTextChangedListener(((TextWatcher) et.getTag(R.id.et)));
        }
        //移除了TextWatcher事件后设置item对应的文本
        et.setText(item.getNum());

        //设置焦点
//        if (item.isFocus()) {
//            if (!et.isFocused()) {
//                et.requestFocus();
//            }
//            CharSequence text = item.getNum();
//            et.setSelection(TextUtils.isEmpty(text) ? 0 : text.length());
//        } else {
//            if (et.isFocused()) {
//                et.clearFocus();
//            }
//        }

    }


    BigDecimal finalTotal3= new BigDecimal("0.00");
    Double total1 = 0.0;
    public void setOnclick(String amount) {
        //判断数据是否为空
        total1 = Double.valueOf(amount);
        Double currentTotal1 = 0.0;
        BigDecimal finalTotal4= new BigDecimal("0.00");
        for (int i = 0; i < data.size(); i++) {
            String num1 = data.get(i).getNum();
            if(num1.equals("")||num1.equals("0")||num1.equals("0.0")||num1.equals("0.00")||num1.equals("0.")) {
                ToastUtil.showSuccessMsg(mContext,"请填写金额");
                return;
            }else {
                currentTotal1 +=Double.parseDouble(num1);
                finalTotal3 = new BigDecimal(num1);
                finalTotal4 = finalTotal4.add(finalTotal3);
            }
        }

        if(finalTotal4.toString().equals(amount)) {
            ToastUtil.showSuccessMsg(mActivity,"余额已兑换完毕");
            return;
        }

        if(BigDecimalUtils.sub(currentTotal1,total1)>0.00) {
            ToastUtil.showSuccessMsg(mActivity,"超出兑换金额");
            return;
        }
//        new ItemModel("").setFocus(true);
        if(data.size()<10) {
            data.add(new ItemModel(""));
            //添加动画
            notifyItemInserted(data.size());
        }else {
            ToastUtil.showSuccessMsg(mContext,"单次最多兑换10张");
        }
    }

    /**
     * 一键兑换
     * @param amount
     */
    BigDecimal finalTotal1= new BigDecimal("0.00");
    public void setCoupon(String amount) {
        BigDecimal finalTotal2 = new BigDecimal("0.00");
        //删除条目之后再点击一键兑换，重置currentTotal
        currentTotal = 0.0;
        total = Double.valueOf(amount);

        //条目内容为空
        if(data.size()==1) {
            et.setText(total-currentTotal+"");
        }else {
            for (int i = 0; i < data.size()-1; i++) {
                String num = data.get(i).getNum();
                currentTotal+=Double.valueOf(num);
                et.setText(total-currentTotal+"");
            }

            for (int i = 0; i < data.size(); i++) {
                String num = data.get(i).getNum();
                if(!num.equals("")||!num.equals("0")||!num.equals("0.0")||!num.equals("0.00")||!num.equals("0.")) {
                    finalTotal1 = new BigDecimal(data.get(i).getNum());
                    finalTotal2 = finalTotal2.add(finalTotal1);

                }
            }
            //判断总条目金额是否达到上线，true则给提示
            if(finalTotal2.toString().equals(amount)) {
                ToastUtil.showSuccessMsg(mActivity,"余额已兑换完毕");
                return;
            }
            if(BigDecimalUtils.sub(currentTotal,total)>0.00) {
                ToastUtil.showSuccessMsg(mActivity,"超出兑换金额");
                return;
            }
        }

    }

    /**
     * 设置规格
     */
    public void setRole() {
        MoneyInputFilter filter = new MoneyInputFilter();
        filter.setDecimalLength(2);//保留小数点后2位
        InputFilter[] filters = {filter};
        et.setFilters(filters);
    }

    /**
     * 确认弹出弹窗
     */

    BigDecimal finalTotal5= new BigDecimal("0.00");
    public void getListData() {
        BigDecimal finalTotal6= new BigDecimal("0.00");
        Double currentTotal2 = 0.0;
        BigDecimal finalTotal = new BigDecimal("0.00");
         //判断数据是否为空
        for (int i = 0; i < data.size(); i++) {
            String num1 = data.get(i).getNum();
            if(num1.equals("")||num1.equals("0")||num1.equals("0.0")||num1.equals("0.00")||num1.equals("0.")) {
                ToastUtil.showSuccessMsg(mContext,"请填写金额");
                return;
            }else {
                currentTotal2 +=Double.parseDouble(num1);
                finalTotal5 = new BigDecimal(num1);
                finalTotal6 = finalTotal6.add(finalTotal5);
            }
        }
//        Log.d("waaaaaaassssss...",currentTotal2+"aa");
//        Log.d("waaaaaaassssss...",total1+"bb");
        if(BigDecimalUtils.sub(currentTotal2,total1)>0.00) {
            ToastUtil.showSuccessMsg(mActivity,"超出兑换金额");
            return;
        }



        //计算总金额传递到弹窗显示
        for (ItemModel itemModel : data) {
            bigDecimal = new BigDecimal(itemModel.getNum());
            finalTotal = finalTotal.add(bigDecimal);
        }

        ExCouponsDialog exCouponDialog = new ExCouponsDialog(mActivity,data,finalTotal);
        exCouponDialog.show();
    }

    public interface OnArticleClickListener {
        void onArticleOnItemClick(EditText et);
    }

    private CouponListssAdapter.OnArticleClickListener onArticleClickListener;

    public void setOnArticleClickListener(CouponListssAdapter.OnArticleClickListener articleClickListener) {
        this.onArticleClickListener = articleClickListener;
    }

}
