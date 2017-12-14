package com.taotao.sso.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.dao.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;

import co.taotao.common.pojo.TaotaoResult;
@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper mapper;
	@Override
	public TaotaoResult checkData(String param, int type) {
		TbUserExample example = new TbUserExample();
		Criteria createCriteria = example.createCriteria();
		//1、2、3分别代表username、phone、email
		if(1 == type){
			createCriteria.andUsernameEqualTo(param);
		}else if(2 == type){
			createCriteria.andPhoneEqualTo(param);
		}else if (3 == type){
			createCriteria.andEmailEqualTo(param);
		}
		List<TbUser> list = mapper.selectByExample(example);
		if(list !=null && !list.isEmpty()){
			return TaotaoResult.ok(true);
		}
		return TaotaoResult.ok(false);
	}
	@Override
	public TaotaoResult register(TbUser user) {
		if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return TaotaoResult.build(400, "bunengweikong");
		}
		TaotaoResult result = checkData(user.getUsername(), 1);
		if(!(boolean) result.getData()){
			TaotaoResult.build(400, "chongfu");
		}
		if (user.getPhone() != null) {
			result = checkData(user.getPhone(), 2);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "手机号重复");
			}
		}
		//校验手机号
		if (user.getEmail() != null) {
			result = checkData(user.getEmail(), 3);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "邮箱重复");
			}
		}
		user.setCreated(new Date());
		user.setUpdated(new Date());
		mapper.insert(user);
		return TaotaoResult.ok();
	}

	

}
