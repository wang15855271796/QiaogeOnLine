package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.adapter.FullActivitesAdapter;

import com.puyue.www.qiaoge.utils.Utils;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2020/9/22
 * (优惠促销弹窗)
 */
public class PromoteDialog extends Dialog implements View.OnClickListener{
    public Unbinder binder;
    Context context;
    View view;
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.webview)
    WebView webview;
    String fullactives;
    public PromoteDialog(Context context, String fullactives) {
        super(context, R.style.dialog);
        this.context = context;
        this.fullactives = fullactives;
        init();
    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_promote, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        iv_close.setOnClickListener(this);

        webview.loadDataWithBaseURL(null,fullactives, "text/html", "UTF-8", null);
//        FullActivitesAdapter fullActivitesAdapter = new FullActivitesAdapter(R.layout.item_full_actives,fullactives);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
//        recyclerView.setAdapter(fullActivitesAdapter);

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


}
