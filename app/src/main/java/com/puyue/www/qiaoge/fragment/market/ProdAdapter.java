package com.puyue.www.qiaoge.fragment.market;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.market.MarketRightModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2019/10/28
 */
public class ProdAdapter extends BaseQuickAdapter<MarketRightModel.DataBean.BrandProdBean.ListBeanX,BaseViewHolder> {

    private ProdInnerAdapter prodInnerAdapter;
    boolean open = false;
    List<MarketRightModel.DataBean.BrandProdBean.ListBeanX> data;
    public ProdAdapter(int layoutResId, @Nullable List<MarketRightModel.DataBean.BrandProdBean.ListBeanX> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketRightModel.DataBean.BrandProdBean.ListBeanX item) {
        helper.setText(R.id.tv_titlt,item.getBrandName());
        RelativeLayout rl_prod = helper.getView(R.id.rl_prod);
        RelativeLayout rl = helper.getView(R.id.rl);
        rl.setVisibility(View.GONE);
        List<MarketRightModel.DataBean.BrandProdBean.ListBeanX.ProdClassifyBean.ListBean> list = item.getProdClassify().getList();
        TextView tv_expand = helper.getView(R.id.tv_expand);
        RecyclerView recyclerViewProd = helper.getView(R.id.recyclerViewProd);
        tv_expand.setText("展开");
        ViewGroup.LayoutParams layoutParams = rl_prod.getLayoutParams();

        ViewGroup.LayoutParams lp = recyclerViewProd.getLayoutParams();
        lp.height = DensityUtil.dip2px(150 * 1,mContext);
        recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));

        prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
        recyclerViewProd.setAdapter(prodInnerAdapter);

        if(data.size()>3) {
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        }else {
            if(data.size()==1) {
                layoutParams.height = DensityUtil.dip2px(450 * 1,mContext);
            }else {
                layoutParams.height = DensityUtil.dip2px(225 * 1,mContext);
            }
        }
        if(list.size()>3 ) {
            tv_expand.setVisibility(View.VISIBLE);
            helper.getView(R.id.rl).setVisibility(View.VISIBLE);

        }else {
            tv_expand.setVisibility(View.GONE);
            helper.getView(R.id.rl).setVisibility(View.GONE);
        }

        rl_prod.setLayoutParams(layoutParams);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() > 3) {
                    if(open) {
                        lp.height = DensityUtil.dip2px(150 * 1,mContext);

                        open = false;
                        tv_expand.setText("展开");
                        recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));
                        prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
                        recyclerViewProd.setAdapter(prodInnerAdapter);
                    }else {
                        if(list.size()%3==0) {
                            lp.height = DensityUtil.dip2px(150 *(list.size()/3),mContext);
                            tv_expand.setText("收起");
                            open = true;
                            recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));
                            prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
                            recyclerViewProd.setAdapter(prodInnerAdapter);

                        }else if(list.size()%3==1) {
                            lp.height = DensityUtil.dip2px(150 *(list.size()/2),mContext);

                            tv_expand.setText("收起");
                            open = true;
                            recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));
                            prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
                            recyclerViewProd.setAdapter(prodInnerAdapter);

                        }else if(list.size()%3==2) {
                            lp.height = DensityUtil.dip2px(150 *(list.size()/2),mContext);

                            tv_expand.setText("收起");
                            open = true;
                            recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));
                            prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
                            recyclerViewProd.setAdapter(prodInnerAdapter);
                        }

                    }

                } else {
                    lp.height = DensityUtil.dip2px(150,mContext);
                    recyclerViewProd.setLayoutManager(new GridLayoutManager(mContext,3));
                    prodInnerAdapter = new ProdInnerAdapter(R.layout.item_prod_inner,list);
                    recyclerViewProd.setAdapter(prodInnerAdapter);
                }
            }
        });
        recyclerViewProd.setLayoutParams(lp);
    }
}
