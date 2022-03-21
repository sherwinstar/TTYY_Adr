package com.ushaqi.zhuishushenqi.httpcore;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonSyntaxException;
import com.ushaqi.zhuishushenqi.util.GsonHelper;
import com.ushaqi.zhuishushenqi.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;


/**
 * okhttp管理类
 * Created by fly on 2016/10/27 0027.
 */
public class RequestManager {

    /**
     * 单例对象
     * add fly
     */
    private static RequestManager mInstance;
    private Handler mHandler;

    private RequestManager() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * 获取单例引用
     * add fly
     *
     * @return
     */
    public static synchronized RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }

    /**
     * 新版的 使用builder 模式更加容易扩展性
     * add fly
     *
     * @param httpRequestBody
     */
    public void sendRequest(HttpRequestBody httpRequestBody) {
        handleRequest(httpRequestBody);
    }

    /**
     * http 请求分发
     * add fly
     *
     * @param requestMethod 请求类型
     * @param object        封装参数model
     */
    public void sendRequest(HttpRequestMethod requestMethod, String actionUrl, Object object, Class clazz, HttpCallBack callBack) {

        HttpRequestBody httpRequestBody = new HttpRequestBody.Builder()
                .requestMethod(requestMethod)
                .object(object)
                .actionUrl(actionUrl)
                .callBack(callBack)
                .clazz(clazz)
                .httpUiThread(HttpRequestBody.HttpUiThread.MAINTHREAD)
                .build();
        handleRequest(httpRequestBody);

    }

    /**
     * http 请求分发
     * add zc
     *
     * @param
     * @param
     */
    public void sendGETRequest(String actionUrl, Class clazz, HttpCallBack callBack) {
        HttpRequestBody httpRequestBody = new HttpRequestBody.Builder()
                .requestMethod(HttpRequestMethod.GET)
                .actionUrl(actionUrl)
                .callBack(callBack)
                .clazz(clazz)
                .httpUiThread(HttpRequestBody.HttpUiThread.MAINTHREAD)
                .build();
        handleRequest(httpRequestBody);

    }

    /**
     * http 请求分发
     * add zc
     *
     * @param
     * @param
     */
    public void sendGETRequest(String actionUrl, Object object, Class clazz, HttpCallBack callBack) {
        HttpRequestBody httpRequestBody = new HttpRequestBody.Builder()
                .requestMethod(HttpRequestMethod.GET)
                .actionUrl(actionUrl)
                .object(object)
                .callBack(callBack)
                .clazz(clazz)
                .httpUiThread(HttpRequestBody.HttpUiThread.MAINTHREAD)
                .build();
        handleRequest(httpRequestBody);

    }


    /**
     * 执行分发，可以多个分发方法
     * add fly
     *
     * @param httpRequestBody
     */
    private void handleRequest(HttpRequestBody httpRequestBody) {
        Worker wk = new Worker(httpRequestBody);
        ThreadPool.getExecutorServiceInstance().execute(wk);
    }

    /**
     * 单线程请求
     * add zhy
     */
    private void handleSingleThreadRequest(HttpRequestBody httpRequestBody) {
        Worker wk = new Worker(httpRequestBody);
        ThreadPool.getSingleExecutorServiceInstance().execute(wk);
    }

    /**
     * 执行线程任务
     * add fly
     */
    private class Worker implements Runnable {

        /**
         * 请求方式
         * add fly
         */
        private HttpRequestMethod requestMethod;
        /**
         * 请求参数
         * add fly
         */
        private Object object;

        /**
         * 请求地址
         * add fly
         */
        private String actionUrl;

        /**
         * 回调事件
         */
        private HttpCallBack callBack;
        /**
         * 返回model类型
         * add fly
         */
        private Class clz;
        /**
         * 请求头
         */
        private Map<String, String> mapHeaders;
        /**
         * 线程回调
         * add fly
         */
        private HttpRequestBody.HttpUiThread httpUiThread;

        /**
         * 统一回调
         */
        private HttpCallBack commonCallBack;

        /**
         * 请求 结果类型
         */
        private Type type;

        /**
         * 上传文件
         */
        private File file;

        public Worker(HttpRequestBody httpRequestBody) {
            this.requestMethod = httpRequestBody.getRequestMethod();
            this.object = httpRequestBody.getObject();
            this.callBack = httpRequestBody.getCallBack();
            this.actionUrl = httpRequestBody.getActionUrl();
            this.clz = httpRequestBody.getClz();
            this.mapHeaders = httpRequestBody.getMapHeaders();
            this.httpUiThread = httpRequestBody.getHttpUiThread();
            this.file = httpRequestBody.getFile();
            this.type = clz == null ? JavaClassHelper.getClassType(callBack.getClass()) : null;
        }

        @Override
        public void run() {
            CallRequestParam callRequestParam = new CallRequestParam.Builder()
                    .paramObject(object)
                    .actionUrl(actionUrl)
                    .requestMethod(requestMethod)
                    .headerMap(mapHeaders)
                    .file(file)
                    .build();

            Call call = OkHttpUtil.getOkHttpUtilInstace().getRequestCall(callRequestParam);
            if (call == null) {
                return;
            }
            // 服务器容灾 是否为需要过滤的urlstart
            // 服务器容灾 end
            long start = new Date().getTime();
            try {
                handleCallBackEvent();
                Response response = call.execute();
                MonitoringManagerHelper.getInstance().recordHttpEvent(MonitoringManagerHelper.getInstance().getRequestCallUrl(call, actionUrl), start, response.code());
                // 服务器容灾 根据Switch是否要停止容灾模式 start
                // 服务器容灾 end
                if (!response.isSuccessful()) {
                    MonitoringManagerHelper.getInstance().dealReportServerError(call, actionUrl, start, "Unexpected response code:" + response.code());
                    commonCallBack.onFailure(HttpExceptionHandle.handleResponeException(actionUrl, response.code() + "", (response.body() != null ? response.body().string() : "")));
                    return;
                }
                handleResponse(response);
            } catch (JsonSyntaxException ex) {
                ex.printStackTrace();
                MonitoringManagerHelper.getInstance().dealReportServerError(call, actionUrl, start, ex.getClass().getName());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commonCallBack.onFailure(HttpExceptionHandle.handleJSONException());
                    }
                });
            } catch (final Exception e) {
                e.printStackTrace();
                MonitoringManagerHelper.getInstance().dealReportServerError(call, actionUrl, start, e.getClass().getName());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        commonCallBack.onFailure(HttpExceptionHandle.handleRequestException(actionUrl, e));
                    }
                });

            }
        }


        private void handleCallBackEvent() {
            commonCallBack = new HttpCallBack() {

                @Override
                public void onFailure(com.ushaqi.zhuishushenqi.httpcore.HttpExceptionMessage obj) {
                    if (httpUiThread == HttpRequestBody.HttpUiThread.MAINTHREAD) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onFailure(obj);
                            }
                        });
                        return;
                    }
                    callBack.onFailure(obj);
                }

                @Override
                public void onSuccess(final Object success) {

                    if (httpUiThread == HttpRequestBody.HttpUiThread.MAINTHREAD) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(success);
                            }
                        });
                        return;
                    }

                    callBack.onSuccess(success);

                }

            };
        }

        /**
         * 处理返回数据Response
         * add fly
         *
         * @param response 返回信息
         */
        private void handleResponse(Response response) throws IOException {
            switch (requestMethod) {
                case GET:
                case DELETE:
                    handlerGetResponse(response);
                    break;
                case POST:
                    handlePostResponse(response);
                    break;
                case ADJSON:
                case JSON:
                case HEADJSON:
                    handlePostResponse(response);
                    break;
                case JSON_POST_ERROR:
                    handlePostResponse(response);
                    break;
                case PUT:
                    handlePostResponse(response);
                    break;
                case UPLOADIMG:
                    handleUploadImage(response);
                    break;
            }
        }

        /**
         * 处理get请求返回值
         * add fly
         *
         * @param response 返回信息
         */
        private void handlerGetResponse(Response response) throws IOException {
            String body = response.body().string();
            if (!StringUtils.isEmpty(body) && clz != null) {
                if (clz.isAssignableFrom(String.class)) {
                    commonCallBack.onSuccess(body);
                    return;
                }
                commonCallBack.onSuccess(GsonHelper.jsonStrToBean(body, clz));
                return;
            }

            if (!StringUtils.isEmpty(body) && type != null) {
                if (type == String.class) {
                    commonCallBack.onSuccess(body);
                    return;
                }
                commonCallBack.onSuccess(GsonHelper.jsonStrToT(body, type));
                return;
            }
            commonCallBack.onFailure(HttpExceptionHandle.handleResponeBodyException(StringUtils.isEmpty(body)));
        }

        /**
         * 处理Post返回数据Response
         * add fly
         */
        private void handlePostResponse(Response response) throws IOException {
            String body = response.body().string();
            if (!StringUtils.isEmpty(body) && clz != null) {//有的post的请求没有返回值或者没有class model
                commonCallBack.onSuccess(GsonHelper.jsonStrToBean(body, clz));
                return;
            }

            if (!StringUtils.isEmpty(body) && type != null) {
                if (type == String.class) {
                    commonCallBack.onSuccess(body);
                    return;
                }
                commonCallBack.onSuccess(GsonHelper.jsonStrToT(body, type));
                return;
            }
            commonCallBack.onSuccess(body);
        }


        public void handleUploadImage(Response response) throws IOException {

            String body = response.body().string();
            if (!StringUtils.isEmpty(body) && clz != null) {
                if (clz.isAssignableFrom(String.class)) {
                    commonCallBack.onSuccess(body);
                    return;
                }
                commonCallBack.onSuccess(GsonHelper.jsonStrToBean(body, clz));
                return;
            }

            if (!StringUtils.isEmpty(body) && type != null) {
                if (type == String.class) {
                    commonCallBack.onSuccess(body);
                    return;
                }
                commonCallBack.onSuccess(GsonHelper.jsonStrToT(body, type));
                return;
            }
            commonCallBack.onSuccess(body);
        }
    }

}
