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

	/************************ ��ȡ�û�ͷ��,Ⱥͷ���ԭͼ�ļ�������ͼ�ļ����ļ��� *****************************/
	/**
	 * ��ȡͷ����ļ���
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
	 * ��ȡȺ��ͷ��
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
	 * ��ȡȺͷ������ͼ�ļ���
	 * 
	 * @param crowdId
	 * @return filename
	 */
	public String getCrowdSmallHead(String crowdId) {
		log.info("��ȡȺͷ�������ͼ�ļ���");
		String filename = getCrowdHead(crowdId, true);
		if (filename.equals("error")) {
			return crowdSHeadName;
		}
		return filename;
	}

	/**
	 * ��ȡȺͷ��ԭͼ�ļ���
	 * 
	 * @param crowdId
	 * @return filename
	 */
	public String getCrowdHead(String crowdId) {
		log.info("��ȡȺͷ��ԭͼ�ļ���");
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
	 * ��ȡ�û�ͷ������ͼ�ļ���
	 * 
	 * @param userId
	 * @return filename
	 */
	public String getUserSmallHead(String userId) {
		log.info("��ȡ�û�ͷ�������ͼ�ļ���");
		String filename = getCrowdHead(userId, true);
		if (filename.equals("error")) {
			return userSHeadName;
		}
		return filename;
	}

	/**
	 * ��ȡ�û�ͷ��ԭͼ�ļ���
	 * 
	 * @param userId
	 * @return filename
	 */
	public String getUserHead(String userId) {
		log.info("��ȡ�û�ԭͼ�ļ���");
		String filename = getCrowdHead(userId, false);
		if (filename.equals("error")) {
			return userHeadName;
		}
		return filename;
	}

	/************************ ��ȡ�û�ͷ��,Ⱥͷ���ԭͼ�ļ�������ͼ�ļ���·�� *****************************/

	/**
	 * ��ȡ�û�ͷ���ļ���·��
	 * 
	 * @param userId
	 * @return path
	 */
	public String getUserHeadPath(String userId) {
		log.info("��ȡ�û�ͷ���ļ���·��");
		String filename = getUserHead(userId);
		log.info("filename:" + filename);
		if (filename.equals(userHeadName)) {
			return getDefaultUserHeadPath();
		} else {
			return getUserPath() + filename;
		}
	}

	/**
	 * ��ȡ�û�ͷ������ͼ�ļ���·��
	 * 
	 * @param userId
	 * @return path
	 */
	public String getUserSmallHeadPath(String userId) {
		log.info("��ȡ�û�ͷ������ͼ�ļ���·��");
		String filename = getUserSmallHead(userId);
		log.info("filename:" + filename);
		if (filename.equals(userSHeadName)) {
			return getDefaultUserSHeadPath();
		} else {
			return getUserPath() + filename;
		}
	}

	/**
	 * ��ȡȺͷ���ļ���·��
	 * 
	 * @param crowdId
	 * @return path
	 */
	public String getCrowdHeadPath(String crowdId) {
		log.info("��ȡȺͷ���ļ���·��");
		String filename = getCrowdHead(crowdId);
		log.info("filename:" + filename);
		if (filename.equals(crowdHeadName)) {
			return getDefaultCrowdHeadPath();
		} else {
			return getCrowdPath() + filename;
		}
	}

	/**
	 * ��ȡȺͷ�������ļ���·��
	 * 
	 * @param crowdId
	 * @return path
	 */
	public String getCrowdSmallHeadPath(String crowdId) {
		log.info("��ȡȺͷ�������ļ���·��");
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
	 * ���ļ�ת���byte[]����
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
	 * ��Ⱥ��ͷ�����ֽ�����
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
	 * Ⱥͷ������ͼ��������ֽ�
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
	 * ���û�ͷ�����ֽ�����
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
	 * �û�ͷ������ͼ��������ֽ�
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
	 * ��ȡsocketChannel�е��ļ�byte����
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileBytes(SocketChannel socketChannel) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * ��ȡsocketChannel�е��ļ�byte����
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] getFileBytes(SocketChannel socketChannel, int length)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * �����û�ͷ��
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
				+ extension;// ͷ������ͼ·��
		String bigPath = saveFile(userId + "." + extension, getUserPath()
				+ userId + "/", filebytes);// ԭͼ·��
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveUserSmallHead(bigPath, smallPath);
		return "image/headImage/user/" + userId + "/" + userId + "."
				+ extension;
	}

	/**
	 * �����û�ͷ������ͼ
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
	 * ��������Ⱥ�ĵ�ͼƬ
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
		// ��һ�޶����ļ���
		filename = UUID.randomUUID().toString() + "_" + filename;

		path = saveFile(filename, path, filebytes);
		return path;
	}

	/**
	 * ���Ⱥͷ��
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
				+ extension;// ͷ������ͼ·��
		String bigPath = saveFile(crowdId + "." + extension, getUserPath()
				+ crowdId + "/", filebytes);// ԭͼ·��
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveUserSmallHead(bigPath, smallPath);
		return "image/headImage/crowd/" + crowdId + "/" + crowdId + "."
				+ extension;
	}

	/**
	 * ����Ⱥͷ�������ͼ
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
	 * ����˽�������ͼƬ��
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
		// ����
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		// ��Ŀ·��/../user/userId_friendId/2015/10/5/filename
		String path = getPrivateChatPath() + userId + "_" + friendId + "/"
				+ date;
		// ��һ�޶����ļ���
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
	 * �����ļ���Ϣ
	 * 
	 * @param filename
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	// TODO �����ļ���Ϣ
	public String saveFile(String filename, String path, byte[] filebytes)
			throws IOException {
		// ��ȡ�ļ�
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
	 * ����MultipartFile�ļ�
	 * 
	 * @param file
	 * @param targetFile
	 * @return
	 */
	public boolean saveFile(MultipartFile file, File targetFile) {
		// ���û������ļ���������һ��
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// ����
		try {
			file.transferTo(targetFile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ����MultipartFile�ļ�
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
		// ����Ϊ����ͼ
		JpgThumbnail
				.BigImageToSmallImage(path + filename, path + smallFileName);

//		JpgThumbnail.BigImageToSmallImage(path + filename, path + filename,
//				1200, 1600);

		return imagePath + smallFileName;
	}

	/**
	 * ��ȡ�ļ���׺��
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
	 * �����û�ͷ�� MultipartFile
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
				log.info("ת������ͼ");
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