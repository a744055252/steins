package com.guanhuan.common.redis;

import redis.clients.jedis.Jedis;

/**
 * Redis 工具类
 * 
 * @author guanhuan-li
 * @email liguanhuan_a@163.com
 * @date 2017年8月11日
 *
 */
public class RedisUtils {
	
	//private static final transient Log log = LogFactory.getLog(RedisUtils.class);
	
	private static final String REDIS_IP;
	private static final String REDIS_SUCCESS;

	private static final Jedis jedis;
	
	
	static {
		REDIS_IP = "127.0.0.1";
		jedis = new Jedis(REDIS_IP);
		REDIS_SUCCESS = "PONG";
		if(REDIS_SUCCESS.equals(jedis.ping())) {
			System.out.println("Redis running! connect success!");
		}
		else {
			System.out.println("Redis error! ");
		}
	}
	
	public static String get(String key) {
		return jedis.get(key);
	}
	
	public static String set(String key, String value) {
		return jedis.set(key, value);
	}
}
