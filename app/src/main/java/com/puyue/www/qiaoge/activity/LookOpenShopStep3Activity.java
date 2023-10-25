package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LookOpenShopStep3Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_up_front)
    ImageView iv_up_front;
    @BindView(R.id.iv_up_bank)
    ImageView iv_up_bank;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.tv_card)
    TextView tv_card;
    List<String> pictureLists = new ArrayList<>();
    List<String> pictureLists1 = new ArrayList<>();
    String applyPhone;
    ApplyInfoModel.DataBean detailInfo;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        applyPhone = getIntent().getStringExtra("applyPhone");
        detailInfo = (ApplyInfoModel.DataBean)getIntent().getSerializableExtra("detailInfo");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_look_open_shop_step3);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        QiaoGeApplication.getInstance().addActivity(this);
        if(detailInfo!=null) {
            Glide.with(mContext).load(detailInfo.getCorporateCardFront()).into(iv_up_front);
            Glide.with(mContext).load(detailInfo.getCorporateCardReverse()).into(iv_up_bank);
            tv_card.setText(detailInfo.getIdNumber());
            pictureLists.add(detailInfo.getCorporateCardFront());
            pictureLists1.add(detailInfo.getCorporateCardReverse());
        }
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        iv_up_bank.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_up_front.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.iv_up_bank:
                if(pictureLists1.size()>0) {
                    AppHelper.showIssueDetailDialog(mActivity, pictureLists1, 0);
                }
                break;

            case R.id.iv_up_front:
                if(pictureLists.size()>0) {
                    AppHelper.showIssueDetailDialog(mActivity, pictureLists, 0);
                }
                break;

            case R.id.tv_next:
                Intent intent = new Intent(mContext,LookOpenShopStep4Activity.class);
                intent.putExtra("detailInfo", (Serializable) detailInfo);
                intent.putExtra("applyPhone",applyPhone);
                startActivity(intent);
                break;
        }
    }
}
