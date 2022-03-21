package com.ushaqi.zhuishushenqi;


import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.util.FileHelper;

/**
 * 常量类-请谨慎修改
 *
 * @author Shaojie
 * @Date 2013-9-27 下午2:27:04
 */
public class AppConstants {

    public static final String PROMOTOTER_ID = "200000107";//"200000072"; //"200000082;"20000010";//"200000069"; //"20000010";"100000150";200000083
    public static final String CHANNEL_NAME = "Official";//"Baidu"; //"Mk360";"Official";//"Samsung";//"Official";"Vivo";Oppo


    public static final String WOWAN_CID="7488";

    public static final String WOWAN_KEY="SmBaciZwnYyCKOHhlFCYf0LnnU5UlHOO";

    /**
     * 京东
     */

    public static final String JD_APPKEY="1123015431de0d77496791e033535f9d";

    public static final String JD_SECRETKEY="b27c4947cc374a17ba337f6b5cf83507";


    /**
     * 淘宝
     */
    public static final String TB_APPKEY="32814233";

    public static final String TB_SECRETKEY="796cd170860599f3d9c631620fc3b8f6";


    /**
     * 拼多多
     */
    public static final String PDD_CLIENT_ID="758f2e69910e429dbbfd8e66b03f2e71";

    public static final String PDD_CLIENT_SECRET="a5883f6bb2c87c8d54dc72282dcd90f4afa4c0e6";

    public static final String PDD_BACK_URL="http://www.jxjuwentech.com/";

    /**
     * 匿名设备标识符
     */
    public static String sOAID;

    public static final String APPLICATION_ID = BuildConfig.APPLICATION_ID;

    //腾讯bugly的id
    public static final String BUGLY_ID = "342c8cd612";

    public static final String USER_PROTOCOL = "https://h5.zhuishushenqi.com/agreement/user-agreement-tiantianyouyu.html";
    public static final String PRIVACY_PROTOCOL = "https://h5.zhuishushenqi.com/agreement/user-privacy-tiantianyouyu.html";
    public static final String GUIDELINES_PROTOCOL = "https://h5.zhuishushenqi.com/agreement/partner-tiantianyouyu.html";

    public static final String CUSTOMER_SERVICE = HttpUrlProvider.getH5Url()+"/ttyy/service";

    public static final String UN_REGISTER_USER_URL = "https://h5.zhuishushenqi.com/ttyy/accountcancel";
    public static final String PREF_FIRST_LAUNCH_TIME = "PREF_FIRST_LAUNCH_TIME";


    /**
     * 是否是冷启动
     */
    public static boolean sIsColdLaunch;

    /**
     * 是否隐藏首页游戏(为了应用市场过审)
     */

    public static  boolean sIsHideGame;
    public static String SD_ABS_PATH = FileHelper.getAbsImgPath();
    public static String SHARE_PATH = SD_ABS_PATH + "/share/";

}
