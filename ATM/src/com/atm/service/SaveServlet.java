package com.atm.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.impl.user.UserInfoDAOImpl;
import com.atm.dao.user.UserDAO;
import com.atm.dao.user.UserInfoDAO;
import com.atm.model.user.User;
import com.atm.model.user.UserInfo;

/**
 * Servlet implementation class SaveServlet
 */
@WebServlet("/SaveServlet")
public class SaveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		String name = request.getParameter("name");
		User user = new User();
		user.setUserId("10002678");
		user.setUserPwd(userPwd);
		UserInfo info = new UserInfo();
		info.setUserId(userId);
		info.setName(name);
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		UserInfoDAO infoDAO = UserInfoDAOImpl.getFromApplicationContext();
		try{
			System.out.println("========save--info===========");
			infoDAO.save(info);
			System.out.println("========save--user===========");
			dao.save(user);
			System.out.println("========success==============");
		}catch(RuntimeException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}catch(Exception e){
			System.out.println("========exception============");
		}
	}

}
