package com.puyue.www.qiaoge.activity.view;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.AskActivity;
import com.puyue.www.qiaoge.activity.NetWorkActivity;
import com.puyue.www.qiaoge.activity.PlayerActivity;
import com.puyue.www.qiaoge.adapter.SchoolAdapter;
import com.puyue.www.qiaoge.api.home.IndexHomeAPI;
import com.puyue.www.qiaoge.api.home.SchoolVideoApi;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.NetWorkHelper;
import com.puyue.www.qiaoge.model.CouponModels;
import com.puyue.www.qiaoge.model.SchoolVideoListModel;
import com.puyue.www.qiaoge.utils.DateUtils;
import com.puyue.www.qiaoge.view.selectmenu.MyScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class SchoolActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
    @BindView(R.id.iv_top)
    ImageView iv_top;
    @BindView(R.id.rl_header)
    RelativeLayout rl_header;
    @BindView(R.id.iv_back1)
    ImageView iv_back1;
    @BindView(R.id.my_scroll)
    MyScrollView my_scroll;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_school);
    }

    @Override
    public void findViewById() {
    }

    SchoolAdapter schoolAdapter;
    @Override
    public void setViewData() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        schoolAdapter = new SchoolAdapter(R.layout.item_school,videos);
        recyclerView.setAdapter(schoolAdapter);

//        smart.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshLayout) {
//                videos.clear();
//                getSchoolVideoList();
//                refreshLayout.finishRefresh();
//            }
//        });

        schoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, PlayerActivity.class);
                intent.putExtra("url",videos.get(position).getVideoUrl());
                intent.putExtra("id",videos.get(position).getVideoId());
                startActivity(intent);
            }
        });

        getSchoolVideoList();

        my_scroll.setScrollChangeListener(new MyScrollView.ScrollChangedListener() {
            @Override
            public void onScrollChangedListener(int x, int y, int oldX, int oldY) {
                if(y==0) {
                    rl_header.setVisibility(View.GONE);
                }else {
                    rl_header.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void setClickEvent() {
        tv_ok.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_back1.setOnClickListener(this);
    }


    List<SchoolVideoListModel.DataBean.VideosBean> videos = new ArrayList<>();
    private void getSchoolVideoList() {
        if (!NetWorkHelper.isNetworkAvailable(mActivity)) {
            Intent intent = new Intent(mContext, NetWorkActivity.class);
            startActivity(intent);
        }else {
            SchoolVideoApi.getVideoList(mActivity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThread())
                    .subscribe(new Subscriber<SchoolVideoListModel>() {

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(SchoolVideoListModel schoolVideoListModel) {
                            if (schoolVideoListModel.getCode()==1) {
                                if(schoolVideoListModel.getData()!=null) {
                                    if(schoolVideoListModel.getData().getVideos()!=null && schoolVideoListModel.getData().getVideos().size()>0) {
                                        videos.addAll(schoolVideoListModel.getData().getVideos());
                                        schoolAdapter.notifyDataSetChanged();
                                    }else {
                                        showEmptyLayout(schoolAdapter);
                                    }
                                    String backgroundUrl = schoolVideoListModel.getData().getBackgroundUrl();
                                    Glide.with(mActivity).load(backgroundUrl).into(iv_top);
                                }
                            }
                        }
                    });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                Intent intent = new Intent(mActivity, AskActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_back:
                finish();
                break;

            case R.id.iv_back1:
                finish();
                break;
        }


    }
}
