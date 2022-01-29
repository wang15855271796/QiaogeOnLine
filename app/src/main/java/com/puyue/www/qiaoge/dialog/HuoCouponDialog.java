package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.HuoCouponAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.event.HuoCouponEvent;
import com.puyue.www.qiaoge.event.HuoOrderContactEvent;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.model.HuoCouponModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoCouponDialog extends Dialog {
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<HuoCouponModel.DataBean> dataList;
    boolean flag = false;
    int pos = -1;
    HuoCouponAdapter huoCouponAdapter;
    public HuoCouponDialog(Context mContext, List<HuoCouponModel.DataBean> dataList) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.dataList = dataList;
        init();
    }

    public void init() {
        view = View.inflate(context, R.layout.dialog_huo_coupon, null);
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
                EventBus.getDefault().post(new HuoCouponEvent("您有"+dataList.size()+"张优惠券可用",""));
                pos = -1;
                huoCouponAdapter.setSelectionPosition(pos);
                dismiss();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HuoCouponEvent(dataList.get(pos).getBusiness_type_str()+dataList.get(pos).getDiscount_str(),
                        dataList.get(pos).getCoupon_id()
                ));
                dismiss();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        huoCouponAdapter = new HuoCouponAdapter(R.layout.item_huo_coupon,dataList);
        recyclerView.setAdapter(huoCouponAdapter);
        huoCouponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                huoCouponAdapter.setSelectionPosition(position);
                pos = position;
//                if(pos!=position) {
//                    pos = position;
//                    if(!flag) {
//                        flag = true;
//                        dataList.get(position).setSelect(flag);
//                        huoCouponAdapter.setSelectionPosition(position,flag);
//                    }else {
//                        flag = false;
//                        dataList.get(position).setSelect(flag);
//                        huoCouponAdapter.setSelectionPosition(position,flag);
//                    }
//                }else {
//                    pos = position;
//                    if(!flag) {
//                        flag = true;
//                        dataList.get(position).setSelect(flag);
//                        huoCouponAdapter.setSelectionPosition(position,flag);
//                    }else {
//                        flag = false;
//                        dataList.get(position).setSelect(flag);
//                        huoCouponAdapter.setSelectionPosition(position,flag);
//                    }
//                }

            }
        });

    }




}
