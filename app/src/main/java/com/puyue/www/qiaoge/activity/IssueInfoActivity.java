package com.puyue.www.qiaoge.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.google.gson.Gson;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.VideoUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.mine.login.LogoutsEvent;
import com.puyue.www.qiaoge.activity.mine.order.VipPayResultActivity;
import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.adapter.PayInfoListAdapter;
import com.puyue.www.qiaoge.api.cart.OrderPayAPI;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.InfoPayDialog;
import com.puyue.www.qiaoge.dialog.ShopStyleDialog;
import com.puyue.www.qiaoge.event.InfoPayEvent;
import com.puyue.www.qiaoge.event.MyShopEvent;
import com.puyue.www.qiaoge.event.ShopStyleEvent;
import com.puyue.www.qiaoge.event.WeChatPayEvent;
import com.puyue.www.qiaoge.event.WeChatUnPayEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.InfoIsPayModel;
import com.puyue.www.qiaoge.model.InfoPubModel;
import com.puyue.www.qiaoge.model.PayInfoListModel;
import com.puyue.www.qiaoge.model.PayInfoModel;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.model.mine.order.SendImageModel;
import com.puyue.www.qiaoge.pictureselectordemo.FullyGridLayoutManager;
import com.puyue.www.qiaoge.pictureselectordemo.ShopImageViewAdapter;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.CascadingMenuPopWindow;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by ${王涛} on 2021/1/4
 */
public class IssueInfoActivity extends BaseSwipeActivity {
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
                picList.remove(position);
                upImage(filesToMultipartBodyParts(picList));
            }
        });
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
        View bottomView = View.inflate(IssueInfoActivity.this, R.layout.layout_bottom_dialog, null);
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
                        PictureSelector.create(IssueInfoActivity.this)
                                .openGallery(PictureMimeType.ofAll())
                                .maxSelectNum(maxSelectNum - selectList.size())
                                .minSelectNum(1)
                                .maxVideoSelectNum(1)
                                .imageSpanCount(4)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .compress(true)
                                .isCamera(false)
                                .recordVideoSecond(30)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(IssueInfoActivity.this)
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

    String path;
    Uri selectedVideoUri;
    private List<String> coverList = new ArrayList();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        selectedVideoUri = data.getData();
//         == REQUEST_TAKE_GALLERY_VIDEO

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    images = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(images);
                    for (LocalMedia media : images) {
                        path = media.getPath();
                        picList.add(media.getRealPath());

                        if(media.getRealPath().contains("jpeg") || media.getRealPath().contains("jpg")) {
                            //图片
                            upImage(filesToMultipartBodyParts(picList));
                        }else {
                            //视频
                            coverList.add(media.getRealPath());
                            upCover(filesToMultipartBodyParts(coverList));
//                            String realPath = media.getRealPath();
//                            executeScaleVideo(realPath);
                        }
                    }
                    shopImageViewAdapter.setList(selectList);
                    shopImageViewAdapter.notifyDataSetChanged();

                    break;
            }
        }
    }
    private String filePath;
    private void executeScaleVideo(String selectedVideoUri) {
        File moviesDir = getTempMovieDir();
        progressDialog.show();
        Uri parse = Uri.parse(selectedVideoUri);
        String filePrefix = "scale_video";
        String fileExtn = ".mp4";
        File dest = new File(moviesDir, filePrefix + fileExtn);
        int fileNo = 0;
        while (dest.exists()) {
            fileNo++;
            dest = new File(moviesDir, filePrefix + fileNo + fileExtn);
        }
        filePath = dest.getAbsolutePath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean success = true;
                try {
                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(IssueInfoActivity.this,parse);
                    int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                    int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

                    int outWidth = originWidth / 2;
                    int outHeight = originHeight / 2;

                    VideoProcessor.processor(getApplicationContext())
                            .input(selectedVideoUri)
                            .output(filePath)
                            .outWidth(outWidth)
                            .outHeight(outHeight)
//                            .startTimeMs(startMs)
//                            .endTimeMs(endMs)
                            .bitrate(bitrate / 2)
                            .process();
                } catch (Exception e) {
                    success = false;
                    e.printStackTrace();
                    postError();
                }
                if(success){
                    startPreviewActivity(filePath);
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    private void startPreviewActivity(String videoPath){
        String name = new File(videoPath).getName();
        int end = name.lastIndexOf('.');
        if(end>0){
            name = name.substring(0,end);
        }
        String strUri = VideoUtil.savaVideoToMediaStore(this, videoPath, name, "From VideoProcessor", "video/mp4");
        Log.d("wdasdwsdss....",strUri+"--");
        coverList.add(strUri);
        upCover(filesToMultipartBodyParts(coverList));

    }

    private void postError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "process error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private File getTempMovieDir(){
        File movie = new File(getCacheDir(), "movie");
        movie.mkdirs();
        return movie;
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
                        Log.d("ewdfasda.....",e.getMessage()+"--");
                    }

                    @Override
                    public void onNext(SendImageModel baseModel) {
                        if (baseModel.success) {
                            videoCoverUrl = "";
                            if (baseModel.data != null) {
                                String[] data = baseModel.data;
                                for(String url: data) {
                                    videoCoverUrl = url;
                                }
                            }

                            Log.d("ewdfasda.....",videoCoverUrl+"--");

                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    private void upVideo(List<MultipartBody.Part> filesToMultipartBodyParts) {
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
                            videoUrl = "";
                            if (baseModel.data != null) {
                                String[] data = baseModel.data;
                                for(String url: data) {
                                    videoUrl = url;
                                }
                                Log.d("wddwdasdad....",videoUrl+"videoUrl");
                            }

                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
    }

    private void upImage(List<MultipartBody.Part> parts) {
        SendImageAPI.requestImgDetail(mContext, parts)
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
                            returnPic = "";
                            if (baseModel.data != null) {
                                String[] data = baseModel.data;
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
                                tv_money.setText(infoIsPayModel.getData().getMsg());
                                tv_money.setVisibility(View.VISIBLE);
                                amount = infoIsPayModel.getData().getShouldPayAmt();
                                tv_money.setText("当前收费"+amount+"元/条，若审核未通过将退回");
                                infoPayDialog = new InfoPayDialog(mContext,amount);
                                infoPayDialog.show();
                            }else {
                                //否
                                tv_money.setVisibility(View.GONE);
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
}
