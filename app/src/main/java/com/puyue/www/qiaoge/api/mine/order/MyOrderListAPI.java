package com.puyue.www.qiaoge.api.mine.order;

import android.content.Context;

import com.puyue.www.qiaoge.api.cart.CartListAPI;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.OrderNumsModel;
import com.puyue.www.qiaoge.model.OrdersModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.model.mine.order.CommonModel;
import com.puyue.www.qiaoge.model.mine.order.MyOrdersModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/20.
 */

public class MyOrderListAPI {
    public interface MyOrderListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Order_List)
        Observable<OrdersModel> setParams(@Field("orderStatus") int orderStatus,
                                            @Field("pageNum") int pageNum,
                                            @Field("pageSize") int pageSize,
                                            @Field("orderDeliveryType") int orderDeliveryType);
    }

    public static Observable<OrdersModel> requestOrderList(Context context, int orderStatus, int pageNum, int pageSize,int orderDeliveryType) {
        Observable<OrdersModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(MyOrderListService.class).setParams(orderStatus, pageNum, pageSize,orderDeliveryType);
        return myOrdersModelObservable;
    }

    public interface ReturnOrderListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Order_List)
        Observable<MyOrdersModel> setParams(@Field("orderStatus") int orderStatus,
                                          @Field("pageNum") int pageNum,
                                          @Field("pageSize") int pageSize,
                                          @Field("orderDeliveryType") int orderDeliveryType);
    }

    public static Observable<MyOrdersModel> getOrderList(Context context, int orderStatus, int pageNum, int pageSize,int orderDeliveryType) {
        Observable<MyOrdersModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnOrderListService.class).setParams(orderStatus, pageNum, pageSize,orderDeliveryType);
        return myOrdersModelObservable;
    }

    public interface MySubOrderListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Sub_Account_List)
        Observable<OrdersModel> setParams(@Field("orderStatus") int orderStatus,
                                            @Field("pageNum") int pageNum,
                                            @Field("pageSize") int pageSize,
                                            @Field("orderDeliveryType") int orderDeliveryType, @Field("subId") String subId);
    }

    public static Observable<OrdersModel> getList(Context context, int orderStatus, int pageNum, int pageSize,int orderDeliveryType,String subId) {
        Observable<OrdersModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(MySubOrderListService.class).setParams(orderStatus, pageNum, pageSize,orderDeliveryType,subId);
        return myOrdersModelObservable;
    }

    public interface OrderCommentService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Order_Comment)
        Observable<CommonModel> setParams(@Field("orderId") String orderId);
    }

    public static Observable<CommonModel> getComment(Context context, String orderId) {
        Observable<CommonModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(OrderCommentService.class).setParams(orderId);
        return myOrdersModelObservable;
    }

    //代付款订单状态
    public interface OrderNumServices {
        @POST(AppInterfaceAddress.Order_Num)
        Observable<OrderNumsModel> getData();
    }

    public static Observable<OrderNumsModel> getNum(Context context) {
        OrderNumServices service = RestHelper.getBaseRetrofit(context).create(OrderNumServices.class);
        return service.getData();
    }


}
