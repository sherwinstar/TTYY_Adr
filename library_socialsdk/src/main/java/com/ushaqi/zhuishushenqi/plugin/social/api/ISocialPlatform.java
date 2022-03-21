package com.ushaqi.zhuishushenqi.plugin.social.api;

import android.app.Activity;
import android.content.Intent;

public interface ISocialPlatform {

    String getPlatformName();

    /**
     * @param listener
     */
    void setPlatformActionListener(PlatformActionListener listener);

    /**
     * @param activity
     */
    void doAuth(final Activity activity);

    void handleActivityResultData(final Activity activity, int requestCode, int resultCode, Intent data);
}
