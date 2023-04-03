package com.puyue.www.qiaoge.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.google.gson.Gson;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.VideoUtil;
import com.luck.picture.lib.PictureMediaScannerConnection;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.animators.AnimationType;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.broadcast.BroadcastAction;
import com.luck.picture.lib.broadcast.BroadcastManager;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.dialog.PictureCustomDialog;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnCallbackListener;
import com.luck.picture.lib.listener.OnCustomCameraInterfaceListener;
import com.luck.picture.lib.listener.OnCustomImagePreviewCallback;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.listener.OnVideoSelectedPlayCallback;
import com.luck.picture.lib.permissions.PermissionChecker;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;
import com.luck.picture.lib.style.PictureSelectorUIStyle;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.luck.picture.lib.tools.MediaUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.luck.picture.lib.tools.ToastUtils;
import com.luck.picture.lib.tools.ValueOf;
import com.puyue.www.qiaoge.R;

import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.adapter.Test1Adapter;
import com.puyue.www.qiaoge.adapter.Test3Adapter;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.InfoPayDialog;
import com.puyue.www.qiaoge.dialog.ShopStyleDialog;
import com.puyue.www.qiaoge.event.InfoPayEvent;
import com.puyue.www.qiaoge.event.MyShopEvent;
import com.puyue.www.qiaoge.event.ShopStyleEvent;
import com.puyue.www.qiaoge.event.WeChatPayEvent;
import com.puyue.www.qiaoge.event.WeChatUnPayEvent;
import com.puyue.www.qiaoge.fragment.market.TestAdapter;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.InfoIsPayModel;
import com.puyue.www.qiaoge.model.InfoPubModel;
import com.puyue.www.qiaoge.model.PayInfoModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.model.mine.order.SendImageModel;
import com.puyue.www.qiaoge.pictureselectordemo.FullyGridLayoutManager;
import com.puyue.www.qiaoge.pictureselectordemo.GridImageAdapter;
import com.puyue.www.qiaoge.pictureselectordemo.ShopImageViewAdapter;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.CascadingMenuPopWindow;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;
import com.puyue.www.qiaoge.view.EasyView;
import com.puyue.www.qiaoge.view.MyScrollView1;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wang.avi.AVLoadingIndicatorView;
import com.yalantis.ucrop.view.OverlayView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ${王涛} on 2019/9/29
 */
public class TestActivity extends BaseSwipeActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rl2)
    RelativeLayout rl2;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.tv_message_style)
    TextView tv_message_style;
    @BindView(R.id.tv_address)
    EditText tv_address;
    @BindView(R.id.tv_sure)
    TextView tv_sure;
    @BindView(R.id.tv_area)
    TextView tv_area;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.lav_activity_loading)
    AVLoadingIndicatorView loadingIndicatorView;
    PopupWindow pop;
    private int maxSelectNum = 6;
    private List<String> picList = new ArrayList();
    private List<LocalMedia> selectList = new ArrayList<>();
    private ShopImageViewAdapter shopImageViewAdapter;
    String returnPic = "";
    String videoUrl = "";
    String videoCoverUrl = "";
    CascadingMenuPopWindow cascadingMenuPopWindow;
    String provinceCode;
    String provinceName;
    String cityName;
    String cityCode;
    String msgId;
    private ProgressDialog progressDialog;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_issue_info);
    }

    @Override
    public void findViewById() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        ButterKnife.bind(this);
    }

    @Override
    public void setViewData() {
        getIsPay1();
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        shopImageViewAdapter = new ShopImageViewAdapter(mContext,onAddPicClickListener);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shopImageViewAdapter);
        shopImageViewAdapter.setOnItemClickListener(new ShopImageViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                List<LocalMedia> selectList = shopImageViewAdapter.getData();
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(pictureType);

                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                            PictureSelector.create(mActivity).externalPicturePreview(position, selectList,position);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(mActivity).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(mActivity).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }

            @Override
            public void deletPic(int position) {
                picsAllList.remove(position);
                upImage(filesToMultipartBodyParts(picsAllList));
            }
        });
        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cascadingMenuPopWindow = new CascadingMenuPopWindow(mActivity, listCity);
                cascadingMenuPopWindow.setMenuViewOnSelectListener(new TestActivity.NMCascadingMenuViewOnSelectListener());
                cascadingMenuPopWindow.showAsDropDown(et, 5, 5);
                cascadingMenuPopWindow.setOutsideTouchable(true);
                cascadingMenuPopWindow.setBackgroundDrawable(new BitmapDrawable());
                cascadingMenuPopWindow.setTouchable(true);
                cascadingMenuPopWindow.setOnDismissListener(new TestActivity.popupDismissListener());
                backgroundAlpha(0.3f);

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
                    getIsPay();
                }
            }
        });

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
        getCityList();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("视频压缩中......");

    }

    private void getIsPay1() {
        InfoListAPI.getIsPay(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoIsPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoIsPayModel infoIsPayModel) {
                        if(infoIsPayModel.getCode()==1) {
                            if(infoIsPayModel.getData().getPayFlag()==1) {
                                //是
                                tv_money.setVisibility(View.VISIBLE);
                                amount = infoIsPayModel.getData().getShouldPayAmt();
                                tv_money.setText("当前收费"+amount+"元/条，若审核未通过将退回");
                            }else {
                                //否
                                tv_money.setVisibility(View.GONE);
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


    private ShopImageViewAdapter.onAddPicClickListener onAddPicClickListener = new ShopImageViewAdapter.onAddPicClickListener() {

        @Override
        public void onAddPicClick() {
            showPop();
            hintKbTwo();
            et.clearFocus();
        }
    };

    private void showPop() {
        View bottomView = View.inflate(TestActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(TestActivity.this)
                                .openGallery(PictureMimeType.ofAll())
                                .maxSelectNum(maxSelectNum - selectList.size())
//                                .maxSelectNum(1)
                                .minSelectNum(1)
                                .maxVideoSelectNum(1)
                                .imageSpanCount(4)
                                .queryMaxFileSize(55)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .compress(true)
                                .isCamera(false)
                                .recordVideoSecond(30)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(TestActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .loadImageEngine(GlideEngine.createGlideEngine())
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


    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    private List<String> coverList = new ArrayList();
    List<String> picsList = new ArrayList<>();
    //记录所有的选中视频和图片
    List<String> picsAllList = new ArrayList<>();
    boolean isHasVideo = false;

    //图片集合
    List<String> listPic = new ArrayList<>();
    //视频集合
    List<String> listVideo = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
//        picList.clear();
        listVideo.clear();
        listPic.clear();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);

                    for (int i = 0; i < images.size(); i++) {
                        if(images.get(i).getRealPath().contains(".mp4")) {
                            if(!videoUrl.equals("") && videoUrl!=null) {
                                ToastUtil.showSuccessMsg(mContext,"只能上传一个视频");
                                return;
                            }else {
                                picsAllList.add(images.get(i).getRealPath());
                            }
                        }else {
                            picsAllList.add(images.get(i).getRealPath());
                        }
                    }

                    for (LocalMedia media : images) {
                        if(media.getRealPath().contains(".mp4")) {
                            listVideo.add(media.getRealPath());
                        }else {
                            listPic.add(media.getRealPath());
                        }
                    }

                    if(listPic.size()>0) {
                        upImage(filesToMultipartBodyParts(listPic));
                    }

                    if(listVideo.size()>0) {
                        upCover(filesToMultipartBodyParts(listVideo));
                    }

                    selectList.addAll(images);
                    shopImageViewAdapter.setList(selectList);
                    shopImageViewAdapter.notifyDataSetChanged();

                    break;
            }
        }
    }


    private void upCover(List<MultipartBody.Part> filesToMultipartBodyParts) {
        SendImageAPI.requestImgDetail(mContext, filesToMultipartBodyParts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SendImageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendImageModel baseModel) {
                        if (baseModel.success) {
                            videoCoverUrl = "";
                            if (baseModel.data != null) {
                                String[] data = baseModel.data;
                                for(String url: data) {
                                    videoCoverUrl = url;
                                    videoUrl = url;
                                }
                            }
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }


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
                            if (baseModel.data != null) {
                                Gson gson = new Gson();
                                for(String datas : baseModel.data)
                                picsList.add(datas);
                                returnPic = gson.toJson(picsList);
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
    @Override
    public void setClickEvent() {

    }

    /**
     * 售卖类型
     * @param shopStyleEvent
     */
    String datum;
    int position = -1;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTotal(ShopStyleEvent shopStyleEvent) {
        datum = shopStyleEvent.getDatum();
        position = shopStyleEvent.getPosition();
        tv_message_style.setText(shopStyleEvent.getDatum());
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

    /**
     * 判断是否需要支付
     */
    String amount;
    InfoPayDialog infoPayDialog;
    private void getIsPay() {
        InfoListAPI.getIsPay(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoIsPayModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoIsPayModel infoIsPayModel) {
                        if(infoIsPayModel.getCode()==1) {
                            if(infoIsPayModel.getData().getPayFlag()==1) {
                                //是
                                tv_money.setVisibility(View.VISIBLE);
                                amount = infoIsPayModel.getData().getShouldPayAmt();
                                infoPayDialog = new InfoPayDialog(mContext,amount);
                                infoPayDialog.show();
                            }else {
                                //否
                                IssueInfo(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                            }
                        }
                    }
                });
    }

    //发布资讯
    private void IssueInfo(String content,String address,String phone) {
        InfoListAPI.InfoIssue(mContext,position,content,returnPic,provinceCode,cityCode,address,phone,videoUrl,videoCoverUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoPubModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(InfoPubModel infoPubModel) {
                        if (infoPubModel.getCode()==1) {
                            ToastUtil.showSuccessMsg(mContext,infoPubModel.getMessage());
                            finish();
                            msgId = infoPubModel.getData();
                            EventBus.getDefault().post(new MyShopEvent());
                        } else {
                            AppHelper.showMsg(mContext, infoPubModel.getMessage());
                        }
                    }
                });
    }

    //发布资讯
    private void IssueInfo1(String content,String address,String phone) {
        InfoListAPI.InfoIssue(mContext,position,content,returnPic,provinceCode,cityCode,address,phone,videoUrl,videoCoverUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InfoPubModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(InfoPubModel infoPubModel) {
                        if (infoPubModel.getCode()==1) {
                            msgId = infoPubModel.getData();
                            EventBus.getDefault().post(new MyShopEvent());
                            getPayInfo(flag,amount,msgId);
                        } else {
                            dialog.dismiss();
                            AppHelper.showMsg(mContext, infoPubModel.getMessage());
                        }
                    }
                });
    }

    private class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }

    /**
     * 生成订单信息
     * @param flag
     * @param amount
     * @param msgId
     */
    String payToken;
    String outTradeNo;
    private void getPayInfo(int flag, String amount, String msgId) {
        OrderPayAPI.getPayInfo(mContext,flag,amount,msgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PayInfoModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(PayInfoModel payInfoModel) {
                        if (payInfoModel.getCode()==1) {
                            dialog.dismiss();
                            payToken = payInfoModel.getData().getPayToken();
                            outTradeNo = payInfoModel.getData().getOutTradeNo();
                            if(flag==14) {
                                payAliPay(payInfoModel.getData().getPayToken());
                            }else {
                                //微信 官方
                                weChatPay2(payInfoModel.getData().getPayToken());
                            }
                        } else {
                            dialog.dismiss();
                            AppHelper.showMsg(mContext, payInfoModel.getMessage());
                        }
                    }
                });
    }

    private void payAliPay(String parms) {
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = UnifyPayRequest.CHANNEL_ALIPAY;
        msg.payData = parms;
        UnifyPayPlugin.getInstance(mContext).sendPayRequest(msg);
    }

    private void weChatPay2(String json) {
        try {
            IWXAPI api = WXAPIFactory.createWXAPI(mContext, "wxbc18d7b8fee86977");
            JSONObject obj = new JSONObject(json);
            PayReq request = new PayReq();
            request.appId = obj.optString("appId");
            request.partnerId = obj.optString("mchID");
            request.prepayId = obj.optString("prepayId");
            request.packageValue = obj.optString("pkg");
            request.nonceStr = obj.optString("nonceStr");
            request.timeStamp = obj.optString("timeStamp");
            request.sign = obj.optString("paySign");
            api.sendReq(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付的回调,使用的eventBus.......((‵□′))
     **/
    @Subscribe
    public void onEventMainThread(WeChatPayEvent event) {
        Intent intent = new Intent(mContext, InfoPayResultActivity.class);
        intent.putExtra("payChannel", flag);
        intent.putExtra("outTradeNo", outTradeNo);
        startActivity(intent);
        if(event.getCode().equals("1")) {
            //支付成功
            finish();
        }
        infoPayDialog.dismiss();
        tv_address.clearFocus();
    }

    /**
     * 微信支付的回调,使用的eventBus.......取消支付
     **/
    @Subscribe
    public void onEventMainThread(WeChatUnPayEvent event) {
        Intent intent = new Intent(mContext, InfoPayResultActivity.class);
        intent.putExtra("payChannel", flag);
        intent.putExtra("outTradeNo", outTradeNo);
        startActivity(intent);
//        finish();
        infoPayDialog.dismiss();
        tv_address.clearFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hintKbTwo();
        if(flag == 14) {
            if(outTradeNo!=null) {
                Intent intent = new Intent(mContext, InfoPayResultActivity.class);
                intent.putExtra("payChannel", flag);
                intent.putExtra("outTradeNo", outTradeNo);
                startActivity(intent);
                finish();

            }
        }
    }

    public static Bitmap getBitmapFormUrl(String url) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
        /*getFrameAtTime()--->在setDataSource()之后调用此方法。 如果可能，该方法在任何时间位置找到代表性的帧，
         并将其作为位图返回。这对于生成输入数据源的缩略图很有用。**/
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    int flag;
    LoadingDailog dialog;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(InfoPayEvent infoPayEvent) {
        flag = infoPayEvent.getS();
        LoadingDailog.Builder loadBuilder = new LoadingDailog.Builder(mContext)
                .setMessage("加载中......")
                .setCancelable(false)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        IssueInfo1(et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }
}
