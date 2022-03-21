package com.ushaqi.zhuishushenqi.sensors;

/**
 * 神策常量
 *
 * @author Andy.zhang
 * @date 2020/5/20
 */
public class SensorsAnalysisConstants {
    /**
     * TODO 免费版没有这个schemaLimited参数
     * 神策数据接收地址
     */
    public static final String SD_SERVER_URL = "https://endpoint.zhuishushenqi.com/sa?project=production&token=schemaLimited-lQZemk8O";

    //https://endpoint.zhuishushenqi.com/sa?project=default&token=schemaLimited-XFBgC8Pb  测试项目地址

    //产品线
    public static final String PRODUCT_LINE = "26";
    /**
     * 平台类型
     * ios（默认手机）=1
     * 安卓（默认手机）=2
     * M站（Html5）=3
     * wap手机端WAP=4
     * pc客户端安装程序=5
     * tv=6
     * Web网站=7
     * mac（客户端安装）=8
     * windows phone：9
     * admin-back(后台充值)=10
     * 14：快应用
     * 15：头条小程序IOS
     * 16：头条小程序Android
     */
    public static final String PLATFORM = "2";

    /** 公共属性 **/
    /**
     * 属性：平台类型
     **/
    public static final String PROP_PLATFORM = "platform";
    /**
     * 属性： 产品线
     **/
    public static final String PROP_PRODUCT_LINE = "product_line";
    /**
     * 属性：apk包渠道
     **/
    public static final String PROP_APK_CHANNEL = "apk_channel";
    /**
     * 属性：设备标识
     **/
    public static final String PROP_DEVICE_IMEI = "device_imei";
    /**
     * 属性：imei
     **/
    public static final String PROP_IMEI = "imei";
    /**
     * 属性：androidid
     **/
    public static final String PROP_ANDROIDID = "androidid";
    /**
     * 属性：oaid
     **/
    public static final String PROP_OAID = "oaid";

    /**
     * 属性：登录ID
     **/
    public static final String PROP_ZS_LOGIN_ID = "zs_login_id";
    /**
     * 属性：商户
     */
    public static final String PROP_BUSINESS_NAME = "business_name";
    /**
     * 属性：渠道名称
     * 传“实时渠道”，BI接口返回
     */
    public static final String PROP_CHANNEL_NAME = "channel_name";
    /**
     * 属性：渠道ID
     */
    public static final String PROP_CHANNEL_ID = "channel_id";
    /**
     * 属性：子渠道名称
     */
    public static final String PROP_CHILD_CHANNEL_NAME = "child_channel_name";
    /**
     * 属性：子渠道ID
     */
    public static final String PROP_CHILD_CHANNEL_ID = "child_channel_id";
    /**
     * 属性：子渠道ID
     */
    public static final String PROP_AD_CLICK_TIME = "ad_click_time";
    /**
     * 属性：下载渠道
     */
    public static final String PROP_DOWNLOAD_CHANNEL = "DownloadChannel";
    /**
     * 属性：app安装时间
     * <p>
     * 客户端的安装时间；卸载重装后会变；这个不是新用户注册时间。
     */
    public static final String PROP_PUB_APP_FIRST_INSTALL_TIME = "pub_app_first_install_time";
    /**
     * 属性：灰度标识
     * 用于区分各个不同的灰度版本
     */
    public static final String PROP_GRAYTEST_MARK = "graytest_mark";

    /**
     * 事件：App激活
     */
    public static final String EVENT_APP_INSTALL = "AppInstall";
    /**
     * 设备入库时间
     */
    public static final String ACTIVE_TIME = "active_time";
    /**
     * uA+ip获取的渠道id
     */
    public static final String UA_CHANNEL_ID = "ua_channel_id";
    /**
     * ua+ip获取的渠道名
     */
    public static final String UA_CHANNEL_NAME = "ua_channel_name";




    /**
     * ua+ip广告点击时间
     */
    public static final String APP_VERSION = "$app_version";


}
