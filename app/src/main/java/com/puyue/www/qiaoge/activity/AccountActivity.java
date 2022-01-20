package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ChooseRequireAdapter;
import com.puyue.www.qiaoge.adapter.MyAccountAdapter;
import com.puyue.www.qiaoge.api.huolala.HuolalaAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.model.ApplyModel;
import com.puyue.www.qiaoge.model.HuoAddressModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoDriverPayModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccountActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.tv_num)
    TextView tv_num;
    List<HuoDetailModel.DataBean.BillAppealBean> billAppeal;
    String orderDisPlayId;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        billAppeal = (List<HuoDetailModel.DataBean.BillAppealBean>) getIntent().getSerializableExtra("billList");
        orderDisPlayId = getIntent().getStringExtra("orderDisPlayId");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_account);
    }

    @Override
    public void findViewById() {

    }

    List<Integer> cList = new ArrayList<>();
    List<String> cNList = new ArrayList<>();
    List<Double> cPList = new ArrayList<>();
    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        MyAccountAdapter myAccountAdapter = new MyAccountAdapter(R.layout.item_account,billAppeal);
        recyclerView.setAdapter(myAccountAdapter);
        myAccountAdapter.setOnItemClickListener(new MyAccountAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, boolean checked,double amount) {
                if(checked) {
                    cList.add(billAppeal.get(position).getBillType());
                    cNList.add(billAppeal.get(position).getBilTypeName());
                    cPList.add(amount);
                }else {
                    cList.remove(new Integer(billAppeal.get(position).getBillType()));
                    cNList.remove(billAppeal.get(position).getBilTypeName());
                    cPList.remove(new Double(amount));
                }
            }
        });

        et_desc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_num.setText(s.toString().length()+"");
            }
        });
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_sure:
                if(TextUtils.isEmpty(et_desc.getText().toString())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写申诉理由");
                    return;
                }

                String etDesc = et_desc.getText().toString();
                JSONArray jsonArray = new JSONArray();
                if(cList.size()>0) {
                    for (int i = 0; i < cList.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("bill_type",cList.get(i));
                            jsonObject.put("billTypeName",cNList.get(i));
                            jsonObject.put("expectAmt",cPList.get(i));
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getDriver(orderDisPlayId,etDesc,jsonArray);
                    }


                }else {
                    ToastUtil.showSuccessMsg(mContext,"请选择申诉项目");
                }

                break;
        }
    }

    private void getDriver(String orderDisPlayId, String etDesc, JSONArray jsonArray) {
        HuolalaAPI.getDriverAccount(mActivity,orderDisPlayId,etDesc,jsonArray)
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
                        if(baseModel.code==1) {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }else {
                            ToastUtil.showSuccessMsg(mActivity,baseModel.message);
                        }
                    }
                });
    }

}
