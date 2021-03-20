package com.puyue.www.qiaoge.adapter.mine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.market.MarketGoodsAdapter;
import com.puyue.www.qiaoge.dialog.CollectionDialog;
import com.puyue.www.qiaoge.dialog.CouponSearchDialog;
import com.puyue.www.qiaoge.helper.GlideRoundTransform;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.collection.CollectionListModel;
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
        RelativeLayout rl_spec = helper.getView(R.id.rl_spec);
        ImageView iv_operate = helper.getView(R.id.iv_operate);
        RelativeLayout rl_price = helper.getView(R.id.rl_price);
        Glide.with(mContext).load(item.getSelfProd()).into(iv_operate);
        iv_sold_out = helper.getView(R.id.iv_sold_out);
        rl_item_collection_data = helper.getView(R.id.rl_item_collection_data);
        if (StringHelper.notEmptyAndNull(item.getDefaultPic())) {
            GlideModel.displayTransForms(mContext,item.getDefaultPic(),helper.getView(R.id.iv_item_my_collection_img));
        }
        helper.setChecked(R.id.cb_item_my_collection, isCheck.get(helper.getAdapterPosition()));
        helper.setText(R.id.tv_item_my_collection_title, item.getProductName());
        helper.setText(R.id.tv_item_my_collection_price, item.getMinMaxPrice());
        helper.setText(R.id.tv_item_my_collection_volume, item.getSalesVolume());

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
