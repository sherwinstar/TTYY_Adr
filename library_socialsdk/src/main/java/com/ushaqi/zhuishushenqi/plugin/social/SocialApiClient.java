package com.ushaqi.zhuishushenqi.plugin.social;

import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;

/**
 * @author Andy.zhang
 * @date 2019/4/24
 */
public final class SocialApiClient {

    /**
     * @param platformName
     * @return
     */
    public static SocialPlatform getPlatform(final String platformName) {
        return SocialApiImpl.getInstance().getPlatform(platformName);
    }

    /**
     * @param socialBridge
     */
    public static void initSocialBridge(SocialBridge socialBridge) {
        SocialApiImpl.getInstance().initSocialBridge(socialBridge);
    }

    public static void destroy() {
        SocialApiImpl.getInstance().destroy();
    }
}
