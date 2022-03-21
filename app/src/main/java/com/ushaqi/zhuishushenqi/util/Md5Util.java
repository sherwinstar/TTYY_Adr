package com.ushaqi.zhuishushenqi.util;

import java.security.MessageDigest;

/**
 * 加密
 * chengwencan
 */
public class Md5Util {
    public static byte[] md5(String string) {
        try {
            byte[] hash;
            try {
                hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
                return hash;
            } catch (Exception e) {
                throw new RuntimeException("Huh, MD5 should be supported?", e);
            }


        } catch (Exception e) {

            return string.getBytes();
        }
    }
}
