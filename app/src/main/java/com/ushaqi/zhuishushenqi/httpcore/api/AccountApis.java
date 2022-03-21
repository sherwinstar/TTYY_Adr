package com.ushaqi.zhuishushenqi.httpcore.api;

import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.model.Account;
import com.ushaqi.zhuishushenqi.model.BaseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
  * 账号相关
   *@author  zengcheng
   *create at 2021/6/22 下午2:22
*/
public interface AccountApis {
    String HOST = HttpUrlProvider.getServerRoot();


    /**
     *@desc  绑定邀请码(还未给到,贾)
     *@param
     *@return
     */
    @FormUrlEncoded
    @POST("/shopping/User/bindPromoter")
    Call<BaseModel> bindInviteCode(@Field("token") String token,
                                   @Field("primoterId") String primoterId);
    /**
     *@desc  绑定支付宝
     *@param
     *@return
     */
    @FormUrlEncoded
    @POST("/shopping/User/bindAliPay")
    Call<BaseModel> bindAliPay(@Field("token") String token, @Field("aliAccount") String aliAccount,
                               @Field("idCardnum") String idCardnum,
                               @Field("realName") String realName);

    /**
     * 三方登录验证
     *
     *
     */
    @FormUrlEncoded
    @POST("/shopping/User/Login")
    Call<Account> thirdUserLogin(
            @Field("openid") String openid,
            @Field("access_token") String accessToken);


    @FormUrlEncoded
    @POST("/shopping/User/UserInfo")
    Call<Account> getUserInfo(
            @Field("access_token") String token);

    @FormUrlEncoded
    @POST("/shopping/User/UserCollectionGoodsAdd")
    Call<BaseModel> collectGoodsAdd(
            @Field("token") String token,
            @Field("sType") String sType,
            @Field("merchantTitle") String merchantTitle,
            @Field("payUser") String payUser,
            @Field("goodsImgUrl") String goodsImgUrl,
            @Field("goodsTitle") String goodsTitle,
            @Field("goodsId") String goodsId,
            @Field("coupons") double coupons,
            @Field("saveMoney") double saveMoney,
            @Field("price") double price,
            @Field("originalprice") double originalprice,
            @Field("isCompareOrder") boolean isCompareOrder
            );


}
