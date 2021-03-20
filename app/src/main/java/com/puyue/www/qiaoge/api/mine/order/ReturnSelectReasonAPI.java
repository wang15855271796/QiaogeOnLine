package com.puyue.www.qiaoge.api.mine.order;

import android.content.Context;

import com.google.gson.JsonArray;
import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.IsReturnModel;
import com.puyue.www.qiaoge.model.mine.order.ReturnOrderDetailModel;
import com.puyue.www.qiaoge.model.mine.wallet.OrderReturnSelectReasonModel;

import org.json.JSONArray;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王文博} on 2019/5/15
 */
public class ReturnSelectReasonAPI {
    public interface ReturnOrderReasonService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.QUERYORDERRETURNTYPE)
        Observable<OrderReturnSelectReasonModel> setParams(@Field("orderStatus") String orderStatus
        );
    }

    public static Observable<OrderReturnSelectReasonModel> requestReturnOrderReason(Context context, String orderStatus) {
        Observable<OrderReturnSelectReasonModel> returnGoodsModelObservable = RestHelper.getBaseRetrofit(context).create(ReturnOrderReasonService.class).setParams(orderStatus);
        return returnGoodsModelObservable;
    }

    /**
     * 退货判断是否有满赠商品
     */
    public interface IsReturnService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Is_ReturnGoods)
        Observable<IsReturnModel> setParams(@Field("orderId") String orderId,
                                            @Field("returnProducts") JSONArray returnProducts
        );
    }

    public static Observable<IsReturnModel> isReturnGoods(Context context, String orderId,JSONArray returnProducts) {
        Observable<IsReturnModel> returnGoodsModelObservable = RestHelper.getBaseRetrofit(context).create(IsReturnService.class).setParams(orderId,returnProducts);
        return returnGoodsModelObservable;
    }
}
