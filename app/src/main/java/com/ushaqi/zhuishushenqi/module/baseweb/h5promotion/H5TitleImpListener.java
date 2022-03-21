package com.ushaqi.zhuishushenqi.module.baseweb.h5promotion;

import android.app.Activity;
import android.content.Intent;
import android.webkit.WebView;

import com.ushaqi.zhuishushenqi.model.baseweb.ShareEntrty;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ZssqWebActivity;

public class H5TitleImpListener implements H5ControlImp.H5titleListener {

    private Activity mActivity;
    private WebView mWebView;

    public H5TitleImpListener(Activity mActivity, WebView mWebView) {
        this.mActivity = mActivity;
        this.mWebView = mWebView;
    }

    @Override
    public void H5TitleClickCallBack(String url, String title, int type) {
        try {
            switch (type) {
                //分享
                case H5ControlImp.SHARE:
                    ShareEntrty shareEntrty = WebJsHelper.changeUrlToJavaBean(url,ShareEntrty.class);
//                    WebShareHelper.share(shareEntrty, mActivity);
                    break;
                //刷新
                case H5ControlImp.REFRESH:
                    mWebView.reload();
                    break;
                //短链
                case H5ControlImp.HELP:
                    Intent linkIntent = ZssqWebActivity.createNormalIntent(mActivity, title, url);
                    mActivity.startActivity(linkIntent);
                    break;
                //返回
                case H5ControlImp.BACK:
                    mActivity.onBackPressed();
                    break;

            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


}
