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
import com.puyue.www.qiaoge.model.HasConnectModel;
import com.puyue.www.qiaoge.model.HuoAddressModel;
import com.puyue.www.qiaoge.model.HuoCityIdModel;
import com.puyue.www.qiaoge.model.HuoCityModel;
import com.puyue.www.qiaoge.model.HuoCouponModel;
import com.puyue.www.qiaoge.model.HuoDetailModel;
import com.puyue.www.qiaoge.model.HuoDriverPayModel;
import com.puyue.www.qiaoge.model.HuoListModel;
import com.puyue.www.qiaoge.model.HuoPayModel;
import com.puyue.www.qiaoge.model.IsAuthModel;
import com.puyue.www.qiaoge.model.QueryProdModel;
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
        Observable<CarStyleModel> getData(@Field("cityId") String cityId,@Field("orderId") String orderId);
    }

    public static Observable<CarStyleModel> getCarStyle(Context context, String cityId,String orderId) {
        CarStyleService service = RestHelper.getBaseRetrofit(context).create(CarStyleService.class);
        return service.getData(cityId,orderId);
    }

    //地址检索
    public interface AddressListService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Address_Search)
        Observable<AddressListModel> getData(@Field("address") String address,
                                             @Field("city") String city,
                                             @Field("placeType") int placeType,
                                             @Field("cityId") String cityId);
    }

    public static Observable<AddressListModel> getAddressList(Context context, String address, String city,int placeType,String cityId) {
        AddressListService service = RestHelper.getBaseRetrofit(context).create(AddressListService.class);
        return service.getData(address,city,placeType,cityId);
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
                                           @Field("reserve_time") String reserve_time,
                                           @Field("vehicle_std") String vehicle_std,
                                           @Field("invoiceType") int invoiceType);
    }

    public static Observable<DealPriceModel> getPrice(Context context, String city_id, String city_info_revision
            ,String order_vehicle_id,String coupon_id,String spec_req,JSONArray addrInfo,int orderTime,String reserve_time,String vehicle_std,int invoiceType) {
        DealPriceService service = RestHelper.getBaseRetrofit(context).create(DealPriceService.class);
        return service.getData(city_id,city_info_revision,order_vehicle_id,coupon_id,spec_req
                ,addrInfo,orderTime,reserve_time,vehicle_std,invoiceType);
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
        @POST(AppInterfaceAddress.Huo_Appoint)
        Observable<AppointModel> getData();
    }

    public static Observable<AppointModel> getAppoint(Context context) {
        AppointService service = RestHelper.getBaseRetrofit(context).create(AppointService.class);
        return service.getData();
    }

    //优惠券列表
    public interface CouponService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Coupon_List)
        Observable<HuoCouponModel> getData(@Field("orderTime") String orderTime,
                                           @Field("reserve_time") String reserve_time,
                                           @Field("city_id") String city_id,
                                           @Field("order_vehicle_id") String order_vehicle_id,
                                           @Field("lat") String lat,
                                           @Field("lon") String lon,
                                           @Field("orderAmt") String orderAmt);
    }

    public static Observable<HuoCouponModel> getCoupon(Context context, String orderTime, String reserve_time,
                                                       String city_id, String order_vehicle_id,
                                                       String lat,String lon,String orderAmt) {
        CouponService service = RestHelper.getBaseRetrofit(context).create(CouponService.class);
        return service.getData(orderTime,reserve_time,city_id,order_vehicle_id,lat,lon,orderAmt);
    }

    //下单获取收银台地址
    public interface OrderAddressService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Pay_Address)
        Observable<HuoPayModel> getData(@Field("city_id") String city_id,
                                        @Field("city_info_revision") String city_info_revision,
                                        @Field("order_vehicle_id") String order_vehicle_id,
                                        @Field("coupon_id") String coupon_id,
                                        @Field("spec_req") String spec_req,
                                        @Field("addrInfo") JSONArray addrInfo,
                                        @Field("orderTime") String orderTime,
                                        @Field("reserve_time") String reserve_time,
                                        @Field("contact_name") String contact_name,
                                        @Field("contact_phone_no") String contact_phone_no,
                                        @Field("remark") String remark,
                                        @Field("orderId") String orderId,
                                        @Field("invoiceType") int invoiceType,
                                        @Field("vehicle_std") String vehicle_std);
    }

    public static Observable<HuoPayModel> getOrderAddress(Context context, String city_id, String city_info_revision,
                                                       String order_vehicle_id, String coupon_id,
                                                       String spec_req,JSONArray addrInfo,String orderTime,String reserve_time,
                                                       String contact_name,String contact_phone_no,String remark,String orderId,
                                                       int invoiceType,String vehicle_std) {

        OrderAddressService service = RestHelper.getBaseRetrofit(context).create(OrderAddressService.class);
        return service.getData(city_id,city_info_revision,order_vehicle_id,coupon_id,spec_req,addrInfo,orderTime,reserve_time,
                contact_name,contact_phone_no,remark,orderId,invoiceType,vehicle_std);
    }

    //回调保存授权信息
    public interface AuthCodeService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Auth_Code)
        Observable<BaseModel> getData(@Field("code") String code);
    }

    public static Observable<BaseModel> getCode(Context context, String code) {
        AuthCodeService service = RestHelper.getBaseRetrofit(context).create(AuthCodeService.class);
        return service.getData(code);
    }

    //货拉拉开放城市获取
    private interface HuoCityService {
        @POST(AppInterfaceAddress.Huo_City)
        Observable<HuoCityModel> getData();
    }

    public static Observable<HuoCityModel> getCity(Context context) {
        HuoCityService service = RestHelper.getBaseRetrofit(context).create(HuoCityService.class);
        return service.getData();
    }

    //获取默认城市id
    private interface HuoCityIdService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_City_Id)
        Observable<HuoCityIdModel> getData(@Field("orderId") String orderId);
    }

    public static Observable<HuoCityIdModel> getCityId(Context context,String orderId) {
        HuoCityIdService service = RestHelper.getBaseRetrofit(context).create(HuoCityIdService.class);
        return service.getData(orderId);
    }

    //获取司机位置
    public interface AuthAddressService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Driver_Address)
        Observable<HuoAddressModel> getData(@Field("orderDisplayId") String orderDisplayId);
    }

    public static Observable<HuoAddressModel> getDriverAddress(Context context, String orderDisplayId) {
        AuthAddressService service = RestHelper.getBaseRetrofit(context).create(AuthAddressService.class);
        return service.getData(orderDisplayId);
    }

    //获取授权地址
    private interface HuoAuthService {
        @POST(AppInterfaceAddress.Huo_AuthUrl)
        Observable<QueryProdModel> getData();
    }

    public static Observable<QueryProdModel> getAuthUrl(Context context) {
        HuoAuthService service = RestHelper.getBaseRetrofit(context).create(HuoAuthService.class);
        return service.getData();
    }

    //司机账单申述
    public interface DriverAccountService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Apply)
        Observable<BaseModel> getData(@Field("orderDisplayId") String orderDisplayId,
                                            @Field("desc") String desc,
                                            @Field("appeals") JSONArray appeals);
    }

    public static Observable<BaseModel> getDriverAccount(Context context, String orderDisplayId,String desc,JSONArray appeals) {
        DriverAccountService service = RestHelper.getBaseRetrofit(context).create(DriverAccountService.class);
        return service.getData(orderDisplayId,desc,appeals);
    }


    //支付司机账单
    public interface DriverPayService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Driver_Pay)
        Observable<HuoDriverPayModel> getData(@Field("orderDisplayId") String orderDisplayId);
    }

    public static Observable<HuoDriverPayModel> getDriverPay(Context context, String orderDisplayId) {
        DriverPayService service = RestHelper.getBaseRetrofit(context).create(DriverPayService.class);
        return service.getData(orderDisplayId);
    }

    //关联货拉拉订单
    public interface ConnectionService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Huo_Connection_Id)
        Observable<BaseModel> getData(@Field("orderId") String orderId,
                                              @Field("hllOrderId") String hllOrderId);
    }

    public static Observable<BaseModel> getConnection(Context context, String orderId, String hllOrderId) {
        ConnectionService service = RestHelper.getBaseRetrofit(context).create(ConnectionService.class);
        return service.getData(orderId,hllOrderId);
    }

    //判断是否有关联订单
    public interface HasConnectionService{
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Has_Connection_Id)
        Observable<HasConnectModel> getData(@Field("orderId") String orderId);
    }

    public static Observable<HasConnectModel> getHasConnection(Context context, String orderId) {
        HasConnectionService service = RestHelper.getBaseRetrofit(context).create(HasConnectionService.class);
        return service.getData(orderId);
    }
}
