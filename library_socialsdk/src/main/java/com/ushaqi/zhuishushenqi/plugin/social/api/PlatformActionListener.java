package com.ushaqi.zhuishushenqi.plugin.social.api;

import java.util.HashMap;

/**
 * @author Andy.zhang
 * @date 2019/4/24
 */
public interface PlatformActionListener {
    /**
     * @param plat
     * @param action
     * @param data
     */
    void onComplete(SocialPlatform plat, int action, HashMap<String, Object> data);

    /**
     * @param plat
     * @param action
     * @param t
     */
    void onError(SocialPlatform plat, int action, Throwable t);

    /**
     * @param plat
     * @param action
     */
    void onCancel(SocialPlatform plat, int action);
}
