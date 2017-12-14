package com.taotao.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.sso.service.LoginOut;

import co.taotao.common.pojo.TaotaoResult;

@Controller
public class LoginOutController {
	
	@Autowired
	private LoginOut loginOut;
	
	@RequestMapping("/loginout/{token}")
	@ResponseBody
	public TaotaoResult loginOut(@PathVariable String token){
		try {
			TaotaoResult result = loginOut.loginOut(token);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
