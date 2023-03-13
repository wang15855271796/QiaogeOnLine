package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.cart.CancelOrderModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王文博} on 2019/3/25
 */
public class DeleteOrderAPI {
    //删除订单1
    private interface DeletrOrder {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.DELETEORDER)
        Observable<CancelOrderModel> getData(@Field("orderId") String orderId);
    }

    public static Observable<CancelOrderModel> requestData(Context context, String orderId) {
        DeletrOrder deletrOrder = RestHelper.getBaseRetrofit(context).create(DeletrOrder.class);
        return deletrOrder.getData(orderId);

    }

    //删除订单2
    private interface DeleteCompleteOrder {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Delete_Order)
        Observable<CancelOrderModel> getData(@Field("orderId") String orderId);
    }

    public static Observable<CancelOrderModel> deleteOrder(Context context, String orderId) {
        DeleteCompleteOrder deletrOrder = RestHelper.getBaseRetrofit(context).create(DeleteCompleteOrder.class);
        return deletrOrder.getData(orderId);

    }
}
