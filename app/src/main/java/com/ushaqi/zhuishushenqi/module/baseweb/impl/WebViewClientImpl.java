package com.ushaqi.zhuishushenqi.module.baseweb.impl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.command.WebJsBridgeCommandPool;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import java.net.URLDecoder;

/**
 * <p>
 *
 * @ClassName: WebViewClientImpl
 * @Date: 2019-05-30 14:43
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: webclient的实现类
 * </p>
 */
public class WebViewClientImpl extends WebViewClient {

    private Activity mActivity;

    private Fragment mFragment;

    private boolean mPageFinished;

    public WebViewClientImpl(Activity activity) {
        this.mActivity = activity;
    }

    public WebViewClientImpl(Activity activity, Fragment fragment) {
        this.mActivity = activity;
        this.mFragment = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        try {
            LogUtil.d("jack-log", "shouldOverrideUrlLoading:" + Thread.currentThread().getName() + "-" + Thread.currentThread().getId() + ":" + (url == null ? "" : url));
            if (webView != null && !TextUtils.isEmpty(url) && WebJsHelper.checkActivityAlive(mActivity)) {
                if (url.startsWith(WebConstans.JS_BRIDGE)) {
                    String decodeUrl = URLDecoder.decode(url);
                    if (!TextUtils.isEmpty(decodeUrl)) {
                        WebJsBridgeCommandPool.excuteWebJsCommand(decodeUrl, mActivity, mFragment, webView);
                    }
                }
                if (url.startsWith(WebConstans.WEI_XIN_BRIDGE) || url.startsWith(WebConstans.ALI_BRIDGE) || url.startsWith(WebConstans.QQ_BRIDGE) || url.startsWith(WebConstans.JD_BRIDGE)) {
                    if (url.startsWith(WebConstans.QQ_BRIDGE) && !AppHelper.isQQClientAvailable(MyApplication.getInstance())) {
                        ToastUtil.show(mActivity, "请安装QQ客户端");
                        return true;
                    }
                    if (url.startsWith(WebConstans.JD_BRIDGE) && !AppHelper.isJDClientAvailable(MyApplication.getInstance())) {
                        ToastUtil.show(mActivity, "请安装京东客户端");
                        return true;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    mActivity.startActivity(intent);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.shouldOverrideUrlLoading(webView, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mPageFinished = false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LogUtil.e("webview","onPageFinished");
        mPageFinished = true;
    }

    public boolean getPageFinishedState(){
        return mPageFinished;
    }
}
