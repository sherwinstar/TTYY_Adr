package com.ushaqi.zhuishushenqi.httpcore.requester;



import com.ushaqi.zhuishushenqi.httpcore.BaseHttpHelper;
import com.ushaqi.zhuishushenqi.httpcore.api.AccountApis;
import com.ushaqi.zhuishushenqi.httpcore.api.CommonApis;


public class AccountRequester extends BaseHttpHelper<AccountApis> {
  private static AccountRequester sInstance;


  AccountRequester() {
    super();
  }

  public static AccountRequester getInstance() {
    if (sInstance == null) {
           synchronized (AccountRequester.class) {
               if (sInstance == null) {
                   sInstance = new AccountRequester();
               }
         }
      };
      return sInstance;
  }

  @Override
  protected String getRequestHost() {
      return AccountApis.HOST;
  }
}
