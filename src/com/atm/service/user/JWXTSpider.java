package com.atm.service.user;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * 教务系统的爬虫 用于验证学生信息
 * 
 * @author ye
 * @dete 2016年11月13日 上午10:37:13
 */
public class JWXTSpider {

	/**
	 * 登录教务系统
	 *
	 * @author ye
	 * @dete 2016年11月13日 上午10:39:27
	 * @param number
	 * @param passwd
	 */
	private static String login(String number, String passwd) {
		String url = "http://jwxt.gduf.edu.cn/jsxsd/xk/LoginToXk";
		Map<String, String> data = new HashMap<>();
		data.put("userAccount", number);
		data.put("userPassword", passwd);
		data.put("jzmmid", "1");
		data.put("encoded", getEncoded(number, passwd));

		try {
			Response response = Jsoup.connect(url).data(data)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Accept-Encoding", "gzip, deflate").header("Upgrade-Insecure-Requests", "1")
					.header("Connection", "keep-alive").header("Referer", "http://jwxt.gduf.edu.cn/jsxsd/")
					.header("Host", "jwxt.gduf.edu.cn").header("Content-Type", "application/x-www-form-urlencoded")
					.method(Method.POST).execute();
			Map<String, String> cookies = response.cookies();
			url = "http://jwxt.gduf.edu.cn/jsxsd/framework/xsMain.jsp";
			response = Jsoup.connect(url).cookies(cookies)
					.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("User-Agent",
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Accept-Encoding", "gzip, deflate").header("Upgrade-Insecure-Requests", "1")
					.header("Connection", "keep-alive").header("Referer", "http://jwxt.gduf.edu.cn/jsxsd/")
					.header("Host", "jwxt.gduf.edu.cn").header("Content-Type", "application/x-www-form-urlencoded")
					.method(Method.GET).execute();
			Document document = response.parse();
			Element element = document.getElementById("Top1_divLoginName");
			return element.text();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取encode字段
	 *
	 * @author ye
	 * @dete 2016年11月13日 上午11:53:43
	 * @param number
	 * @param passwd
	 * @return
	 */
	private static String getEncoded(String number, String passwd) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		try {
			String jsFileName = System.getProperty("user.dir") + "/src/com/atm/service/user/test.js"; // 读取js文件
			FileReader reader = new FileReader(jsFileName); // 执行指定脚本
			engine.eval(reader);

			if (engine instanceof Invocable) {
				Invocable invocable = (Invocable) engine; // 调用encodeInp方法
				String account = (String) invocable.invokeFunction("encodeInp", number);
				String password = (String) invocable.invokeFunction("encodeInp", passwd);
				return account + "%%%" + password;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String confirmTeach(String number,String passwd) {
		String name = login(number, passwd);
		if (name.length() > 0) {			
			name = name.substring(0,name.indexOf("("));
		}
		return name;
	}
	
	public static void main(String[] args) throws ScriptException {
		System.out.println(confirmTeach("131544215", "131544215ccy"));
	}
}
