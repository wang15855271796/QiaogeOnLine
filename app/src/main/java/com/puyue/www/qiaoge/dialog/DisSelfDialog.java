package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.DisTributionSelfEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisSelfDialog extends Dialog {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.cb_1)
    CheckBox cb_1;
    @BindView(R.id.cb_2)
    CheckBox cb_2;
    @BindView(R.id.tv_name)
    TextView tv_name;
    String sendAmount;
    int type;
    public DisSelfDialog(Context mContext, String sendAmount,int type) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.sendAmount = sendAmount;
        this.type = type;
        init();
    }

    boolean isCb1 = false;
    boolean isCb2 = false;
    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_distribution, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        tv_price.setText("满"+sendAmount+"元免配送费");
        tv_name.setText("到仓自提");
        tv_price.setVisibility(View.GONE);
        cb_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb1) {
                    isCb1 = false;
                    cb_1.setBackgroundResource(R.drawable.checkbox_no);
                    if(isCb2) {
                        cb_2.setBackgroundResource(R.drawable.checkbox_no);
                    }
                }else {
                    isCb1 = true;
                    isCb2 = false;
                    cb_1.setBackgroundResource(R.drawable.icon_address_oval);
                    cb_2.setBackgroundResource(R.drawable.checkbox_no);
                }
            }
        });

        cb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb2) {
                    isCb2 = false;
                    cb_2.setBackgroundResource(R.drawable.checkbox_no);
                    if(isCb1) {
                        cb_1.setBackgroundResource(R.drawable.checkbox_no);
                    }
                }else {
                    isCb2 = true;
                    isCb1 = false;
                    cb_2.setBackgroundResource(R.drawable.icon_address_oval);
                    cb_1.setBackgroundResource(R.drawable.checkbox_no);
                }
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!cb_1.isChecked()&&!cb_2.isChecked()) {
                    ToastUtil.showSuccessMsg(context,"请选择配送服务");
                    return;
                }

                if(cb_1.isChecked()) {
                    EventBus.getDefault().post(new DisTributionSelfEvent("到仓自提",0));
                    dismiss();
                    return;
                }

                if(cb_2.isChecked()) {
                    EventBus.getDefault().post(new DisTributionSelfEvent("我自己叫货拉拉",1));
                    EventBus.getDefault().post(new RefreshEvent());
                    dismiss();
                }

            }
        });

    }

}
