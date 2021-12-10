package com.puyue.www.qiaoge.api.market;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.PriceTrendModel;
import com.puyue.www.qiaoge.model.market.MarketAlreadyGoodModel;
import com.puyue.www.qiaoge.model.market.MarketSelectGoodModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王文博} on 2019/5/21
 */
public class MarketAlreadyGoodAPI {

    public interface MarketGoodsAlreadyService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.GETALREADYPRODUCT)
        Observable<MarketAlreadyGoodModel> setParams(
                @Field("firstId") String firstId,
                @Field("secondId") int secondId
        );
    }

    public static Observable<MarketAlreadyGoodModel> requestMarketAlready(Context context,  String firstId, int secondId) {
        Observable<MarketAlreadyGoodModel> marketGoodsModelObservable = RestHelper.getBaseRetrofit(context).create(MarketGoodsAlreadyService.class).setParams( firstId, secondId);
        return marketGoodsModelObservable;
    }

    //价格走势图
    public interface PriceTrendService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Price_Trend)
        Observable<PriceTrendModel> setParams(
                @Field("priceId") String priceId,
                @Field("productId") String productId,
                @Field("month") int month
        );
    }

    public static Observable<PriceTrendModel> getTrends(Context context, String priceId, String productId, int month) {
        Observable<PriceTrendModel> marketGoodsModelObservable = RestHelper.getBaseRetrofit(context).create(PriceTrendService.class).setParams( priceId, productId,month);
        return marketGoodsModelObservable;
    }
}
