package com.taotao.sso.service;

import com.taotao.pojo.TbUser;

import co.taotao.common.pojo.TaotaoResult;

public interface RegisterService {

	TaotaoResult checkData(String param, int type);
	TaotaoResult register(TbUser user);
}
