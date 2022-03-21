package com.ushaqi.zhuishushenqi.httpcore.api;

import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.model.BindPddRelationBean;
import com.ushaqi.zhuishushenqi.model.CommandGoodsBean;
import com.ushaqi.zhuishushenqi.model.GoodsUrlBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
   * 商品相关 
   *@author  zengcheng
   *create at 2021/6/22 下午2:22
*/
public interface GoodsApis {

    String HOST = HttpUrlProvider.getServerRoot();


    /**
     * 淘宝淘口令搜索
     *
     *
     */
    @FormUrlEncoded
    @POST("/shopping/Goods/SearchTkl")
    Call<CommandGoodsBean> getTaoCommandGoods(@Field("token") String token,
                                              @Field("tkl") String tkl, @Field("SearchType") String SearchType);



    /**
     *根据 Token, goodsId 获取商品链接 淘宝
     *
     *
     */
    @FormUrlEncoded
    @POST("/shopping/Goods/TaobaoGoodsUrl")
    Call<GoodsUrlBean> getTaobaoGoodsUrl(
            @Field("goodsId") String goodsId,@Field("token") String token,@Field("topAccessToken")String topAccessToken);


    /**
     *根据 Token, goodsId 获取商品链接 pdd
     *
     *
     */
    @FormUrlEncoded
    @POST("/shopping/Goods/PddGoodsUrl")
    Call<GoodsUrlBean> getPddGoodsUrl(
            @Field("goodsId") String goodsId,@Field("token") String token);


    /**
     * 绑定淘宝客户端
     * @param topAccessToken
     * @param token
     * @param platform
     * @return
     */
    @FormUrlEncoded
    @POST("/shopping/Goods/BindTaobaoRelation")
    Call<BindPddRelationBean> bindTaobaoRelation(
            @Field("topAccessToken") String topAccessToken,
            @Field("token") String token,
            @Field("platform") int platform
            );


    /**
     * 绑定拼多多客户端
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("/shopping/Goods/BindPddRelation")
    Call<BindPddRelationBean> bindPddRelation(
            @Field("token") String token
    );

}
