package com.ushaqi.zhuishushenqi.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Andy.zhang
 * @date 2018/7/31
 */
public final class ListUtils {
    private ListUtils() {
        throw new UnsupportedOperationException("ListUtils can't be instantiated");
    }

    /**
     * @param strs
     * @return
     */
    public static List<String> stringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }

    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> int getSize(List<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> int getSize(V[] sourceList) {
        return sourceList == null ? 0 : sourceList.length;
    }

    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> int getSize(Collection<V> sourceList) {
        return sourceList == null ? 0 : sourceList.size();
    }

    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }

    /**
     * @param list
     * @param separator
     * @return
     */
    public static String join(List<String> list, char separator) {
        return join(list, new String(new char[]{separator}));
    }

    /**
     * @param list
     * @param separator
     * @return
     */
    public static String join(List<String> list, String separator) {
        return list == null ? "" : TextUtils.join(separator, list);
    }

    /**
     * @param sourceList
     * @param value
     * @param <V>
     * @return
     */
    public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
        return (sourceList != null && value != null) ? sourceList.add(value) : false;
    }


    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> List<V> invertList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return sourceList;
        }

        List<V> invertList = new ArrayList<V>(sourceList.size());
        for (int i = sourceList.size() - 1; i >= 0; i--) {
            invertList.add(sourceList.get(i));
        }

        return invertList;
    }

    /**
     * @param sourceList
     * @param <V>
     * @return
     */
    public static <V> boolean isEmpty(V[] sourceList) {
        return (sourceList == null || sourceList.length == 0);
    }

    public static <V> Collection<V> getValidList(Collection<V> list) {
        return getValidList(list, Collections.<V>emptyList());
    }

    public static <V> Collection<V> getValidList(Collection<V> list, Collection<V> defaultList) {
        return list == null || list.isEmpty() ? defaultList : list;
    }

    public static <V> boolean isEmpty(Collection<V> list) {
        return list == null || list.isEmpty();
    }
}
