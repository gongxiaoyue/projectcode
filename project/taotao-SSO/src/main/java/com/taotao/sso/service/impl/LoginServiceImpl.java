package com.taotao.sso.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.dao.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.LoginService;

import co.taotao.common.pojo.CartItem;
import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.CookieUtils;
import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	private TbUserMapper mapper;
	@Autowired
	private JedisClient client;
	@Value("${REDIS_SESSION_KEY}")
	private String REDIS_SESSION_KEY;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsernameEqualTo(username);
		List<TbUser> list = mapper.selectByExample(example);
		if(list == null || list.isEmpty()){
			return TaotaoResult.build(400, "bucunzai");
		}
		TbUser user = list.get(0);
		if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
			return TaotaoResult.build(400, "mimacuowu");
		}
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		String cookieValue = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(cookieValue != null && !"".equals(cookieValue)){
			CombineCart(cookieValue,user.getId());
		}
		client.set(REDIS_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
		client.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		return TaotaoResult.ok(token);
	}
	
	private void CombineCart(String cookieValue, Long id) {
		List<CartItem> cartList = JsonUtils.jsonToList(cookieValue, CartItem.class);
		String redisList = client.get(String.valueOf(id));
		List<CartItem> list = new ArrayList<>();
		if(!StringUtils.isBlank(redisList)){
			List<CartItem> redisCart = JsonUtils.jsonToList(redisList, CartItem.class);
			for (CartItem cartItem : cartList) {
				boolean flag = false;
				for (CartItem c : redisCart) {
					if(cartItem.getId().equals(c.getId())){
						c.setNum(c.getNum()+cartItem.getNum());
						flag = true;
					}
					list.add(c);
				}
				if(!flag){
					list.add(cartItem);
				}
			}
			client.set(String.valueOf(id), JsonUtils.objectToJson(list));
		}else{
			client.set(String.valueOf(id), JsonUtils.objectToJson(cartList));
		}
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		// TODO Auto-generated method stub
		String string = client.get(REDIS_SESSION_KEY+":"+token);
		if(StringUtils.isBlank(string)){
			return TaotaoResult.build(400, "guoqi");
		}
		TbUser jsonToPojo = JsonUtils.jsonToPojo(string, TbUser.class);
		client.expire(REDIS_SESSION_KEY+":"+token, SESSION_EXPIRE);
		return TaotaoResult.ok(jsonToPojo);
	}

}
