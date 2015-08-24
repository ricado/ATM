package com.atm.util.bbs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atm.model.define.bbs.EssayDetailView;

/**
 * @TODO：
 * @fileName : com.atm.util.essayFilter.java
 * date | author | version |   
 * 2015年8月4日 | Jiong | 1.0 |
 */

	public class essayFilter implements Filter {
		Logger log = Logger.getLogger(getClass());
	   private FilterConfig filterConfig;
	   private FilterChain chain;
	   private HttpServletRequest request;
	   private HttpServletResponse response;
	   public void destroy() {
	       this.filterConfig = null;
	   }
	   public void init(FilterConfig filterConfig) throws ServletException {
	       this.filterConfig = filterConfig;
	    }

	   public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain chain)
	       throws IOException, ServletException {
	       this.chain = chain;
	       this.request = (HttpServletRequest) servletRequest;
	       this.response = (HttpServletResponse) servletResponse;
	       String url = request.getServletPath();
	       if (url == null)
	           url = "";
	       if(url.equals("/essay.jsp")&&request.getAttribute("essayBean")==null){
	    	   log.debug("过滤器跳转到action");
	    	   request.getRequestDispatcher("/essay_detail.action").forward(request, response); 
	       }else{
	    	   chain.doFilter(request, response);
	       }
	   }
	
}

