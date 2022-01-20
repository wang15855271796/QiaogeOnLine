package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.event.HuoAddress1Event;
import com.puyue.www.qiaoge.event.HuoAddressEvent;
import com.puyue.www.qiaoge.event.HuoCityEvent;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class HuoEditxActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.bt_sure)
    Button bt_sure;
    int type;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_edits_address);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        et_name.setText(SharedPreferencesUtil.getString(mContext,"etName1"));
        et_desc.setText(SharedPreferencesUtil.getString(mContext,"etDesc1"));
        et_phone.setText(SharedPreferencesUtil.getString(mContext,"etPhone1"));
        tv_address.setText(SharedPreferencesUtil.getString(mContext,"address1"));

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_address.setOnClickListener(this);
        et_phone.addTextChangedListener(textWatcher);
        et_name.addTextChangedListener(textWatcher);
        bt_sure.setOnClickListener(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;

            case R.id.bt_sure:
                String etName = et_name.getText().toString();
                String etDesc = et_desc.getText().toString();
                String etPhone = et_phone.getText().toString();
                String address = tv_address.getText().toString();
                if(TextUtils.isEmpty(etName)||etName.equals("")
                        ||TextUtils.isEmpty(etPhone)||etPhone.equals("")
                        ||TextUtils.isEmpty(address)||address.equals("")) {
                    ToastUtil.showSuccessMsg(mContext,"请填写对应信息");
                    return;
                }
                SharedPreferencesUtil.saveString(mContext,"etName1",etName);
                SharedPreferencesUtil.saveString(mContext,"etDesc1",etDesc);
                SharedPreferencesUtil.saveString(mContext,"etPhone1",etPhone);
                SharedPreferencesUtil.saveString(mContext,"address1",address);
                if(type==1) {
                    EventBus.getDefault().postSticky(new HuoAddressEvent(dataBean,et_name.getText().toString(),et_phone.getText().toString(),type));
                }else {
                    EventBus.getDefault().postSticky(new HuoAddressEvent(dataBean,et_name.getText().toString(),et_phone.getText().toString(),type));
                }
                finish();
                break;

            case R.id.tv_address:
                Intent intent = new Intent(mContext,HuoSearchAddressActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                break;
        }
    }

    AddressListModel.DataBean dataBean;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(HuoAddress1Event huoAddressEvent) {
        dataBean = huoAddressEvent.getDataBean();
        String address = huoAddressEvent.getDataBean().getAddr();
        tv_address.setText(address);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCity(HuoCityEvent huoCityEvent) {
//        SharedPreferencesUtil.saveString(mContext,"etName","");
//        SharedPreferencesUtil.saveString(mContext,"etDesc","");
//        SharedPreferencesUtil.saveString(mContext,"etPhone","");
//        SharedPreferencesUtil.saveString(mContext,"address","");
//
//        et_name.setText("");
//        et_desc.setText("");
//        et_phone.setText("");
//        tv_address.setText("");
    }
}
