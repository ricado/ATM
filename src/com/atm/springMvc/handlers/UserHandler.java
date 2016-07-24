package com.atm.springMvc.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atm.chat.nio.server.util.Config;
import com.atm.dao.FeedbackDAO;
import com.atm.dao.user.StudentDAO;
import com.atm.dao.user.TeacherDAO;
import com.atm.dao.user.UserDAO;
import com.atm.model.Feedback;
import com.atm.model.user.Student;
import com.atm.model.user.Teacher;
import com.atm.model.user.User;
import com.atm.service.user.UserService;
import com.atm.util.JsonUtil;
import com.atm.util.TimeUtil;

@RequestMapping("/user")
@Controller
public class UserHandler {

	private static Logger log = LoggerFactory.getLogger("UserHandler");
	@Autowired
	private UserDAO userDao;
	@Autowired
	private StudentDAO studentDao;
	@Autowired
	private TeacherDAO teacherDao;
	@Autowired
	private FeedbackDAO feedbackDao;

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param pwdOrEmail
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "/changePassword.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String changePwd(User user) {
		if (userDao.updateByUser(user) > 0) {
			return Config.SUCCESS + "";
		} else {
			return Config.FAILED + "";
		}
	}

	/**
	 * 修改邮箱
	 * 
	 * @param userId
	 * @param pwdOrEmail
	 * @param flag
	 * @return
	 */
	@RequestMapping(value = "/changeEmail.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String changeEmail(String userId, String email) {

		UserService userService = new UserService();
		String str = userService.findByEmail(email);
		if (str.equals("")) {
			log.info("邮箱可用");
		} else {
			log.info("邮箱已经被使用");
			return Config.USED + "";
		}

		try {
			Student student = studentDao.findById(userId);
			if (student == null) {
				log.info("修改老师邮箱");
				Teacher teacher = teacherDao.findById(userId);
				teacher.setEmail(email);
				teacherDao.saveOrUpdate(teacher);
			} else {
				log.info("修改学生邮箱");
				student.setEmail(email);
				studentDao.saveOrUpdate(student);
			}
			return Config.SUCCESS + "";
		} catch (Exception e) {
			return Config.FAILED + "";
		}
	}

	/**
	 * 添加反馈
	 * 
	 * @param feedback
	 * @author ye
	 * @data 2016-7-18
	 * @return
	 */
	@RequestMapping(value = "/feedback.do", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String addFeedback(Feedback feedback) {
		try {
			log.info("userId:" + feedback.getUserId() + ",content:"
					+ feedback.getFeeContent());
			feedback.setFeeTime(TimeUtil.getTimestamp());
			feedbackDao.save(feedback);
			return Config.SUCCESS + "";
		} catch (Exception e) {
			log.error(e.getMessage());
			return Config.FAILED + "";
		}
	}
}
