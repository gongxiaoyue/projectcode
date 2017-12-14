package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.portal.pojo.CartItem;

import co.taotao.common.pojo.TaotaoResult;

public interface CartService {
	TaotaoResult addCart(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
	List<CartItem> getCartItems(HttpServletRequest request);
	TaotaoResult updateCartItem(Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response);
	TaotaoResult deleteCartItem(Long itemId, String cookiekey);
}
