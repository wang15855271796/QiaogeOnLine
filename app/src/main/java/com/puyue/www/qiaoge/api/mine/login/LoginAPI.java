package com.puyue.www.qiaoge.api.mine.login;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.AddressMessageModel;
import com.puyue.www.qiaoge.model.HisModel;
import com.puyue.www.qiaoge.model.mine.login.LoginModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/18.
 */

public class LoginAPI {
    public interface LoginService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.LOGIN)
        Observable<LoginModel> setParams(@Field("phone") String phone,
                                         @Field("password") String password,
                                         @Field("verifyCode") String verifyCode,
                                         @Field("loginType") int loginType);
    }

    public static Observable<LoginModel> requestLogin(Context context, String phone, String password, String verifyCode, int loginType) {
        Observable<LoginModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(LoginService.class).setParams(phone, password, verifyCode, loginType);
        return loginModelObservable;
    }

    //登录后、更换手机号验证码验证
    public interface CheckYzmService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Yzm)
        Observable<BaseModel> setParams(@Field("phone") String phone,
                                        @Field("verifyCode") String verifyCode);
    }

    public static Observable<BaseModel> checkYzm(Context context, String phone, String verifyCode) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CheckYzmService.class).setParams(phone, verifyCode);
        return loginModelObservable;
    }

    //登录后、更换手机号获取验证码
    public interface GetYzmService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Yzm)
        Observable<BaseModel> setParams(@Field("phone") String phone);
    }

    public static Observable<BaseModel> getYzm(Context context, String phone) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(GetYzmService.class).setParams(phone);
        return loginModelObservable;
    }

    // 登录后，更换手机号设置密码
    public interface SetSecretService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Set_Secret)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("password") String password);
    }

    public static Observable<BaseModel> setSecret(Context context, String phone, String password) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(SetSecretService.class).setParams(phone,password);
        return loginModelObservable;
    }

    //验证登录密码
    public interface CheckSecretService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Secret)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("password") String password);
    }

    public static Observable<BaseModel> checkSecret(Context context, String phone, String password) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CheckSecretService.class).setParams(phone,password);
        return loginModelObservable;
    }

    //验证是否第一次更换
    public interface CheckFirstService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_First)
        Observable<BaseModel> setParams(@Field("phone") String phone);
    }

    public static Observable<BaseModel> checkFirst(Context context, String phone) {
        Observable<BaseModel> loginModelObservable = RestHelper.getBaseRetrofit(context).create(CheckFirstService.class).setParams(phone);
        return loginModelObservable;
    }

    //验证支付密码
    public interface CheckPayService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Pay_Secret)
        Observable<BaseModel> setParams(@Field("phone") String phone, @Field("password") String password);
    }

    public static Observable<BaseModel> checkPay(Context context, String phone,String password) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckPayService.class).setParams(phone,password);
        return myOrdersModelObservable;
    }

    //登录后，修改支付密码
    public interface ModifyPaySecretService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Logined_Modify_Secret)
        Observable<BaseModel> setParams(@Field("phone") String phone, @Field("password") String password);
    }

    public static Observable<BaseModel> modifyPay(Context context, String phone,String password) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(ModifyPaySecretService.class).setParams(phone,password);
        return myOrdersModelObservable;
    }

    //校验手机号
    public interface CheckPhoneService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Phone)
        Observable<BaseModel> setParams(@Field("phone") String phone);
    }

    public static Observable<BaseModel> checkPhone(Context context, String phone) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckPhoneService.class).setParams(phone);
        return myOrdersModelObservable;
    }

    //获取验证地址
    public interface CheckAddressService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Check_Address)
        Observable<HisModel> setParams(@Field("phone") String phone);
    }

    public static Observable<HisModel> getCheckAddress(Context context, String phone) {
        Observable<HisModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckAddressService.class).setParams(phone);
        return myOrdersModelObservable;
    }

    // 根据手机号 收货地址进行验证
    public interface CheckAddressMessageService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Address)
        Observable<AddressMessageModel> setParams(@Field("phone") String phone,@Field("addressId") String addressId);
    }

    public static Observable<AddressMessageModel> checkAddress(Context context, String phone, String addressId) {
        Observable<AddressMessageModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckAddressMessageService.class).setParams(phone,addressId);
        return myOrdersModelObservable;
    }

    //校验收货地址中的收货人和联系电话
    public interface CheckContactService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Contact)
        Observable<BaseModel> setParams(@Field("userName") String userName,@Field("addressId") String addressId,@Field("contactPhone") String contactPhone,@Field("phone") String phone);
    }

    public static Observable<BaseModel> checkContact(Context context, String userName, String addressId,String contactPhone,String phone) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckContactService.class).setParams(userName,addressId,contactPhone,phone);
        return myOrdersModelObservable;
    }

    //找回密码验证短信
    public interface CheckMessageService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_Message)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("verifyCode") String verifyCode);
    }

    public static Observable<BaseModel> checkMessage(Context context, String phone,String verifyCode) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(CheckMessageService.class).setParams(phone,verifyCode);
        return myOrdersModelObservable;
    }

    // 找回密码(修改密码)没有验证码
    public interface UnLoginSetSecretService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.UnLogin_Set_Secret)
        Observable<BaseModel> setParams(@Field("phone") String phone,@Field("password") String password);
    }

    public static Observable<BaseModel> setUnLoginSecret(Context context, String phone,String password) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(UnLoginSetSecretService.class).setParams(phone,password);
        return myOrdersModelObservable;
    }

    //未登录 更换手机号码
    public interface UnLoginChnagePhoneService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.UnLogin_Change_Phone)
        Observable<BaseModel> setParams(@Field("newPhone") String newPhone,@Field("oldPhone") String oldPhone,@Field("verifyCode") String verifyCode);
    }

    public static Observable<BaseModel> setChnagePhone(Context context, String newPhone,String oldPhone,String verifyCode) {
        Observable<BaseModel> myOrdersModelObservable = RestHelper.getBaseRetrofit(context).create(UnLoginChnagePhoneService.class).setParams(newPhone,oldPhone,verifyCode);
        return myOrdersModelObservable;
    }
}
