package com.ushaqi.zhuishushenqi.httpcore.interceptor;




import java.io.IOException;
import java.net.URL;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTP DNS 拦截器 （主要针对okhttp 框架设计）
 * Created by fly on 2017/1/9 0009.
 */
public class HttpDNSInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originRequest = chain.request();

        HttpUrl httpUrl = originRequest.url();
//        if (UmConfig.getSwitchStatus(MyApplication.getInstance(), UmConfig.ALI_HTTP_DNS_SWITCH)) {
//            URL url = AliHttpDnsConfig.getHttpdnsUrl(httpUrl.url());
//            if (url != null) {
//                Request.Builder builder = originRequest.newBuilder();
//                builder.url(url.toString());
//                builder.header("Host", httpUrl.host());
//                Request newRequest = builder.build();
//                return chain.proceed(newRequest);
//            }
//        }
        return chain.proceed(originRequest);
    }
}
