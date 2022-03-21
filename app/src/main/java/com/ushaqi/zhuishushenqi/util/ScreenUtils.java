package com.ushaqi.zhuishushenqi.util;

import android.content.Context;

/**
 * @author shijingxing
 * @date 2020/6/30
 */
public class ScreenUtils {

    private static int sScreenWidth;
    private static int sScreenHeight;

    public static int getScreenWidth(Context context){
        if(sScreenWidth == 0){
            sScreenWidth = DisplayUtils.getScreenWidth(context);
        }
        return sScreenWidth;
    }

    public static int getScreenHeight(Context context){
        if(sScreenHeight == 0){
            sScreenHeight = DisplayUtils.getScreenHeight(context);
        }
        return sScreenHeight;
    }
}
