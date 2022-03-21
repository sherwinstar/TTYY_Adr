package com.ushaqi.zhuishushenqi;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.ushaqi.zhuishushenqi.util.AppHelper;

import java.io.File;

/**
 * 监听DownloadManager下载完成
 * <p/>
 * Created by ccheng on 5/22/14.
 */
public class DownloadCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor c = downloadManager.query(query);
                if (c != null) {
                    if (c.moveToFirst()) {
                        String url = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                        String uri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                        AppDownloadManager app = AppDownloadManager.getInstance();
                        if (app.getDownloadAds().contains(url)
                                || app.getDownloadGames().contains(url)
                                || app.getDownloadIds().contains(downloadId)) {
                            File file = new File(Uri.parse(uri).getPath());
                            AppHelper.installApk(context, file);
                            context.sendBroadcast(new Intent("update_game_item_status"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}