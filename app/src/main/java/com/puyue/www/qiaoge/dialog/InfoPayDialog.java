package com.puyue.www.qiaoge.dialog;

import static com.umeng.socialize.utils.ContextUtil.getContext;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.PayInfoListAdapter;
import com.puyue.www.qiaoge.adapter.PayListAdapter;
import com.puyue.www.qiaoge.adapter.TimeAdapter;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.event.InfoPayEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.PayInfoListModel;
import com.puyue.www.qiaoge.model.PayInfoModel;
import com.puyue.www.qiaoge.model.PayListModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InfoPayDialog extends Dialog {
    Context context;
    View view;
    Unbinder binder;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_pay)
    TextView tv_pay;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Integer flag;
    String amount;
    public InfoPayDialog(Context context,String amount) {
        super(context, R.style.dialog);
        this.context = context;
        this.amount = amount;
        init();

    }

    private void init() {
        view = View.inflate(context, R.layout.dialog_pay, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        tv_amount.setText(amount);
        getPayList();
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new InfoPayEvent(flag));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        payListAdapter = new PayInfoListAdapter(R.layout.item_pay_info_list,list);
        recyclerView.setAdapter(payListAdapter);

        payListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                payListAdapter.selectionPosition(position);
                flag = list.get(position).getFlag();
                Log.d("aswdfadeaw....","1233");
            }
        });


    }

    /**
     * 提交订单
     */
    List<PayInfoListModel.DataBean> data;
    List<PayInfoListModel.DataBean> list = new ArrayList<>();
    PayInfoListAdapter payListAdapter;

    private void getPayList() {
        OrderPayAPI.getPayList(getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayInfoListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(PayInfoListModel payListModel) {
                        if (payListModel.getCode()==1) {
                            data = payListModel.getData();
                            list.clear();
                            list.addAll(data);
                            payListAdapter.notifyDataSetChanged();
                        } else {
                            AppHelper.showMsg(getContext(), payListModel.getMessage());
                        }
                    }
                });
    }
}
