package com.atm.test.image;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.user.UserDAO;
import com.atm.model.user.User;
import com.atm.util.Application;
import com.atm.util.ImageUtil;

public class ImageUtilTest implements Application{

	private static final Logger log = LoggerFactory.getLogger(ImageUtilTest.class);
	
	private UserDAO userDAO = (UserDAO)context.getBean("UserDAOImpl");
	
	private ImageUtil imageUtil = new ImageUtil();
	@Test
	public void test() {
	}

	@Test
	public void tesGetUserHead(){
		List<User> users = (List<User>)userDAO.findAll();
		List<String> headPaths = new ArrayList<String>();
		List<String> smallHeadPaths = new ArrayList<String>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			headPaths.add(imageUtil.getUserHeadPath(user.getUserId()));
			smallHeadPaths.add(imageUtil.getUserSmallHeadPath(user.getUserId()));
			log.info("userId:" + user.getUserId());
			log.info("headPath:" + headPaths.get(i));
			log.info("smallHeadPath:" + smallHeadPaths.get(i));
		}
		assertEquals("user", "user");
	}
}
