package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.CouponListAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.api.home.QueryHomePropupAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.GoToMarketEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.home.QueryHomePropupModel;

import org.greenrobot.eventbus.EventBus;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuoOrderDialog extends Dialog {

    Context mContext;
    public RecyclerView recyclerView;
    ImageView iv_close;
    TextView tv_deal;


    public HuoOrderDialog(@NonNull Context context, IndexInfoModel.DataBean couponListModel) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_huo_order);
        mContext = context;
        initView();
        initAction();

    }

    private void initView() {
        tv_deal = (TextView) findViewById(R.id.tv_deal);
        iv_close = (ImageView) findViewById(R.id.iv_close);
    }


    private void initAction() {

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
