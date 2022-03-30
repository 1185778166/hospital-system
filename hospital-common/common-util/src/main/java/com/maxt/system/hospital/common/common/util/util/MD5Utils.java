package com.maxt.system.hospital.common.common.util.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Maxt
 * @Date 2022/3/23 下午2:05
 * @Version 1.0
 * @Description  MD5加密工具类
 */
public class MD5Utils {
    public static String encrypt(String strSrc){
        try {
            char hexChars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] bytes = strSrc.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(bytes);
            bytes = md5.digest();
            int length = bytes.length;
            char[] chars = new char[length * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" +e);
        }
    }
}
