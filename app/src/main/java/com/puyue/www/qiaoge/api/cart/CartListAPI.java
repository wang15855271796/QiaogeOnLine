package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.CartFullModel;
import com.puyue.www.qiaoge.model.CartFullsModel;
import com.puyue.www.qiaoge.model.ComputedFullModel;
import com.puyue.www.qiaoge.model.HomeCouponModel;
import com.puyue.www.qiaoge.model.cart.CartListModel;
import com.puyue.www.qiaoge.model.cart.CartTestModel;
import com.puyue.www.qiaoge.model.cart.CartsListModel;
import com.puyue.www.qiaoge.model.mine.login.LoginModel;

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

    //购物车单选
    public interface CartChooseServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cart_Choose)
        Observable<HomeCouponModel> getData(@Field("cartId") int cartId, @Field("checkFlag") int checkFlag);
    }

    public static Observable<HomeCouponModel> setCartChoose(Context context,int cartId,int checkFlag) {
        CartChooseServices service = RestHelper.getBaseRetrofit(context).create(CartChooseServices.class);
        return service.getData(cartId,checkFlag);
    }

    //购物车全选
    public interface CartChooseAllServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Cart_Choose_All)
        Observable<HomeCouponModel> getData(@Field("checkFlag") int checkFlag);
    }

    public static Observable<HomeCouponModel> setCartChooseAll(Context context,int checkFlag) {
        CartChooseAllServices service = RestHelper.getBaseRetrofit(context).create(CartChooseAllServices.class);
        return service.getData(checkFlag);
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

    //满减详情(新)
    public interface CartFullDetailsServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Full_Cart_Details)
        Observable<CartFullsModel> getData(@Field("type") int type);
    }

    public static Observable<CartFullsModel> getFullDetails(Context context, int type) {
        CartFullDetailsServices service = RestHelper.getBaseRetrofit(context).create(CartFullDetailsServices.class);
        return service.getData(type);
    }

    //计算满减优惠
    public interface CartComputedServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Computed_Full)
        Observable<ComputedFullModel> getData(@Field("amount") String amount);
    }

    public static Observable<ComputedFullModel> getFullReduce(Context context, String amount) {
        CartComputedServices service = RestHelper.getBaseRetrofit(context).create(CartComputedServices.class);
        return service.getData(amount);
    }
}
