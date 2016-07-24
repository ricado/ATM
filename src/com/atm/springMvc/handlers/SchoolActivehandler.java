package com.atm.springMvc.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.atm.dao.SchoolActiveDAO;
import com.atm.model.SchoolActive;
import com.atm.util.Application;
import com.atm.util.FileUtil;
import com.atm.util.ImageUtil;
import com.atm.util.Path;
import com.atm.util.TimeUtil;

/**
 * 校园动态的管理
 * 
 * @author ye
 *
 */
@RequestMapping("/schoolActive")
@Controller
public class SchoolActivehandler implements Application {
	private static final Logger log = LoggerFactory
			.getLogger(SchoolActivehandler.class);

	private SchoolActiveDAO schoolActiveDao = (SchoolActiveDAO) context
			.getBean("SchoolActiveDAO");

	/**
	 * /schoolActive/saveSchoolActive.do 添加新的校友动态新闻
	 * 1.用schoolActive一一接受jsp上传上来的属性 2.设置time,readCount等属性
	 * 3.初步保存schoolActive到数据库中 4.保存成功后 schoolActive会有 newsId
	 * 5.根据newsId用线程将上传上来的图片文件 以及 正文图片 下载到本地对应的目录中 6.再次保存修稿数据库中的信息 7.返回到
	 * schoolActive.jsp
	 * 
	 * @param schoolActive
	 * @return
	 */
	@RequestMapping(value = "/saveSchoolActive.do", produces = "text/plain;charset=UTF-8")
	private String saveSchoolActive(
			@RequestParam(value = "file") MultipartFile file,
			SchoolActive schoolActive, Map<String, Object> map) {

		// 获取纯文本内容
		// String describe = schoolActive.getDescribe();
		// describe = describe.trim();
		// if(describe.length()>50){
		// describe = describe.substring(0, 50);
		// }
		// 内容
		String content = schoolActive.getContent();
		// 设置时间
		schoolActive.setTime(TimeUtil.getTimestamp());
		schoolActive.setReadCount(0);
		// schoolActive.setDescribe(describe);
		log.info("title:" + schoolActive.getMainTitle());
		log.info("viceTitle:" + schoolActive.getViceTitle());
		log.info("writer:" + schoolActive.getWriter());
		log.info("content:" + content);
		log.info("time:" + TimeUtil.getCurrentDateFormat());
		// 保存校友动态
		schoolActiveDao.attachDirty(schoolActive);
		log.info("newsId:" + schoolActive.getNewsId());

		// 保存校友动态的列表图片
		String newImagePath = new ImageUtil().saveNewsImage(file,
				schoolActive.getNewsId() + "");
		schoolActive.setNewImagePath(newImagePath);

		// 保存内容
		new Thread() {
			public void run() {
				try {
					schoolActive.setContent(imagePath(content,
							schoolActive.getNewsId()));
					// 更新
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		schoolActiveDao.attachDirty(schoolActive);
		// 先返回
		map.put("schoolActive", schoolActive);
		return "schoolActive";
	}

	public String imagePath(String content, int newsId) throws IOException {
		String newsPath = "image/news/" + newsId + "/";
		String savePath = new Path().getBasicPath() + newsPath;
		String src;
		int index = 0;
		int end;
		log.info("savePath:" + savePath);
		do {
			// 如果没有找到 退出循环 从index位置开始找
			index = content.indexOf("src=\"", index);
			log.info("index:" + index);
			if (index == -1)
				break;
			index += 5;
			log.info("index:" + index);
			end = content.indexOf("\"", index);
			if (end == -1)
				break;
			// 获取url字符串
			src = content.substring(index, end);
			log.info("src:" + src);
			// 得到图片的后缀名
			int srcIndex = src.lastIndexOf(".");
			String filename = src.substring(srcIndex);
			log.info("filename:10001" + filename);
			// 以当前毫秒数为文件名
			filename = System.currentTimeMillis() + filename;
			FileUtil.download(src, savePath, filename);
			// 图片保存成功后修改content中的img对应的图片路径
			content = content.replaceFirst(src, newsPath + filename);
			log.info("filename:" + newsPath + filename);
			// 修改index
			index = end;
		} while (true);

		return content;
	}

	/**
	 * 请求校友动态的列表。 1.获取index(想要从第index开始获取10条以内的校友动态的列表) 2.进行数据库查找
	 * 3.将查找到的列表进行选择性的写进 JSONArray 以及 JSONObject 4.JSONArray.toString
	 * 
	 * @param map
	 *            (modelAndView)
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value = "/newsList.do", produces = "text/plain;charset=UTF-8")
	public String newsList(Map<String, Object> map) {
		int index = 0;
		try {
			index = Integer.parseInt((String) map.get("index"));
		} catch (Exception e) {
			log.info("传过来的不是数字:");
		}

		// 获取校友动态的列表
		List<SchoolActive> newss = schoolActiveDao.findList(index, 10);

		JSONArray jsonArray = new JSONArray();

		for (SchoolActive schoolActive : newss) {
			JSONObject jsonObject2 = new JSONObject();
			// id
			jsonObject2.put("newsId", schoolActive.getNewsId());
			// imagepath
			jsonObject2.put("newsImagePath", schoolActive.getNewImagePath());
			jsonObject2.put("mainTitle", schoolActive.getMainTitle());
			jsonObject2.put("viceTitle", schoolActive.getViceTitle());
			// jsonObject2.put("content", schoolActive.getContent());
			jsonObject2.put("viewCount", schoolActive.getReadCount());
			jsonArray.add(jsonObject2);
		}
		log.info("json:" + jsonArray.toString());
		return jsonArray.toString();
	}

	/**
	 * 获取newsId的校友动态网页。 链接使用{newsId}.do
	 * 1.获取到newsId。用schoolActiveDao搜索到newsId对应的校友动态的model类schoolActive
	 * 2.将阅读量进行加一处理 3.将schoolActive写进map。 4.返回到 schoolActive.jsp
	 * 
	 * @param newsId
	 *            校友动态新闻的Id
	 * @param map
	 *            modelAndView
	 * @return schoolActive --> schoolActive.jsp
	 * 
	 */
	@RequestMapping(value = "/getNews/{newsId}.do")
	public String getNews(@PathVariable("newsId") Integer newsId,
			Map<String, Object> map) {
		log.info("newsId:" + newsId);
		SchoolActive schoolActive = schoolActiveDao.findById(newsId);
		if (schoolActive == null) {
			log.info("aa");
			return "schoolActive";
		}
		// 设置阅读量

		int readCount = 0;
		try {
			readCount = schoolActive.getReadCount();
			readCount++;
		} catch (Exception e) {
			readCount = 1;
		}
		schoolActive.setReadCount(readCount);

		schoolActiveDao.attachDirty(schoolActive);
		map.put("schoolActive", schoolActive);

		return "schoolActive";
	}

	/**
	 * /schoolActive/getHeadNews.do
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getHeadNews.do", produces = "text/plain;charset=UTF-8")
	public String getHeadNew() {
		log.info("获取头条新闻");
		List<SchoolActive> actives = schoolActiveDao.findHeadNews();

		JSONArray jsonArray = new JSONArray();

		for (SchoolActive schoolActive : actives) {
			JSONObject jsonObject2 = new JSONObject();
			// id
			jsonObject2.put("newsId", schoolActive.getNewsId());
			// imagepath
			String imagePath = schoolActive.getNewImagePath();
			log.info("imagePath:" + imagePath);
			imagePath = imagePath.replaceFirst("small_", "");
			log.info("imagePath-after:" + imagePath);
			jsonObject2.put("newsImagePath", imagePath);

			// jsonObject2.put("newsImagePath",schoolActive.getNewImagePath());

			jsonObject2.put("mainTitle", schoolActive.getMainTitle());
			jsonArray.add(jsonObject2);
		}
		log.info("json:" + jsonArray.toString());

		return jsonArray.toString();
	}

	@RequestMapping(value = "/getNewsList.do")
	public String getNewsList(Map<String, Object> map) {
		List<SchoolActive> actives = schoolActiveDao.findHeadNews();
		List<SchoolActive> actives2 = schoolActiveDao.findList(0, 10);
		map.put("headNews", actives);
		map.put("news", actives2);
		return "newsList";
	}

	@RequestMapping(value = "/setIsHeadNews/{newsId}.do")
	public String setIsHeadNews(@PathVariable("newsId") Integer newsId,
			Map<String, Object> map) {

		SchoolActive schoolActive = schoolActiveDao.findById(newsId);

		if (schoolActive.getIsHeadNews() == 0) {
			schoolActive.setIsHeadNews(1);
		} else {
			schoolActive.setIsHeadNews(0);
		}

		schoolActiveDao.attachDirty(schoolActive);

		return "redirect:/schoolActive/getNewsList.do";
	}

	@RequestMapping(value = "/changeSchoolActive/{newsId}.do")
	public String changeSchoolAtctive(@PathVariable("newsId") Integer newsId,
			Map<String, Object> map) {

		SchoolActive news = schoolActiveDao.findById(newsId);

		map.put("news", news);

		return "writeSA";
	}

}
