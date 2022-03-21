package com.ushaqi.zhuishushenqi.httpcore;

import android.text.TextUtils;

import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;

import java.lang.reflect.ParameterizedType;

/**
 * Created by JackHu on 2018/8/2.
 * retrofit网络框架base类
 */
public abstract class BaseHttpHelper<K>{
    protected K mApi;
    protected K mApiSync;
    protected Class<K> mApiClass;
    private String mHost;

    public BaseHttpHelper() {
        registerHost(getRequestHost());
    }

    protected abstract String getRequestHost();

    public String getToken() {
        return UserHelper.getInstance().getToken();
    }

    public K registerHost(String Host) {
        if (TextUtils.isEmpty(Host)) {
            return mApi;
        }
        if (Host.equalsIgnoreCase(mHost) && null != mApi) {
            return mApi;
        }
        mHost = Host;
        mApi = createApi(Host);
        return getApi();
    }

    private void generalApiEntity() {
        if (mApiClass == null) {
            mApiClass = (Class<K>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }

    private K createApi(String HOST) {
        generalApiEntity();
        return RetrofitProvider.provideRetrofit(HOST, true).create(mApiClass);
    }




    /**
     * 只转换解析结果
     *
     * @param observable
     * @param <T>
     * @return
     */
//    protected <T> Flowable<T> transform(Flowable<MyHttpResponse<T>> observable) {
//        return observable.compose(RxUtil.<MyHttpResponse<T>>rxSchedulerHelper())
//                .compose(RxUtil.<T>handleResult());
//    }
//
//    /**
//     * 全量转换
//     *
//     * @param observable
//     * @param <T>
//     * @return
//     */
//    protected <T> Flowable<T> transformFull(Flowable<T> observable) {
//        return observable.compose(RxUtil.<T>rxSchedulerHelper());
//    }

    /**
     * 获取一个返回值在子线程的retrofit
     *
     * @return
     */
    public K getSyncApi() {
        if (mApiSync != null) {
            return mApiSync;
        }
        generalApiEntity();
        mApiSync = RetrofitProvider.provideRetrofit(mHost, false).create(mApiClass);
        return mApiSync;
    }

    /**
     * 获取一个返回值在主线程的retrofit
     *
     * @return
     */
    public K getApi() {
        if (mApi != null) {
            return mApi;
        }
        generalApiEntity();
        mApi = RetrofitProvider.provideRetrofit(mHost, true).create(mApiClass);
        return mApi;
    }
}
