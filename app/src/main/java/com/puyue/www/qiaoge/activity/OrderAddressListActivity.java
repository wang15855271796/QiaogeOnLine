package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.account.EditAddressActivity;
import com.puyue.www.qiaoge.activity.mine.account.EditAndAddActivity;
import com.puyue.www.qiaoge.adapter.OrderAddressAdapter;
import com.puyue.www.qiaoge.api.mine.address.AddressListAPI;
import com.puyue.www.qiaoge.api.mine.address.DefaultAddressAPI;
import com.puyue.www.qiaoge.api.mine.address.DeleteAddressAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.OrderAddressEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderAddressListActivity extends BaseSwipeActivity {

    public static final String TYPE = "type";//是来编辑地址,还是来新建地址的
    public static final String USER_NAME = "user_name";
    public static final String USER_PHONE = "user_phone";
    public static final String STORE_NAME = "store_name";
    public static final String AREA = "area";//三级城市
    public static final String ADDRESS = "address";//详细地址
    public static final String DEFAULT = "default";//是否是默认地址
    public static final String ADDRESS_ID = "address_id";//地址id
    public static final String PROVINCE_CODE = "province_code";//省ID
    public static final String CITY_CODE = "city_code";//市ID
    public static final String AREA_CODE = "area_code";
    public static final String ORDERID = "orderId";



    private ImageView mIvBack;
    private PtrClassicFrameLayout mPtr;
    private RecyclerView mRv;
    private ImageView mIvNoData;
    private Button mBtnAdd;
    private List<AddressModel.DataBean> mListData = new ArrayList<>();
    private OrderAddressAdapter mAdapterAddress;
    private AddressModel mModelAddress;
    private BaseModel mModelDeleteAddress;
    private BaseModel mModelEditDefaultAddress;
    private int defaultId = -1;
    private String orderId;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_address_list);
    }

    @Override
    public void findViewById() {
        mIvBack = (ImageView) findViewById(R.id.iv_address_list_back);
        mPtr = (PtrClassicFrameLayout) findViewById(R.id.ptr_address_list);
        mRv = (RecyclerView) findViewById(R.id.rv_address_list);
        mIvNoData = (ImageView) findViewById(R.id.iv_address_list_no_data);
        mBtnAdd = (Button) findViewById(R.id.btn_address_list_add);
    }

    @Override
    public void setViewData() {
        orderId = getIntent().getStringExtra("orderId");
        mPtr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestAddressList();
            }
        });

        mAdapterAddress = new OrderAddressAdapter(R.layout.item_addresss, mListData);
        mAdapterAddress.setOnItemChangeClickListener(new OrderAddressAdapter.OnEventClickListener() {

            @Override
            public void onEventClick(View view, int position, String flag) {
                if (flag.equals("default")) {
                   getReceiveAddress(mListData.get(position).id,orderId);
                   mAdapterAddress.notifyDataSetChanged();
                } else if (flag.equals("delete")) {
                    showDeleteDialog(position);
                } else if (flag.equals("edit")) {
                    Intent intent = new Intent(mContext,EditAddressActivity.class);
                    intent.putExtra(TYPE, "edit");
                    intent.putExtra(USER_NAME, mListData.get(position).userName);
                    intent.putExtra(USER_PHONE, mListData.get(position).contactPhone);
                    intent.putExtra(STORE_NAME,   mListData.get(position).shopName);
                    intent.putExtra(AREA, mListData.get(position).cityName);
                    intent.putExtra(ADDRESS, mListData.get(position).detailAddress);
                    intent.putExtra(DEFAULT, "false");
                    intent.putExtra(ADDRESS_ID,  String.valueOf(mListData.get(position).id));
                    intent.putExtra(PROVINCE_CODE,  mListData.get(position).provinceCode);
                    intent.putExtra(CITY_CODE, mListData.get(position).cityCode);
                    intent.putExtra(AREA_CODE, mListData.get(position).areaCode);
                    intent.putExtra(ORDERID, orderId);
                    intent.putExtra("allAddress", (mListData.get(position).provinceName + " " + mListData.get(position).cityName + " " + mListData.get(position).areaName));
                    startActivityForResult(intent,22);
//                    startActivityForResult(EditAddressActivity.getIntent(mContext, EditAddressActivity.class,
//                            "edit",(mListData.get(position).provinceName + " " + mListData.get(position).cityName + " " + mListData.get(position).areaName), mListData.get(position).userName, mListData.get(position).contactPhone,
//                            mListData.get(position).shopName, (mListData.get(position).provinceName + " " +
//                                    mListData.get(position).cityName + " " + mListData.get(position).areaName),
//                            mListData.get(position).detailAddress, "false", String.valueOf(mListData.get(position).id),
//                            mListData.get(position).provinceCode, mListData.get(position).cityCode, mListData.get(position).areaCode, orderId), 22);
                }
            }

            @Override
            public void onEventLongClick(View view, int position, String flag) {

            }
        });


        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    mPtr.setEnabled(false);
                } else {
                    mPtr.setEnabled(true);
                }
            }
        });
        mRv.setAdapter(mAdapterAddress);
        requestAddressList();
    }

    private void requestAddressList() {
        AddressListAPI.requestAddressModel(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressModel addressModel) {
                        mPtr.refreshComplete();
                        logoutAndToHome(mContext, addressModel.code);
                        mModelAddress = addressModel;
                        if (mModelAddress.success) {
                            updateAddressList();
                        } else {
                            AppHelper.showMsg(mContext, mModelAddress.message);
                        }
                    }
                });
    }

    private void updateAddressList() {
        if (mModelAddress.data.size() > 0 && mModelAddress.data != null) {
            mListData.clear();
            mListData.addAll(mModelAddress.data);
            mAdapterAddress.notifyDataSetChanged();
            mRv.setVisibility(View.VISIBLE);
            mIvNoData.setVisibility(View.GONE);
        } else {
            mRv.setVisibility(View.GONE);
            mIvNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setClickEvent() {
        mIvBack.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                finish();
            }
        });

        mBtnAdd.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {

                Intent intent = new Intent(mContext,EditAddressActivity.class);
                intent.putExtra(TYPE, "add");
                intent.putExtra(USER_NAME,"");
                intent.putExtra(USER_PHONE, "");
                intent.putExtra(STORE_NAME, "");
                intent.putExtra(AREA, "");
                intent.putExtra(ADDRESS, "");
                intent.putExtra(DEFAULT, "false");
                intent.putExtra(ADDRESS_ID, "");
                intent.putExtra(PROVINCE_CODE, "");
                intent.putExtra(CITY_CODE,"");
                intent.putExtra(AREA_CODE, "");
                intent.putExtra(ORDERID, orderId);
                intent.putExtra("allAddress", "");
                startActivityForResult(intent,22);

//                startActivityForResult(EditAndAddActivity.getIntent(mContext, EditAddressActivity.class,
//                        "add", "","", "", "", "", "", "false",
//                        "", "", "", "", orderId), 11);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //重新请求一次地址列表数据
        mPtr.autoRefresh();
    }

    private void showDeleteDialog(final int position) {
        final AlertDialog alertDialog = new AlertDialog.Builder(mContext, R.style.CommonDialogStyle).create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_delete_address);
        TextView mTvCancel = (TextView) window.findViewById(R.id.tv_delete_address_dialog_cancel);
        TextView mTvConfirm = (TextView) window.findViewById(R.id.tv_delete_address_dialog_confirm);
        mTvCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                alertDialog.dismiss();
            }
        });
        mTvConfirm.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                requestDeleteAddress(position);
                alertDialog.dismiss();
            }
        });
    }

    //删除地址
    private void requestDeleteAddress(int position) {
        DeleteAddressAPI.requestDeleteAddress(mContext, mListData.get(position).id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        mModelDeleteAddress = baseModel;
                        if (mModelDeleteAddress.success) {
                            AppHelper.showMsg(mContext, "删除地址成功");
                            //删除地址之后重新请求一次地址列表
                            mPtr.autoRefresh();
                            EventBus.getDefault().post(new AddressEvent());
                        } else {
                            AppHelper.showMsg(mContext, mModelDeleteAddress.message);
                        }
                    }
                });
    }

    //修改默认地址
    private void getReceiveAddress(int id, String orderId) {
        DefaultAddressAPI.getReceiveAddress(mContext, id, orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        mModelEditDefaultAddress = baseModel;
                        if (mModelEditDefaultAddress.code==1) {
                            finish();
                            EventBus.getDefault().post(new OrderAddressEvent());
                        } else {
                            AppHelper.showMsg(mContext, mModelEditDefaultAddress.message);
                        }

                    }
                });
    }
}
