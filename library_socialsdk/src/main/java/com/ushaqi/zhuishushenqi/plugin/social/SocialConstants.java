package com.ushaqi.zhuishushenqi.plugin.social;

/**
 * @author Andy.zhang
 * @date 2019/4/24
 */
public class SocialConstants {
    public static final String PLATFORM_QQ = "QQ";
    public static final String PLATFORM_WEIXIN = "WeixinNew";
    public static final String PLATFORM_WEIBO = "SinaWeibo";
    public static final String QQ_APPID = "1107924107";
    public static final String WEIXIN_APP_ID = "wxab50df634bbd4104";
    public static final String WEIXIN_APP_SECRET = "25262119030bad732c2347b43cc35f27";

    /**
     * 新浪微博APPKEY
     */
    public static final String WEIBO_APP_KEY = "2023668704";
    /**
     * 新浪微博秘钥
     */
    public static final String WEIBO_APP_SECRET = "26efa7a6a6bed540092c9535bda75db9";
    /**
     * 应⽤的授权回调页⾯
     *
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     */
    public static final String WEIBO_REDIRECT_URL = "http://ushaqi.com";

    /**
     * 查看：http://open.weibo.com/wiki/Scope
     */
    public static final String WEIBO_SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";

    public static final String UID = "uid";
    public static final String TOKEN = "token";

}
