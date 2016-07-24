package com.atm.springMvc.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.chainsaw.Main;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atm.daoDefined.RecommendSmDAO;
import com.atm.model.define.RecommendSm;
import com.atm.util.Application;
import com.atm.util.bbs.JsonUtil;

/**
 * 推荐校友
 * 
 * @author ye
 * @time 2016-03-21
 */
// "http://139.129.131.179/ATM/RecommendSm/recommendSm.do"
@RequestMapping("/RecommendSm")
@Controller
public class RecommendSchoolmate implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(RecommendSchoolmate.class);
	private RecommendSmDAO recommendSmDAO = (RecommendSmDAO) context
			.getBean("RecommendSmDAO");

	/**
	 * 推荐校友
	 * 
	 * @param recommendSm
	 *            参数 推荐校友表有 姓名name 入学时间time 推荐理由 reason
	 */
	@ResponseBody
	@RequestMapping(value = "/recommend.do", produces = "text/plain;charset=UTF-8")
	public String recommendSchoolmate(RecommendSm recommendSm) {
		log.info("--推荐校友-name:" + recommendSm.getName() + ",time:"
				+ recommendSm.getTime() + ",reason:" + recommendSm.getReason());
		try {
			recommendSmDAO.save(recommendSm);
			return "{tip:'success'}";
		} catch (Exception e) {
			return "{tip:'error'}";
		}
	}

	/**
	 * 推荐校友
	 * 
	 * @param recommendSm
	 *            参数 推荐校友表有 姓名name 入学时间time 推荐理由 reason
	 * @throws JSONException
	 * @throws IOException
	 *             "http://139.129.131.179/ATM/RecommendSm/recommendSm.do"
	 */
	@ResponseBody
	@RequestMapping(value = "/recommendSm.do", produces = "text/plain;charset=UTF-8")
	public String recommendSchoolmate(HttpServletRequest request)
			throws IOException, JSONException {
		JsonUtil jsonUtil = new JsonUtil();
		JSONArray jsonArray = jsonUtil.getJSONArray(request);
		log.info("推荐校友---" + jsonArray.toString());
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		String name = jsonObject.getString("name");
		String time = jsonObject.getString("time");
		String reason = jsonObject.getString("reason");
		log.info("--推荐校友--: name:" + name + ",time:" + time + ",reason:"
				+ reason + "---");
		RecommendSm recommendSm = null;
		try {
			recommendSm = new RecommendSm(name, time, reason);
		} catch (Exception e) {
			log.info("error");
		}
		try {
			log.info("-----------------------------------");
			recommendSmDAO.save(recommendSm);
			log.info("save success");
			return "[{\"tip\":\"success\"}]";
		} catch (Exception e) {
			log.info("error");
			return "[{\"tip\":\"error\"}]";
		}

	}

	@ResponseBody
	@RequestMapping(value = "/testRecommendSm.do", produces = "text/plain;charset=UTF-8")
	public String textRecommendSm() {
		RecommendSm recommendSm = new RecommendSm("也有", "2019", "aaaa");
		log.info("--推荐校友-name:" + recommendSm.getName() + ",time:"
				+ recommendSm.getTime() + ",reason:" + recommendSm.getReason());
		try {
			recommendSmDAO.save(recommendSm);
			return "{\"tip\":\"success\"}";
		} catch (Exception e) {
			return "{\"tip\":\"error\"}";
		}
	}
}