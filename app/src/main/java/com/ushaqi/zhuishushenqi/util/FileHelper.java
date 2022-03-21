package com.ushaqi.zhuishushenqi.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import com.ushaqi.zhuishushenqi.AppConstants;
import com.ushaqi.zhuishushenqi.MyApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper {

    public static boolean isExitFile(String path) {
        File file = new File(path);
        return file.exists();
    }
    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 15:10
     * @Description 将字符串写入本地文件
     */
    public static void writeTextToLocalFile(String path, String fileName, String text) {
        FileWriter output = null;
        BufferedWriter writer = null;
        try {
            File d = new File(path);
            if (!d.exists()) {
                d.mkdirs();
            }
            File file = new File(path, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            output = new FileWriter(path + "/" + fileName);
            writer = new BufferedWriter(output);
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != output) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param
     * @return
     * @add by Jared.Bai
     * @add on 2019-11-01 14:17
     * @Description 从本地文件读取信息
     */
    public static String readTextFromLocalFile(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                fis.close();
                bufferedReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }
    /**
     *
     *
     * @return
     */
    public static String getAbsImgPath() {
            File externalFilesDir = null;
            try {
                externalFilesDir = MyApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (externalFilesDir == null) {
                return MyApplication.getInstance().getFilesDir().getAbsolutePath();
            }
            return externalFilesDir.getAbsolutePath();
        }




    public static void saveInviteImage(Context context, File fileName, Bitmap bitmap, int quality) throws IOException {
        if (bitmap == null || fileName == null || context == null) {
            return;
        }
        FileOutputStream fos = null;
        ByteArrayOutputStream stream = null;
        try {
            fos = new FileOutputStream(fileName);
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            byte[] bytes = stream.toByteArray();
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}