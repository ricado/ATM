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
 * @fileName : com.atm.test.image.manyFile.java
 * date | author | version |   
 * 2015��10��5�� | Jiong | 1.0 |
 */ 

/**
 * ������������д�뵽�����ʱ��ȡ�ֽڲ���UTF-8���루���������һ�£�
 * @author Jiong
 *
 */
  
/* 
 * �޸���ʷ�� 
 */  
public class manyFile {  
    String multipart_form_data = "multipart/form-data";  
    String twoHyphens = "--";  
    String boundary = "****************fD4fH3gL0hK7aI6";    // ���ݷָ���  
    String lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.  
      
    /* 
     * �ϴ�ͼƬ���ݣ���ʽ��ο�HTTP Э���ʽ�� 
     * ������Photos.upload�еġ�������á�http://wiki.dev.renren.com/wiki/Photos.upload#.E7.A8.8B.E5.BA.8F.E8.B0.83.E7.94.A8 
     * �����ʽ���͵ķǳ������� 
     * ��ʽ������ʾ�� 
     * --****************fD4fH3hK7aI6 
     * Content-Disposition: form-data; name="upload_file"; filename="apple.jpg" 
     * Content-Type: image/jpeg 
     * 
     * ������ļ������ݣ�������������ʽ 
     */  
    private void addFileContent(File[] files, DataOutputStream output) {  
    	if(files == null){ //û���ļ�ֱ���˳�����
    		return;
    	}
    	//ѭ�����ļ���ʽд�뵽ͷ�ļ���
        for(File file : files) {  
            StringBuilder split = new StringBuilder();  
            split.append(twoHyphens + boundary + lineEnd);  
            //�����files��Ҫ�ͷ��������յ�����һ����������Ϊfiles
            split.append("Content-Disposition: form-data; name=\"files\"; filename=\"" + file.getName() + "\"" + lineEnd);  
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
            output.write(sb.toString().getBytes("UTF-8"));// д����ֶ����� ,UTF-8����������
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    }  
      
    /** 
     * ֱ��ͨ�� HTTP Э���ύ���ݵ���������ʵ�ֱ��ύ���ܡ� 
     * @param actionUrl �ϴ�·�� 
     * @param params �������keyΪ��������valueΪ����ֵ 
     * @param files �ϴ��ļ���Ϣ 
     * @return ���������� 
     */  
    List<String> cookies = null;//�������ʱ�����õ�cookie������ֱ���ô�java�����ԣ�
    public String post(String actionUrl, Map<Object, Object> params, File[] files) {  
        HttpURLConnection conn = null;  
        DataOutputStream output = null;  
        BufferedReader input = null;  
        try {  
            URL url = new URL(actionUrl);  
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(120000);
            conn.setRequestProperty("Charset", "utf-8"); // ���ñ���  
            conn.setDoInput(true);        // ��������  
            conn.setDoOutput(true);        // �������  
            conn.setUseCaches(false);    // ��ʹ��Cache  
            conn.setRequestMethod("POST");  
            if(cookies!=null){
            	System.out.println("��ȡ��cookie");
	            for (String cookie : cookies) {
	                conn.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
	            }
            }
            conn.setRequestProperty("Connection", "keep-alive");  
            conn.setRequestProperty("Content-Type", multipart_form_data + "; boundary=" + boundary);  
              
            conn.connect();  
            output = new DataOutputStream(conn.getOutputStream());  
              
            addFileContent(files, output);    // ���ͼƬ����  
             addFormField(params, output);    //������
              
            output.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);// ���ݽ�����־  
            output.flush();   
            int code = conn.getResponseCode();    
            cookies = conn.getHeaderFields().get("Set-Cookie"); 
            if(code != 200) {  
                throw new RuntimeException("����" + actionUrl +"��ʧ�ܣ�");  
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
      
    public static void main(String[] args) throws UnsupportedEncodingException {
    	
    	//System.out.println(System.getProperty("file.encoding"));
    	
    		manyFile go = new manyFile();
    		
            String response = "";    
            	//������ͼƬ�ķ�������·���ĳ����������������
            String address = "http://localhost:8080/ATM/"; 
            address = "http://139.129.131.179/ATM/";
            
            String actionUrl = address+"essay/publish.do";
            
            //�ȵ�¼
            String loginUrl = address+"user_login.action";
            Map<Object,Object> loginParams = new HashMap<Object,Object>();
            loginParams.put("userId", "1");
            loginParams.put("password", "1");
            response = go.post(loginUrl, loginParams, null);   
            
            System.out.println("���ؽ����" + response);  
            
            //����������Щ���Ⱑʲô���߰��������
            Map<Object,Object> params = new HashMap<Object,Object>();
            params.put("type", "����");
            params.put("label", "�ϴ�*#���ļ�");
            params.put("title", "��������2");
            params.put("department", "����ϵ");
            params.put("content", "���ļ��ϴ�����");
            
            //�ļ���ͼƬ��
            ArrayList<File> list = new ArrayList<File>();
            list.add(new File("E:\\test-0.jpg"));
            list.add(new File("E:\\test-1.jpg"));
            File[] files = (File[])list.toArray(new File[list.size()]);
            
            //��������
            response = go.post(actionUrl, params, files);  
            System.out.println("���ؽ����" + response);  
        
    }  
}  

