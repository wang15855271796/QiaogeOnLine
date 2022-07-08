package com.puyue.www.qiaoge.api.mine.address;

import android.content.Context;
import android.widget.FrameLayout;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.AreasModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/23.
 */

public class DefaultAddressAPI {
    public interface DefaultAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.EDIT_DEFAULT_ADDRESS)
        Observable<BaseModel> setParams(@Field("id") int id,
                                        @Field("orderId") String orderId);
    }

    public static Observable<BaseModel> requestEditDefaultAddress(Context context, int id,String orderId) {
        Observable<BaseModel> editDefaultAddressObservable = RestHelper.getBaseRetrofit(context).create(DefaultAddressService.class).setParams(id,orderId);
        return editDefaultAddressObservable;
    }

    public interface ReceiveAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.SetOrderAddress)
        Observable<BaseModel> setParams(@Field("addressId") int addressId,
                                        @Field("orderId") String orderId);
    }

    public static Observable<BaseModel> getReceiveAddress(Context context, int addressId,String orderId) {
        Observable<BaseModel> editDefaultAddressObservable = RestHelper.getBaseRetrofit(context).create(ReceiveAddressService.class).setParams(addressId,orderId);
        return editDefaultAddressObservable;
    }

    //获取详细地址
    public interface GetAreaService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Area)
        Observable<AreasModel> setParams(@Field("chooseCityName") String chooseCityName,
                                         @Field("keyword") String keyword);
    }

    public static Observable<AreasModel> getArea(Context context, String chooseCityName,String keyword) {
        Observable<AreasModel> editDefaultAddressObservable = RestHelper.getBaseRetrofit(context).create(GetAreaService.class).setParams(chooseCityName,keyword);
        return editDefaultAddressObservable;
    }
}
