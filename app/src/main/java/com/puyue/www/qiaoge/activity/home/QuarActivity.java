package com.puyue.www.qiaoge.activity.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.QuarAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuarActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    List<String> QuarList;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_quar);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        QuarList = (List<String>) getIntent().getSerializableExtra("quar");

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        QuarAdapter quarAdapter = new QuarAdapter(R.layout.item_quar,QuarList);
        recyclerView.setAdapter(quarAdapter);
        quarAdapter.notifyDataSetChanged();
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
