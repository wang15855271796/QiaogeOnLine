package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.CompanyListAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.event.AddressEvent;
import com.puyue.www.qiaoge.event.CompanyEvent;
import com.puyue.www.qiaoge.model.CompanyListModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

public class ChooseCompanyActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_long)
    TextView tv_long;
    @BindView(R.id.tv_short)
    TextView tv_short;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_choose)
    TextView tv_choose;
    @BindView(R.id.tv_other)
    TextView tv_other;
    @BindView(R.id.rl_current_company)
    RelativeLayout rl_current_company;
    CompanyListAdapter companyListAdapter;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }
    @Override
    public void setContentView() {
        setContentView(R.layout.choose_company_activity);
    }

    @Override
    public void findViewById() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        companyListAdapter = new CompanyListAdapter(R.layout.item_company,list);
        recyclerView.setAdapter(companyListAdapter);
        getCompanyList();
        companyListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                companyListAdapter.setSelectionPos(position);
                companyId = list.get(position).getCompanyId();
                companyListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setClickEvent() {
        ll_add.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        tv_choose.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_add:
                Intent intent = new Intent(mActivity,AddCompanyActivity.class);
                startActivity(intent);
                break;

            case R.id.tv_sure:
                if(!companyId.equals("")) {
                    getFullTips(companyId);
                }else {
                    ToastUtil.showSuccessMsg(mActivity,"请选择对应企业");
                }

                break;

            case R.id.tv_choose:
                chooseStyle();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCompanyList(CompanyEvent companyEvent) {
        getCompanyList();
    }

    /**
     * 切换城市版或企业版
     */
    private void chooseStyle() {
        IndexHomeAPI.changeCity(mContext, SharedPreferencesUtil.getInt(mActivity,"wad"))
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
                            SharedPreferencesUtil.saveInt(mActivity,"wad",0);
                            EventBus.getDefault().post(new AddressEvent());
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 更改企业
     * @param companyId
     */
    private void getFullTips(String companyId) {
        IndexHomeAPI.changeCompany(mContext,companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
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
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            SharedPreferencesUtil.saveInt(mActivity,"wad",1);
                            EventBus.getDefault().post(new AddressEvent());
                            finish();
                        }else {
                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    /**
     * 获取公司列表
     */
    String companyId = "";
    List<CompanyListModel.DataBean> list = new ArrayList<>();
    private void getCompanyList() {
        IndexHomeAPI.getCompanyList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CompanyListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CompanyListModel companyListModel) {
                        if(companyListModel.getCode()==1) {
                            list.clear();
                            if(companyListModel.getData()!=null&&companyListModel.getData().size()>0) {
                                tv_sure.setVisibility(View.VISIBLE);
                                List<CompanyListModel.DataBean> data = companyListModel.getData();
                                for (int i = 0; i < data.size(); i++) {
                                    if(data.get(i).getFlag()==1) {
                                        tv_short.setText(data.get(i).getShortName());
                                        tv_long.setText(data.get(i).getCompanyName());
                                        companyId = data.get(i).getCompanyId();
                                        tv_other.setVisibility(View.GONE);
                                        rl_current_company.setVisibility(View.VISIBLE);
                                    }else {
                                        list.add(data.get(i));
                                        tv_other.setVisibility(View.VISIBLE);
                                    }
                                }
                                companyListAdapter.notifyDataSetChanged();
                            }else {
                                rl_current_company.setVisibility(View.GONE);
                                tv_sure.setVisibility(View.GONE);
                                tv_other.setVisibility(View.GONE);
                            }

                        }else {
                            ToastUtil.showSuccessMsg(mContext,companyListModel.getMessage());
                        }
                    }
                });
    }

}
