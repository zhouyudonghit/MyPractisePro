package com.example.localuser.retrofittest.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static int LOCAL_SAVED_HISTORY_DAYS = 31;
    public static String DATE_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_PATTERN_2 = "yyyy-MM-dd";//运动数据的后台日期格式
    public static String DATE_PATTERN_3 = "yyyy-MM";//跑步记录的后台日期格式
    public static String DATE_PATTERN_4 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static String DATE_PATTERN_5 = "yyyy年MM月";

    public static String format(long timestamp, String pattern) {
        Date date = new Date(timestamp);
        return dateToString(date, pattern);
    }

    public static String dateToString(Date date, String pattern) {
        if (null != date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        } else {
            return "";
        }
    }


    public static Date stringToDate(String dateStr, String pattern)
    {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = formatter.parse(dateStr);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某个
     * @param
     * @return
     */
    public static int getMonthDay(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthDay(long millionSecond)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date(millionSecond));
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentMonthDay()
    {
        return getMonthDay(new Date());
    }

    /**
     * 计算历史记录的最早时间点
     * 计算方法，比如现在是2018-10-17- 10:12:39，获取31天的最早时间，是今天算一天，然后往前数30天，并且从0点开始。
     * 这个时间算法要跟同步的时间取值保持一致
     * @return
     */
    public static long getEndline()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis()-(LOCAL_SAVED_HISTORY_DAYS-1)*24L*3600*1000;
    }

    /**
     * 获取几天前的凌晨时间点
     * @param daycount daycount >= 1
     * @return
     */
    public static long getEndlineByDayCount(int daycount)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis()-(daycount-1)*24L*3600*1000;
    }

    /**
     * 计算历史记录的最早时间点,针对年表
     * 计算方法，比如现在是2018-10-17- 10:12:39，获取2018-10-01 00:00:00的时间
     * @return
     */
    public static long getEndlineForYearData()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        return  calendar.getTimeInMillis();
    }

    public static Date clearDateForDayData(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date clearDateForWeekMonthData(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date clearDateForYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return new Date(calendar.getTimeInMillis());
    }

    //24小时制：0-23
    public static int getDateHour(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isSameDay(Date date1, Date date2)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.clear();
        calendar1.setTime(date1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.clear();
        calendar2.setTime(date2);

        if(calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
            && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
            && calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH))
        {
            return true;
        }
        return false;
    }

    public static long getCurrentTimeMillis()
    {
        return new Date().getTime();
    }
}
