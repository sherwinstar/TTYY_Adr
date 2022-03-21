package com.ushaqi.zhuishushenqi.model.baseweb;

import android.text.TextUtils;

/**
 * Created by mac on 2018/12/6.
 */

public class CommonEntity {


    public String type = "";

    public String link = "";

    public String title = "";


    public boolean isLinkType() {
        return TextUtils.equals("link", type);
    }


    public boolean isRefreshType(){
        return TextUtils.equals("refresh", type);
    }
}
