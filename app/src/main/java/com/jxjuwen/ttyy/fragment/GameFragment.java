package com.jxjuwen.ttyy.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;


import com.jxjuwen.ttyy.HomeActy;
import com.jxjuwen.ttyy.LoginActy;
import com.jxjuwen.ttyy.base.ClassicsFragment;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.event.PermissionEvent;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ProgressWebView;
import com.ushaqi.zhuishushenqi.permission.SysPermissionHelper;
import com.ushaqi.zhuishushenqi.sensors.SensorsAnalysisManager;
import com.ushaqi.zhuishushenqi.sensors.SensorsPageEventHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;


import java.net.URLEncoder;

import jfq.wowan.com.myapplication.PlayMeUtil;
import jfq.wowan.com.myapplication.X5JavaScriptInterface;

/**
   * 我玩游戏sdk
   *@author  zengcheng
   *create at 2021/5/19 上午10:25
*/
public  class GameFragment extends ClassicsFragment implements FragmentBackListener {
    private WebView webView;
    private  boolean isFirst=true;
    @Override
    protected int getLayoutId() {
        return R.layout.game_fragment;
    }

    public static   GameFragment newInstance(){
        return   new GameFragment();
    }

    @Override
    protected void initViewAndData(View view) {
      initWebview((ViewGroup)view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActy) mActivity).setBackListener(this);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        ((HomeActy) mActivity).setBackListener(null);
    }
    @Override
    public void onBackForward() {
        if (webView!=null && webView.canGoBack()) {
            webView.goBack();
        }else {
            ((HomeActy) mActivity).setInterception(false);
        }

    }
    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if(webView!=null){
        webView.loadUrl(rebuildUrl());
        }
    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible&&isFirst){
            isFirst=false;
            SensorsPageEventHelper.addZSPageShowEvent(null,"游戏");
        }
    }

    @Subscribe
    public void onPermissionEvent(PermissionEvent event) {
        SensorsAnalysisManager.registerImeiStaticParam(AppHelper.getIMEI(MyApplication.getInstance()));
        if(webView!=null){
            webView.loadUrl(rebuildUrl());
        }
    }


    private String rebuildUrl(){
        String cid = AppConstants.WOWAN_CID;//渠道id（我方提供）
        String cuid = UserHelper.getInstance().getUserId();//用户标识 ，用户的userid,渠道提供
        //cuid不能为空，没登录的时候随便填一个
        if(TextUtils.isEmpty(cuid)){
          cuid="1443910";
        }
        String deviceid =getDeviceid(mActivity);;//设备码
        String key = AppConstants.WOWAN_KEY;//key:秘钥（我方提供）
        String oaid = AppHelper.getOAID();//android10以上oaid必传  通过移动安全联盟sdk获取
        String appid = "";//一个渠道如果有多个应用接入，用appid和appname区分 ，渠道和我方共同约定
        String appname = "";//一个渠道如果有多个应用接入，用appid和appname区分 ，渠道和我方共同约定


        //开始拼接链接
        String md5Str = "t=2&cid=" + cid + "&cuid=" + cuid + "&deviceid=" + deviceid + "&unixt="
                + System.currentTimeMillis();
        String keycode = PlayMeUtil.encrypt(md5Str + key);

        String osversion = "";
        String phonemodel = "";
        try {
            osversion = Build.VERSION.RELEASE; // 操作系统版本号
            phonemodel = Build.MODEL; // 手机型号
        } catch (Exception e) {
            e.printStackTrace();
        }
        md5Str = md5Str + "&keycode=" + keycode + "&issdk=1&sdkver=1.0&oaid=" + oaid + "&osversion=" + osversion + "&phonemodel=" + phonemodel + "&listtype=1";

        String url = "https://m.playmy.cn/View/Wall_AdList.aspx?" + md5Str;
        if (!TextUtils.isEmpty(appid)) {
            url = url + "&appid=" + appid;
        }
        if (!TextUtils.isEmpty(appname)) {
            url = url + "&appname=" + URLEncoder.encode(appname);
        }
        return url;
    }


    private  void initWebview(ViewGroup view) {
        //创建web并加载
        ProgressWebView progressWebView = new ProgressWebView(mActivity);
        progressWebView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        view.addView(progressWebView);

        try {
            webView=progressWebView.getWebView();
            webView.setVerticalScrollBarEnabled(false);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setAllowFileAccess(true);

            // 允许混合模式（http与https）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            /*
             * NORMAL：正常显示，没有渲染变化。 SINGLE_COLUMN：把所有内容放到WebView组件等宽的一列中。
             * //这个是强制的，把网页都挤变形了 NARROW_COLUMNS：可能的话，使所有列的宽度不超过屏幕宽度。 //好像是默认的
             */
            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            webSettings.setDefaultTextEncodingName("UTF-8");
            // 提高渲染的优先级
            webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            webSettings.setTextZoom(100);

            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    if (progressWebView == null) {
                        return;
                    }
                    ProgressBar progressbar = progressWebView.getProgressbar();
                    if (progressbar == null) {
                        return;
                    }
                    if (newProgress == 100) {
                        progressbar.setVisibility(View.GONE);
                    } else {
                        if (progressbar.getVisibility() == View.GONE) {
                            progressbar.setVisibility(View.VISIBLE);
                        }
                        progressbar.setProgress(newProgress);
                    }
                }
            });

            webView.setWebViewClient(new WebViewClient() {


                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // TODO Auto-generated method stub
                    // super.onReceivedSslError(view, handler, error);
                    // 接受所有网站的证书，忽略SSL错误，执行访问网页
                    handler.proceed();
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (!TextUtils.isEmpty(url)&&url.contains("adid=")) {//进入详情页需要设备权限
                        LogUtil.e(TAG,"11:"+url);
                         if(canJumpGameDetail(mActivity)){
                             if(UserHelper.getInstance().isLogin()){
                                 PlayMeUtil.openAdDetail(mActivity, AppConstants.WOWAN_CID, url);
                             }else {
                                 mActivity.startActivity(LoginActy.createIntent(mActivity));
                             }
                         }
                        return true;
                    } else {
                        return true;
                    }

                }
            });

            webView.addJavascriptInterface(new X5JavaScriptInterface(mActivity, webView), "android");

            webView.loadUrl(rebuildUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * 是否能跳转游戏详情
     *
     * @param context
     * @return
     */
    public static boolean canJumpGameDetail(final Context context) {
        if (context instanceof Activity && !SysPermissionHelper.skipPhonePermission(context)) {//没有授予电话权限
            final Activity activity = (Activity) context;
            if (!SysPermissionHelper.shouldShowRequestPermissionRationale(activity, SysPermissionHelper.PERMISSION_PHONE)) {
                if (Build.VERSION.SDK_INT >= 29){//android 10以上 不用获取 拿不到
                    return true;
                }
                ToastUtil.show(activity, "进入游戏详情页需要授予设备信息权限");
                return false;
            }else {
                SysPermissionHelper.requestPermission(activity, SysPermissionHelper.PHONE_REQUEST_CODE, SysPermissionHelper.PERMISSION_PHONE);
                LogUtil.e(TAG,"22:没有权限");
                SysPermissionHelper.saveFirstRequestPermission( SysPermissionHelper.PERMISSION_PHONE, false);
                return false;
            }
        }

        return true;
    }





    //获取deviceid
    @SuppressLint("MissingPermission")
    public static String getDeviceid(Context context) {
        if (null == context) {
            return "";
        }
      String  deviceid = "";
        try {
            int permission = ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.READ_PHONE_STATE);
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != telephonyManager && permission == PackageManager.PERMISSION_GRANTED) {
                //有权限
                deviceid = telephonyManager.getDeviceId();
            }
            if(TextUtils.isEmpty(deviceid)){
                deviceid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG,"deviceid:"+e.toString());
            deviceid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        }
            LogUtil.e(TAG,"deviceid:"+deviceid);
        return deviceid;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
