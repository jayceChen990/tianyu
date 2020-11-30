package com.tianyu.utils;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils {
	@Setter
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasCacheKey(String key) {
		try {
			final Boolean aBoolean = redisTemplate.hasKey(key);
			return aBoolean;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public Object getCache(String key) {
		return key == null ? null : redisTemplate.opsForValue().get(key);
	}

	/**
	 * 普通缓存放入
	 *
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean setCache(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 普通缓存放入,超时时间
	 *
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean setCacheTimeOut(String key, Object value, long timeout, TimeUnit unit) {
		try {
			redisTemplate.opsForValue().set(key, value, timeout, unit);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}