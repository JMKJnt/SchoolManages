package com.cn.common.filter;

import com.cn.common.Utils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 跨域支持过滤器，在HTTP响应中增加一些头信息
 * @author songzhili
 * 2016年7月1日上午9:04:29
 */
public class CrossDomainFilter implements Filter {
	
	    public void init(FilterConfig filterConfig) throws ServletException {
	    }

	    /**
	     * 跨域支持配置
	     * @param servletRequest
	     * @param servletResponse
	     * @param filterChain
	     * @throws IOException
	     * @throws ServletException
	     */
	public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
		HttpServletResponse response = (HttpServletResponse)servletResponse;
		request.setCharacterEncoding("UTF-8");
		String origin = request.getHeader("Origin");
		if(!Utils.isEmpty(origin)&& origin.contains(".")){
			response.setHeader("Access-Control-Allow-Origin", origin);
		}else{
			response.setHeader("Access-Control-Allow-Origin", "*");
		}
		response.setHeader("Access-Control-Allow-Methods",
				"GET,POST,PUT,DELETE,OPTIONS");
		response.setHeader("Access-Control-Allow-Headers",
				"Content-Type,userName,indicate,certificateId,secret,x-requested-with");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Max-Age", "17280000");
		filterChain.doFilter(request, response);
	}

	public void destroy() {

	}

}
