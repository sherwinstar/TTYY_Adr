package com.ushaqi.zhuishushenqi.httpcore.callback;



import com.ushaqi.zhuishushenqi.HttpExceptionMessage;
import com.ushaqi.zhuishushenqi.httpcore.HttpConfig;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by JackHu on 2019/4/23
 */
public abstract class ClientCallBack<T> implements Callback<T> {


    public ClientCallBack() {
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() && null != response.body()) {
            onSuccess(response.body());
        } else {
            int code = response.code();
            StringBuilder errorBody = new StringBuilder(response.message());
            if (null == response.body()){
                code = 10009;
                errorBody.append(" response's body is null ");
            }
            if (null != response.errorBody()) {
                errorBody.append(" ");
                errorBody.append(response.errorBody().toString());
            }
            onFailed(new HttpExceptionMessage(String.valueOf(code), errorBody.toString()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailed(new HttpExceptionMessage(HttpConfig.HTTP_NETWORK_ERROR, t.getMessage()));
    }

    public abstract void onSuccess(T response);

    public abstract void onFailed(HttpExceptionMessage exceptionMessage);

}
