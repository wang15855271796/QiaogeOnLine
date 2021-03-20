package com.puyue.www.qiaoge.api.mine.collection;

import android.content.Context;

import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.home.ProductNormalModel;
import com.puyue.www.qiaoge.model.mine.collection.CollectionListModel;
import com.puyue.www.qiaoge.model.mine.collection.DeleteCollectionModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/4/20.
 */

public class MyCollectionListAPI {
//    public interface MyCollectionListService {
//        @GET(AppInterfaceAddress.GET_COLLECTION_LIST)
//        Observable<CollectionListModel> setParams();
//    }
//
//    public static Observable<CollectionListModel> requestCollectionList(Context context) {
//        Observable<CollectionListModel> collectionListModelObservable = RestHelper.getBaseRetrofit(context).create(MyCollectionListService.class).setParams();
//        return collectionListModelObservable;
//    }

    public interface MyCollectionListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.GET_COLLECTION_LIST)
        Observable<ProductNormalModel> setParams(@Field("state") String state, @Field("pageNum") int pageNum, @Field("pageSize") String pageSize);
    }

    public static Observable<ProductNormalModel> requestCollectionList(Context context, String state,int pageNum,String pageSize) {
        Observable<ProductNormalModel> collectionListModelObservable = RestHelper.getBaseRetrofit(context).create(MyCollectionListService.class).setParams(state,pageNum,pageSize);
        return collectionListModelObservable;
    }
}
