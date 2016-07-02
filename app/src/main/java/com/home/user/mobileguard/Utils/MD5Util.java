package com.home.user.mobileguard.Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密工具类
 * Created by user on 16-7-1.
 */
public class MD5Util {
    public static String md5Digest(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        MessageDigest md = MessageDigest.getInstance("MD5");
        //进行加密
        byte[] bytes = md.digest(password.getBytes("UTF-8"));
        for (byte b:bytes) {
            //转成16进制
            String s = Integer.toHexString(b & 0x000000ff);
            //长度为一高位补0
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s);
        }
        return sb.toString();
    }
}
