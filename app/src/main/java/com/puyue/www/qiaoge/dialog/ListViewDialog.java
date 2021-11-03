package com.puyue.www.qiaoge.dialog;

import android.app.Dialog;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.TimeAdapter;
import com.puyue.www.qiaoge.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ${王涛} on 2020/6/12
 */
public class ListViewDialog extends Dialog {

    Context context;
    public View view;
    public Unbinder binder;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    Onclick onclick;
    public ListViewDialog(Context context, List<String> mlist,Onclick onclick) {
        super(context, R.style.dialog);
        this.context = context;
        this.onclick = onclick;
        this.list = mlist;
        init();

    }


    public void init() {
        view = View.inflate(context, R.layout.dialog_listview, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        binder = ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = Utils.getScreenWidth(context);
        getWindow().setAttributes(attributes);
        TimeAdapter timeAapter = new TimeAdapter(R.layout.item_text,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(timeAapter);

        timeAapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(onclick!=null) {
                    onclick.click(position);
                }
            }
        });

    }

    public interface Onclick {
        void click(int position);
    }

}
