package com.taotao.portal.service.Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserServic;

import co.taotao.common.pojo.TaotaoResult;
import co.taotao.common.pojo.utils.CookieUtils;
import co.taotao.common.pojo.utils.HttpClientUtil;
import co.taotao.common.pojo.utils.JsonUtils;

public class UserServiceImpl implements UserServic {

	@Value("${SSO_BASE_URL}")
	private String SSO_BASE_URL;
	@Value("${SSO_USER_TOKEN_SERVICE}")
	private String SSO_USER_TOKEN_SERVICE;
	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			if(StringUtils.isBlank(token)){
				return null;
			}
			String doGet = HttpClientUtil.doGet(SSO_BASE_URL+SSO_USER_TOKEN_SERVICE +token);
			TaotaoResult format = TaotaoResult.format(doGet);
			if(format.getStatus() != 200){
				return null;
			}
			TaotaoResult formatToPojo = TaotaoResult.formatToPojo(doGet, TbUser.class);
			TbUser data = (TbUser) formatToPojo.getData();
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
