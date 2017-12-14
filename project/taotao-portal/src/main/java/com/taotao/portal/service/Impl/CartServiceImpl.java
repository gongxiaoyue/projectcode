package com.taotao.portal.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.order.dao.JedisClient;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.ItemService;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.CookieUtils;
import co.taotao.common.pojo.utils.JsonUtils;
@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private JedisClient client;
	@Autowired
	private ItemService itemService;
	@Value("${COOKIE_EXPIRE}")
	private int COOKIE_EXPIRE;
	@Value("${COOKIE_CARTNAME}")
	private String COOKIE_CARTNAME;
	@Override
	public TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response) {
		//获取购物车的cookie，如果没有则添加该cookie
		String cartkey = CookieUtils.getCookieValue(request, COOKIE_CARTNAME, true);
		if(cartkey==null){
			cartkey = "cart"+UUID.randomUUID();
			CookieUtils.setCookie(request, response, COOKIE_CARTNAME, cartkey, true);
		}
		String itemKey = String.valueOf(itemId);
		String hget = client.hget(cartkey, itemKey);
		CartItem cartItem = null;
		if(hget == null){
			TbItem itemById = itemService.getItemById(itemId);
			cartItem.setId(itemById.getId());
			cartItem.setNum(1);
			cartItem.setPrice(itemById.getPrice());
			cartItem.setTitle(itemById.getTitle());
			cartItem.setImage(itemById.getImage());
		}else{
			cartItem = JsonUtils.jsonToPojo(hget, CartItem.class);
			cartItem.setNum(cartItem.getNum()+1);
		}
		client.hset(cartkey, itemKey, JsonUtils.objectToJson(cartItem), 60*60);
		return TaotaoResult.ok();
	}


	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, COOKIE_CARTNAME);
		if(StringUtils.isBlank(cookieValue))
			return null;
		Map<String, String> cartMap = client.getAll(cookieValue);
		List<CartItem> cartItems = new ArrayList<CartItem>();
		for(String value : cartMap.values()){
			cartItems.add(JsonUtils.jsonToPojo(value, CartItem.class));
		}
		client.expire(cookieValue, 60*60);
		client.hset(cookieValue, "update", String.valueOf(System.currentTimeMillis()));
		return cartItems;
	}

	@Override
	public TaotaoResult updateCartItem(Long itemId, Integer num, HttpServletRequest request,
			HttpServletResponse response) {
		String cookieValue = CookieUtils.getCookieValue(request, "cartKey", true);
		if(cookieValue != null){
			String string = client.hget(cookieValue, String.valueOf(itemId));
			CartItem cartItem = JsonUtils.jsonToPojo(string, CartItem.class);
			cartItem.setNum(num);
			client.hset(cookieValue, String.valueOf(itemId), JsonUtils.objectToJson(cartItem), 60*60);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteCartItem(Long itemId, String cartkey) {
		Long itemList = client.hdel(cartkey, String.valueOf(itemId));
		return TaotaoResult.ok(itemList);
	}

}
