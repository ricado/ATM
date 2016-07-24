package com.atm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtil extends Path {
	private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

	public ImageUtil() {
		super();
	}

	/************************ 获取用户头像,群头像的原图文件，缩略图文件的文件名 *****************************/
	/**
	 * 获取头像的文件名
	 * 
	 * @param file
	 * @param isSmall
	 * @return
	 */
	private String getHeadFilename(File file, boolean isSmall) {
		if (!file.exists()) {
			return "error";
		}
		String[] filenames = file.list();
		if (filenames.length == 0) {
			return "error";
		}
		log.info("filenames[0] : " + filenames[0] + " filenames[1]:"
				+ filenames[1]);
		if ((filenames[0].length() > filenames[1].length() && isSmall)
				|| (filenames[0].length() < filenames[1].length() && !isSmall)) {
			return filenames[0];
		} else {
			return filenames[1];
		}
	}

	/**
	 * 获取群聊头像
	 * 
	 * @param crowdId
	 * @param isSmall
	 * @return
	 */
	private String getCrowdHead(String crowdId, boolean isSmall) {
		String path = getCrowdPath() + crowdId;
		File file = new File(path);
		return getHeadFilename(file, isSmall);
	}

	/**
	 * 获取群头像缩略图文件名
	 * 
	 * @param crowdId
	 * @return filename
	 */
	public String getCrowdSmallHead(String crowdId) {
		log.info("获取群头像的缩略图文件名");
		String filename = getCrowdHead(crowdId, true);
		if (filename.equals("error")) {
			return crowdSHeadName;
		}
		return filename;
	}

	/**
	 * 获取群头像原图文件名
	 * 
	 * @param crowdId
	 * @return filename
	 */
	public String getCrowdHead(String crowdId) {
		log.info("获取群头像原图文件名");
		String filename = getCrowdHead(crowdId, false);
		if (filename.equals("error")) {
			return crowdHeadName;
		}
		return filename;
	}

	/**
	 * 
	 * @param userId
	 * @param isSmall
	 * @return filename
	 */
	private String getUserHead(String userId, boolean isSmall) {
		String path = getUserPath() + userId;
		File file = new File(path);
		return getHeadFilename(file, isSmall);
	}

	/**
	 * 获取用户头像缩略图文件名
	 * 
	 * @param userId
	 * @return filename
	 */
	public String getUserSmallHead(String userId) {
		log.info("获取用户头像的缩略图文件名");
		String filename = getCrowdHead(userId, true);
		if (filename.equals("error")) {
			return userSHeadName;
		}
		return filename;
	}

	/**
	 * 获取用户头像原图文件名
	 * 
	 * @param userId
	 * @return filename
	 */
	public String getUserHead(String userId) {
		log.info("获取用户原图文件名");
		String filename = getCrowdHead(userId, false);
		if (filename.equals("error")) {
			return userHeadName;
		}
		return filename;
	}

	/************************ 获取用户头像,群头像的原图文件，缩略图文件的路径 *****************************/

	/**
	 * 获取用户头像文件的路径
	 * 
	 * @param userId
	 * @return path
	 */
	public String getUserHeadPath(String userId) {
		log.info("获取用户头像文件的路径");
		String filename = getUserHead(userId);
		log.info("filename:" + filename);
		if (filename.equals(userHeadName)) {
			return getDefaultUserHeadPath();
		} else {
			return getUserPath() + filename;
		}
	}

	/**
	 * 获取用户头像缩略图文件的路径
	 * 
	 * @param userId
	 * @return path
	 */
	public String getUserSmallHeadPath(String userId) {
		log.info("获取用户头像缩略图文件的路径");
		String filename = getUserSmallHead(userId);
		log.info("filename:" + filename);
		if (filename.equals(userSHeadName)) {
			return getDefaultUserSHeadPath();
		} else {
			return getUserPath() + filename;
		}
	}

	/**
	 * 获取群头像文件的路径
	 * 
	 * @param crowdId
	 * @return path
	 */
	public String getCrowdHeadPath(String crowdId) {
		log.info("获取群头像文件的路径");
		String filename = getCrowdHead(crowdId);
		log.info("filename:" + filename);
		if (filename.equals(crowdHeadName)) {
			return getDefaultCrowdHeadPath();
		} else {
			return getCrowdPath() + filename;
		}
	}

	/**
	 * 获取群头像缩略文件的路径
	 * 
	 * @param crowdId
	 * @return path
	 */
	public String getCrowdSmallHeadPath(String crowdId) {
		log.info("获取群头像缩略文件的路径");
		String filename = getCrowdSmallHead(crowdId);
		log.info("filename:" + filename);
		if (filename.equals(crowdSHeadName)) {
			return getDefaultCrowdSHeadPath();
		} else {
			return getCrowdPath() + filename;
		}
	}

	/***********************************/

	/**
	 * 将文件转变成byte[]数组
	 * 
	 * @param fileFath
	 * @return
	 * @throws IOException
	 */
	public byte[] makeFileToByte(String fileFath) throws IOException {
		File file = new File(fileFath);
		FileInputStream fis = new FileInputStream(file);
		int length = (int) file.length();
		log.info("length:" + length);
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
	 * 让群聊头像变成字节数组
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public byte[] makeCrowdHeadToByte(String crowdId) throws IOException {
		String path = getCrowdHeadPath(crowdId);
		log.info("filename:" + path);
		return makeFileToByte(path);
	}

	/**
	 * 群头像缩略图变成数组字节
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public byte[] makeCrowdSmallHeadToByte(String crowdId) throws IOException {
		String path = getCrowdSmallHeadPath(crowdId);
		log.info("filename:" + path);
		return makeFileToByte(path);
	}

	/**
	 * 让用户头像变成字节数组
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public byte[] makeUserHeadToByte(String userId) throws IOException {
		String path = getUserHeadPath(userId);
		log.info("filename:" + path);
		return makeFileToByte(path);
	}

	/**
	 * 用户头像缩略图编程数组字节
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public byte[] makeUserSmallHeadToByte(String userId) throws IOException {
		String path = getUserSmallHeadPath(userId);
		log.info("filename:" + path);
		return makeFileToByte(path);
	}

	/*******************************************************/

	/**
	 * 获取socketChannel中的文件byte数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileBytes(SocketChannel socketChannel) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		int size = 0;
		byte[] bytes = null;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		log.info("----------------");
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
	 * 获取socketChannel中的文件byte数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileBytes(SocketChannel socketChannel, int length)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 文件可能会很大
		int size = 0;
		byte[] bytes = null;
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

		log.info("11");
		int i = 0;
		while (length > 0) {
			if (length < 1024) {
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
	 * 保存用户头像
	 * 
	 * @param filename
	 * @param userId
	 * @param filebytes
	 * @return
	 * @throws IOException
	 */
	public String saveUserHeadPath(String extension, String userId,
			byte[] filebytes) throws IOException {
		String smallPath = getUserPath() + userId + "/" + userId + "_small."
				+ extension;// 头像缩略图路径
		String bigPath = saveFile(userId + "." + extension, getUserPath()
				+ userId + "/", filebytes);// 原图路径
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveUserSmallHead(bigPath, smallPath);
		return "image/headImage/user/" + userId + "/" + userId + "."
				+ extension;
	}

	/**
	 * 保存用户头像缩略图
	 * 
	 * @param bigPath
	 * @param smallPath
	 * @throws IOException
	 */
	public void saveUserSmallHead(String bigPath, String smallPath)
			throws IOException {
		Thumbnails.of(bigPath).size(60, 60).toFile(smallPath);
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
	public String saveCrowdChatPath(String filename, int crowdId,
			byte[] filebytes) throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = getCrowdChatPath() + crowdId + "/" + date;
		// 独一无二的文件名
		filename = UUID.randomUUID().toString() + "_" + filename;

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
	public String saveCrowdHeadPath(String extension, String crowdId,
			byte[] filebytes) throws IOException {
		// g://...//ATM/image/headImage/crowd/ 10001 / 10001_small.jpg
		String smallPath = getUserPath() + crowdId + "/" + crowdId + "_small."
				+ extension;// 头像缩略图路径
		String bigPath = saveFile(crowdId + "." + extension, getUserPath()
				+ crowdId + "/", filebytes);// 原图路径
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveUserSmallHead(bigPath, smallPath);
		return "image/headImage/crowd/" + crowdId + "/" + crowdId + "."
				+ extension;
	}

	/**
	 * 保存群头像的缩略图
	 * 
	 * @param bigPath
	 * @param smallPath
	 * @throws IOException
	 */
	public void saveSmallCrowdHead(String bigPath, String smallPath)
			throws IOException {
		Thumbnails.of(bigPath).size(60, 60).toFile(smallPath);
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
	public String savePrivateChatPath(String filename, String userId,
			String friendId, byte[] filebytes) throws IOException {
		// 日期
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		// 项目路径/../user/userId_friendId/2015/10/5/filename
		String path = getPrivateChatPath() + userId + "_" + friendId + "/"
				+ date;
		// 独一无二的文件名
		filename = UUID.randomUUID().toString() + "_" + filename;

		if (new File(path).exists()) {
			path = saveFile(filename, path, filebytes);
		} else {
			path = getPrivateChatPath() + friendId + "_" + userId + "/" + date;
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
	public String saveFile(String filename, String path, byte[] filebytes)
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

	/************** MultipartFile ***********************/

	/**
	 * 保存MultipartFile文件
	 * 
	 * @param file
	 * @param targetFile
	 * @return
	 */
	public boolean saveFile(MultipartFile file, File targetFile) {
		// 如果没有这个文件，就生成一个
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 保存MultipartFile文件
	 * 
	 * @param file
	 * @param targetFile
	 * @return
	 */
	public String saveNewsImage(MultipartFile file, String newsId) {
		String imagePath = "image/news/";
		String path = getBasicPath() + imagePath;
		log.info("path:" + path);
		String filename = file.getOriginalFilename();
		String smallFileName = newsId + "_small_" + filename;
		filename = newsId + "_" + filename;

		log.info("filename:" + filename + ",smallname:" + smallFileName);
		
		

		File file2 = new File(path + filename);

		saveFile(file, file2);
		// 保存为缩略图
		JpgThumbnail
				.BigImageToSmallImage(path + filename, path + smallFileName);

//		JpgThumbnail.BigImageToSmallImage(path + filename, path + filename,
//				1200, 1600);

		return imagePath + smallFileName;
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param file
	 * @return
	 */
	public String getFileSuffix(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		log.info("filename:" + fileName);
		String[] strings = fileName.split("[#.?]");
		for (int i = 0; i < strings.length; i++) {
			log.info("string:" + strings[i]);
		}
		String suffix = strings[1];
		log.info("suffix:" + suffix);
		return suffix;
	}

	/**
	 * 保存用户头像 MultipartFile
	 * 
	 * @param file
	 * @param userId
	 * @return
	 */
	public String saveUserHead(MultipartFile file, String userId) {
		String suffix = getFileSuffix(file);
		String smallFileName = userId + "/" + userId + "_small." + suffix;
		String bigFileName = userId + "/" + userId + "." + suffix;
		log.info("smallFileName:" + smallFileName);
		String path = getUserPath();
		log.info("path:" + path + bigFileName);
		File targetFile = new File(path, bigFileName);
		// saveUserSmallHead(path + bigFileName, path + smallFileName);
		saveFile(file, targetFile);
		new Thread() {
			public void run() {
				log.info("转化缩略图");
				//
				JpgThumbnail.BigImageToSmallImage(path + bigFileName, path
						+ smallFileName);
				/*
				 * Thumbnails.of(path + bigFileName).size(100, 100)
				 * .outputQuality(1).toFile(path + smallFileName);
				 */
			}
		}.start();
		return "image/headImage/user/" + smallFileName;
	}

	public String getPath() {
		String path = this.getClass().getResource("/").getPath();
		path = path.substring(1);
		path = path.replaceFirst("WEB-INF/classes/", "");
		return path;
	}
}