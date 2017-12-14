package com.taotao.rest.service;

import co.taotao.common.pojo.TaotaoResult;

public interface RedisSyncService {

	TaotaoResult syncContent(long cid);
}
