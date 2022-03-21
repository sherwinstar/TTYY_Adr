package com.ushaqi.zhuishushenqi.util;

import android.text.TextUtils;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 *
 * @author XuShaojie
 * @Date 2013-09-09
 */
public class StringUtils {

    public static final String EXTRA_POST_LIST_BOOK_ID = "book_post_list_bookId";
    public static final String EXTRA_POST_LIST_BOOK_TITLE = "book_post_list_bookTitle";
    public static final String EXTRA_POST_CATEGORY = "add_post_category";
    public static final String EXTRA_POST_MODE = "add_post_mode";
    public static final String EXTRA_POST_FROM_READER = "book_post_list_from_reader";
    public static final String EXTRA_POST_FROM_DISCUSS = "extra_post_from_discuss";
    public static final String EXTRA_POST_BLOCK_NAME = "book_post_block_name";

    public static final String EXTRA_ADD_VOTE_TITLE = "add_vote_title";
    public static final String EXTRA_ADD_VOTE_DESC = "add_vote_desc";

    // 新话题、新消息请求时间
    public static final String PREF_NEW_IMP_NOTIF_TIME = "pref_new_imp_notif_time";
    public static final String PREF_NEW_UNIMP_NOTIF_TIME = "pref_new_unimp_notif_time";


    private StringUtils() {
    }

    /**
     * 判断给定字符串是否空(包括null，空字符串、或由空格、制表符、回车符、换行符组成的字符串)
     */
    public static boolean isEmpty(String input) {
        if (input == null || input.length() == 0) {
            return true;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 字符串转整数
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defValue;
    }

    /**
     * 字符串转整数
     *
     * @return 转换异常返回0
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * 字符串转long类型
     *
     * @return 转换异常返回 0
     */
    public static long toLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isASCII(String name) {
        if (name == null) {
            return true;
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c > 0xff || c < 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isLogFile(String name) {
        if (name == null) {
            return true;
        }
        String lowerName = name.toLowerCase();
        if (lowerName.contains("log") || lowerName.contains("debug")
                || lowerName.contains("jason") || lowerName.contains("sig")
                || containDateString(name)) {
            return true;
        }
        return false;
    }

    public static boolean isNoLogFile(String lowerName) {
        if (lowerName == null || lowerName.length() == 0) {
            return true;
        }
        return !lowerName.contains("log") && !lowerName.contains("Log") && !lowerName.contains("LOG") && !lowerName.contains("LOG") && !lowerName.contains("debug")
                && !lowerName.contains("Debug") && !lowerName.contains("DEBUG") && !lowerName.contains("jason") && !lowerName.contains("sig");
    }

    private static final String mRegex = "^.*[0-9]+(-|/| )?[0-9]+(-|/| )?[0-9]+.*$";

    public static boolean containDateString(String name) {
        return Pattern.compile(mRegex).matcher(name).matches();
    }

    public static String formatWordCount(int wordCount) {
        if (wordCount / 10000 > 0) {
            return wordCount / 10000 + "万";
        } else if (wordCount / 1000 > 0) {
            return wordCount / 1000 + "千";
        } else if (wordCount / 100 > 0) {
            return wordCount / 100 + "百";
        } else {
            return String.valueOf(wordCount);
        }
    }

    public static String formatPersonCount(int personCount) {
        if (personCount < 10000) {
            return personCount + "";
        } else {
            double n = (double) personCount / 10000;
            return new DecimalFormat(".00").format(n) + "万";
        }
    }

    public static String formatQuestionCount(int questionCount) {
        if (questionCount < 100000) {
            return questionCount + "";
        } else {
            double n = (double) questionCount / 100000;
            return new DecimalFormat(".00").format(n) + "万";
        }
    }

    /**
     * 取整数部分
     *
     * @param number
     * @return
     */
    public static String formatInteger(final float number) {
        return new DecimalFormat("##0").format(number);
    }

    /**
     * 格式化为2个小数点
     */
    public static String formatFloat(float number) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    public static String toJson(String[] tags) {
        if (tags == null) {
            return "";
        }
        JSONArray jsonArray = new JSONArray();
        for (String tag : tags) {
            jsonArray.put(tag);
        }
        return jsonArray.toString();
    }

    /**
     * 过滤掉字符串中的空格、回车、换行符、制表符
     */

//    public static String replaceBlank(String str) {
//        String dest = "";
//        if (str != null) {
//            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//            Matcher m = p.matcher(str);
//            dest = m.replaceAll("");
//        }
//        return dest;
//    }

    public static String replaceBlankWhenStringComplex(String str) {
        if (str == null) {
            return null;
        }
        String s = str.replace("\n", "")
                .replace("\r", "")
                .replace("\t", "")
                .replace(" ", "");
        return s;
    }

    /**
     * 万（11000 => 1.1万）
     *
     * @param count
     * @return
     */
    public static String formatTenThousand(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        } else {
            float newCount = (float) count / 10000;
            return (newCount + "万");
        }
    }

    /**
     * 保留万位后一位小数
     */
    public static String remainTenThousandDecimal(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        } else {
            double newCount = (double) count / 10000;
            return new DecimalFormat(".0").format(newCount) + "万";

        }
    }

    /**
     * 保留万位后一位小数
     */
    public static String remainTenThousandInteger(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        } else if (count > 100000000) {
            return new DecimalFormat(".0").format((count * 1.0d / 100000000)) + "亿";
        } else {
            return new DecimalFormat(".0").format((count * 1.0d / 10000)) + "万";

        }
    }

    /**
     * 保留万位后一位小数
     */
    public static String remainTenThousandLong(long count) {
        if (count < 10000) {
            return String.valueOf(count);
        } else if (count > 100000000) {
            return new DecimalFormat(".0").format((count * 1.0d / 100000000)) + "亿";
        } else {
            return new DecimalFormat(".0").format((count * 1.0d / 10000)) + "万";

        }
    }

//    public static String getUTCTime(String inTime) {
//        String time;
//        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);//输入的被转化的时间格式
//        try {
//            Date date = dff.parse(inTime);
//            return DateHelper.getRelativeTime(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            time = "刚刚";
//        } catch (Exception e) {
//            time = "刚刚";
//        }
//        return time;
//    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断手机号
     *
     * @param mobiles
     * @return
     */
//    public static boolean isMobileNO(String mobiles) {
//        String telRegex = "^((\\+86)|(86))?[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[35789]"代表第二位可以为3、5、8,7,9中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
//        if (TextUtils.isEmpty(mobiles))
//            return false;
//        else
//            return mobiles.matches(telRegex);
//    }

    /**
     * @param numerator   分子
     * @param denominator 分母
     * @return
     */
    public static String formatPercentage(int numerator, int denominator) {
        try {
            BigDecimal b = new BigDecimal((Float.parseFloat("" + numerator) / Float.parseFloat("" + denominator)) * 100);
            float finalFloatValue = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
            return finalFloatValue + "%";
        } catch (Exception e) {
        }

        return "";
    }
}
