package com.guanhuan.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取日期工具
 * 
 * @author guanhuan-li
 * @email liguanhuan_a@163.com
 * @date 2017年8月16日
 *
 */
public class DateUtil {
	
	/**
	 * 得到一个当前日期
	 * 格式为yyyy-MM-dd HH:mm:ss
	 * YMDHMS 分别代表 年月日 时分秒
	 * 
	 * @return
	 */
	public static String getCurrentYMDHMSDate() {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(now);
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.getCurrentYMDHMSDate());
	}
}
