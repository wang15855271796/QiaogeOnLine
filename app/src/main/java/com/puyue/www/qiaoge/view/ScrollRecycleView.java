package com.puyue.www.qiaoge.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.atomic.AtomicBoolean;

public class ScrollRecycleView extends RecyclerView {

    Thread thread = null;
    AtomicBoolean shouldContinue = new AtomicBoolean(false);
    Handler mHandler;

    public ScrollRecycleView(Context context) {
        super(context);
    }

    public ScrollRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        //主线程的handler，用于执行Marquee的滚动消息
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1://不论是竖直滚动还是水平滚动，都是偏移5个像素
                        ScrollRecycleView.this.smoothScrollBy(15,0);
                        break;
                }
            }
        };
        if (thread == null) {
            thread = new Thread() {
                public void run() {
                    while (shouldContinue.get()) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message msg=mHandler.obtainMessage();
                        msg.what=1;
                        msg.sendToTarget();
                    }
                    //退出循环时清理handler
                    mHandler=null;
                }
            };
        }
    }

    @Override
    /**
     * 在附到窗口的时候开始滚动
     */
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        shouldContinue.set(true);
        init();
        thread.start();
    }

    @Override
    /**
     * 在脱离窗口时处理相关内容
     */
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopMarquee();
    }

    /**
     * 停止滚动
     */
    public void stopMarquee() {
        shouldContinue.set(false);
        thread = null;
    }
    /**
     * Adapter类
     */
//    public static class InnerAdapter extends Adapter<InnerAdapter.MarqueHolder> {
//        private List<String> mData;
//        private int size;
//        private LayoutInflater mLayoutInflater;
//        public InnerAdapter(List<String> data,Context context) {
//            mData = data;
//            size=mData.size();
//            mLayoutInflater=LayoutInflater.from(context);
//        }
//
//        @Override
//        public MarqueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//            View view=mLayoutInflater.inflate(R.layout.marque_item,parent,false);
//
//            return new MarqueHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(MarqueHolder holder, int position) {
//            holder.tv.setText(mData.get(position%size));
//        }
//
//        @Override
//        public int getItemCount() {
//            return Integer.MAX_VALUE;
//        }
//        /**
//         ** ViewHolder类
//         **/
//        static class MarqueHolder extends ViewHolder {
//            TextView tv;
//
//            public MarqueHolder(View view) {
//                super(view);
//                tv = (TextView) view.findViewById(R.id.tv);
//            }
//
//        }
//    }

}
