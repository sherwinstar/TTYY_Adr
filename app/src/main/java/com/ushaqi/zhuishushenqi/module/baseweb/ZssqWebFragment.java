package com.ushaqi.zhuishushenqi.module.baseweb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jxjuwen.ttyy.HomeActy;
import com.jxjuwen.ttyy.base.ClassicsFragment;
import com.jxjuwen.ttyy.fragment.FragmentBackListener;
import com.squareup.otto.Subscribe;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.event.BindInviteCodeEvent;
import com.ushaqi.zhuishushenqi.event.BusProvider;
import com.ushaqi.zhuishushenqi.event.LoadFinishEvent;
import com.ushaqi.zhuishushenqi.event.LoginEvent;
import com.ushaqi.zhuishushenqi.event.LogoutEvent;
import com.ushaqi.zhuishushenqi.event.TabChangeEvent;
import com.ushaqi.zhuishushenqi.helper.KeyboardChangeListenerManager;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.DisplayFullType;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.DisplayNoramlType;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.H5ControlImp;
import com.ushaqi.zhuishushenqi.module.baseweb.h5promotion.H5TitleImpListener;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebLoadUtil;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebStyleHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.impl.WebChromeClientImpl;
import com.ushaqi.zhuishushenqi.module.baseweb.impl.WebViewInitImpl;
import com.ushaqi.zhuishushenqi.module.baseweb.view.ProgressWebView;
import com.ushaqi.zhuishushenqi.module.baseweb.view.WebViewStatusBarHelper;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.DensityUtil;
import com.ushaqi.zhuishushenqi.util.GsonHelper;
import com.ushaqi.zhuishushenqi.util.HandlerUtils;
import com.ushaqi.zhuishushenqi.util.LogUtil;
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
public class ZssqWebFragment extends ClassicsFragment implements WebChromeClientImpl.OnWebChromeListener , FragmentBackListener {
    private static final String TAG ="ZssqWebActivity" ;
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

    private  RelativeLayout mRLPageContainer;

    public static ZssqWebFragment newInstance(String title, String url) {
        ZssqWebData webData = new ZssqWebData();
        webData.setTitle(title);
        webData.setUrl(url);

        webData.setFullScreenType(WebConstans.H5_FULL_SCREEN_TYPE_COMMON);
        ZssqWebFragment fragment = new ZssqWebFragment();
        Bundle args = new Bundle();
        args.putSerializable(WebConstans.WEB_DATA,webData);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 11:04
     * @Description 初始化视图
     */
    private void initView(View rootView) {
        try {
            mWebViewStatusBarHelper = new WebViewStatusBarHelper();
            mWebViewStatusBarHelper.showStatusBar(mZssqWebData, mActivity);
            // WebView初始化对象
            mWebViewInitializer = new WebViewInitImpl(mActivity);
            mRLPageContainer = rootView.findViewById(R.id.rl_page_ontainer);
            //将webView装入容器
            mRLWebContainer = rootView.findViewById(R.id.rl_web_container);

            mLLWebEmptyContainer= rootView.findViewById(R.id.ll_web_empty_view);
            // WebView初始化
            if (mWebView != null) {
                mWebView.removeAllViews();
            } else {
                mProgressWebView = new ProgressWebView(mActivity);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.MATCH_PARENT);

                mProgressWebView.setLayoutParams(layoutParams);
                mProgressWebView.getProgressbar().setVisibility(View.GONE);
                mWebView = mWebViewInitializer.initWebView(mProgressWebView.getWebView());
                mWebView.setWebViewClient(mWebViewInitializer.initFragmentWebChromeClient(this));
                mWebView.setWebChromeClient(mWebViewInitializer.initWebChromeClient());
                mWebView.setDownloadListener(mWebViewInitializer.initDownLoader(mZssqWebData));
                mWebView.addJavascriptInterface(new TtyyApi(mActivity, mWebView), "ttyyApi");
            }

            mWebViewInitializer.setOnWebChromeListener(this);

            if (mRLWebContainer.getChildCount() > 0) {
                mRLWebContainer.removeAllViews();
            }
            mRLWebContainer.addView(mProgressWebView);
            //初始化H5ControlImp
            initH5ControlImp();
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
            rootView.findViewById(R.id.tv_web_refresh).setOnClickListener(new View.OnClickListener() {
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


            startKeyboardListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        initWebTitleView();
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mResumed = false;

    }


    private void initWebTitleView() {
        if (mZssqWebData == null || TextUtils.isEmpty(mZssqWebData.getTitle())) {
            mZssqWebData = (ZssqWebData) getArguments().getSerializable(WebConstans.WEB_DATA);
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
        YJToolBar toolBar = mRLPageContainer.findViewById(R.id.web_tootbar);
        if (mZssqWebData != null) {
            if (WebStyleHelper.isFullScreenStyle(mZssqWebData)) {
                mH5ControlImp = new H5ControlImp(mRLPageContainer, new DisplayFullType(toolBar, mZssqWebData.getTitle()), new H5TitleImpListener(mActivity, mWebView), mZssqWebData, mWebViewStatusBarHelper);

            } else {
                mH5ControlImp = new H5ControlImp(new DisplayNoramlType(mActivity, toolBar, mZssqWebData.getTitle()), mActivity, mZssqWebData);
            }

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
    public void onDestroy() {
        try {
            super.onDestroy();
            if (mRLWebContainer != null && mWebView != null && mProgressWebView != null) {
                mRLWebContainer.removeAllViews();
                mWebView.destroy();
                mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mProgressWebView = null;
                WebLoadUtil.getInstance().recycle();
                stopKeyboardListener();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_zssq_web;
    }

    @Override
    protected void initViewAndData(View view) {
        initIntentData();
        //渲染
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        initView(view);

    }



    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 10:20
     * @Description 初始化intentData
     */
    private void initIntentData() {
        Bundle intent = getArguments();
        if (intent != null) {
            mZssqWebData = (ZssqWebData) intent.getSerializable(WebConstans.WEB_DATA);
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
            BusProvider.getInstance().post(new LoadFinishEvent());
            return;
        }
//        if (progressbar == null) {
//            return;
//        }
        if (newProgress == 100) {
            BusProvider.getInstance().post(new LoadFinishEvent());
            if(progressbar.getVisibility()==View.VISIBLE){
                progressbar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onCustomViewShow(View view) {

    }

    @Override
    public void onCustomViewHidden() {

    }


    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        LogUtil.e(TAG,"title："+mZssqWebData.getTitle());
        LogUtil.e(TAG,"curVisib000ileTab："+WebConstans.sJSCurVisibileTab);
        if (UserHelper.getInstance().isLogin()) {
            String userInfo = GsonHelper.javaBeanToJson(event.getAccount().getUser());
            if (TextUtils.equals(WebConstans.sJSCurVisibileTab,mZssqWebData.getTitle())) {
                if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod) && !TextUtils.isEmpty(userInfo)) {
                    mWebView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + userInfo + ")");
                }
                mWebView.loadUrl("javascript:updateUserInfo(" + userInfo + ")");
//                WebConstans.sNeedLoginCallJs = false;
            }else  {
                mWebView.loadUrl("javascript:updateUserInfo(" + userInfo + ")");
            }
        }
    }
    @Subscribe
    public void onBindInviteCode(BindInviteCodeEvent event){
        if(TextUtils.equals(mZssqWebData.getTitle(),"我的")||TextUtils.equals(mZssqWebData.getTitle(),"合伙人")){
            String userInfo = GsonHelper.javaBeanToJson(UserHelper.getInstance().getUser());
            mWebView.loadUrl("javascript:updateUserInfo(" + userInfo + ")");
        }

    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent event) {
        if(mWebView!=null){
        mWebView.loadUrl("javascript:updateUserInfo({})");
        }
    }



    public boolean isResumedState() {
        return mResumed;
    }



//

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
        if (mWebView!=null && mWebView.canGoBack()) {
            mWebView.goBack();
        }else {
            ((HomeActy) mActivity).setInterception(false);
        }

    }

    @Override
    protected void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if(visible){
            WebConstans.sJSCurVisibileTab =mZssqWebData.getTitle();
        }

    }

    @Subscribe
    public void onTabChange(TabChangeEvent event){
        LogUtil.e(TAG,"TabChangeEvent");
        if(mWebView!=null&&TextUtils.equals( WebConstans.sJSCurVisibileTab,mZssqWebData.getTitle())){
            LogUtil.e(TAG,mZssqWebData.getTitle()+",可见");
        mWebView.loadUrl("javascript:pageActive()");
        }

    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WebConstans.START_H5_REQUEST_CODE && mWebView != null) {
            onResultCallback();
            //h5执行pop指令后，setResult为200，重新load webview
//            if (resultCode == 200) {
//                mWebView.reload();
//            }
        }
    }



    /**
     * onActivityResult返回执行callback
     */
    public void onResultCallback(){

        if (WebConstans.isShareHasSuccess) {
            WebJsHelper.callShareSuccessJs(mWebView);
            WebJsHelper.callNewJsMethod("success",mWebView);
        } else {
            WebJsHelper.callJsMethod("success",mWebView);
            WebJsHelper.callNewJsMethod("success",mWebView);
        }
        WebConstans.isShareHasSuccess = false;
    }


    /**
     * 小键盘监听管理类
     */
    private KeyboardChangeListenerManager mKeyboardChangeListenerManager;

    public void startKeyboardListener() {
        if (mKeyboardChangeListenerManager != null) {
            return;
        }
        mKeyboardChangeListenerManager = new KeyboardChangeListenerManager(mActivity.getWindow().getDecorView());
        mKeyboardChangeListenerManager.setKeyboardChangetListener((isActive, keyboardHeight) -> {
            LogUtil.e("webview","keyboardHeightInPx2:"+keyboardHeight+",isActive:"+isActive);
            if (isActive) {
                if(mWebView!=null){
                    keyboardHeight= DensityUtil.px2dp(keyboardHeight)-60;
                    mWebView.loadUrl("javascript:onKeyborad("+keyboardHeight+")");
                }
            }else {
                if(mWebView!=null){
                    mWebView.loadUrl("javascript:onKeyborad(0)");
                }

            }
        });
        mKeyboardChangeListenerManager.startKeyBoardListener();
    }
    public void stopKeyboardListener() {
        if (mKeyboardChangeListenerManager != null) {
            mKeyboardChangeListenerManager.stopKeyBoardListener();
        }
    }

    public NestedScrollWebView getCurrentWebview(){
        if (mWebView!=null){
            return mWebView;
        }
        return null;
    }
}
