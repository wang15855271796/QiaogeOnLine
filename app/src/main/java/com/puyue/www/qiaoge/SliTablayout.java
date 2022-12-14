package com.puyue.www.qiaoge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;

public class SliTablayout extends TabLayout {
    /**
     * 每个tab的宽度
     */
    private int tabWidth;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 自定义指示器
     */
    private Bitmap mSlideIcon;
    /**
     * 滑动过程中指示器的水平偏移量
     */
    private int mTranslationX;
    /**
     * 指示器初始X偏移量
     */
    private int mInitTranslationX;
    /**
     * 指示器初始Y偏移量
     */
    private int mInitTranslationY;
    /**
     * 默认的页面可见的tab数量
     */
    private static final int COUNT_DEFAULT_VISIBLE_TAB = 4;
    /**
     * 页面可见的tab数量，默认4个
     */
    private int mTabVisibleCount = COUNT_DEFAULT_VISIBLE_TAB;

    public SliTablayout(Context context) {
        super(context);
    }

    public SliTablayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //核心:指示器自定义的图片线条
        this.mSlideIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_d);
        this.mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        @SuppressLint({"Recycle", "CustomViewStyleable"}) TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabLayoutLine);
        //指示器数量
        mTabVisibleCount = ta.getInteger(R.styleable.TabLayoutLine_selectedNum, 2);
        //异步修改Tab宽度
        post(new Runnable() {
            @Override
            public void run() {
                resetTabParams();
            }
        });
    }

    /**
     * 重绘下标
     */
    public void redrawIndicator(int position, float positionOffset) {
        mTranslationX = (int) ((position + positionOffset) * tabWidth);
        invalidate();
    }

    /**
     * 动态设图
     **/

    public void setmSlideIcon(Bitmap mSlideIcon) {
        this.mSlideIcon = mSlideIcon;
    }

    /**
     * tab的父容器，注意空指针
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    public LinearLayout getTabStrip() {
        Class<?> tabLayout = TabLayout.class;
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        assert tabStrip != null;
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert llTab != null;
        llTab.setClipChildren(false);
        return llTab;
    }

    /**
     * 绘制指示器
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mSlideIcon == null) {
            return;
        }
        canvas.save();
        // 平移到正确的位置，修正tabs的平移量
        canvas.translate(mInitTranslationX + mTranslationX, this.mInitTranslationY);
        canvas.drawBitmap(this.mSlideIcon, 0, 0, null);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    /**
     * 重设tab宽度
     */
    private void resetTabParams() {
        LinearLayout tabStrip = getTabStrip();
        if (tabStrip == null) {
            return;
        }
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            LinearLayout tabView = (LinearLayout) tabStrip.getChildAt(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((mScreenWidth / (mTabVisibleCount)), LinearLayout.LayoutParams
                    .WRAP_CONTENT);
            tabView.setLayoutParams(params);
            //tab中的图标可以超出父容器
            tabView.setClipChildren(false);
            tabView.setClipToPadding(false);

            tabView.setPadding(0, 30, 0, 30);
        }
        initTranslationParams(tabStrip, mScreenWidth);
    }

    /**
     * 初始化三角下标的坐标参数
     */
    private void initTranslationParams(LinearLayout llTab, int screenWidth) {
        if (mSlideIcon == null) {
            return;
        }
        tabWidth = (screenWidth / (mTabVisibleCount));
        View firstView = llTab.getChildAt(0);
        if (firstView != null) {
            this.mInitTranslationX = (firstView.getLeft() + tabWidth / 2 - this.mSlideIcon.getWidth() / 2);
            this.mInitTranslationY = (getBottom() - getTop() - this.mSlideIcon.getHeight());
        }
    }

}
