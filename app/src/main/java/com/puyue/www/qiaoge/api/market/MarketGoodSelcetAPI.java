package com.puyue.www.qiaoge.api.market;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王文博} on 2019/5/21
 */
public class MarketGoodSelcetAPI {

    public interface MarketRight {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.CLASSIFY_RIGHT)
        Observable<MarketRightModel> setParams(@Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize,
                                               @Field("firstId") String firstId,
                                               @Field("secondId") int secondId,
                                               @Field("saleVolume") String saleVolume,
                                               @Field("priceUp") String priceUp,
                                               @Field("priceDown") String priceDown,
                                               @Field("newProduct") String newProduct,
                                               @Field("brandName") String brandName,
                                               @Field("minPrice") String minPrice,
                                               @Field("maxPrice") String maxPrice);
    }

    public static Observable<MarketRightModel> getClassifyRight(Context context, int pageNum, int pageSize, String firstId, int secondId, String saleVolume, String priceUp,String priceDown,String newProduct, String  brandName, String minPrice, String maxPrice) {
        Observable<MarketRightModel> marketGoodsModelObservable = RestHelper.getBaseRetrofit(context).create(MarketRight.class).setParams(pageNum, pageSize, firstId, secondId, saleVolume,priceUp,priceDown,newProduct,brandName,minPrice,maxPrice);
        return marketGoodsModelObservable;
    }
}
