package com.atm.test;

import java.sql.Timestamp;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import com.atm.util.DateJsonValueProcessor;
import com.atm.util.TimestampMorpher;

public class TimestampTest {  
		public static void main(String[] args) {  
			String jsonStr="{\"id\":\"101\",\"name\":\"����\",\"age\":\"20\",\"birthday\":\"1992-10-19 23:52:18\"}";  
		    Student s=new Student();  
		    Timestamp b=Timestamp.valueOf("1992-10-19 23:52:18");  
		    s.setId(123456);  
		    s.setName("����");  
		    s.setAge(20);  
		    s.setBirthday(b);  
		    Student s1=jsonToBean(jsonStr);  
		    System.out.println(s1.getBirthday());  
		    System.out.println(beanToJson(s));  
		}  
		public static Student jsonToBean(String json){  
		    String[] formats={"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"};  
		    JSONUtils.getMorpherRegistry().registerMorpher(new TimestampMorpher(formats));  
		    JSONObject jsonObject=JSONObject.fromObject(json);  
		    return (Student)JSONObject.toBean(jsonObject,Student.class);  
		}  
		  
		public static String beanToJson(Student s){  
		    JsonConfig config=new JsonConfig();  
		    config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));  
		    JSONObject json=JSONObject.fromObject(s,config);  
		    return json.toString();
		}  
}
