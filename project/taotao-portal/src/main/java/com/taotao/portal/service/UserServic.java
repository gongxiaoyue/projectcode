package com.taotao.portal.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.pojo.TbUser;

public interface UserServic {
	TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response);
}
