package com.neueda.assignment.shortenurlapi.common;

/**
 * Provides encoding and decoding
 */
public class ConversionUtils {
    private static final String ALLOWED_STRING_COMBINATIONS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int NUMBER_OF_CHARS = ALLOWED_STRING_COMBINATIONS.length();// total 62

    public static String decode(Long num) {
        StringBuilder str = new StringBuilder();
        while (num > 0) {
            str.insert(0, ALLOWED_STRING_COMBINATIONS.charAt((int) (num % NUMBER_OF_CHARS)));
            num = num / NUMBER_OF_CHARS;
        }
        return str.toString();
    }

    public static Long encode(String str) {
        Long num = 0L;
        for (int i = 0; i < str.length(); i++) {
            num = num * NUMBER_OF_CHARS + ALLOWED_STRING_COMBINATIONS.indexOf(str.charAt(i));
        }
        return num;
    }

    private ConversionUtils() {}

}
