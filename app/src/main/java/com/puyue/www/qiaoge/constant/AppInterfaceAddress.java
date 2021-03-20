package com.puyue.www.qiaoge.constant;

/**
 * Created by GuoGai on 2016/10/31.
 */
public class AppInterfaceAddress {
    //https://shaokao.qoger.com/qiaoge/正式   http://192.168.2.188:8082/qiaoge/
    //http://116.62.67.230:8082/qiaoge/   测试http://120.55.55.99:8082/qiaoge/
    public static final String BASE_URL = "https://shaokao.qoger.com/qiaoge/";
    // 正式https://shaokao.qoger.com/qiaoge/
    //http://192.168.1.45/   本地192.168.101.69:8088
    //http://qg.zhiyun88u.com/shen
    //http://192.168.101.17/

    //http://192.168.101.41:8088/
//    queryReturnProdIsContainFullGift

    /**
     * 新必买清单
     */
    public static final String Must_New = "common/product/getMustBuyProductListNew";
    /**
     *资讯编辑
     */
    public static final String Info_Classify = "message/updateMessage";
    /**
     * 首页顶部分类
     */
    public static final String Index_Classify = "common/getHomeBasicData";
    /**
     *  顶部首页推荐
     */
    public static final String Recommend_List = "common/getProdHotKey";
    /**
     *获取资讯详情
     */
    public static final String Info_Issue_detail = "message/detail";
    /**
     * 发布资讯
     */
    public static final String Info_Issue = "message/addMessage";
    /**
     *删除资讯
     */
    public static final String Info_Deleted = "message/deleteMessage";
    /**
     * 我的资讯列表
     */
    public static final String Info_My_List = "message/mylist";
    /**
     * 资讯详情
     */
    public static final String Info_Detail = "message/detail";
    /**
     *  资讯列表
     */
    public static final String Info_List = "message/getList";
    /**
     *未登录 更换手机号码
     */
    public static final String UnLogin_Change_Phone = "auth/changePhone";
    /**
     *  找回密码(修改密码)没有验证码
     */
    public static final String UnLogin_Set_Secret = "auth/resetPwdNoCode";
    /**
     * 优惠券-去使用-跳转商品列表
     */
    public static final String Go_Use = "gift/queryGiftUseProdPage";
    /**
     *找回密码验证短信
     *
     */
    public static final String Check_Message = "auth/checkResetPwd";
    /**
     * 校验收货地址中的收货人和联系电话
     */
    public static final String Check_Contact = "auth/checkAddressByUserAndPhone";
    /**
     *  根据手机号 收货地址进行验证
     */
    public static final String Check_Address = "auth/checkByAddress";
    /**
     *获取验证地址
     */
    public static final String Get_Check_Address = "auth/getAddressByPhone";
    /**
     *校验手机号
     */
    public static final String Check_Phone = "auth/checkPhone";
    /**
     * 登录后，修改支付密码
     */
    public static final String Logined_Modify_Secret = "user/resetPayPwd";
    /**
     *  校验支付密码
     */
    public static final String Check_Pay_Secret = "user/checkPayPwd";
    /**
     * 待付款订单数量
     */
    public static final String Order_Num = "user/getWaitPayOrderNum";

    /**
     * 验证是否第一次更换
     */
    public static final String Check_First = "user/checkUserAccout";
    /**
     * 验证登录密码
     */
    public static final String Check_Secret = "user/checkLogin";
    /**
     *  登录后，更换手机号设置密码
     */
    public static final String Set_Secret = "user/resetChangePhonePwd";
    /**
     * 登录后、更换手机号获取验证码
     */
    public static final String Get_Yzm = "user/getChangePhoneVerifyCode";

    /**
     * 登录后、更换手机号验证码验证
     */
    public static final String Check_Yzm = "user/checkChangePhoneVerifyCode";
    /**
     *订单列表-去评价获取订单商品信息
     */
    public static final String Order_Comment = "commonOrder/queryOrderCommentProdList";

    /**
     *新订单列表
     */
    public static final String Order_List = "commonOrder/queryUserOrderPage";

    /**
     * 查看供应商资质信息
     */
    public static final String Query_Detail_Info = "common/getAppSupplierInfo";

    /**
     *查看优惠券指定商品
     */
    public static final String Query_Prod = "gift/queryGiftUseInProds";


    /**
     *退货判断是否有满赠商品
     */
    public static final String Is_ReturnGoods = "order/queryReturnProdIsContainFullGift";
    /**
     *首页是否根据城市展示价格
     */
    public static final String Is_Show = "common/company/enjoyProduct";
    /**
     *余额兑换优惠券
     */
    public static final String Ex_Coupon = "user/exchangeBalanceToGift";
    /**
     *关键字搜索
     */
    public static final String Hot_Key = "common/getSearchHotKey";
    /**
     * 获取注销原因
     */

    public static final String Cancle_Reason = "app/selectConfigByKey";
    /**
     *用户注销--校验用户是否可以注销
     *
     */
    public static final String Is_Cancle = "user/isCancel";

    /**
     * 是否能跳转产品详情
     */
    public static final String JumpDetail = "commonOrder/orderProdJump";

    public static final String ProdRECOMMEND = "common/queryBrandProdBrandName";
    public static final String SEARCHRESULT = "common/queryProductByName";
    public static final String RECOMMEND = "common/getRecommendProductName";
    public static final String REGISTER_AGREEMENT = "auth/getRegisterProtocol";
    public static final String SEND_AUTH_CODE = "auth/sendMsg";
    public static final String REGISTER = "auth/register";
    public static final String LOGIN = "auth/login";
    public static final String ACCOUNT_CENTER = "user/personCenter";
    public static final String LOG_OUT = "user/loginOut";
    public static final String CHANGE_LOGIN_PASSWORD = "auth/resetPwd";
    public static final String CHANGE_PAY_PASSWORD = "pay/updatePayPwd";

    /**
     *登录后，更换手机号-老号码获取验证码
     */
    public static final String Old_Change_phone = "auth/getChangeOldPhoneVerifyCode";
    /**
     *
     * 支付列表
     */
    public static final String Pay_List = "app/payChannelList";
    /**
     *
     * 判断地址是否在配送范围内
     */

    public static final String Is_Send = "common/judgeAddressIsInSend";
    /**
     * 转盘数据
     */
    public static final String Turn_Table = "user/getRegisterPoolInfo";


    /**
     * 转盘数据
     */
    public static final String Is_Turn_Table = "user/getRegisterPoolState";

    /**
     *
     * 转盘点击领取
     */
    public static final String Turn_Table_Receive = "user/getRegisterPool";
    /**
     * 获取隐私政策
     */
    public static final String Privacy = "common/getPrivacyPolicyBtn";

    /**
     * 猜你喜欢
     */
    public static final String GUESSLIKE = "common/product/getGuessYourLikeProductList";

    /**
     * 子账户详情
     */
    public static final String GET_SUB_ACCOUNT = "user/appUserGetSubAccountDetail";
    /**
     * 子账户列表
     */
    public static final String GET_SUB_USER = "user/appGetUserSubAccountList";

    /**
     * 资质信息
     */
    public static final String IntellGency = "common/getCompanyQualification";

    public static final String CHECKMESSAGE = "auth/checkRegisterCode";
    /**
     *用户注销
     */
    public static final String User_cancle = "user/cancelUser";
    /**
     * 首页基础信息
     */
    public static final String INDEXHOME = "common/basicHomeIndexInfo";

    /**
     * 首页订单信息
     */
    public static final String Index_Order = "user/getHomeOrders";
    /**
     * 必买清单
     */

    public static final String INDEXMUST = "common/product/getMustBuyProductList";

    /**
     * 首页新品，热销，降价，常用列表
     */
    public static final String NORMALLIST = "common/queryProductPageByType";

    /**
     * 首页banner
     */
    public static final String BANNER = "common/product/getTopBanner";
    /**
     * 首页其他信息
     */
    public static final String INDEXINFO = "common/getAreaHomeBasicInfo";
    /**
     * 首页其他信息
     */
    public static final String COUPONINFO = "common/getAreaHomeActiveInfo";

    /**
     * 首页优惠券弹窗列表
     */
    public static final String Coupon_List = "common/getUserPopups";

    /**
     * 活动订阅取消
     */
    public static final String SPIKEACTIVEQUERY = "common/saveOrCancelActive";

    public static final String SECKILLISTQUERY = "auth/secKillMoreList?";
    /**
     * 团购tan
     */
    public static final String TEAMTab = "common/getActiveTab";
    /**
     * 团购列表
     */
    public static final String TEAMACTIVEQUERY = "common/getActiveInfoList";

    public static final String CHECK_COMMON_CODE = "auth/CommonCheckResetPwd";
    /**
     * 添加子账户
     */
    public static final String ADD_SUB_USER = "user/appUserAddSubAccount";
    /**
     * 编辑子账户 Edit_SUB_USER
     */
    public static final String Edit_SUB_USER = "user/appUserEditSubAccount";
    /**
     * 删除子账户
     */
    public static final String DELETE_SUB_USER = "user/appUserDelSubAccount";
    /**
     * 子账户消息列表
     */
    public static final String Sub_Account_list = "user/querySubMessagePage";

    /**
     * 设子账号消息 - 消息设为已读
     */

    public static final String Message_Read = "user/subMessageRead";

    /**
     * 获取未读消息数量
     */

    public static final String Message_Unread = "user/getUnReadMessageNum";


    /**
     * 子账户消息全部已读
     *
     */
    public static final String Sub_Account_Message = "user/allSubMessageRead";

    /**
     * 获取地址列表
     */
    public static final String GET_ADDRESS_LIST = "common/getHomeAddressList";
    /**
     * 选择省市区
     */
    public static final String GET_AREA_LIST = "common/area/getAllAreaList";
    /**
     * 选择可用省市区
     */
    public static final String GET_ENABLEAREA_LIST = "common/area/getEnableAreaList";
    /**
     * 我的收藏列表
     */
    public static final String GET_COLLECTION_LIST = "commonCollect/myCollectList";
    /**
     *子账户订单列表
     *
     */
    public static final String Sub_Account_List = "user/querySupSubAllOrderPage";

    public static final String FEEDBACK = "feedback/addFeedBack";
    public static final String EDIT_ADDRESS = "address/updateAddress";
    public static final String DELETE_ADDRESS = "address/deleteAddressById";
    /**
     * 设置默认地址
     */
    public static final String EDIT_DEFAULT_ADDRESS = "address/setDefaultAddressById";
    public static final String MESSAGE_CENTER = "notice/getNoticeInfoVOPage";
    public static final String MESSAGE_DETAIL = "notice/getNoticeInfoDetail";

    public static final String GETPRODUCTDETAIL = "common/querySpecProductDetail";
    /**
     * 切换商品规格
     */
    public static final String EXCHANGEPRODUCT = "common/prodChangeSpec";
    /**
     * 商品详情用户评论
     */
    public static final String GETALLCOMMENTLISTBYPAGE = "common/getCommentListByPage?";
    public static final String HASCOLLECT = "commonCollect/hasCollect?";
    public static final String CLICKCOLLECTION = "commonCollect/collectOrCancel?";
    public static final String ADDCART = "cart/addCart?";

    /**
     * 获取订单详情
     */
    public static final String GETORDERDETAIL = "commonOrder/queryUserOrderDetail";
    /**
     * 去支付
     */
    public static final String ORDERPAY = "commonOrder/orderPay?";
    /**
     * 配送信息
     */
    public static final String DISTRIBUTE = "common/getAreaHomeOrderSendInfo";
    public static final String CHECKPAYPWD = "pay/checkPayPwd?";
    public static final String GETPAYRESULT = "commonOrder/getPaymentResult";
    /**
     * 取消订单
     */
    public static final String CANCELORDER = "commonOrder/cancelOrder?";
    /**
     * 删除订单
     */
    public static final String DELETEORDER = "commonOrder/deleteOrder";

    public static final String GETWALLETAMOUNT = "wallet/getWalletAmount?";
    /**
     * 我的账单列表
     */
    public static final String GETWALLERTRECORDBYPAGE = "wallet/queryWalletRecordMonth";

    public static final String DELETE_CART = "cart/deleteByCartIds";
    public static final String CART_BALANCE = "cart/cartToBalance";

    /**
     * 我的
     */
    public static final String USER_MY_COUNTER = "user/myCenter";
    /**
     * 商品评价
     */
    public static final String COMMENT_ORDER = "commonOrder/commentOrder";
    /**
     * 创蓝一键注册
     */
    public static final String CHAUNGLAN = "auth/getMobileCl";

    /**
     * 授权码
     */
    public static final String CHECK_CODE = "auth/validateUserInvitation";
    public static final String CONFIRM_GET_GOODS = "commonOrder/confirmGetGoods";
    public static final String APPLAY_RETURN_GOODS = "commonOrder/applayReturnGoods";

    public static final String COPY_TO_CART = "cart/copyToCart";
    public static final String UPDATE_BIND_PHONE = "user/updateBindPhone";
    public static final String UPDATE_MESSAGE_STATE = "notice/updateNoticeLookStatus";
    //版本升级
    public static final String AND_VERSION = "auth/andVersion";
    public static final String GETCARTNUM = "cart/getCartNum";
    public static final String GETCUSTOMERPHONE = "auth/getCustomerPhone";
    public static final String DELETE_COMMON_COLLECT = "commonCollect/batchCancelCollect";
    public static final String UPDATEUSERINVITATION = "auth/updateUserInvitation";
    public static final String QUERY_USER_DEDUCTBYSTATE = "gift/queryUserDeductByState";
    public static final String USER_CHOOSE_DEDUCT = "gift/queryAppOrderChooseGiftList";//gift/userChooseDeduct
    /**
     * 生成订单
     */
    public static final String CART_GENERATEORDER = "cart/generateOrder";
    public static final String POINT_MYPOINT = "point/myPoint";
    public static final String WALLETGETMYBALANCEINFO = "wallet/getMyBalanceInfo";
    public static final String COMMONSHARGETSHAREINFO = "common/share/getShareInfo";
    public static final String AUTHQUERYHOMEPROPUP = "auth/queryHomePropup";
    public static final String AUTHPOPVIEW = "auth/popupView";
    public static final String VIPPAY = "vip/pay";
    public static final String VIPPAYRESULT = "vip/payResult";
    public static final String CARTGETREDUCTDESC = "cart/getReductDesc";
    public static final String CARTPOSTCHANGEORDERDETAIL = "cart/cartNumChange";

    /**
     * 公用详情
     */
    public static final String SPECIALOFFERDETAIL = "common/getActiveInfoDetail";
    /**
     * 秒杀-更多-顶部
     */
    public static final String SECKILLMORE = "auth/secKillMore";

    /**
     * 获取热门店铺类型
     */
    public static final String REGISTERSHOPTYPE = "auth/getHotShopType";

    /**
     *  店铺类型列表数据
     */
    public static final String SHOP_TYPE_LIST = "auth/getShopTypeList";

    /**
     * 商品详情添加数量判断
     */
    public static final String JUDGEPRODUCTINVENT = "cart/judgeProductInvent";
    /**
     * 获取配送时间
     */
    public static final String GETDELIVERTIME = "cart/getDeliverTime";
    /**
     * 发送地理位置
     */
    public static final String GETUSERADDRESS = "auth/getUserAddress";

    public static final String GET_CART_LISTS = "cart/queryUserCartList";
    /**
     * 获取退货订单信息
     */
    public static final String RETURNORDERTYPE = "order/queryOrderReturnProduct";
    /**
     * 退货理由
     */
    public static final String QUERYORDERRETURNTYPE = "order/queryOrderReturnType";

    /**
     * 获取分类筛选商品名字
     */
    public static final String GETPRODUCTBRAND = "common/product/getProductBrand";

    /**
     * 购买过的商品
     */
    public static final String GETALREADYPRODUCT = "common/product/getAlreadyBuyProduct";

    /**
     * 单位改变
     */
    public static final String ONLINEPRODUCTAMOUTN = "order/sumOnlineProductAmount";

    /**
     * 分类左边数据
     */
    public static final String CLASSIFY = "common/queryProdNewClassify";

    /**
     * 分类右边
     */
    public static final String CLASSIFY_RIGHT = "common/queryAppProductByClassifyId";

    /**
     * 上传图片凭证
     */
    public static final String UPLOADMESSAGEIMG = "common/uploadMessageImg";
    /**
     * 生成退货单
     */
    public static final String RETURNORDER = "order/onlineUserReturnOrder";

    /**
     * 获取物流信息
     */

    public static final String GETORDERMAP = "commonOrder/getLogisticsDetail";
    /**
     * 我的界面确认优惠
     */
    public static final String CLOSEINFO = "user/closeExpiredInfo";
    /**
     * 上传极光推送id
     */
    public static final String SENDJGUESRID = "common/returnJiGuangUserId";
    /**
     * 下载量
     */
    public static final String LOADAMOUNT = "common/appDownLoadLogAdd";
    /**
     * 退出司机登录
     */
    public static final String DRIVERLOGOUT = "common/driverLoginOut";
    /**
     * 上传司机位置
     */
    public static final String SENDLOCAITON = "common/saveLocation";

    /**
     * 扫描先校验是否属于该司机
     */
    public static final String DRIVERCHECK = "common/checkOrderDriver";
    /**
     * 校验完成后装车
     */

    public static final String DRIVERSCANORDER = "common/scanOrderToCar";

    /**
     * 获取分类新接口
     */
    public static final String PRODUCTCLASSIFY = "common/product/queryAllClassify";
    /**
     *判断库存
     */
    public static final String ADDCARTJUDGE = "cart/judgeCartProductInvent";

    /**
     *购物车列表加减接口
     */
    public static final String ADDCARTJUDGES = "cart/userChangeCartGoodsNum";

    /**
     * 本机认证
     */
    public static final String AUTHMOBILE = "auth/verifyMobileCl";
    /**
     * 获取自提时间
     */
    public static final String QUERYSELFORDERTIME = "common/querySelfOrderDeliverTime";
    /**
     * 修改自提信息
     */
    public static final String MODIFYORDERINFO = "commonOrder/modifyOrderPickInfo";

    /**
     * 自提订单确认提货
     */
    public static final String SELFORDERRECEIVE = "commonOrder/selfOrderReceive";

    /**
     * 获取配送时间列表接口
     */
    public static final String GETDELIVERORDERTIME = "commonOrder/getOrderDeliverTime";
    /**
     * 修改配送时间接口
     */
    public static final String UPDATEDELIVERTIME = "commonOrder/updateDeliverTime";

    /**
     * 获取开放城市列表
     */
    public static final String MODIFYCITY = "common/getAreaOpenList";


    /**
     * 账单明细搜索
     */
    public static final String WALLETSEARCH = "wallet/getSearchList";
    /**
     * 我的账单列表总计
     */
    public static final String WALLETSUMPRICE = "wallet/getSumPrice";
    /**
     * 账单流水详情
     */
    public static final String WALLETDETAIL = "wallet/getWalletDetail";

    /**
     * 评价详情
     */
    public static final String EVALUATEDETAIL = "commonOrder/getCommentDetail";

    /**
     * 退货优惠均价不同单位
     */
    public static final String ORDERSUMDEDUCTAMOUNT = "order/sumProdDeductAmount";


    /**
     * 新退货订单详情
     */
    public static final String NEWRETURNDETAIL = "order/queryReturnOrderDetail";
    /**
     * 取消退货申请
     */
    public static final String USERCANCELORDER = "commonOrder/userCancelReturnOrder";

    /**
     * 首页关闭优惠券列表弹窗
     */
    public static final String Coupon_Close_List = "common/viewedUserPopups";
    /**
     * 隐私政策已读
     */
    public static final String Read_Privacy = "common/readPrivacyPolicy";

}
