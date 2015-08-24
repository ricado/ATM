package com.atm.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.atm.dao.impl.user.UserDAOImpl;
import com.atm.dao.user.UserDAO;
import com.atm.model.user.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name="loginServlet",urlPatterns={"/loginServlet"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		UserDAO dao = UserDAOImpl.getFromApplicationContext();
		User user =(User)request.getAttribute("user");
		String json = (String)request.getAttribute("json");
		/*JSONArray jsonArray = new JSONArray();
		JSONObject  dataJson=n
		
        JSONObject jsonObject = new JSONObject(json);
		dataJson.get(key)*/
		
		request.setAttribute("userId", user.getUserId());
		if(!dao.findByExample(user).isEmpty()){
			response.sendRedirect("welcome.jsp");
		}else{
			response.sendRedirect("error.jsp");
		}
	}
}