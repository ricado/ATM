package com.atm.util.bbs;

import java.io.File;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @TODO：
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
					return startTime.substring(0,17);
				}
			}else{
				//大于366天
				return startTime.substring(0,17);
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
			path="E:/MyEclipse/atmDemo1.1/WebRoot/biaoqing";
			File file = new File(path);
			while(matcher.find()){
				File[] aPng = file.listFiles();
				for(int i=0;i<aPng.length;i++){
					String name = aPng[i].getName();
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
}

