package com.ushaqi.zhuishushenqi.module.baseweb.impl;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.webkit.DownloadListener;

import com.ushaqi.zhuishushenqi.model.baseweb.ZssqWebData;

public class WebDownLoadImpl implements DownloadListener {

    private Activity mActivity;
    private DownloadManager mDownloadManager;
    private ZssqWebData mZssqWebData;

    public WebDownLoadImpl(Activity activity) {
        this.mActivity = activity;
        mDownloadManager = (DownloadManager) mActivity.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        if (mDownloadManager != null) {
            // 2.3及其以上的使用DownloadManager下载
//            downloadLink(url);
        } else {
            // 2.3以下的使用浏览器打开
//            downloadDefault(url);
        }
    }

    public void fillWebData(ZssqWebData zssqWebData) {
        this.mZssqWebData = zssqWebData;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 16:28
     * @Description 2.3以下的使用浏览器打开
     */
//    private void downloadLink(final String url) {
//        try {
//            if (!isDownloadingFinalUrl(url)) {
//                if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed() || mZssqWebData == null) {
//                    return;
//                }
//                String downloadUrl = mZssqWebData.getUrl();
//                if (!TextUtils.isEmpty(mZssqWebData.getUrl())
//                        && mZssqWebData.getUrl().contains("zs_web_source=fulishe")) {
//                    downloadUrl = url;
//                }
//                final String fileName = NetUtil.getApkFileName(downloadUrl); //使用原始的下载地址，webview加载后的地址拼接了其它参数，每次都不一样
//                String path = ApkInstallHelper.getDownloadApkPath(fileName);
//                if (!TextUtils.isEmpty(path)) {
//                    File file = new File(path);
//                    if (file.exists()) {
//                        ApkInstallHelper.installAPK(mActivity, file);
//                        return;
//                    }
//                }
//                ProxyManager.getWebJsProxy().downloadApk(mActivity, url, fileName, mZssqWebData, mDownloadManager);
//            } else {
//                ToastUtil.show(mActivity, "已经在下载队列中");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 16:32
     * @Description 是否已经在下载中
     */
    private boolean isDownloadingFinalUrl(String finalUrl) {
        DownloadManager.Query query = new DownloadManager.Query();
        Cursor c = mDownloadManager.query(query);
        if (c != null) {
            while (c.moveToNext()) {
                String u = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                if (u.equals(finalUrl) && (status == DownloadManager.STATUS_PENDING || status == DownloadManager
                        .STATUS_RUNNING)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 16:32
     * @Description 开始下载
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
//    public static void downloadApk(String url, String fileName, ZssqWebData zssqWebData, DownloadManager downloadManager) {
//        Uri source = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(source);
//        if (zssqWebData != null) {
//            request.setTitle(zssqWebData.getTitle());
//        }
//        if (OSUtil.hasHoneycomb()) {
//            request.allowScanningByMediaScanner();
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        }
//        if (null != zssqWebData) {
//            request.setTitle(zssqWebData.getTitle());
//        }
////        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
//        request.setDestinationInExternalFilesDir(GlobalConfig.getInstance().getContext(),Environment.DIRECTORY_DOWNLOADS,fileName);
//        request.setMimeType("application/vnd.android.package-archive");
//        // bug:IllegalArgumentException: Unknown URL content://downloads/my_downloads
//        try {
//            long id = downloadManager.enqueue(request);
//            ApkInstallHelper.addApk(new DownApkInfo(id, ""));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        AppDownloadManager.getInstance().getDownloadAds().add(url);
//    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-22 16:28
     * @Description 2.3及其以上的使用DownloadManager下载
     */
//    private void downloadDefault(String url) {
//        AppHelper.startActionView(mActivity, url);
//        MobclickAgent.onEvent(mActivity, "splash_ad_download", url);
//    }

}
