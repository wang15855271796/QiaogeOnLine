package com.puyue.www.qiaoge.activity.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.adapter.home.SeckillGoodActivity;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveNewAdapter;
import com.puyue.www.qiaoge.adapter.home.SpikeActiveQueryAdapter;
import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.SecKillMoreListAPI;
import com.puyue.www.qiaoge.api.home.SpikeActiveQueryAPI;
import com.puyue.www.qiaoge.api.home.SpikeNewActiveQueryAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.base.BaseSwipeActivity;
import com.puyue.www.qiaoge.calendar.utils.SelectBean;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.fragment.cart.ChangeStatEvent;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.FVHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.OnItemClickListener;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.SeckillListModel;
import com.puyue.www.qiaoge.model.home.SpikeNewQueryModel;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerView1;
import com.puyue.www.qiaoge.view.SnapUpCountDownTimerViewss;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/17.
 */
//列表
public class HomeGoodsListActivity extends BaseSwipeActivity {

    public TextView tv_num;
    private ImageView mIvBack;
    private RecyclerView mRvData;
    private RecyclerView mRvSpikeData;
    //秒杀预告，特惠
    private RelativeLayout linearLayoutSpike;
    //秒杀活动
    private SpikeActiveNewAdapter mAdapterNewSpike;
    private List<SpikeNewQueryModel.DataBean> mListSpikeNew = new ArrayList<>();

    private SpikeActiveQueryAdapter mAdapterSpikeQuery;
    //秒杀列表
    private List<SeckillListModel.DataBean.KillsBean> mListSeckill = new ArrayList<>();
    private int currentPosition = 0;
    private RelativeLayout rl_good_cart;
    SnapUpCountDownTimerViewss snap;
    TextView tv_desc;
    TextView tv_start_time;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onStop() {
        super.onStop();
        long end = (System.currentTimeMillis()-currentTime)/1000;
        long time = Time.getTime(end);
        getDatas(time);

    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,4,end)
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
                    public void onNext(BaseModel baseModel) {

                    }
                });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_home_goods_list);
    }


    @Override
    public void findViewById() {
        tv_start_time = FVHelper.fv(this, R.id.tv_start_time);
        tv_desc = FVHelper.fv(this, R.id.tv_desc);
        snap = FVHelper.fv(this, R.id.snap);
        mIvBack = FVHelper.fv(this, R.id.iv_activity_back);
        tv_num = FVHelper.fv(this, R.id.tv_num);
        mRvData = FVHelper.fv(this, R.id.rv_activity_goods_list);
        linearLayoutSpike = FVHelper.fv(this, R.id.linearLayout_spike);
        mRvSpikeData = FVHelper.fv(this, R.id.recyclerview_spike_content);
        rl_good_cart = FVHelper.fv(this, R.id.rl_good_cart);
        EventBus.getDefault().register(this);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void setViewData() {
        getCustomerPhone();
        getCartNum();
        judgePageType();//进行差异性的设置。
        getNewSpikeTool();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    /**
     * 购物车数量
     */
    private void getCartNum() {
        GetCartNumAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {
                        if (getCartNumModel.isSuccess()) {
                            if(getCartNumModel.getData().getNum().equals("0")) {
                                tv_num.setVisibility(View.GONE);
                            }else {
                                tv_num.setVisibility(View.VISIBLE);

                                tv_num.setText(getCartNumModel.getData().getNum());
                            }
                        } else {
                            AppHelper.showMsg(mContext, getCartNumModel.getMessage());
                        }
                    }
                });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(ReduceNumEvent event) {
        getCartNum();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCartNum(NumEvent event) {
        getCartNum();
    }

    @Override
    public void setClickEvent() {
        rl_good_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mActivity))) {
                    startActivity(new Intent(mContext, HomeActivity.class));
                    EventBus.getDefault().post(new GoToCartFragmentEvent());
                } else {
                    AppHelper.showMsg(mActivity, "请先登录");
                    startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                }


            }
        });
    }

    /**
     * 判断是哪种类型的页面
     **/
    private void judgePageType() {
        linearLayoutSpike.setVisibility(View.VISIBLE);
        mRvData.setLayoutManager(new LinearLayoutManager(mContext));
        mRvData.setBackgroundColor(Color.parseColor("#F5F5F5"));

    }

    /**
     * 秒杀专区更多-顶部
     */
    private void getNewSpikeTool() {
        SpikeNewActiveQueryAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SpikeNewQueryModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
                        if (spikeNewQueryModel.isSuccess()) {
                            mListSpikeNew.clear();
                            if (spikeNewQueryModel.getData() != null) {
                                mListSpikeNew.addAll(spikeNewQueryModel.getData());
                                //秒杀专区-顶部
                                mAdapterNewSpike = new SpikeActiveNewAdapter(mContext, mListSpikeNew);
                                mRvSpikeData.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
                                mRvSpikeData.setAdapter(mAdapterNewSpike);
                                List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
                                if(data.get(currentPosition).getFlag()==1) {
                                    tv_desc.setText("距离本场活动结束");
                                }else {
                                    tv_desc.setText("距离本场活动开始");
                                }
                                tv_start_time.setText("活动开始时间:"+data.get(currentPosition).getDateTime());
                                long startTime = data.get(currentPosition).getStartTime();
                                long endTime = data.get(currentPosition).getEndTime();
                                long currentTime = data.get(currentPosition).getCurrentTime();
                                snap.setTime(false, currentTime, startTime,endTime);
                                snap.start();
                                mAdapterNewSpike.setOnItemClickListener(new OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        getNewSpikeTwo(position);
                                        tv_start_time.setText("活动开始时间: "+data.get(position).getDateTime());
                                    }

                                    @Override
                                    public void onItemLongClick(View view, int position) {

                                    }
                                });

                                //请求列表数据
                                spikeActiveQuery(spikeNewQueryModel.getData().get(currentPosition).getActiveId());
                            }


                        } else {
                            AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
                        }

                    }
                });
    }

    private void getNewSpikeTwo(int pos) {
        SpikeNewActiveQueryAPI.requestData(mContext)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SpikeNewQueryModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SpikeNewQueryModel spikeNewQueryModel) {
                        if (spikeNewQueryModel.isSuccess()) {
                            if (spikeNewQueryModel.getData() != null) {
                                List<SpikeNewQueryModel.DataBean> data = spikeNewQueryModel.getData();
                                mAdapterNewSpike.selectPosition(pos);
                                spikeActiveQuery(mListSpikeNew.get(pos).getActiveId());
                                currentPosition = pos;
                                if(data.get(currentPosition).getFlag()==1) {
                                    tv_desc.setText("距离本场活动结束");
                                }else {
                                    tv_desc.setText("距离本场活动开始");
                                }
                                long startTime = data.get(currentPosition).getStartTime();
                                long endTime = data.get(currentPosition).getEndTime();
                                long currentTime = data.get(currentPosition).getCurrentTime();
                                snap.setTime(false, currentTime, startTime,endTime);
                                snap.start();
                                mAdapterNewSpike.notifyDataSetChanged();
                            }
                        } else {
                            AppHelper.showMsg(mContext, spikeNewQueryModel.getMessage());
                        }

                    }
                });
    }
    /**
     * 秒杀-更多-列表
     */
    private void spikeActiveQuery(int activeId) {
        SecKillMoreListAPI.requestMoreListData(mContext, activeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SeckillListModel>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(SeckillListModel seckillListModel) {
                        if (seckillListModel.success) {
                            mAdapterSpikeQuery = new SpikeActiveQueryAdapter(R.layout.spike_new_active_product, seckillListModel.data.kills, activeId, new SpikeActiveQueryAdapter.Onclick() {
                                @Override
                                public void tipClick() {
                                    AppHelper.ShowAuthDialog(mActivity,cell);
                                }
                            });
                            mRvData.setAdapter(mAdapterSpikeQuery);

                            mListSeckill.clear();
                            if (seckillListModel.data.kills != null) {
                                mListSeckill.addAll(seckillListModel.data.kills);
                            }

                            mAdapterSpikeQuery.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(mContext, SeckillGoodActivity.class);
                                    intent.putExtra(AppConstant.ACTIVEID, mListSeckill.get(position).activeId);
                                    intent.putExtra("priceType",SharedPreferencesUtil.getString(mContext,"priceType"));
                                    intent.putExtra("num","-1");
                                    startActivity(intent);

                                }
                            });

                            int flag = seckillListModel.data.flag;
                            UserInfoHelper.saveSpikePosition(mContext, String.valueOf(flag));

                        } else {

                            AppHelper.showMsg(mActivity, seckillListModel.message);

                        }
                    }
                });
    }

    /**
     * 弹出电话号码
     */
    private AlertDialog mDialog;
    TextView tv_phone;
    TextView tv_time;
    public void showPhoneDialog(final String cell) {
        mDialog = new AlertDialog.Builder(mContext).create();
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_shouye_tip);
        tv_phone = mDialog.getWindow().findViewById(R.id.tv_phone);
        tv_time = mDialog.getWindow().findViewById(R.id.tv_time);
        tv_phone.setText("客服热线 ("+cell+")");

        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + cell));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                mDialog.dismiss();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnicornManager.inToUnicorn(mContext);
                mDialog.dismiss();
            }
        });
    }

    /**
     * @param
     */
    String cell;
    private void getCustomerPhone() {
        PublicRequestHelper.getCustomerPhone(mActivity, new OnHttpCallBack<GetCustomerPhoneModel>() {
            @Override
            public void onSuccessful(GetCustomerPhoneModel getCustomerPhoneModel) {
                if (getCustomerPhoneModel.isSuccess()) {
                    cell = getCustomerPhoneModel.getData();
                } else {
                    AppHelper.showMsg(mActivity, getCustomerPhoneModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {
            }
        });
    }

    /**
     * 获取提醒状态  SpikeActiveQueryAPI  int activeId,SeckillListModel seckillListModel
     */
    private void getStat(int activeId) {
        SpikeActiveQueryAPI.requestData(mContext, activeId)
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
                    public void onNext(BaseModel baseModel) {
                        int warnMe = SharedPreferencesUtil.getInt(mContext, "warnMe");
                        if(baseModel.success) {
                            if(warnMe == 0) {
                                SharedPreferencesUtil.saveInt(mContext,"warnMe",1);
                            }else {
                                SharedPreferencesUtil.saveInt(mContext,"warnMe",0);
                            }

                        }else {
                            AppHelper.showMsg(mActivity, baseModel.message);
                        }

                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SelectBean.cleanDate();
        EventBus.getDefault().unregister(this);

    }
    long currentTime;
    @Override
    protected void onResume() {
        super.onResume();
        currentTime = System.currentTimeMillis();
        getCustomerPhone();
    }



}

