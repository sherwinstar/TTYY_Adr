package com.ushaqi.zhuishushenqi.httpcore;


import com.ushaqi.zhuishushenqi.BuildConfig;

/**
 * http网络url提供者,从ApiService中拆出
 * Created by JackHu on 2019/5/15
 */
public class HttpUrlProvider {
    /**
     * 测试模式
     */
    public static final boolean DEBUG = BuildConfig.DEBUG;
    /**
     * 追书服务路径
     */
    public static String SERVER_NAME = "zhuishushenqi.com";

    /**
     * 追书服务主默认地址
     */
    private static String DEFAULT_ROOT ="http://apinew.zhuishushenqi.com";// 新地址

    /**
     * 阿里云域名
     */
    public static final String ALIYUN_ROOT = "http://aliyunapi.zhuishushenqi.com";
    public static final String ALIYUN_HOST = "aliyunapi.zhuishushenqi.com";
    /**
     * 友盟配置的默认图片链接地址
     */
    public static String default_img_url = "";

    /**
     * 图片地址
     */
    public static String IMG_PATH = default_img_url.equals("") ? "http://statics." + SERVER_NAME : default_img_url;//"http://zhui-test.qiniudn.com/";

    /**
     * apk下载路径
     */
    public static String APK_PATH = "http://statics." + SERVER_NAME;



    public static final String TOKEN_INVALID = "TOKEN_INVALID";

    public static final String REFRESH_RDO_CODE_URL = "refresh_rdo_code_url";

    public static String getServerRoot() {
        if (DEBUG) {
//            return "http://120.132.62.230:8980";
//            return "http://gpp-api.testing.zhuishushenqi.com";
//            return  "http://sp-api.testing.zhuishushenqi.com";
//            return "http://hr-0.testing.zhuishushenqi.com";
//            return "http://wry-api.testing.zhuishushenqi.com";
            return "http://testapinew.zhuishushenqi.com";
        }
//        return "http://dongfei-gleeman-api.testing.zhuishushenqi.com";
        return DEFAULT_ROOT;
    }



    public static String getNewServiceRoot(){
        if(DEBUG){
            return   "http://test.apinew.zhuishushenqi.com";
        }
        return "http://apinew.zhuishushenqi.com";
    }
    public static void updateServerName(String serverName) {
        if (serverName.equals(HttpUrlProvider.SERVER_NAME)) {
            return;
        }
        if (HttpUrlProvider.SERVER_NAME.contains("192.168")) {
            return;
        }
        IMG_PATH = HttpUrlProvider.default_img_url.equals("") ? "http://statics." + HttpUrlProvider.SERVER_NAME : HttpUrlProvider.default_img_url;
    }

    public static String getH5Url() {
        if (DEBUG) {
            return   getTH5Url();
        }
        return "https://h5.zhuishushenqi.com";
    }


    public static String getTH5Url() {
        return "http://th5.zhuishushenqi.com";
    }


//    /**
//     * 获取书库的域名
//     *
//     * @return
//     */
//    public static String getBookLibServerRoot() {
//        if (BOOKLIB_ROOT == null) {
//            BOOKLIB_ROOT = DistributeBookLibRootUrlManager.getDistributeRootUrl();
//        }
//        return BOOKLIB_ROOT;
//    }
//
//
//    /**
//     * 书库发生切换域名，重新获取
//     * add fly
//     */
//    public static void updateBookLibRootCheckUrl() {
//        BOOKLIB_ROOT = DistributeBookLibRootUrlManager.getDistributeRootUrl();
//    }
//
//
//    /**
//     * 发生切换域名 切换 从新获取
//     * add fly
//     */
//    public static void updateRootCheckUrl() {
//        DEFAULT_ROOT = DistributeRootUrlManager.getDistributeRootUrl();
//    }

}
