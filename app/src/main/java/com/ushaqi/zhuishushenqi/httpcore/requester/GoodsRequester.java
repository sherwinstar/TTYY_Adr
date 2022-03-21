package com.ushaqi.zhuishushenqi.httpcore.requester;



import com.ushaqi.zhuishushenqi.httpcore.BaseHttpHelper;
import com.ushaqi.zhuishushenqi.httpcore.api.CommonApis;
import com.ushaqi.zhuishushenqi.httpcore.api.GoodsApis;


public class GoodsRequester extends BaseHttpHelper<GoodsApis> {
  private static GoodsRequester sInstance;


  GoodsRequester() {
    super();
  }

  public static GoodsRequester getInstance() {
    if (sInstance == null) {
           synchronized (GoodsRequester.class) {
               if (sInstance == null) {
                   sInstance = new GoodsRequester();
               }
         }
      };
      return sInstance;
  }

  @Override
  protected String getRequestHost() {
      return GoodsApis.HOST;
  }
}
