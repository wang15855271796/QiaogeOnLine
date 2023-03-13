package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.ShopEvent;
import com.puyue.www.qiaoge.adapter.RoleAdapter;
import com.puyue.www.qiaoge.adapter.ShopAdapter;
import com.puyue.www.qiaoge.adapter.ShopTypeAdapter;
import com.puyue.www.qiaoge.model.home.GetRegisterShopModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class ShopDialog extends Dialog implements View.OnClickListener {
    Context context;
    View view;
    public Unbinder binder;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<GetRegisterShopModel.DataBean> mList;
    public ShopDialog(Context mContext, List<GetRegisterShopModel.DataBean> mList) {
        super(mContext, R.style.dialog);
        this.context = mContext;
        this.mList = mList;
        init();
    }

    int pos = -1;
    int id;
    public void init() {
        view = View.inflate(context, R.layout.dialog_style, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        tv_sure.setOnClickListener(this);



        recyclerView.setLayoutManager(new GridLayoutManager(context,3));
        ShopAdapter shopAdapter = new ShopAdapter(R.layout.item_shops,mList);
        recyclerView.setAdapter(shopAdapter);
        shopAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                shopAdapter.setPos(position);
                pos = position;
                id = mList.get(position).getId();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                if(pos == -1) {
                    ToastUtil.showSuccessMsg(context,"请选择店铺类型");
                    return;
                }
                confirm(mList.get(pos).getName(),mList.get(pos).getId());
                break;
        }
    }

    public abstract void confirm(String name,int shopTypeId);

}
