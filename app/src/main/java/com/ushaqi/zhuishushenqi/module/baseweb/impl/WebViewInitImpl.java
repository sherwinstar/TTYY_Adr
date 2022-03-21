package com.ushaqi.zhuishushenqi.module.baseweb.impl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.IWebViewInit;
import com.ushaqi.zhuishushenqi.widget.NestedScrollWebView;

public class WebViewInitImpl implements IWebViewInit {

    private WebChromeClientImpl mWebChromeClient;
    private WebViewClientImpl mWebViewClient;
    private Activity mActivity;
    private Fragment mFragment;

    public WebViewInitImpl(Activity activity) {
        mActivity = activity;
    }

    public WebViewInitImpl(Activity activity, Fragment fragment) {
        this.mActivity = activity;
        this.mFragment = fragment;
    }
    @Override
    public NestedScrollWebView initWebView(NestedScrollWebView webView) {
        return (NestedScrollWebView) initWeb(webView);
    }


    public WebView initQKSWebView(WebView webView) {
        webView.setVerticalScrollBarEnabled(false);
        return initWeb(webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    private WebView initWeb(WebView webView) {
        //WebView开启硬件加速导致屏幕花屏问题的解决
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        //设置WebView的一些缩放功能点
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);

        //开启webView远程调试
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        WebSettings webSetting = webView.getSettings();
        // ===设置JS可用
        webSetting.setJavaScriptEnabled(true);

        // ===缩放可用
        webSetting.setLoadWithOverviewMode(true);

        //设置渲染级别
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        //缓存 // 决定是否从网络上取数据。
        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setAppCacheEnabled(true);

        //设置WebView是否使用viewport，当该属性被设置为false时，加载页面的宽度总是适应WebView控件宽度；当被设置为true，当前页面包含viewport属性标签，在标签中指定宽度值生效，如果页面不包含viewport标签，无法提供一个宽度值，这个时候该方法将被使用
        webSetting.setUseWideViewPort(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webSetting.setDomStorageEnabled(true);
        return webView;
    }

    @Override
    public WebViewClient initWebViewClient() {
        mWebViewClient = new WebViewClientImpl(mActivity);
        return mWebViewClient;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        mWebChromeClient = new WebChromeClientImpl(mActivity);
        return mWebChromeClient;
    }

    @Override
    public DownloadListener initDownLoader(ZssqWebData zssqWebData) {
        WebDownLoadImpl downLoader = new WebDownLoadImpl(mActivity);
        downLoader.fillWebData(zssqWebData);
        return downLoader;
    }

    @Override
    public WebViewClient initFragmentWebChromeClient(Fragment fragment) {
        mWebViewClient = new WebViewClientImpl(mActivity,fragment);
        return mWebViewClient;
    }

    /**
     * 页面标题、进度回调
     */
    public void setOnWebChromeListener(WebChromeClientImpl.OnWebChromeListener onWebChromeListener) {
        if (mWebChromeClient != null) {
            mWebChromeClient.setOnWebChromeListener(onWebChromeListener);
        }

    }

    /**
     * 选择相机相册处理
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mWebChromeClient != null) {
            mWebChromeClient.onActivityResult(requestCode, resultCode, data);
        }
    }

    public boolean isPageFinished(){
        if(mWebViewClient != null){
            return mWebViewClient.getPageFinishedState();
        }
        return false;
    }


}
