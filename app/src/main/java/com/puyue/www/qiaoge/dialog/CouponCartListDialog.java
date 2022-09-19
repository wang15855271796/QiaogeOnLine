package com.puyue.www.qiaoge.dialog;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.CouponUseActivity;
import com.puyue.www.qiaoge.adapter.RoleAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.model.FullCouponListModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CouponCartListDialog extends Dialog implements View.OnClickListener {
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
    @BindView(R.id.rv_role)
    RecyclerView rv_role;
    @BindView(R.id.iv_arrow)
    ImageView iv_arrow;
    public List<GetProductDetailModel.DataBean.ProdSpecsBean> prodSpecs;
    String giftPoolNo;
    RoleAdapter roleAdapter;
    String name;
    int type;
    public CouponCartListDialog(Context mContext, String giftPoolNo, int type, String name) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.type = type;
        this.giftPoolNo = giftPoolNo;
        this.name = name;
        init();
        getFullList();
    }


    boolean isOpen;
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

        if(type==1) {
            tv_detail.setVisibility(View.VISIBLE);
        }else if(type==2) {
            tv_detail.setVisibility(View.VISIBLE);
        }else {
            tv_detail.setVisibility(View.GONE);
        }
        rv_role.setLayoutManager(new LinearLayoutManager(context));
        roleAdapter = new RoleAdapter(R.layout.item_text1,roleList);
        rv_role.setAdapter(roleAdapter);

        iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
        ViewGroup.LayoutParams lp = rv_role.getLayoutParams();
        roleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if(!isOpen) {
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_up);
                    lp.height = DensityUtil.dip2px(RecyclerView.LayoutParams.WRAP_CONTENT,context);
                }else {
                    lp.height = DensityUtil.dip2px(15,context);
                    iv_arrow.setImageResource(R.mipmap.icon_arrow_down);
                }
                rv_role.setLayoutParams(lp);
                isOpen = !isOpen;
            }
        });

        lp.height = DensityUtil.dip2px(15,context);
        rv_role.setLayoutParams(lp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;

            case R.id.tv_detail:
                if(type==1) {
                    Intent intent = new Intent(context, CouponUseActivity.class);
                    intent.putExtra("type",1+"");
                    intent.putExtra("poolNo",giftPoolNo);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                    tv_detail.setVisibility(View.VISIBLE);
                }else if(type==2) {
                    Intent intent = new Intent(context, CouponUseActivity.class);
                    intent.putExtra("type",2+"");
                    intent.putExtra("poolNo",giftPoolNo);
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

    List<String> roleList = new ArrayList<>();
    private void getFullList() {
        IndexHomeAPI.getFullCouponList(context,giftPoolNo)
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

                                roleList.clear();
                                roleList.addAll(fullCouponListModel.getData().getGiftUseRole());
                                roleAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(getContext(),fullCouponListModel.getMessage());
                        }
                    }
                });
    }
}
