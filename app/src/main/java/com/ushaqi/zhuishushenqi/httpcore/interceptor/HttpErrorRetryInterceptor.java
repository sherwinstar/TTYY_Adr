package com.ushaqi.zhuishushenqi.httpcore.interceptor;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ushaqi.zhuishushenqi.util.OSUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by JackHu at 20/2/13
 */
public class HttpErrorRetryInterceptor implements Interceptor {
    public static final String HEADER_RETRY_BUY_KEY = "header_retry_buy";
    public static final String HEADER_RETRY_BUY_PURCHASE_BOOK_PRICE = "header_retry_buy:3";
    public static final String HEADER_RETRY_BUY_BATCH_CONFIG = "header_retry_buy:1";
    public static final String HEADER_RETRY_BUY_BATCH_PAY_PRICE = "header_retry_buy:2";

    public int maxRetry = 1;//最大重试次数
    private final Gson gson = new Gson();

    public HttpErrorRetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Headers headers = request.headers();
        String value = headers.get(HEADER_RETRY_BUY_KEY);
        if (TextUtils.isEmpty(value)) {
            return chain.proceed(request);
        }

        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.removeHeader(HEADER_RETRY_BUY_KEY);
        Request newRequest = requestBuilder.build();

        Response response = chain.proceed(newRequest);
        if (response == null || !OSUtil.belowKitKatWatch()) {
            return response;
        }
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return response;
        }
        BufferedSource bufferedSource = responseBody.source();
        if (bufferedSource == null || bufferedSource.buffer() == null) {
            return response;
        }
        bufferedSource.request(Long.MAX_VALUE);
        Buffer buffer = bufferedSource.buffer().clone();
        String bs = buffer.readString(Charset.defaultCharset());
//        boolean isDataEncryptError = isEncryptError(bs, value);

        int retryCount = 0;
//        while (isDataEncryptError && retryCount < maxRetry) {
//            retryCount++;
//            response = chain.proceed(newRequest.newBuilder()
//                    .removeHeader("third-token")
//                    .header("third-token", ZsApi.getZsThirdToken())
//                    .build());
//        }
        return response;
    }

//    private boolean isEncryptError(String bs, String value) {
//        try {
//            if ("1".equalsIgnoreCase(value)) {
//                BatchResponse batchResponse = gson.fromJson(bs, BatchResponse.class);
//                return batchResponse != null && !batchResponse.isOk() && batchResponse.isEncryptError();
//            } else if ("2".equalsIgnoreCase(value)) {
//                BatchPayPriceResponse batchPayPriceResponse = gson.fromJson(bs, BatchPayPriceResponse.class);
//                return batchPayPriceResponse != null && !batchPayPriceResponse.isOk() && batchPayPriceResponse.isEncryptError();
//            } else if ("3".equalsIgnoreCase(value)) {
//                PurchaseBookPriceInfo purchaseBookPriceInfo = gson.fromJson(bs, PurchaseBookPriceInfo.class);
//                return purchaseBookPriceInfo != null && !purchaseBookPriceInfo.isOk() && purchaseBookPriceInfo.isEncryptError();
//            }
//        } catch (JsonSyntaxException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }

}
