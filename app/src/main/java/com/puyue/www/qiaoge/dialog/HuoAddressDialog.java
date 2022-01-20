package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
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
import com.puyue.www.qiaoge.adapter.ReasonsAdapter;
import com.puyue.www.qiaoge.event.HuoReasonEvent;
import com.puyue.www.qiaoge.model.CancelReasonModel;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuoAddressDialog extends Dialog {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_z)
    TextView tv_z;
    @BindView(R.id.tv_x)
    TextView tv_x;
    @BindView(R.id.tv_x_name)
    TextView tv_x_name;
    @BindView(R.id.tv_x_phone)
    TextView tv_x_phone;
    @BindView(R.id.tv_z_name)
    TextView tv_z_name;
    @BindView(R.id.tv_z_phone)
    TextView tv_z_phone;
    ReasonsAdapter reasonsAdapter;

    String zName;
    String xName;
    String zPhone;
    String xPhone;
    String zAddr;
    String xAddr;
    List<CancelReasonModel.DataBean> list;

    public HuoAddressDialog(Context mContext, String zName, String xName, String zPhone, String xPhone, String zAddr, String xAddr) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.zName = zName;
        this.xName = xName;
        this.zPhone = zPhone;
        this.xPhone = xPhone;
        this.zAddr = zAddr;
        this.xAddr = xAddr;
        init();
    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_address, null);
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

        tv_x_name.setText(xName);
        tv_x_phone.setText(xPhone);
        tv_z_name.setText(zName);
        tv_z_phone.setText(zPhone);
        tv_z.setText(zAddr);
        tv_x.setText(xAddr);
    }

}
