package com.example;

/**
 * java测试类
 */
public class Test {
    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String args[]) {
        //MD5
//        try {
//            System.out.print(MD5Util.md5Digest("123")+"----"+MD5Util.md5Digest("123").length());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        //加密与解密
        System.out.println("---------加密与解密--------");
        String m = EncryptionTools.encryption(2, "8");
        System.out.println(m);
        String num = EncryptionTools.deciphering(2, m);
        System.out.println(num);
    }
}
