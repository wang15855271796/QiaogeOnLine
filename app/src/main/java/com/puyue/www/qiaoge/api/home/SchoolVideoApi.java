package com.puyue.www.qiaoge.api.home;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.SchoolVideoListModel;
import com.puyue.www.qiaoge.model.VideoDetailModel;
import com.puyue.www.qiaoge.model.home.CouponModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public class SchoolVideoApi {

    /**
     * 获取视频列表
     */

    private interface SchoolVideoService {
        @POST(AppInterfaceAddress.School_Video_List)
        Observable<SchoolVideoListModel> getData();
    }

    public static Observable<SchoolVideoListModel> getVideoList(Context context) {
        SchoolVideoService spikeActiveQueryService = RestHelper.getBaseRetrofit(context).create(SchoolVideoService.class);
        return spikeActiveQueryService.getData();
    }

    /**
     * 申请咨询
     */
    private interface SchoolAskService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.School_Video_Ask)
        Observable<BaseModel> getData(@Field("contactName") String contactName, @Field("contactPhone") String contactPhone
        , @Field("pName") String pName, @Field("cName") String cName,
                                      @Field("aName") String aName, @Field("detailAddress") String detailAddress, @Field("memo") String memo);
    }

    public static Observable<BaseModel> getVideoAsk(Context context,String contactName,String contactPhone,String pName,String cName,String aName,String detailAddress,String memo) {
        SchoolAskService service = RestHelper.getBaseRetrofit(context).create(SchoolAskService.class);
        return service.getData(contactName,contactPhone,pName,cName,aName,detailAddress,memo);
    }

    /**
     * 记录和视频浏览量
     */
    private interface SchoolNumService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.School_Video_Num)
        Observable<BaseModel> getData(@Field("videoId") String videoId);
    }

    public static Observable<BaseModel> getVideoNum(Context context,String videoId) {
        SchoolNumService service = RestHelper.getBaseRetrofit(context).create(SchoolNumService.class);
        return service.getData(videoId);
    }

    /**
     * 视频详情
     */
    private interface SchoolVideoDetailService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.School_Video_Detail)
        Observable<VideoDetailModel> getData(@Field("videoId") String videoId);
    }

    public static Observable<VideoDetailModel> getVideoDetail(Context context,String videoId) {
        SchoolVideoDetailService service = RestHelper.getBaseRetrofit(context).create(SchoolVideoDetailService.class);
        return service.getData(videoId);
    }

}
