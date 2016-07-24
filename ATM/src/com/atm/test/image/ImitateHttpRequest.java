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
 * @TODO：多文件上传测试，中文会乱码
 * @fileName : com.atm.test.image.ImitateHttpRequest.java
 * date | author | version |   
 * 2015年10月5日 | Jiong | 1.0 |
 */ 

/**
 * 中文乱码解决：写入到输出流时获取字节采用UTF-8编码（即与服务器一致）
 * @author Jiong
 *
 */

/**
 * TODO: 工具类，模仿浏览器提交表单到服务器,先设置uri再提交
 * @author Jiong
 * @date: 2015年10月06日 22:14:55 修改
 *@使用方法，创建对象，设置一下uri（请求地址），调用post方法即可将参数传到服务器
 *         如果需要访问不同的地址则在每次post前设置一下要访问的uri就可以	 		 
 */
  
public class ImitateHttpRequest {
	
	/*//使用示例
	public static void main(String[] args) throws UnsupportedEncodingException {
		ImitateHttpRequest go = new ImitateHttpRequest();
        String response = "";    
        
        String address = "http://localhost:8080/ATM/"; 
        //address = "http://139.129.131.179/ATM/";
        
        //模仿登录
        Map<Object,Object> loginParams = new HashMap<Object,Object>();
        loginParams.put("userId", "1");
        loginParams.put("password", "1");
        go.setUri(address+"user_login.action");
        response = go.post(loginParams);   
        System.out.println("返回结果：" + response);  
        
        //模仿发帖子
        //表单，即是哪些标题啊什么乱七八糟的内容
        Map<Object,Object> params = new HashMap<Object,Object>();
        params.put("type", "囧哥测试");
        params.put("label", "上传*#多文件");
        params.put("title", "测试乱码");
        params.put("department", "外卖系");
        params.put("content", "多文件上传测试");
        //文件（图片）
        ArrayList<File> list = new ArrayList<File>();
        list.add(new File("E:\\test-0.jpg"));
        list.add(new File("E:\\test-1.jpg"));
        File[] files = (File[])list.toArray(new File[list.size()]);
        //发送请求
        go.setUri(address+"essay/publish.do");
        response = go.post(params, files,"files");  
        System.out.println("返回结果：" + response);  
    
}*/
	
	
	//参数设置
    private String enctype = "multipart/form-data";  //MIME编码
    private Integer timeOut = 120000;  //超时时间
    private String charset = "UTF-8"; //字符编码
    private String requestMethod = "POST"; //请求方式
    private String connection = "keep-alive"; //连接状态
    private static String uri = ""; //连接地址
    
    private String boundary = "****************fD4fH3gL0hK7aI6";    // 数据分隔符 ,即每个数据前都需要加分隔符
    
    
    private List<String> cookies = null;//多次请求时可能用到cookie
    
    
    private String twoHyphens = "--";  
    private String lineEnd = System.getProperty("line.separator");    // The value is "\r\n" in Windows.  
      
    
    /**
     * 上传表单
     * @param params
     * @return
     */
    public String post(Map<Object, Object> params){
    	return post(params, null,null);
    }
    /**
     * 上传文件
     * @param files
     * @param fileName
     * @return
     */
    public String post(File[] files,String key){
    	return post(null, files,key);
    }
    
    /** 
     * 直接通过 HTTP 协议提交数据到服务器，实现表单提交功能。 
     * @param actionUrl 上传路径 
     * @param params 请求参数key为参数名，value为参数值 
     * @param files 上传文件信息 
     * @return 返回请求结果 
     */  
    public String post(Map<Object, Object> params, File[] files,String key) { 
    	if(uri.length()==0){
    		return "未设置uri";
    	}
        HttpURLConnection conn = null;  
        DataOutputStream output = null;  
        BufferedReader input = null;  
        try {  
            URL url = new URL(uri);  
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeOut);
            conn.setRequestProperty("Charset", charset); // 设置编码  
            conn.setRequestMethod(requestMethod); 
            conn.setDoInput(true);        // 允许输入  
            conn.setDoOutput(true);        // 允许输出  
            conn.setUseCaches(false);    // 不使用Cache  
            if(cookies!=null){
	            for (String cookie : cookies) {
	                conn.addRequestProperty("Cookie", cookie.split(";", 2)[0]);
	            }
            }
            conn.setRequestProperty("Connection", connection);  
            conn.setRequestProperty("Content-Type", enctype + "; boundary=" + boundary);  
              
            conn.connect();  
            output = new DataOutputStream(conn.getOutputStream());  
              
            addFileContent(files,key,output);    // 添加图片内容  
            addFormField(params, output);    //表单内容
              
            output.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);// 数据结束标志  
            output.flush();   
            int code = conn.getResponseCode();    
            cookies = conn.getHeaderFields().get("Set-Cookie");
            if(code != 200) {  
                throw new RuntimeException("请求‘" + uri +"’失败！");  
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
            // 统一释放资源  
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
     * 写入图片内容，格式请参考HTTP 协议格式。 
     * 人人网Photos.upload中的”程序调用“http://wiki.dev.renren.com/wiki/Photos.upload#.E7.A8.8B.E5.BA.8F.E8.B0.83.E7.94.A8 
     * 对其格式解释的非常清晰。 
     * 格式如下所示： 
     * --****************fD4fH3hK7aI6 
     * Content-Disposition: form-data; name="upload_file"; filename="apple.jpg" 
     * Content-Type: image/jpeg 
     * 
     * 这儿是文件的内容，二进制流的形式 
     */  
    private void addFileContent(File[] files,String key, DataOutputStream output) {  
    	if(files == null||files.length==0){ //没有文件直接退出方法
    		return;
    	}
    	//循环将文件格式写入到头文件中
        for(File file : files) {  
            StringBuilder split = new StringBuilder();  
            split.append(twoHyphens + boundary + lineEnd);  
            split.append("Content-Disposition: form-data; name=\""+key+"\"; filename=\"" + file.getName() + "\"" + lineEnd);  
            split.append("Content-Type: application/octet-stream; charset=utf-8" + lineEnd);  
            split.append(lineEnd);  
            try {  
                // 写入文件数据  
            	
                output.writeBytes(split.toString());  //写入前面的格式
                //将文件化作字节流并写入
                InputStream is = new FileInputStream(file);  
                byte[] bytes = new byte[1024];  
                int len = 0;  
                while ((len = is.read(bytes)) != -1) {  
                    output.write(bytes, 0, len);  
                }  
                is.close();  
                
                output.writeBytes(lineEnd);  //写入后面的格式
            } catch (IOException e) {  
                throw new RuntimeException(e);  
            }  
        }  
    }  
      
    /* 
     * 构建表单字段内容，格式请参考HTTP 协议格式（用FireBug可以抓取到相关数据）。(以便上传表单相对应的参数值) 
     * 格式如下所示： 
     * --****************fD4fH3hK7aI6 
     * Content-Disposition: form-data; name="action" 
     * // 一空行，必须有 
     * upload 
     */  
    private void addFormField(Map<Object, Object> params, DataOutputStream output) {  
    	if(params==null||params.size()==0){
    		return;
    	}
        StringBuilder sb = new StringBuilder();  
        //循环将表单格式写入到头文件中
        //entrySet()---------------------
        for(Map.Entry<Object, Object> param : params.entrySet()) {  
            sb.append(twoHyphens + boundary + lineEnd);  
            sb.append("Content-Disposition: form-data; name=\"" + param.getKey() + "\"" + lineEnd);  
            sb.append(lineEnd);  
            sb.append(param.getValue() + lineEnd);  
        }  
        try {  
            output.write(sb.toString().getBytes(charset));// 写入表单字段数据 ,UTF-8在这解决乱码
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
  
      
   
	public String getEnctype() {
		return enctype;
	}
	
	/**
	 * 设置MIME编码
	 */
	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}

	public Integer getTimeOut() {
		return timeOut;
	}
	/**
	 * 设置超时时间
	 * @param timeOut
	 */
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}

	public String getCharset() {
		return charset;
	}
	/**
	 * 设置字符编码
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getRequestMethod() {
		return requestMethod;
	}
	
	/**
	 * 设置请求方式
	 * @param requestMethod
	 */
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getConnection() {
		return connection;
	}
	
	/**
	 * 设置连接
	 * @param connection
	 */
	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getBoundary() {
		return boundary;
	}

	/**
	 * 设置分隔符，一般默认就行
	 * @param boundary
	 */
	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}

	public List<String> getCookies() {
		return cookies;
	}
	
	/**
	 * 设置cookie
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

