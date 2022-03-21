package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import static com.ushaqi.zhuishushenqi.module.baseweb.WebConstans.JUMP;

import android.text.TextUtils;
import android.webkit.WebView;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.model.baseweb.JumpEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;

import java.util.HashMap;

/**
 * <p>
 *
 * @ClassName: WebLoadUtil
 * @Date: 2019-05-22 14:47
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: web加载页面，后期可以扩展加载本地页面
 * </p>
 */
public class WebLoadUtil {

    private static volatile WebLoadUtil sInstance;

    private WebLoadUtil() {
    }

    public static WebLoadUtil getInstance() {
        if (sInstance == null) {
            synchronized (WebLoadUtil.class) {
                if (sInstance == null) {
                    sInstance = new WebLoadUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 打开WebActivity
     */
    public void loadWebPage(WebView webView, ZssqWebData webData) {
        if (webView != null) {
            if (webData != null && !TextUtils.isEmpty(webData.getUrl())) {
                String url = jointUrl(webData);
                url = CodeDebugManager.getInstance().changeH5HttpAndTh5(url);
                webView.loadUrl(url);
            }
        } else {
            throw new NullPointerException("WebView is null!");
        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 20:21
     * @Description 拼接url
     */
    public String jointUrl(ZssqWebData webData) {
        return jointUrl(webData.getUrl(), webData.getTitle());
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 20:21
     * @Description 拼接URl
     */
    public String jointUrl(String url, String title) {
        //拼接时间戳
        //h5要求只有vip页面的不要加时间戳
        if (!url.contains("vipCenter")) {
            if (url.contains("?")) {
                url = url + "&t=" + System.currentTimeMillis();
            } else {
                url = url + "?t=" + System.currentTimeMillis();
            }
        }

        //拼接Version
        if (!url.contains("version=")) {
            if (url.contains("?")) {
                url = url + "&version=" + WebConstans.WEB_VERSION;
            } else {
                url = url + "?version=" + WebConstans.WEB_VERSION;
            }
        }
        //拼接平台参数
        if (!url.contains("platform=")) {
            if (url.contains("?")) {
                url = url + "&platform=android";
            } else {
                url = url + "?platform=android";
            }
        }
        //拼接用户设备信息
//<<<<<<< HEAD
//        if (!url.contains("deviceid=")) {
//            if (url.contains("?")) {
//                url = url + "&deviceid=" + AppHelper.getDeviceId(MyApplication.getInstance());
//            } else {
//                url = url + "?deviceid=" + AppHelper.getDeviceId(MyApplication.getInstance());
//            }
//        }
//=======
        //网安过检，干掉
        /*if (!url.contains("deviceid=")) {
            url = url + "&deviceid=" + AppHelper.getDeviceId(MyApplication.getInstance());
        }*/

        //拼接包名
        if (!url.contains("packageName=")) {
            if (url.contains("?")) {
                url = url + "&packageName=" + AppConstants.APPLICATION_ID;
            } else {
                url = url + "?packageName=" + AppConstants.APPLICATION_ID;
            }
        }

//        //拼接用户信息
//        if (AppHelper.isLogin()) {
//            if (!url.contains("gender=") && AppHelper.getAccount() != null && AppHelper.getAccount().getUser() != null && !TextUtils.isEmpty(AppHelper.getAccount().getUser().getGender())) {
//                if (url.contains("?")) {
//                    url = url + "&gender=" + AppHelper.getAccount().getUser().getGender();
//                } else {
//                    url = url + "?gender=" + AppHelper.getAccount().getUser().getGender();
//                }
//            }
//
//            if (!url.contains("userid") && AppHelper.getAccount() != null && AppHelper.getAccount().getUser() != null && !TextUtils.isEmpty(AppHelper.getAccount().getUser().getId())) {
//                if (url.contains("?")) {
//                    url = url + "&userid=" + AppHelper.getAccount().getUser().getId();
//                } else {
//                    url = url + "?userid=" + AppHelper.getAccount().getUser().getId();
//                }
//            }
//
//            if (!url.contains("token=") && AppHelper.getAccount() != null && !TextUtils.isEmpty(AppHelper.getAccount().getToken())) {
//                if (url.contains("?")) {
//                    url = url + "&token=" + AppHelper.getAccount().getToken();
//                } else {
//                    url = url + "?token=" + AppHelper.getAccount().getToken();
//                }
//            }
//        } else {
//            String userIdNecessary = UserHelper.getInstance().getUserIdNecessary();
//            if (!TextUtils.isEmpty(userIdNecessary)) {
//                url = url + "&userid=" + userIdNecessary;
//            }
//        }
//        //vedio相关
//        if (url.contains("video")) {
//            WebVideoHelper.videoUrl = url;
//            WebVideoHelper.videoTitle = title;
//        }
//        if (TeenagerModeHelper.isOpenTeenagerMode()) {
//            //youthModel=true，标记青少年模式不显示角标
//            if (url.contains("?")) {
//                url = url + "&youthModel=true";
//            } else {
//                url = url + "?youthModel=true";
//            }
//        }
//
//        if (url.contains(AppConstants.TASK_CENTER)) {
//            url = url + "&rv=" + RewardVideoSwitchMananger.getInstance().getRvState();
//        }
        return url;
    }

    /**
     * @param url
     * @return 寻找url的指定参数
     */
    public static HashMap getUrlParams(String url) {
        HashMap<String, Object> map = new HashMap<>();
        url = url.replace("?", ";");
        if (!url.contains(";")) {
            return map;
        }
        if (url.split(";").length > 0) {
            String[] arr = url.split(";")[1].split("&");
            for (String s : arr) {
                String key = s.split("=")[0];
                String value = s.split("=")[1];
                map.put(key, value);
            }
            return map;

        } else {
            return map;
        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-24 09:12
     * @Description 回收单例对象
     */
    public void recycle() {
        if (sInstance != null) {
            sInstance = null;
        }
    }

    public String getPddSmallAppUrl(JumpEntity jumpEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(JUMP);
        stringBuilder.append(String.format("t=%d", System.currentTimeMillis()));
        stringBuilder.append(String.format("&param=%s", jumpEntity.toPddSmallAppString()));
        return stringBuilder.toString();
    }
}
