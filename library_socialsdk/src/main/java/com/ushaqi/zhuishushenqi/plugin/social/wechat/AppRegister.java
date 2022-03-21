package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppRegister extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WeChatCore.getsInstance().registerAppToWechat(context);
    }
}
