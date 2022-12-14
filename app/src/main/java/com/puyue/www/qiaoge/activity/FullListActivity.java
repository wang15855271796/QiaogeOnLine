package com.puyue.www.qiaoge.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.FullListAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.event.FullListModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.selectmenu.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by ${王涛} on 2021/10/8
 */
public class FullListActivity extends BaseSwipeActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    FullListAdapter fullListAdapter;

    private int fadingHeight = 600;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_full_list);
    }

    @Override
    public void findViewById() {
        setTranslucentStatus();
    }

    @Override
    public void setViewData() {
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        fullListAdapter = new FullListAdapter(R.layout.item_fulls,list);
        recyclerView.setAdapter(fullListAdapter);

        getFullList();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    List<FullListModel.DataBean> list = new ArrayList<>();
    private void getFullList() {
        IndexHomeAPI.getFullList(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<FullListModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FullListModel fullListModel) {
                        if(fullListModel.getCode()==1) {
                            if(fullListModel.getData()!=null&&fullListModel.getData().size()>0) {
                                list.addAll(fullListModel.getData());
                                fullListAdapter.notifyDataSetChanged();
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,fullListModel.getMessage());
                        }
                    }
                });
    }
}
