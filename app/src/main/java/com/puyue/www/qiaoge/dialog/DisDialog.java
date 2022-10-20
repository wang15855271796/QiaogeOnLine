package com.puyue.www.qiaoge.dialog;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.DisTributionSelfEvent;
import com.puyue.www.qiaoge.helper.StringSpecialHelper;
import com.puyue.www.qiaoge.model.ModeModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.schedulers.Schedulers;

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
    @BindView(R.id.tv_name2)
    TextView tv_name2;
    @BindView(R.id.rl_cb1)
    RelativeLayout rl_cb1;
    @BindView(R.id.rl_cb2)
    RelativeLayout rl_cb2;
    @BindView(R.id.iv_choose1)
    ImageView iv_choose1;
    @BindView(R.id.iv_choose2)
    ImageView iv_choose2;
    @BindView(R.id.tv_tip)
    TextView tv_tip;
    @BindView(R.id.tv_unOpen)
    TextView tv_unOpen;
    @BindView(R.id.tv_call)
    TextView tv_call;
    String sendAmount;
    int type;
    public DisDialog(Context mContext, String sendAmount,int type) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.sendAmount = sendAmount;
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

    @Override
    public void hide() {
        super.hide();
        EventBus.getDefault().unregister(this);
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

       getMode();

        rl_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb1) {
                    tv_name2.setTextColor(Color.parseColor("#333333"));
                    tv_huo.setTextColor(Color.parseColor("#333333"));
                    isCb1 = false;
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    iv_choose1.setVisibility(View.GONE);
                    tv_name.setTextColor(Color.parseColor("#333333"));
                    tv_price.setTextColor(Color.parseColor("#999999"));
                    if(isCb2) {
                        iv_choose2.setVisibility(View.GONE);
                        tv_tip.setTextColor(Color.parseColor("#999999"));
                        rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                        tv_name2.setTextColor(Color.parseColor("#333333"));
                        tv_huo.setTextColor(Color.parseColor("#999999"));
                    }
                }else {
                    isCb1 = true;
                    isCb2 = false;
                    rl_cb1.setBackgroundResource(R.drawable.shape_orange14);
                    tv_name.setTextColor(Color.parseColor("#FF5C00"));
                    tv_price.setTextColor(Color.parseColor("#FF5C00"));
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    tv_name.setTextColor(Color.parseColor("#FF5C00"));
                    tv_name2.setTextColor(Color.parseColor("#333333"));
                    tv_tip.setTextColor(Color.parseColor("#999999"));
                    tv_huo.setTextColor(Color.parseColor("#999999"));
                    iv_choose1.setVisibility(View.VISIBLE);
                    iv_choose2.setVisibility(View.GONE);
                    EventBus.getDefault().post(new DisTributionEvent("翘歌配送",0));
                    ToastUtil.showSuccessMsg(context,"配送方式已选好");
                    dismiss();
                }

            }
        });

        rl_cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_name.setTextColor(Color.parseColor("#333333"));
                tv_price.setTextColor(Color.parseColor("#999999"));
                if(isCb2) {
                    isCb2 = false;
                    iv_choose2.setVisibility(View.VISIBLE);
                    tv_name2.setTextColor(Color.parseColor("#333333"));
                    tv_tip.setTextColor(Color.parseColor("#999999"));
                    tv_huo.setTextColor(Color.parseColor("#333333"));
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    iv_choose2.setVisibility(View.GONE);
                    if(isCb1) {
                        iv_choose1.setVisibility(View.GONE);
                        rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                        tv_name.setTextColor(Color.parseColor("#333333"));
                        tv_price.setTextColor(Color.parseColor("#999999"));
                    }
                }else {
                    isCb2 = true;
                    isCb1 = false;
                    EventBus.getDefault().post(new DisTributionEvent("买家自己呼叫货拉拉",1));
                    ToastUtil.showSuccessMsg(context,"配送方式已选好");
                    dismiss();
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    rl_cb2.setBackgroundResource(R.drawable.shape_orange14);
                    tv_name2.setTextColor(Color.parseColor("#FF5C00"));
                    tv_tip.setTextColor(Color.parseColor("#FF5C00"));
                    tv_huo.setTextColor(Color.parseColor("#FF5C00"));
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
        EventBus.getDefault().post(new DisTributionEvent("买家自己呼叫货拉拉",1));
        dismiss();
        rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
        rl_cb2.setBackgroundResource(R.drawable.shape_orange14);
        iv_choose1.setVisibility(View.GONE);
        iv_choose2.setVisibility(View.VISIBLE);
        tv_name2.setTextColor(Color.parseColor("#FF5C00"));
        tv_tip.setTextColor(Color.parseColor("#FF5C00"));
        tv_huo.setTextColor(Color.parseColor("#FF5C00"));
        tv_tip.setTextColor(Color.parseColor("#FF5C00"));
        tv_name.setTextColor(Color.parseColor("#333333"));
        tv_price.setTextColor(Color.parseColor("#999999"));
    }

    /**
     * 是否显示内容
     */
    ModeModel modeModel1;
    public void getMode() {
        IndexHomeAPI.getMode(context, SharedPreferencesUtil.getInt(context,"wad"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<ModeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ModeModel modeModel) {
                        if(modeModel.getCode()==1) {
                            if(modeModel.getData()!=null) {
                                modeModel1 = modeModel;
                                if(modeModel1.getData().getHllBtn()==1) {
                                    tv_name2.setTextColor(Color.parseColor("#999999"));
                                    tv_huo.setVisibility(View.GONE);
                                    tv_tip.setVisibility(View.GONE);
                                    tv_call.setVisibility(View.GONE);
                                    tv_unOpen.setVisibility(View.VISIBLE);
                                }else {
                                    tv_name2.setTextColor(Color.parseColor("#333333"));
                                    tv_huo.setVisibility(View.VISIBLE);
                                    tv_tip.setVisibility(View.VISIBLE);
                                    tv_call.setVisibility(View.VISIBLE);
                                    tv_unOpen.setVisibility(View.GONE);
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(context,modeModel.getMessage());
                        }
                    }
                });
    }

}
