package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
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
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.adapter.HuoCouponAdapter;
import com.puyue.www.qiaoge.adapter.HuoOtherAdapter;
import com.puyue.www.qiaoge.event.OtherEvent;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.HuoCouponModel;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuoOtherDialog extends Dialog {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<CarStyleModel.DataBean.SpecReqItemBean> vehicleStdItemList;
    public HuoOtherDialog(Context mContext, List<CarStyleModel.DataBean.SpecReqItemBean> vehicleStdItem) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.vehicleStdItemList = vehicleStdItem;
        init();
    }

    @Override
    public void show() {
        super.show();
        EventBus.getDefault().register(this);
    }

    @Override
    public void hide() {
        super.hide();
        EventBus.getDefault().unregister(this);
    }

    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_other, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OtherEvent(list));
                dismiss();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        HuoOtherAdapter huoOtherAdapter = new HuoOtherAdapter(R.layout.item_other,vehicleStdItemList);
        recyclerView.setAdapter(huoOtherAdapter);
    }

    List<String> list;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getOther(OtherEvent otherEvent) {
        list = otherEvent.getList();
    }

}
