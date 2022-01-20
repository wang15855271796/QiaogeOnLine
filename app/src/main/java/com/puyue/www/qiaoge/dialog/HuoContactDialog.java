package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ReasonsAdapter;
import com.puyue.www.qiaoge.event.HuoOrderContactEvent;
import com.puyue.www.qiaoge.model.CancelReasonModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HuoContactDialog extends Dialog {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    public HuoContactDialog(Context mContext) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        init();
    }

    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_contact, null);
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
                String etName = et_name.getText().toString();
                String etPhone = et_phone.getText().toString();
                if(!TextUtils.isEmpty(etName)&&!TextUtils.isEmpty(etPhone)) {
                    EventBus.getDefault().post(new HuoOrderContactEvent(etName,etPhone));
                    dismiss();
                }else {
                    ToastUtil.showSuccessMsg(context,"请填写对应信息");
                }
            }
        });
    }
}
