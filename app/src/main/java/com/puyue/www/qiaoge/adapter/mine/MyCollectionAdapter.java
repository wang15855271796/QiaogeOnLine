package com.puyue.www.qiaoge.adapter.mine;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.dialog.CollectionDialog;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.view.GlideModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/10.
 */

public class MyCollectionAdapter extends BaseQuickAdapter<ProductNormalModel.DataBean.ListBean, BaseViewHolder> {
    public Map<Integer, Boolean> isCheck;
    private OnEventClickListener mOnEventClickListener;
    boolean isShow;
    RelativeLayout rl_item_collection_data;
    Onclick onclick;
    CollectionDialog searchDialog;
    ImageView iv_sold_out;
    public MyCollectionAdapter(int layoutResId, @Nullable List<ProductNormalModel.DataBean.ListBean> data, Map<Integer, Boolean> isCheck,Onclick onclick) {
        super(layoutResId, data);
        this.isCheck = isCheck;
        this.onclick = onclick;
    }

    public interface Onclick {
        void addDialog();
        void getPrice();
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public interface OnEventClickListener {
        void onEventClick(View view, int position, String type);

        void onEventLongClick(View view, int position, String type);
    }

    public void setOnItemClickListener(OnEventClickListener onEventClickListener) {
        mOnEventClickListener = onEventClickListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, ProductNormalModel.DataBean.ListBean item) {
        TextView tv_price_desc = helper.getView(R.id.tv_price_desc);
        RelativeLayout rl_spec = helper.getView(R.id.rl_spec);
        ImageView iv_operate = helper.getView(R.id.iv_operate);
        ImageView iv_fresh_price = helper.getView(R.id.iv_fresh_price);
        RelativeLayout rl_price = helper.getView(R.id.rl_price);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        iv_sold_out = helper.getView(R.id.iv_sold_out);
        rl_item_collection_data = helper.getView(R.id.rl_item_collection_data);

        if(item.getFreshPriceFlag() == 1) {
            iv_fresh_price.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(R.mipmap.ic_refresh_price);
        }else {
            iv_fresh_price.setVisibility(View.GONE);
        }


        if (StringHelper.notEmptyAndNull(item.getDefaultPic())) {
            GlideModel.displayTransForms(mContext,item.getDefaultPic(),helper.getView(R.id.iv_item_my_collection_img));
        }
        helper.setChecked(R.id.cb_item_my_collection, isCheck.get(helper.getAdapterPosition()));
        helper.setText(R.id.tv_item_my_collection_title, item.getProductName());
        helper.setText(R.id.tv_item_my_collection_price, item.getMinMaxPrice());
        helper.setText(R.id.tv_item_my_collection_volume, item.getSalesVolume());
        TextView tv_price = helper.getView(R.id.tv_item_my_collection_price);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        CheckBox cb_item_my_collection = helper.getView(R.id.cb_item_my_collection);
        if(isShow) {
            cb_item_my_collection.setVisibility(View.VISIBLE);
            rl_item_collection_data.setEnabled(false);
            rl_item_collection_data.setClickable(false);
        }else {
            cb_item_my_collection.setVisibility(View.GONE);
            rl_item_collection_data.setEnabled(true);
            rl_item_collection_data.setClickable(true);
        }

        ImageView iv_send = helper.getView(R.id.iv_send);

        if(item.getNotSend()!=null) {
            if(item.getNotSend().equals("1")||item.getNotSend().equals("1.0")) {
                iv_send.setImageResource(R.mipmap.icon_not_send2);
                iv_send.setVisibility(View.VISIBLE);
            }else {
                iv_send.setVisibility(View.GONE);
            }
        }

        if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            if(SharedPreferencesUtil.getString(mContext,"priceType").equals("1")) {
                tv_price.setVisibility(View.VISIBLE);
                tv_desc.setVisibility(View.GONE);
                rl_spec.setVisibility(View.VISIBLE);
                rl_price.setVisibility(View.GONE);
            }else {
                rl_price.setVisibility(View.VISIBLE);
                rl_spec.setVisibility(View.GONE);
                tv_price.setVisibility(View.GONE);
                tv_desc.setVisibility(View.VISIBLE);
            }
        }else {
            tv_price.setVisibility(View.VISIBLE);
            tv_desc.setVisibility(View.GONE);
        }

        if (item.getFlag()==0){
            Glide.with(mContext).load(item.getTypeUrl()).into(iv_sold_out);
            iv_sold_out.setVisibility(View.VISIBLE);
            rl_spec.setVisibility(View.GONE);
//            rl_price.setVisibility(View.GONE);

        }else if (item.getFlag()==1){
            iv_sold_out.setVisibility(View.GONE);
            rl_spec.setVisibility(View.VISIBLE);
//            rl_price.setVisibility(View.VISIBLE);
        }

        rl_spec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.addDialog();
                }

                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    searchDialog = new CollectionDialog(mContext,item);
                    searchDialog.show();
                }
            }
        });

        rl_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    onclick.getPrice();
                }
            }
        });
        ((FrameLayout) helper.getView(R.id.fl_item_collection_check)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "check");
            }
        });

        ((RelativeLayout) helper.getView(R.id.rl_item_collection_data)).setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                mOnEventClickListener.onEventClick(view, helper.getAdapterPosition(), "jump");

            }
        });
    }
}
