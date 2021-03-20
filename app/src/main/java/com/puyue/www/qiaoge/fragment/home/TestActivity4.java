package com.puyue.www.qiaoge.fragment.home;

import android.animation.IntEvaluator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.DensityUtil;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.TestActivity;
import com.puyue.www.qiaoge.adapter.MyAdapter;
import com.puyue.www.qiaoge.adapter.MyTestAdapter;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.view.selectmenu.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2021/1/8
 */
public class TestActivity4 extends AppCompatActivity {
    private static final float ENDMARGINLEFT = 50;
    private static final float ENDMARGINTOP = 5;
    private static final float STARTMARGINLEFT = 20;
    private static final float STARTMARGINTOP = 140;
    private RelativeLayout rv_bar;
    private RelativeLayout rv_search;

    private ImageView iv_search;
    private int scrollLength;//顶部栏从透明变成不透明滑动的距离

    private int evaluatemargin;
    private int evaluatetop;
    private FrameLayout.LayoutParams layoutParams;

    List<String> list = new ArrayList<>();
    int i;
    int topHeight;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.test6);



        rv_bar = (RelativeLayout) findViewById(R.id.rv_bar);
        rv_search = (RelativeLayout) findViewById(R.id.rv_search);
        ObservableScrollView sv_search = (ObservableScrollView) findViewById(R.id.sv_search);
        iv_search = (ImageView) findViewById(R.id.iv_search);
        RecyclerView lv_searchview = (RecyclerView) findViewById(R.id.lv_searchview);

        for (int j = 0; j < 50; j++) {
            list.add("sss");
        }
//        lv_searchview.setAdapter(new searchAdapter(R.layout.item_test, list));
//        sv_search.smoothScrollTo(0, 0);
//        lv_searchview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        sv_search.setScrollViewListener(new ScrollViewListener() {
//
//            private int evaluatemargin;
//            private int evaluatetop;
//            private FrameLayout.LayoutParams layoutParams;
//
//            @Override
//            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
//
//                int abs_y = Math.abs(y);
//                //滑动距离小于顶部栏从透明到不透明所需的距离
//                if ((scrollLength - abs_y) > 0) {
//
//                    //估值器
//                    IntEvaluator evaluator = new IntEvaluator();
//                    float percent = (float) (scrollLength - abs_y) / scrollLength;
//
//                    if (percent <= 1) {
//
//                        //透明度
//                        int evaluate = evaluator.evaluate(percent, 255, 0);
//                        rv_bar.getBackground().setAlpha(evaluate);
//                        //搜索栏左右margin值
//                        evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this), DensityUtil.dip2px(STARTMARGINLEFT,TestActivity4.this));
//                        //搜索栏顶部margin值
//                        evaluatetop = evaluator.evaluate(percent,  DensityUtil.dip2px(ENDMARGINTOP,TestActivity4.this), DensityUtil.dip2px(STARTMARGINTOP,TestActivity4.this));
//
//                        layoutParams = (FrameLayout.LayoutParams) rv_search.getLayoutParams();
//                        layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
//                        rv_search.requestLayout();
//                    }
//
//
//                } else {
//                    rv_bar.getBackground().setAlpha(255);
//                    if(layoutParams!=null){
//                        layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this),DensityUtil.dip2px(5,TestActivity4.this), DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this), 0);
//                        rv_search.requestLayout();
//                    }
//
//                }
//            }
//        });

        rv_bar.post(new Runnable() {
            @Override
            public void run() {
                int height = iv_search.getHeight();
                i = DensityUtil.px2dip(height,getApplicationContext());
                int height_rv = rv_bar.getHeight();
                int height_iv = iv_search.getHeight();

                scrollLength = Math.abs(height_iv - height_rv);
                //把顶部bar设置为透明
                rv_bar.getBackground().setAlpha(0);

                topHeight = DensityUtil.dip2px(i-50,getBaseContext());

                sv_search.setScrollViewListener(new ScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                        int abs_y = Math.abs(y);
                        //滑动距离小于顶部栏从透明到不透明所需的距离
                        if ((scrollLength - abs_y) > 0) {

                            //估值器
                            IntEvaluator evaluator = new IntEvaluator();
                            float percent = (float) (scrollLength - abs_y) / scrollLength;

                            if (percent <= 1) {

                                //透明度
                                int evaluate = evaluator.evaluate(percent, 255, 0);
                                rv_bar.getBackground().setAlpha(evaluate);
                                //搜索栏左右margin值
                                evaluatemargin = evaluator.evaluate(percent, DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this), DensityUtil.dip2px(STARTMARGINLEFT,TestActivity4.this));
                                //搜索栏顶部margin值
                                evaluatetop = evaluator.evaluate(percent,  DensityUtil.dip2px(ENDMARGINTOP,TestActivity4.this), DensityUtil.dip2px(STARTMARGINTOP,TestActivity4.this));

                                layoutParams = (FrameLayout.LayoutParams) rv_search.getLayoutParams();
                                layoutParams.setMargins(evaluatemargin, evaluatetop, evaluatemargin, 0);
                                rv_search.requestLayout();
                            }


                        } else {
                            rv_bar.getBackground().setAlpha(255);
                            if(layoutParams!=null){
                                layoutParams.setMargins(DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this),DensityUtil.dip2px(5,TestActivity4.this), DensityUtil.dip2px(ENDMARGINLEFT,TestActivity4.this), 0);
                                rv_search.requestLayout();
                            }

                        }
                    }
                });
            }
        });

    }



//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//
//        int height_rv = rv_bar.getHeight();
//        int height_iv = iv_search.getHeight();
//
//        scrollLength = Math.abs(height_iv - height_rv);
//
//        //把顶部bar设置为透明
//        rv_bar.getBackground().setAlpha(0);
//
//    }

    class searchAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


        public searchAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    }
}


