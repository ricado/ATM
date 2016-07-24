package com.atm.chat.nio.server.handler;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atm.util.FileUtil;

public class ImageHandler extends Thread {

	private static final Logger log = 
			LoggerFactory.getLogger(ImageHandler.class);
	//��׺��
	private String extension = null;
	//ͼ���ֽ�����
	private byte[] imagebytes;
	//crowd userId
	private int type;
	//�˺�
	private String number;

	public ImageHandler(String extension, byte[] imagebytes, int type,
			String number) {
		this.extension = extension;
		this.imagebytes = imagebytes;
		this.type = type;
		this.number = number;
	}

	public void run() {
		if (type == 1) {
			try {
				log.info("-------------->saveCrowdHeandImage");
				saveCrowdHeadImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("-------------->saveCrowdHeandImage------error---------");
			}
		} else {
			try {
				log.info("-------------->saveUserIdHeandImage");
				saveUserHeadImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.info("-------------->saveUserIdHeandImage------error---------");
			}
		}
	}

	/**
	 * ����Ⱥ��ͼƬ
	 * @throws IOException
	 */
	public void saveCrowdHeadImage() throws IOException {
		
		String path = FileUtil.getCrowdHeadPath(extension, number, imagebytes);
	}

	public void saveUserHeadImage() throws IOException {
		
		String path = FileUtil.getUserHeadPath(extension, number, imagebytes);
	}
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getNumber() {
		return number;
	}

	public void setId(String number) {
		this.number = number;
	}

	public byte[] getImagebytes() {
		return imagebytes;
	}

	public void setImagebytes(byte[] imagebytes) {
		this.imagebytes = imagebytes;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
