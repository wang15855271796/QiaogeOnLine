package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.DisTributionSelfEvent;
import com.puyue.www.qiaoge.event.HuoBeizhuEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_huo)
    TextView tv_huo;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.rl_cb1)
    RelativeLayout rl_cb1;
    @BindView(R.id.rl_cb2)
    RelativeLayout rl_cb2;
    @BindView(R.id.iv_choose1)
    ImageView iv_choose1;
    @BindView(R.id.iv_choose2)
    ImageView iv_choose2;
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

    @Override
    public void show() {
        super.show();
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
            tv2.setTextColor(Color.parseColor("#999999"));
            tv_huo.setText("(暂未开启货拉拉配送服务)");
        }else {
            tv2.setTextColor(Color.parseColor("#333333"));
            tv_huo.setText("(订单支付后，用户自己发起配送服务)");
        }

        rl_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb1) {
                    isCb1 = false;
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    iv_choose1.setVisibility(View.GONE);
                    if(isCb2) {
                        iv_choose2.setVisibility(View.GONE);
                        rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    }
                }else {
                    isCb1 = true;
                    isCb2 = false;
                    rl_cb1.setBackgroundResource(R.drawable.shape_orange14);
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    iv_choose1.setVisibility(View.VISIBLE);
                    iv_choose2.setVisibility(View.GONE);
                    EventBus.getDefault().post(new DisTributionEvent("翘歌配送",0));
                    dismiss();
                }
            }
        });

        rl_cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb2) {
                    isCb2 = false;
                    iv_choose2.setVisibility(View.VISIBLE);
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    if(isCb1) {
                        iv_choose1.setVisibility(View.GONE);
                        rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    }
                }else {
                    isCb2 = true;
                    isCb1 = false;
                    EventBus.getDefault().post(new DisTributionEvent("我自己叫货拉拉",1));
                    dismiss();
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    rl_cb2.setBackgroundResource(R.drawable.shape_orange14);
                    iv_choose1.setVisibility(View.GONE);
                    iv_choose2.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //配送方式
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDistribution(DisTributionSelfEvent disTributionEvent) {
        isCb2 = true;
        isCb1 = false;
        EventBus.getDefault().post(new DisTributionEvent("我自己叫货拉拉",1));
        dismiss();
        rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
        rl_cb2.setBackgroundResource(R.drawable.shape_orange14);
        iv_choose1.setVisibility(View.GONE);
        iv_choose2.setVisibility(View.VISIBLE);
    }

}
