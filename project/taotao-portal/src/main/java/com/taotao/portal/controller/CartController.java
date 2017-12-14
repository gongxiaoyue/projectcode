package com.taotao.portal.controller;

import java.awt.Dialog.ModalExclusionType;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

import co.taotao.common.pojo.TaotaoResult;

@Controller
public class CartController {
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/cart/add/{itemId}")
	@ResponseBody
	public String addCart(@PathVariable("itemId") Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
		TaotaoResult addCart = cartService.addCart(itemId, num, request, response);
		return "cartSuccess";
	}
	
	@RequestMapping("/cart/cart")
	public String getCartItems(HttpServletRequest request, Model model){
		List<CartItem> cartItems = cartService.getCartItems(request);
		model.addAttribute("cartList", cartItems);
		return "cart";
	}
	
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public TaotaoResult updateCart(@PathVariable Long itemId, Integer num, HttpServletRequest request, HttpServletResponse response){
		TaotaoResult updateCartItem = cartService.updateCartItem(itemId, num, request, response);
		return updateCartItem;
	}
	
	@RequestMapping("/cart/delete/{itemId}")
	@ResponseBody
	public String deleteCartItem(@PathVariable("itemId") Long itemId, HttpServletRequest request,@CookieValue("cartKey") String cookiekey){
		TaotaoResult deleteCartItem = cartService.deleteCartItem(itemId, cookiekey);
		return "redirect:/cart/cart.html";
	}
}
