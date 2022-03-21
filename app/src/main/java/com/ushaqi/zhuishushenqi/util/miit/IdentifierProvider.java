package com.ushaqi.zhuishushenqi.util.miit;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisManager;

/**
 *
 * @author Andy.zhang
 * @date 2019/10/18
 */
public class IdentifierProvider {
    public static final String DEVICE_IDENTIFIER_OAID = "device_identifier_OAID";

    /**
     * miit sdk支持的最低Android版本
     */
    public static final int MIN_MIIT_SUPPORT_SDK_VERSION = 29;

    public static boolean enableOAID(final Context context) {
        /*if ((OSHelper.isVivo() || OSHelper.isOppo()) && Build.VERSION.SDK_INT < 29) {
            return false;
        }*/

        return Build.VERSION.SDK_INT >= MIN_MIIT_SUPPORT_SDK_VERSION;
    }

    /**
     *
     * @param context
     */
    public static void getMobileDeviceIdentifier(final Context context) {
        final String localOAID = GlobalPreference.getInstance().getString(DEVICE_IDENTIFIER_OAID, "");
        if (!TextUtils.isEmpty(localOAID)) {
            return;
        }
        MiitHelper.getMobileDeviceIdentifier(context, (oaid, udid, vaid, aaid) -> {
            if (!TextUtils.isEmpty(oaid)) {
                AppConstants.sOAID = oaid;
                GlobalPreference.getInstance().saveString(DEVICE_IDENTIFIER_OAID, oaid);
                SensorsAnalysisManager.registerOaidStaticParam(oaid);
            }

//                if (!TextUtils.isEmpty(vaid)) {
//                    AppGlobalManager.sVAID = vaid;
//                    GlobalPreference.getInstance().saveString(DEVICE_IDENTIFIER_VAID, vaid);
//                }
        });
    }

}
