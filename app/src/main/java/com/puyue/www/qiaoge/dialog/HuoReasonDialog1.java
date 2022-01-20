package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.Reasons1Adapter;
import com.puyue.www.qiaoge.adapter.ReasonsAdapter;
import com.puyue.www.qiaoge.event.HuoReason1Event;
import com.puyue.www.qiaoge.event.HuoReasonEvent;
import com.puyue.www.qiaoge.model.CancelReasonModel;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuoReasonDialog1 extends Dialog implements View.OnClickListener {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    Reasons1Adapter reasonsAdapter;
    List<CancelReasonModel.DataBean.SubReasonBean> list;
    public HuoReasonDialog1(Context context, List<CancelReasonModel.DataBean.SubReasonBean> list) {
        super(context, R.style.dialog);
        this.context = context;
        this.list = list;
        init();
    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_reason, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        iv_close.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        reasonsAdapter = new Reasons1Adapter(R.layout.item_reasons,list);
        recyclerView.setAdapter(reasonsAdapter);

        reasonsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                list.get(position).getName();
                name = list.get(position).getName();
                reasonsAdapter.setSelectPosition(position);
            }
        });

        tv_sure.setOnClickListener(this);
    }


    String name;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_sure:
                EventBus.getDefault().post(new HuoReason1Event(name));
                dismiss();
                break;
            default:
                break;

        }
    }

}
