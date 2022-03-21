package com.ushaqi.zhuishushenqi.module.baseweb;

public class WebConstans {

    /**
     * H5的intent参数
     */
    public static final String TITLE = "extra_title";
    public static final String FULLSCREEN = "full_screen";
    public static final String H5_FULL_SCREEN_TYPE_COMMON = "full_screen_common";
    public static final String H5_FULL_SCREEN_TYPE_SPECIAL = "full_screen_special";
    public static final String H5_FULL_SCREEN_TYPE_HEART_MODE = "full_screen_heart_mode";
    public static final String H5_FULL_SCREEN_TYPE_STATUS_BAR_TRANSPARENT = "full_screen_status_bar_transparent";
    /**
     * 全屏模式标志位flag
     */
    public static final int FLAG_PAGE_FULL_SCREEN_PLACEHOLDER = 0x20000;
    /**
     * 屏幕显示类型
     * 0*：默认
     *      01：默认不显示title，上滑显示title和icon，
     * 4*：全屏显示，中间显示标题，上滑中间标题变色，同transparentTitle，full_screen_common
     *      01：全屏不显示title，上滑中间显示title
     * 5*：全屏显示，中间显示标题，上滑标题栏变成黑色，同full_screen_special
     * 6*：全屏显示 同full_screen_heart_mode
     *
     */
    public static final int PAGE_STYLE_DEFAULT = 0x00;
    public static final int PAGE_STYLE_DASHEN_TUWEN = PAGE_STYLE_DEFAULT + 0x01;
    public static final int PAGE_STYLE_FULLSCREEN_COMMON = 0x40 | FLAG_PAGE_FULL_SCREEN_PLACEHOLDER;
    public static final int PAGE_STYLE_FULLSCREEN_COMMON_DASHEN_MASTER = PAGE_STYLE_FULLSCREEN_COMMON + 0x01;
    public static final int PAGE_STYLE_FULLSCREEN_COMMON_FLS = PAGE_STYLE_FULLSCREEN_COMMON + 0x02;
    public static final int PAGE_STYLE_FULLSCREEN_SPECIAL = 0x50 | FLAG_PAGE_FULL_SCREEN_PLACEHOLDER;
    public static final int PAGE_STYLE_FULLSCREEN_HEART_MODE= 0x60 | FLAG_PAGE_FULL_SCREEN_PLACEHOLDER;

    /**
     * 启动h5界面的请求码
     */
    public static final int START_H5_REQUEST_CODE = 0x63;
    /**
     * 跳转过来带有的url
     */
    public static final String URL = "extra_url";
    /**
     * 是否从闪屏界面跳转过来的
     */
    public static final String SPLASH = "from_splash";
    /**
     * 书籍id
     */
    public static final String BOOK_ID = "book_id";
    /**
     * 从短信短链跳转
     */

    public static final String FROM_INNER = "from_inner";
    /**
     * 从短信短链跳转
     */
    public static final String FROM_SEARCH = "fromSearch";
    /**
     * intentData
     */
    public static final String WEB_DATA = "zssq_web_data";

    /**
     * intentData
     */
    public static final String EXTRA_SENSORS_MAP = "extra_sensors_map";
    /**
     * 链接中有获取用户信息类型
     */
    public static final String USER_INFO = "jsbridge://getUserInfo?";

    /**
     * 获取淘宝的topAccessToken
     */
    public static final String GET_TAOBAO_ACCESSTOKEN = "jsbridge://getTaoBaoAccessToken?";

    /**
     * 获取状态栏的高度
     */
    public static final String GET_STATUSBAR_HEIGHT = "jsbridge://getStatusBarHeight?";

    /**
     * 链接中有获取用户信息类型
     */
    public static final String DEVICE_INFO = "jsbridge://getDeviceInfo?";
    /**
     * 链接中含有分享
     */
    public static final String SHARE = "jsbridge://share?";
    /**
     * 分享图片
     */
    public static final String SHARE_SPREAD = "jsbridge://sharespread?";
    /**
     * 消耗当前Activity
     */
    public static final String JUMP_POP_CURRENT = "jsbridge://pop?";
    /**
     * 操作书架
     */
    public static final String BOOK_SHELF = "jsbridge://handleBookShelf?";
    /**
     * 补签
     */
    public static final String NATIVE_PAGE_SIGN = "jsbridge://openPayForSignin?";
    /**
     * 刷新任务列表
     */
    public static final String REFRESH_TASK_COUNT = "jsbridge://refreshTaskCount?";
    /**
     * 激励视频
     */
    public static final String OPENVIDEOAD = "jsbridge://openVideoAd?";
    /**
     * 剪贴板操作
     */
    public static final String COPY_STR = "jsbridge://copyBoard?";
    /**
     * 同步联系人
     */
    public static final String SYNC_CONTACTS = "jsbridge://syncContacts?";
    /**
     * 支付
     */
    public static final String RE_CHARGE = "jsbridge://recharge?";
    /**
     * 支付成功
     */
    public static final String CODE_SUCESS = "jsbridge://activityCodeSuccess?";
    /**
     * 打开淘宝
     */
    public static final String OPEN_TAOBAO = "jsbridge://openTaobaoDetail?";

    /**
     * 打开饿了么链接
     */
    public static final String OPEN_ELEME = "jsbridge://openelemeDetail?";

    /**
     * 更新用户偏好信息
     */
    public static final String UPDATE_USER_PREFERENCE = "jsbridge://updateUserPreference?";
    /**
     * 跳转Js
     */
    public static final String JUMP = "jsbridge://jump?";
    /**
     * 事件回调参数
     */
    public static final String APPEVENTPARAM = "jsbridge://setUserBehavior?";
    /**
     * 设置topbar
     */
    public static final String SET_TOP_BAR = "jsbridge://setTopBarColor?";
    /**
     * 充值
     */
    public static final String NATIVE_PAGE_CHARGE = "jsbridge://baseRecharge?";
    /**
     * 打开书城
     */
    public static final String NATIVE_PAGE_BOOK_CITY = "jsbridge://openBookstore?";
    /**
     * 打开书架
     */
    public static final String NATIVE_PAGE_HOME_SHELF = "jsbridge://openBookshelf?";
    /**
     * 绑定手机
     */
    public static final String NATIVE_PAGE_BIND_PHONE = "jsbridge://openBindPhone?";
    /**
     * 关注追书服务号
     */
    public static final String NATIVE_PAGE_FOLLOW_ZHUISHU = "jsbridge://openFollowZhuishuWechat?";
    /**
     * 书评
     */
    public static final String NATIVE_PAGE_BOOK_REVIEW = "jsbridge://openBookReview?";
    /**
     * 支持追书
     */
    public static final String NATIVE_PAGE_SUPPORT_ZHUISHU = "jsbridge://openSupport?";
    /**
     * 积分墙
     */
    public static final String INTEGRA_WALL = "jsbridge://openIntegralWall?";

    /**
     * 大神圈视频页
     */
    public static final String OPEN_TOPIC_VIDEO = "jsbridge://openTopicVideo?";

    /**
     * 大神传递给客户端的内容详情
     */
    public static final String DASHEN_DETAIL = "jsbridge://daShenDetail?";

    /**
     * 大神圈帖子举报
     */
    public static final String DASHEN_TOPIC_REPORT = "jsbridge://report?";

    /**
     * 大神圈帖子评论
     */
    public static final String DASHEN_TOPIC_COMMENT = "jsbridge://addDashenTopicComment?";

    /**
     * webView创建事件，用于上传H5页面持续浏览事件
     */
    public static final String ON_WEBVIEW_LOADED = "jsbridge://onWebviewLoaded?";

    /**
     * 分享收入
     */
    public static final String SHARE_EARNING = "jsbridge://shareEarnings?";
    /**
     * 完成签到
     */
    public static final String SIGIN_SUCCESS = "jsbridge://completeSignIn?";
    /**
     * toast show
     */
    public static final String SHOW_TOAST = "jsbridge://showToast?";
    /**
     * 添加发音人
     */
    public static final String ADD_SPEAKER = "jsbridge://addVoicePackage?";
    /**
     * 获取本地发音人信息
     */
    public static final String GET_LOCAL_SPEAKERS = "jsbridge://getVoicePackageList?";
    /**
     * 购买发音人
     */
    public static final String PAY_SPEAKER = "jsbridge://buyVoicePackage?";
    /**
     * 更新当前用户状态
     */
    public static final String UPDATE_USER_STATE = "jsbridge://updateUserVoicePackageStatus?";
    /**
     * 移除发音人
     */
    public static final String REMOVE_SPEAKER = "jsbridge://removeVoicePackage?";
    /**
     * 获取本地基础资源状态
     */
    public static final String GET_COMMON_OFFLINE = "jsbridge://getBaseVoiceIsDownloaded?";
    /**
     * 下载离线基础资源
     */
    public static final String DOWNLAD_BASE_RESOURCE = "jsbridge://downloadBaseVoicePackage?";
    /**
     * 下载语音包
     */
    public static final String DOWNLAD_RESOURCE = "jsbridge://downloadVoicePackage?";
    /**
     * 设置顶部button样式
     */
    public static final String SET_OPTION_BUTTON = "jsbridge://setOptionButton?";
    /**
     * 版权书籍缓存
     */
    public static final String COPYRIGHT_BOOK_CACHE = "jsbridge://startBookCache?";
    /**
     * 上传版权书籍缓存进度
     */
    public static final String UPLOAD_COPYRIGHT_BOOK_CACHE_STATUS = "jsbridge://getBooksCacheStatus?";
    /**
     * 网络诊断
     */
    public static final String NET_WROK_DIAGOSIS_INFO = "jsbridge://getNetworkDiagnosisInfo?";

    /**
     * 移动mm入口
     */
    public static final String OPEN_YDMM = "jsbridge://openYdmmPay?";
    /**
     * 获取数美id
     */
    public static final String GET_SHUMEI_ID = "jsbridge://getShumeiInfo";

    /**
     * 打开签到对话框
     */
    public static final String OPEN_SIGNIN_DIALOG = "jsbridge://openSignInDialog";

    /**
     * 更新签到对话框
     */
    public static final String UPDATE_SIGNIN_DIALOG = "jsbridge://updateSignInDialog";

    /**
     * 打开金币球奖励对话框
     */
    public static final String OPEN_GOLDBALL_DIALOG = "jsbridge://openAdVideoDialog";

    /**
     * 更新金币球奖励对话框
     */
    public static final String UPDATE_GOLDBALL_DIALOG = "jsbridge://updateAdVideoDialog";

    /**
     * 更新签到对话框
     */
    public static final String UPDATE_BACK_CLICK = "jsbridge://onBackButtonClick";
    /**
     * 任务赚金币（愉悦-cpa)
     */
    public static final String OPEN_YUYUE_TASK = "jsbridge://yuyue-task";
    /**
     * 新闻赚金币（愉悦-看看赚）
     */
    public static final String OPEN_YUYUE_NEWS = "jsbridge://yuyue-news";
    /**
     * 资讯赚金币（皮皮玩-新闻）
     */
    public static final String OPEN_PIPIWAN_NEWS = "jsbridge://pipiwan-news";
    /**
     * 快手内容联盟
     */
    public static final String OPEN_KSCONTENT_TASK = "jsbridge://kscontent-task";
    /**
     * 变现猫内容联盟
     */
    public static final String OPEN_BXM_GAME = "jsbridge://bxm_content-task";
    /**
     * 神策埋点
     */
    public static final String SET_SENSOR_USERS_BEHAVIOR = "jsbridge://setSensorsUserBehavior";

    /**
     * 变现猫
     */
    public static final String OPEN_BXM_PAGE = "jsbridge://bianxianmao-task";

    /**
     * 打开新闻分享任务
     */
    public static final String OPEN_NEWS_SHARE_PAGE = "jsbridge://wenlv-news";

    /**
     * 关闭webview
     */
    public static final String HIDE_WEBVIEW = "jsbridge://hideWebView";

    /**
     * 批量保存图片
     */
    public static final String SAVE_IMAGES="jsbridge://saveImages";

    /**
     * callbackl类型js
     */
    public static final String CALL_BACK = "callback=";
    public static final String BACK_EVENT = "backEvent";

    /**
     * H5的version
     * 大神圈功能修改version为17
     */
    public static final String WEB_VERSION = "18";

    /**
     * H5的跳转类型
     */
    public static final String SAFARI = "safari";
    public static final String FISSION = "fission";
    /**
     * 跳转书籍详情
     */
    public static final String JUMP_BOOK_DETAIL = "bookDetail";
    /**
     * 漫画详情
     */
    public static final String JUMP_CARTOON_DETAIL = "cartoonDetail";

    /**
     * 跳转清库书详情
     */
    public static final String UNREACHABLE_BOOK_DETAIL = "cleanBookDetail";

    /**
     * 跳转听书
     */
    public static final String JUMP_LISTEN_BOOK = "listenBook";
    /**
     * 跳转个人信息
     */
    public static final String JUMP_PERSONINFO_TASK = "personalinfo";
    /**
     * 社区个人中心
     */
    public static final String JUMP_PERSONALCENTER_TASK = "personalCenter";
    /**
     * 原生充值
     */
    public static final String JUMP_BASE_CHARGE = "baseRecharge";
    /**
     * 跳转原生分类
     */
    public static final String JUMP_CATEGORY = "category";
    /**
     * 跳转帖子详情
     */
    public static final String POST = "post";
    /**
     * 跳转登录
     */
    public static final String JUMP_LOGIN = "login";

    /**
     * 跳转合伙人
     */
    public static final String JUMP_JOIN_PARTNER = "join_partner";
    /**
     * 跳转优选
     */
    public static final String JUMP_OPTIMIZATION = "optimization";

    /**
     * 跳转支付宝提现
     */
    public static final String JUMP_ALIPAY_WITHDRAW_SETTING = "alipay_withdraw_setting";

    /**
     * 账号安全
     */
    public static final String JUMP_ACCOUNT_SAFE = "account_safe";
    /**
     * 跳转个人信息
     */
    public static final String JUMP_PERSONAL_INFO = "personal_info";

    /**
     * 跳转支付宝提现
     */
    public static final String JUMP_SETTING = "setting";

    /**
     * 跳转绑定邀请码
     */
    public static final String JUMP_BIND_INVITE_CODE = "bind_invite_code";
    /**
     * 跳转第三方详情页
     */
    public static final String JUMP_PRODUCT_DETAIL = "product_detail";


    /**
     * 跳转游戏中心
     */
    public static final String JUMP_GAME_CENTER = "game_center";
    /**
     * 网页跳转
     */
    public static final String JUMP_WEB = "webview";
    /**
     * 原生跳转
     */
    public static final String JUMP_NATIVE = "native";


    public static final String JS_BRIDGE = "jsbridge://";

    public static final String WEI_XIN_BRIDGE = "weixin://";
    public static final String ALI_BRIDGE = "alipays://";
    public static final String QQ_BRIDGE = "mqq://";
    public static final String JD_BRIDGE = "openapp.jdmobile://";


    /**
     * 微信朋友圈分享
     */
    public static final String SHARE_TYPE_WECHAT_MOMENT = "wechatmoment";

    /**
     * 微信分享
     */
    public static final String SHARE_TYPE_WECHAT = "wechat";


    /**
     * 下载图片
     */
    public static final String DOWNLOAD_IMAGE = "download_image";


    /**
     * 一些JS回调参数
     */
    public static String sJsCallBackParam = "";
    public static String sJsCallBackMethod = "";
    public static String sJsNewCallBackParam = "";
    public static String sJsNewCallBackMethod = "";
    public static boolean sNeedLoginCallJs = false;
    public static boolean isShareHasSuccess = false;
    public static String sJSCurVisibileTab ="";

    /**
     * topbar的颜色
     */
    public static final String BACK_COLOR_WHITE = "white";

    public static final String FILE_SEPARATE = "/";
    public static final String MARK_CONTENT_TYPE_TEXT = "TEXT";
    public static final String MARK_CONTENT_TYPE_AVATAR = "IMAGE";
    public static final String IMAGE_SUFFIX_JPG = ".jpg";
    public static final String MARK_CONTENT_TYPE_QR = "QRCODE_TEXT";

    /**
     * callBack指令类型
     */
    public static final String JS_COMMAND_CALLBACK = "callBackJsCommand";
    /**
     * 跳转指令类型
     */
    public static final String JS_COMMAND_JUMP = "jumpJsCommand";
    /**
     * 操作发音人的js指令类型
     */
    public static final String JS_COMMAND_VOICE = "voiceJsCommand";
    /**
     * 一般指令类型
     */
    public static final String JS_COMMAND_GENERAL = "generalJsCommand";
    /**
     * 更新Ui的指令类型
     */
    public static final String JS_COMMAND_UPDATEUI = "updateUiJsCommand";

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 20:09
     * @Description 复位JS回调参数
     */
    public static void resetJsCallParam() {
        sJsCallBackParam = "";
        sJsCallBackMethod = "";
        sJsNewCallBackParam = "";
        sJsNewCallBackMethod = "";
        sNeedLoginCallJs = false;
        isShareHasSuccess = false;
        sJSCurVisibileTab ="";
    }

}
