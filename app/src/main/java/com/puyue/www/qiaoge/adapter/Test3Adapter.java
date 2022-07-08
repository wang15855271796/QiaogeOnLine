package com.puyue.www.qiaoge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${王涛} on 2021/6/18
 */
public class Test3Adapter  extends RecyclerView.Adapter<Test3Adapter.MyHolder> {

    private final List<String> list; //定义一个String类型的list数组
    public Test3Adapter(){
        list =  new ArrayList<String>(); //数组初始化
        //用for循环向数组赋值
        for (int i = 0; i < 10; i++) { list.add("头像" + i);}
    }

    //创建一个实例对象，该实例对象继承RecyclerView.ViewHolder
    public class MyHolder extends RecyclerView.ViewHolder {
        public ImageView icon; //声明一个图形变量
        public TextView textView; //声明一个文本变量
        //实现的方法
        public MyHolder(View itemView) {
            super(itemView);
            icon= (ImageView ) itemView.findViewById(R.id.item_iv_icon); //图形控件绑定
            textView= (TextView) itemView.findViewById(R.id.item_tv_title); //文本控件绑定
        }
    }

    //OnCreateViewHolder用来给rv创建缓存的
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //将layout下的item.xml布局文件生成为View控件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test13, parent, false);
        MyHolder holder = new MyHolder (view); //将View转换为MyHolder实例对象，赋给holder
        return holder; //返回MyHolder实例对象
    }

    //数据绑定
    public void onBindViewHolder(MyHolder holder, int i) {
        //holder.textView.setText(list.get(position)); //holder对象的textView变量绑定list数组中的数据
        //holder对象的textView变量绑定list数组中的数据，%是求模运算符
        holder.textView.setText(list.get(i % list.size()));
        holder.icon.setImageResource(R.mipmap.bg_discount); //holder对象的icon变量绑定R.mipmap.tx1图片
    }
    //获取记录数
    public int getItemCount() {
        //return list.size(); //数组长度
        return Integer.MAX_VALUE; //返回最大的整数值
    }
    //实现无限自动循环滚动效果，注意以下两点
    //onBindViewHolder方法中控件绑定数组数据时，索引值后添加"%list.size()"
    //getItemCount()方法返回Integer.MAX_VALUE最大值就能实现无限循了
}
