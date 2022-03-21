package com.ushaqi.zhuishushenqi.module.baseweb;

import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.widget.NestedScrollWebView;

public interface IWebViewInit {
    /**
     * 初始化和设置WebView
     */
    NestedScrollWebView initWebView(NestedScrollWebView webView);

    /**
     * 2. 初始化WebViewClient
     */
    WebViewClient initWebViewClient();

    /**
     * 3. 初始化WebChromeClient
     */
    WebChromeClient initWebChromeClient();

    /**
     * 4.初始化downLoader
     */
    DownloadListener initDownLoader(ZssqWebData webData);

    WebViewClient initFragmentWebChromeClient(Fragment fragment);
}
