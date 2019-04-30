package com.liuming.sorm.utils;

/**
 * 封装了字符串常用操作
 */
public class StringUtils {
    /**
     * @param str str
     * @return 首字母大写
     */
    public static String firstChar2UpperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
