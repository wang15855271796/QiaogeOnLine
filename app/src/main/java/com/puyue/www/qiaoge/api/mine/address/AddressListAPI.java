package com.puyue.www.qiaoge.api.mine.address;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.mine.address.AddressModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/20.
 */

public class AddressListAPI {
//    public interface AddressListService {
//        @GET(AppInterfaceAddress.GET_ADDRESS_LIST)
//        Observable<AddressModel> setParams();
//    }
//
//    public static Observable<AddressModel> requestAddressModel(Context context) {
//        Observable<AddressModel> addressModelObservable = RestHelper.getBaseRetrofit(context).create(AddressListService.class).setParams();
//        return addressModelObservable;
//    }

    public interface SearchAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.GET_ADDRESS_LIST)
        Observable<AddressModel> setParams(@Field("searchKey") String searchKey);
    }

    public static Observable<AddressModel> requestAddressModel(Context context, String searchKey) {
        Observable<AddressModel> addAddressObservable = RestHelper.getBaseRetrofit(context).create(SearchAddressService.class).setParams(searchKey);
        return addAddressObservable;
    }

    //从订单里进去选择省市区
    public interface AddressListServices {
        @GET(AppInterfaceAddress.GET_AREA_LIST)
        Observable<AreaModel> setParams();
    }

    public static Observable<AreaModel> getArea(Context context) {
        Observable<AreaModel> addressModelObservable = RestHelper.getBaseRetrofit(context).create(AddressListServices.class).setParams();
        return addressModelObservable;
    }

    public interface AddressListServicess {
        @GET(AppInterfaceAddress.GET_ENABLEAREA_LIST)
        Observable<AreaModel> setParams();
    }

    public static Observable<AreaModel> getEnableArea(Context context) {
        Observable<AreaModel> addressModelObservable = RestHelper.getBaseRetrofit(context).create(AddressListServicess.class).setParams();
        return addressModelObservable;
    }

}
