package com.example;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * java测试类
 */
public class Test {
    /**
     * 主方法
     * @param args
     */
    public static void main(String args[]){
        try {
            System.out.print(MD5Util.md5Digest("123")+"----"+MD5Util.md5Digest("123").length());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
