package com.ushaqi.zhuishushenqi.module.baseweb.helper;


import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.repository.GlobalPreference;


/**
 * Created by shijingxing on 2019/5/7.
 * 调试界面管理类
 */
public class CodeDebugManager {

    /**
     * api，web的http开关SP的key
     */
    public final static String SP_HTTP_SWITCH = "sp_http_switch";

    /**
     * web的th5开关SP的key
     */
    public final static String SP_TH5_SWITCH = "sp_th5_switch";

    /**
     * 修改api host开关SP的key
     */
    public final static String SP_SPECIFIC_API_HOST_SWITCH = "sp_specific_api_host_switch";

    /**
     * 修改coin host开关SP的key
     */
    public final static String SP_SPECIFIC_COIN_HOST_SWITCH = "sp_specific_coin_host_switch";

    /**
     * 修改的api host的key
     */
    public final static String SP_SPECIFIC_API_HOST = "sp_specific_api_host";

    /**
     * 修改的coin host的key
     */
    public final static String SP_SPECIFIC_COIN_HOST = "sp_specific_coin_host";



    /**
     * 设置里面显示个推的tag
     */
    public final static String SP_GETUI_TAG_SWITCH = "sp_getui_tag_switch";



    /**
     * api，web的http开关
     */
    private boolean mHttpSwitchOpen;

    /**
     * web的th5开关
     */
    private boolean mTh5SwitchOpen;

    /**
     * 修改api host开关
     */
    private boolean mApiHostOpen;

    /**
     * 修改coin host开关
     */
    private boolean mCoinHostOpen;

    /**
     * 修改的api host的值
     */
    private String mApiHost;

    /**
     * 修改的coin host的值
     */
    private String mCoinHost;

    private boolean mGetuiTagSwitchOpen;

    private static CodeDebugManager sCodeDebugManager;

    private CodeDebugManager() {
        initValueFromSp();
    }

    public static CodeDebugManager getInstance() {
        if (sCodeDebugManager == null) {
            synchronized (CodeDebugManager.class) {
                if (sCodeDebugManager == null) {
                    sCodeDebugManager = new CodeDebugManager();
                }
            }
        }
        return sCodeDebugManager;
    }

    /**
     * 切换web页面的https和h5
     *
     * @param url
     * @return
     */
    public String changeH5HttpAndTh5(String url) {
        String httpUrl = changeH5UrlToHttp(url);
        String th5Url = changeH5UrlToTh5(httpUrl);
        return th5Url;
    }

    /**
     * 切换Api的host
     *
     * @param url
     * @return
     */
    public String changeApiHost(String url) {
        if (mApiHostOpen) {
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(mApiHost)) {
                String changeUrl = url.replaceFirst("//api.{0,12}\\.zhuishushenqi.com+", "//" + mApiHost);
                return changeUrl;
            }
        }
        return url;
    }

    /**
     * 切换coin的host
     *
     * @param url
     * @return
     */
    public String changeCoinHost(String url) {
        if (mCoinHostOpen) {
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(mCoinHost) && url.contains("goldcoin.zhuishushenqi.com")) {
                String changeUrl = url.replaceFirst("goldcoin.zhuishushenqi.com", mCoinHost);
                return changeUrl;
            }
        }
        return url;
    }


    public boolean isHttpSwitchOpen() {
        return mHttpSwitchOpen;
    }

    public void setHttpSwitchOpen(boolean httpSwitchOpen) {
        this.mHttpSwitchOpen = httpSwitchOpen;
        GlobalPreference.getInstance().saveBoolean(SP_HTTP_SWITCH, httpSwitchOpen);
    }

    public boolean isTh5SwitchOpen() {
        return mTh5SwitchOpen;
    }


    public boolean isGetuiTagSwitchOpen() {
        return mGetuiTagSwitchOpen;
    }

    public void setGetuiTagSwitchOpen(boolean getuiTagSwitchOpen) {
        this.mGetuiTagSwitchOpen = getuiTagSwitchOpen;
        GlobalPreference.getInstance().saveBoolean(SP_GETUI_TAG_SWITCH, getuiTagSwitchOpen);
    }

    public void setTh5SwitchOpen(boolean th5SwitchOpen) {
        this.mTh5SwitchOpen = th5SwitchOpen;
        GlobalPreference.getInstance().saveBoolean(SP_TH5_SWITCH, th5SwitchOpen);
    }

    public boolean isApiHostOpen() {
        return mApiHostOpen;
    }

    public void setApiHostOpen(boolean apiHostOpen) {
        this.mApiHostOpen = apiHostOpen;
        GlobalPreference.getInstance().saveBoolean(SP_SPECIFIC_API_HOST_SWITCH, apiHostOpen);
    }

    public boolean isCoinHostOpen() {
        return mCoinHostOpen;
    }

    public void setCoinHostOpen(boolean coinHostOpen) {
        this.mCoinHostOpen = coinHostOpen;
        GlobalPreference.getInstance().saveBoolean(SP_SPECIFIC_COIN_HOST_SWITCH, coinHostOpen);
    }

    public String getApiHost() {
        return mApiHost;
    }

    public void setApiHost(String apiHost) {
        this.mApiHost = apiHost;
        GlobalPreference.getInstance().saveString(SP_SPECIFIC_API_HOST, apiHost);
    }

    public String getCoinHost() {
        return mCoinHost;
    }

    public void setCoinHost(String coinHost) {
        this.mCoinHost = coinHost;
        GlobalPreference.getInstance().saveString(SP_SPECIFIC_COIN_HOST, coinHost);
    }

    /**
     * 初始化SP值
     */
    private void initValueFromSp() {
        mHttpSwitchOpen = GlobalPreference.getInstance().getBoolean(SP_HTTP_SWITCH, false);
        mTh5SwitchOpen = GlobalPreference.getInstance().getBoolean(SP_TH5_SWITCH, false);
        mApiHostOpen = GlobalPreference.getInstance().getBoolean(SP_SPECIFIC_API_HOST_SWITCH, false);
        mCoinHostOpen = GlobalPreference.getInstance().getBoolean(SP_SPECIFIC_COIN_HOST_SWITCH, false);
        mApiHost = GlobalPreference.getInstance().getString(SP_SPECIFIC_API_HOST);
        mCoinHost = GlobalPreference.getInstance().getString(SP_SPECIFIC_COIN_HOST);
        mGetuiTagSwitchOpen= GlobalPreference.getInstance().getBoolean(SP_GETUI_TAG_SWITCH, false);
    }

    /**
     * 切换web页面的https到http
     *
     * @param url
     * @return
     */
    private String changeH5UrlToHttp(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (mHttpSwitchOpen) {
            if (url.startsWith("https")) {
                return url.replaceFirst("https", "http");
            }
        }
        return url;
    }

    /**
     * 切换web页面的h5到th5
     *
     * @param url
     * @return
     */
    private String changeH5UrlToTh5(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (mTh5SwitchOpen) {
            if (!url.contains("th5.zhuishushenqi.com") && url.contains("h5.zhuishushenqi.com")) {
                return url.replaceFirst("//h5.zhuishushenqi.com", "//th5.zhuishushenqi.com");
            }
        }
        return url;
    }
}
