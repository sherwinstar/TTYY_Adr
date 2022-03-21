package com.ushaqi.zhuishushenqi.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 处理json 数据
 * Created by fly on 2016/12/28 0028.
 */
public final class GsonHelper {
    /**
     * gson工具
     * add fly
     */
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            String dateString = json.getAsString().replace("Z", "+0000");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = format.parse(dateString, new ParsePosition(0));
            if (date!= null) {
                return date;
            } else {
                dateString = json.getAsString();
                dateString = dateString.substring(0, dateString.length() -1) + ".0" + "+0000";
                return format.parse(dateString, new ParsePosition(0));
            }
        }
    }).create();


    /**
     * @param @param  list
     * @param @return 设定文件
     * @return String 返回类型
     * @Title: javaBeanToJson
     * @Description: 生成json字符串
     * @author fly
     */
    public static <T> String listToJson(List<T> list) {
        try {
            if (null == list || list.isEmpty()) {
                return null;
            }
            return gson.toJson(list);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param @param  object
     * @param @return 设定文件
     * @return String 返回类型
     * @Title: javaBeanToJson
     * @Description: java对象转换成json
     * @author fly
     */
    public static String javaBeanToJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param JsonStr
     * @param name
     * @return String 返回类型
     * @Title: getJsonStr
     * @Description: 取出json字符串中指定内容
     * @author fly
     */
    public static String getJsonStr(String JsonStr, String name) {
        if (TextUtils.isEmpty(JsonStr) || TextUtils.isEmpty(name)) {
            return null;
        }
        try {
            JSONObject jsonObject = new JSONObject(JsonStr);
            if (jsonObject != null && jsonObject.has(name))
                return jsonObject.getString(name);
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * @param jsonStr
     * @param c
     * @return T 返回类型
     * @Title: jsonStrToBean
     * @Description: json解析成java bean可用于多层解析
     * @author fly
     */
    public static <T> T jsonStrToBean(String jsonStr, Class<T> c) {
        T t = gson.fromJson(jsonStr, c);
        return gson.fromJson(jsonStr, c);
    }

    /**
     *
     * @param jsonString
     * @param t
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToBeanList(String jsonString, Class<T> t) {
        List<T> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonarray = parser.parse(jsonString).getAsJsonArray();
            for (JsonElement element : jsonarray
                    ) {
                list.add(gson.fromJson(element, t));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @Title: jsonStrToList
     * @Description: 将json数组解析成list [{},{},{}]
     * @param jsonStr
     * @param type
     * @return List<T> 返回类型
     * @Title: jsonStrToList
     * @Description: 将json数组解析成list [{},{},{}]
     * @author fly
     */
    public static <T> List<T> jsonStrToList(String jsonStr, Type type) {
        try {
            T[] arr= gson.fromJson(jsonStr, type);
            return Arrays.asList(arr);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @param jsonStr
     * @param type
     * @return List<T> 返回类型
     * @Title: jsonStrToList
     * @author fly
     */
    public static <T> T jsonStrToT(String jsonStr, Type type)  {
        return gson.fromJson(jsonStr, type);
    }

    /**
     * @param jsonStr
     * @param c
     * @return List<T> 返回类型
     * @Title: jsonStrToList
     * @Description: 将json数组解析成list [{},{},{}]
     * @author fly
     */
    public static <T> List<T> jsonStrToListClz(String jsonStr, Class<T[]> c) {
        T[] arr = gson.fromJson(jsonStr, c);
        return Arrays.asList(arr);
    }

    /**
     * 获取JsonObject
     *
     * @param json
     * @return
     */
    public static JsonObject parseJson(String json) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(json).getAsJsonObject();
        return jsonObj;
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(JsonObject json) {
        Map<String, Object> map = new HashMap<String, Object>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof JsonArray) {
                map.put((String) key, jsonToList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                map.put((String) key, jsonToMap((JsonObject) value));
            } else {
                String val = value.toString().replaceAll("\"", "");
                map.put((String) key, val);
            }
        }
        return map;
    }

    /**
     * 将JSONArray对象转换成List集合
     *
     * @param json
     * @return
     */
    public static List<Object> jsonToList(JsonArray json) {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < json.size(); i++) {
            Object value = json.get(i);
            if (value instanceof JsonArray) {
                list.add(jsonToList((JsonArray) value));
            } else if (value instanceof JsonObject) {
                list.add(jsonToMap((JsonObject) value));
            } else {
                list.add(value);
            }
        }
        return list;
    }

    /**
     * 从JSON数据中解析结果，返回一个存储目标对象的List<V>
     *
     * @param target 目标对象
     */
    public static  <V> List<V> getListFormJson(String jsonStr, Class<V> target)  {
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(jsonStr).getAsJsonArray();
            List<V> list = new ArrayList<V>();
            for (JsonElement obj : jsonArray) {
                // 这里貌似自动完成了目标对象的属性设置(对象没有set方法也可以)
                list.add(gson.fromJson(obj, target));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是滞错误J
     * @param json
     * @return
     */
    public static boolean isBadJson(String json) {
        return !isGoodJson(json);
    }

    /**
     * 判断是否正确json格式
     * @param json
     * @return
     */
    public static boolean isGoodJson(String json) {
        if (json == null || json.length() == 0) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {

            return false;
        }
    }
}
