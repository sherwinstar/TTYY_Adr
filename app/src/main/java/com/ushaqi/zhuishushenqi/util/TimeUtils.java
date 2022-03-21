package com.ushaqi.zhuishushenqi.util;



import com.ushaqi.zhuishushenqi.TimeConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Andy.zhang
 * @date 2018/8/1
 */
public class TimeUtils {
    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat DATE_FORMAT_YYYYMMDD = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    private static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_HHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    private static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
    private static final SimpleDateFormat EDITOR_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    /**
     * one hour in ms
     */
    private static final int ONE_HOUR = 60 * 60 * 1000;
    /**
     * one minute in ms
     */
    private static final int ONE_MIN = 60 * 1000;
    /**
     * one second in ms
     */
    private static final int ONE_SECOND = 1000;

    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     *
     * @param date
     * @return
     */
    public static String yyyyMMdd(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMAT_YYYYMMDD.format(date);
    }



    /**
     * 字符串转换为date，失败则返回最新的date
     */
    public static Date yyyyMMddHHmm(String time) {
        try {
            return DATE_FORMAT_YYYY_MM_DD_HHMM.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * @param day
     * @return
     */
    public static long day2Millis(final int day) {
        return (long) (day * TimeConstants.DAY);
    }

    /**
     * @param hour
     * @return
     */
    public static long hour2Millis(final int hour) {
        return (long) (hour * TimeConstants.HOUR);
    }

    /**
     * @param millis
     * @return
     */
    public static int toHour(final long millis) {
        return (int) (millis / TimeConstants.HOUR);
    }

    /**
     * @param millis
     * @return
     */
    public static int toMinute(final long millis) {
        return (int) (millis / TimeConstants.MIN);
    }

    /**
     * @param millis
     * @return
     */
    public static int toDay(final long millis) {
        return (int) (millis / TimeConstants.DAY);
    }

    /**
     * get current time in seconds
     *
     * @return
     */
    public static int currentTimeSecond() {
        return (int) (System.currentTimeMillis() / TimeConstants.SEC);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(final long timeInMillis, final SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(final long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeString() {
        return getTime(currentTimeMillis());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeString(final SimpleDateFormat dateFormat) {
        return getTime(currentTimeMillis(), dateFormat);
    }

    /**
     * 格式化时间
     *
     * @param ms
     * @return HH:mm:ss
     */
    public static String getTimeFormatString(long ms) {
        StringBuilder sb = new StringBuilder();
        int hour = (int) (ms / ONE_HOUR);
        int min = (int) ((ms % ONE_HOUR) / ONE_MIN);
        int sec = (int) (ms % ONE_MIN) / ONE_SECOND;
        if (hour == 0) {
//			sb.append("00:");
        } else if (hour < 10) {
            sb.append("0").append(hour).append(":");
        } else {
            sb.append(hour).append(":");
        }
        if (min == 0) {
            sb.append("00:");
        } else if (min < 10) {
            sb.append("0").append(min).append(":");
        } else {
            sb.append(min).append(":");
        }
        if (sec == 0) {
            sb.append("00");
        } else if (sec < 10) {
            sb.append("0").append(sec);
        } else {
            sb.append(sec);
        }
        return sb.toString();
    }

    /**
     *  格式化"2019-10-16T07:42:46.882Z"类型的时间格式
     * @param date
     * @return
     */
    public static Date formatServerDate(String date) {
        try {
            return SERVER_DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  格式化"2019-10-16"类型的时间格式
     * @param date
     * @return
     */
    public static Date formatEditorDate(String date) {
        try {
            return EDITOR_DATE_FORMAT.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得当前日期的前N天
     *
     * @return
     */
    public static Date getCurDayBefore(int beforeDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - beforeDays);
        return c.getTime();
    }

    /**
     * 是否为同一天的时间戳
     * @param lastTimeMillis
     * @param curTimeMillis
     * @return
     */
    public static boolean isSameDay(final long lastTimeMillis, final long curTimeMillis) {
        Date lastDate = new Date(lastTimeMillis);
        Date nowDate = new Date(curTimeMillis);
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTime(nowDate);
        Calendar lastCalendar = Calendar.getInstance();
        lastCalendar.setTime(lastDate);
        return (nowCalendar.get(Calendar.YEAR) == lastCalendar.get(Calendar.YEAR)
                && nowCalendar.get(Calendar.MONTH) == lastCalendar.get(Calendar.MONTH)
                && nowCalendar.get(Calendar.DATE) == lastCalendar.get(Calendar.DATE));
    }

}
