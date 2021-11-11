package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.AutoPollRecyclerView;

import java.lang.ref.WeakReference;

public class AutosRecycleView extends RecyclerView {

    private static final long TIME_AUTO_POLL = 2000;
    AutoPollTask autoPollTask;
    int index = 0;
    int lastX =0;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false
    public AutosRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutosRecycleView.AutoPollTask(this);
    }
    static class AutoPollTask implements Runnable {
        private final WeakReference<AutosRecycleView> mReference;
        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutosRecycleView reference) {
            this.mReference = new WeakReference<AutosRecycleView>(reference);
        }
        @Override
        public void run() {
            AutosRecycleView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running &&recyclerView.canRun) {
                recyclerView.smoothScrollToPosition(++recyclerView.index);

                recyclerView.postDelayed(recyclerView.autoPollTask,recyclerView.TIME_AUTO_POLL);
            }
        }
    }



    //开启:如果正在运行,先停止->再开启
    public void start() {
        removeCallbacks(autoPollTask);
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask,TIME_AUTO_POLL);
    }
    public void stop(){
        running = false;
        removeCallbacks(autoPollTask);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) ev.getRawX();
                if (running)
                    stop();

                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                int nowX = (int) ev.getRawX();
//                if(lastX-nowX>10) {
//                    smoothScrollToPosition(++index);
//
//                }
//
//                if(nowX-lastX>10) {
//                    smoothScrollToPosition(index ==0 ?0 : --index);
//
//                }
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(ev);
    }

}
