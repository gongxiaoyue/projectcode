package com.taotao.portal.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserServic;

public class LoginInterceptor implements HandlerInterceptor {

	@Autowired 
	private UserServic userService;
	@Value("SSO_LOGIN_URL")
	private String SSO_LOGIN_URL;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		TbUser userByToken = userService.getUserByToken(request, response);
		if(userByToken == null){
			response.sendRedirect(SSO_LOGIN_URL+"?RediretURL_"+request.getRequestURL());
			return false;
		}
		request.setAttribute("user", userByToken);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
