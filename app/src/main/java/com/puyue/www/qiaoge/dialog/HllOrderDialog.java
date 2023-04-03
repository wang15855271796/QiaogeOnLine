package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HuoDetailActivity;
import com.puyue.www.qiaoge.adapter.FullCartAdapter;
import com.puyue.www.qiaoge.adapter.HllOrderAdapter;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.event.HllOrderEvent;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class HllOrderDialog extends Dialog {
    Activity context;
    public View view;
    public Unbinder binder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    List<String> hllConnectOrderIds;
    public HllOrderDialog(Activity activity, List<String> hllConnectOrderIds) {
        super(activity, R.style.dialog);
        this.context = activity;
        this.hllConnectOrderIds = hllConnectOrderIds;
        init();
    }

    //初始化布局
    int pos = 0;
    private void init() {
        if(view == null) {
            view = View.inflate(context, R.layout.hll_order_dialog, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binder = ButterKnife.bind(this, view);
            setContentView(view);
            ImageView iv_close = view.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
            getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = Utils.getScreenWidth(context);
            getWindow().setAttributes(attributes);
        }

        recycleView.setLayoutManager(new LinearLayoutManager(context));
        HllOrderAdapter hllOrderAdapter = new HllOrderAdapter(R.layout.item_hll_order,hllConnectOrderIds);
        recycleView.setAdapter(hllOrderAdapter);
        hllOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                hllOrderAdapter.setSelectionPos(position);
                pos = position;


            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close(pos);
                dismiss();
            }
        });

    }
    public abstract void close(int pos);
}
