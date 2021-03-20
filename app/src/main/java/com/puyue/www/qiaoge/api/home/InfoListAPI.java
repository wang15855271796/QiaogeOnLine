package com.puyue.www.qiaoge.api.home;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.InfoDetailIssueModel;
import com.puyue.www.qiaoge.model.MyInfoListModel;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2021/1/5
 */
public class InfoListAPI {

    private interface InfoList {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_List)
        Observable<InfoListModel> getData(@Field("type") String type,@Field("pageNum") int pageNum, @Field("pageSize") int pageSize
                , @Field("provinceCode") String provinceCode, @Field("cityCode") String cityCode);
    }

    public static Observable<InfoListModel> requestData(Context context,String type, int pageNum, int pageSize,String provinceCode,String cityCode) {
        InfoList service = RestHelper.getBaseRetrofit(context).create(InfoList.class);
        return service.getData(type,pageNum, pageSize,provinceCode,cityCode);
    }

    private interface InfoDetail {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Detail)
        Observable<InfoDetailModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<InfoDetailModel> getDetail(Context context,String msgId) {
        InfoDetail service = RestHelper.getBaseRetrofit(context).create(InfoDetail.class);
        return service.getData(msgId);
    }

    private interface MyInfoList {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_My_List)
        Observable<InfoListModel> getData(@Field("pageNum") int pageNum,@Field("pageSize") int pageSize);
    }

    public static Observable<InfoListModel> getMyList(Context context, int pageNum,int pageSize) {
        MyInfoList service = RestHelper.getBaseRetrofit(context).create(MyInfoList.class);
        return service.getData(pageNum,pageSize);
    }

    private interface InfoDeleted {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Deleted)
        Observable<BaseModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<BaseModel> InfoDeleted(Context context,String msgId) {
        InfoDeleted service = RestHelper.getBaseRetrofit(context).create(InfoDeleted.class);
        return service.getData(msgId);
    }

    /**
     * 发布资讯
     */
    private interface InfoIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Issue)
        Observable<BaseModel> getData(@Field("msgId") String msgId,@Field("msgType") int msgType,@Field("content") String content,@Field("pictureJson") String pictureJson,@Field("provinceCode") String provinceCode,@Field("cityCode") String cityCode,@Field("detailAddress") String detailAddress,@Field("phone")String phone);
    }

    public static Observable<BaseModel> InfoIssue(Context context,String msgId,int msgType,String content,String pictureJson,String provinceCode,String cityCode,String detailAddress,String phone) {
        InfoIssue service = RestHelper.getBaseRetrofit(context).create(InfoIssue.class);
        return service.getData(msgId,msgType,content,pictureJson,provinceCode,cityCode,detailAddress,phone);
    }

    /**
     * 资讯详情
     */
    private interface InfoDetailIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Issue_detail)
        Observable<InfoDetailIssueModel> getData(@Field("msgId") String msgId);
    }

    public static Observable<InfoDetailIssueModel> InfoDetailIssue(Context context,String msgId) {
        InfoDetailIssue service = RestHelper.getBaseRetrofit(context).create(InfoDetailIssue.class);
        return service.getData(msgId);
    }

    /**
     * 资讯编辑
     */

    private interface InfoClassifyIssue {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Info_Classify)
        Observable<BaseModel> getData(@Field("msgId") String msgId,@Field("msgType") int msgType,@Field("content") String content,@Field("pictureJson") String pictureJson,@Field("provinceCode") String provinceCode,@Field("cityCode") String cityCode,@Field("detailAddress") String detailAddress,@Field("phone")String phone);
    }

    public static Observable<BaseModel> EditInfo(Context context,String msgId,int msgType,String content,String pictureJson,String provinceCode,String cityCode,String detailAddress,String phone) {
        InfoClassifyIssue service = RestHelper.getBaseRetrofit(context).create(InfoClassifyIssue.class);
        return service.getData(msgId,msgType,content,pictureJson,provinceCode,cityCode,detailAddress,phone);
    }
}
