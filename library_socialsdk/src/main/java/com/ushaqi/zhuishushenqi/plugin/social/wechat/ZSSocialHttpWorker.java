package com.ushaqi.zhuishushenqi.plugin.social.wechat;

import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.plugin.social.HttpWorker;
import com.ushaqi.zhuishushenqi.plugin.social.wechat.login.SocialRequestCallback;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by JackHu on 2019/4/25
 */
public class ZSSocialHttpWorker implements HttpWorker {

    public static HttpWorker newInstance() {
        return new ZSSocialHttpWorker();
    }

    private String mUrl;
    private String mMethod = GET;
    private boolean mAllowRedirect = true;
    private boolean mAllowUserInteraction = false;

    @Override
    public void request(final SocialRequestCallback chatLoginRequestCallback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int resCode;
                InputStream in;
                String httpResult;
                try {
                    URL url = new URL(mUrl);
                    URLConnection urlConnection = url.openConnection();
                    HttpsURLConnection httpsConn = (HttpsURLConnection) urlConnection;
                    httpsConn.setAllowUserInteraction(mAllowUserInteraction);
                    httpsConn.setInstanceFollowRedirects(mAllowRedirect);
                    httpsConn.setRequestMethod(mMethod);
                    httpsConn.connect();
                    resCode = httpsConn.getResponseCode();

                    if (resCode == HttpURLConnection.HTTP_OK) {
                        in = httpsConn.getInputStream();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                in, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line).append("\n");
                        }
                        in.close();
                        httpResult = sb.toString();
                        chatLoginRequestCallback.onSuccess(httpResult);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    chatLoginRequestCallback.onFailed(e.getMessage());
                }
            }
        }).start();
    }


    @Override
    public void param(String url) {
        mUrl = url;
    }

    @Override
    public void allowRedirect(boolean allowRedirect) {
        mAllowRedirect = allowRedirect;
    }

    @Override
    public void allowUserInteraction(boolean allowUserInteraction) {
        this.mAllowUserInteraction = allowUserInteraction;
    }

    @Override
    public void setMethod(String method) {
        if (!TextUtils.isEmpty(method)) {
            mMethod = method;
        }
    }
}
