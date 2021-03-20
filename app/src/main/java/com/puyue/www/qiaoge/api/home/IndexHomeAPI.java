package com.puyue.www.qiaoge.api.home;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.event.CouponListModel;
import com.puyue.www.qiaoge.event.IsTurnModel;
import com.puyue.www.qiaoge.event.PrivacyModel;
import com.puyue.www.qiaoge.event.TurnModel;
import com.puyue.www.qiaoge.event.TurnReceiveModel;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.OrderModel;
import com.puyue.www.qiaoge.model.SendModel;
import com.puyue.www.qiaoge.model.home.CouponModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.HomeNewRecommendModel;
import com.puyue.www.qiaoge.model.home.IndexHomeModel;
import com.puyue.www.qiaoge.model.home.MustModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.home.RecommendModel;
import com.puyue.www.qiaoge.model.home.SpikeNewQueryModel;
import com.puyue.www.qiaoge.model.mine.order.HomeBaseModel;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/17.
 */

public class IndexHomeAPI {
    private interface IndexHomeService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.INDEXHOME)
        Observable<IndexHomeModel> getData(
                @Field("version") String version,
                @Field("clientType") String clientType);
    }

    public static Observable<IndexHomeModel> requestData(Context context, String version, String clientType) {
        IndexHomeService service = RestHelper.getBaseRetrofit(context).create(IndexHomeService.class);
        return service.getData(version, clientType);
    }


    /**
     * 首页banner
     */

    private interface BannerService {
        @POST(AppInterfaceAddress.BANNER)
        Observable<BannerModel> getData();

    }

    public static Observable<BannerModel> getBanner(Context context) {
        BannerService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(BannerService.class);
        return spikeActiveQueryService.getData();
    }


    /**
     * 必买清单
     */

    private interface MustService {
        @POST(AppInterfaceAddress.INDEXMUST)
        Observable<MustModel> getData();

    }

    public static Observable<MustModel> getMust(Context context) {
        MustService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(MustService.class);
        return spikeActiveQueryService.getData();
    }

    /**
     * 必买清单
     */

    private interface Must2Service {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Must_New)
        Observable<ProductNormalModel> getData(@Field("pageNum") int pageNum,@Field("pageSize") int pageSize);

    }

    public static Observable<ProductNormalModel> getMust2(Context context,int pageNum,int pageSize) {
        Must2Service spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(Must2Service.class);
        return spikeActiveQueryService.getData(pageNum,pageSize);
    }

    /**
     * 首页其他信息
     */
    private interface RecommendService {
        @POST(AppInterfaceAddress.INDEXINFO)
        Observable<IndexInfoModel> getData();

    }

    public static Observable<IndexInfoModel> getIndexInfo(Context context) {
        RecommendService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(RecommendService.class);
        return spikeActiveQueryService.getData();
    }

    /**
     * 首页司机信息
     */
    private interface DriverService {
        @POST(AppInterfaceAddress.DISTRIBUTE)
        Observable<DriverInfo> getData();

    }

    public static Observable<DriverInfo> getDriverInfo(Context context) {
        DriverService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(DriverService.class);
        return spikeActiveQueryService.getData();
    }

    /**
     * 折扣、组合数据
     */
    private interface CouponService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.COUPONINFO)
        Observable<CouponModel> getData(@Field("activityType") String activityType);
    }

    public static Observable<CouponModel> getCouponList(Context context,  String activityType) {
        CouponService service = RestHelper.getBaseRetrofit(context).create(CouponService.class);
        return service.getData(activityType);
    }


    /**
     * 首页优惠券弹窗列表
     */
    private interface CouponsService {
        @POST(AppInterfaceAddress.Coupon_List)
        Observable<CouponListModel> getData();

    }

    public static Observable<CouponListModel> getCouponLists(Context context) {
        CouponsService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(CouponsService.class);
        return spikeActiveQueryService.getData();
    }

    /**
     * 首页优惠券关闭弹窗列表
     */
    private interface CouponCloseService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Coupon_Close_List)
        Observable<BaseModel> getData(@Field("id") String id);

    }

    public static Observable<BaseModel> getCouponClose(Context context,String id) {
        CouponCloseService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(CouponCloseService.class);
        return spikeActiveQueryService.getData(id);
    }

    /**
     * 隐私政策  Privacy
     */

    private interface PrivacyService {
        @POST(AppInterfaceAddress.Privacy)
        Observable<PrivacyModel> getData();
    }

    public static Observable<PrivacyModel> getPrivacy(Context context) {
        PrivacyService privacyService = RestHelper.getBaseRetrofit(context).create(PrivacyService.class);
        return privacyService.getData();
    }

    /**
     *
     * 转盘数据
     */
    private interface TurnService {
        @POST(AppInterfaceAddress.Turn_Table)
        Observable<TurnModel> getData();
    }

    public static Observable<TurnModel> getTurn(Context context) {
        TurnService privacyService = RestHelper.getBaseRetrofit(context).create(TurnService.class);
        return privacyService.getData();
    }

    /**
     *
     * 是否显示转盘
     */
    private interface IsTurnService {
        @POST(AppInterfaceAddress.Is_Turn_Table)
        Observable<IsTurnModel> getData();
    }

    public static Observable<IsTurnModel> isTurn(Context context) {
        IsTurnService privacyService = RestHelper.getBaseRetrofit(context).create(IsTurnService.class);
        return privacyService.getData();
    }

    /**
     *
     * 转盘点击领取
     */
    private interface TurnReceiveService {
        @POST(AppInterfaceAddress.Turn_Table_Receive)
        Observable<TurnReceiveModel> getData();
    }

    public static Observable<TurnReceiveModel> TurnReceive(Context context) {
        TurnReceiveService privacyService = RestHelper.getBaseRetrofit(context).create(TurnReceiveService.class);
        return privacyService.getData();
    }

    /**
     * 隐私政策已读  common/readPrivacyPolicy
     */

    private interface ReadService {
        @POST(AppInterfaceAddress.Read_Privacy)
        Observable<BaseModel> getData();
    }


    public static Observable<BaseModel> readPrivacy(Context context) {
        ReadService readService = RestHelper.getBaseRetrofit(context).create(ReadService.class);
        return readService.getData();
    }

    /**
     * 判断地址是否在配送范围内
     */

    private interface SendService {
        @POST(AppInterfaceAddress.Is_Send)
        Observable<SendModel> getData();
    }


    public static Observable<SendModel> isSend(Context context) {
        SendService readService = RestHelper.getBaseRetrofit(context).create(SendService.class);
        return readService.getData();
    }

    /**
     * 首页顶部推荐
     */
    private interface TopRecommendService {
        @POST(AppInterfaceAddress.Recommend_List)
        Observable<RecommendModel> getData();
    }


    public static Observable<RecommendModel> recommendList(Context context) {
        TopRecommendService readService = RestHelper.getBaseRetrofit(context).create(TopRecommendService.class);
        return readService.getData();
    }

    /**
     * 首页顶部分类
     */
    private interface TopClassifyService {
        @POST(AppInterfaceAddress.Index_Classify)
        Observable<IndexInfoModel> getData();
    }


    public static Observable<IndexInfoModel> classifyList(Context context) {
        TopClassifyService readService = RestHelper.getBaseRetrofit(context).create(TopClassifyService.class);
        return readService.getData();
    }

    /**
     * 首页订单信息
     */

    private interface IndexOrderService {
        @POST(AppInterfaceAddress.Index_Order)
        Observable<OrderModel> getData();
    }

    public static Observable<OrderModel> indexOrder(Context context) {
        IndexOrderService readService = RestHelper.getBaseRetrofit(context).create(IndexOrderService.class);
        return readService.getData();
    }


}
