package com.ushaqi.zhuishushenqi.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.ushaqi.zhuishushenqi.AppDownloadManager;
import com.ushaqi.zhuishushenqi.R;
import com.ushaqi.zhuishushenqi.model.UpdateApkConfig;
import com.ushaqi.zhuishushenqi.util.AppUtils;
import com.ushaqi.zhuishushenqi.util.OSUtil;

/**
 * @author chengwencan
 * @date 2021/7/12
 * Describe：
 */
public class UpdateAppDialog extends Dialog implements View.OnClickListener{
    private TextView mTvUpdateDes,mTvUpdateConFirm;
    private ImageView mIvUpdateCancel;
    private UpdateApkConfig updateApkConfig;
    private String  apkUrl;
    private Context context;

    public UpdateAppDialog(Context context , UpdateApkConfig  updateApkConfig) {
        super(context);
        this.context=context;
        this.updateApkConfig=updateApkConfig;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.dialog_update_app);
        mTvUpdateDes=  findViewById(R.id.tv_update_des);
        mTvUpdateConFirm=findViewById(R.id.tv_update_confirm);
        mIvUpdateCancel= findViewById(R.id.iv_update_cancel);
        if (updateApkConfig!=null&&updateApkConfig.getData()!=null){
            mTvUpdateDes.setText(updateApkConfig.getData().getContext());
            apkUrl=updateApkConfig.getData().getApkUrl();
        }


        mTvUpdateConFirm.setOnClickListener(this);
        mIvUpdateCancel.setOnClickListener(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_update_confirm){
            if (isShowing()) {
                dismiss();
            }
            downloadAndQuite(apkUrl);

        }else if (v.getId()==R.id.iv_update_cancel){
            if (isShowing()) {
                dismiss();
            }
        }

    }

    private void downloadAndQuite(String apkUrk) {
        try {
            String fileName = AppUtils.getApkFileName(apkUrk);
            downloadApk(apkUrk, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected android.app.DownloadManager mDownloadManager = null;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void downloadApk(String url, String fileName) {
        mDownloadManager = (android.app.DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri source = Uri.parse(url);
        android.app.DownloadManager.Request request = new android.app.DownloadManager.Request(source);
        if (OSUtil.hasHoneycomb()) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);
        // bug:IllegalArgumentException: Unknown URL content://downloads/my_downloads
        try {
            request.setMimeType("application/vnd.android.package-archive");
            mDownloadManager.enqueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AppDownloadManager.getInstance().getDownloadAds().add(url);
    }
}
