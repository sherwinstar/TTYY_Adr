package com.ushaqi.zhuishushenqi.local;

import java.io.Serializable;

/**
 * sp接口方法
 * Created By JackHu on 2018/8/9.
 */
public interface Preference {

    void saveString(String key, String value);

    void saveBoolean(String key, boolean boo);

    void saveInt(String key, int value);

    void saveFloat(String key, float value);

    void saveLong(String key, long value);

    boolean saveObject(String key, Serializable dataObj);

    String getString(String key, String... defValue);

    boolean getBoolean(String key, Boolean... defValue);

    int getInt(String key, Integer... defValue);

    float getFloat(String key, Float... defValue);

    long getLong(String key, Long... defValue);

    Object getObject(String objKey);

    void clear();

    void clearKey(String... key);
}
