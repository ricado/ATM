package com.atm.util.bbs;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

/**
 * @TODO����ͨ������
 * @fileName : com.atm.util.CommonUtil.java
 * date | author | version |   
 * 2015��8��9�� | Jiong | 1.0 |
 */
public class CommonUtil {
	Logger log  = Logger.getLogger(getClass());
	
	/**@author Jiong
	 * ��ʱ��
	 * @param startTime
	 * @return
	 */
	public String subTime(String startTime) {
		
		DateFormat sd = SimpleDateFormat.getDateTimeInstance();
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		try {
			c1.setTime(new Date());
			c2.setTime(sd.parse(startTime));
			//�������ʱ��ĺ���ʱ�����
			long diff = new Date().getTime() - sd.parse(startTime).getTime();
			
			long min = diff/(1000*60);//�������ٷ���
			long hour;
			long day;
			if(min<0){
				return "���ڴ���";
			}else if(min==0){
				return "�ոշ���";
			}else if(min<60){
				return min+"����ǰ";
			}else if((hour=min/60)<24){
				if(c1.get(Calendar.DAY_OF_MONTH)==c2.get(Calendar.DAY_OF_MONTH)){
					return hour+"Сʱǰ";//24Сʱ������ͬһ��
				}else{
					return "����  "+
							c2.get(Calendar.HOUR_OF_DAY)+"ʱ"+
							c2.get(Calendar.MINUTE)+"��";//24Сʱ�ڵ��Ѿ�����һ��
				}
			}else if((day=hour/24)<366){
				if(c1.get(Calendar.DAY_OF_YEAR)-1==c2.get(Calendar.DAY_OF_YEAR)){
					return "����  "+
							c2.get(Calendar.HOUR_OF_DAY)+"ʱ"+
							c2.get(Calendar.MINUTE)+"��";//366���������һ��
				}else if(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)){
					//366��������ͬһ�꣬��ʾ�¡��ա�ʱ����
					return (c2.get(Calendar.MONTH)+1)+"��"+
							c2.get(Calendar.DAY_OF_MONTH)+"��  "+
							c2.get(Calendar.HOUR_OF_DAY)+"ʱ"+
							c2.get(Calendar.MINUTE)+"��";
				}else{
					//366���ڵ��Ѿ�����һ��
					return startTime.substring(0,16);
				}
			}else{
				//����366��
				return startTime.substring(0,16);
			}
		} catch (ParseException e) {
			log.error("ת��ʱ��������",e);
			return "δ֪";
		}
		
	}
	
	  /**
		 * @�û�����������е���ҳ��ر�ǩת��
		 * @���� valueΪҪת�����ַ���
		 * @����ֵ ת������ַ�
		 */
		public static String changeHTML(String value){
			value=value.replace("&","&amp;");
			value=value.replace(" ","&nbsp;");
			value=value.replace("<","&lt;");
			value=value.replace(">","&gt;");
			value=value.replace("\r\n","<br/>");
			return value;
		}
	//�滻�����еı���
	public String getExp(String text){
		String reg = "\\#\\((\\w+)\\)";//��#����������ƥ���������
		Pattern pattern = Pattern.compile(reg);//��ʽ
		Matcher matcher = pattern.matcher(text);//ƥ��
		String path="";
		try {
			String u=getClass().getResource("/").getPath();//��ľ���·��
			path=u.substring(0,u.indexOf("WEB-INF"))+"biaoqing";//E:/MyEclipse/atmDemo1.1/WebRoot/biaoqing";
			File file = new File(path);//��������Ŀ¼
			//���ƥ�䵽
			while(matcher.find()){
				File[] aPng = file.listFiles();
				//��������
				for(int i=0;i<aPng.length;i++){
					String name = aPng[i].getName();
					//ƥ��ı��������ļ�����ͬ
					if(matcher.group(1).equals(name.substring(0,name.indexOf(".")))){
						name = "<img src='biaoqing/"+name+"' class='expression'/>";
						text = text.replace("#("+matcher.group(1)+")", name); 
					}
	            }
			}
		} catch (Exception e) {
			log.error("ת���������"+path,e);
		}
		
		return text;
	}
	
	
	/**
	 * 
	 * @param file
	 * @param folder
	 * @return
	 */
	public ArrayList saveFile(MultipartFile[] files,String path,String folder,String subFolder){
		ArrayList<String> savePath = new ArrayList<String>();
		path = makePath(folder,subFolder,path);
        for(MultipartFile file:files){
        	if(file.isEmpty()){
        		continue;
        	}
        	log.debug("�ϴ��ļ�");
	        String fileName = file.getOriginalFilename();
	        //Ϊ�ļ�����һ����һ�޶�������
	        fileName = makeFilename(fileName);
			//�õ��ļ��ı���Ŀ¼
	        File targetFile = new File(path, fileName); 
	        //���û������ļ���������һ��
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }
	        //����  
	        try {  
	            file.transferTo(targetFile);
	            //��������ͼ
	            File shrinkFile = new File(makeShrinkPath(path),fileName);
	            if(!shrinkFile.exists()){  
		            shrinkFile.createNewFile();  
		        }
	            ImageUtil.resize(targetFile,shrinkFile,400, 0.9f);
	        } catch (Exception e) {  
	        	log.error(e);
	            return savePath; 
	        }  
	        //model.addAttribute("fileUrl",path+"/"+fileName); 
	        //request.setAttribute("photoPath", path+"/"+fileName);
	        //log.info("==========" + path+"/"+fileName);
	        savePath.add(path+"\\shrink"+"/"+fileName);
        }
        return savePath;
	}
	/**
	 * ����һ����һ�޶����ļ���
	 * @param filename
	 * @return filename
	 */
	public String makeFilename(String filename){
		//Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		 return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * ����ʱ���Ŀ¼�ļ���
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String folder,String subFolder,String savePath){
		//�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ�������
		//���ڴ��еĵ�ַ
		//(new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4; //0--15*/		
		//�����µı����Ŀ¼
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + folder + "\\" + date+ "\\" +subFolder;//upload\2\3  upload\3\5
		//File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
		File file = new File(dir);
		//���Ŀ¼������
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
	
	//����ѹ���ļ���Ŀ¼
	public String makeShrinkPath(String path){
		String dir  = path + "\\shrink";
		File file = new File(dir);
		//���Ŀ¼������
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
}
