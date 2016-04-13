package com.jiuyi.qujiuyi.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @description Util
 * @author zhb
 * @createTime 2016年4月13日
 */
public class Util {
    /**
     * @description 获取一周开始时间
     * @return
     * @throws ParseException
     */
    public static Date getStartTimeOfWeek() throws ParseException {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        long time = System.currentTimeMillis();
        if (day > 1) {
            time = time - (day - 2) * 24 * 60 * 60 * 1000L;
        } else {
            time = time - 6 * 24 * 60 * 60 * 1000L;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.parse(sdf.format(date));
        return date;
    }

    /**
     * @description 获取一周结束时间
     * @return
     * @throws ParseException
     */
    public static Date getEndTimeOfWeek() throws ParseException {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);
        long time = System.currentTimeMillis();
        if (day > 1) {
            time = time + (8 - day) * 24 * 60 * 60 * 1000L;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = sdf2.parse(sdf1.format(date) + " 23:59:59");
        return date;
    }
}