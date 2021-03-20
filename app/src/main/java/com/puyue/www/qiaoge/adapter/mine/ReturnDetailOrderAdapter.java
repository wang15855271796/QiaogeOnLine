package com.puyue.www.qiaoge.adapter.mine;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.puyue.www.qiaoge.adapter.ReturnFullAdapter;
import com.puyue.www.qiaoge.adapter.ReturnGivenAdapter;
import com.puyue.www.qiaoge.api.home.GetAllCommentListByPageAPI;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.model.home.JumpModel;
import com.puyue.www.qiaoge.model.mine.order.NewReturnOrderModel;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王文博} on 2019/8/28
 */
public class ReturnDetailOrderAdapter extends BaseQuickAdapter<NewReturnOrderModel.DataBean.ProductsBean, BaseViewHolder> {

    private TextView tv_return_num;
    private TextView tv_title;
    private ImageView iv_food;
    private ImageView iv_flag;
    private RelativeLayout rl_good;
    private int returnState;
    String orderId;
    RecyclerView rv_given;
    RecyclerView rv_full;
    List<NewReturnOrderModel.DataBean.ProductsBean> data;
    List<NewReturnOrderModel.DataBean.ProductsBean.AdditionList> data1 = new ArrayList<>();
    List<NewReturnOrderModel.DataBean.ProductsBean.AdditionList> data2 = new ArrayList<>();
    ReturnGivenAdapter returnGivenAdapter;
    ReturnFullAdapter returnFullAdapter;
    LinearLayout ll;
    TextView tv_return_amount;
    public ReturnDetailOrderAdapter(int layoutResId, @Nullable List<NewReturnOrderModel.DataBean.ProductsBean> data, int returnState, String orderId) {
        super(layoutResId, data);
        this.returnState = returnState;
        this.orderId = orderId;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewReturnOrderModel.DataBean.ProductsBean item) {
        ll = helper.getView(R.id.ll);
        ll.setVisibility(View.GONE);
        tv_return_amount = helper.getView(R.id.tv_return_amount);
        tv_return_num = helper.getView(R.id.tv_return_num);
        tv_title = helper.getView(R.id.tv_title);
        iv_food = helper.getView(R.id.iv_food);
        iv_flag = helper.getView(R.id.iv_flag);
        rl_good = helper.getView(R.id.rl_good);
        rv_given = helper.getView(R.id.rv_given);
        rv_full = helper.getView(R.id.rv_full);

        tv_return_num.setText("退货数量:" + item.getReturnNum());
        tv_title.setText(item.getProductName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_food);
        tv_return_amount.setText("￥"+item.getReturnTotalAmount());
        data1 = new ArrayList<>();
        data2 = new ArrayList<>();

        if(item.getAdditionList()!=null) {
            for (int i = 0; i < item.getAdditionList().size(); i++) {
                List<NewReturnOrderModel.DataBean.ProductsBean.AdditionList> additionList = item.getAdditionList();
                if(additionList.get(i).getType().equals("1")) {
                    //优惠券
                    data1.add(additionList.get(i));
                    rv_full.setLayoutManager(new LinearLayoutManager(mContext));
                    returnFullAdapter = new ReturnFullAdapter(R.layout.item_full, data1);
                    rv_full.setAdapter(returnFullAdapter);
                }else {
                    data2.add(additionList.get(i));
                    returnGivenAdapter = new ReturnGivenAdapter(R.layout.item_given, data2);
                    rv_given.setAdapter(returnGivenAdapter);
                    rv_given.setLayoutManager(new LinearLayoutManager(mContext));
                }
            }
        }

        if (item.getTypeImg() != null && StringHelper.notEmptyAndNull(item.getTypeImg())) {
            iv_flag.setVisibility(View.GONE);
            Glide.with(mContext).load(item.getTypeImg()).into(iv_flag);
        } else {
            iv_flag.setVisibility(View.GONE);

        }

        rl_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getBusinessType() == 1) {
                    jumpDetail(orderId, item.productMainId, item.getBusinessType(),item);
                }else {
                    jumpDetail(orderId, item.getBusinessId(), item.getBusinessType(),item);
                }
            }
        });


    }

    /**
     * 跳转详情
     */
    private void jumpDetail(String orderId, int businessId, int businessType, NewReturnOrderModel.DataBean.ProductsBean item) {
        GetAllCommentListByPageAPI.jumpDetail(mContext, orderId, businessId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JumpModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(JumpModel jumpModel) {
                        if(jumpModel.isSuccess()) {
                            if(jumpModel.getData()!=null) {
                                if(jumpModel.getData().equals("-1")) {
                                    Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,item.productMainId);
                                    intent.putExtra("num",jumpModel.getData());
                                    mContext.startActivity(intent);
                                }else {
                                    Intent intent = new Intent(mContext, CommonGoodsDetailActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID,item.productMainId);
                                    intent.putExtra("num",jumpModel.getData());
                                    intent.putExtra("city",jumpModel.getMessage());
                                    mContext.startActivity(intent);
                                }
                            }else {
                                AppHelper.showMsg(mContext,jumpModel.getMessage());
                            }
                        }
                    }
                });
    }
}
