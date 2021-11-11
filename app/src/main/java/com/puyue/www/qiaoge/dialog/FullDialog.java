package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.FullCartAdapter;
import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/8/26
 */
public class FullDialog extends Dialog {
    Activity context;
    public View view;
    public Unbinder binder;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    String deductInfo;
    public FullDialog(Activity activity) {
        super(activity, R.style.dialog);
        this.context = activity;
        init();
    }

    //初始化布局
    private void init() {
        if(view == null) {
            view = View.inflate(context, R.layout.full_dialog, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            binder = ButterKnife.bind(this, view);
            setContentView(view);

            getWindow().setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = Utils.getScreenWidth(context);
            getWindow().setAttributes(attributes);
        }

        getFullDetail();
    }

    List<CartFullModel.DataBean.SendProdsBean> sendProds = new ArrayList<>();
    List<CartFullModel.DataBean> data = new ArrayList<>();
    private void getFullDetail() {
        CartListAPI.getFullDetail(context,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CartFullModel>() {
                    @Override
                    public void onCompleted() {
                    }


                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CartFullModel cartFullModel) {
                        if(cartFullModel.getCode()==1) {
                            sendProds.clear();
                            if(cartFullModel.getData()!=null&&cartFullModel.getData().size()>0) {
                                data = cartFullModel.getData();
                            }

                            recycleView.setLayoutManager(new LinearLayoutManager(context));
                            FullCartAdapter fullCartAdapter = new FullCartAdapter(R.layout.item_full_carts,deductInfo,data);
                            recycleView.setAdapter(fullCartAdapter);

                        }else {
                            ToastUtil.showSuccessMsg(context,cartFullModel.getMessage());
                        }

                    }
                });
    }


}
