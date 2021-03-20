package com.puyue.www.qiaoge.adapter.mine;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.adapter.market.MarketGoodBrandAdapter;
import com.puyue.www.qiaoge.api.home.GetAllCommentListByPageAPI;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.GlideRoundTransform;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.CartBalanceModel;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.model.home.GetAllCommentListByPageModel;
import com.puyue.www.qiaoge.model.home.JumpModel;
import com.puyue.www.qiaoge.model.mine.order.NewOrderDetailModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.GlideModel;
import com.puyue.www.qiaoge.view.LineBreakLayout;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${daff}
 * on 2018/10/20
 * 备注 改版后的 订单详情
 */
public class NewOrderDetailAdapter extends BaseQuickAdapter<GetOrderDetailModel.DataBean.OrderProdsBean, BaseViewHolder> {

    String orderId;
    RecyclerView rv1;
    TextView tv_title;
    TextView tv_time;
    TextView tv_num;
    RelativeLayout rl;
    TextView tv_copy;
    Activity mActivity;
    ImageView iv_operate_pic;
    public NewOrderDetailAdapter(Activity mActivity,int layoutResId, @Nullable List<GetOrderDetailModel.DataBean.OrderProdsBean> data, String orderId) {
        super(layoutResId, data);
        this.orderId = orderId;
        this.mActivity = mActivity;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderDetailModel.DataBean.OrderProdsBean item) {
        iv_operate_pic = helper.getView(R.id.iv_operate_pic);
        tv_title = helper.getView(R.id.tv_title);
        tv_time = helper.getView(R.id.tv_time);
        tv_num = helper.getView(R.id.tv_num);
        rv1 = helper.getView(R.id.rv1);
        rl = helper.getView(R.id.rl);
        tv_copy = helper.getView(R.id.tv_copy);
        tv_num.setText("订单编号:"+item.orderId);
        tv_title.setText(item.title);
        tv_time.setText(item.sendTimeStr);
        Glide.with(mContext).load(item.sendTimeTpl).into(iv_operate_pic);
        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm1 = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData1 = ClipData.newPlainText("Label", item.orderId);
                // 将ClipData内容放到系统剪贴板里。
                cm1.setPrimaryClip(mClipData1);
                AppHelper.showMsg(mContext, "复制成功");
            }
        });

        rv1.setLayoutManager(new LinearLayoutManager(mContext));
        OrderAdapter orderAdapter = new OrderAdapter(R.layout.item_order_good, item.products,item.orderId);
        rv1.setAdapter(orderAdapter);

    }



}