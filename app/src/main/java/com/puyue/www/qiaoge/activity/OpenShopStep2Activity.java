package com.puyue.www.qiaoge.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenShopStep2Activity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_up_business)
    ImageView iv_up_business;
    @BindView(R.id.iv_up_allow)
    ImageView iv_up_allow;
    @BindView(R.id.tv_up_business)
    TextView tv_up_business;
    @BindView(R.id.tv_up_allow)
    TextView tv_up_allow;
    @BindView(R.id.tv_pre)
    TextView tv_pre;
    @BindView(R.id.tv_next)
    TextView tv_next;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_finish_business)
    TextView tv_finish_business;
    @BindView(R.id.tv_finish_allow)
    TextView tv_finish_allow;
    PopupWindow pop;

    String checkNo;
    String applyPhone;
    String licenseUrl;
    String licenseNo;
    String companyName;
    String companyAddress;
    int companyType;
    int licenseLongTerm;
    String licenseValidityStart;
    String licenseValidityEnd;
    String businessUrl;
    String businessValidity;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        checkNo = getIntent().getStringExtra("checkNo");
        applyPhone = getIntent().getStringExtra("applyPhone");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_open_shop_step2);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {

    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_pre.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        iv_up_business.setOnClickListener(this);
        iv_up_allow.setOnClickListener(this);
    }

    int requestCode = 0;
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:

            case R.id.tv_pre:
                finish();
                break;

            case R.id.iv_up_business:
                requestCode = 0;
                if(TextUtils.isEmpty(licenseUrl)) {
                    showPop(requestCode);
                }else {
                    Intent intent = new Intent(mContext,BusinessDetailActivity.class);
                    intent.putExtra("licensePath",licenseUrl);
                    startActivityForResult(intent,3);
                }

                break;

            case R.id.iv_up_allow:
                requestCode = 1;
                if(TextUtils.isEmpty(businessUrl)) {
                    showPop(requestCode);
                }else {
                    Intent intent = new Intent(mContext,AllowDetailActivity.class);
                    intent.putExtra("businessPath",businessUrl);
                    startActivityForResult(intent,4);
                }
                break;

            case R.id.tv_next:
                applyProviderTwo();
                QiaoGeApplication.getInstance().addActivity(this);
                break;
        }
    }

    //申请第二步
    private void applyProviderTwo() {
        RecommendApI.getQualification(mContext,checkNo,applyPhone,licenseUrl,licenseNo,companyName,companyAddress,companyType,licenseLongTerm
                        ,licenseValidityStart,licenseValidityEnd,businessUrl,businessValidity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("wdadwad.....",e.getMessage());
                    }

                    @Override
                    public void onNext(GetAddressModel getAddressModel) {
                        if (getAddressModel.getCode()==1) {
                            Intent intent = new Intent(mContext, OpenShopStep3Activity.class);
                            intent.putExtra("checkNo",checkNo);
                            intent.putExtra("applyPhone",applyPhone);
                            startActivity(intent);
                        } else {
                            ToastUtil.showSuccessMsg(mContext, getAddressModel.getMessage());
                        }
                    }
                });
    }
    //上传
    private void showPop(final int requestCode) {
        View bottomView = View.inflate(mActivity, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = (TextView) bottomView.findViewById(R.id.tv_album);
        TextView mCamera = (TextView) bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(mActivity)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(requestCode);
                        break;

                    case R.id.tv_cancel:
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);

    }

    List<String> picList = new ArrayList<>();
    List<LocalMedia> images = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picList.clear();
        images.clear();
        switch (requestCode) {
            case 0:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath = images.get(0).getCompressPath();
                picList.add(compressPath);
                Glide.with(mActivity).load(compressPath).into(iv_up_business);
                List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);
                upImage(parts,requestCode);
                break;

            case 1:
                images = PictureSelector.obtainMultipleResult(data);
                String compressPath1 = images.get(0).getCompressPath();
                picList.add(compressPath1);
                Glide.with(mActivity).load(compressPath1).into(iv_up_allow);
                List<MultipartBody.Part> parts1 = filesToMultipartBodyParts(picList);
                upImage(parts1,requestCode);
                break;
        }

        if(resultCode == 3) {
            iv_up_business.setVisibility(View.GONE);
            tv_finish_business.setVisibility(View.VISIBLE);
            licenseUrl = data.getStringExtra("licenseUrl");
            licenseNo = data.getStringExtra("licenseNo");
            companyName = data.getStringExtra("companyName");
            companyAddress = data.getStringExtra("companyAddress");
            companyType = data.getIntExtra("companyType",0);
            licenseLongTerm = data.getIntExtra("licenseLongTerm",0);
            licenseValidityStart = data.getStringExtra("licenseValidityStart");
            licenseValidityEnd = data.getStringExtra("licenseValidityEnd");
        }

        if(resultCode == 4) {
            iv_up_allow.setVisibility(View.GONE);
            tv_finish_allow.setVisibility(View.VISIBLE);
            businessUrl = data.getStringExtra("businessUrl");
            businessValidity = data.getStringExtra("businessValidity");
        }

    }

    public void upImage(List<MultipartBody.Part> parts,int requestCode) {
        SendImageAPI.requestImg(mContext, parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendImagesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendImagesModel sendImageModel) {
                        if (sendImageModel.code==1) {

                            if(sendImageModel.data!=null) {
                                switch (requestCode) {
                                    case 0:
                                        licenseUrl = sendImageModel.data.get(0);
                                        break;

                                    case 1:
                                        businessUrl = sendImageModel.data.get(0);
                                        break;
                                }
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext,sendImageModel.message);
                        }
                    }
                });
    }

    public List<MultipartBody.Part> filesToMultipartBodyParts(List<String> localUrls) {
        List<MultipartBody.Part> parts = new ArrayList<>(localUrls.size());
        for (String url : localUrls) {
            File file = new File(url);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("detailFiles", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }


    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }
}
