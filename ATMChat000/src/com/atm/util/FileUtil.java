package com.atm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
	private static String basicPath = "C:/websoft/tomcat7_64/webapps/ATM/image/";
	
	/**
	 * 获取群头像缩略图文件名
	 * @param path
	 * @return
	 */
	public static String getSmallCrowdHead(String path){
		File file = new File(path);
		String[] filenames = file.list();
		String filename = filenames[0];
		if(filenames[0].length()<filenames[1].length()){
			filename = filenames[1];
		}
		log.info("filename:" + filename);
		return filename;
	}
	/**
	 * 获取群头像原图文件名
	 * @param path
	 * @return
	 */
	public static String getCrowdHead(String path){
		File file = new File(path);
		String[] filenames = file.list();
		String filename = filenames[0];
		if(filenames[0].length()>filenames[1].length()){
			filename = filenames[1];
		}
		log.info("filename:" + filename);
		return filename;
	}
	/**
	 * 让
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeCrowdHeadToByte(String path) throws IOException{
		File file = new File(path);
		String[] filenames = file.list();
		String filename = filenames[0];
		if(filenames[0].length()>filenames[1].length()){
			filename = filenames[1];
		}
		log.info("filename:" + filename);
		return makeFileToByte(path + "/" + filename);
	}
	/**
	 * 群头像缩略图编程数组字节
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeSmallCrowdHeadToByte(String path) throws IOException{
		File file = new File(path);
		String[] filenames = file.list();
		String filename = filenames[0];
		if(filenames[0].length()<filenames[1].length()){
			filename = filenames[1];
		}
		log.info("filename:" + filename);
		return makeFileToByte(path + "/" + filename);
	}
	/**
	 * 将文件转变成byte[]数组
	 * 
	 * @param fileFath
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeFileToByte(String fileFath) throws IOException {
		File file = new File(fileFath);
		byte[] bytes = makeFileToByte(file);
		return bytes;
	}

	public static byte[] makeFileToByte(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		int length = (int) file.length();
		byte[] bytes = new byte[length];
		int temp = 0;
		int index = 0;
		int i = 0;
		while (true) {
			index = fis.read(bytes, temp, length - temp);
			if (index <= 0)
				break;
			temp += index;
			log.info("----------" + i++);
		}
		fis.close();
		return bytes;
	}
	
	/**
	 * 获取文件的byte数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(SocketChannel socketChannel)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		int size = 0;
		byte[] bytes = null;
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		log.info("11");
		int i = 0;
		while ((size = socketChannel.read(buffer)) > 0) {
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
			log.info(i++ + "");
		}
		return baos.toByteArray();
	}

	/**
	 * 获取文件的byte数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(SocketChannel socketChannel, int length)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		int size = 0;
		byte[] bytes = null;
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

		log.info("11");
		int i = 0;
		while (length > 0) {
			if(length < 1024){
				buffer = ByteBuffer.allocateDirect(length);
			}
			size = socketChannel.read(buffer);
			log.info("-------------size:" + size);
			buffer.flip();
			bytes = new byte[size];
			buffer.get(bytes);
			baos.write(bytes);
			buffer.clear();
			log.info(i++ + "");
			length = length - size;
			log.info("------------length-size:" + length);
		}
		return baos.toByteArray();
	}

	/**
	 * 保存属于用户的图片
	 * 
	 * @param filename
	 * @param userId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public static String getUserPath(String filename, String userId,
			byte[] filebytes) throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = "G:/myeclipse/apache-tomcat-7.0.27/webapps/ATM/WEB-INF/image/user/"
				+ userId + "/" + date;
		path = saveFile(filename, path, filebytes);
		return path;
	}

	/**
	 * 保存用户头像
	 * 
	 * @param filename
	 * @param userId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public static String getUserHeadPath(String extension, String userId,
			byte[] filebytes) throws IOException {
		String path = "/headImage/user/" + userId;
		String smallPath = path + "/" + userId  + "_small." + extension;//缩略图路径
		String bigPath = saveFile(userId + "." + extension, path, filebytes);//原图路径
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveSmallUserHead(bigPath,smallPath);
		return path;
	}

	/**
	 * 保存用户头像缩略图
	 * @param bigPath
	 * @param smallPath
	 * @throws IOException
	 */
	public static void saveSmallUserHead(String bigPath,String smallPath) throws IOException {
		Thumbnails.of(bigPath).size(60, 60)
		.toFile(smallPath);
	}

	/**
	 * 保存属于群聊的图片
	 * 
	 * @param filename
	 * @param crowdId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public static String getCrowdPath(String filename, int crowdId,
			byte[] filebytes) throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = basicPath + "crowd/" + crowdId + "/" + date;
		path = saveFile(filename, path, filebytes);
		return path;
	}

	/**
	 * 存放群头像
	 * 
	 * @param filename
	 * @param crowdId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public static String getCrowdHeadPath(String extension, String crowdId,
			byte[] filebytes) throws IOException {
		String path = basicPath + "headImage/crowd/" + crowdId;//数据库保存的路径
		log.info("path:" + path);
		
		String smallPath = path + "/" + crowdId  + "_small." + extension;//缩略图路径
		String bigPath = saveFile(crowdId + "." + extension, path, filebytes);//原图路径
		
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		getSmallCrowdHead(bigPath,smallPath);
		return path;
	}

	/**
	 * 保存群头像的缩略图
	 * @param bigPath
	 * @param smallPath
	 * @throws IOException
	 */
	public static void getSmallCrowdHead(String bigPath,String smallPath)
			throws IOException {
		Thumbnails.of(bigPath).size(60, 60)
			.toFile(smallPath);
	}

	/**
	 * 保存私信聊天的图片。
	 * 
	 * @param filename
	 * @param userId
	 * @param friendId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public static String getPrivateChatPath(String filename, String userId,
			String friendId, byte[] filebytes) throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = basicPath + "user/" + userId + "_" + friendId + "/"
				+ date;
		if (new File(path).exists()) {
			path = saveFile(filename, path, filebytes);
		} else {
			path = basicPath + "user/" + friendId + "_" + userId + "/" + date;
			path = saveFile(filename, path, filebytes);
		}
		return path;
	}

	/**
	 * 保存文件消息
	 * 
	 * @param filename
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	// TODO 保存文件消息
	public static String saveFile(String filename, String path, byte[] filebytes)
			throws IOException {
		// 获取文件
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream os = null;
		new File(path + "/" + filename);
		try {
			os = new FileOutputStream(new File(path + "/" + filename));
			os.write(filebytes);
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info(path + "/" + filename);
		return path + "/" + filename;
	}
	
	/**
	 * 下载网络上的文件
	 * 
	 * @param urlString
	 * @param savePath
	 * @param filename
	 * @throws IOException
	 */
	public static void download(String urlString, String savePath, String filename)
			throws IOException {
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 设置请求超时为10s
		con.setConnectTimeout(10 * 1000);
		// 输入流
		InputStream is = con.getInputStream();

		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
	}

}