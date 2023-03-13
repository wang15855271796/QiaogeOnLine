package com.puyue.www.qiaoge.activity;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.MyStandardGSYVideoPlayer;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.adapter.PicVideoAdapter;
import com.puyue.www.qiaoge.adapter.SchoolVideoAdapter;
import com.puyue.www.qiaoge.api.home.SchoolVideoApi;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.model.SchoolVideoListModel;
import com.puyue.www.qiaoge.model.VideoDetailModel;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.NumIndicator;
import com.puyue.www.qiaoge.view.VideoPlayerIJK;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class PlayerActivity extends BaseActivity {
    @BindView(R.id.video_player)
    MyStandardGSYVideoPlayer video_player;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    String url;
    String id;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        url = getIntent().getStringExtra("url");
        id = getIntent().getStringExtra("id");
        return false;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.palyer_activity);
    }

    @Override
    public void findViewById() {
        video_player.setUp(url, true, "");
        video_player.startPlayLogic();
    }

    @Override
    public void setViewData() {
        getSchoolVideoDetail();
    }

    @Override
    public void setClickEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSchoolVideoNum();
    }

    private void getSchoolVideoNum() {
        SchoolVideoApi.getVideoNum(mActivity,id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<BaseModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel.code==1) {
//                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                            finish();
                        }else {
//                            ToastUtil.showSuccessMsg(mContext,baseModel.message);
                        }
                    }
                });
    }

    private void getSchoolVideoDetail() {
        SchoolVideoApi.getVideoDetail(mActivity,id)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThread())
                .subscribe(new Subscriber<VideoDetailModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(VideoDetailModel videoDetailModel) {
                        if (videoDetailModel.getCode()==1) {
                            if(videoDetailModel.getData()!=null) {
                                VideoDetailModel.DataBean data = videoDetailModel.getData();
                                tv_time.setText(data.getVideoName());
                                tv_desc.setText(data.getVideoDesc());
                                tv_time.setText(data.getDateTime());
                                tv_num.setText(data.getViewNum());
                            }
                        }else {
                            ToastUtil.showSuccessMsg(mContext,videoDetailModel.getMessage());
                        }
                    }
                });
    }

}
