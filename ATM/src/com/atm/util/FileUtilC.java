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

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtilC {
	private static final Logger log = LoggerFactory.getLogger(FileUtilC.class);
	private static String basicPath = "";
	public FileUtilC(){
		basicPath = getPath();
	}
	/**
	 * ��ȡȺͷ������ͼ�ļ���
	 * @param path
	 * @return
	 */
	public static String getSmallCrowdHead(String path){
		path = basicPath + path;
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
	 * ��ȡȺͷ��ԭͼ�ļ���
	 * @param path
	 * @return
	 */
	public static String getCrowdHead(String path){
		path = basicPath + path;
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
	 * ��
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeCrowdHeadToByte(String path) throws IOException{
		path = basicPath + path;
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
	 * Ⱥͷ������ͼ��������ֽ�
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeSmallCrowdHeadToByte(String path) throws IOException{
		path = basicPath + path;
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
	 * ���ļ�ת���byte[]����
	 * 
	 * @param fileFath
	 * @return
	 * @throws IOException
	 */
	public static byte[] makeFileToByte(String fileFath) throws IOException {
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
	 * ��ȡ�ļ���byte����
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(SocketChannel socketChannel)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * ��ȡ�ļ���byte����
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] getFileBytes(SocketChannel socketChannel, int length)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// �ļ����ܻ�ܴ�
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
	 * ���������û���ͼƬ
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
		String path = basicPath + "image/user/"
				+ userId + "/" + date;
		path = saveFile(filename, path, filebytes);
		return path;
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
	public static String getUserHeadPath(String extension, String userId,
			byte[] filebytes) throws IOException {
		String path = "/headImage/user/" + userId;
		String smallPath = path + "/" + userId  + "_small." + extension;//����ͼ·��
		String bigPath = saveFile(userId + "." + extension, path, filebytes);//ԭͼ·��
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		saveSmallUserHead(bigPath,smallPath);
		return path;
	}

	/**
	 * �����û�ͷ������ͼ
	 * @param bigPath
	 * @param smallPath
	 * @throws IOException
	 */
	public static void saveSmallUserHead(String bigPath,String smallPath) throws IOException {
		Thumbnails.of(bigPath).size(60, 60)
		.toFile(smallPath);
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
	public static String getCrowdPath(String filename, int crowdId,
			byte[] filebytes) throws IOException {
		String date = (new java.text.SimpleDateFormat("yyyy/MM/dd"))
				.format(new Date());
		String path = basicPath + "crowd/" + crowdId + "/" + date;
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
	public static String getCrowdHeadPath(String extension, String crowdId,
			byte[] filebytes) throws IOException {
		String path = basicPath + "headImage/crowd/" + crowdId;//���ݿⱣ���·��
		log.info("path:" + path);
		
		String smallPath = path + "/" + crowdId  + "_small." + extension;//����ͼ·��
		String bigPath = saveFile(crowdId + "." + extension, path, filebytes);//ԭͼ·��
		
		log.info("smallPath:" + smallPath);
		log.info("bigPath:" + bigPath);
		getSmallCrowdHead(bigPath,smallPath);
		return path;
	}

	/**
	 * ����Ⱥͷ�������ͼ
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
	 * ����˽�������ͼƬ��
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
	 * �����ļ���Ϣ
	 * 
	 * @param filename
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	// TODO �����ļ���Ϣ
	public static String saveFile(String filename, String path, byte[] filebytes)
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
	public String getPath() {
		String path = this.getClass().getResource("/").getPath();
		path = path.substring(1);
		path = path.replaceFirst("WEB-INF/classes/", "");
		return path;
	}
}
