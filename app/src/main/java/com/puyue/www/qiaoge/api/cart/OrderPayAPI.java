package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.HelpPersonInfoModel;
import com.puyue.www.qiaoge.model.PayInfoListModel;
import com.puyue.www.qiaoge.model.PayInfoModel;
import com.puyue.www.qiaoge.model.PayListModel;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.cart.OrderPayModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/26.
 */

public class OrderPayAPI {
    private interface OrderPayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.ORDERPAY)
        Observable<OrderPayModel> getData(@Field("orderId") String orderId,
                                          @Field("payChannel") byte payChannel,
                                          @Field("payAmount") double payAmount,
                                          @Field("remark") String remark,
                                          @Field("errorFlag") int errorFlag);
    }

    public static Observable<OrderPayModel> requestData(Context context, String orderId, byte payChannel, double payAmount, String remark,int errorFlag) {
        OrderPayService service = RestHelper.getBaseRetrofit(context).create(OrderPayService.class);
        return service.getData(orderId, payChannel, payAmount, remark,errorFlag);
    }


    private interface PayListService {
        @GET(AppInterfaceAddress.Pay_List)
        Observable<PayListModel> setParams();
    }

    public static Observable<PayListModel> requestsData(Context context) {
        PayListService service = RestHelper.getBaseRetrofit(context).create(PayListService.class);
        return service.setParams();
    }

    private interface PayInfoListService {
        @POST(AppInterfaceAddress.Pay_Info_List)
        Observable<PayInfoListModel> setParams();
    }

    public static Observable<PayInfoListModel> getPayList(Context context) {
        PayInfoListService service = RestHelper.getBaseRetrofit(context).create(PayInfoListService.class);
        return service.setParams();
    }

    //查询帮付人信息口
    private interface QueryHelpPersonService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Query_Help_Person)
        Observable<HelpPersonInfoModel> setParams(@Field("queryPhone") String queryPhone);
    }

    public static Observable<HelpPersonInfoModel> getHelpPerson(Context context,String queryPhone) {
        QueryHelpPersonService service = RestHelper.getBaseRetrofit(context).create(QueryHelpPersonService.class);
        return service.setParams(queryPhone);
    }

    //发送订单给朋友
    private interface SendOrderPersonService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Send_Order_To_Person)
        Observable<BaseModel> setParams(@Field("orderId") String orderId, @Field("receiveUserId") String receiveUserId);
    }

    public static Observable<BaseModel> sendOrder(Context context,String orderId,String receiveUserId) {
        SendOrderPersonService service = RestHelper.getBaseRetrofit(context).create(SendOrderPersonService.class);
        return service.setParams(orderId,receiveUserId);
    }

    //行业资讯 生成支付信息
    private interface PayInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Pay_Info)
        Observable<PayInfoModel> getData(@Field("payChannel") int payChannel,
                                         @Field("payAmount") String payAmount,
                                         @Field("msgId") String msgId);
    }

    public static Observable<PayInfoModel> getPayInfo(Context context, int payChannel, String payAmount, String msgId) {
        PayInfoService service = RestHelper.getBaseRetrofit(context).create(PayInfoService.class);
        return service.getData( payChannel, payAmount, msgId);
    }

}
