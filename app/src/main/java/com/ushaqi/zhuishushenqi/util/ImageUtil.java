package com.ushaqi.zhuishushenqi.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 获取图片并处理图片的工具类
 * Created by user on 2017/3/27.
 */

public class ImageUtil {

    /**
     * 获取屏幕的尺寸
     *
     * @param context 上下文
     * @return 设备屏幕的宽高, 放在数组里
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    /**
     * 给图片设定缩放值,对图片大小进行判断,大于控件宽度的图片缩放到与控件一样宽
     *
     * @param bitmap
     * @return 设定好缩放值的bitmap, 用一个bitmap来接收
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, Context mContext) {
        int imgWidth = bitmap.getWidth();
        int imgHeight = bitmap.getHeight();
        int screenWidth = getScreenSize(mContext)[0];
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图
        if (imgWidth >= screenWidth) {
            float scale = screenWidth * 0.9f / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, imgWidth, imgHeight, mx, true);
        }
        return bitmap;
    }

    /**
     * 获取系统图库图片的SD卡路径
     *
     * @param selectedImage 图片的URI
     * @param mContext      上下文
     * @return 图片在系统的真实路径
     */
    public static String getPhoneImage(Uri selectedImage, Context mContext) {
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        String fileName = "";
        Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            fileName = cursor.getString(idx);
            cursor.close();
        } else {
            fileName = selectedImage.getPath();
        }
        return fileName;
    }


    /**
     * 压缩图片
     *
     * @param fileName 图片的路径
     * @param mCotenxt 上下文
     * @return 压缩后的图片, 对图片的尺寸和质量进行压缩, 不失真
     */
    public static Bitmap CompressPic(String fileName, Context mCotenxt) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileName, options);
        // 计算图片大小
        options.inSampleSize = calculateInSampleSize(options, 1080, 1920);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
        //缩放图片
        bitmap = resizeBitmap(bitmap, mCotenxt);
        return bitmap;
    }

    /**
     * 计算图片的缩放比
     *
     * @param options
     * @param reqWidth  目标宽度
     * @param reqHeight 目标高度
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    public static Bitmap convertViewToBitmap(View view, int width, int height) {
        view.layout(0, 0, width, height);
        //调用下面这个方法非常重要，如果没有调用这个方法，得到的bitmap为null
        int measureSpecWith = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measureSpecHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        //这个方法也非常重要，设置布局的尺寸和位置
        view.measure(measureSpecWith, measureSpecHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas c = new Canvas(b);
        //view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        // Draw background
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(c);
        } else {
            c.drawColor(Color.WHITE);
        }
        // Draw view to canvas
        view.draw(c);
        return b;
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-10-16 09:17
     * @Description dp2px
     */
    public static float dip2px(float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, Resources.getSystem().getDisplayMetrics());
    }

    public static int dp2px(float dip) {
        return (int)dip2px(dip);
    }

    public static int sp2px(float sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getBitmapFromRes(Context context, int res) {
        return BitmapFactory.decodeResource(context.getResources(), res);
    }

    /**
     * Return the compressed bitmap using quality.
     *
     * @param src         The source of bitmap.
     * @param maxByteSize The maximum size of byte.
     * @param recycle     True to recycle the source of bitmap, false otherwise.
     * @return the compressed bitmap
     */
    public static byte[] getByteCompressByQuality(final Bitmap src,
                                                  final long maxByteSize,
                                                  final boolean recycle) {
        if (src == null || src.getWidth() == 0 || src.getHeight() == 0 || maxByteSize <= 0) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes;
        if (baos.size() <= maxByteSize) {
            bytes = baos.toByteArray();
        } else {
            baos.reset();
            src.compress(Bitmap.CompressFormat.JPEG, 0, baos);
            if (baos.size() >= maxByteSize) {
                bytes = baos.toByteArray();
            } else {
                // find the best quality using binary search
                int st = 0;
                int end = 100;
                int mid = 0;
                while (st < end) {
                    mid = (st + end) / 2;
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, mid, baos);
                    int len = baos.size();
                    if (len == maxByteSize) {
                        break;
                    } else if (len > maxByteSize) {
                        end = mid - 1;
                    } else {
                        st = mid + 1;
                    }
                }
                if (end == mid - 1) {
                    baos.reset();
                    src.compress(Bitmap.CompressFormat.JPEG, st, baos);
                }
                bytes = baos.toByteArray();
            }
        }

        if (recycle && !src.isRecycled()) {
            src.recycle();
        }

        return bytes;
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
            file = new File(galleryPath, picName + ".jpg");
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
