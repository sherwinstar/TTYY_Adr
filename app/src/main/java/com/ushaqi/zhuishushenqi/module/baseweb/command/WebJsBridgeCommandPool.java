package com.ushaqi.zhuishushenqi.module.baseweb.command;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.ali.auth.third.core.service.impl.CredentialManager;
import com.alibaba.alibclogin.AlibcLogin;
import com.alibaba.alibcprotocol.route.proxy.IAlibcLoginProxy;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.dialog.ShareDialog;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.CopyEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.ImagesBean;
import com.ushaqi.zhuishushenqi.model.baseweb.JumpEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.ShareImageEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.TaoBaoDetailEntry;
import com.ushaqi.zhuishushenqi.model.baseweb.ToastEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJumpHelper;
import com.ushaqi.zhuishushenqi.sensors.H5AnalysisManager;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.LogUtil;
import com.ushaqi.zhuishushenqi.util.StatusBarUtils;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

import java.net.URLDecoder;


/**
 * <p>
 *
 * @ClassName: WebJsBridgeCommandPool
 * @Date: 2019-05-30 20:08
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: webJs指令池，包含各种类型的JS指令(后期可扩展)，外部只需要调用执行指令的静态方法就行了
 * </p>
 */
public class WebJsBridgeCommandPool {

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-30 22:43
     * @Description 供外部调用的方法
     */
    public static void excuteWebJsCommand(String url, Activity activity, Fragment fragment, WebView webView) {
        if (TextUtils.isEmpty(url) || webView == null || !WebJsHelper.checkActivityAlive(activity)) {
            return;
        }
        for (WebJsCommand jsCommand : WebJsCommand.values()) {
            if (jsCommand.matchCommand(url)) {
                jsCommand.execute(activity, webView, url);
                jsCommand.executeReplenish(activity, fragment, webView, url);
                if (!jsCommand.keepExcuteJscommand(url)) {
                    break;
                }
            }
        }
    }

    /**
     * JS的指令池,新增jsbridge,需要在这里添加一个枚举类型
     */
    public enum WebJsCommand implements IWebJsCommand {

        /**
         * 获取Callback信息
         */
        excuteGetCallback {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                if (!TextUtils.isEmpty(decodeUrl) && decodeUrl.contains(WebConstans.BACK_EVENT)) {
                    WebConstans.sJsNewCallBackMethod = WebJsHelper.getCallBackMethod(decodeUrl);
                    WebConstans.sJsNewCallBackParam = WebJsHelper.getCallBacParam(decodeUrl);
                    return;
                }
                if (!TextUtils.isEmpty(decodeUrl) && decodeUrl.contains(WebConstans.CALL_BACK)) {
                    WebConstans.sJsCallBackMethod = WebJsHelper.getCallBackMethod(decodeUrl);
                    WebConstans.sJsCallBackParam = WebJsHelper.getCallBacParam(decodeUrl);
                }
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return WebJsHelper.isCallBackJs(decodeUrl);
            }
        },

        /**
         * 保留信息到黏贴版
         */
        copy {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                CopyEntity copyEntity = WebJsHelper.changeUrlToJavaBean(decodeUrl, CopyEntity.class);
                WebJsHelper.copy(activity, copyEntity, webView);
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.COPY_STR);
            }
        },

        /**
         * 获取设备信息
         */
        getDeciceInfo {
            @Override
            public void execute(Activity activity, WebView webView, String url) {
                String deviceInfo = WebJsHelper.jointDeviceInfoJson();
                if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod) && !TextUtils.isEmpty(deviceInfo)) {
                    webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + deviceInfo + ")");
                }
            }

            @Override
            public boolean matchCommand(String url) {
                return url.startsWith(WebConstans.DEVICE_INFO);
            }

        },

        /**
         * 获取用户信息指令
         */
        getUserInfo {
            @Override
            public void execute(Activity activity, WebView webView, String url) {
                if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod)) {
                    WebConstans.sNeedLoginCallJs = true;
                }
                if (UserHelper.getInstance().isLogin()) {
                    String userInfo = WebJsHelper.jointUserInfoJson();
                    if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod) && !TextUtils.isEmpty(userInfo)) {
                        webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + userInfo + ")");
                    }
                }else {
                    webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({})");
                }

            }

            @Override
            public boolean matchCommand(String url) {
                return url.startsWith(WebConstans.USER_INFO);
            }

        },
        sharespread {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                ShareImageEntity shareEntity = WebJsHelper.changeUrlToJavaBean(decodeUrl, ShareImageEntity.class);
                ShareDialog shareDialog = new ShareDialog(activity, shareEntity,webView);
                if (!WebJsHelper.checkActivityAlive(activity)) {
                    return;
                }
                shareDialog.show();
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.SHARE_SPREAD) || decodeUrl.startsWith(WebConstans.SHARE_EARNING);
            }
        },
        getStatusBarHeight{
            @Override
            public void execute(Activity activity, WebView webView, String url) {
                int height=StatusBarUtils.getStatusBarHeight(MyApplication.getInstance());
                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "("+height+")");
            }

            @Override
            public boolean matchCommand(String url) {
                return url.startsWith(WebConstans.GET_STATUSBAR_HEIGHT);
            }
        },
        getTaoBaoAccessToken  {
            @Override
            public void execute(Activity activity, WebView webView, String url) {
                if (UserHelper.getInstance().isLogin()) {
                    IAlibcLoginProxy alibcLogin = AlibcLogin.getInstance();
                    if(!alibcLogin.isLogin()){
                        if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod)) {
                        webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(\"\")");
                        }
                    }else {
                        if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod)) {
                            String accessToken=  CredentialManager.INSTANCE.getSession().topAccessToken;
                            LogUtil.e("zccc","获取token:"+accessToken);
                            if(TextUtils.isEmpty(accessToken)){
                                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(\"\")");
                            }else {
                                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "('" + accessToken + "')");
                            }
                        }
                    }
                }else {
                    webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(\"\")");
                }

            }

            @Override
            public boolean matchCommand(String url) {
                return url.startsWith(WebConstans.GET_TAOBAO_ACCESSTOKEN);
            }

        },

        /**
         * 同步通讯录
         */

        /**
         * pop掉当前activity
         */
        pop {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                activity.setResult(200);
                activity.finish();
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.JUMP_POP_CURRENT);
            }
        },
        /**
         * 打开淘宝
         */
        openTaobaoDetail {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                try {
                    if (!TextUtils.isEmpty(WebConstans.sJsCallBackParam)) {
                        String taoBaoUrl = URLDecoder.decode(WebConstans.sJsCallBackParam);
                        TaoBaoDetailEntry taoBaoDetailEntry = WebJsHelper.changeUrlToJavaBean(taoBaoUrl, TaoBaoDetailEntry.class);
                        if (taoBaoDetailEntry != null) {
                            String param = taoBaoDetailEntry.getUrl();
                            AppHelper.jumpProductDetail(activity, param);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.OPEN_TAOBAO);
            }
        },

        openEleMeDetail {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                try {
                    if (!TextUtils.isEmpty(WebConstans.sJsCallBackParam)) {
                        String taoBaoUrl = URLDecoder.decode(WebConstans.sJsCallBackParam);
                        TaoBaoDetailEntry taoBaoDetailEntry = WebJsHelper.changeUrlToJavaBean(taoBaoUrl, TaoBaoDetailEntry.class);
                        if (taoBaoDetailEntry != null) {
                            String param = taoBaoDetailEntry.getUrl();
                            AppHelper.jumpElemeProductDetail(activity, param);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.OPEN_ELEME);
            }
        },


        /**
         * 绑定手机
         */
        openBindPhone {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.NATIVE_PAGE_BIND_PHONE);
            }
        },





        /**
         * showToast
         */
        showToast {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                ToastEntity toastEntity = WebJsHelper.changeUrlToJavaBean(decodeUrl, ToastEntity.class);
                if (toastEntity != null && !TextUtils.isEmpty(toastEntity.getMsg())) {
                    ToastUtil.show(toastEntity.getMsg());
                }
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.SHOW_TOAST);
            }
        },

        /**
         * 开启跳转
         */
        jump {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {

            }

            @Override
            public void executeReplenish(Activity activity, Fragment fragment, WebView webView, String decodeUrl) {
                JumpEntity jumpEntity = WebJsHelper.changeUrlToJavaBean(decodeUrl, JumpEntity.class);
                WebJumpHelper.startJump(jumpEntity, activity, fragment);
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.JUMP);
            }
        },


        saveImages{
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                ImagesBean imagesBean= WebJsHelper.changeUrlToJavaBean(decodeUrl, ImagesBean.class);
                String []urls= imagesBean.getImgs().split(",");
                for (int i=0;i<urls.length;i++){
                    AppHelper.saveImage(activity,urls[i]);
                }
                ToastUtil.show("保存成功");
                if (!TextUtils.isEmpty(WebConstans.sJsCallBackMethod)) {
                    webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(\"\")");
                }
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.SAVE_IMAGES);
            }
        },

        hideWebView {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                    activity.onBackPressed();
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.HIDE_WEBVIEW);
            }
        },
        setSensorsUserBehavior {
            @Override
            public void execute(Activity activity, WebView webView, String decodeUrl) {
                H5AnalysisManager.addH5SensorsEvent(decodeUrl);
            }

            @Override
            public boolean matchCommand(String decodeUrl) {
                return decodeUrl.startsWith(WebConstans.SET_SENSOR_USERS_BEHAVIOR);
            }
        };

        /**
         * @param decodeUrl url
         * @return
         * @add by Jared.Bai
         * @add on 2019-05-30 22:23
         * @Description 当前js是否匹配当前指令
         */
        public abstract boolean matchCommand(String decodeUrl);

        /**
         * @param
         * @return
         * @add by Jared.Bai
         * @add on 2019-05-30 22:24
         * @Description 当前指令执行完之后是否需要继续执行下一条指令，默认不执行，除了带CallBack的指令和UpdateUi的指令
         */
        public boolean keepExcuteJscommand(String url) {
            return WebJsHelper.isCallBackJs(url) || WebJsHelper.isUpdateWebJs(url);
        }

        /**
         * 补充执行commend
         *
         * @param activity
         * @param fragment
         * @param webView
         * @param decodeUrl
         */
        public void executeReplenish(Activity activity, Fragment fragment, WebView webView, String decodeUrl) {
        }
    }

}
