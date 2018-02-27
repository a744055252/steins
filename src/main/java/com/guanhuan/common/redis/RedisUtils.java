package com.guanhuan.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Redis 工具类
 * 
 * @author guanhuan-li
 * @since  2017年8月11日
 *
 */
public class RedisUtils {
	
	private static final String REDIS_IP;
	private static final String REDIS_SUCCESS;

	private static final Jedis jedis;
	
	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);
	
	static {
		REDIS_IP = "127.0.0.1";
		jedis = new Jedis(REDIS_IP);
		REDIS_SUCCESS = "PONG";
		if(REDIS_SUCCESS.equals(jedis.ping())) {
			logger.info("Redis running! connect success!");
		}
		else {
			logger.info("Redis error! ");
		}
	}
	
	public static String get(String key) {
		return jedis.get(key);
	}
	
	public static String set(String key, String value) {
		return jedis.set(key, value);
	}
}
