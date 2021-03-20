package com.puyue.www.qiaoge.helper;

import android.content.Context;

import com.puyue.www.qiaoge.api.cart.GetCartNumAPI;
import com.puyue.www.qiaoge.api.home.GetCustomerPhoneAPI;
import com.puyue.www.qiaoge.api.home.HasCollectAPI;
import com.puyue.www.qiaoge.event.OnHttpCallBack;
import com.puyue.www.qiaoge.model.cart.GetCartNumModel;
import com.puyue.www.qiaoge.model.home.GetCustomerPhoneModel;
import com.puyue.www.qiaoge.model.home.HasCollectModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by win7 on 2018/7/24.
 */

public class PublicRequestHelper {
    /**
     * 获取客服电话
     */
    public static void getCustomerPhone(Context context, final OnHttpCallBack<GetCustomerPhoneModel> callBack) {
        GetCustomerPhoneAPI.requestData(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCustomerPhoneModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.toString());
                    }

                    @Override
                    public void onNext(GetCustomerPhoneModel getCustomerPhoneModel) {
                        callBack.onSuccessful(getCustomerPhoneModel);
                    }
                });
    }

    /**
     * 获取角标数据
     */
    public static void getCartNum(Context context, final OnHttpCallBack<GetCartNumModel> callBack) {

        GetCartNumAPI.requestData(context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GetCartNumModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.toString());
                    }

                    @Override
                    public void onNext(GetCartNumModel getCartNumModel) {

                        callBack.onSuccessful(getCartNumModel);
                    }
                });
    }

    /**
     * 获取收藏状态
     */
    public static void hasCollectState(Context context, int businessId, byte businessType, final OnHttpCallBack<HasCollectModel> callBack) {
        HasCollectAPI.requestData(context, businessId, businessType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HasCollectModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFaild(e.toString());
                    }

                    @Override
                    public void onNext(HasCollectModel hasCollectModel) {
                        callBack.onSuccessful(hasCollectModel);
                    }
                });
    }


}
