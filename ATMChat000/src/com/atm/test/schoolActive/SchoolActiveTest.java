package com.atm.test.schoolActive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.atm.dao.SchoolActiveDAO;
import com.atm.model.SchoolActive;
import com.atm.util.Application;

public class SchoolActiveTest implements Application {
	public static void main(String[] args) throws IOException,
			URISyntaxException {
		/*
		 * String content = "5月28日，“金子之光”校友报告会第二十七场在教学实验中心大楼学" + "<br><br><br>"
		 * +
		 * "<img src='blob:http://localhost:8080/f1d244e5-2022-4c0b-8f65-f674198630ce' class='content_image'>"
		 * + "safjkasf"; int index = content.indexOf("src='") + 5; int end =
		 * content.indexOf("'", index); String src = content.substring(index,
		 * end); System.out.println("src:" + src);
		 * 
		 * src =
		 * "http://image.96weixin.com/uploads/userup/image/20151017/1445045608802542.gif"
		 * ; index = src.lastIndexOf("."); String filename =
		 * src.substring(index); System.out.println("filename:10001" +
		 * filename); filename = "10001" + filename; String savePath =
		 * "f:/image/"; FileUtil.download(src, savePath, filename);
		 * System.out.println("ok");
		 */

		testFindList();
	}

	public static void testFindList() {
		SchoolActiveDAO activeDAO = (SchoolActiveDAO) context
				.getBean("SchoolActiveDAO");
		List<SchoolActive> actives = activeDAO.findList(0, 10);
		for (SchoolActive schoolActive : actives) {
			System.out.println("newsId:" + schoolActive.getNewsId());
		}
		JSONArray jsonObject = new JSONArray().fromObject(actives);
		System.out.println("json:" + jsonObject.toString());
		JSONArray jsonArray = new JSONArray();
		
		for (SchoolActive schoolActive : actives) {
			JSONObject jsonObject2 = new JSONObject();
			//id
			jsonObject2.put("newsId", schoolActive.getNewsId());
			//imagepath
			jsonObject2.put("newsId", schoolActive.getNewsId());
			jsonObject2.put("newsImagePath", schoolActive.getNewImagePath());
			jsonObject2.put("mainTitle", schoolActive.getMainTitle());
			jsonObject2.put("viceTitle", schoolActive.getViceTitle());
			jsonObject2.put("content", schoolActive.getContent());
			jsonObject2.put("viewCount", schoolActive.getReadCount());
			System.out.println("time:" + schoolActive.getTime());
			jsonArray.add(jsonObject2);
		}
	}
}