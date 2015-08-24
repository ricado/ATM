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
	 * @���� ���ݴ����request���󷵻ظ������JSONArray����
	 * @author Jiong
	 * @���� request������
	 * @���� JSONArray����
	 * @ʱ�� 
	 */
	public JSONArray getJSONArray(HttpServletRequest request) throws IOException, JSONException{
		//������
		BufferedInputStream buf = new BufferedInputStream(request.getInputStream());
		String temp="";
		int i;
		char c;
		while((i=buf.read())!=-1){
			c = (char)i;
			temp += c;
		}
		temp = changeString(temp);
		log.debug("���գ�"+temp);
		//temp = "{'userId':'11ceshi','name':'lzlzj','nickname':'��','aaa':'bbb','userPwd':'dsa'}";
		//temp = "{'userId':'ceshi18','name':'lzlzj','nickname':'��','aaa':'bbb'}";
		//temp = "";
		/*temp="{'essayType':'�����Ӵ���','title':'��˵��ĿҪ����������','content':'�����ϵ<����ֽ����>��ʽ����������ѡ�������ԣ�\\r\\n"+
				"�����רҵ����sir�Լ����Ե�������ĵ�ϼɽ\\r\\nͶ��sir��ѡ1��Ͷ��ϼɽ��ѡ2:\\r\\n[3][4]',"+
				"'department':'�������ѧ�뼼��ϵ','labName':'Ů����*#û��ѡ��1*#û��ѡ��2'}";*/
		if(temp.length()==0){ //�����ݣ����ؿ�����
			temp = "[]";
		}
		if(temp.charAt(0)!='['){ //����array��ʽ����array��ʽ
			temp = "["+temp+"]";
		}
		return new JSONArray(temp);
	}
	
	/**
     * ��jsonת��Ϊʵ��POJO
     * @param jsonStr
     * @param obj
     * @return
     */
    public <T> Object JSONToObj(String jsonObject,Class<T> obj) {
        T t = null;
        try {  
        	//����ʵ����δ���������
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
     * ��ʵ��POJOת��ΪJSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     * @flag ture:�����Կ�ֵ�򡰡��� false�����Կ�ֵ�򡰡�
     */
    public <T> JSONObject objectToJson(T obj,boolean flag) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper(); 
        if(!flag){
        	//Include.NON_EMPTY ����Ϊ �գ�""��  ����Ϊ NULL �������л� 
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
    //Ĭ�ϲ����Կ�����
    public <T> JSONObject objectToJson(T obj) throws JSONException, IOException {
    	return objectToJson(obj,true);
    }
    
   /**
    * ��list����תΪJSONArray
    * @param list
    * @return
    * @throws JSONException
    * @throws IOException
    * @flag ture:�����Կ�ֵ�򡰡��� false�����Կ�ֵ�򡰡�
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
    //��list����תΪJSONArray(�����Կ�ֵ���ԣ�
    public <T> JSONArray objectToArray(List<T> list) throws JSONException, IOException{
    	return objectToArray(list,true);
    }
    
    //ע���ʽ���
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
     * ת���ַ����룬�����������
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
