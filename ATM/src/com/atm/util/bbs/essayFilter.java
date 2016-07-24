package com.atm.util.bbs;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.atm.model.define.bbs.EssayDetailView;
import com.atm.model.user.UserInfo;

/**
 * @TODO��
 * @fileName : com.atm.util.essayFilter.java
 * date | author | version |   
 * 2015��8��4�� | Jiong | 1.0 |
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
	       log.error("���ӣ�"+url);
	       Cookie[] cookies = request.getCookies();
	       if(cookies!=null)
			for(int i=0;i<cookies.length;i++){
				log.error("cookis:"+cookies[i].getName());
				UserInfo user = (UserInfo) request.getSession().getAttribute("user");
				if(user!=null)
				log.error("�û���"+user.getUserId());
				else{
					log.error("��cookie��δ��½");
				}
			}else{
	    	   log.error("û��Cookie");
	       }
	       if(url.equals("/essay.jsp")&&request.getAttribute("essayBean")==null){
	    	   log.debug("��������ת��action");
	    	   request.getRequestDispatcher("/essay_detail.action").forward(request, response); 
	       }else if(url.equals("/recuit.jsp")&&request.getAttribute("recuitBean")==null){
	    	   log.debug("�û�ֱ�ӷ���jsp�ļ�����������ת��action");
	    	   request.getRequestDispatcher("/recuit_detail.action").forward(request, response); 
	       }else{
	    	   chain.doFilter(request, response);
	       }
	   }
	
}

