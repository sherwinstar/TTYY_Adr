package com.ushaqi.zhuishushenqi.repository;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.ushaqi.zhuishushenqi.MyApplication;

import java.io.IOException;
import java.io.Serializable;

/**
 * 应用程序配置管理类
 * @author JackHu
 */
public abstract class BasePrefAccessor {

    private SharedPreferences mSharedPreferences;

    protected BasePrefAccessor(String fileName) {
        mSharedPreferences = MyApplication.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void saveString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public void saveBoolean(String key, boolean boo) {
        mSharedPreferences.edit().putBoolean(key, boo).apply();
    }

    public void saveInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public void saveFloat(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    public void saveLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public boolean saveObject(String key, Serializable dataObj) {
        Editor editor = mSharedPreferences.edit();
        try {
            editor.putString(key, ObjectSerializer.serialize(dataObj));
        } catch (IOException e) {
            e.printStackTrace(); // [should not happen on Android]
        }
        return editor.commit();
    }

    public String getString(String key, String... defValue) {
        if (defValue.length > 0)
            return mSharedPreferences.getString(key, defValue[0]);
        else
            return mSharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key, Boolean... defValue) {
        if (defValue.length > 0)
            return mSharedPreferences.getBoolean(key, defValue[0]);
        else
            return mSharedPreferences.getBoolean(key, true);
    }

    public int getInt(String key, Integer... defValue) {
        if (defValue.length > 0)
            return mSharedPreferences.getInt(key, defValue[0]);
        else
            return mSharedPreferences.getInt(key, 0);
    }

    public float getFloat(String key, Float... defValue) {
        if (defValue.length > 0)
            return mSharedPreferences.getFloat(key, defValue[0]);
        else
            return mSharedPreferences.getFloat(key, 0);
    }

    public long getLong(String key, Long... defValue) {
        if (defValue.length > 0)
            return mSharedPreferences.getLong(key, defValue[0]);
        else
            return mSharedPreferences.getLong(key, 0);
    }

    public Object getObject(String objKey) {
        Object dataObj = null;
        try {
            dataObj = ObjectSerializer.deserialize(mSharedPreferences.getString(objKey, null));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataObj;
    }

    @SuppressLint({"CommitPrefEdits"})
    public void clear(){
        mSharedPreferences.edit().clear();
        mSharedPreferences.edit().apply();
    }

    @SuppressLint("CommitPrefEdits")
    public void clearKey(String... key) {
        try {
            if (key != null && key.length > 0) {
                int size = key.length;
                for (int i = 0; i < size; i++) {
                    mSharedPreferences.edit().remove(key[i]);
                }
                mSharedPreferences.edit().apply();
            }
        } catch (Exception e) {

        }
    }

    public void writeAtOnce() {
        if (mSharedPreferences != null) {
            try {
                mSharedPreferences.edit().commit();
            } catch (Exception e) {
                //TODO
            }
        }
    }


    public void remove(String[] keys) {
        if (keys == null || keys.length == 0) {
            return;
        }
        Editor editor = mSharedPreferences.edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }
}
