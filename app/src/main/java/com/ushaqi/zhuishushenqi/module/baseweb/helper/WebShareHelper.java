package com.ushaqi.zhuishushenqi.module.baseweb.helper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.MyApplication;
import com.ushaqi.zhuishushenqi.dialog.ShareDialog;
import com.ushaqi.zhuishushenqi.httpcore.HttpUrlProvider;
import com.ushaqi.zhuishushenqi.imageloader.ImageHelper;
import com.ushaqi.zhuishushenqi.imageloader.ImageLoadingListener;
import com.ushaqi.zhuishushenqi.local.UserHelper;
import com.ushaqi.zhuishushenqi.model.baseweb.ShareImageEntity;
import com.ushaqi.zhuishushenqi.module.baseweb.WebConstans;
import com.ushaqi.zhuishushenqi.util.FileHelper;
import com.ushaqi.zhuishushenqi.util.ToastUtil;
import com.ushaqi.zhuishushenqi.widget.SVProgressHUD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * <p>
 *
 * @ClassName: WebShareHelper
 * @Date: 2019-05-23 16:34
 * @Author: jared
 * @Org: 上海元聚网络科技有限公司
 * @Description: H5的分享辅助类
 * </p>
 */
public class WebShareHelper {

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 16:36
     * @Description 邀请下载
     */
    public static void shareSpread(final ShareImageEntity shareImageEntity, final Activity activity,  final WebView webView,String shareType) {
        if (shareImageEntity == null) {
            return;
        }

//        shareLoadingState(true, activity);
        if(TextUtils.equals("inviteDownload",shareImageEntity.getGroup())){
            if (!UserHelper.getInstance().isLogin()) {
                ToastUtil.show("请先登录");
                return;
            }
            String imageUrl= HttpUrlProvider.getServerRoot().concat("/shopping/Config/shareImg/").concat(UserHelper.getInstance().getUser().getPromoterId());
            shareContent(activity, webView,imageUrl,"invite.jpg",shareType);
        }else {
            String src = shareImageEntity.getSrc();
            if(URLUtil.isNetworkUrl(src)){
            String lastPathSegment = Uri.parse(src).getLastPathSegment();
            shareContent(activity, webView,src,lastPathSegment,shareType);
            }
        }

    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-05-23 16:53
     * @Description 开始分享
     */
    private static void shareContent(final Activity activity, final WebView webView, String imageUrl,String jpgName,String shareType) {
        File shareImageDir = new File(AppConstants.SHARE_PATH);
        if(!shareImageDir.exists()){
            shareImageDir.mkdirs();
        }
        File imageFile = new File(shareImageDir, jpgName);
        ImageHelper.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, Throwable cause) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                try {
                    if(TextUtils.equals(shareType,WebConstans.DOWNLOAD_IMAGE)){
                        saveBmp2Gallery(activity,loadedImage,jpgName);
                        ToastUtil.show("图片已保存至相册");
                    }else {
                        FileHelper.saveInviteImage(MyApplication.getInstance(), imageFile, loadedImage, 100);
                        if(TextUtils.equals(WebConstans.SHARE_TYPE_WECHAT,shareType)){
                            WebJsDataHelper.getInstance().shareWechat(activity,  imageFile.getAbsolutePath(), imageUri);
                        }else {
                            WebJsDataHelper.getInstance().shareWechatMoment(activity,  imageFile.getAbsolutePath(), imageUri);
                        }
                    }
                   //storage/emulated/0/Android/data/com.jxjuwen.ttyy/files/Pictures/share/invite.jpg
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
            }
        });

//        shareLoadingState(false, activity);
        WebJsDataHelper.getInstance().setShareStateCallBackCallBack(new WebJsDataHelper.ShareStateCallBack() {
            @Override
            public void onSuccess(String group) {
                WebJsHelper.callJsMethod("success", webView);
                ToastUtil.show(activity, "分享成功");
                if(!TextUtils.equals(shareType,WebConstans.DOWNLOAD_IMAGE)){
                    imageFile.delete();
                }

            }

            @Override
            public void onFailed(String group) {
                WebJsHelper.callJsMethod("fail", webView);
                ToastUtil.show(activity, "取消分享");
            }
        });
    }


    /**
     * 耗时操作时候的loading图
     *
     * @param b
     */
    private static void shareLoadingState(boolean b, final Activity activity) {
        if (b) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SVProgressHUD.show(activity);
                }
            });
        } else {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (SVProgressHUD.isShowing(activity)) {
                        SVProgressHUD.dismiss(activity);
                    }
                }
            });
        }
    }


    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
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



}
