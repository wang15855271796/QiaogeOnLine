package com.puyue.www.qiaoge.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.DatePickerView;
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

public class BusinessDetailActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_business)
    RoundImageView iv_business;
    @BindView(R.id.et_register_num)
    EditText et_register_num;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_place_business)
    EditText et_place_business;
    @BindView(R.id.rb_personal)
    RadioButton rb_personal;
    @BindView(R.id.rb_company)
    RadioButton rb_company;
    @BindView(R.id.tv_regular)
    TextView tv_regular;
    @BindView(R.id.tv_long_term)
    TextView tv_long_term;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_ok)
    TextView tv_ok;
    @BindView(R.id.tv_upload)
    TextView tv_upload;
    @BindView(R.id.rg_style)
    RadioGroup rg_style;
    @BindView(R.id.ll_time)
    LinearLayout ll_time;
    PopupWindow pop;
    String licensePath;
    String companyName;
    String registerNum;
    String companyAddress;
    int companyType = -1;
    int licenseLongTerm = -1;
    String licenseValidityStart;
    String licenseValidityEnd;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        licensePath = getIntent().getStringExtra("licensePath");

        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_business_detail);
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void setViewData() {
        Glide.with(mContext).load(licensePath).into(iv_business);
        rg_style.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_personal:
                        companyType = 1;
                        break;

                    case R.id.rb_company:
                        companyType = 0;
                        break;
                }
            }
        });
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_regular.setOnClickListener(this);
        tv_long_term.setOnClickListener(this);
        tv_upload.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_upload.getBackground().setAlpha(180);
        tv_end_time.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;

            case R.id.tv_end_time:
                showDatePickView(1);
                break;
            case R.id.tv_start_time:
                showDatePickView(0);
                break;

            case R.id.tv_regular:
                licenseLongTerm = 0;
                ll_time.setVisibility(View.VISIBLE);
                tv_regular.setBackgroundResource(R.drawable.shape_orange27);
                tv_long_term.setBackgroundResource(R.drawable.shape_grey3);
                tv_regular.setTextColor(Color.parseColor("#FF3D03"));
                tv_long_term.setTextColor(Color.parseColor("#414141"));
                break;

            case R.id.tv_long_term:
                licenseLongTerm = 1;
                licenseValidityStart = "";
                licenseValidityEnd = "";
                ll_time.setVisibility(View.GONE);
                tv_regular.setBackgroundResource(R.drawable.shape_grey3);
                tv_long_term.setBackgroundResource(R.drawable.shape_orange27);
                tv_regular.setTextColor(Color.parseColor("#414141"));
                tv_long_term.setTextColor(Color.parseColor("#FF3D03"));
                break;

            case R.id.tv_upload:
                showPop();
                break;

            case R.id.tv_ok:
                companyName = et_company_name.getText().toString().trim();
                companyAddress = et_place_business.getText().toString().trim();
                registerNum = et_register_num.getText().toString().trim();
                licenseValidityStart = tv_start_time.getText().toString().trim();
                licenseValidityEnd = tv_end_time.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("licenseUrl",licensePath);
                intent.putExtra("licenseNo",registerNum);
                intent.putExtra("companyName",companyName);
                intent.putExtra("companyAddress",companyAddress);
                intent.putExtra("licenseLongTerm",licenseLongTerm);
                intent.putExtra("licenseValidityStart",licenseValidityStart);
                intent.putExtra("licenseValidityEnd",licenseValidityEnd);
                setResult(3,intent);
                QiaoGeApplication.getInstance().addActivity(this);
                finish();
                break;
        }
    }

    @SuppressLint("ResourceType")
    private void showDatePickView(int flag) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(mContext,3, new DatePickerDialog.OnDateSetListener() {
            // 绑定监听器
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                int newMonth = monthOfYear+1;
                if(flag == 0) {
                    tv_start_time.setText(year+"-"+newMonth +"-"+dayOfMonth);
                }else {

                    tv_end_time.setText(year+"-"+newMonth +"-"+dayOfMonth);
                }
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
                            licensePath = sendImageModel.data.get(0);
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
