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
 * @TODO：普通工具类
 * @fileName : com.atm.util.CommonUtil.java
 * date | author | version |   
 * 2015年8月9日 | Jiong | 1.0 |
 */
public class CommonUtil {
	Logger log  = Logger.getLogger(getClass());
	
	/**@author Jiong
	 * 改时间
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
			//获得两个时间的毫秒时间差异
			long diff = new Date().getTime() - sd.parse(startTime).getTime();
			
			long min = diff/(1000*60);//计算差多少分钟
			long hour;
			long day;
			if(min<0){
				return "日期错误";
			}else if(min==0){
				return "刚刚发布";
			}else if(min<60){
				return min+"分钟前";
			}else if((hour=min/60)<24){
				if(c1.get(Calendar.DAY_OF_MONTH)==c2.get(Calendar.DAY_OF_MONTH)){
					return hour+"小时前";//24小时内且是同一天
				}else{
					return "昨天  "+
							c2.get(Calendar.HOUR_OF_DAY)+"时"+
							c2.get(Calendar.MINUTE)+"分";//24小时内但已经经过一天
				}
			}else if((day=hour/24)<366){
				if(c1.get(Calendar.DAY_OF_YEAR)-1==c2.get(Calendar.DAY_OF_YEAR)){
					return "昨天  "+
							c2.get(Calendar.HOUR_OF_DAY)+"时"+
							c2.get(Calendar.MINUTE)+"分";//366天内且相隔一天
				}else if(c1.get(Calendar.YEAR)==c2.get(Calendar.YEAR)){
					//366天内且在同一年，显示月、日、时、分
					return (c2.get(Calendar.MONTH)+1)+"月"+
							c2.get(Calendar.DAY_OF_MONTH)+"日  "+
							c2.get(Calendar.HOUR_OF_DAY)+"时"+
							c2.get(Calendar.MINUTE)+"分";
				}else{
					//366天内但已经过了一年
					return startTime.substring(0,16);
				}
			}else{
				//大于366天
				return startTime.substring(0,16);
			}
		} catch (ParseException e) {
			log.error("转换时间间隔错误",e);
			return "未知";
		}
		
	}
	
	  /**
		 * @用户输入的内容中的网页相关标签转换
		 * @参数 value为要转换的字符串
		 * @返回值 转换后的字符
		 */
		public static String changeHTML(String value){
			value=value.replace("&","&amp;");
			value=value.replace(" ","&nbsp;");
			value=value.replace("<","&lt;");
			value=value.replace(">","&gt;");
			value=value.replace("\r\n","<br/>");
			return value;
		}
	//替换内容中的表情
	public String getExp(String text){
		String reg = "\\#\\((\\w+)\\)";//从#（表情名）匹配出表情名
		Pattern pattern = Pattern.compile(reg);//样式
		Matcher matcher = pattern.matcher(text);//匹配
		String path="";
		try {
			String u=getClass().getResource("/").getPath();//类的绝对路径
			path=u.substring(0,u.indexOf("WEB-INF"))+"biaoqing";//E:/MyEclipse/atmDemo1.1/WebRoot/biaoqing";
			File file = new File(path);//表情所在目录
			//如果匹配到
			while(matcher.find()){
				File[] aPng = file.listFiles();
				//遍历表情
				for(int i=0;i<aPng.length;i++){
					String name = aPng[i].getName();
					//匹配的表情名与文件名相同
					if(matcher.group(1).equals(name.substring(0,name.indexOf(".")))){
						name = "<img src='biaoqing/"+name+"' class='expression'/>";
						text = text.replace("#("+matcher.group(1)+")", name); 
					}
	            }
			}
		} catch (Exception e) {
			log.error("转换表情错误"+path,e);
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
        	log.debug("上传文件");
	        String fileName = file.getOriginalFilename();
	        //为文件创建一个独一无二的名字
	        fileName = makeFilename(fileName);
			//得到文件的保存目录
	        File targetFile = new File(path, fileName); 
	        //如果没有这个文件，就生成一个
	        if(!targetFile.exists()){  
	            targetFile.mkdirs();  
	        }
	        //保存  
	        try {  
	            file.transferTo(targetFile);
	            //保存缩略图
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
	 * 创建一个独一无二的文件名
	 * @param filename
	 * @return filename
	 */
	public String makeFilename(String filename){
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		 return UUID.randomUUID().toString() + "_" + filename;
	}
	/**
	 * 创建时间的目录文件夹
	 * @param filename
	 * @param savePath
	 * @return
	 */
	public String makePath(String folder,String subFolder,String savePath){
		//得到文件名的hashCode的值，得到的就是filename这个字符串对象
		//在内存中的地址
		//(new java.text.SimpleDateFormat("yyyy/MM/dd")).format(new Date())
		/*int hashcode = filename.hashCode();
		int dir1 = hashcode&0xf;  //0--15
		int dir2 = (hashcode&0xf0)>>4; //0--15*/		
		//构造新的保存的目录
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String dir = savePath + "\\" + folder + "\\" + date+ "\\" +subFolder;//upload\2\3  upload\3\5
		//File既可以代表文件也可以代表目录
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
	
	//生成压缩文件的目录
	public String makeShrinkPath(String path){
		String dir  = path + "\\shrink";
		File file = new File(dir);
		//如果目录不存在
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}
}

