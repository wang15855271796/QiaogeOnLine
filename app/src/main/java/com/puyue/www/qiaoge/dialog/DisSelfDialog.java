package com.puyue.www.qiaoge.dialog;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.event.ChangeDeliverEvent;
import com.puyue.www.qiaoge.event.DisTribution1Event;
import com.puyue.www.qiaoge.event.DisTributionEvent;
import com.puyue.www.qiaoge.event.DisTributionSelf1Event;
import com.puyue.www.qiaoge.event.DisTributionSelfEvent;
import com.puyue.www.qiaoge.event.RefreshEvent;
import com.puyue.www.qiaoge.model.ModeModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DisSelfDialog extends Dialog {
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


        rl_cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb1) {
                    isCb1 = false;
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    iv_choose1.setVisibility(View.GONE);
                    if(isCb2) {
                        rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                        iv_choose2.setVisibility(View.GONE);
                    }

                }else {
                    isCb1 = true;
                    isCb2 = false;
                    ToastUtil.showSuccessMsg(context,"配送方式已选好");
                    rl_cb1.setBackgroundResource(R.drawable.shape_orange14);
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
//                    EventBus.getDefault().post(new DisTributionSelfEvent("到仓自提",0));
//                    EventBus.getDefault().post(new DisTribution1Event("买家自己呼叫货拉拉",1));

                    EventBus.getDefault().post(new DisTributionSelfEvent("到仓自提",0));
                    dismiss();
                    iv_choose1.setVisibility(View.VISIBLE);
                    iv_choose2.setVisibility(View.GONE);
                }

            }
        });

        rl_cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCb2) {
                    isCb2 = false;
                    iv_choose2.setVisibility(View.GONE);
                    rl_cb2.setBackgroundResource(R.drawable.shape_grey9);
                    if(isCb1) {
                        iv_choose1.setVisibility(View.GONE);
                        rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    }
                }else {
                    isCb2 = true;
                    isCb1 = false;
                    EventBus.getDefault().post(new DisTributionSelf1Event("买家自己呼叫货拉拉",1));
                    EventBus.getDefault().post(new DisTributionEvent("买家自己呼叫货拉拉",1));
                    EventBus.getDefault().post(new RefreshEvent());
                    dismiss();
                    rl_cb1.setBackgroundResource(R.drawable.shape_grey9);
                    rl_cb2.setBackgroundResource(R.drawable.shape_orange14);
                    iv_choose2.setVisibility(View.VISIBLE);
                    iv_choose1.setVisibility(View.GONE);
                    ToastUtil.showSuccessMsg(context,"配送方式已选好");
                }
            }
        });
    }

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
                                }else {
                                    tv_name2.setTextColor(Color.parseColor("#333333"));
                                }

                            }
                        }else {
                            ToastUtil.showSuccessMsg(context,modeModel.getMessage());
                        }
                    }
                });
    }


}
