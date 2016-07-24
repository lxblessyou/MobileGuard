package com.example;

/**
 * Created by user on 16/7/24.
 */
public class EncryptionTools {
    public static String encryption(int offset, String num) {
        byte[] bytes = num.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ offset);
        }
        return new String(bytes);
    }

    public static String deciphering(int offset, String m) {
        byte[] bytes = m.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (bytes[i] ^ offset);
        }
        return new String(bytes);
    }
}
