package com.atm.test.image;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @TODO�����ļ��ϴ����ԣ����Ļ�����
 * @fileName : com.atm.test.image.ImitateHttpRequest.java
 * date | author | version |   
 * 2015��10��5�� | Jiong | 1.0 |
 */ 

/**
 * ������������д�뵽�����ʱ��ȡ�ֽڲ���UTF-8���루���������һ�£�
 * @author Jiong
 *
 */

/**
 * TODO: �����࣬ģ��������ύ����������,������uri���ύ
 * @author Jiong
 * @date: 2015��10��06�� 22:14:55 �޸�
 *@ʹ�÷�����������������һ��uri�������ַ��������post�������ɽ���������������
 *         �����Ҫ���ʲ�ͬ�ĵ�ַ����ÿ��postǰ����һ��Ҫ���ʵ�uri�Ϳ���	 		 
 */
  
public class ImitateHttpRequest {
	
	/*//ʹ��ʾ��
	public static void main(String[] args) throws UnsupportedEncodingException {
		ImitateHttpRequest go = new ImitateHttpRequest();
        String response = "";    
        
        String address = "http://localhost:8080/ATM/"; 
        //address = "http://139.129.131.179/ATM/";
        
        //ģ�µ�¼
        Map<Object,Object> loginParams = new HashMap<Object,Object>();
        loginParams.put("userId", "1");
        loginParams.put("password", "1");
        go.setUri(address+"user_login.action");
        response = go.post(loginParams);   
        System.out.println("���ؽ����" + response);  
        
        //ģ�·�����
        //����������Щ���Ⱑʲô���߰��������
        Map<Object,Object> params = new HashMap<Object,Object>();
        params.put("type", "������");
        params.put("label", "�ϴ�*#���ļ�");
        params.put("title", "��������");
        params.put("department", "����ϵ");
        params.put("content", "���ļ��ϴ�����");
        //�ļ���ͼƬ��
        ArrayList<File> list = new ArrayList<File>();
        list.add(new File("E:\\test-0.jpg"));
        list.add(new File("E:\\test-1.jpg"));
        File[] files = (File[])list.toArray(new File[list.size()]);
        //��������
        go.setUri(address+"essay/publish.do");
        response = go.post(params, files,"files");  
        System.out.println("���ؽ����" + response);  
    
}*/
	
	
	//��������
    private String enctype = "multipart/form-data";  //MIME����
    private Integer timeOut = 120000;  //��ʱʱ��
    private String charset = "UTF-8"; //�ַ�����
    private String requestMethod = "POST"; //����ʽ
    private String connection = "keep-alive"; //����״̬
    private static String uri = ""; //���ӵ�ַ
    
    private String boundary = "****************fD4fH3gL0hK7aI6";    // ���ݷָ��� ,��ÿ������ǰ����Ҫ�ӷָ���
    
    
    private List<String> cookies = null;//�������ʱ�����õ�cookie
    
    
    private String twoHyphens = "--";  
    private String lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.  
      
    
    /**
     * �ϴ���
     * @param params
     * @return
     */
    public String post(Map<Object, Object> params){
    	return post(params, null,null);
    }
    /**
     * �ϴ��ļ�
     * @param files
     * @param fileName
     * @return
     */
    public String post(File[] files,String key){
    	return post(null, files,key);
    }
    
    /** 
     * ֱ��ͨ�� HTTP Э���ύ���ݵ���������ʵ�ֱ��ύ���ܡ� 
     * @param actionUrl �ϴ�·�� 
     * @param params �������keyΪ��������valueΪ����ֵ 
     * @param files �ϴ��ļ���Ϣ 
     * @return ���������� 
     */  
    public String post(Map<Object, Object> params, File[] files,String key) { 
    	if(uri.length()==0){
    		return "δ����uri";
    	}
        HttpURLConnection conn = null;  
        DataOutputStream output = null;  
        BufferedReader input = null;  
        try {  
            URL url = new URL(uri);  
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeOut);
            conn.setRequestProperty("Charset", charset); // ���ñ���  
            conn.setRequestMethod(requestMethod); 
            conn.setDoInput(true);        // ��������  
            conn.setDoOutput(true);        // �������  
            conn.setUseCaches(false);    // ��ʹ��Cache  
            if(cookies!=null){
	            for (String cookie : cookies) {
	                conn.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
	            }
            }
            conn.setRequestProperty("Connection", connection);  
            conn.setRequestProperty("Content-Type", enctype + "; boundary=" + boundary);  
              
            conn.connect();  
            output = new DataOutputStream(conn.getOutputStream());  
              
            addFileContent(files,key,output);    // ���ͼƬ����  
            addFormField(params, output);    //������
              
            output.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);// ���ݽ�����־  
            output.flush();   
            int code = conn.getResponseCode();    
            cookies = conn.getHeaderFields().get("Set-Cookie");
            if(code != 200) {  
                throw new RuntimeException("����" + uri +"��ʧ�ܣ�");  
            }  
              
            input = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            StringBuilder response = new StringBuilder();  
            String oneLine;  
            while((oneLine = input.readLine()) != null) {
                response.append(oneLine + lineEnd);  
            }  
              
            return response.toString();  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        } finally {  
            // ͳһ�ͷ���Դ  
            try {  
                if(output != null) {  
                    output.close();  
                }  
                if(input != null) {  
                    input.close();  
                }  
            } catch (IOException e) {  
                throw new RuntimeException(e);  
            }  
              
            if(conn != null) {  
                conn.disconnect();  
            }  
        }  
    }  
    
    
    /* 
     * д��ͼƬ���ݣ���ʽ��ο�HTTP Э���ʽ�� 
     * ������Photos.upload�еġ�������á�http://wiki.dev.renren.com/wiki/Photos.upload#.E7.A8.8B.E5.BA.8F.E8.B0.83.E7.94.A8 
     * �����ʽ���͵ķǳ������� 
     * ��ʽ������ʾ�� 
     * --****************fD4fH3hK7aI6 
     * Content-Disposition: form-data; name="upload_file"; filename="apple.jpg" 
     * Content-Type: image/jpeg 
     * 
     * ������ļ������ݣ�������������ʽ 
     */  
    private void addFileContent(File[] files,String key, DataOutputStream output) {  
    	if(files == null||files.length==0){ //û���ļ�ֱ���˳�����
    		return;
    	}
    	//ѭ�����ļ���ʽд�뵽ͷ�ļ���
        for(File file : files) {  
            StringBuilder split = new StringBuilder();  
            split.append(twoHyphens + boundary + lineEnd);  
            split.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\"" + file.getName() + "\"" + lineEnd);  
            split.append("Content-Type: application/octet-stream; charset=utf-8" + lineEnd);  
            split.append(lineEnd);  
            try {  
                // д���ļ�����  
            	
                output.writeBytes(split.toString());  //д��ǰ��ĸ�ʽ
                //���ļ������ֽ�����д��
                InputStream is = new FileInputStream(file);  
                byte[] bytes = new byte[1024];  
                int len = 0;  
                while ((len = is.read(bytes)) != -1) {  
                    output.write(bytes, 0, len);  
                }  
                is.close();  
                
                output.writeBytes(lineEnd);  //д�����ĸ�ʽ
            } catch (IOException e) {  
                throw new RuntimeException(e);  
            }  
        }  
    }  
      
    /* 
     * �������ֶ����ݣ���ʽ��ο�HTTP Э���ʽ����FireBug����ץȡ��������ݣ���(�Ա��ϴ������Ӧ�Ĳ���ֵ) 
     * ��ʽ������ʾ�� 
     * --****************fD4fH3hK7aI6 
     * Content-Disposition: form-data; name="action" 
     * // һ���У������� 
     * upload 
     */  
    private void addFormField(Map<Object, Object> params, DataOutputStream output) {  
    	if(params==null||params.size()==0){
    		return;
    	}
        StringBuilder sb = new StringBuilder();  
        //ѭ��������ʽд�뵽ͷ�ļ���
        //entrySet()---------------------
        for(Map.Entry<Object, Object> param : params.entrySet()) {  
            sb.append(twoHyphens + boundary + lineEnd);  
            sb.append("Content-Disposition: form-data; name=\"" + param.getKey() + "\"" + lineEnd);  
            sb.append(lineEnd);  
            sb.append(param.getValue() + lineEnd);  
        }  
        try {  
            output.write(sb.toString().getBytes(charset));// д����ֶ����� ,UTF-8����������
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
  
      
   
	public String getEnctype() {
		return enctype;
	}
	
	/**
	 * ����MIME����
	 */
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	public Integer getTimeOut() {
		return timeOut;
	}
	/**
	 * ���ó�ʱʱ��
	 * @param timeOut
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public String getCharset() {
		return charset;
	}
	/**
	 * �����ַ�����
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getRequestMethod() {
		return requestMethod;
	}
	
	/**
	 * ��������ʽ
	 * @param requestMethod
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getConnection() {
		return connection;
	}
	
	/**
	 * ��������
	 * @param connection
	 */
	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getBoundary() {
		return boundary;
	}

	/**
	 * ���÷ָ�����һ��Ĭ�Ͼ���
	 * @param boundary
	 */
	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public List<String> getCookies() {
		return cookies;
	}
	
	/**
	 * ����cookie
	 * @param cookies
	 */
	public void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}  
	
	 

}  

