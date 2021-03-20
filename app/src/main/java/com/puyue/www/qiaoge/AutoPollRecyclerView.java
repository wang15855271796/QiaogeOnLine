package com.puyue.www.qiaoge;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by ${王涛} on 2020/8/18
 */
public class AutoPollRecyclerView extends RecyclerView {
    private static final long TIME_AUTO_POLL = 16;
    AutoPollTask autoPollTask;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false
    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
    }
    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;
        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollRecyclerView reference) {
            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
        }
        @Override
        public void run() {
            AutoPollRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running &&recyclerView.canRun) {
                recyclerView.scrollBy(2, 2);
                recyclerView.postDelayed(recyclerView.autoPollTask,recyclerView.TIME_AUTO_POLL);
            }
        }
    }
    //开启:如果正在运行,先停止->再开启
    public void start() {
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
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }
//    private static final long TIME_AUTO_POLL = 5000;
//    AutoPollTask autoPollTask;
//    int index = 0;
//    int lastX =0;
//    private boolean running; //标示是否正在自动轮询
//    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false
//    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        autoPollTask = new AutoPollTask(this);
//    }
//    static class AutoPollTask implements Runnable {
//        private final WeakReference<AutoPollRecyclerView> mReference;
//        //使用弱引用持有外部类引用->防止内存泄漏
//        public AutoPollTask(AutoPollRecyclerView reference) {
//            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
//        }
//        @Override
//        public void run() {
//            AutoPollRecyclerView recyclerView = mReference.get();
//            if (recyclerView != null && recyclerView.running &&recyclerView.canRun) {
//                recyclerView.smoothScrollToPosition(++recyclerView.index);
//
//                recyclerView.postDelayed(recyclerView.autoPollTask,recyclerView.TIME_AUTO_POLL);
//            }
//        }
//    }
//
//
//
//    //开启:如果正在运行,先停止->再开启
//    public void start() {
//        removeCallbacks(autoPollTask);
//        if (running)
//            stop();
//        canRun = true;
//        running = true;
//        postDelayed(autoPollTask,TIME_AUTO_POLL);
//    }
//    public void stop(){
//        running = false;
//        removeCallbacks(autoPollTask);
//    }
//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                lastX = (int) ev.getRawX();
//                if (running)
//                    stop();
//
//                if (running)
//                    stop();
//                break;
//            case MotionEvent.ACTION_UP:
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_OUTSIDE:
//                int nowX = (int) ev.getRawX();
////                if(lastX-nowX>10) {
////                    smoothScrollToPosition(++index);
////
////                }
////
////                if(nowX-lastX>10) {
////                    smoothScrollToPosition(index ==0 ?0 : --index);
////
////                }
//                if (canRun)
//                    start();
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

}
