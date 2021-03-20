package com.puyue.www.qiaoge.fragment.market;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.home.CommonGoodsDetailActivity;
import com.puyue.www.qiaoge.activity.home.SpecialGoodDetailActivity;

import com.puyue.www.qiaoge.adapter.FullGiftAdapter;
import com.puyue.www.qiaoge.adapter.GivensAdapter;
import com.puyue.www.qiaoge.adapter.cart.CartAdapter;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.fragment.cart.CartFragment;
import com.puyue.www.qiaoge.fragment.cart.UpdateEvent;
import com.puyue.www.qiaoge.fragment.cart.UpdateOperateEvent;
import com.puyue.www.qiaoge.fragment.cart.UpdateUnOperateEvent;
import com.puyue.www.qiaoge.model.FullModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.model.mine.CartCheckModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.view.Arith;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends BaseQuickAdapter<CartsListModel.DataBean.ValidListBean,BaseViewHolder> {
    private RecyclerView recyclerView;
    public List<CartsListModel.DataBean.ValidListBean> data;
    public List<CartsListModel.DataBean.ValidListBean> data1 = new ArrayList<>();
    private IProductSelectCallback iProductSelectCallback;
    private OnResfreshListener mOnResfreshListener;
    private RecyclerView rv_given;
    List<CartCheckModel> list = new ArrayList<>();
    private Onclick onclick;
    RecyclerView rv_full;
    List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList>additionVOList1 = new ArrayList<>();
    List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList>additionVOList2 = new ArrayList<>();
    List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionProductVOList> additionProductVOList = new ArrayList<>();
    int cartId;
    List<CartsListModel.DataBean.ValidListBean> mListCart;
    View v1;
    public TestAdapter(int layoutResId, @Nullable List<CartsListModel.DataBean.ValidListBean> data, List<CartsListModel.DataBean.ValidListBean> mListCart,IProductSelectCallback iProductSelectCallback,Onclick onclick) {
        super(layoutResId, data);
        this.data = data;
        this.iProductSelectCallback = iProductSelectCallback;
        this.onclick = onclick;
        this.mListCart = mListCart;
    }

    @Override
    protected void convert(BaseViewHolder helper, CartsListModel.DataBean.ValidListBean item) {
        CheckBox cb_choose = helper.getView(R.id.cb_choose);
        TextView tv_big_title =  helper.getView(R.id.tv_big_title);
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        v1 = helper.getView(R.id.v1);
        rv_full = helper.getView(R.id.rv_full);
        TextView tv_full_desc = helper.getView(R.id.tv_full_desc);
        TextView tv_delete = helper.getView(R.id.tv_delete);
        LinearLayout ll_cart = helper.getView(R.id.ll_cart);
        recyclerView = helper.getView(R.id.recyclerView);
        rv_given = helper.getView(R.id.rv_given);
        CheckBox cb_item_out = helper.getView(R.id.cb_item_out);
        ImageView iv_head = helper.getView(R.id.iv_head);
//        tv_big_title.setText(item.getSendTimeStr());

        additionVOList1 = new ArrayList<>();
        additionVOList2 = new ArrayList<>();
        additionProductVOList = new ArrayList<>();
        for (int i = 0; i < item.getSpecProductList().size(); i++) {
            cartId = item.getSpecProductList().get(i).getCartId();
            if(item.getSpecProductList().get(i).getProductDescVOList()!=null) {
                additionProductVOList = item.getSpecProductList().get(i).getAdditionProductVOList();
            }
            if(item.getSpecProductList().get(i).getAdditionVOList()!=null) {
                List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.AdditionVOList> additionVOList = item.getSpecProductList().get(i).getAdditionVOList();
                for (int j = 0; j <additionVOList.size(); j++) {
                    //优惠券
                    if(additionVOList.get(j).getType().equals("1")) {
                        additionVOList1.add(additionVOList.get(j));
                    }else {
                        additionVOList2.add(additionVOList.get(j));
                    }
                }
                v1.setVisibility(View.VISIBLE);
            }else {
                v1.setVisibility(View.GONE);
            }
        }


        for (int i = 0; i < item.getSpecProductList().size(); i++) {
            if(item.getSpecProductList().get(i).getBuySendAdditionInfo()!=null) {
                if(item.getSpecProductList().get(i).getBuySendAdditionInfo().equals("")) {
                    tv_full_desc.setVisibility(View.GONE);
                    v1.setVisibility(View.GONE);
                }else {
                    tv_full_desc.setText(item.getSpecProductList().get(i).getBuySendAdditionInfo());
                    tv_full_desc.setVisibility(View.VISIBLE);
                    v1.setVisibility(View.VISIBLE);
                }
            }else {
                tv_full_desc.setVisibility(View.GONE);
                v1.setVisibility(View.GONE);
            }
        }

        rv_full.setLayoutManager(new LinearLayoutManager(mContext));
        FullGiftAdapter fullGiftAdapter = new FullGiftAdapter(R.layout.item_full,additionVOList1);
        rv_full.setAdapter(fullGiftAdapter);
        Glide.with(mContext).load(item.getFlagUrl()).into(iv_icon);

        //满赠赠品
        rv_given.setLayoutManager(new LinearLayoutManager(mContext));
        GivenAdapter givenAdapter = new GivenAdapter(R.layout.item_given,additionVOList2);
        rv_given.setAdapter(givenAdapter);
        helper.setText(R.id.tv_title,item.getProductName());
        Glide.with(mContext).load(item.getDefaultPic()).into(iv_head);


        ll_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新品
                if(item.getBusinessType()==1) {
                    Intent intent = new Intent(mContext,CommonGoodsDetailActivity.class);
                    intent.putExtra(AppConstant.ACTIVEID, item.getProductMainId());
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    mContext.startActivity(intent);
                }else if(item.getBusinessType()==2) {
                    //活动详情页,秒杀详情
                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    intent.putExtra("num","-1");
                    intent.putExtra(AppConstant.ACTIVEID, item.getBusinessId());
                    mContext.startActivity(intent);
                }else if (item.getBusinessType() == 3) {
//                        //活动,团购详情
                    Intent intent = new Intent(mContext, SpecialGoodDetailActivity.class);
                    intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                    intent.putExtra(AppConstant.ACTIVEID, item.getBusinessId());
                    mContext.startActivity(intent);

                }else if (item.getBusinessType() == 11) {
                        Intent intent = new Intent(mContext, SpecialGoodDetailActivity.class);
                        intent.putExtra(AppConstant.ACTIVEID, item.getBusinessId());
                        intent.putExtra("priceType", SharedPreferencesUtil.getString(mContext,"priceType"));
                        mContext.startActivity(intent);
                    }
            }
        });


        CartSpecAdapter cartSpecAdapter = new CartSpecAdapter(iProductSelectCallback,data,R.layout.item_cart_spec, item.getSpecProductList(),item,
                this,item.getBusinessType(),item.getBusinessId());
        cb_item_out.setOnCheckedChangeListener(null);
//        cb_choose.setOnCheckedChangeListener(null);
        cb_item_out.setChecked(item.isSelected());
//        cb_choose.setChecked(item.isSelected());
        if(mOnResfreshListener != null){
            boolean isSelect = false;

            for(int i = 0;i < data.size(); i++){
                if(!data.get(i).isSelected()){
                    isSelect = false;
                    break;
                }else{
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
            iProductSelectCallback.update1(data);
        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onclick!=null) {
                    onclick.deteItem(helper.getAdapterPosition(),item);
                }
            }
        });

//        cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                cb_item_out.setChecked(isChecked);
//
//                for (CartsListModel.DataBean.ValidListBean validListBean: data) {
//                    validListBean.setSelected(isChecked);
//                    notifyDataSetChanged();
//                }
//
//                for (CartsListModel.DataBean.ValidListBean.SpecProductListBean specProductList: item.getSpecProductList()) {
//                    specProductList.setSelected(isChecked);
//                    notifyDataSetChanged();
//                }
//
//                EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
//                iProductSelectCallback.update(mListCart);
//            }
//        });

        cb_item_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isPressed()) {
                    item.setSelected(isChecked);
                    for (CartsListModel.DataBean.ValidListBean.SpecProductListBean specProductList: item.getSpecProductList()) {
                        specProductList.setSelected(isChecked);
                        notifyDataSetChanged();
                    }

                    for (CartsListModel.DataBean.ValidListBean validListBean: data1) {
                        validListBean.setSelected(isChecked);
                        notifyDataSetChanged();
                    }
                }

                EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
                iProductSelectCallback.update(mListCart);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(cartSpecAdapter);
    }

    /**
     * 计算商品总价格
     * @return
     */
    public String getAllPrice() {
        BigDecimal allprice =new BigDecimal("0");
        if(mListCart!=null){
            for (int i=0;i<mListCart.size();i++){

                List<CartsListModel.DataBean.ValidListBean.SpecProductListBean> specProductList = mListCart.get(i).getSpecProductList();
                for (int y=0;y<specProductList.size();y++){
                    if(specProductList.get(y).isSelected()){
                        List<CartsListModel.DataBean.ValidListBean.SpecProductListBean.ProductDescVOListBean> productDescVOList = specProductList.get(y).getProductDescVOList();
                        for (int j = 0; j <productDescVOList.size() ; j++) {
                            BigDecimal interestRate = new BigDecimal(productDescVOList.get(j).getProductNum()); //数量
                            double interest = Arith.mul(Double.parseDouble(productDescVOList.get(j).getPrice()), interestRate);
                            allprice=allprice.add(BigDecimal.valueOf(interest));
                        }
                    }
                }
            }
        }
        return allprice.toString();
    }

    /**
     * 设置全选和全不选
     * @param b
     */
    public void setAllselect(boolean b){

        for(int i=0;i<data.size();i++){
            data.get(i).setSelected(b);
            for (CartsListModel.DataBean.ValidListBean.SpecProductListBean specProductList : data.get(i).getSpecProductList()){
                specProductList.setSelected(b);
            }
        }

        notifyDataSetChanged();
        //发送 消息
        iProductSelectCallback.update(data);
        EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
    }

    /**
     * 设置自营的全选与否
     */
    public void setOperateSelect(boolean b,List<CartsListModel.DataBean.ValidListBean> data1){
        for(int i=0;i<data1.size();i++){
            data1.get(i).setSelected(b);
            for (CartsListModel.DataBean.ValidListBean.SpecProductListBean specProductList : data1.get(i).getSpecProductList()){
                specProductList.setSelected(b);
            }
        }

        notifyDataSetChanged();
        //发送 消息
//        iProductSelectCallback.update(data1);
//        EventBus.getDefault().post(new UpdateOperateEvent(getOperatePrice()));
        EventBus.getDefault().post(new UpdateEvent(getAllPrice()));
    }



    public interface IProductSelectCallback {
        void update(List<CartsListModel.DataBean.ValidListBean> data);
        void update1(List<CartsListModel.DataBean.ValidListBean> data);
    }


    public interface OnResfreshListener{
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener){
        this.mOnResfreshListener = mOnResfreshListener;
    }

    public interface Onclick {

//        void deteItem(int pos,int id);
        void deteItem(int pos,CartsListModel.DataBean.ValidListBean validListBean);
    }

}
