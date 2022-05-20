package com.puyue.www.qiaoge.activity.home;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.puyue.www.qiaoge.R;
import com.puyue.www.qiaoge.UnicornManager;
import com.puyue.www.qiaoge.activity.FullActiveActivity;
import com.puyue.www.qiaoge.activity.HomeActivity;
import com.puyue.www.qiaoge.activity.IntelliGencyInfoActivity;
import com.puyue.www.qiaoge.activity.ShopsActivity;
import com.puyue.www.qiaoge.activity.mine.login.LoginActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterActivity;
import com.puyue.www.qiaoge.activity.mine.login.RegisterMessageActivity;
import com.puyue.www.qiaoge.activity.view.VideoHolder;
import com.puyue.www.qiaoge.adapter.PicVideoAdapter;
import com.puyue.www.qiaoge.adapter.SupplierAdapter;
import com.puyue.www.qiaoge.adapter.cart.ChooseSpecAdapter;
import com.puyue.www.qiaoge.adapter.cart.ImageViewAdapter;
import com.puyue.www.qiaoge.adapter.market.GoodsRecommendAdapter;
import com.puyue.www.qiaoge.api.cart.RecommendApI;
import com.puyue.www.qiaoge.api.home.ClickCollectionAPI;
import com.puyue.www.qiaoge.api.home.GetAllCommentListByPageAPI;
import com.puyue.www.qiaoge.api.home.GetProductDetailAPI;
import com.puyue.www.qiaoge.api.mine.GetShareInfoAPI;
import com.puyue.www.qiaoge.base.BaseActivity;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppConstant;
import com.puyue.www.qiaoge.dialog.ChooseDialog;
import com.puyue.www.qiaoge.dialog.CouponDialog;
import com.puyue.www.qiaoge.dialog.ProductDescDialog;
import com.puyue.www.qiaoge.dialog.PromoteDialog;
import com.puyue.www.qiaoge.event.GoToCartFragmentEvent;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.fragment.cart.NumEvent;
import com.puyue.www.qiaoge.fragment.cart.ReduceNumEvent;
import com.puyue.www.qiaoge.helper.AppHelper;
import com.puyue.www.qiaoge.helper.FVHelper;
import com.puyue.www.qiaoge.helper.PublicRequestHelper;
import com.puyue.www.qiaoge.helper.StringHelper;
import com.puyue.www.qiaoge.helper.UserInfoHelper;
import com.puyue.www.qiaoge.listener.NoDoubleClickListener;
import com.puyue.www.qiaoge.model.PicVideoModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.ChoiceSpecModel;
import com.puyue.www.qiaoge.model.home.ClickCollectionModel;
import com.puyue.www.qiaoge.model.home.GetAllCommentListByPageModel;
import com.puyue.www.qiaoge.model.home.GetProductDetailModel;
import com.puyue.www.qiaoge.model.home.GuessModel;
import com.puyue.www.qiaoge.model.market.GoodsDetailModel;
import com.puyue.www.qiaoge.model.mine.GetShareInfoModle;
import com.puyue.www.qiaoge.utils.LoginUtil;
import com.puyue.www.qiaoge.utils.SharedPreferencesUtil;
import com.puyue.www.qiaoge.utils.Time;
import com.puyue.www.qiaoge.utils.ToastUtil;
import com.puyue.www.qiaoge.view.FlowLayout;
import com.puyue.www.qiaoge.view.NumIndicator;
import com.puyue.www.qiaoge.view.StarBarView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/19.
 * 普通商品详情
 */

public class CommonGoodsDetailActivity extends BaseActivity {
    private ImageView mIvBack;
    private Banner mBanner;
    private TextView mTvTitle;
    private TextView mTvPrice;
    private String productName;
    private ImageView buyImg;
    private LinearLayout mLlCustomer;
    private TextView mTvCollection;
    private ImageView mIvCollection;
    private LinearLayout mLlCar;
    private ImageView mIvCar;
    private TextView mTvCarNum;
    private TextView mTvAmount;
    private TextView mTvFee;
    private TextView mTvAddCar;
    private List<String> images = new ArrayList<>();
    private List<PicVideoModel.DatasBean> picVideo = new ArrayList<>();
    private int productId;
    private int pageNum = 1;
    private int pageSize = 10;
    private byte businessType = 1;
    private boolean isCollection = false;
    private List<ChoiceSpecModel> account = new ArrayList<>();
    private String cell;
    //用户评论
    private TextView userEvaluationNum;
    private TextView goodsEvaluationNumber;
    private TextView goodsEvaluationTime;
    private TextView goodsEvaluationContent;
    private TextView goodsEvaluationReply;
    private StarBarView sbv_star_bar;
    private TextView tv_status;
    //推荐
    private RecyclerView recyclerViewRecommend;
    private GoodsRecommendAdapter adapterRecommend;
    private List<GoodsDetailModel> mListDetailImage = new ArrayList<>();
    private LinearLayout linearLayoutEvaluation;// 评价布局
    private LinearLayout linearLayoutEvaluationNoData;
    private String mShareTitle;
    private String mShareDesc;
    private String mShareIcon;
    private String mShareUrl;
    private LinearLayout linearLayoutOnclick;
    ChooseDialog chooseDialog;
    @BindView(R.id.ll_surp)
    LinearLayout ll_surp;
    @BindView(R.id.ImageViewShare)
    ImageView ImageViewShare;
    @BindView(R.id.tv_come)
    TextView tv_come;
    @BindView(R.id.tv_sale)
    TextView tv_sale;
    @BindView(R.id.fl_container)
    FlowLayout fl_container;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.rv_supplier)
    RecyclerView rv_supplier;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.recyclerViewImage)
    RecyclerView recyclerViewImage;
    @BindView(R.id.iv_flag)
    ImageView iv_flag;
    CouponDialog couponDialog;
    TextView tv_city;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;
    @BindView(R.id.tv_detail)
    TextView tv_detail;
    @BindView(R.id.iv_send)
    ImageView iv_send;
    @BindView(R.id.rl_operate)
    RelativeLayout rl_operate;
    @BindView(R.id.rl_unOperate)
    RelativeLayout rl_unOperate;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_operate_date)
    TextView tv_operate_date;
    @BindView(R.id.tv_full_desc)
    TextView tv_full_desc;
    @BindView(R.id.iv_operate)
    ImageView iv_operate;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.tv_send_area)
    TextView tv_send_area;
    @BindView(R.id.tv_coupon_desc)
    TextView tv_coupon_desc;
    @BindView(R.id.rl_coupons)
    RelativeLayout rl_coupons;
    @BindView(R.id.iv_sound)
    ImageView iv_sound;
    @BindView(R.id.rl_check)
    RelativeLayout rl_check;
    private AlertDialog mTypedialog;
    LinearLayout ll_service;
    TextView tv_price;
    public List<GetProductDetailModel.DataBean.ProdSpecsBean> prodSpecs;
    List<GetProductDetailModel.DataBean.SupProdsBean> surplierList = new ArrayList();
    GuessModel searchResultsModel;
    //猜你喜欢集合
    private List<GuessModel.DataBean> searchList = new ArrayList<>();
    //图片详情集合
    private List<String> detailList = new ArrayList<>();
    private int productId1;
    private ChooseSpecAdapter chooseSpecAdapter;
    private ImageViewAdapter imageViewAdapter;
    String num = null;
    String city;
    String priceType;
    private GetProductDetailModel models;
    RelativeLayout ll_desc;
    RelativeLayout rl_date;
    @Override
    public boolean handleExtra(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if(bundle.getString("num")!=null) {
                num = bundle.getString("num");
            }

            if(bundle.getString("city")!=null) {
                city = bundle.getString("city");
            }

            if(bundle.getString("priceType")!=null) {
                priceType = bundle.getString("priceType");
            }
            productId = bundle.getInt(AppConstant.ACTIVEID);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handleExtra(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_common_details);
    }

    @Override
    public void findViewById() {
        rl_date = FVHelper.fv(this, R.id.rl_date);
        ll_desc = FVHelper.fv(this, R.id.ll_desc);
        tv_price =  FVHelper.fv(this, R.id.tv_price);
        ll_service = FVHelper.fv(this, R.id.ll_service);
        tv_city = FVHelper.fv(this, R.id.tv_city);
        mIvBack = FVHelper.fv(this, R.id.iv_back);
        mBanner = FVHelper.fv(this, R.id.banner_activity_common);
        mTvTitle = FVHelper.fv(this, R.id.tv_activity_common_title);
        mTvPrice = FVHelper.fv(this, R.id.tv_activity_common_price);
        mLlCustomer = FVHelper.fv(this, R.id.ll_include_common_customer);
        mTvCollection = FVHelper.fv(this, R.id.tv_include_common_collection);
        mIvCollection = FVHelper.fv(this, R.id.iv_include_common_collection);
        mLlCar = FVHelper.fv(this, R.id.ll_include_common_car);
        mIvCar = FVHelper.fv(this, R.id.iv_include_common_car);
        mTvCarNum = FVHelper.fv(this, R.id.tv_include_common_car_number);
        mTvAmount = FVHelper.fv(this, R.id.tv_include_common_amount);
        mTvFee = FVHelper.fv(this, R.id.tv_include_common_fee);
        mTvAddCar = FVHelper.fv(this, R.id.tv_add_car);

        userEvaluationNum = (TextView) findViewById(R.id.userEvaluationNum);
        goodsEvaluationNumber = (TextView) findViewById(R.id.goodsEvaluationNumber);
        goodsEvaluationTime = (TextView) findViewById(R.id.goodsEvaluationTime);
        goodsEvaluationContent = (TextView) findViewById(R.id.goodsEvaluationContent);
        goodsEvaluationReply = (TextView) findViewById(R.id.goodsEvaluationReply);
        recyclerViewRecommend = (RecyclerView) findViewById(R.id.recyclerViewRecommend);
        linearLayoutEvaluation = (LinearLayout) findViewById(R.id.linearLayoutEvaluation);
        linearLayoutEvaluationNoData = (LinearLayout) findViewById(R.id.linearLayoutEvaluationNoData);
        linearLayoutOnclick = (LinearLayout) findViewById(R.id.linearLayoutOnclick);
        sbv_star_bar = findViewById(R.id.sbv_star_bar);
        tv_status = findViewById(R.id.tv_status);
        if(city!=null) {
            tv_city.setText("该商品为"+city+"地区商品，请切换到该地区购买");
        }
    }
    SupplierAdapter supplierAdapter;
    @Override
    public void setViewData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //获取数据
        if(num!=null) {
            if(num.equals("-1")) {
                mTvAddCar.setEnabled(false);
                getProductDetail(productId,num, this);
                ll_service.setVisibility(View.GONE);
                mTvAddCar.setText("加入购物车");
                mTvAddCar.setBackgroundResource(R.drawable.app_car_orange);

            }else {
                getProductDetail(productId,num, this);
                ll_service.setVisibility(View.VISIBLE);
                mTvAddCar.setEnabled(false);
                mTvAddCar.setBackgroundResource(R.drawable.app_car);

            }
        }else {
            mTvAddCar.setEnabled(false);
            getProductDetail(productId,num,this);
            mTvAddCar.setText("加入购物车");
            mTvAddCar.setBackgroundResource(R.drawable.app_car_orange);
        }

        getAllCommentList(pageNum, pageSize, productId, businessType);

        adapterRecommend = new GoodsRecommendAdapter(R.layout.item_goods_recommend, searchList);
        LinearLayoutManager linearLayoutManagerCoupons = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewRecommend.setLayoutManager(linearLayoutManagerCoupons);
        recyclerViewRecommend.setAdapter(adapterRecommend);

        supplierAdapter = new SupplierAdapter(R.layout.item_goods_recommend,surplierList);
        rv_supplier.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.HORIZONTAL,false));
        rv_supplier.setAdapter(supplierAdapter);
        mTypedialog = new AlertDialog.Builder(mActivity, R.style.DialogStyle).create();
        mTypedialog.setCancelable(false);

        imageViewAdapter = new ImageViewAdapter(R.layout.item_imageview,detailList);
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerViewImage.setAdapter(imageViewAdapter);

        if(SharedPreferencesUtil.getInt(mActivity,"wad")==1) {
            rl_date.setVisibility(View.GONE);
        }else {
            rl_date.setVisibility(View.VISIBLE);
        }
    }

    long start;
    @Override
    protected void onResume() {
        super.onResume();
        start = System.currentTimeMillis();
        if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
            getCartNum();
        } else {
            mIvCollection.setImageResource(R.mipmap.ic_love);
        }

        if (player != null)
            player.onVideoResume();
    }

    private void getDatas(long end) {
        RecommendApI.getDatas(mContext,14,end)
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
    public void setClickEvent() {
        ImageViewShare.setOnClickListener(noDoubleClickListener);
        mIvCollection.setOnClickListener(noDoubleClickListener);
        mIvBack.setOnClickListener(noDoubleClickListener);
        mTvAddCar.setOnClickListener(noDoubleClickListener);
        mLlCar.setOnClickListener(noDoubleClickListener);
        mLlCustomer.setOnClickListener(noDoubleClickListener);
        linearLayoutOnclick.setOnClickListener(noDoubleClickListener);
        rl_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,QuarActivity.class);
                List<String> quarterPic = models.getData().getQuarterPic();
                intent.putExtra("quar",(Serializable) quarterPic);
                startActivity(intent);

            }
        });
        rl_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FullActiveActivity.class);
                intent.putExtra("fullId",models.getData().getFullId());
                startActivity(intent);
            }
        });


        iv_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                boolean muteFlag = false;//获取当前音乐多媒体是否静音
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    muteFlag = audioManager.isStreamMute(AudioManager.STREAM_MUSIC);
                }
                if(muteFlag){
                    iv_sound.setImageResource(R.mipmap.icon_opens);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE, 0);//取消静音
                }else{
                    iv_sound.setImageResource(R.mipmap.icon_close);
                    audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE , 0);//设为静音
                }
            }
        });
        tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,IntelliGencyInfoActivity.class);
                intent.putExtra("id",models.getData().getSupplierId());
                startActivity(intent);
            }
        });

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromoteDialog promoteDialog = new PromoteDialog(mContext,models.getData().getDivFullGiftSendInfo());
                promoteDialog.show();
            }
        });

        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,ChangeCityActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }


    private NoDoubleClickListener noDoubleClickListener = new NoDoubleClickListener() {
        @Override
        public void onNoDoubleClick(View view) {
            if (view == mIvBack) {
                EventBus.getDefault().post(new NumEvent());
                finish();
            } else if (view == mIvCollection) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if (UserInfoHelper.getUserType(mContext).equals(AppConstant.USER_TYPE_RETAIL)) {
                        //这个用户是零售用户
//                        if ("批发".equals(type)) {

                        if (StringHelper.notEmptyAndNull(cell)) {
                            AppHelper.showAuthorizationDialog(mContext, cell, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (StringHelper.notEmptyAndNull(AppHelper.getAuthorizationCode())) {
                                    } else {
                                        AppHelper.showMsg(mContext, "请输入完整授权码");
                                    }
                                }
                            });
                        }
                    }
                    else {
                        boolean isCollection = SharedPreferencesUtil.getBoolean(mContext, "isCollection");
                        if (isCollection) {
                            //取消收藏
                            clickCollection(productId1, businessType, (byte)2);
                            AppHelper.showMsg(mContext,"取消收藏");
                        } else {
                            clickCollection(productId1, businessType, (byte) 1);
                            AppHelper.showMsg(mContext,"收藏成功");
                        }
                    }
                } else {
                    initDialog();
                }
            }
            else if (view == mTvAddCar) {
                if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(priceType.equals("1")) {
                        if(chooseDialog==null) {
                            chooseDialog = new ChooseDialog(mContext,productId1,models,0);
                        }
                        chooseDialog.show();
                    }else {
//                        showPhoneDialog(cell);
                        AppHelper.ShowAuthDialog(mActivity,cell);
                    }

                }else {
                    initDialog();
                }

            } else if (view == mLlCar) {
                if (StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                    if(UserInfoHelper.getUserType(mContext).equals(AppConstant.USER_TYPE_RETAIL)) {
                        if (StringHelper.notEmptyAndNull(cell)) {
                            AppHelper.showAuthorizationDialog(mContext, cell, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (StringHelper.notEmptyAndNull(AppHelper.getAuthorizationCode()) && AppHelper.getAuthorizationCode().length() == 6) {
                                    } else {
                                        AppHelper.showMsg(mContext, "请输入完整授权码");
                                    }
                                }
                            });
                        }
                    } else if (UserInfoHelper.getUserType(mContext).equals(AppConstant.USER_TYPE_WHOLESALE)) {
                        //这个用户是批发用户
                        // startActivity(new Intent(mContext,HomeActivity.class));
//                        startActivityForResult(new Intent(mContext, CartActivity.class), 21);
                        startActivity(new Intent(mContext, HomeActivity.class));
                        EventBus.getDefault().post(new GoToCartFragmentEvent());
                    }
                } else {
                    initDialog();
                }
            } else if (view == mLlCustomer) {
                if (StringHelper.notEmptyAndNull(cell)) {
                    AppHelper.showPhoneDialog(mContext, cell);
                } else {
                    AppHelper.showMsg(mContext, "获取客服号码失败");
                }
            } else if (view == linearLayoutOnclick) {
                Intent intent = new Intent(CommonGoodsDetailActivity.this, EvaluationActivity.class);
                intent.putExtra("productId", productId);
                intent.putExtra("businessType", businessType);
                startActivity(intent);

            }
            else if (view == ImageViewShare) {
                requestGoodsList();
            }
        }
    };

    /**
     * 授权弹窗
     * @param cell
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
     * 获取详情
     */

    private void getProductDetail(final int productId, String num, CommonGoodsDetailActivity commonGoodsDetailActivity) {
        GetProductDetailAPI.requestData(mContext,productId,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetProductDetailModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetProductDetailModel model) {
                        if (model.isSuccess()) {
                            detailList.clear();
                            detailList.addAll(model.getData().getDetailPic());
                            imageViewAdapter.notifyDataSetChanged();
                            models = model;
                            boolean hasCollect = models.getData().isHasCollect();
                            SharedPreferencesUtil.saveBoolean(mContext,"isCollection",hasCollect);
                            productId1 = model.getData().getProductId();
                            productName = model.getData().getProductName();
                            mTvTitle.setText(productName);
                            cell = model.getData().getCustomerPhone();
                            if(models.getData().getDivFullGiftSendInfo()!=null&&!models.getData().getDivFullGiftSendInfo().equals("")) {
                                rl_coupons.setVisibility(View.VISIBLE);
                                tv_coupon_desc.setText(models.getData().getDivFullGiftSendInfo());
                            }else {
                                rl_coupons.setVisibility(View.GONE);
                            }
                            if(model.getData().getFullGiftSendInfo()!=null&&model.getData().getFullGiftSendInfo().size()>0) {
                                tv_full_desc.setText(model.getData().getFullGiftSendInfo().get(0));
                            }
                            if(models.getData().getSendTimeStr() == null||models.getData().getSendTimeStr().equals("")) {
                                tv_date.setVisibility(View.GONE);
                            }else {
                                tv_date.setText(models.getData().getSendTimeStr());
                                tv_date.setVisibility(View.VISIBLE);
                            }

                            if(models.getData().getQuarterPic()!=null&&models.getData().getQuarterPic().size()>0) {
                                rl_check.setVisibility(View.VISIBLE);
                            }else {
                                rl_check.setVisibility(View.GONE);
                            }
                            if (model.getData().isHasCollect()) {
                                //已收藏
                                mIvCollection.setImageResource(R.mipmap.icon_collection_fill);
                            } else {
                                mIvCollection.setImageResource(R.mipmap.ic_love);
                            }
                            if(model.getData().getSelfProd()!=null) {
                                Glide.with(mContext).load(model.getData().getSelfProd()).into(iv_operate);
                            }

                            tv_come.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext,ShopsActivity.class);
                                    intent.putExtra("surplieId",model.getData().getSupplierId());
                                    startActivity(intent);
                                }
                            });

                            Glide.with(mContext).load(models.getData().getSelfProd()).into(iv2);
                            if("自营商品".equals(model.getData().getCompanyName())) {
                                tv_date.setText(model.getData().getSendTimeStr());
                                tv_address.setText(model.getData().getCompanyName()+"");
                            }else {
                                tv_address.setText(model.getData().getCompanyName());
                                tv_date.setText(model.getData().getSendTimeStr());
                            }

                            if(models.getData().getFullGiftSendInfo()!=null) {
                                rl_coupon.setVisibility(View.VISIBLE);
                            }else {
                                rl_coupon.setVisibility(View.GONE);
                            }

                            //单点不送
                            if(models.getData().getNotSend()!=null) {
                                if(models.getData().getNotSend().equals("1")||models.getData().getNotSend().equals("1.0")) {
                                    iv_send.setImageResource(R.mipmap.icon_not_send2);
                                    iv_send.setVisibility(View.VISIBLE);
                                }else {
                                    iv_send.setVisibility(View.GONE);
                                }
                            }

                            if(models.getData().getAddress().equals("")) {
                                tv_send_area.setText("杭州西湖区 >");
                            }else {
                                tv_send_area.setText(models.getData().getAddress()+" >");

                            }
                            tv_send_area.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent messageIntent = new Intent(mActivity, ChooseAddressActivity.class);
                                    messageIntent.putExtra("cityName",models.getData().getCityName());
                                    messageIntent.putExtra("areaName",models.getData().getAreaName());
                                    messageIntent.putExtra("fromPage","1");
                                    startActivity(messageIntent);
                                }
                            });

                            if(priceType.equals("1")) {
                                mTvPrice.setText(model.getData().getMinMaxPrice());
                                mTvPrice.setVisibility(View.VISIBLE);
                                tv_price.setVisibility(View.GONE);

                            }else {
                                mTvPrice.setVisibility(View.GONE);
                                tv_price.setVisibility(View.VISIBLE);
                            }

                            tv_price.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AppHelper.ShowAuthDialog(mActivity,cell);

                                }
                            });

                            if(model.getData().getTypeUrl()==null||model.getData().getTypeUrl().equals("")) {
                                iv_flag.setVisibility(View.GONE);
                            }else {
                                iv_flag.setVisibility(View.VISIBLE);
                                Glide.with(mContext).load(model.getData().getTypeUrl()).into(iv_flag);
                            }
                            //单点不送
                            tv_sale.setText(model.getData().getSalesVolume());
                            prodSpecs = model.getData().getProdSpecs();
                            if(model.getData().getIntroduction()==null||model.getData().getIntroduction().equals("")) {
                                ll_desc.setVisibility(View.GONE);
                            }else {
                                tv_desc.setText(model.getData().getIntroduction());
                                ll_desc.setVisibility(View.VISIBLE);
                            }
                            tv_desc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ProductDescDialog productDescDialog = new ProductDescDialog(mActivity,model.getData().getIntroduction());
                                    productDescDialog.show();
                                }
                            });
                            chooseSpecAdapter = new ChooseSpecAdapter(mContext,prodSpecs, new ChooseSpecAdapter.Onclick() {
                                @Override
                                public void addDialog(int position) {
                                    if(StringHelper.notEmptyAndNull(UserInfoHelper.getUserId(mContext))) {
                                        if(priceType.equals("1")) {
                                            if(UserInfoHelper.getUserType(mContext).equals(AppConstant.USER_TYPE_RETAIL)) {

                                            }else {
                                                chooseSpecAdapter.selectPosition(position);
                                                if(chooseDialog==null){
                                                    chooseDialog = new ChooseDialog(mContext, productId1,models,position);

                                                }
                                                chooseDialog.show();
                                            }
                                        }else {
//                                            showPhoneDialog(cell);
                                            AppHelper.ShowAuthDialog(mActivity,cell);
                                        }

                                    }else {
                                        initDialog();
                                    }

                                }
                            });

                            fl_container.setAdapter(chooseSpecAdapter);


                            //填充banner
                            images.clear();
//                            mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
//                            mBanner.setImageLoader(new GlideImageLoader());
                            if(model.getData().getTopPic()!=null&&model.getData().getTopPic().size()>0) {
                                images.addAll(model.getData().getTopPic());
                            }else {
                                images.add(model.getData().getDefaultPic());
                            }

                            if(model.getData().getProdVideoUrl()!=null&&!model.getData().getProdVideoUrl().equals("")) {
                                images.add(0,model.getData().getProdVideoUrl());
                                iv_sound.setVisibility(View.VISIBLE);
                            }else {
                                iv_sound.setVisibility(View.GONE);
                            }

                            //banner设置点击监听
                            mBanner.setOnBannerListener(new OnBannerListener() {
                                @Override
                                public void OnBannerClick(Object data, int position) {
                                    AppHelper.showPhotoDetailDialog(mContext, images, position);
                                }
                            });

                            getProductList();
                            mTvAddCar.setEnabled(true);
                            //填充详情
                            mListDetailImage.clear();

                            if(model.getData().getSupplierId()!=null) {
                                ll_surp.setVisibility(View.VISIBLE);
                            }else  {
                                ll_surp.setVisibility(View.GONE);
                            }

                            if(images.size()>0) {
                                for (int i = 0; i < images.size(); i++) {
                                    if(model.getData().getProdVideoUrl()!=null&&!model.getData().getProdVideoUrl().equals("")) {
                                        if(i==0) {
                                            picVideo.add(new PicVideoModel.DatasBean(images.get(0),2));
                                        }else {
                                            picVideo.add(new PicVideoModel.DatasBean(images.get(i),1));
                                        }
                                    } else {
                                        picVideo.add(new PicVideoModel.DatasBean(images.get(i),1));
                                    }
                                }
                            }else {
                                for (int i = 0; i < images.size(); i++) {
                                    picVideo.add(new PicVideoModel.DatasBean(images.get(i),1));
                                }
                            }

                            mBanner.addBannerLifecycleObserver(commonGoodsDetailActivity)
                                    .setAdapter(new PicVideoAdapter(mContext, picVideo))
                                    .setIndicator(new NumIndicator(mContext))
                                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                                    .addOnPageChangeListener(new OnPageChangeListener() {
                                        @Override
                                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                            stopVideo(position);
                                        }

                                        @Override
                                        public void onPageSelected(int position) {
                                            stopVideo(position);
                                        }

                                        @Override
                                        public void onPageScrollStateChanged(int state) {

                                        }
                                    });

                            surplierList.clear();
                            surplierList.addAll(model.getData().getSupProds());
                            supplierAdapter.notifyDataSetChanged();

                        } else {
                            ToastUtil.showErroMsg(mActivity,model.getMessage());
                        }
                    }
                });
    }
    StandardGSYVideoPlayer player;
    private void stopVideo(int position) {
        if (player == null) {
            RecyclerView.ViewHolder viewHolder = mBanner.getAdapter().getViewHolder();
            if (viewHolder instanceof VideoHolder) {
                VideoHolder holder = (VideoHolder) viewHolder;
                player = holder.player;
                if (position != 0) {
                    player.onVideoPause();
                }
            }
        }else {
            if (position != 0) {
                player.onVideoPause();
            }
        }
    }

    private void initDialog() {
        couponDialog = new CouponDialog(mActivity) {
            @Override
            public void Login() {
                startActivity(LoginActivity.getIntent(mActivity, LoginActivity.class));
                dismiss();
            }

            @Override
            public void Register() {
                startActivity(RegisterActivity.getIntent(mActivity, RegisterMessageActivity.class));
                LoginUtil.initRegister(mActivity);
                dismiss();
            }
        };
        couponDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 15) {
            account.clear();
        }
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private float star;

    /**
     * 获取评价
     */
    private void getAllCommentList(final int pageNum, int pageSize, int businessId, byte businessType) {
        GetAllCommentListByPageAPI.requestData(mContext, pageNum, pageSize, businessId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetAllCommentListByPageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetAllCommentListByPageModel model) {
                        if (model.success) {

                            if (model.data.list != null && model.data.list.size() > 0) {

                                linearLayoutEvaluationNoData.setVisibility(View.GONE);
                                linearLayoutEvaluation.setVisibility(View.VISIBLE);
                                goodsEvaluationContent.setText(model.data.list.get(0).commentContent + "");
                                if (model.data.list.get(0).replayContent != null || !TextUtils.isEmpty(model.data.list.get(0).replayContent)) {
                                    goodsEvaluationReply.setText("翘歌客服: " + model.data.list.get(0).replayContent + "");
                                    goodsEvaluationReply.setVisibility(View.VISIBLE);
                                } else {
                                    goodsEvaluationReply.setVisibility(View.GONE);
                                }

                                goodsEvaluationTime.setText(model.data.list.get(0).commentDate + "");
                                goodsEvaluationNumber.setText(model.data.list.get(0).customerPhone);
                                userEvaluationNum.setText("商品评价(" + model.data.total + ")");
                                if (model.data.list.get(0).level != null && StringHelper.notEmptyAndNull(model.data.list.get(0).level)) {
                                    sbv_star_bar.setVisibility(View.VISIBLE);
                                    if (model.data.list.get(0).level.equals("5")) {
                                        star = 5.0f;
                                    } else if (model.data.list.get(0).level.equals("4")) {
                                        star = 4.0f;

                                    } else if (model.data.list.get(0).level.equals("3")) {
                                        star = 3.0f;

                                    } else if (model.data.list.get(0).level.equals("2")) {
                                        star = 2.0f;

                                    } else if (model.data.list.get(0).level.equals("1")) {
                                        star = 1.0f;
                                    }

                                    setStarName(tv_status, star);
                                    sbv_star_bar.setStarRating(star);

                                } else {
                                    sbv_star_bar.setVisibility(View.GONE);
                                }


                                sbv_star_bar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sbv_star_bar.setStarSolid(star);

                                    }
                                });


                            } else {
                                linearLayoutEvaluationNoData.setVisibility(View.VISIBLE);
                                linearLayoutEvaluation.setVisibility(View.GONE);
                            }


                        } else {
                            ToastUtil.showSuccessMsg(mContext, model.message);
                        }
                    }
                });

    }

    /**
     * 设置星星文字
     */
    private void setStarName(TextView tv_content, float star_num) {
        if (star_num == 5.0f) {
            tv_content.setText("很满意");

        } else if (star_num == 4.0f) {
            tv_content.setText("满意");

        } else if (star_num == 3.0f) {
            tv_content.setText("一般");

        } else if (star_num == 2.0f) {
            tv_content.setText("不满意");

        } else if (star_num == 1.0f) {
            tv_content.setText("非常差");

        }

    }

    /**
     * 推荐
     **/
    private void getProductList() {

        RecommendApI.getLikeList(mContext,productId+"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GuessModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GuessModel recommendModel) {
                        if (recommendModel.isSuccess()) {
                            searchResultsModel = recommendModel;
                            if(recommendModel.getData()!=null) {
                                searchList.addAll(recommendModel.getData());
                                adapterRecommend.notifyDataSetChanged();
                            }

                        } else {
                            ToastUtil.showSuccessMsg(mContext, recommendModel.getMessage());
                        }
                    }
                });
    }



    /**
     * 点击收藏
     */
    private void clickCollection(int businessId, byte businessType, byte type) {
        ClickCollectionAPI.requestData(mContext, businessId, businessType, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ClickCollectionModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ClickCollectionModel clickCollectionModel) {
                        if (clickCollectionModel.success) {
                            boolean isCollection = SharedPreferencesUtil.getBoolean(mContext, "isCollection");
                            if (isCollection) {
                                mIvCollection.setImageResource(R.mipmap.ic_love);
                                mTvCollection.setText("取消收藏");
                                SharedPreferencesUtil.saveBoolean(mContext, "isCollection",!isCollection);
                            } else {
                                mIvCollection.setImageResource(R.mipmap.icon_collection_fill);
                                mTvCollection.setText("已收藏");
                                SharedPreferencesUtil.saveBoolean(mContext, "isCollection",!isCollection);
                            }
                        } else {
                            AppHelper.showMsg(mContext, clickCollectionModel.message);
                        }
                    }
                });
    }

    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();// 获得Window界面的最顶层
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        //animLayout.setId();
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vp, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    private ViewGroup anim_mask_layout;

    public void setAnim(final View v, int[] start_location) {


        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);

        // 当前位置
        float[] currentPosition = new float[2];
        int[] controlPosition = new int[2];
        View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
        int[] end_location = new int[2];// 存储动画结束位置的X,Y坐标
        mIvCar.getLocationInWindow(end_location);// 将购物车的位置存储起来
        controlPosition[0] = (start_location[0] + end_location[0]) / 2;
        controlPosition[1] = ((start_location[1] + 100) / 2);

        int total = 0;
        for (int i = 0; i < account.size(); i++) {
            if (account.get(i).totalNum != 0) {
                total += account.get(i).totalNum;
            }
        }


        Path path = new Path();
        path.moveTo(start_location[0], start_location[1]);
        path.quadTo(controlPosition[0], controlPosition[1], end_location[0], end_location[1]);
        PathMeasure pathMeasure = new PathMeasure();
        // false表示path路径不闭合
        pathMeasure.setPath(path, false);
        // ofFloat是一个生成器
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(400);

        valueAnimator.setRepeatCount(0);
        // valueAnimator.setRepeatMode();
        valueAnimator.addUpdateListener(animation -> {
            float value = (Float) animation.getAnimatedValue();
            pathMeasure.getPosTan(value, currentPosition, null);
            buyImg.setX(currentPosition[0]);
            buyImg.setY(currentPosition[1]);
        });
        valueAnimator.start();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                v.setVisibility(View.VISIBLE);


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.setVisibility(View.GONE);

                valueAnimator.cancel();
                animation.cancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });

        ObjectAnimator anim = ObjectAnimator//
                .ofFloat(view, "scale", 1.0F, 1.5F, 1.0f)//
                .setDuration(500);
        anim.setStartDelay(1000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                mIvCar.setScaleX(cVal);
                mIvCar.setScaleY(cVal);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开始轮播
//        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束轮播
//        mBanner.stopAutoPlay();
        long end = (System.currentTimeMillis()-start)/1000;
        long time = Time.getTime(end);
        getDatas(time);

        if (player != null)
            player.onVideoPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        //释放所有
        if (player != null)
            player.setVideoAllCallBack(null);
        super.onBackPressed();
    }

    /**
     * 获取角标数据
     */
    private void getCartNum() {
        PublicRequestHelper.getCartNum(mContext, new OnHttpCallBack<GetCartNumModel>() {
            @Override
            public void onSuccessful(GetCartNumModel getCartNumModel) {
                if (getCartNumModel.isSuccess()) {
                    if (Integer.valueOf(getCartNumModel.getData().getNum()) > 0) {
                        mIvCar.setImageResource(R.mipmap.ic_buy_car_fill);
                        mTvCarNum.setVisibility(View.VISIBLE);
                        mTvCarNum.setText(getCartNumModel.getData().getNum());
                        mTvAmount.setText("￥" + getCartNumModel.getData().getTotalPrice() + "元");
                        mTvAmount.setTextColor(Color.parseColor("#000000"));
                        mTvFee.setVisibility(View.VISIBLE);
                        mTvFee.setText("满" + getCartNumModel.getData().getSendAmount() + "元免配送费");
                    } else {
                        mIvCar.setImageResource(R.mipmap.ic_buy_car);
                        mTvCarNum.setVisibility(View.GONE);
                        mTvAmount.setText("未选购商品");
                        mTvAmount.setTextColor(Color.parseColor("#A7A7A7"));
                        mTvFee.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtil.showSuccessMsg(mContext, getCartNumModel.getMessage());
                }
            }

            @Override
            public void onFaild(String errorMsg) {

            }
        });
    }


    // 分享
    private void showInviteDialog() {
        final Dialog dialog = new Dialog(mContext, R.style.Theme_Light_Dialog);
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_invite, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.dialogStyle);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        LinearLayout mLlInviteQQ = dialogView.findViewById(R.id.ll_invite_dialog_qq);
        LinearLayout mLlInviteWxCircle = dialogView.findViewById(R.id.ll_invite_dialog_wxcircle);
        LinearLayout mLlInviteWeChat = dialogView.findViewById(R.id.ll_invite_dialog_wechat);
        TextView mTvInviteText = dialogView.findViewById(R.id.tv_invite_dialog_text);
        TextView mTvInviteCancel = dialogView.findViewById(R.id.tv_invite_dialog_cancel);
        dialog.show();
        mLlInviteQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMWeb umWeb = new UMWeb(mShareUrl);
                umWeb.setDescription(mShareDesc);
                umWeb.setThumb(new UMImage(CommonGoodsDetailActivity.this, mShareIcon));
                umWeb.setTitle(mShareTitle);

                new ShareAction(CommonGoodsDetailActivity.this)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .withMedia(umWeb)//分享内容
                        .setCallback(umShareListener)//回调监听器
                        .share();
            }
        });


        mLlInviteWeChat.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(mShareTitle)
                        && StringHelper.notEmptyAndNull(mShareDesc)
                        && StringHelper.notEmptyAndNull(mShareIcon)
                        && StringHelper.notEmptyAndNull(mShareUrl)) {

                    UMWeb umWeb = new UMWeb(mShareUrl);
                    umWeb.setDescription(mShareDesc);
                    umWeb.setThumb(new UMImage(CommonGoodsDetailActivity.this, mShareIcon));
                    umWeb.setTitle(mShareTitle);
                    new ShareAction(CommonGoodsDetailActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                            .withMedia(umWeb)//分享内容
                            .setCallback(umShareListener)//回调监听器
                            .share();
                } else {
                    Toast.makeText(CommonGoodsDetailActivity.this, "数据不全!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        mLlInviteWxCircle.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                if (StringHelper.notEmptyAndNull(mShareTitle)
                        && StringHelper.notEmptyAndNull(mShareDesc)
                        && StringHelper.notEmptyAndNull(mShareIcon)
                        && StringHelper.notEmptyAndNull(mShareUrl)) {
                    UMWeb umWeb = new UMWeb(mShareUrl);
                    umWeb.setDescription(mShareDesc);
                    umWeb.setThumb(new UMImage(CommonGoodsDetailActivity.this, mShareIcon));
                    umWeb.setTitle(mShareTitle);
                    new ShareAction(CommonGoodsDetailActivity.this)
                            .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
                            .withMedia(umWeb)//分享内容
                            .setCallback(umShareListener)//回调监听器
                            .share();
                } else {
                    Toast.makeText(CommonGoodsDetailActivity.this, "数据不全!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        mTvInviteCancel.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View view) {
                dialog.dismiss();
            }
        });
    }


    // 获取分享内容
    private void requestGoodsList() {
        GetShareInfoAPI.requestGetShareInfoService(mContext, productId1, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetShareInfoModle>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(GetShareInfoModle getShareInfoModle) {
                        if (getShareInfoModle.isSuccess()) {
                            mShareTitle = getShareInfoModle.getData().getTitle();
                            mShareDesc = getShareInfoModle.getData().getDesc();
                            mShareIcon = getShareInfoModle.getData().getIcon();
                            mShareUrl = getShareInfoModle.getData().getPageUrl();
                            showInviteDialog();

                        }


                    }
                });
    }

    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(CommonGoodsDetailActivity.this, " 收藏成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(CommonGoodsDetailActivity.this, " 分享成功", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEventBuss(ReduceNumEvent event) {
        //刷新UI
        getCartNum();

    }
}
