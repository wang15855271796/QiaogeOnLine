package com.puyue.www.qiaoge.api.cart;

import android.content.Context;

import com.puyue.www.qiaoge.base.BaseModel;
import com.puyue.www.qiaoge.constant.AppInterfaceAddress;
import com.puyue.www.qiaoge.helper.RestHelper;
import com.puyue.www.qiaoge.model.ApplyInfoModel;
import com.puyue.www.qiaoge.model.CheckUsedModel;
import com.puyue.www.qiaoge.model.IsApplyModel;
import com.puyue.www.qiaoge.model.SupplierModel;
import com.puyue.www.qiaoge.model.SurpliListModel;
import com.puyue.www.qiaoge.model.SurpliModel;
import com.puyue.www.qiaoge.model.cart.SendInfoModel;
import com.puyue.www.qiaoge.model.home.GetAddressModel;
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
     * 获取配送地址 ，配送时间
     */
    private interface GetSendInfoService {
        @POST(AppInterfaceAddress.Get_Send_Info)
        Observable<SendInfoModel> getData();
    }

    public static Observable<SendInfoModel> getSendInfo(Context context) {
        GetSendInfoService service = RestHelper.getBaseRetrofit(context).create(GetSendInfoService.class);
        return service.getData();
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
     * 是否已经申请过供应商入驻
     */
    private interface IsApplyModelService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Is_Apply_Provider)
        Observable<IsApplyModel> getData(@Field("phone") String phone);
    }

    public static Observable<IsApplyModel> isApplyProvider(Context context, String phone) {
        IsApplyModelService service = RestHelper.getBaseRetrofit(context).create(IsApplyModelService.class);
        return service.getData(phone);
    }

    /**
     *  验证供应商账号是否可用
     */
    private interface CheckUsedService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Check_USED)
        Observable<CheckUsedModel> getData(@Field("checkNo") String checkNo,@Field("userName") String userName);
    }

    public static Observable<CheckUsedModel> checkUsed(Context context, String checkNo,String userName) {
        CheckUsedService service = RestHelper.getBaseRetrofit(context).create(CheckUsedService.class);
        return service.getData(checkNo,userName);
    }

    /**
     * 申请入驻供应商-基本信息
     */
    private interface ApplyProviderBasicInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Apply_Provider_Basic_Info)
        Observable<GetAddressModel> getData(@Field("checkNo") String checkNo, @Field("applyPhone") String applyPhone, @Field("supplierName") String supplierName
        , @Field("address") String address, @Field("contactName") String contactName, @Field("contactPhone") String contactPhone
        , @Field("companyProvinceCode") String companyProvinceCode, @Field("companyCityCode") String companyCityCode
        , @Field("companyAreaCode") String companyAreaCode, @Field("supplyProdType") String supplyProdType);
    }

    public static Observable<GetAddressModel> applyProviderOne(Context context, String checkNo,String applyPhone,String supplierName,String address
        ,String contactName,String contactPhone,String companyProvinceCode,String companyCityCode,String companyAreaCode,String supplyProdType) {
        ApplyProviderBasicInfoService service = RestHelper.getBaseRetrofit(context).create(ApplyProviderBasicInfoService.class);
        return service.getData(checkNo,applyPhone,supplierName,address,contactName,contactPhone,companyProvinceCode,companyCityCode,companyAreaCode,supplyProdType);
    }

    /**
     * 申请入驻供应商-资质信息
     */
    private interface QualificationInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Apply_Qualification_Info)
        Observable<GetAddressModel> getData(@Field("checkNo") String checkNo, @Field("applyPhone") String applyPhone, @Field("licenseUrl") String licenseUrl
                , @Field("licenseNo") String licenseNo, @Field("companyName") String companyName, @Field("companyAddress") String companyAddress
                , @Field("companyType") int companyType, @Field("licenseLongTerm") int licenseLongTerm
                , @Field("licenseValidityStart") String licenseValidityStart, @Field("licenseValidityEnd") String licenseValidityEnd
                , @Field("businessUrl") String businessUrl, @Field("businessValidity") String businessValidity);
    }

    public static Observable<GetAddressModel> getQualification(Context context, String checkNo,String applyPhone,String licenseUrl,String licenseNo
            ,String companyName,String companyAddress,int companyType,int licenseLongTerm,String licenseValidityStart,String licenseValidityEnd
            ,String businessUrl,String businessValidity) {
        QualificationInfoService service = RestHelper.getBaseRetrofit(context).create(QualificationInfoService.class);
        return service.getData(checkNo,applyPhone,licenseUrl,licenseNo,companyName,companyAddress,companyType,licenseLongTerm,licenseValidityStart
                ,licenseValidityEnd,businessUrl,businessValidity);
    }

    /**
     * 申请入驻供应商-法人信息
     */
    private interface LegalInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Apply_Legal_Info)
        Observable<GetAddressModel> getData(@Field("checkNo") String checkNo, @Field("applyPhone") String applyPhone
                , @Field("corporateCardFront") String corporateCardFront, @Field("corporateCardReverse") String corporateCardReverse
                , @Field("idNumber") String idNumber);
    }

    public static Observable<GetAddressModel> getLegalInfo(Context context, String checkNo,String applyPhone,String corporateCardFront
            ,String corporateCardReverse,String idNumber) {
        LegalInfoService service = RestHelper.getBaseRetrofit(context).create(LegalInfoService.class);
        return service.getData(checkNo,applyPhone,corporateCardFront,corporateCardReverse,idNumber);
    }

    /**
     * 申请入驻供应商-结算账户
     */
    private interface ApplyFinalInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Apply_Provider_Final)
        Observable<GetAddressModel> getData(@Field("checkNo") String checkNo, @Field("applyPhone") String applyPhone
        , @Field("accountType") int accountType, @Field("accountName") String accountName, @Field("accountBank") String accountBank
        ,@Field("provinceCode") String provinceCode,@Field("cityCode") String cityCode,@Field("siteNo") String siteNo
        ,@Field("bankCardNo") String bankCardNo,@Field("phone") String phone,@Field("userName") String userName,@Field("pwd") String pwd);
    }

    public static Observable<GetAddressModel> getFinalInfo(Context context, String checkNo,String applyPhone,int accountType
            ,String accountName,String accountBank,String provinceCode,String cityCode,String siteNo,String bankCardNo,String phone
            ,String userName,String pwd) {
        ApplyFinalInfoService service = RestHelper.getBaseRetrofit(context).create(ApplyFinalInfoService.class);
        return service.getData(checkNo,applyPhone,accountType,accountName,accountBank,provinceCode,cityCode,siteNo,bankCardNo
                ,phone,userName,pwd);
    }

    /**
     * 获取供应商申请信息
     */
    private interface ApplyInfoService {
        @FormUrlEncoded
        @POST(AppInterfaceAddress.Get_Provider_Apply_Info)
        Observable<ApplyInfoModel> getData(@Field("checkNo") String checkNo);
    }

    public static Observable<ApplyInfoModel> getApplyInfo(Context context, String checkNo) {
        ApplyInfoService service = RestHelper.getBaseRetrofit(context).create(ApplyInfoService.class);
        return service.getData(checkNo);
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
