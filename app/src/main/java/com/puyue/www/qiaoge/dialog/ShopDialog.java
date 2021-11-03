package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class ShopDialog extends Dialog {

    Context context;
    public View view;
    public Unbinder binder;

    public ShopDialog(Context context) {
        super(context, R.style.dialog);
        this.context = context;

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
    public void cancel() {
        super.cancel();
        EventBus.getDefault().unregister(this);
    }

    public void init() {
        view = View.inflate(context, R.layout.dialog_shop_city, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);


    }





    public interface Onclick {
        void addDialog(int num);
    }

}
