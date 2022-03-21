package com.ushaqi.zhuishushenqi.httpcore.interceptor;




import com.ushaqi.zhuishushenqi.MyApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by JackHu on 2019/1/10
 */
public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        return originResponse.newBuilder().removeHeader("pragma")
                .header("Cache-Control", "max-age=60").build();
    }

    private void setCache(OkHttpClient.Builder builder) {
        try {
            File cacheFile = new File(MyApplication.getInstance().getExternalCacheDir().getAbsolutePath(), "zssq_cache");
            int cacheSize = 64 * 1024 * 1024;
            final Cache cache = new Cache(cacheFile, cacheSize);
            builder.cache(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
