package com.jiuyi.qujiuyi.common.util;

import java.util.Random;

/**
 * @description 字符串工具类
 * @author zhb
 * @createTime 2015年12月18日
 */
public class StringUtil {
    private final static Random random = new Random();

    /**
     * @description 判断字符串不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * @description 判断字符串为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * @description 获取唯一值
     * @return
     */
    public static String getUniqueSn() {
        return System.currentTimeMillis() + "" + random.nextInt(9) + random.nextInt(9);
    }
}