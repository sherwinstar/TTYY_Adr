package com.ushaqi.zhuishushenqi.repository;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;

/**
 * 保存应用程序配置管理类 <br>
 * <p>
 * Note: 该文件将保存到应用程序级别的sp文件，多个登录用户访问的数据都是共享的。<br>
 * 如果需要根据不同的登录用户来区分独立配置，请使用{@code com.ushaqi.zhuishushenqi.ui.virtualcoin.repository.UserPreference}
 * </p>
 * add JackHu
 */
public class GlobalPreference extends BasePrefAccessor {



    private static  GlobalPreference instance;

    private GlobalPreference(String fileName) {
        super(fileName);
    }



    public static GlobalPreference getInstance() {
        if (instance == null) {
            instance = new GlobalPreference(AppConstants.APPLICATION_ID + "_preferences");
        }
        return instance;
    }
}
