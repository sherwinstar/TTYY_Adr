package com.ushaqi.zhuishushenqi.module.baseweb.impl;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

/**
 * <p>
 *
 * @ClassName: WebChromeClientImpl
 * @Date: 2019-05-30 14:53
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: </p>
 */
public class WebChromeClientImpl extends WebChromeClient {
    /**
     * WebView打开相机相册的请求码
     */
    public static final int FILE_REQUEST_CODE = 0x011;
    /**
     * 进度条的回调监听
     */
    private OnWebChromeListener onWebChromeListener;
    /**
     * 打开相册 本地文件等等
     */
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    /**
     * 播放Video的回调
     */
    private CustomViewCallback customViewCallback;

    private Activity mActivity;

    public WebChromeClientImpl(Activity activity) {
        mActivity = activity;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        try {
            customViewCallback = callback;
            onWebChromeListener.onCustomViewShow(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onShowCustomView(view, callback);
    }


    @Override
    public void onHideCustomView() {
        try {
            if (customViewCallback != null) {
                // 隐藏掉
                customViewCallback.onCustomViewHidden();
            }
            onWebChromeListener.onCustomViewHidden();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onHideCustomView();
    }

    /**
     * For Android < 3.0
     */
    @SuppressWarnings("static-access")
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        openImageChooserActivity(valueCallback, null);
    }

    // For Android  >= 3.0
    @SuppressWarnings("static-access")
    public void openFileChooser(ValueCallback valueCallback, String acceptType) {
        openImageChooserActivity(valueCallback, null);
    }

    //For Android  >= 4.1
    @SuppressWarnings("static-access")
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        openImageChooserActivity(valueCallback, null);
    }

    // For Android >= 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        openImageChooserActivity(null, filePathCallback);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == FILE_REQUEST_CODE) {
                if (null == mUploadMessage && null == mUploadCallbackAboveL) {
                    return;
                }
                Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
                if (mUploadCallbackAboveL != null) {
                    onActivityResultAboveL(requestCode, resultCode, data);
                } else if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(result);
                    mUploadMessage = null;
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if (null != mUploadMessage) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_REQUEST_CODE || mUploadCallbackAboveL == null) {
            return;
        }
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            }
        }
        mUploadCallbackAboveL.onReceiveValue(results);
        mUploadCallbackAboveL = null;
    }

    /**
     * 进度发生改变
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (onWebChromeListener != null) {
            onWebChromeListener.onProgressChanged(view, newProgress);
        }
    }

    /**
     * 回调方法触发本地选择文件
     */
    private void openImageChooserActivity(ValueCallback<Uri> uploadMsg, ValueCallback<Uri[]> filePathCallback) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        mUploadMessage = uploadMsg;
        mUploadCallbackAboveL = filePathCallback;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        mActivity.startActivityForResult(Intent.createChooser(intent, "请选择"), FILE_REQUEST_CODE);
    }

    /**
     * 接收到标题
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (onWebChromeListener != null) {
            onWebChromeListener.onReceivedTitle(view, title);
        }
    }


    /**
     * 页面标题、加载进度回调监听接口
     */
    public interface OnWebChromeListener {

        void onReceivedTitle(WebView view, String title);


        void onProgressChanged(WebView view, int newProgress);


        void onCustomViewShow(View view);

        void onCustomViewHidden();
    }

    public void setOnWebChromeListener(OnWebChromeListener onWebChromeListener) {
        this.onWebChromeListener = onWebChromeListener;
    }
}
