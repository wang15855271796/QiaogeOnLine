package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.adapter.ChooseFullSpecAdapters;
import com.puyue.www.qiaoge.adapter.HotItemAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.event.FullListModel;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.UpDateNumEvent6;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.FullCouponListModel;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ExchangeProductModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;
import com.puyue.www.qiaoge.view.FlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class CouponFullListDialog extends Dialog implements View.OnClickListener {

    Context context;
    public View view;
    public Unbinder binder;

    @BindView(R.id.iv_close)
    ImageView iv_close;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_role)
    TextView tv_role;
    @BindView(R.id.tv_time)
    TextView tv_time;
    int productId;
    public List<GetProductDetailModel.DataBean.ProdSpecsBean> prodSpecs;
    FullDetailModel.DataBean.ProdsBean item;
    String poolNo;

    public CouponFullListDialog(Context mContext, String poolNo) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.poolNo = poolNo;
        init();
        getFullList();
    }

    public void init() {
        view = View.inflate(context, R.layout.dialog_full_list, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        iv_close.setOnClickListener(this);


//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            default:
                break;

        }
    }

    private void getFullList() {
        IndexHomeAPI.getFullCouponList(context,poolNo)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<FullCouponListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FullCouponListModel fullCouponListModel) {
                        if(fullCouponListModel.getCode()==1) {
                            if(fullCouponListModel.getData()!=null) {
                                FullCouponListModel.DataBean data = fullCouponListModel.getData();
                                tv_title.setText(data.getGiftName());
                                tv_time.setText(data.getDateTime());
                                tv_amount.setText(data.getAmountStr());
                                tv_role.setText(data.getRole().get(0));
                            }
                        }else {
                            ToastUtil.showSuccessMsg(getContext(),fullCouponListModel.getMessage());
                        }
                    }
                });
    }

}
