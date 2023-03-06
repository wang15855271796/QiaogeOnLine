package com.puyue.www.qiaoge.activity.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.AskActivity;
import com.puyue.www.qiaoge.activity.PlayerActivity;
import com.puyue.www.qiaoge.adapter.SchoolAdapter;
import com.puyue.www.qiaoge.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SchoolActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_school);
    }

    @Override
    public void findViewById() {
    }

    @Override
    public void setViewData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i+"ss");
        }
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        SchoolAdapter schoolAdapter = new SchoolAdapter(R.layout.item_school,list);
        recyclerView.setAdapter(schoolAdapter);

        schoolAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mActivity, PlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setClickEvent() {
        tv_ok.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:
                Intent intent = new Intent(mActivity, AskActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_back:
                finish();
                break;
        }


    }
}
