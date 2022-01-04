package com.puyue.www.qiaoge.api.huolala;

import android.content.Context;

import com.puyue.www.qiaoge.api.home.CityChangeAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.AddressListModel;
import com.puyue.www.qiaoge.model.AppointModel;
import com.puyue.www.qiaoge.model.CancelReasonModel;
import com.puyue.www.qiaoge.model.CarPriceModel;
import com.puyue.www.qiaoge.model.CarStyleModel;
import com.puyue.www.qiaoge.model.DealPriceModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoListModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.home.ClickCollectionModel;

import org.json.JSONArray;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class HuolalaAPI {

    //是否已授权
    private interface IsAuthService {
        @POST(AppInterfaceAddress.Is_Authorize)
        Observable<IsAuthModel> getData();
    }

    public static Observable<IsAuthModel> isAuthorize(Context context) {
        IsAuthService service = RestHelper.getBaseRetrofit(context).create(IsAuthService.class);
        return service.getData();
    }

    //车型获取
    public interface CarStyleService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Car_Style)
        Observable<CarStyleModel> getData(@Field("cityId") String cityId);
    }

    public static Observable<CarStyleModel> getCarStyle(Context context, String cityId) {
        CarStyleService service = RestHelper.getBaseRetrofit(context).create(CarStyleService.class);
        return service.getData(cityId);
    }

    //地址检索
    public interface AddressListService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Address_Search)
        Observable<AddressListModel> getData(@Field("address") String address,
                                             @Field("city") String city,
                                             @Field("placeType") int placeType);
    }

    public static Observable<AddressListModel> getAddressList(Context context, String address, String city,int placeType) {
        AddressListService service = RestHelper.getBaseRetrofit(context).create(AddressListService.class);
        return service.getData(address,city,placeType);
    }

    //计价
    public interface DealPriceService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Deal_Price)
        Observable<DealPriceModel> getData(@Field("city_id") String city_id,
                                           @Field("city_info_revision") String city_info_revision,
                                           @Field("order_vehicle_id") String order_vehicle_id,
                                           @Field("coupon_id") String coupon_id,
                                           @Field("spec_req") String spec_req,
                                           @Field("addrInfo") JSONArray addrInfo,
                                           @Field("orderTime") int orderTime,
                                           @Field("reserve_time") String reserve_time);
    }

    public static Observable<DealPriceModel> getPrice(Context context, String city_id, String city_info_revision
            ,String order_vehicle_id,String coupon_id,String spec_req,JSONArray addrInfo,int orderTime,String reserve_time) {
        DealPriceService service = RestHelper.getBaseRetrofit(context).create(DealPriceService.class);
        return service.getData(city_id,city_info_revision,order_vehicle_id,coupon_id,spec_req
                ,addrInfo,orderTime,reserve_time);
    }

    //订单列表
    public interface HuoListService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_list)
        Observable<HuoListModel> getData(@Field("pageSize") int pageSize,
                                         @Field("pageNum") int pageNum,
                                         @Field("state") String state);
    }

    public static Observable<HuoListModel> getHuoList(Context context, int pageSize, int pageNum,String state) {
        HuoListService service = RestHelper.getBaseRetrofit(context).create(HuoListService.class);
        return service.getData(pageSize,pageNum,state);
    }

    //订单详情
    public interface HuoDetailService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Detail)
        Observable<HuoDetailModel> getData(@Field("orderDisplayId") String orderDisplayId);
    }

    public static Observable<HuoDetailModel> getHuoDetail(Context context, String orderDisplayId) {
        HuoDetailService service = RestHelper.getBaseRetrofit(context).create(HuoDetailService.class);
        return service.getData(orderDisplayId);
    }

    //添加小费
    public interface AddTipsService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Add_Tips)
        Observable<BaseModel> getData(@Field("orderDisplayId") String orderDisplayId,
                                           @Field("tips") String tips);
    }

    public static Observable<BaseModel> getTips(Context context, String orderDisplayId,String tips) {
        AddTipsService service = RestHelper.getBaseRetrofit(context).create(AddTipsService.class);
        return service.getData(orderDisplayId,tips);
    }

    //取消订单
    public interface CancelOrderService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cancel_Order)
        Observable<BaseModel> getData(@Field("orderDisplayId") String orderDisplayId,
                                      @Field("reason") String reason);
    }

    public static Observable<BaseModel> cancelOrder(Context context, String orderDisplayId,String reason) {
        CancelOrderService service = RestHelper.getBaseRetrofit(context).create(CancelOrderService.class);
        return service.getData(orderDisplayId,reason);
    }

    //取消原因
    private interface CancelReasonService {
        @POST(AppInterfaceAddress.Cancel_Reason)
        Observable<CancelReasonModel> getData();
    }

    public static Observable<CancelReasonModel> cancelReason(Context context) {
        CancelReasonService service = RestHelper.getBaseRetrofit(context).create(CancelReasonService.class);
        return service.getData();
    }

    //车型费用明细
    public interface CarPriceService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Car_Price)
        Observable<CarPriceModel> getData(@Field("cityId") String cityId,
                                      @Field("orderVehicleId") String orderVehicleId);
    }

    public static Observable<CarPriceModel> getCarPrice(Context context, String cityId, String orderVehicleId) {
        CarPriceService service = RestHelper.getBaseRetrofit(context).create(CarPriceService.class);
        return service.getData(cityId,orderVehicleId);
    }

    //预约时间
    private interface AppointService {
        @POST(AppInterfaceAddress.Cancel_Reason)
        Observable<AppointModel> getData();
    }

    public static Observable<AppointModel> getAppoint(Context context) {
        AppointService service = RestHelper.getBaseRetrofit(context).create(AppointService.class);
        return service.getData();
    }
}
