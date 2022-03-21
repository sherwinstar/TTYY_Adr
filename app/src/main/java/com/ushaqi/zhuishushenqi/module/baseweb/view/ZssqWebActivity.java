package com.ushaqi.zhuishushenqi.module.baseweb.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.jxjuwen.ttyy.AbstractActivity;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.dialog.CommandGoodsDialog;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.helper.CommandGoodsHelper;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.TtyyApi;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.DisplayNoramlType;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.H5ControlImp;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebLoadUtil;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebStyleHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.impl.WebChromeClientImpl;
import com.ushaqi.zhuishushenqi.module.baseweb.impl.WebViewInitImpl;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.GsonHelper;
import com.ushaqi.zhuishushenqi.util.HandlerUtils;
import com.ushaqi.zhuishushenqi.widget.NestedScrollWebView;
import com.ushaqi.zhuishushenqi.widget.YJToolBar;


/**
 * <p>
 *
 * @ClassName: ZssqWebActivity
 * @Date: 2019-05-24 09:01
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: 所有H5页面
 * </p>
 */
public class ZssqWebActivity extends AbstractActivity implements WebChromeClientImpl.OnWebChromeListener {
    private static final String TAG = "ZssqWebActivity";
    /**
     * webView的容器
     */
    private RelativeLayout mRLWebContainer;

    private LinearLayout mLLWebEmptyContainer;
    /**
     * webView的初始化实现
     */
    private WebViewInitImpl mWebViewInitializer;
    /**
     * web
     */
    private NestedScrollWebView mWebView;
    /**
     * 带进度的web
     */
    private ProgressWebView mProgressWebView;
    /**
     * intentData
     */
    public ZssqWebData mZssqWebData;

    /**
     * H5ControlImp
     */
    public H5ControlImp mH5ControlImp;


    private boolean mResumed;


    private WebViewStatusBarHelper mWebViewStatusBarHelper;

    private RelativeLayout mRLPageContainer;

    private boolean isSearch;

    public static Intent createIntent(Context context, String title, String url) {

        ZssqWebData webData = new ZssqWebData();
        webData.setTitle(title);
        webData.setUrl(url);
        webData.setPageStyle(WebConstans.PAGE_STYLE_FULLSCREEN_COMMON);
        Intent intent = new Intent(context, ZssqWebActivity.class);
        intent.putExtra(WebConstans.WEB_DATA, webData);
        return intent;
    }

    /**
     * 沉浸式状态栏
     *
     * @param context
     * @param title
     * @param url
     * @return
     */
    public static Intent createImmerseIntent(Context context, String title, String url) {
        ZssqWebData webData = new ZssqWebData();
        webData.setTitle(title);
        webData.setUrl(url);
        webData.setFullScreenType(WebConstans.H5_FULL_SCREEN_TYPE_STATUS_BAR_TRANSPARENT);
        Intent intent = new Intent(context, ZssqWebActivity.class);
        intent.putExtra(WebConstans.WEB_DATA, webData);
        return intent;
    }

    public static Intent createNormalIntent(Context context, String title, String url) {
        ZssqWebData webData = new ZssqWebData();
        webData.setTitle(title);
        webData.setUrl(url);
        Intent intent = new Intent(context, ZssqWebActivity.class);
        intent.putExtra(WebConstans.WEB_DATA, webData);
        return intent;
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        if (WebStyleHelper.isImmerseFullScreenStyle(mZssqWebData)) {
            setTheme(R.style.TranslucentTheme);
            WebJsHelper.transparencyBar(this);
        }
        ;
        setContentView(R.layout.activity_zssq_web);
        //渲染
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        initView();
        checkPhonePermission();
        BusProvider.getInstance().register(this);

        String url = mZssqWebData.getUrl();
        if (!TextUtils.isEmpty(url) && url.contains("/ttyy/search")) {
            isSearch = true;
        }

    }


    /**
     * 检查电话权限
     */
    private void checkPhonePermission() {
        try {


//            if (!SysPermissionHelper.skipPhonePermission(this) &&
//                    SysPermissionHelper.shouldShowRequestPermissionRationale(this, SysPermissionHelper.PERMISSION_PHONE)) {
//                SysPermissionHelper.requestPermission(this, SysPermissionHelper.PHONE_REQUEST_CODE, SysPermissionHelper.PERMISSION_PHONE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (SysPermissionHelper.PHONE_REQUEST_CODE == requestCode && permissions != null &&
//                grantResults != null && permissions.length == grantResults.length && permissions.length > 0) {
//            for (int i = 0, length = permissions.length; i < length; ++i) {
//                final String permission = permissions[i];
//                SysPermissionHelper.saveFirstRequestPermission(permission, false);
//                if (SysPermissionHelper.PERMISSION_PHONE.equals(permission)) {
//                    handlePhonePermissionResult(grantResults[i]);
//                    break;
//                }
//            }
//        }
    }

    /**
     * @param result
     */
    private void handlePhonePermissionResult(final int result) {
//        if (SysPermissionHelper.isPermissionDenied(result)) {
//            finish();
//        }else {
//            if (!AppHelper.enableOAID(this)){
//                AppChannelManager.syncCpsInfoFromChannel(true,null);
//            }
//        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isSearch) {
            CommandGoodsHelper.getInstance().checkCommandGoods(this, "搜索");
        }

    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 11:04
     * @Description 初始化视图
     */
    private void initView() {
        try {
            mWebViewStatusBarHelper = new WebViewStatusBarHelper();
            mWebViewStatusBarHelper.showStatusBar(mZssqWebData, this);
            // WebView初始化对象
            mWebViewInitializer = new WebViewInitImpl(this);
            mRLPageContainer = findViewById(R.id.rl_page_ontainer);
            //将webView装入容器
            mRLWebContainer = findViewById(R.id.rl_web_container);
            mLLWebEmptyContainer= findViewById(R.id.ll_web_empty_view);


            // WebView初始化
            if (mWebView != null) {
                mWebView.removeAllViews();
            } else {
                mProgressWebView = new ProgressWebView(this);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                mProgressWebView.setLayoutParams(layoutParams);
                mWebView = mWebViewInitializer.initWebView(mProgressWebView.getWebView());
                mWebView.setWebViewClient(mWebViewInitializer.initWebViewClient());
                mWebView.setWebChromeClient(mWebViewInitializer.initWebChromeClient());
                mWebView.setDownloadListener(mWebViewInitializer.initDownLoader(mZssqWebData));
                mWebView.addJavascriptInterface(new TtyyApi(this, mWebView), "ttyyApi");
            }

            mWebViewInitializer.setOnWebChromeListener(this);

            if (mRLWebContainer.getChildCount() > 0) {
                mRLWebContainer.removeAllViews();
            }
            mRLWebContainer.addView(mProgressWebView);
            //初始化H5ControlImp
            if (!WebStyleHelper.isImmerseFullScreenStyle(mZssqWebData)) {
                initH5ControlImp();
            }
            if (mWebView != null && mZssqWebData != null) {
                WebLoadUtil.getInstance().loadWebPage(mWebView, mZssqWebData);
            }
            if (AppHelper.isConnected(MyApplication.getInstance())){
                mLLWebEmptyContainer.setVisibility(View.GONE);
                mRLWebContainer.setVisibility(View.VISIBLE);
            }else {
                mLLWebEmptyContainer.setVisibility(View.VISIBLE);
                mRLWebContainer.setVisibility(View.GONE);
            }


            findViewById(R.id.tv_web_refresh).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppHelper.isConnected(MyApplication.getInstance())){
                        if (mWebView != null && mZssqWebData != null) {
                            WebLoadUtil.getInstance().loadWebPage(mWebView, mZssqWebData);
                        }
                        HandlerUtils.postDelay(new Runnable() {
                            @Override
                            public void run() {
                                mLLWebEmptyContainer.setVisibility(View.GONE);
                                mRLWebContainer.setVisibility(View.VISIBLE);
                            }
                        },200);

                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        initWebTitleView();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mResumed = false;

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void initWebTitleView() {
        if (mZssqWebData == null || TextUtils.isEmpty(mZssqWebData.getTitle())) {
            mZssqWebData = (ZssqWebData) getIntent().getSerializableExtra(WebConstans.WEB_DATA);
            if (mZssqWebData == null) {
                mZssqWebData = new ZssqWebData();
            }
        }

    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-29 14:46
     * @Description 初始化H5ControlImp
     */
    private void initH5ControlImp() {
        YJToolBar toolBar = findViewById(R.id.web_tootbar);
        if (mZssqWebData != null) {
//            if (WebStyleHelper.isFullScreenStyle(mZssqWebData)) {
//                mH5ControlImp = new H5ControlImp(mRLPageContainer, new DisplayFullType(toolBar, mZssqWebData.getTitle()), new H5TitleImpListener(this, mWebView), mZssqWebData, mWebViewStatusBarHelper);
////                StatusBarCompat.setLightStatusBar(getWindow(), false);
////                StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.white));
//            } else {
//            }
            mH5ControlImp = new H5ControlImp(new DisplayNoramlType(this, toolBar, mZssqWebData.getTitle()), this, mZssqWebData);

            mH5ControlImp.toolBarInit();
            mH5ControlImp.titleInit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mResumed = true;


    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            if (mRLWebContainer != null && mWebView != null && mProgressWebView != null) {
                mRLWebContainer.removeAllViews();
                mWebView.destroy();
                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mProgressWebView = null;
                WebLoadUtil.getInstance().recycle();
                if (isSearch) {
                    CommandGoodsDialog commandGoodsDialog = CommandGoodsHelper.getInstance().getCommandGoodsDialog();
                    if (commandGoodsDialog != null && commandGoodsDialog.isShowing()) {
                        commandGoodsDialog.dismiss();
                    }
                    CommandGoodsHelper.getInstance().setDialogNull();
                }
            }

            BusProvider.getInstance().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }

        super.onBackPressed();
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 10:20
     * @Description 初始化intentData
     */
    private void initIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            mZssqWebData = (ZssqWebData) intent.getSerializableExtra(WebConstans.WEB_DATA);
            if (mZssqWebData == null) {
                mZssqWebData = new ZssqWebData();
            }


        }
    }


    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (mProgressWebView == null) {
            return;
        }
        ProgressBar progressbar = mProgressWebView.getProgressbar();
        //页面加载完成后不显示进度条(不同系统版本WebView的回调有区别)
        if (mWebViewInitializer.isPageFinished()) {
            progressbar.setVisibility(View.GONE);
            return;
        }
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

    @Override
    public void onCustomViewShow(View view) {
        try {
            if (mWebView != null && mRLWebContainer != null) {
                // 设置webView隐藏
                mWebView.setVisibility(View.GONE);
                // 将video放到当前视图中
                mRLWebContainer.addView(view);
                // 横屏显示
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCustomViewHidden() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理相机相册选择
        if (mWebViewInitializer != null) {
            mWebViewInitializer.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == WebConstans.START_H5_REQUEST_CODE) {
            if (WebConstans.isShareHasSuccess) {
                WebJsHelper.callShareSuccessJs(mWebView);
                WebJsHelper.callNewJsMethod("success", mWebView);
            } else {
                WebJsHelper.callJsMethod("success", mWebView);
                WebJsHelper.callNewJsMethod("success", mWebView);
            }
            WebConstans.isShareHasSuccess = false;
        }
    }


    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (UserHelper.getInstance().isLogin()) {
            String userInfo = GsonHelper.javaBeanToJson(event.getAccount().getUser());
            if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod) && !TextUtils.isEmpty(userInfo)) {
                mWebView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + userInfo + ")");
            } else {
                mWebView.loadUrl("javascript:updateUserInfo(" + userInfo + ")");
            }
        }
    }

    /**
     * 网络诊断回调
     *
     * @param
     */
//    @Subscribe
//    public void onNetworkDiagnosisInfoEvent(NetworkDiagnosisInfoEvent event) {
//        if (this.isFinishing() || this.isDestroyed() || mWebView == null || WebConstans.sJsCallBackMethod == null) {
//            return;
//        }
//        mWebView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + event.getNetworkJson() + ")");
//    }

//    @Subscribe
//    public void onCancelLoginEvent(SelfCancelLoginEvent event) {
//        if (WebConstans.sNeedLoginCallJs) {
//            if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod)) {
//                mWebView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({})");
//            }
//            WebConstans.sNeedLoginCallJs = false;
//        }
//    }


//
//    @Subscribe
//    public void onUserInfoChanged(UserGenderChangedEvent userGenderChangedEvent) {
//        try {
//            if (mWebView != null) {
//                mWebView.clearCache(true);
//                WebLoadUtil.getInstance().loadWebPage(mWebView, mZssqWebData);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    @Subscribe
//    public void onBindPhoneDoneEvent(BindPhoneSuccessEvent event) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
////                if (mWebView != null) {
////                    mWebView.reload();
////                }
////            }
////        });
//    }

//    @Subscribe
//    public void onWeixinFollowereDoneEvent(WeiXinFollowerSuccessEvent event) {
//        try {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (mWebView != null) {
//                        mWebView.reload();
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//
//    @Subscribe
//    public void onH5RefreshContentEvent(H5RefreshContentEvent event) {
//        if (event != null && mWebView != null
//                && !TextUtils.isEmpty(WebConstans.sJsNewCallBackMethod)) {
//            WebJsHelper.callNewJsMethod("success", mWebView);
//        }
//    }

//    @Subscribe
//    public void onWebPraiseEvent(WebPraiseEvent event) {
//        if (!WebJsHelper.checkActivityAlive(this) || mWebView == null || !isResumedState()) {
//            return;
//        }
//        if (event.isPraise()) {
//            mWebView.loadUrl("javascript:window.topicPraise(\"" + event.getTopicId() + "\")");
//        } else {
//            mWebView.loadUrl("javascript:window.topicUnPraise(\"" + event.getTopicId() + "\")");
//        }
//    }

//    @Subscribe
//    public void onWebShareEvent(WebShareEvent event) {
//        if (!WebJsHelper.checkActivityAlive(this) || mWebView == null || !isResumedState()) {
//            return;
//        }
//        mWebView.loadUrl("javascript:window.topicShare(\"" + event.getTopicId() + "\")");
//    }

//    @Subscribe
//    public void onWebSubscribeEvent(WebSubscribeEvent event) {
//        if (!WebJsHelper.checkActivityAlive(this) || mWebView == null || !isResumedState()) {
//            return;
//        }
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("{");
//        stringBuilder.append("\"id\":");
//        stringBuilder.append("\"");
//        stringBuilder.append(event.getCriticUserId());
//        stringBuilder.append("\"");
//        stringBuilder.append(",");
//        stringBuilder.append("\"subscribed\":");
//        //stringBuilder.append("\"");
//        stringBuilder.append(event.isSubscribe());
//        //stringBuilder.append("\"");
//        stringBuilder.append("}");
//        mWebView.loadUrl("javascript:window.updateDaShenSubscribeStatus" + "(" + stringBuilder.toString() + ")");
//        if (mH5ControlImp != null) {
//            mH5ControlImp.updateDaShenSubscribeState(event.getCriticUserId(), event.isSubscribe());
//        }
//    }


//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        if (mIsJumpNotificationSetting) {
//            boolean isOpenNotice = GeTuiPushHelper.turnOnGeTuiPush();
//            if (mIsCallbackFromNTSetting) {
//                mWebView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + isOpenNotice + ")");
//                mIsCallbackFromNTSetting = false;
//            }
//            mIsJumpNotificationSetting = false;
//
//        }
//    }

//    public void executeNativeTabPageShow(){
//        if(!isTaskCenterUrl()){
//            return;
//        }
//        if (mWebView == null) {
//            return;
//        }
//        String jsStr = "javascript:HybridApi._Event.emitTemp({\"event\":\"nativeTabPageShow\"})";
//        mWebView.loadUrl(jsStr);
//        LogUtil.d("jack", "WebActivity executeNativeTabPageShow:" + jsStr);
//    }
    public boolean isResumedState() {
        return mResumed;
    }


//
//    public void showTaskSigninDialog(final H5SignInBean h5SignInBean) {
//        if (h5SignInBean == null){
//            return;
//        }
//        switchCurrentTaskDialog();
//        taskSignInDialog = new TaskSignInDialog(GlobalConfig.getInstance().getContext().getApplicationContext());
//        taskSignInDialog.setFromPage(mFromPage);
//        taskSignInDialog.show(h5SignInBean.dialogInfo.source);
//        taskSignInDialog.updateSignInInfo(h5SignInBean.dialogInfo);
//        taskSignInDialog.setOnDismissListener(new AlertDialog.OnDismissListener() {
//
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                String jsStr = "javascript:" + h5SignInBean.dialogClose.callback + "({\"event\": \"" + h5SignInBean.dialogClose.param.event + "\"})";
//                mWebView.loadUrl(jsStr);
//            }
//        });
//
//        taskSignInDialog.setOnRewardListener(new TaskSignInDialog.OnRewardListener() {
//            @Override
//            public void onReward(int status, String verify) {
//                String statusStr = "success";
//                if (status == TaskSignInDialog.OnRewardListener.STATUS_FAIL) {
//                    statusStr = "fail";
//                } else if (status == TaskSignInDialog.OnRewardListener.STATUS_RESET) {
//                    statusStr = "reset";
//                }
//                String imei = AppHelper.getDeviceId(GlobalConfig.getInstance().getContext());
//                if (TextUtils.isEmpty(imei)) {
//                    imei = AppHelper.getAndroidId();
//                }
//                String jsStr = "javascript:" + h5SignInBean.videoSDK.callback + "({\"event\": \"" + h5SignInBean.videoSDK.param.event + "\"," +
//                        "\"data\":{\"result\":{\"status\": \"" + statusStr + "\",\"msg\":\"" + verify + "\", \"imei\": \"" +
//                        imei + "\"}}})";
//                mWebView.loadUrl(jsStr);
//            }
//        });
//
//        TaskAdManager.getInstance(this).setCurrentTaskDialog(taskSignInDialog);
//    }

//    public void updateTaskSigninDialog(final H5SignInBean h5SignInBean) {
//        if (taskSignInDialog == null || h5SignInBean == null){
//            return;
//        }
//        taskSignInDialog.updateSignInInfo(h5SignInBean.dialogInfo);
//    }
//
//    private void switchCurrentTaskDialog() {
//        TaskSignInDialog currentDialog = TaskAdManager.getInstance(this).getCurrentTaskDialog();
//        if (currentDialog != null && currentDialog.isShowing()) {
//            currentDialog.dismiss();
//            TaskAdManager.getInstance(this).setCurrentTaskDialog(null);
//        }
//    }


}
