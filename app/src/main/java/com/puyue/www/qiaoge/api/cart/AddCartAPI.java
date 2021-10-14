package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.cart.AddCartModel;

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
        Observable<AddCartModel> getData(@Field("businessType") int businessType,
                                         @Field("businessId") int businessId,
                                         @Field("num") int num);
    }

    public static Observable<AddCartModel> requestData(Context context, int businessType, int businessId, int num) {
        AddCartService service = RestHelper.getBaseRetrofit(context).create(AddCartService.class);
        return service.getData(businessType, businessId, num);
    }
}
