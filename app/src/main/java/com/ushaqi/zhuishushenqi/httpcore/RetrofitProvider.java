package com.ushaqi.zhuishushenqi.httpcore;



import com.ushaqi.zhuishushenqi.httpcore.converter.ZSGsonConverterFactory;
import com.ushaqi.zhuishushenqi.thread.ThreadPoolFactory;
import com.ushaqi.zhuishushenqi.util.LogUtil;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by JackHu on 2019/5/10
 */
public class RetrofitProvider {
    private static RxJava2CallAdapterFactory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
    private static ZSGsonConverterFactory zsGsonConverterFactory = ZSGsonConverterFactory.create();
    private static HashMap<String, Retrofit> sRetrofitHashMap = new HashMap<>();
    private static HashMap<String, Retrofit> sRetrofitHashMapWithCallbackExecutor = new HashMap<>();

    /**
     * default retrofit
     *
     * @param url
     * @param responseInMainThread 是否在主线程接受结果
     * @return
     */
    public synchronized static Retrofit provideRetrofit(String url, boolean responseInMainThread) {

        if (!responseInMainThread) {
            if (sRetrofitHashMapWithCallbackExecutor.containsKey(url)) {
                return sRetrofitHashMapWithCallbackExecutor.get(url);
            }
            Retrofit retrofit = provideRetrofitBuilder()
                    .baseUrl(url)
                    .client(OkHttpClientProvider.provideClient())
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .addConverterFactory(zsGsonConverterFactory)
                    .callbackExecutor(ThreadPoolFactory.getThreadPool())
                    .build();
            sRetrofitHashMapWithCallbackExecutor.put(url, retrofit);
            LogUtil.d("RetrofitProvider", "createRetrofit:" + url);
            return retrofit;
        }
        if (sRetrofitHashMap.containsKey(url)) {
            return sRetrofitHashMap.get(url);
        }
        Retrofit retrofit = provideRetrofitBuilder()
                .baseUrl(url)
                .client(OkHttpClientProvider.provideClient())
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(zsGsonConverterFactory)
                .build();
        sRetrofitHashMap.put(url, retrofit);
        LogUtil.d("RetrofitProvider", "createRetrofit:" + url);
        return retrofit;
    }


    public static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    public static void release() {
        sRetrofitHashMap.clear();
        sRetrofitHashMap = null;
        sRetrofitHashMapWithCallbackExecutor.clear();
        sRetrofitHashMapWithCallbackExecutor = null;
    }
}
