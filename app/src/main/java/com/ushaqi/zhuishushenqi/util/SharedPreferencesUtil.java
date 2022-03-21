package com.ushaqi.zhuishushenqi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;


public class SharedPreferencesUtil {

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }
    private static final String ONLINE_PARAM_FILE_SP = "zs_online_param";
    // ---------------------------------------------------------------------------------------------

    public static boolean get(Context context, String key) {
        return get(context, key, true);
    }

    public static boolean get(Context context, String key, boolean defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    public static int get(Context context, String key, int defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getInt(key, defValue);
    }

    public static String get(Context context, String key, String defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static long get(Context context, String key, long defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static float get(Context context, String key, float defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    public static void put(Context context, String key, boolean value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, long value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, String value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, int value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.apply();
    }

    public static void put(Context context, String key, float value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defValue) {
        if (context == null) {
            return defValue;
        }
        return getSharedPreferences(context).getLong(key, defValue);
    }

    public static void putLong(Context context, String key, long value) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.apply();
    }

    public static void clear(Context context) {
        if (context == null) {
            return;
        }
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.apply();
    }
    /**
     * 取得editor，避免大批量保存时候重复打开关闭文件
     * @param context
     * @zhy
     */
    public static SharedPreferences.Editor getOnlinePararSPEditor(Context context ){
        SharedPreferences sp = context.getSharedPreferences(ONLINE_PARAM_FILE_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        return editor;
    }
    /**
     * 保存数据，保存完成后需要提交commit
     * @param editor
     * @param key
     * @param object
     * @zhy
     */
    public static void setOnlineParam( SharedPreferences.Editor editor, String key, Object object){

        String type = object.getClass().getSimpleName();
      /*  SharedPreferences sp = context.getSharedPreferences(ONLINE_PARAM_SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();*/

        if("String".equals(type)){
            editor.putString(key, (String)object);
        }
        else if("Integer".equals(type)){
            editor.putInt(key, (Integer)object);
        }
        else if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean)object);
        }
        else if("Float".equals(type)){
            editor.putFloat(key, (Float)object);
        }
        else if("Long".equals(type)){
            editor.putLong(key, (Long)object);
        }

        editor.apply();
    }
    /**
     * 提交保存文件
     * @param editor
     * @zhy
     */
    public static void commitOnlineParam(SharedPreferences.Editor editor){
        editor.commit();
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param context
     * @param key
     * @param defaultObject
     * @return
     * @zhy
     */
    public static Object getOnlineParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(ONLINE_PARAM_FILE_SP, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }
    /** SharedPreferences get */
    public static String getSP(Context mContext, String fileName, String Key, String value) {
        if (mContext != null) {
            @SuppressWarnings("static-access")
            SharedPreferences preferences = mContext.getSharedPreferences(fileName, mContext.MODE_PRIVATE);
            String result = preferences.getString(Key, value);

            return result;
        } else {
            return null;
        }
    }
    /**
     * 递归删除文件和文件夹
     *
     * @param file
     *            要删除的根目录
     */
    public static void DeleteFile(File file) {
        try{
            if (!file.exists()) {
                Log.d("test", "文件可能不存在");
                return;
            } else {
                if (file.isFile()) {
                    file.delete();
                    return;
                }
                if (file.isDirectory()) {
                    File[] childFile = file.listFiles();
                    if (childFile == null || childFile.length == 0) {
                        file.delete();
                        return;
                    }
                    for (File f : childFile) {
                        DeleteFile(f);
                    }
                    file.delete();
                }
            }
        }catch (Exception e){}

    }









}
