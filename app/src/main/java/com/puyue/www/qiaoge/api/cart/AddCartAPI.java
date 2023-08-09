package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.cart.AddCartModel;
import com.puyue.www.qiaoge.model.cart.CartAddModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * If I become novel would you like ?
 * Created by WinSinMin on 2018/4/24.
 */

public class AddCartAPI {
    private interface AddCartService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.ADDCART)
        Observable<CartAddModel> getData(@Field("businessType") int businessType,
                                         @Field("businessId") int businessId,
                                         @Field("num") int num,
                                         @Field("freshPriceFlag") int freshPriceFlag);
    }

    public static Observable<CartAddModel> requestData(Context context, int businessType, int businessId, int num,int freshPriceFlag) {
        AddCartService service = RestHelper.getBaseRetrofit(context).create(AddCartService.class);
        return service.getData(businessType, businessId, num,freshPriceFlag);
    }
}
