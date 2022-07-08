package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.SupplierModel;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.SurpliModel;
import com.puyue.www.qiaoge.model.home.GuessModel;
import com.puyue.www.qiaoge.model.home.RecommendModel;
import com.puyue.www.qiaoge.model.home.SearchResultsModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ${王涛} on 2019/9/27
 */
public class RecommendApI {

    private interface RecommendService {
        @POST(AppInterfaceAddress.RECOMMEND)
        Observable<RecommendModel> getData();
    }

    public static Observable<RecommendModel> requestData(Context context) {
        RecommendService service = RestHelper.getBaseRetrofit(context).create(RecommendService.class);
        return service.getData();
    }



    private interface ProdRecommendService {
        @POST(AppInterfaceAddress.RECOMMEND)
        Observable<ProdRecommendModel> getData();
    }

    public static Observable<ProdRecommendModel> getProdRecommend(Context context) {
        ProdRecommendService service = RestHelper.getBaseRetrofit(context).create(ProdRecommendService.class);
        return service.getData();
    }

    /**
     * 搜索结果页面接口
     */
    private interface SearchResultService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.SEARCHRESULT)
        Observable<SearchResultsModel> getData(@Field("productName") String productName,
                                               @Field("pageNum") int pageNum,
                                               @Field("pageSize") int pageSize,
                                               @Field("isSelf") int isSelf,
                                               @Field("saleDownFlag") int saleDownFlag,
                                               @Field("priceFlag") int priceFlag
                                           );
    }

    public static Observable<SearchResultsModel> requestData(Context context, String productName,
                                                             int pageNum, int pageSize,int isSelf,int saleDownFlag,int priceFlag) {
        SearchResultService service = RestHelper.getBaseRetrofit(context).create(SearchResultService.class);
        return service.getData(productName,pageNum,pageSize,isSelf,saleDownFlag,priceFlag);
    }

    /**
     * 品牌搜索结果
     */
    private interface SearchProdService {
        @POST(AppInterfaceAddress.ProdRECOMMEND)
        Observable<ProdRecommendModel> getData();
    }

    public static Observable<ProdRecommendModel> getSearchProd(Context context) {
        SearchProdService service = RestHelper.getBaseRetrofit(context).create(SearchProdService.class);
        return service.getData();
    }

    /**
     * 猜你喜欢
     */
    private interface GuessService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.GUESSLIKE)
        Observable<GuessModel> getData(@Field("productMainId") String productMainId);
    }

    public static Observable<GuessModel> getLikeList(Context context, String productMainId) {
        GuessService service = RestHelper.getBaseRetrofit(context).create(GuessService.class);
        return service.getData(productMainId);
    }

    /**
     * 供应商信息
     */

    private interface SupplierService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Business)
        Observable<SurpliModel> getData(@Field("supplierId") String supplierId);
    }

    public static Observable<SurpliModel> getSupplier(Context context, String supplierId) {
        SupplierService service = RestHelper.getBaseRetrofit(context).create(SupplierService.class);
        return service.getData(supplierId);
    }

    /**
     * 供应商列表数据
     */
    private interface SupplierListService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Business_List)
        Observable<SurpliListModel> getData(@Field("supplierId") String supplierId, @Field("searchName") String searchName, @Field("pageNum") int pageNum,
                                            @Field("pageSize") int pageSize);
    }

    public static Observable<SurpliListModel> getSupplierList(Context context, String supplierId, String searchName, int pageNum, int pageSize) {
        SupplierListService service = RestHelper.getBaseRetrofit(context).create(SupplierListService.class);
        return service.getData(supplierId,searchName,pageNum,pageSize);
    }

    /**
     * 数据埋点
     */
    private interface DataPointService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Data_Burial)
        Observable<BaseModel> getData(@Field("pageType") int pageType, @Field("num") long num);
    }

    public static Observable<BaseModel> getDatas(Context context, int pageType,long num) {
        DataPointService service = RestHelper.getBaseRetrofit(context).create(DataPointService.class);
        return service.getData(pageType,num);
    }
}
