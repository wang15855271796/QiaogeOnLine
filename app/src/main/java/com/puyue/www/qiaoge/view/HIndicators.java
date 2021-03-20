package com.puyue.www.qiaoge.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.puyue.www.qiaoge.R;

/**
 * Created by ${王涛} on 2020/12/23
 */
public class HIndicators extends View {

    private Paint mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mBgRect = new RectF();
    private Float mRadius = 0f;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mRect = new RectF();
    private int viewWidth = 0;
    private int mBgColor = Color.parseColor("#e5e5e5");
    private int mIndicatorColor = Color.parseColor("#ff4646");
    Float ratio = 0.5f;        //长度比例

    public HIndicators(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public HIndicators(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public void init(AttributeSet attrs){
        @SuppressLint("CustomViewStyleable") TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.lwvWheelView);
        mBgColor = typedArray.getColor(R.styleable.lwvWheelView_HIndicator_hi_indicatorColor, mBgColor);
        mIndicatorColor = typedArray.getColor(R.styleable.lwvWheelView_HIndicator_hi_bgColor, mIndicatorColor);
        typedArray.recycle();
        mBgPaint.setColor(mBgColor);
        mBgPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mIndicatorColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void set(Float value) {
        ratio = value;
        invalidate();
    }
    Float progress = 0f;    //滑动进度比例
    public void setProgress(Float value) {
        progress = value;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        mBgRect.set(0f, 0f, w * 1f, h * 1f);
        mRadius = h / 2f;
    }

    /**
     * 设置指示器背景进度条的颜色
     * @param color 背景色
     */
    public void setBgColor(int color) {
        mBgPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置指示器的颜色
     * @param color 指示器颜色
     */
    public void  setIndicatorColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 绑定recyclerView
     */
    public void bindRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                float offsetX = recyclerView.computeHorizontalScrollOffset();
                float range = recyclerView.computeHorizontalScrollRange();
                float extend = recyclerView.computeHorizontalScrollExtent();
                float progres = offsetX * 1.0f / (range - extend);
                progress = progres;     //设置滚动距离所占比例
                setProgress(progress);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        canvas.drawRoundRect(mBgRect, mRadius, mRadius, mBgPaint);

        //计算指示器的长度和位置
        float leftOffset = viewWidth * (1f - ratio) * progress;
        float left = mBgRect.left + leftOffset;
        float right = left + viewWidth * ratio;
        mRect.set(left, mBgRect.top, right, mBgRect.bottom);

        //绘制指示器
        canvas.drawRoundRect(mRect, mRadius, mRadius, mPaint);
    }


}
