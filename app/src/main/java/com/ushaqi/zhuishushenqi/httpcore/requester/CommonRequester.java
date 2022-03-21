package com.ushaqi.zhuishushenqi.httpcore.requester;



import com.ushaqi.zhuishushenqi.httpcore.BaseHttpHelper;
import com.ushaqi.zhuishushenqi.httpcore.api.CommonApis;



public class CommonRequester extends BaseHttpHelper<CommonApis> {
  private static CommonRequester sInstance;


  CommonRequester() {
    super();
  }

  public static CommonRequester getInstance() {
    if (sInstance == null) {
           synchronized (CommonRequester.class) {
               if (sInstance == null) {
                   sInstance = new CommonRequester();
               }
         }
      };
      return sInstance;
  }

  @Override
  protected String getRequestHost() {
      return CommonApis.HOST;
  }
}
