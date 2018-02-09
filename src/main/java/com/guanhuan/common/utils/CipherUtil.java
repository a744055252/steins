package com.guanhuan.common.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 加密工具类
 * 
 * @author guanhuan-li
 * @since  2017年8月17日
 *
 */
public class CipherUtil {
	
	/**
	 * MD5加密
	 * @return String
	 */
	public static String makeMD5(String password) {
		MessageDigest md;
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(password.getBytes());
			// digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return password;
	}
	
	public static void main(String[] args) {
		String password = "1111111";
		String result = BCrypt.hashpw(password, BCrypt.gensalt());
		System.err.println(result);
		String result1 = BCrypt.hashpw(password, BCrypt.gensalt(10));
		System.err.println(result1);
		
		String candidate = "1111111";
		if(BCrypt.checkpw(candidate, result)) {
			System.out.println("success!");
		} else {
			System.out.println("error!");
		}
	}
}
