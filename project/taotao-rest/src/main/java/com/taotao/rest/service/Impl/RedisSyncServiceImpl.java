package com.taotao.rest.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisSyncService;

import co.taotao.common.pojo.TaotaoResult;
import redis.clients.jedis.Jedis;
@Service
public class RedisSyncServiceImpl implements RedisSyncService {

	@Autowired
	private JedisClient client;
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	@Override
	public TaotaoResult syncContent(long cid) {
		// TODO Auto-generated method stub
		client.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}

}
