package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CouponUseActivity;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.model.FullCouponListModel;
import com.puyue.www.qiaoge.model.FullDetailModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
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
    @BindView(R.id.tv_user_factor)
    TextView tv_user_factor;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.tv_role)
    TextView tv_role;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    int productId;
    public List<GetProductDetailModel.DataBean.ProdSpecsBean> prodSpecs;
    FullDetailModel.DataBean.ProdsBean item;
    String poolNo;
    String giftProdUseType;
    String name;
    public CouponFullListDialog(Context mContext, String poolNo, String giftProdUseType, String name) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.poolNo = poolNo;
        this.giftProdUseType = giftProdUseType;
        this.name = name;
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
        tv_detail.setOnClickListener(this);

        if(giftProdUseType.equals("1")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(giftProdUseType.equals("2")) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_detail:
                if(giftProdUseType.equals("1")) {
                    Intent intent = new Intent(context, CouponUseActivity.class);
                    intent.putExtra("type",giftProdUseType);
                    intent.putExtra("poolNo",poolNo);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else if(giftProdUseType.equals("2")) {
                    Intent intent = new Intent(context, CouponUseActivity.class);
                    intent.putExtra("type",giftProdUseType);
                    intent.putExtra("poolNo",poolNo);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else {
                    tv_detail.setVisibility(View.GONE);
                }
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
                                tv_user_factor.setText(data.getUseInfo());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(getContext(),fullCouponListModel.getMessage());
                        }
                    }
                });
    }

}
