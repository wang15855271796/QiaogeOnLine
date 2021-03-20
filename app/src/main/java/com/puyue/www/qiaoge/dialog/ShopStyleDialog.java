package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.ReduceItemAdapter;
import com.puyue.www.qiaoge.adapter.ShopTypeAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.ShopStyleEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent5;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.puyue.www.qiaoge.view.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/4
 */
public class ShopStyleDialog extends Dialog{

    Context context;
    public View view;
    public Unbinder binder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();

    String[] data = {"店铺转让","器具转让","厨师招聘","其它信息"};
    public ShopStyleDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;
        init();
    }



    public void init() {
        view = View.inflate(context, R.layout.dialog_shop_style, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);

        List<String> strings = Arrays.asList(data);
        ShopTypeAdapter shopTypeAdapter = new ShopTypeAdapter(R.layout.dialog_shop_type,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(shopTypeAdapter);

        shopTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                shopTypeAdapter.setSelectionPosition(position);
                shopTypeAdapter.notifyDataSetChanged();
                EventBus.getDefault().post(new ShopStyleEvent(data[position],position+1));
                dismiss();
            }
        });

    }

}
