package com.atm.springMvc.handlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Download {

	@RequestMapping(value = "/downloadApp.do", method = RequestMethod.POST)
	public String downloadApp(HttpServletResponse response,
			HttpServletRequest request) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ "atm.apk");
		try {
			
			String path = this.getClass().getResource("/").getPath().substring(1)
					.replaceAll("WEB-INF/classes/", "") +"/file/ShareTribe.apk"; // ���downloadĿ¼Ϊɶ������classes�µ� 
			InputStream inputStream = new FileInputStream(new File(path));
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			// ������Ҫ�رա�
			os.close();

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ����ֵҪע�⣬Ҫ��Ȼ�ͳ�������������
		// java+getOutputStream() has already been called for this response
		return null;
	}
}
