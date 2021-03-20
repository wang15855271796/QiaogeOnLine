package com.puyue.www.qiaoge.api.mine.coupon;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.QueryProdModel;
import com.puyue.www.qiaoge.model.mine.coupons.UserChooseDeductModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author daff
 * @date 2018/9/23.
 * 备注 收银台选择优惠券
 */
public class userChooseDeductAPI {

    private interface userChooseDeductService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.USER_CHOOSE_DEDUCT)
        Observable<UserChooseDeductModel> getData(
                @Field("orderDeliveryType") String orderDeliveryType,
                @Field("activityBalanceVOStr") String activityBalanceVOStr,
                @Field("normalProductBalanceVOStr") String normalProductBalanceVOStr,
                @Field("enabled") String enabled);
    }

    public static Observable<UserChooseDeductModel> requestData(Context context,String orderDeliveryType,String activityBalanceVOStr, String normalProductBalanceVOStr,String enabled) {
        userChooseDeductService service = RestHelper.getBaseRetrofit(context).create(userChooseDeductService.class);
        return service.getData(orderDeliveryType,activityBalanceVOStr, normalProductBalanceVOStr,enabled);
    }

    private interface QueryProdsService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Query_Prod)
        Observable<QueryProdModel> getData(@Field("giftDetailNo") String giftDetailNo);
    }

    public static Observable<QueryProdModel> queryProd(Context context,String giftDetailNo) {
        QueryProdsService service = RestHelper.getBaseRetrofit(context).create(QueryProdsService.class);
        return service.getData(giftDetailNo);
    }

}
