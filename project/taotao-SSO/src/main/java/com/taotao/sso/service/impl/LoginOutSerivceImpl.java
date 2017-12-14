package com.taotao.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.LoginOut;

import co.taotao.common.pojo.TaotaoResult;
@Service
public class LoginOutSerivceImpl implements LoginOut{

	@Autowired
	private JedisClient client;
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Override
	public TaotaoResult loginOut(String token) {
		// TODO Auto-generated method stub
		Long del = client.del(REDIS_SESSION_KEY+":"+token);
		if(del.equals(0)){
			return TaotaoResult.ok();
		}
		return TaotaoResult.build(400, "shibai");
	}

}
