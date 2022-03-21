package com.ushaqi.zhuishushenqi.httpcore.api;

import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.model.SwitchConfig;
import com.ushaqi.zhuishushenqi.model.UpdateApkConfig;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * 通用接口
 *
 */

public interface CommonApis {

    String HOST = HttpUrlProvider.getServerRoot();


    /**
     *@desc  送审开关
     *@param
     *@return
     */
    @FormUrlEncoded
    @POST("/shopping/Config/auditSwitchConfig")
    Call<SwitchConfig> getAuditSwitchConfig(@Field("platform") String platform,
                                            @Field("channelName") String channelName, @Field("version")String versionCode);


    /**
     *@desc  得到分享的图片
     *@param
     *@return
     */

    @GET("/shopping/Config/shareImg/{promoterId}")
    Call<SwitchConfig> getShareImg(@Query("promoterId") String promoterId);

    /**
     *@desc  更新配置
     *@param
     *@return
     */
    @FormUrlEncoded
    @POST("/shopping/Config/updateConfig")
    Call<UpdateApkConfig> getUpdateApkConfig(@Field("channelId") String channelId, @Field("version")String versionCode);



}
