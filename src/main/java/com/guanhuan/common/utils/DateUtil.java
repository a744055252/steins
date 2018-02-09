package com.guanhuan.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取日期工具
 * 
 * @author guanhuan-li
 * @since  2017年8月16日
 *
 */
public class DateUtil {

	private static final SimpleDateFormat sdf;

	static {
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}


	/**
	 * 得到一个当前日期
	 * 格式为yyyy-MM-dd HH:mm:ss
	 * YMDHMS 分别代表 年月日 时分秒
	 * 
	 * @return String
	 */
	public static String getCurrentYMDHMSDate() {
		return getYMDHMSDate(System.currentTimeMillis());
	}

	public static String getYMDHMSDate(long time){
		Date date = new Date(time);
		return sdf.format(date);
	}

	public static long getLongDate(String timeStr){
		try {
			return sdf.parse(timeStr).getTime();
		} catch (ParseException e) {
			throw new IllegalArgumentException("日期格式错误",e);
		}
	}

	public static void main(String[] args) {
		System.out.println(getLongDate(""));
	}
}
