package com.puyue.www.qiaoge.fragment.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.puyue.www.qiaoge.NewWebViewActivity;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.Test1Activity;
import com.puyue.www.qiaoge.activity.TestActivity;
import com.puyue.www.qiaoge.activity.home.HomeGoodsListActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.home.CommonProductActivity;
import com.puyue.www.qiaoge.adapter.home.HotProductActivity;
import com.puyue.www.qiaoge.adapter.home.ReductionProductActivity;
import com.puyue.www.qiaoge.api.home.IndexInfoModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.utils.LoginUtil;

import java.util.List;

import static cn.com.chinatelecom.account.api.CtAuth.mContext;

public class RvIconAdapter extends BaseQuickAdapter<IndexInfoModel.DataBean.ClassifyListBean,BaseViewHolder> {

    public RvIconAdapter(int layout, List<IndexInfoModel.DataBean.ClassifyListBean> classifyList) {
        super(layout, classifyList);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexInfoModel.DataBean.ClassifyListBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_desc = helper.getView(R.id.tv_desc);
        Glide.with(mContext).load(item.getImg()).into(iv_icon);
        tv_desc.setText(item.getTitle());
        Log.d("wddafvfggf....","add");
    }
}
