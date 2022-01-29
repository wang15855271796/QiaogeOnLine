package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HuoOtherAdapter;
import com.puyue.www.qiaoge.event.OtherEvent;
import com.puyue.www.qiaoge.event.OtherSureEvent;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class HuoOtherDialog extends Dialog {
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
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
                Confirm(list,listType);
//                EventBus.getDefault().post(new OtherSureEvent(list,listType));
//                dismiss();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        HuoOtherAdapter huoOtherAdapter = new HuoOtherAdapter(R.layout.item_other,vehicleStdItemList);
        recyclerView.setAdapter(huoOtherAdapter);
    }

    List<String> list = new ArrayList<>();
    List<Integer> listType = new ArrayList<>();
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getOther(OtherEvent otherEvent) {
        list = otherEvent.getList();
        listType = otherEvent.getListType();
    }

    public abstract void Confirm(List<String> list, List<Integer> listType);
}
