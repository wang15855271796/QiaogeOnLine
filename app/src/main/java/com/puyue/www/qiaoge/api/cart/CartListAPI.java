package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.cart.CartListModel;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/4.
 */

public class CartListAPI {



    public interface CartListServices {
        @POST(AppInterfaceAddress.GET_CART_LISTS)
        Observable<CartsListModel> getData();
    }

    public static Observable<CartsListModel> requestCartLists(Context context) {
        CartListServices service = RestHelper.getBaseRetrofit(context).create(CartListServices.class);
        return service.getData();
    }

    //最新购物车列表
    public interface CartListsServices {
        @POST(AppInterfaceAddress.GET_CART_LIST)
        Observable<CartTestModel> getData();
    }

    public static Observable<CartTestModel> getCartsList(Context context) {
        CartListsServices service = RestHelper.getBaseRetrofit(context).create(CartListsServices.class);
        return service.getData();
    }

    //满减详情
    public interface CartFullDetailServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Full_Cart_Detail)
        Observable<CartFullModel> getData(@Field("type") int type);
    }

    public static Observable<CartFullModel> getFullDetail(Context context, int type) {
        CartFullDetailServices service = RestHelper.getBaseRetrofit(context).create(CartFullDetailServices.class);
        return service.getData(type);
    }

}
