package com.ushaqi.zhuishushenqi.httpcore;

/**
 * 网络配置,超时时间
 * Created by JackHu on 2019/5/10
 */
public interface HttpConfig {
    /**
     * http连接超时
     */
    long HTTP_CONNECT_TIMEOUT = 15;
    /**
     * 读取超时时间
     */
    long HTTP_READ_TIMEOUT = 15;
    /**
     * 写入超时时间
     */
    long HTTP_WRITE_TIMEOUT = 15;

    /**
     * header需要特殊处理接口
     */
    String HEADER_FILTER_URL_INTEGRAL = "app-promote-tasks";
    String[] HEADER_FILTER_URL_TOC_DUMMY = new String[]{
            "bookshelf.html5.qq.com",
            "novel.mse.sogou.com",
            "m.leidian.com",
            "api.easou.com"
    };

    /**
     * toc特殊UA
     */
    String ssUA = "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/6.0 MQQBrowser/4.3 Mobile/11D257 Safari/7534.48.3";
    String sgUA = "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; MI 3 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 SogouMSE,SogouMobileBrowser/3.6.2";
    String ldUA = "Mozilla/5.0 (iPad; CPU OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D257 Safari/9537.53";
    String esUA = "Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; MI 3 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.7.500 U3/0.8.0 Mobile Safari/534.30";

    /**
     * 网络请求默认错误码
     */
    String HTTP_NETWORK_ERROR = "10001";
    /**
     * 平台参数
     */
    String PLATFORM = "android";

}
