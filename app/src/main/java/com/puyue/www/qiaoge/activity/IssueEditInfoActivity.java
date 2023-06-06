package com.puyue.www.qiaoge.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.VideoUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnItemClickListener;
import com.luck.picture.lib.style.PictureWindowAnimationStyle;
import com.puyue.www.qiaoge.MyStandardGSYVideoPlayer;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.activity.view.GlideEngine;
import com.puyue.www.qiaoge.adapter.ShopImageViewssAdapter;
import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.api.home.InfoListAPI;
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.dialog.InfoPayDialog;
import com.puyue.www.qiaoge.dialog.ShopStyleDialog;
import com.puyue.www.qiaoge.event.MyShopEvent;
import com.puyue.www.qiaoge.event.ShopStyleEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.InfoDetailIssueModel;
import com.puyue.www.qiaoge.model.SendImagesModel;
import com.puyue.www.qiaoge.model.home.CityChangeModel;
import com.puyue.www.qiaoge.model.mine.order.SendImageModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.CascadingMenuPopWindow;
import com.puyue.www.qiaoge.view.CascadingMenuViewOnSelectListener;
import com.puyue.www.qiaoge.view.VideoPlayerIJK;

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
//    @BindView(R.id.tv_money)
//    TextView tv_money;
    String msgId;
    ShopImageViewssAdapter shopImageViewAdapter;
    private List<String> picList = new ArrayList();
    String returnPic;
    ProgressDialog progressDialog;
    private List<LocalMedia> selectList = new ArrayList<>();
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
        getInfoDetail(msgId);
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
                    if(pictureLists.size()>0) {
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }else {
                        returnPic = "";
                        IssueInfo(msgId,returnPic,et.getText().toString(),tv_address.getText().toString(),et_phone.getText().toString());
                    }
                }
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("视频上传中......");

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
        InfoListAPI.EditInfo(mContext,msgIds,position,content,returnPic,provinceCode,cityCode,address,phone,videoUrl,videoCoverUrl)
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
                                Gson gson = new Gson();
                                picsList.addAll(baseModel.data);
                                pictureLists.addAll(baseModel.data);
                                returnPic = gson.toJson(picsList);
                                shopImageViewAdapter.notifyDataSetChanged();
                            }
                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
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
                    retriever.setDataSource(IssueEditInfoActivity.this,parse);
                    int originWidth = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    int originHeight = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                    int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

                    int outWidth = originWidth / 2;
                    int outHeight = originHeight / 2;

                    VideoProcessor.processor(getApplicationContext())
                            .input(parse)
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
        coverList.add(videoPath);
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


    List<String> pictureLists = new ArrayList<>();
    InfoDetailIssueModel.DataBean data;
    //单独记录图片的集合
    List<String> picsList = new ArrayList<>();
    private void getInfoDetail(String msgId) {
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

                            pictureLists.addAll(data.getPictureList());
                            picsList.addAll(data.getPictureList());
                            returnPic = gson.toJson(pictureLists);

                            GridLayoutManager manager = new GridLayoutManager(mContext,3);
                            if(data.getVideoUrl()!=null) {
                                videoUrl = data.getVideoUrl();
                            }

                            if(data.getVideoCoverUrl()!=null) {
                                videoCoverUrl = data.getVideoCoverUrl();
                                pictureLists.add(videoCoverUrl);
                            }

                            shopImageViewAdapter = new ShopImageViewssAdapter(mActivity,pictureLists, new ShopImageViewssAdapter.Onclick() {
                                @Override
                                public void addDialog() {
                                    showPop();
                                    hintKbTwo();
                                    et.clearFocus();
                                }

                                @Override
                                public void deletPic(int pos) {
                                    Gson gson1 = new Gson();
                                    if(pictureLists.size()>0) {
                                        String url = pictureLists.get(pos);

                                        if(url.endsWith(".mp4")) {
                                            //删除的是视频
                                            pictureLists.remove(pos);
                                            returnPic = gson1.toJson(pictureLists);
                                            videoCoverUrl = "";
                                            videoUrl = "";
                                        }else {
                                            //删除的是图片
                                            pictureLists.remove(pos);
//                                            picsList.remove(pos);
                                            returnPic = gson1.toJson(picsList);
                                        }
                                    }


                                    shopImageViewAdapter.notifyDataSetChanged();
                                }
                            });
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(shopImageViewAdapter);
                            shopImageViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    if(pictureLists.size()>0) {
                                        AppHelper.showIssueDetailDialog(mActivity, pictureLists, position);
                                    }

//                                    List<LocalMedia> data = shopImageViewAdapter.getData();
//                                    images.get(position).getRealPath()
//                                    if(pictureLists.get(position).contains(".mp4")) {
//                                        PictureSelector.create(mActivity).externalPictureVideo(pictureLists.get(position));
//                                        Log.d("esfzdfdzasda....",pictureLists.get(position));
//                                        MyStandardGSYVideoPlayer myStandardGSYVideoPlayer = new MyStandardGSYVideoPlayer(mActivity);
//                                        myStandardGSYVideoPlayer.setUp(pictureLists.get(position),false,"");
//                                        myStandardGSYVideoPlayer.startPlayLogic();
//                                    }else {
//                                        AppHelper.showIssueDetailDialog(mActivity, pictureLists, position);
//                                    }

//                                    if (data.size() > 0) {
//                                        LocalMedia media = images.get(position);
//                                        String pictureType = media.getMimeType();
//                                        int mediaType = PictureMimeType.getMimeType(pictureType);
//
//                                        switch (mediaType) {
//                                            case 1:
//                                                // 预览图片 可自定长按保存路径
//                                                //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
//                                              PictureSelector.create(ShopDetailActivity.this).externalPictureVideo(pictureList.get(position));
////                            PictureSelector.create(ShopDetailActivity.this).externalPictureVideo(pictureList.get(position));
//                                                break;
//                                            case 2:
//                                                // 预览视频
//                                                PictureSelector.create(mActivity).externalPictureVideo(media.getPath());
//                                                break;
//                                            case 3:
//                                                // 预览音频
//                                                PictureSelector.create(mActivity).externalPictureAudio(media.getPath());
//                                                break;
//                                        }
//                                    }
                                }
                            });
                        } else {
                            AppHelper.showMsg(mContext, infoListModel.getMessage());
                        }
                    }
                });
    }
    PopupWindow pop;

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
                                .openGallery(PictureMimeType.ofAll())
                                .maxSelectNum(1)
                                .maxVideoSelectNum(1)
//                                .minSelectNum(1)
                                .queryMaxFileSize(55)
                                .imageSpanCount(4)
                                .isCompress(true)
                                .isCamera(false)
                                .loadImageEngine(GlideEngine.createGlideEngine())
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);

                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(IssueEditInfoActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .maxVideoSelectNum(1)
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

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
         if(requestCode==PictureConfig.CHOOSE_REQUEST&&resultCode==Activity.RESULT_OK){
            handleImgeOnKitKat(data);
        }else {
             progressDialog.dismiss();
         }
    }

    String path;
    List<LocalMedia> images = new ArrayList<>();
    private List<String> coverList = new ArrayList();
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        images = PictureSelector.obtainMultipleResult(data);
        picList.clear();
        coverList.clear();
        progressDialog.show();
        for (LocalMedia media : images) {
            path = media.getPath();
            picList.add(media.getRealPath());

            for (String picUrl: pictureLists) {
                if(picUrl.contains(".mp4")) {
                    for (LocalMedia url: images) {
                        if(url.getRealPath().contains(".mp4")) {
                            progressDialog.dismiss();
                            ToastUtil.showSuccessMsg(mContext,"只能上传一个视频");
                            return;
                        }
                    }
                }
            }

            if(media.getMimeType().equals("image/jpeg")) {
                //图片
                upImage(filesToMultipartBodyParts(picList));
                progressDialog.dismiss();
            }else {
                //视频
                coverList.add(media.getRealPath());
                upCover(filesToMultipartBodyParts(coverList));
            }

            selectList.addAll(images);
            shopImageViewAdapter.setList(selectList);
            Log.d("cwdadwd.......",selectList.size()+"a");
        }
    }


    private void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    String videoCoverUrl ="";
    String videoUrl;
    private void upCover(List<MultipartBody.Part> filesToMultipartBodyParts) {
        SendImageAPI.requestImg(mContext, filesToMultipartBodyParts)
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
                            videoCoverUrl = "";
                            Gson gson = new Gson();
                            if (baseModel.data != null) {
                                pictureLists.addAll(baseModel.data);
                                for(String url: baseModel.data) {
                                    videoCoverUrl = url;
                                    videoUrl = url;
                                }
                                returnPic = gson.toJson(picsList);
                                shopImageViewAdapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }

                        } else {
                            AppHelper.showMsg(mContext, baseModel.message);
                        }
                    }
                });
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
