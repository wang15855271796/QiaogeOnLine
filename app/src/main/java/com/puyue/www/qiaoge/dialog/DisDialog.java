package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.HuoBeizhuEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DisDialog extends Dialog {
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
    @BindView(R.id.tv_huo)
    TextView tv_huo;
    @BindView(R.id.tv2)
    TextView tv2;
    String sendAmount;
    int hllBtn;
    int type;
    public DisDialog(Context mContext, String sendAmount,int type,int hllBtn) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.sendAmount = sendAmount;
        this.hllBtn = hllBtn;
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

        String content = "(满"+sendAmount+"元免配送费)";
        SpannableStringBuilder spannableStringBuilder = StringSpecialHelper.buildSpanColorStyle(content, 2,
                sendAmount.length(), Color.parseColor("#FD6601"));
        tv_price.setText(spannableStringBuilder);
        tv_price.setVisibility(View.VISIBLE);
        tv_name.setText("翘歌配送");

        if(hllBtn==1) {
            cb_2.setClickable(false);
            tv2.setTextColor(Color.parseColor("#999999"));
            tv_huo.setText("(暂未开启货拉拉配送服务)");
        }else {
            tv2.setTextColor(Color.parseColor("#333333"));
            tv_huo.setText("(订单支付后，用户自己发起配送服务)");
            cb_2.setClickable(true);
        }
        cb_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
                    EventBus.getDefault().post(new DisTributionEvent("翘歌配送",0));
                    dismiss();
                }
            }

        });

        cb_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isCb2) {
                    isCb2 = false;
                    cb_2.setBackgroundResource(R.drawable.checkbox_no);
                    if(isCb1) {
                        cb_1.setBackgroundResource(R.drawable.checkbox_no);
                    }
                }else {
                    isCb2 = true;
                    isCb1 = false;
                    EventBus.getDefault().post(new DisTributionEvent("我自己叫货拉拉",1));
                    dismiss();
                    cb_2.setBackgroundResource(R.drawable.icon_address_oval);
                    cb_1.setBackgroundResource(R.drawable.checkbox_no);
                }

            }
        });


    }
}
