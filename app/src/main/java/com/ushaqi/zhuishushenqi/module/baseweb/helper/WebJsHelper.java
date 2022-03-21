package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.ta.utdid2.device.UTDevice;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.User;
import com.ushaqi.zhuishushenqi.model.baseweb.CopyEntity;
import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.GsonHelper;

import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * <p>
 *
 * @ClassName: WebJsHelper
 * @Date: 2019-05-30 14:45
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: </p>
 */
public class WebJsHelper {

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 16:41
     * @Description 检查activiy是否活跃
     */
    public static boolean checkActivityAlive(Activity activity) {
        return !AppUtils.isActivityInValid(activity);
    }

    public static void setWebTheme(Activity activity, ZssqWebData zssqWebData) {

//        boolean isFullScreen = WebStyleHelper.isFullScreenStyle(zssqWebData);
//        activity.setTheme(isFullScreen ? R.style.ReaderBuyTheme  : R.style.Theme_Tiantianyouyu);

    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 10:19
     * @Description 设置透明
     */
    private static void fixShadowStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                field.setInt(activity.getWindow().getDecorView(), Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            StatusBarCompat.setLightStatusBar(activity.getWindow(), true);
        }
    }
    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-30 23:17
     * @Description 是否callBack的JS
     */
    public static boolean isCallBackJs(String decodeUrl) {
        return (!TextUtils.isEmpty(decodeUrl) && decodeUrl.contains(WebConstans.BACK_EVENT)) || (!TextUtils.isEmpty(decodeUrl) && decodeUrl.contains(WebConstans.CALL_BACK));
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-30 23:19
     * @Description 是否更新UI的Js
     */
    public static boolean isUpdateWebJs(String decodeUrl) {
        return !TextUtils.isEmpty(decodeUrl) && ((decodeUrl.startsWith(WebConstans.SET_TOP_BAR) || decodeUrl.startsWith(WebConstans.SET_OPTION_BUTTON)));
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-17 20:10
     * @Description 获取callBack方法
     */
    public static String getCallBackMethod(String decodeUrl) {
        if (decodeUrl.contains("&param")) {
            return decodeUrl.substring(decodeUrl.indexOf(WebConstans.CALL_BACK) + WebConstans.CALL_BACK.length(), decodeUrl.indexOf("&param"));
        } else {
            return decodeUrl.substring(decodeUrl.indexOf(WebConstans.CALL_BACK) + WebConstans.CALL_BACK.length());
        }

    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-17 20:10
     * @Description 获取callback参数
     */
    public static String getCallBacParam(String decodeUrl) {
        if (decodeUrl.contains("param=")) {
            return decodeUrl.substring(decodeUrl.indexOf("param=") + "param=".length());
        } else {
            return "";
        }

    }


    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 16:58
     * @Description 拼接用户信息
     */
    public static String jointUserInfoJson() {
        User    user = UserHelper.getInstance().getUser();
        return GsonHelper.javaBeanToJson(user);
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 18:06
     * @Description 将Url转换成java对象
     */
    public static <T> T changeUrlToJavaBean(String url, Class<T> clazz) {
        if (!TextUtils.isEmpty(url)) {
            try {
                //截取json
                String data = url.substring(url.indexOf("{"), url.lastIndexOf("}") + 1);
                Gson gson = new Gson();
                return gson.fromJson(data, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T stringToBean(String data, Class<T> clazz) {
        if (!TextUtils.isEmpty(data)) {
            try {
                Gson gson = new Gson();
                return gson.fromJson(data, clazz);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 16:25
     * @Description 复制内容
     */
    public static void copy(Activity activity, CopyEntity copyEntity, WebView webView) {
        try {
            if (copyEntity != null) {
                ClipboardManager cmb = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("simple text", copyEntity.getCopyStr());
                if (cmb != null) {
                    cmb.setPrimaryClip(clipData);
                    callJsMethod("success", webView);
//                    ToastUtil.show("已复制");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 17:39
     * @Description 上传喜好
     */
//    public static void updateUserPreference(LikeCateEntity likeCate) {
//        try {
//            NewUserAttribute newUserAttribute = (NewUserAttribute) SerializeHelper.readObject(AppConstants.NEW_USER_ATTRIBUTE);
//            if (newUserAttribute != null) {
//                newUserAttribute.setLikeCate(likeCate.getLikecate());
//                SerializeHelper.saveObject(newUserAttribute, AppConstants.NEW_USER_ATTRIBUTE);
//            }
//            if (AppHelper.isLogin()) {
//                Account account = AppHelper.getAccount();
//                if (account != null && account.getUser() != null) {
//                    account.getUser().setLikeCate(likeCate.getLikecate());
//                    UserPropertyHelper.getInstance().saveAccount(account);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void callJsMethod(String status, WebView webView) {
        if (webView != null) {
            if (!TextUtils.isEmpty(WebConstans.sJsCallBackParam)) {
                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + WebConstans.sJsCallBackParam.replace("}", "," + "\"" + "result" + "\"" + ":" + "\"" + status + "\"" + "}") + ")");
            } else {
                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "()");
            }
            WebConstans.isShareHasSuccess = false;
        }
    }

    public static void callPayRechargeJsMethod(WebView webView, ZssqWebData zssqWebData, String status) {
        if (webView != null) {
            if (!TextUtils.isEmpty(WebConstans.sJsCallBackParam)) {
                StringBuilder sbCallbackParam = new StringBuilder(WebConstans.sJsCallBackParam);
                int index = sbCallbackParam.lastIndexOf("}");
                sbCallbackParam.insert(index, "," + "\"" + "result" + "\"" + ":" + "\"" + status + "\"" +
                        "," + "\"" + "orderId" + "\"" + ":" + "\"" + zssqWebData.getOrderId() + "\"");
                String jsString = "javascript:" + WebConstans.sJsCallBackMethod + "(" + sbCallbackParam.toString() + ")";
                webView.loadUrl(jsString);
            } else {
                webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "()");
            }
        }
    }

    public static void callJsMethod(JSONObject jo, WebView webView) {
        if (WebConstans.sJsCallBackParam != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "(" + WebConstans.sJsCallBackParam.replace("}", ",\"result\"" + ":" + jo.toString()) + "})");
        }
    }

    public static void callShareSuccessJs(WebView webView) {
        if (webView != null) {
            if (!TextUtils.isEmpty(WebConstans.sJsCallBackParam)) {
                webView.loadUrl("javascript:refreshCallBack" + "(" + WebConstans.sJsCallBackParam + ")");
            } else {
                webView.loadUrl("javascript:refreshCallBack()");
            }
        }
    }

    public static void callNewJsMethod(String status, WebView webView) {
        if (webView != null) {
            if (!TextUtils.isEmpty(WebConstans.sJsNewCallBackParam)) {
                webView.loadUrl("javascript:" + WebConstans.sJsNewCallBackMethod + "(" + WebConstans.sJsNewCallBackParam.replace("}", "," + "\"" + "result" + "\"" + ":" + "\"" + status + "\"" + "}") + ")");
            } else {
                webView.loadUrl("javascript:" + WebConstans.sJsNewCallBackMethod + "()");
            }
        }
    }

    public static void callProgressJs(int progress, WebView webView) {
        if (webView != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({result:false,progress:" + progress + "})");
        }
    }

    public static void callDownLoadSuccessJs(WebView webView) {
        if (webView != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({result:true,progress:100})");
        }
    }

    public static void callDownLoadFailJs(WebView webView) {
        if (webView != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({result:'fail',progress:100})");
        }
    }

    public static void callUnZipSuccessJs(WebView webView) {
        if (webView != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({result:'unzipSuccess',progress:100})");
        }
    }

    public static void callUnZipFailJs(WebView webView) {
        if (webView != null) {
            webView.loadUrl("javascript:" + WebConstans.sJsCallBackMethod + "({result:'unzipFail',progress:100})");
        }
    }

    public static String jointDeviceInfoJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");

        stringBuilder.append("oaid:");
        stringBuilder.append("\"");
        stringBuilder.append(AppHelper.getOAID());
        stringBuilder.append("\"");
        stringBuilder.append(",");

        stringBuilder.append("androidID:");
        stringBuilder.append("\"");
        stringBuilder.append(AppHelper.getAndroidId());
        stringBuilder.append("\"");
        stringBuilder.append(",");

        stringBuilder.append("UTDID:");
        stringBuilder.append("\"");
        stringBuilder.append(UTDevice.getUtdid(MyApplication.getInstance()));
        stringBuilder.append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

}
