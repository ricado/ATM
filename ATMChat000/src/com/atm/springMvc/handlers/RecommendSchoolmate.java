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
 * �Ƽ�У��
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
	 * �Ƽ�У��
	 * 
	 * @param recommendSm
	 *            ���� �Ƽ�У�ѱ��� ����name ��ѧʱ��time �Ƽ����� reason
	 */
	@ResponseBody
	@RequestMapping(value = "/recommend.do", produces = "text/plain;charset=UTF-8")
	public String recommendSchoolmate(RecommendSm recommendSm) {
		log.info("--�Ƽ�У��-name:" + recommendSm.getName() + ",time:"
				+ recommendSm.getTime() + ",reason:" + recommendSm.getReason());
		try {
			recommendSmDAO.save(recommendSm);
			return "{tip:'success'}";
		} catch (Exception e) {
			return "{tip:'error'}";
		}
	}

	/**
	 * �Ƽ�У��
	 * 
	 * @param recommendSm
	 *            ���� �Ƽ�У�ѱ��� ����name ��ѧʱ��time �Ƽ����� reason
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
		log.info("�Ƽ�У��---" + jsonArray.toString());
		JSONObject jsonObject = (JSONObject) jsonArray.get(0);
		String name = jsonObject.getString("name");
		String time = jsonObject.getString("time");
		String reason = jsonObject.getString("reason");
		log.info("--�Ƽ�У��--: name:" + name + ",time:" + time + ",reason:"
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
		RecommendSm recommendSm = new RecommendSm("Ҳ��", "2019", "aaaa");
		log.info("--�Ƽ�У��-name:" + recommendSm.getName() + ",time:"
				+ recommendSm.getTime() + ",reason:" + recommendSm.getReason());
		try {
			recommendSmDAO.save(recommendSm);
			return "{\"tip\":\"success\"}";
		} catch (Exception e) {
			return "{\"tip\":\"error\"}";
		}
	}
}