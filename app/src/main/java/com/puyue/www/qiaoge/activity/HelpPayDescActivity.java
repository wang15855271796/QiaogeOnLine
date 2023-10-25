package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.order.NewOrderDetailActivity;
import com.puyue.www.qiaoge.activity.mine.order.SelfSufficiencyOrderDetailActivity;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.ToFriendModel;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViews;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import butterknife.BindView;

public class HelpPayDescActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.snap)
    SnapUpCountDownTimerViews snap;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_order)
    TextView tv_order;
    ToFriendModel.DataBean dataBean;
    int orderDeliveryType;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        dataBean = (ToFriendModel.DataBean) getIntent().getSerializableExtra("data");
        orderDeliveryType = getIntent().getIntExtra("orderDeliveryType",0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_help_pay_desc);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        snap.setTime(true,dataBean.getSystemTime(),0,dataBean.getCancelTime());
        snap.start();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_back:
                Intent intent = new Intent(mContext,HomeActivity.class);
                startActivity(intent);
                EventBus.getDefault().post(new HomeFragmentEvent());
                finish();
                break;

            case R.id.tv_order:
                Intent intent1;
                if(orderDeliveryType == 0) {
                    intent1 = new Intent(mContext, NewOrderDetailActivity.class);
                }else {
                    intent1 = new Intent(mContext, SelfSufficiencyOrderDetailActivity.class);
                }
                intent1.putExtra("orderId",dataBean.getOrderId());
                intent1.putExtra("account","2");
                startActivity(intent1);
                finish();

                break;

        }
    }
}
