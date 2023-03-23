package com.puyue.www.qiaoge.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.puyue.www.qiaoge.api.mine.order.SendImageAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.fragment.market.TestAdapter;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.model.mine.order.SendImageModel;
import com.puyue.www.qiaoge.pictureselectordemo.FullyGridLayoutManager;
import com.puyue.www.qiaoge.pictureselectordemo.GridImageAdapter;
import com.puyue.www.qiaoge.view.EasyView;
import com.puyue.www.qiaoge.view.MyScrollView1;
import com.yalantis.ucrop.view.OverlayView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
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
public class TestActivity extends AppCompatActivity implements View.OnClickListener{

    private final static String TAG = "PictureSelectorTag";
    private GridImageAdapter mAdapter;
    private int maxSelectNum = 9;
    private TextView tv_select_num;
    private TextView tv_original_tips;
    private TextView tvDeleteText;
    private RadioGroup rgb_crop;
    private CheckBox cb_mode;

    private int themeId;
    private int chooseMode = PictureMimeType.ofAll();
    private boolean needScaleBig = true;
    private boolean needScaleSmall = true;
    private int language = -1;
    private int x = 0, y = 0;
    private PictureSelectorUIStyle mSelectorUIStyle;
    private PictureParameterStyle mPictureParameterStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        themeId = R.style.picture_default_style;
        mSelectorUIStyle = PictureSelectorUIStyle.ofDefaultStyle();
        ImageView minus = findViewById(R.id.minus);
        ImageView plus = findViewById(R.id.plus);
        tvDeleteText = findViewById(R.id.tv_delete_text);
        tv_select_num = findViewById(R.id.tv_select_num);
        tv_original_tips = findViewById(R.id.tv_original_tips);
        rgb_crop = findViewById(R.id.rgb_crop);
        cb_mode = findViewById(R.id.cb_mode);
        RecyclerView mRecyclerView = findViewById(R.id.recycler);
        ImageView left_back = findViewById(R.id.left_back);
        left_back.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        tv_select_num.setText(ValueOf.toString(maxSelectNum));
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new GridImageAdapter(getContext(), onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }

        mAdapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                List<LocalMedia> selectList = mAdapter.getData();
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String mimeType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(mimeType);
                    switch (mediaType) {
                        case PictureConfig.TYPE_VIDEO:
                            // 预览视频
                            PictureSelector.create(TestActivity.this)
                                    .themeStyle(R.style.picture_default_style)
                                    .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    .externalPictureVideo(TextUtils.isEmpty(media.getAndroidQToPath()) ? media.getPath() : media.getAndroidQToPath());
                            break;
                        default:
                            // 预览图片 可自定长按保存路径
                            PictureSelector.create(TestActivity.this)
                                    .themeStyle(R.style.picture_default_style) // xml设置主题
                                    .setPictureStyle(mPictureParameterStyle)// 动态自定义相册主题
                                    //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                                    .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                                    .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                                    //.bindCustomPlayVideoCallback(new MyVideoSelectedPlayCallback(getContext()))// 自定义播放回调控制，用户可以使用自己的视频播放界面
                                    .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                                    .openExternalPreview(position, selectList);
                            break;
                    }
                }
            }

            @Override
            public void deletPic(int position) {

            }
        });
//        createActivityResultLauncher();

    }

    private final GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(TestActivity.this)
                    .openGallery(PictureMimeType.ofAll())
                    .maxSelectNum(maxSelectNum)
                    .minSelectNum(1)
                    .maxVideoSelectNum(1)
                    .imageSpanCount(4)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .compress(true)
                    .isCamera(false)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    /**
     * 创建一个ActivityResultLauncher
     *
     * @return
     */
//    private ActivityResultLauncher<Intent> createActivityResultLauncher() {
//        return registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        int resultCode = result.getResultCode();
//                        if (resultCode == RESULT_OK) {
//                            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(result.getData());
//                            mAdapter.setList(selectList);
//                            mAdapter.notifyDataSetChanged();
//                        }
//
//                    }
//                });
//    }

    private List<LocalMedia> selectList = new ArrayList<>();
    String path;
    private List<String> coverList = new ArrayList();
    private List<String> picList = new ArrayList();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
//                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                mAdapter.setList(selectList);
//                mAdapter.notifyDataSetChanged();
//            }
//        }

        List<LocalMedia> images;
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

//                            upImage(filesToMultipartBodyParts(picList));
                        }else {
                            //视频
                            coverList.add(media.getRealPath());
                            List<MultipartBody.Part> parts = filesToMultipartBodyParts(coverList);
//                            upCover(filesToMultipartBodyParts(coverList));
                        }
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();


                    break;
            }
        }

    }

    private void upCover(List<MultipartBody.Part> filesToMultipartBodyParts) {
        SendImageAPI.requestImgDetail(getContext(), filesToMultipartBodyParts)
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
//                            videoCoverUrl = "";
//                            if (baseModel.data != null) {
//                                String[] data = baseModel.data;
//                                for(String url: data) {
//                                    videoCoverUrl = url;
//                                }
//                            }

                        } else {
                            AppHelper.showMsg(getContext(), baseModel.message);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_back:
                finish();
                break;
            case R.id.minus:
                if (maxSelectNum > 1) {
                    maxSelectNum--;
                }
                tv_select_num.setText(maxSelectNum + "");
                mAdapter.setSelectMax(maxSelectNum);
                break;
            case R.id.plus:
                maxSelectNum++;
                tv_select_num.setText(maxSelectNum + "");
                mAdapter.setSelectMax(maxSelectNum);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PictureConfig.APPLY_STORAGE_PERMISSIONS_CODE) {// 存储权限
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
//                    PictureCacheManager.deleteCacheDirFile(getContext(), PictureMimeType.ofImage(), new OnCallbackListener<String>() {
//                        @Override
//                        public void onCall(String absolutePath) {
//                            new PictureMediaScannerConnection(getContext(), absolutePath);
//                            Log.i(TAG, "刷新图库:" + absolutePath);
//                        }
//                    });
                } else {
                    Toast.makeText(TestActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public Context getContext() {
        return this;
    }

}
