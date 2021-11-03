package com.puyue.www.qiaoge.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ${王涛} on 2019/9/29
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.rv_auto_view)
    RecyclerView rv_auto_view;
    List<String> string = new ArrayList<>();
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.test6);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);

        for (int i = 0; i < 6; i++) {
            string.add("string");
        }
//        PagerSnapHelper snapFull = new PagerSnapHelper();

        PagerSnapHelper snapFull = new PagerSnapHelper();
        Test3Adapter test3Adapter = new Test3Adapter(R.layout.test13,string);
        rv_auto_view.setAdapter(test3Adapter);
//        new GridPagerSnapHelper(1,2).attachToRecyclerView(rv_auto_view);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext,2);
        snapFull.attachToRecyclerView(rv_auto_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        rv_auto_view.setLayoutManager(layoutManager);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                rv_auto_view.smoothScrollToPosition(layoutManager.findFirstVisibleItemPosition() + 1);
            }
        }, 2000, 2000, TimeUnit.MILLISECONDS);



        test3Adapter.notifyDataSetChanged();
    }

    @Override
    public void setViewData() {


    }

    @Override
    public void setClickEvent() {

    }
//    @BindView(R.id.rv1)
//    RecyclerView rv1;
//    @BindView(R.id.bt)
//    Button bt;
//    List<String> list = new ArrayList<>();
//    @Override
//    public boolean handleExtra(Bundle savedInstanceState) {
//        return false;
//    }
//
//    @Override
//    public void setContentView() {
//        setContentView(R.layout.test4);
//    }
//
//    @Override
//    public void findViewById() {
//        ButterKnife.bind(this);
//
////        for (int i = 0; i < 100; i++) {
////            list.add("123");
////        }
//        requestGoodsList("");
////        MyAdapter myAdapter = new MyAdapter(mActivity,list);
////        rv1.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
////        rv1.setAdapter(myAdapter);
//
////        bt.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                rv1.smoothScrollToPosition(20);
////            }
////        });
//    }
//
//    @Override
//    public void setViewData() {
//
//    }
//
//    @Override
//    public void setClickEvent() {
//
//    }
//
//    private void requestGoodsList(String fromId) {
//        MarketGoodsClassifyAPI.getClassifys(getContext())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<ClassIfyModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(ClassIfyModel marketGoodsModel) {
//                        rv1.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
//                        List<ClassIfyModel.DataBean> data = marketGoodsModel.getData();
//                        FirstAdapter firstAdapter = new FirstAdapter(R.layout.item_icons, data);
//                        rv1.setAdapter(firstAdapter);
//
//                    }
//                });
//    }

}
