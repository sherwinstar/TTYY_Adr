package com.ushaqi.zhuishushenqi.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.signapk.walle.WalleChannelReader;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.BuildConfig;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.imageloader.ImageHelper;
import com.ushaqi.zhuishushenqi.imageloader.ImageLoadingListener;
import com.ushaqi.zhuishushenqi.permission.SysPermissionHelper;
import com.ushaqi.zhuishushenqi.plugin.social.SocialConstants;
import com.ushaqi.zhuishushenqi.repository.GlobalPreference;
import com.ushaqi.zhuishushenqi.util.miit.IdentifierProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AppHelper {
    private static String mAndroidId;

    //跳转到商品详情页
    public static void jumpProductDetail(Activity activity, String tbPath) {

        try {
            if (hasInstallTaoBao(activity)) {
                Intent intent = new Intent();
                intent.setAction("Android.intent.action.VIEW");
                Uri uri = Uri.parse(tbPath); // 商品地址
                intent.setData(uri);
                intent.setClassName("com.taobao.taobao", "com.taobao.tao.detail.activity.DetailActivity");
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(tbPath);
                intent.setData(url);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static String getVersionName(Context context) {
        String name = "";
        PackageInfo packInfo = getPackageInfo(context);
        if (packInfo != null) {
            name = packInfo.versionName;
        }
        return name;
    }

    /**
     * 跳转到饿了么详情页
     * @param activity
     * @param tbPath
     */
    public static void jumpElemeProductDetail(Activity activity, String tbPath) {

        try {
            if (hasInstallEleme(activity)) {
                Intent intent = new Intent();
                intent.setAction("Android.intent.action.VIEW");
                Uri uri = Uri.parse(tbPath); // 商品地址
                intent.setData(uri);
                intent.setClassName("me.ele", "me.ele/.newretail.shop.RetailShopDetailActivity");
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri url = Uri.parse(tbPath);
                intent.setData(url);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //判断是否安装淘宝或者天猫
    public static boolean hasInstallTaoBao(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos =getInstalledPackagesForJump(packageManager);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                if (packName.equals("com.taobao.taobao")) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否安装饿了么
    public static boolean hasInstallEleme(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos =getInstalledPackagesForJump(packageManager);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                if (packName.equals("me.ele")) {
                    return true;
                }
            }
        }
        return false;
    }


    public static List<PackageInfo> getInstalledPackagesForJump(PackageManager pm ){
        if(pm == null){
            pm = MyApplication.getInstance().getPackageManager();
        }
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        return packageInfos;
    }
    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = getInstalledPackagesForJump(packageManager);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * 判断 用户是否安装京东客户端
     */
    public static boolean isJDClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = getInstalledPackagesForJump(packageManager);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.jingdong.app.mall")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断 用户是否安装拼多多客户端
     */
    public static boolean isPinduoduoClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = getInstalledPackagesForJump(packageManager);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.xunmeng.pinduoduo")) {
                    return true;
                }
            }
        }
        return false;
    }


    private static PackageInfo getPackageInfo(Context context) {
        synchronized (AppHelper.class) {
            PackageInfo packInfo = null;
            try {
                PackageManager packageManager = context.getPackageManager();
                packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packInfo;
        }
    }
    /**
     * 获取当前版本号
     *
     * @return versionCode，失败返回0
     */
//    public static int getVersionCode(Context context) {
//        int code = 0;
//        PackageInfo packInfo = getPackageInfo(context);
//        if (packInfo != null) {
//            code = packInfo.versionName;
//        }
//        return code;
//    }

    public static String getOAID() {
        String oaidValue = AppConstants.sOAID;
        if (TextUtils.isEmpty(oaidValue)) {
            oaidValue = GlobalPreference.getInstance().getString(IdentifierProvider.DEVICE_IDENTIFIER_OAID, "");
            AppConstants.sOAID = oaidValue;
        }

        return oaidValue;
    }

    public static String getAndroidId() {
        if (mAndroidId == null || mAndroidId.isEmpty()) {
            mAndroidId = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return mAndroidId;
    }
    @SuppressLint("MissingPermission")
    public static String getIMEI(final Context context) {
        String imei = null;
        try {
            if (SysPermissionHelper.hasPermission(context, SysPermissionHelper.PERMISSION_PHONE)&& Build.VERSION.SDK_INT < 29) {
                TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imei = telephonyMgr.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (imei != null) ? imei : "";
    }
    @SuppressLint("MissingPermission")
    public static String getRealDeviceInfo(Context context) {
//        if (!TextUtils.isEmpty(AppConstants.clientId)) {
//            return AppConstants.clientId;
//        }

        String deviceId = "";
        int checkSelfPermission1 = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkSelfPermission2 = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkSelfPermission1 != PackageManager.PERMISSION_GRANTED || checkSelfPermission2 != PackageManager.PERMISSION_GRANTED) {
            deviceId = getIMEI(context);
//            AppConstants.clientId = deviceId;
            return deviceId;
        }

        try {
            if (Build.VERSION.SDK_INT < 29){
                TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = TelephonyMgr.getDeviceId();
            }
            //故意这样写 不让玩家看出来
            String dbPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Android/system/core/" +
                    "error.txt";
            File file = new File(dbPath);
            if (file.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader bf = new BufferedReader(fr);
                String id = bf.readLine();
                if (!TextUtils.isEmpty(id)) {
                    deviceId = id;
                } else {
                    if (TextUtils.isEmpty(deviceId) || "000000000000000".equals(deviceId)) {
                        deviceId = getAndroidId();
                        FileWriter fw = new FileWriter(file);
                        fw.write(deviceId);
                        fw.close();
                    }
                }
                bf.close();
                fr.close();

            } else {// 文件不存在
                if (TextUtils.isEmpty(deviceId) || "000000000000000".equals(deviceId)) {
                    deviceId = getAndroidId();
                }
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();

                FileWriter fw = new FileWriter(file);
                fw.write(deviceId);
                fw.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        AppConstants.clientId = deviceId;
        return deviceId;
    }

    public static String getInstallTime() {
        long firstInstallTime = GlobalPreference.getInstance().getLong( AppConstants.PREF_FIRST_LAUNCH_TIME, 0L);
        return TimeUtils.getTime(firstInstallTime);
    }
    public static String getChannelId(MyApplication instance) {
        if (BuildConfig.DEBUG) {
            return AppConstants.PROMOTOTER_ID;
        }
        String channel = WalleChannelReader.getChannel(instance);
        if (TextUtils.isEmpty(channel)) {
            return "";
        }
        String[] split = channel.split("_");
        if (split != null && split.length >= 2) {
            return split[1];
        } else {
            return "";
        }
    }

    /**
     * 获取进入需要的时间
     * @return
     */
    public static long getEnterAppTime(){
        return System.currentTimeMillis()-GlobalPreference.getInstance().getLong("long_enter_app_application_time", System.currentTimeMillis());
    }

    /**
     * 安装APK文件
     *
     * @return true表示正常执行安装，反之为false
     */
    public static boolean installApk(Context context, File apkFile) {
        if (context == null) {
            return false;
        }
        if (!apkFile.exists()) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 24) {//判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(context, "com.jxjuwen.ttyy.fileProvider", apkFile);//在AndroidManifest中的android:authorities值
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        } else {
            Intent install = new Intent(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(install);
        }
        return false;
    }

//    private static void handleWebviewDir(Context context) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
//            return;
//        }
//        try {
//            String suffix = "";
//            String processName = getProcessName(context);
//            if (!TextUtils.equals(context.getPackageName(), processName)) {//判断不等于默认进程名称
//                suffix = TextUtils.isEmpty(processName) ? context.getPackageName() : processName;
//                WebView.setDataDirectorySuffix(suffix);
//                suffix = "_" + suffix;
//            }
//            tryLockOrRecreateFile(context,suffix);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.P)
//    private static void tryLockOrRecreateFile(Context context,String suffix) {
//        String sb = context.getDataDir().getAbsolutePath() +
//                "/app_webview"+suffix+"/webview_data.lock";
//        File file = new File(sb);
//        if (file.exists()) {
//            try {
//                FileLock tryLock = new RandomAccessFile(file, "rw").getChannel().tryLock();
//                if (tryLock != null) {
//                    tryLock.close();
//                } else {
//                    createFile(file, file.delete());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                boolean deleted = false;
//                if (file.exists()) {
//                    deleted = file.delete();
//                }
//                createFile(file, deleted);
//            }
//        }
//    }
//
//    private static void createFile(File file, boolean deleted){
//        try {
//            if (deleted && !file.exists()) {
//                file.createNewFile();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 打开微信方法
     * @param context
     */
    public static void openWeiXin(Context context){
        Intent lan = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setComponent(lan.getComponent());
        context.startActivity(intent);
    }


    /**
     * 保存图片
     * @param act
     * @param value
     */
    public static void saveImage(Activity act,String value) {
        File shareImageDir = new File(AppConstants.SHARE_PATH);
        if(!shareImageDir.exists()){
            shareImageDir.mkdirs();
        }
        ImageHelper.getInstance().loadImage(value, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, Throwable cause) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    saveBmp2Gallery(act,loadedImage,null);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });

    }

    public static void saveBmp2Gallery(Activity mContext, Bitmap bmp, String picName) {
        if (mContext == null || mContext.isFinishing() || mContext.isDestroyed()){
            return;
        }
        if (bmp == null){
            return;
        }
        String fileName = null;
        //系统相册目录
        String galleryPath = Environment.getExternalStorageDirectory()
                + File.separator + Environment.DIRECTORY_DCIM
                + File.separator + "Camera" + File.separator;
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;
        // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
        try {
            file = new File(galleryPath, picName );
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MediaStore.Images.Media.insertImage(mContext.getContentResolver(),bmp,fileName,null);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 打开微信小程序
     */
    public static void openMiniApp(String path) {
        try {
//            if(!SocialUtils.checkWXSupport()){
//                ToastUtil.show("请安装微信客户端后重试");
//                return;
//            }
            IWXAPI api = WXAPIFactory.createWXAPI(MyApplication.getInstance(), SocialConstants.WEIXIN_APP_ID);
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = "gh_72a4eb2d4324"; // 小程序原始id
            //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.path = path;
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            api.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openMiniApp(String path, String userName) {
        try {
//            if(!SocialUtils.checkWXSupport()){
//                ToastUtil.show("请安装微信客户端后重试");
//                return;
//            }
            IWXAPI api = WXAPIFactory.createWXAPI(MyApplication.getInstance(), SocialConstants.WEIXIN_APP_ID);
            WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
            req.userName = userName; // 小程序原始id
            //拉起小程序页面的可带参路径，不填默认拉起小程序首页
            req.path = path;
            req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
            api.sendReq(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断网络是否已经链接
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

}
