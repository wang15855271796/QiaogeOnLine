package com.puyue.www.qiaoge.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.dialog.BackDialog;
import com.puyue.www.qiaoge.dialog.BackDialog1;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.utils.Utils;

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
    String licenseUrl;
    String companyName;
    String registerNum;
    String companyAddress;
    int companyType = -1;
    int licenseLongTerm = -1;
    String licenseValidityStart;
    String licenseValidityEnd;
    String checkNo;
    ApplyInfoModel.DataBean applyData;
    int licenseFinish;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        licenseUrl = getIntent().getStringExtra("licensePath");
        checkNo = getIntent().getStringExtra("checkNo");
        licenseFinish = getIntent().getIntExtra("licenseFinish",0);

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
        if(!TextUtils.isEmpty(licenseUrl)) {
            Glide.with(mContext).load(licenseUrl).into(iv_business);
        }
        QiaoGeApplication.getInstance().addActivity(this);
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

        if(!TextUtils.isEmpty(checkNo) && licenseFinish == 1 && TextUtils.isEmpty(SharedPreferencesUtil.getString(mContext,"licenseNo"))) {
            getDetailInfo();
        }


        if(licenseFinish == 0 || licenseFinish == 1 && !TextUtils.isEmpty(SharedPreferencesUtil.getString(mContext,"licenseNo"))) {
            et_register_num.setText(SharedPreferencesUtil.getString(mContext,"licenseNo"));
            et_company_name.setText(SharedPreferencesUtil.getString(mContext,"companyName"));
            et_place_business.setText(SharedPreferencesUtil.getString(mContext,"companyAddress"));
            if(SharedPreferencesUtil.getInt(mContext,"companyType") == 0) {
                //企业
                rb_company.setChecked(true);
            }

            if(SharedPreferencesUtil.getInt(mContext,"companyType") == 1) {
                //个人
                rb_personal.setChecked(true);
            }

            if(SharedPreferencesUtil.getInt(mContext,"licenseLongTerm") == 0) {
                licenseLongTerm = 0;
                ll_time.setVisibility(View.VISIBLE);
                tv_regular.setBackgroundResource(R.drawable.shape_orange27);
                tv_long_term.setBackgroundResource(R.drawable.shape_grey3);
                tv_regular.setTextColor(Color.parseColor("#FF3D03"));
                tv_long_term.setTextColor(Color.parseColor("#414141"));
                tv_start_time.setText(SharedPreferencesUtil.getString(mContext,"licenseValidityStart"));
                tv_end_time.setText(SharedPreferencesUtil.getString(mContext,"licenseValidityEnd"));

            }

            if(SharedPreferencesUtil.getInt(mContext,"licenseLongTerm") == 1) {
                licenseLongTerm = 1;
                ll_time.setVisibility(View.GONE);
                tv_regular.setBackgroundResource(R.drawable.shape_grey3);
                tv_long_term.setBackgroundResource(R.drawable.shape_orange27);
                tv_regular.setTextColor(Color.parseColor("#414141"));
                tv_long_term.setTextColor(Color.parseColor("#FF3D03"));
            }

            if(!TextUtils.isEmpty(SharedPreferencesUtil.getString(mContext,"licenseUrl"))) {
                String licenseUrl = SharedPreferencesUtil.getString(mContext, "licenseUrl");
                Glide.with(mContext).load(licenseUrl).into(iv_business);
            }
        }
    }

    ApplyInfoModel.DataBean data;
    private void getDetailInfo() {
        RecommendApI.getApplyInfo(mContext,checkNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ApplyInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ApplyInfoModel applyInfoModel) {
                        if (applyInfoModel.getCode()==1) {
                            if(applyInfoModel.getData()!=null) {
                                data = applyInfoModel.getData();
                                if(!TextUtils.isEmpty(data.getLicenseNo())) {
                                    et_register_num.setText(data.getLicenseNo());
                                }
                                if(!TextUtils.isEmpty(data.getCompanyName())) {
                                    et_company_name.setText(data.getCompanyName());
                                }
                                if(!TextUtils.isEmpty(data.getCompanyAddress())) {
                                    et_place_business.setText(data.getCompanyAddress());
                                }

                                if(data.getCompanyType() == 0) {
                                    companyType = 0;
                                    rb_company.setChecked(true);
                                }

                                if(data.getCompanyType() == 1) {
                                    companyType = 1;
                                    rb_personal.setChecked(true);
                                }

                                if(data.getLicenseLongTerm() == 0) {
                                    licenseLongTerm = 0;
                                    ll_time.setVisibility(View.VISIBLE);
                                    tv_regular.setBackgroundResource(R.drawable.shape_orange27);
                                    tv_long_term.setBackgroundResource(R.drawable.shape_grey3);
                                    tv_regular.setTextColor(Color.parseColor("#FF3D03"));
                                    tv_long_term.setTextColor(Color.parseColor("#414141"));
                                    tv_start_time.setText(data.getLicenseValidityStart());
                                    tv_end_time.setText(data.getLicenseValidityEnd());
                                }

                                if(data.getLicenseLongTerm() == 1) {
                                    licenseLongTerm = 1;
                                    ll_time.setVisibility(View.GONE);
                                    tv_regular.setBackgroundResource(R.drawable.shape_grey3);
                                    tv_long_term.setBackgroundResource(R.drawable.shape_orange27);
                                    tv_regular.setTextColor(Color.parseColor("#414141"));
                                    tv_long_term.setTextColor(Color.parseColor("#FF3D03"));
                                }
                            }
                        } else {
                            ToastUtil.showSuccessMsg(mContext, applyInfoModel.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setClickEvent() {
        iv_back.setOnClickListener(this);
        tv_regular.setOnClickListener(this);
        tv_long_term.setOnClickListener(this);
        iv_business.setOnClickListener(this);
        tv_ok.setOnClickListener(this);
        tv_upload.getBackground().setAlpha(180);
        tv_end_time.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
//                Utils.getLicense(mContext,companyName,companyAddress,registerNum,companyType,licenseLongTerm,
//                        licenseValidityStart,licenseValidityEnd,licenseUrl);
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

            case R.id.iv_business:
                showPop();
                break;

            case R.id.tv_ok:
                if(TextUtils.isEmpty(et_register_num.getText().toString().trim())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写注册号/统一信用代码");
                    return;
                }

                if(TextUtils.isEmpty(et_company_name.getText().toString().trim())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写单位名称");
                    return;
                }

                if(TextUtils.isEmpty(et_place_business.getText().toString().trim())) {
                    ToastUtil.showSuccessMsg(mContext,"请填写经营场所");
                    return;
                }

                if(companyType == -1) {
                    ToastUtil.showSuccessMsg(mContext,"请选择公司类型");
                    return;
                }

                if(licenseLongTerm == -1) {
                    ToastUtil.showSuccessMsg(mContext,"请选择执照有效期");
                    return;
                }

                if(licenseLongTerm == 0) {
                    //营业执照是否长期（否）
                    if(TextUtils.isEmpty(tv_start_time.getText().toString().trim())) {
                        ToastUtil.showSuccessMsg(mContext,"请选择执照起始时间");
                        return;
                    }

                    if(TextUtils.isEmpty(tv_end_time.getText().toString().trim())) {
                        ToastUtil.showSuccessMsg(mContext,"请选择执照结束时间");
                        return;
                    }
                }
                companyName = et_company_name.getText().toString().trim();
                companyAddress = et_place_business.getText().toString().trim();
                registerNum = et_register_num.getText().toString().trim();
                licenseValidityStart = tv_start_time.getText().toString().trim();
                licenseValidityEnd = tv_end_time.getText().toString().trim();

                Intent intent = new Intent();
                intent.putExtra("licenseUrl",licenseUrl);
                intent.putExtra("licenseNo",registerNum);
                intent.putExtra("companyName",companyName);
                intent.putExtra("companyType",companyType);
                intent.putExtra("companyAddress",companyAddress);
                intent.putExtra("licenseLongTerm",licenseLongTerm);
                intent.putExtra("licenseValidityStart",licenseValidityStart);
                intent.putExtra("licenseValidityEnd",licenseValidityEnd);
                setResult(3,intent);

                finish();

                Utils.getLicense(mContext,companyName,companyAddress,registerNum,companyType,licenseLongTerm,
                        licenseValidityStart,licenseValidityEnd,licenseUrl);
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
            if(images!=null && images.size()>0) {
                String compressPath = images.get(0).getCompressPath();
                picList.add(compressPath);
                Glide.with(mActivity).load(compressPath).into(iv_business);

                List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);
                upImage(parts);
            }
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
                            licenseUrl = sendImageModel.data.get(0);

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
