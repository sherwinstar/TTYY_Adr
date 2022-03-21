package com.ushaqi.zhuishushenqi.sensors;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * JSONObject构建者
 *
 * @author JackHu
 * @date 2020/5/20
 */
public class SensorsParamBuilder {
    private JSONObject properties;

    public static SensorsParamBuilder create() {
        return new SensorsParamBuilder();
    }

    public static SensorsParamBuilder createIfNull(SensorsParamBuilder paramBuilder) {
        return (paramBuilder == null) ? new SensorsParamBuilder() : paramBuilder;
    }

    private SensorsParamBuilder() {
        properties = new JSONObject();
    }

    public SensorsParamBuilder put(String key, Boolean value) {
        putProperty(key, value);
        return this;
    }

    public SensorsParamBuilder put(String key, String value) {
        putProperty(key, value);
        return this;
    }

    public SensorsParamBuilder put(String key, Float value) {
        if(value == null){
            putProperty(key, 0f);
        }else{
            putProperty(key, value);
        }
        return this;
    }

    public SensorsParamBuilder put(String key, Double value) {
        if(value == null){
            putProperty(key, 0d);
        }else{
            putProperty(key, value);
        }
        return this;
    }

    public SensorsParamBuilder put(String key, Long value) {
        if(value == null){
            putProperty(key, 0L);
        }else{
            putProperty(key, value);
        }
        return this;
    }

    public SensorsParamBuilder put(String key, Integer value) {
        if(value == null){
            putProperty(key, 0);
        }else{
            putProperty(key, value);
        }
        return this;
    }

    public SensorsParamBuilder put(String key, Byte value) {
        putProperty(key, value);
        return this;
    }

    public SensorsParamBuilder putJsonObject(JSONObject jsonObject) {
        if (jsonObject == null) {
            return this;
        }
        Iterator<String> keyIterator = jsonObject.keys();
        try {
            while (keyIterator.hasNext()) {
                String next = keyIterator.next();
                Object value = jsonObject.get(next);
                putProperty(next, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public <T> SensorsParamBuilder putArray(String key, List<T> value) {
        putArrayProperty(key, value);
        return this;
    }

    public JSONObject build() {
        return properties;
    }

    public void remove(String key) {
        properties.remove(key);
    }

    private <T> void putArrayProperty(String key, List<T> value) {
        if (value == null || value.isEmpty()) {
            return;
        }

        try {
            JSONArray array = new JSONArray();
            for (T t : value) {
                if (t == null || (t instanceof String && TextUtils.isEmpty((String) t))) {
                    continue;
                }
                array.put(t);
            }
            properties.put(key, array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public <T> void putProperty(String key, T value) {
        if (value == null) {
            return;
        }
        if (value instanceof String && TextUtils.isEmpty((String) value)) {
            return;
        }
        try {
            properties.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putProperty(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }
        try {
            properties.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}