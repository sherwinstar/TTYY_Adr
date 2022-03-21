package com.ushaqi.zhuishushenqi.module.baseweb;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.ushaqi.zhuishushenqi.imageloader.ImageHelper;
import com.ushaqi.zhuishushenqi.imageloader.ImageLoadingListener;
import com.ushaqi.zhuishushenqi.model.baseweb.SaveImageEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.helper.WebJsHelper;
import com.ushaqi.zhuishushenqi.sensors.H5AnalysisManager;
import com.ushaqi.zhuishushenqi.util.AppHelper;
import com.ushaqi.zhuishushenqi.util.ImageUtil;
import com.ushaqi.zhuishushenqi.util.ToastUtil;

/**
 * 用于在js中调用的对象
 *

 */
public class TtyyApi {
    private static final String TAG = "TtyyApi";
    private Activity mCtx;
    private WebView mWebView;

    public TtyyApi(Activity activity, WebView webView) {
        this.mCtx = activity;
        this.mWebView = webView;
    }

    @JavascriptInterface
    public void upLoadKeyItemExpousure(String value) {
        H5AnalysisManager.addH5KeyItemExpousure(value);
    }
    @JavascriptInterface
    public void openMiniApp(String path) {
        AppHelper.openMiniApp(path);
    }

    @JavascriptInterface
    public void openMiniApp(String path,String userName) {
        AppHelper.openMiniApp(path,userName);
    }

    @JavascriptInterface
    public void openWeiXin() {
        AppHelper.openWeiXin(mCtx);
    }

    @JavascriptInterface
    public void saveImage(String value) {
//        if (!SysPermissionHelper.hasPermission(mCtx, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            final Dialog dialog = SysPermissionHelper.showPermissionDialog(mCtx, "存储权限", "用于保存图片",
//                    "【去开启】-【应用信息页】-【权限】-【存储权限开关】", Manifest.permission.WRITE_EXTERNAL_STORAGE, "2");
//            return;
//        }
        if (TextUtils.isEmpty(value)) {
            ToastUtil.show("图片内容为空");
            return;
        }
        final SaveImageEntity saveImageEntity = WebJsHelper.changeUrlToJavaBean(value, SaveImageEntity.class);
        if (saveImageEntity != null && !TextUtils.isEmpty(saveImageEntity.getImageValue())) {
            if (saveImageEntity.isBase64()) {
                try {
                    byte[] bitmapArray = android.util.Base64.decode(saveImageEntity.getImageValue(), android.util.Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
                    String fileName = "zs_" + System.currentTimeMillis();
                    ImageUtil.saveBmp2Gallery(mCtx, bitmap, fileName);
                    ToastUtil.show("图片保存成功");
                    if (saveImageEntity.isJumpToWeChat()) {
                        openWechatScan(mCtx);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                ImageHelper.getInstance().loadImage(saveImageEntity.getImageValue(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, Throwable cause) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        String fileName = "zs_" + System.currentTimeMillis();
                        ImageUtil.saveBmp2Gallery(mCtx, loadedImage, fileName);
                        mCtx.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.show("图片保存成功");
                                if (saveImageEntity.isJumpToWeChat()) {
                                   openWechatScan(mCtx);
                                }
                            }
                        });
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }

        }

    }
    public static void openWechatScan(Activity activity){
        try {
            if (activity == null || activity.isFinishing() || activity.isDestroyed()){
                return;
            }
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
            //intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction("android.intent.action.VIEW");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.show("请先安装微信客户端～～");
        }
    }
}
