package com.puyue.www.qiaoge.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.mine.AreaAdapter;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.fragment.home.CityEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.IsShowModel;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BackDialog extends Dialog implements View.OnClickListener {
    Activity mContext;
    TextView tv_sure;
    TextView tv_continue;

    public BackDialog(Activity context) {
        super(context, R.style.promptDialog);
        setContentView(R.layout.dialog_back);
        this.mContext = context;
        initView();
    }

    private void initView() {
        tv_sure = findViewById(R.id.tv_sure);
        tv_continue = findViewById(R.id.tv_continue);
        tv_sure.setOnClickListener(this);
        tv_continue.setOnClickListener(this);
    }

    public abstract void Confirm();
    public abstract void sure();

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                sure();
                break;

            case R.id.tv_continue:
                Confirm();
                break;
        }
    }
}
