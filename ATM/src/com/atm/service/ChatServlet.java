package com.atm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atm.dao.chat.PrivateChatDAO;
import com.atm.dao.impl.chat.PrivateChatDAOImpl;
import com.atm.model.chat.PrivateChat;
import com.atm.util.HttpUtil;
import com.atm.util.JsonUtil;

/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/chatServlet")
public class ChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userReceiveId = request.getParameter("userReceiveId");
		String userSendId = request.getParameter("userSendId");
		PrivateChatDAO chatDAO = PrivateChatDAOImpl.getFromApplicationContext();
		System.out.println("send............");
		List list = chatDAO.findByReceiveAndSend("10001", "10002");  //查找出10001和10002的对话信息
		System.out.println("ok.........");
		for(int i=0;i<list.size();i++){
			PrivateChat chat = (PrivateChat)list.get(i);
			System.out.println(chat.getUserReceiveId() + ": " + chat.getSendContent());
		}
		System.out.println("===============");
		String json = JsonUtil.listToJson(list);
		request.setAttribute("json", json);
		HttpUtil.forward("chat.jsp");
	}

}
