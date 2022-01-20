package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ReasonAdapter;
import com.puyue.www.qiaoge.adapter.ReasonsAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.HuoReasonEvent;
import com.puyue.www.qiaoge.model.CancelReasonModel;
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

public class HuoReasonDialog extends Dialog implements View.OnClickListener {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    ReasonsAdapter reasonsAdapter;
    List<CancelReasonModel.DataBean> list;
    public HuoReasonDialog(Context context, List<CancelReasonModel.DataBean> list) {
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
        reasonsAdapter = new ReasonsAdapter(R.layout.item_reasons,list);
        recyclerView.setAdapter(reasonsAdapter);

        reasonsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                sub_reason = list.get(position).getSub_reason();
                name = list.get(position).getName();
                reasonsAdapter.setSelectPosition(position);
            }
        });

        tv_sure.setOnClickListener(this);
    }


    String name;
    List<CancelReasonModel.DataBean.SubReasonBean> sub_reason;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_sure:
                dismiss();
                EventBus.getDefault().post(new HuoReasonEvent(sub_reason,name));
                break;

            default:
                break;

        }
    }
}
