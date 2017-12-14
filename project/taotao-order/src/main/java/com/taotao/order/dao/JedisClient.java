package com.taotao.order.dao;

import java.util.Map;

public interface JedisClient {
	String get(String key);
	Map<String, String> getAll(String key);
	String set(String key, String value);
	long incr(String key);
	Long hset(String hkey, String key, String value);
	Long hset(String hkey, String key, String value, int seconds);
	String hget(String hkey, String key);
	Long del(String key);
	Long hdel(String hkey, String key);
	Long expire(String key, int second);
}
