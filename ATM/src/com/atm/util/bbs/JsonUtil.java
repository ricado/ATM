package com.atm.util.bbs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class JsonUtil {
	Logger log = Logger.getLogger(getClass());
	/**
	 * @方法 根据传入的request请求返回该请求的JSONArray数组
	 * @author Jiong
	 * @参数 request：请求
	 * @返回 JSONArray数组
	 * @时间 
	 */
	public JSONArray getJSONArray(HttpServletRequest request) throws IOException, JSONException{
		//输入流
		BufferedInputStream buf = new BufferedInputStream(request.getInputStream());
		String temp="";
		int i;
		char c;
		while((i=buf.read())!=-1){
			c = (char)i;
			temp += c;
		}
		temp = changeString(temp);
		log.debug("接收："+temp);
		//temp = "{'userId':'11ceshi','name':'lzlzj','nickname':'囧','aaa':'bbb','userPwd':'dsa'}";
		//temp = "{'userId':'ceshi18','name':'lzlzj','nickname':'囧','aaa':'bbb'}";
		//temp = "";
		/*temp="{'essayType':'软妹子大赛','title':'听说题目要长才有人来','content':'计算机系<软妹纸大赛>正式启动，参赛选手有来自：\\r\\n"+
				"计算机专业的钟sir以及来自电子商务的丹霞山\\r\\n投钟sir请选1，投丹霞山请选2:\\r\\n[3][4]',"+
				"'department':'计算机科学与技术系','labName':'女汉子*#没有选项1*#没有选项2'}";*/
		if(temp.length()==0){ //空数据，返回空数组
			temp = "[]";
		}
		if(temp.charAt(0)!='['){ //不是array格式换成array格式
			temp = "["+temp+"]";
		}
		return new JSONArray(temp);
	}
	
	/**
     * 将json转化为实体POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public <T> Object JSONToObj(String jsonObject,Class<T> obj) {
        T t = null;
        try {  
        	//忽略实体中未定义的属性
            ObjectMapper objectMapper = new ObjectMapper().setVisibility(JsonMethod.FIELD,Visibility.ANY);
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t = objectMapper.readValue(jsonObject,
                    obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    /**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     * @flag ture:不忽略空值或“”； false：忽略空值或“”
     */
    public <T> JSONObject objectToJson(T obj,boolean flag) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper(); 
        if(!flag){
        	//Include.NON_EMPTY 属性为 空（""）  或者为 NULL 都不序列化 
	        mapper.setSerializationInclusion(Inclusion.NON_EMPTY);
        }
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    //默认不忽略空属性
    public <T> JSONObject objectToJson(T obj) throws JSONException, IOException {
    	return objectToJson(obj,true);
    }
    
   /**
    * 将list对象转为JSONArray
    * @param list
    * @return
    * @throws JSONException
    * @throws IOException
    * @flag ture:不忽略空值或“”； false：忽略空值或“”
    */
    public <T> JSONArray objectToArray(List<T> list,boolean flag) throws JSONException, IOException{
    	JSONArray jsonArray = new JSONArray();
    	JSONObject jsonObject;
    	
    	for(int i = 0;i<list.size();i++){
    		if(!flag){
    			jsonObject = objectToJson(list.get(i),flag);
    		}else{
    			jsonObject = objectToJson(list.get(i));
    		}
    		jsonArray.put(jsonObject);
    	}
    	return jsonArray;
    }
    //将list对象转为JSONArray(不忽略空值属性）
    public <T> JSONArray objectToArray(List<T> list) throws JSONException, IOException{
    	return objectToArray(list,true);
    }
    
    //注册格式检查
    public boolean registerCheck(JSONObject jsonObject) throws JSONException{
    	boolean ok = false;
    	int flag = 0;
    	if(jsonObject.getString("userPwd").equals(jsonObject.getString("userPwdAgain"))){
    		flag++;
    	}
    	if(flag==1){
    		ok = true;
    	}else{
    		ok = false;
    	}
    	return true;
    }
    
    /**
     * 
     * 转换字符编码，解决中文乱码
     * @param str
     * @return
     */
    public String changeString(String str){
    	try {
			return new String(str.getBytes("ISO-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return str;
		}
    }
    
  
}
