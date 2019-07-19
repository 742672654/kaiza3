package com.cz.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间计算
 * @author wy
 */
public class TimeUtil {

	private static final SimpleDateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_TIME_SHORT = new SimpleDateFormat("MM-dd HH:mm");
	private static final SimpleDateFormat DATE_TIME_LONG = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss");
	private static final SimpleDateFormat WEEK = new SimpleDateFormat("EEEE");
	

	/**
	 *返回当前时间戳
	 */
	public static long getCurrentTime() {
		
		return new Date().getTime();
	}
	
	/**
	 *获取当前时间
	 *格式：HH:mm:ss
	 */
	public static String getTime() {
		 
		return TIME.format(new Date());
	}
	/**
	 *截取当前时间
	 *格式：HH:mm:ss
	 */
	public static String getTime(Date date) {
		 
		return TIME.format( date );
	}
	/**
	 *获取当前日期
	 *格式：yyyy-MM-dd
	 */
	public static String getDate() {
		 
		return DATE.format(new Date());
	}
	/**
	 *截取当前日期
	 *格式：yyyy-MM-dd
	 */
	public static String getDate(Date date) {
		 
		return DATE.format( date );
	}
	/**
	 *获取当前日期时间
	 *格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateTime() {
	 
		return DATE_TIME.format(new Date());
	}

    /**
     * 获取当前日期时间Short
     * 格式：MM-dd HH:mm
     */
    public static String getDateTimeShort() {

        return DATE_TIME_SHORT.format(new Date());
    }

	/**
	 * 获取当前日期时间Long
	 * 格式：yyyyMMddHHmmss
	 */
	public static String getDateTimeLong() {

		return DATE_TIME_LONG.format(new Date());
	}

	/**
	 *获取当前星期
	 */
	public static String getWeek() {

		return WEEK.format( new Date() );
	}
	/**
	 *获取当前月份
	 */
	public static int getMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	/**
	 *获取当年指定月份的第一天
	 */
	public final static String currentFirstTime(int month) {

		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		String first = DATE_TIME.format(c.getTime());
		return first;
	}

	/**
	 *获取当年指定月份的最后一天
	 */
	public final static String currentLastTime(int month) {
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.MONTH, month - 1);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置为本月最后一天
		ca.set(Calendar.HOUR_OF_DAY, 23);
		ca.set(Calendar.SECOND, 59);
		ca.set(Calendar.MINUTE, 59);
		String last = DATE_TIME.format(ca.getTime());
		return last;
	}

	/**
	 *获取本周第一天
	 */
	public static String currentFirstWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MINUTE, 0);
		String fitwek = DATE_TIME.format(c.getTime());
		return fitwek;
	}

	/**
	 *获取本周最后一天
	 */
	public static String currentLastWeek() {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		c.add(Calendar.DATE, -day_of_week + 7);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MINUTE, 59);
		String latwek = DATE_TIME.format(c.getTime());
		return latwek;
	}

	


	/**
	 *返回指定日期是星期几
	 */
	public static String dayforweek(String time) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "星期一");
		map.put(2, "星期二");
		map.put(3, "星期三");
		map.put(4, "星期四");
		map.put(5, "星期五");
		map.put(6, "星期六");
		map.put(7, "星期日");

		return map.get(dayForWeek(time));
	}
	/**
	 *返回指定日期是星期几
	 */
	public static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {

			c.setTime(new Date());
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	public static String dayforweek(Date time) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "星期一");
		map.put(2, "星期二");
		map.put(3, "星期三");
		map.put(4, "星期四");
		map.put(5, "星期五");
		map.put(6, "星期六");
		map.put(7, "星期日");

		return map.get(dayForWeek(time));
	}
	/**
	 *返回指定日期是星期几
	 */
	public static int dayForWeek(Date pTime) {
 
		Calendar c = Calendar.getInstance();
		  		 c.setTime( (pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	
	/**
	 *现在的时间加几天
	 */
	public static Date dateAddDay(int day) {

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE, day);// num为增加的天数，可以改变的
		return ca.getTime();
	}

	
	/**
	 *现在的时间加几分钟
	 */
	public static Date dateAddMin(int min) {

		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MINUTE, min);// num为增加的分钟，可以改变的
		return ca.getTime();
	}
	
	/**
	 *两个时间相差多少
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - nowDate.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    // 计算差多少秒//输出结果
	    // long sec = diff % nd % nh % nm / ns;
        return (day == 0 ? "" : day + "天") + (hour == 0 ? "" : hour + "时") + min + "分";
	}
	
	
	/**
	 *两个时间相差多少分钟
	 */
	public static long getDatePoorMin(Date endDate, Date nowDate) {
		 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - nowDate.getTime();
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    return  min;
	}
}
