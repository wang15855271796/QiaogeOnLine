package com.puyue.www.qiaoge.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.ShopImageViewsAdapter;
import com.puyue.www.qiaoge.adapter.ShopImageViewssAdapter;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.home.InfoListModel;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.ShopStyleDialog;
import com.puyue.www.qiaoge.event.MyShopEvent;
import com.puyue.www.qiaoge.event.ShopStyleEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.InfoDetailIssueModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.model.mine.order.SendImageModel;
import com.puyue.www.qiaoge.pictureselectordemo.FullyGridLayoutManager;
import com.puyue.www.qiaoge.pictureselectordemo.ShopImageViewAdapter;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.CascadingMenuPopWindow;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2021/1/11
 */
public class IssueEditInfoActivity extends BaseSwipeActivity {
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_message_style)
    TextView tv_message_style;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    String msgId;
    ShopImageViewssAdapter shopImageViewAdapter;
    private List<String> picList = new ArrayList();
    String returnPic;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_issue_edit);
    }

    @Override
    public void findViewById() {
        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        EventBus.getDefault().register(this);
        msgId = getIntent().getStringExtra("msgId");
        position = Integer.parseInt(getIntent().getStringExtra("msgType"));
        getCityList(msgId);
        getCityList();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShopStyleDialog shopStyleDialog = new ShopStyleDialog(mContext);
                shopStyleDialog.show();
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = et_phone.getText().toString();
                int result = checkPhoneNum(phone);
                if (result == 2) {
                    Toast.makeText(getApplicationContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (result == 1) {
                    Toast.makeText(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if(pictureList.size()>0) {
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }else {
                        returnPic = "";
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }
                }
            }
        });
    }

    private int checkPhoneNum(String username){
        if (TextUtils.isEmpty(username)){
            return 2;
        }else if (!username.matches("^[1][0-9]{10}$")){
            return 1;
        }else {
            return 0;
        }
    }
    private void IssueInfo(String msgIds,String returnPic,String content,String address,String phone) {
        InfoListAPI.EditInfo(mContext,msgIds,position,content,returnPic,provinceCode,cityCode,address,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(BaseModel infoListModel) {
                        if (infoListModel.success) {
                            ToastUtil.showSuccessMsg(mContext,infoListModel.message);
                            finish();
                            EventBus.getDefault().post(new MyShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoListModel.message);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    String s1;
    private void upImage(List<MultipartBody.Part> parts) {
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
                    public void onNext(SendImagesModel baseModel) {

                        if (baseModel.success) {
                            returnPic = "";

                            if (baseModel.data != null) {
                                List<String> data = baseModel.data;
                                data.addAll(pictureLists);
                                Gson gson = new Gson();
                                returnPic = gson.toJson(data);
                            }
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
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

    CascadingMenuPopWindow cascadingMenuPopWindow;

    String provinceCode;
    String provinceName;
    String cityName;
    String cityCode;
    @Override
    public void setClickEvent() {
        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cascadingMenuPopWindow = new CascadingMenuPopWindow(mActivity, listCity);
                cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
                cascadingMenuPopWindow.showAsDropDown(et, 5, 5);
                cascadingMenuPopWindow.setOutsideTouchable(true);
                cascadingMenuPopWindow.setBackgroundDrawable(new BitmapDrawable());
                cascadingMenuPopWindow.setTouchable(true);
                cascadingMenuPopWindow.setOnDismissListener(new popupDismissListener());
                backgroundAlpha(0.3f);
            }
        });
    }



    ArrayList<CityChangeModel.DataBean> listCity = new ArrayList<>();
    private void getCityList() {
        CityChangeAPI.requestCity(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CityChangeModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(CityChangeModel cityChangeModel) {
                        if (cityChangeModel.isSuccess()) {
                            listCity.clear();
                            List<CityChangeModel.DataBean> data = cityChangeModel.getData();
                            listCity.addAll(data);

                        } else {
                            AppHelper.showMsg(mContext, cityChangeModel.getMessage());
                        }
                    }
                });
    }


    List<String> pictureList;
    List<String> pictureLists = new ArrayList<>();
    InfoDetailIssueModel.DataBean data;
    List<String> deletPic = new ArrayList<>();
    List<String> deletPics = new ArrayList<>();
    private void getCityList(String msgId) {
        InfoListAPI.InfoDetailIssue(mContext,msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoDetailIssueModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoDetailIssueModel infoListModel) {
                        if (infoListModel.isSuccess()) {
                            data = infoListModel.getData();
                            et.setText(data.getContent());

                            cityCode = data.getCityCode();
                            provinceCode = data.getProvinceCode();
                            tv_area.setText(data.getAreaName()+data.getCityName());
                            tv_message_style.setText(data.getMsgTypeName()+"");
                            et_phone.setText(data.getContactPhone());
                            tv_address.setText(data.getDetailAddress());
                            Gson gson = new Gson();
                            pictureList = data.getPictureList();
                            pictureLists.addAll(data.getPictureList());
//                            for (int i = 0; i < pictureLists.size(); i++) {
//                                Log.d("wdadqwssss.....",pictureLists.get(i)+"bb");
//                            }
                            returnPic = gson.toJson(pictureList);
                            GridLayoutManager manager = new GridLayoutManager(mContext,3);

                            shopImageViewAdapter = new ShopImageViewssAdapter(mActivity,pictureList, new ShopImageViewssAdapter.Onclick() {
                                @Override
                                public void addDialog() {
                                    showPop();
                                }

                                @Override
                                public void deletPic(int pos) {
                                    String removePic = pictureList.remove(pos);
                                    deletPic.add(removePic);
                                    String removes = pictureLists.remove(pos);
                                    deletPics.add(removes);
                                    shopImageViewAdapter.notifyDataSetChanged();
                                    Gson gson1 = new Gson();
                                    returnPic = gson1.toJson(pictureLists);

                                }
                            });

                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(shopImageViewAdapter);


                        } else {
                            AppHelper.showMsg(mContext, infoListModel.getMessage());
                        }
                    }
                });
    }
    PopupWindow pop;
    private int maxSelectNum = 6;
    private List<String> selectList = new ArrayList<>();

    private void showPop() {
        View bottomView = View.inflate(IssueEditInfoActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(IssueEditInfoActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .isCamera(false)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);

                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(IssueEditInfoActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .setOutputCameraPath("/CustomPath")
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
         if(requestCode==PictureConfig.CHOOSE_REQUEST&&resultCode==Activity.RESULT_OK){
            handleImgeOnKitKat(data);
        }
    }

    List<LocalMedia> images;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        images = PictureSelector.obtainMultipleResult(data);

        for (int i = 0; i < images.size(); i++) {
            picList.add(images.get(i).getCompressPath());
        }


        pictureList.add(images.get(0).getCompressPath());
        shopImageViewAdapter.notifyDataSetChanged();
        List<MultipartBody.Part> parts = filesToMultipartBodyParts(picList);

        upImage(parts);
    }


    private void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    String datum;
    int position = -1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(ShopStyleEvent shopStyleEvent) {
        datum = shopStyleEvent.getDatum();
        position = shopStyleEvent.getPosition();
        tv_message_style.setText(shopStyleEvent.getDatum());
    }

    private class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        mActivity.getWindow().setAttributes(lp);
    }

    // 级联菜单选择回调接口
    class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener {
        @Override
        public void getValue(CityChangeModel.DataBean area) {
            provinceName = area.getProvinceName();
            provinceCode = area.getProvinceCode();
        }

        @Override
        public void getValues(CityChangeModel.DataBean.CityNamesBean area) {
            backgroundAlpha(1);
            cityName = area.getCityName();
            tv_area.setText(provinceName+cityName);
            cityCode = area.getCityCode();
        }

        @Override
        public void cloese() {
            backgroundAlpha(1);
        }

    }

}
