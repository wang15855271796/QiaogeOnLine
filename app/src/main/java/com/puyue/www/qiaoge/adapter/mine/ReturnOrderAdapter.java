package com.puyue.www.qiaoge.adapter.mine;

import androidx.annotation.Nullable;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.R;

import java.util.List;

/**
 * Created by ${王文博} on 2019/5/15
 */
public class ReturnOrderAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private TextView mReasonContent;
    public ReturnOrderAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        mReasonContent = helper.getView(R.id.tv_reason_content);
        mReasonContent.setText(item);
    }
}
