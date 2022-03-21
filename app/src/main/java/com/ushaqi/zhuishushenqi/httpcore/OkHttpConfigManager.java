package com.ushaqi.zhuishushenqi.httpcore;

import android.annotation.SuppressLint;

import com.ushaqi.zhuishushenqi.util.SystemUtil;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * okhttp 配置参数和获取执行client
 * add fly
 * Created by fly on 2016/12/27 0027.
 */
public class OkHttpConfigManager {

    /**
     * 提交json
     * add fly
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    /**
     * okHttpClient 实例
     * add fly
     */
    private static OkHttpClient mOkHttpClient;

    /**
     * 获取okHttpClient 实例
     * add fly
     *
     * @return okHttpClient 实例
     */
    public static synchronized OkHttpClient getConfigManagerInstace() {
        if (mOkHttpClient == null) {
            initOkHttpSetting();
        }
        return mOkHttpClient;
    }

    /**
     * 初始化 okhttp 设置参数
     * add fly
     */
    private static void initOkHttpSetting() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
//        builder.interceptors().add(new HttpDNSInterceptor());//添加拦截器（使用场景DNS 拦截解析）
        if (!SystemUtil.isMeizuNotEnableHttps()) {
            builder.sslSocketFactory(createSSLSocketFactory(), new TrustAllManager())
                    .hostnameVerifier(new TrustAllHostnameVerifier());
        }
        mOkHttpClient = builder.build();
    }

    /**
     * 默认信任所有的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private static SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()},
                    new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return sSLSocketFactory;
    }

    private static class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            if (session == null) {
                return false;
            }
            return true;
        }

    }

}
