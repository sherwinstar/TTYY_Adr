package com.ushaqi.zhuishushenqi.plugin.social;

import android.app.Activity;
import android.content.Context;

import com.ushaqi.zhuishushenqi.plugin.social.api.SocialPlatform;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.SocialWX;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Andy.zhang
 * @date 2019/4/24
 */
public class SocialApiImpl {
    private static final String TAG = SocialApiImpl.class.getSimpleName();
    private static volatile SocialApiImpl sInstance;

    public static SocialApiImpl getInstance() {
        if (sInstance == null) {
            synchronized (SocialApiImpl.class) {
                if (sInstance == null) {
                    sInstance = new SocialApiImpl();
                }
            }
        }

        return sInstance;
    }

    private static final SocialPlatform DEFAULT_PLATFORM = new SocialPlatform() {
        @Override
        public String getPlatformName() {
            return null;
        }

        /**
         *
         * @param activity
         */
        @Override
        public void doAuth(Activity activity) {
            SocialUtils.printLog(TAG, "doAuth");
            handleError(ACTION_AUTH, new Throwable("NO PLATFORM"));
        }
    };

    private HashMap<String, SocialPlatform> mPlatformMap = new HashMap<>(4);
    private Context mContext;
    private SocialBridge mSocialBridge;

    /**
     * @return
     */
    public Context getContext() {
        return mContext;
    }

    public SocialBridge getSocialBridge() {
        return mSocialBridge;
    }

    public void initSocialBridge(SocialBridge socialBridge) {
        mSocialBridge = socialBridge;
    }

    public void destroy() {
        if (mPlatformMap == null) {
            return;
        }

        try {
            Iterator iter = mPlatformMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                SocialPlatform platform = (SocialPlatform) entry.getValue();
                if (platform != null) {
                    platform.release();
                }
            }
            mPlatformMap.clear();
            mPlatformMap = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param platformName
     * @return
     */
    public SocialPlatform getPlatform(final String platformName) {
        if (mPlatformMap == null) {
            mPlatformMap = new HashMap<>(2);
        }

        SocialPlatform platform = mPlatformMap.get(platformName);
        if (platform == null) {
            platform = createPlatform(platformName);
            if (platform != null) {
                mPlatformMap.put(platformName, platform);
            }
        }

        if (platform == null) {
            platform = DEFAULT_PLATFORM;
        }

        return platform;
    }

    /**
     * @param platformName
     * @return
     */
    private static SocialPlatform createPlatform(final String platformName) {
     /*   if (SocialQQ.NAME.equals(platformName)) {
            return new SocialQQ();
        } else*/
        if (SocialWX.NAME.equals(platformName)) {
            return new SocialWX();
        } /*else if (SocialWeiBo.NAME.equals(platformName)) {
            return new SocialWeiBo();
        }*/

        return null;
    }
}

