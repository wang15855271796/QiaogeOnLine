package com.puyue.www.qiaoge.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2018/7/6.
 */

public class MarqueeView extends LinearLayout {

    private Context mContext;
    private RecyclerView mRv;
    private MarqueeRunnable marqueeRunnable;
    private static Handler marqueeHandler;

    private static final int MILLISECONDS_MARQUEE_STAND = 3000;
    private static final int MILLISECONDS_MARQUEE_TURNING = 3000;

    private int mStandDuration = MILLISECONDS_MARQUEE_STAND;
    private int mTurningDuration = MILLISECONDS_MARQUEE_TURNING;

    private int mMarqueeHeight = 100;
    private int mScrollItemCount = 1;
    private int mshowItemCount = 1;

    private final boolean DEBUG = true;
    private final String TAG = "MarqueeTag";

    private MarqueeScrollListener marqueeScrollListener;
    private SmoothScrolLinearLayoutManager layoutManager;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MarqueeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }


    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        if (attrs != null) {
            int[] systemAttrs = {android.R.attr.layout_height};
            TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
            int height = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.MATCH_PARENT);
            mMarqueeHeight = height;
            a = context.obtainStyledAttributes(attrs, com.frankfancode.marqueeview.R.styleable.MarqueeViewStyle);
            mScrollItemCount = a.getInteger(com.frankfancode.marqueeview.R.styleable.MarqueeViewStyle_scrollItemCount, 1);
            mshowItemCount = a.getInteger(com.frankfancode.marqueeview.R.styleable.MarqueeViewStyle_showItemCount, 1);
            mStandDuration = a.getInteger(com.frankfancode.marqueeview.R.styleable.MarqueeViewStyle_standDuration, MILLISECONDS_MARQUEE_STAND);
            mTurningDuration = a.getInteger(com.frankfancode.marqueeview.R.styleable.MarqueeViewStyle_turningDuration, MILLISECONDS_MARQUEE_TURNING);
            a.recycle();
        }

        if (DEBUG) {
            Log.i(TAG, "item count " + mScrollItemCount);
        }
        mRv = new RecyclerView(context);
        mRv.setHasFixedSize(true);
        layoutManager = new SmoothScrolLinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);

        marqueeScrollListener = new MarqueeScrollListener();
        mRv.clearOnScrollListeners();
        mRv.addOnScrollListener(marqueeScrollListener);

        marqueeRunnable = new MarqueeRunnable();
        synchronized (this) {
            if (marqueeHandler == null) {
                marqueeHandler = new Handler();
            }
        }
        addView(mRv);
        mRv.getLayoutParams().height = mMarqueeHeight;
    }

    public void startScroll() {
        LinearLayoutManager llm = (LinearLayoutManager) mRv.getLayoutManager();
        if (llm.getItemCount() > mshowItemCount) {
            if (marqueeHandler != null && marqueeRunnable != null) {
                marqueeHandler.removeCallbacks(marqueeRunnable);
                marqueeHandler.postDelayed(marqueeRunnable, mStandDuration);
            }
        } else {
            marqueeHandler.removeCallbacks(marqueeRunnable);
        }
    }

    public void stopScroll() {
        if (marqueeHandler != null && marqueeRunnable != null) {
            marqueeHandler.removeCallbacks(marqueeRunnable);
        }
    }
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRv.setAdapter(adapter);
    }

    private class MarqueeScrollListener extends RecyclerView.OnScrollListener {
        MarqueeScrollListener() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    if (DEBUG) {
                        Log.i(TAG, " sroll state idle ");
                    }
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int itemCount = llm.getItemCount();
                    if (DEBUG) {
                        Log.i(TAG, "itemcount " + itemCount + " showitemcout " + mshowItemCount + " lastvisiable " + llm.findLastVisibleItemPosition());
                    }
                    if (itemCount > mshowItemCount) {
                        if (itemCount == llm.findLastVisibleItemPosition() + 1) {
                            recyclerView.scrollToPosition(0);
                        }
                        startScroll();
                    }
                    break;
            }
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        }
    }


    private void smoothNextPosition(RecyclerView rv) {
        if (rv != null) {
            LinearLayoutManager llm = (LinearLayoutManager) rv.getLayoutManager();

            int lastVisibleItemPosition = llm.findLastVisibleItemPosition();
            int totalCount = llm.getItemCount();
            if (totalCount == lastVisibleItemPosition + 1) {
                rv.scrollToPosition(0);
            }
            int nextPosition = llm.findLastVisibleItemPosition() + mScrollItemCount;
            if (nextPosition < totalCount) {
                rv.smoothScrollToPosition(nextPosition);
            }
        }
    }

    private class MarqueeRunnable implements Runnable {
        @Override
        public void run() {
            smoothNextPosition(mRv);
            if (DEBUG) {
                Log.i(TAG, "smooth next position ");
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            ev.setAction(MotionEvent.ACTION_DOWN);
            mRv.dispatchTouchEvent(ev);
            ev.setAction(MotionEvent.ACTION_UP);
            mRv.dispatchTouchEvent(ev);
        }
        super.dispatchTouchEvent(ev);
        Log.d("swdadwdddddd....",ev.getAction()+"aa");

        return true;

    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:   //表示父类需要
                return false;
            case MotionEvent.ACTION_UP:
                return false;
            default:
                break;
        }
        return true;    //如果设置拦截，除了down,其他都是父类处理
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (marqueeHandler != null) {
            marqueeHandler.removeCallbacks(marqueeRunnable);
            mRv.scrollToPosition(0);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (DEBUG) {
            Log.i(TAG, "onacctached");
        }
        startScroll();
    }

//    private Context mContext;
//    private List<String> notices;
//    private boolean isSetAnimDuration = false;
//    private OnItemClickListener onItemClickListener;
//
//    private int interval = 2000;
//    private int animDuration = 500;
//    private int textSize = 14;
//    private int textColor = 0xffffffff;
//
//    private int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
//    private static final int TEXT_GRAVITY_LEFT = 0, TEXT_GRAVITY_CENTER = 1, TEXT_GRAVITY_RIGHT = 2;
//
//    public MarqueeView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs, 0);
//    }
//
//    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
//        this.mContext = context;
//        if (notices == null) {
//            notices = new ArrayList<>();
//        }
//
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
//        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
//        isSetAnimDuration = typedArray.hasValue(R.styleable.MarqueeViewStyle_mvAnimDuration);
//        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
//        if (typedArray.hasValue(R.styleable.MarqueeViewStyle_mvTextSize)) {
//            textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
//            textSize = px2sp(textSize);
//        }
//        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
//        int gravityType = typedArray.getInt(R.styleable.MarqueeViewStyle_mvGravity, TEXT_GRAVITY_LEFT);
//        switch (gravityType) {
//            case TEXT_GRAVITY_CENTER:
//                gravity = Gravity.CENTER;
//                break;
//            case TEXT_GRAVITY_RIGHT:
//                gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
//                break;
//        }
//        typedArray.recycle();
//
//        setFlipInterval(interval);
//
//        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
//        if (isSetAnimDuration) animIn.setDuration(animDuration);
//        setInAnimation(animIn);
//
//        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
//        if (isSetAnimDuration) animOut.setDuration(animDuration);
//        setOutAnimation(animOut);
//    }
//
//    public void startWithText(final String notice) {
//        if (TextUtils.isEmpty(notice)) return;
//        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                startWithFixedWidth(notice, getWidth());
//            }
//        });
//    }
//
//    private boolean isList = false;
//
//    public void startWithList(List<String> notices) {
//        isList = true;
//        setNotices(notices);
//        start();
//    }
//
//    private void startWithFixedWidth(String notice, int width) {
//        int noticeLength = notice.length();
//        int dpW = px2dip(width);
//        int limit = dpW / textSize;
//        if (dpW == 0) {
//            throw new RuntimeException("Please set MarqueeView width !");
//        }
//
//        if (noticeLength <= limit) {
//            notices.add(notice);
//        } else {
//            int size = noticeLength / limit + (noticeLength % limit != 0 ? 1 : 0);
//            for (int i = 0; i < size; i++) {
//                int startIndex = i * limit;
//                int endIndex = ((i + 1) * limit >= noticeLength ? noticeLength : (i + 1) * limit);
//                notices.add(notice.substring(startIndex, endIndex));
//            }
//        }
//        start();
//    }
//
//    // 启动轮播
//    public boolean start() {
//        if (notices == null || notices.size() == 0) return false;
//        removeAllViews();
//
//        for (int i = 0; i < notices.size(); i++) {
//            final TextView textView = createTextView(notices.get(i), i);
//            final int finalI = i;
//            textView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.onItemClick(finalI, textView);
//                    }
//                }
//            });
//            addView(textView);
//        }
//
//        if (notices.size() > 1) {
//            startFlipping();
//        }
//        return true;
//    }
//
//    private TextView createTextView(String text, int position) {
//        TextView tv = new TextView(mContext);
//        tv.setGravity(gravity);
//        tv.setText(text);
//        tv.setTextColor(textColor);
//        tv.setTextSize(textSize);
//        tv.setSingleLine();
//        if(isList)
//            tv.setEllipsize(TextUtils.TruncateAt.END);
//        tv.setTag(position);
//        return tv;
//    }
//
//    public int getPosition() {
//        return (int) getCurrentView().getTag();
//    }
//
//    public List<String> getNotices() {
//        return notices;
//    }
//
//    public void setNotices(List<String> notices) {
//        this.notices = notices;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.onItemClickListener = onItemClickListener;
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(int position, TextView textView);
//    }
//
//    // 将px值转换为dip或dp值，保证尺寸大小不变
//    public int px2dip(float pxValue) {
//        final float scale = mContext.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//
//    public int px2sp(float pxValue) {
//        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
//        return (int) (pxValue / fontScale + 0.5f);
//    }
}
