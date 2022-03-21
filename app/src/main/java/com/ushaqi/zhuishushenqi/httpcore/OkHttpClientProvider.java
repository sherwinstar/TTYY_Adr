package com.ushaqi.zhuishushenqi.httpcore;


import com.ushaqi.zhuishushenqi.httpcore.interceptor.HeaderInterceptor;
import com.ushaqi.zhuishushenqi.httpcore.interceptor.HttpDNSInterceptor;
import com.ushaqi.zhuishushenqi.httpcore.interceptor.HttpErrorRetryInterceptor;
import com.ushaqi.zhuishushenqi.plugin.BuildConfig;
import com.ushaqi.zhuishushenqi.util.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by JackHu on 2019/5/10
 */
public class OkHttpClientProvider implements HttpConfig {

    /**
     * default okhttp client
     *
     * @return
     */
    public static OkHttpClient.Builder provideOkBuilder() {
        return new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketClient.createSSLSocketFactory())
                .hostnameVerifier(new SSLSocketClient.TrustAllHostnameVerifier());
    }

    /**
     * @return
     */
    public static OkHttpClient provideClient() {
        return providClientWithTimeOut(provideOkBuilder(), HTTP_CONNECT_TIMEOUT, HTTP_READ_TIMEOUT, HTTP_WRITE_TIMEOUT);
    }

    public static OkHttpClient providClientWithTimeOut(OkHttpClient.Builder builder, long connectTimeout, long readTimeout, long writeTimeout) {
        LogUtil.d("HttpModule", "provideClient");
        //        setCache(builder);
        addInterceptor(builder);
        //设置超时
        builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
        builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    protected static void addInterceptor(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new HeaderInterceptor());
//        builder.addInterceptor(new HttpTolerantInterceptor());
        builder.addInterceptor(new HttpDNSInterceptor());
        builder.addInterceptor(new HttpErrorRetryInterceptor(1));
//        builder.addNetworkInterceptor(new CacheInterceptor());
    }



}
