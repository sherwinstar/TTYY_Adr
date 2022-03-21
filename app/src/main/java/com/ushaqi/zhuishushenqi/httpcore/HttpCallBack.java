package com.ushaqi.zhuishushenqi.httpcore;

/**
 * okhttp回调函数接口
 * Created by fly on 2016/10/31 0031.
 */
public interface HttpCallBack<T> {

    /**
     * 请求失败回调
     * add fly
     */
    void onFailure(HttpExceptionMessage obj);

    /**
     * 请求成功时回调
     * add fly
     */
    void onSuccess(T obj);

}
