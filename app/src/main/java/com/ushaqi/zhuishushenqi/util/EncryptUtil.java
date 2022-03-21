package com.ushaqi.zhuishushenqi.util;

import java.security.MessageDigest;

/**
 * 加密
 * Created by ccheng on 7/3/15.
 */
public class EncryptUtil {
    public static byte[] md5(String string) {
        try {
            byte[] hash;
            try {
                hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            } catch (Exception e) {
                throw new RuntimeException("Huh, MD5 should be supported?", e);
            }

            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString().getBytes();
        } catch (Exception e) {
            System.out.println("Md5 parse failded");
            return string.getBytes();
        }
    }
}
