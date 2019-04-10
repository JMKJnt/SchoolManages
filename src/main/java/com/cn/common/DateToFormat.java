package com.cn.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * 日期处理相关操作
 * Created by cuixiaowei on 2015/12/9.
 */
public class DateToFormat {

    /**
     *
     * @param dateString 传入的时间类型字符串
     * @param dateFormat 需要转义的时间类型格式 例如：yyyy-MM-dd
     * @return
     */
    public static Date getString2Date(String dateString,String dateFormat)
    {
        String strFormat=dateFormat;
        if(dateString!=null&& dateString.length()<12)
        {
            strFormat="yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        Date date = null;
        //Date ed = null;
        try {
            date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     *
     * @param dateString 传入的时间类型字符串
     * @param dateFormat 需要转义的时间类型格式 例如：yyyy-MM-dd
     * @return
     */
    public static Date getStringToDate(String dateString,String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     *
     * @param date 传入的时间类型
     * @param dateFormat 需要转义的时间类型格式 例如：yyyy-MM-dd
     * @return
     */
    public static String date2String(Date date,String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String date_e = null;
        //Date ed = null;
        try {
            date_e = sdf.format(date);
            return date_e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /**
     * 将时间转为指定的格式
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatTime(Object date,String dateFormat)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String date_e = null;
        try {
            date_e = sdf.format(date);
            return date_e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getYesterday 返回今天23时59分59秒的日期
     *
     * @param date
     *            Date
     * @return Date 返回今天24时0分0秒的日期
     */

    public static Date getDayEnd(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    /**
     * getTodayBegin 返回今天0时0分0秒的日期
     *
     * @param date
     *            Date
     * @return Date 返回今天0时0分0秒的日期
     */
    public static Date getDayBegin(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    /**
     * getMonthBegin 返回当前月第1天0时0分0秒的日期
     *
     * @param date
     *            Date
     * @return Date 返回当前月第1天0时0分0秒的日期
     */

    public static Date getMonthBegin(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
    /**
     * 获取指定位数的随机数(纯数字)
     * @param length 随机数的位数
     * @return String
     */
    public static String getRandomNum(int length) {
        if (length <= 0) {
            length = 1;
        }
        StringBuilder res = new StringBuilder();
        Random random = new Random();
        int i = 0;
        while (i < length) {
            res.append(random.nextInt(10));
            i++;
        }
        return res.toString();
    }
    /**
     * 获取现在时间
     *
     * @return返回长时间格式 yyyy-MM-dd HH:mm:ss
     */

        public static Date getNowDateShort() {
            Date currentTime = new Date();
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            Date dateString1 = null;
            try {
                dateString1 = formatter.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            };
           return dateString1;
    }

    /**
     * 随机生成字符串
     * @param length 字符串长度
     * @return
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
