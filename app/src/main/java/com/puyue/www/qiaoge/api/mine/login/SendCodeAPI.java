package com.puyue.www.qiaoge.api.mine.login;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/18.
 */

public class SendCodeAPI {
    public interface SendCodeService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.SEND_AUTH_CODE)
        Observable<BaseModel> setParams(@Field("phone") String phone, @Field("type") int type);
    }

    public static Observable<BaseModel> requestSendCode(Context context, String phone, int type) {
        Observable<BaseModel> sendCodeObservable = RestHelper.getBaseRetrofit(context).create(SendCodeService.class).setParams(phone, type);
        return sendCodeObservable;
    }

    public interface SendCodeServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.CHECKMESSAGE)
        Observable<BaseModel> setParams(@Field("phone") String phone, @Field("verifyCode") String verifyCode);
    }

    public static Observable<BaseModel> requestSendCodes(Context context, String phone, String verifyCode) {
        Observable<BaseModel> sendCodeObservable = RestHelper.getBaseRetrofit(context).create(SendCodeServices.class).setParams(phone, verifyCode);
        return sendCodeObservable;
    }

    /**
     * 最终注销
     */
    public interface SendCancleServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.User_cancle)
        Observable<BaseModel> setParams(@Field("phone") String phone, @Field("verifyCode") String verifyCode, @Field("cancelReason") String cancelReason, @Field("pwd") String pwd);
    }

    public static Observable<BaseModel> requestsCode(Context context, String phone, String verifyCode,String cancelReason,String pwd) {
        Observable<BaseModel> sendCodeObservable = RestHelper.getBaseRetrofit(context).create(SendCancleServices.class).setParams(phone, verifyCode,cancelReason,pwd);
        return sendCodeObservable;
    }

    /**
     * 登录后，更换手机号-老号码获取验证码
     */
    public interface OldChangeServices {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Old_Change_phone)
        Observable<BaseModel> setParams(@Field("phone") String phone);
    }

    public static Observable<BaseModel> changePhone(Context context, String phone) {
        Observable<BaseModel> sendCodeObservable = RestHelper.getBaseRetrofit(context).create(OldChangeServices.class).setParams(phone);
        return sendCodeObservable;
    }


}
