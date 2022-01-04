package com.puyue.www.qiaoge.api.mine.order;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.VipListModel;
import com.puyue.www.qiaoge.model.cart.GetOrderDetailModel;
import com.puyue.www.qiaoge.model.mine.order.CartGetReductModel;
import com.puyue.www.qiaoge.model.mine.order.VipPayModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${daff}
 * on 2018/10/25
 * 备注
 */
public class VipPayAPI  {

    private interface VipPayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.VIPPAY)
        Observable<VipPayModel> setParams(@Field("vipPackageId") String vipPackageId,
                                              @Field("payChannel") int payChannel);
    }

    public static Observable<VipPayModel> requestVipPayData(Context context, String vipPackageId, int payChannel) {
        VipPayService service = RestHelper.getBaseRetrofit(context).create(VipPayService.class);
        return service.setParams(vipPackageId, payChannel);
    }

    public interface VipPayListService {
        @POST(AppInterfaceAddress.VIP_List)
        Observable<VipListModel> setParams();
    }

    public static Observable<VipListModel> getPayList(Context context) {
        Observable<VipListModel> changeOrderAddressModelObservable = RestHelper.getBaseRetrofit(context).create(VipPayListService.class).setParams();
        return changeOrderAddressModelObservable;
    }

}
