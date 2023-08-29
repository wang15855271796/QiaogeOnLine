package com.puyue.www.qiaoge.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.QiaoGeApplication;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.RoundImageView;
import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllowDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_business)
    RoundImageView iv_business;
    @BindView(R.id.tv_valid_time)
    TextView tv_valid_time;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_upload)
    TextView tv_upload;

    PopupWindow pop;
    String businessUrl;
    String businessValidity;

    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        businessUrl = getIntent().getStringExtra("businessPath");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_allow_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        Glide.with(mContext).load(businessUrl).into(iv_business);
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_upload.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_valid_time.setOnClickListener(this);
        tv_upload.getBackground().setAlpha(180);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_valid_time:
                showDatePickView();
                break;

            case R.id.tv_upload:
                showPop();
                break;

            case R.id.tv_ok:
                businessValidity = tv_valid_time.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("businessUrl",businessUrl);
                intent.putExtra("businessValidity",businessValidity);
                setResult(4,intent);
                finish();
                QiaoGeApplication.getInstance().addActivity(this);
                break;
        }
    }

    @SuppressLint("ResourceType")
    private void showDatePickView() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(mContext,3, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                int newMonth = monthOfYear+1;
                tv_valid_time.setText(year+"-"+newMonth +"-"+dayOfMonth);
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //上传
    private void showPop() {
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
                                .forResult(1);
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
        if(resultCode == -1) {
            picList.clear();
            images.clear();
            images = PictureSelector.obtainMultipleResult(data);
            String compressPath = images.get(0).getCompressPath();
            picList.add(compressPath);
            Glide.with(mActivity).load(compressPath).into(iv_business);
            List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);
            upImage(parts);
        }

    }

    public void upImage(List<MultipartBody.Part> parts) {
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
                            businessUrl = sendImageModel.data.get(0);
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
