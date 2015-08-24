package com.atm.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.dao.impl.AppealDAOImpl;
import com.atm.model.Appeal;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(AppealDAOImpl.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// �õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
		String savePath = this.getServletContext().getRealPath("/WEB-INF/image");
		//�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
		String photoPath = "";
		/**
		 * �õ������ļ����͡������ļ������������������ӣ�����ͷ��
		 */
		String type = (String)request.getAttribute("type"); 
		File tempFile = new File(tempPath);
		if(!tempFile.exists()){
			//������ʱĿ¼
			tempFile.mkdir();
		}
		//��Ϣ��ʾ
		String message = "";
		try {
			//ʹ��Apache�ļ��ϴ���������ļ��ϴ�����
			// 1.����һ��DiskFileItemFactory����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�С�£��ͻ����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼��
			factory.setSizeThreshold(1024*100);//���û�������СΪ100kb�������ָ������ô�������Ĵ�СĬ����10KB
			//�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
			factory.setRepository(tempFile);
			//2.����һ���ļ��ϴ�������
			ServletFileUpload upload = new ServletFileUpload(factory);
			//�����ļ��ϴ�����
			upload.setProgressListener(new ProgressListener() {
				@Override
				public void update(long pByteRead, long pContentLength, int arg2) {
					System.out.println("�ļ���СΪ:" + pContentLength + ",��ǰ�Ѵ���:" + pByteRead);
				}
			});
			//����ϴ��ļ�������������
			upload.setHeaderEncoding("UTF-8");
			//3.�ж��ύ�����������Ƿ����ϴ���������
			log.info("oooooooooooooooooooooooo");
			if(!ServletFileUpload.isMultipartContent(request)){
				//���մ�ͳ��ʽ��ȡ����
				log.info("11111111111111111111111");
				return;
			}
			//�����ϴ������ļ���С�����ֵ��Ŀǰ��1024*1024�ֽ�,Ҳ����1MB
			upload.setFileSizeMax(1024*1024);
			//�����ϴ��ļ����������ֵ�����ֵ=ͬʱ�ϴ��Ķ���ļ��Ĵ�С�����ֵ�ĺͣ�Ŀǰ����Ϊ10MB
			upload.setSizeMax(1024*1024*10);
			//4.ʹ��ServletFileUpload�����������ϴ����ݣ�
			//����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				//���fileitem�з�װ������ͨ�����������
				if(item.isFormField()){
					String name = item.getFieldName();
					//�����ͨ����������ݵ�������������
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				}else{
					//���fileitem�з�װ�����ϴ��ļ�
					//�õ��ϴ����ļ�����
					String filename = item.getName();
					System.out.println(filename);
					if(filename==null||filename.trim().equals("")){
						continue;
					}
					//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ�
					//�磺  c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
					//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					//�õ��ϴ��ļ�����չ��
					String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
					System.out.println(filename + "�ļ���չ����:" + fileExtName);
					//��ȡitem�е��ϴ��ļ���������
					InputStream in = item.getInputStream();
					//�õ��ļ����������
					String saveFilename = makeFilename(filename);
					System.out.println("saveFilename:" + saveFilename);
					//�õ��ļ��ı���Ŀ¼
					String realSavePath = makePath(type,savePath);
					System.out.println("realSavePath:" + realSavePath);
					/**
					 * ����·�� ���ᱻ����Ե���Ӧ�����ݿ���ͼƬ·��������
					 */
					photoPath = realSavePath + "\\" + saveFilename;
					request.setAttribute("photoPath", realSavePath + "\\" + saveFilename);
					//����һ���ļ������
					 FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
					//����һ��������
					byte buffer[] = new byte[1024];
					//����һ���������е������Ƿ��Ѿ�����ı�־
					int len = 0;
					System.out.println("========1======");
					//ѭ�������������뵽�������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
					while((len=in.read(buffer))>0){
						//����FileOutPutStream�������������������д�뵽ָ����Ŀ¼��savePath + "\\" + filename)����
						out.write(buffer,0,len);
					}
					System.out.println("========2======");
					 //�ر�������
					in.close();
					//�ر������
					out.close();
					//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
					//item.delete();
					log.info("�ļ��ϴ��ɹ���");
				}
			}
			if(type.equals("appeal")){
				Appeal appeal = (Appeal)request.getAttribute("appeal");
				//String photoPath = (String)request.getAttribute("photoPath");
				//����·��
				log.info(photoPath);
				System.out.println(photoPath);
				appeal.setPhotoPath(photoPath);
				//����appeal
				//message = AppealService.save(appeal);
				System.out.println("=====================");
			}
		}catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "�����ļ��������ֵ������");
			request.getRequestDispatcher("/message.jsp").forward(request, response);
				return;
			}catch (FileUploadBase.SizeLimitExceededException e) {
				e.printStackTrace();
		        request.setAttribute("message", "�ϴ��ļ����ܵĴ�С�������Ƶ����ֵ������");
			    request.getRequestDispatcher("/message.jsp").forward(request, response);
		        return;
			}catch (Exception e) {
				message= "�ļ��ϴ�ʧ�ܣ�";
				e.printStackTrace();
		}
		
		request.setAttribute("message",message);
		//request.getRequestDispatcher("/message.jsp").forward(request, response);
	}	
	public String makeFilename(String filename){
		//Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		 return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * ������һ�޶����ļ���
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String type,String savePath){
		//�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ�������
		//���ڴ��еĵ�ַ
		//(new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4; //0--15*/		
		//�����µı����Ŀ¼
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + type + "\\" + date;//upload\2\3  upload\3\5
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		//���Ŀ¼������
		if(!file.exists()){
			//����Ŀ¼
			System.out.println("create file" + file.getName());
			file.mkdirs();
		}
		return dir;
	}
}
