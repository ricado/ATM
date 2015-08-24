package com.atm.test.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JpegTool {
	private boolean isInitFlag = false; // �����Ƿ񼺾���ʼ��
	private String pic_big_pathfilename = null; // ����ԴͼƬ���ڵĴ�·��Ŀ¼���ļ���
	private String pic_small_pathfilename = null; // ����СͼƬ�Ĵ����·��Ŀ¼���ļ���
	private int smallpicwidth = 0; // ��������СͼƬ�Ŀ�Ⱥ͸߶ȣ�����һ���Ϳ�����
	private int smallpicheight = 0;
	private int pic_big_width = 0;
	private int pic_big_height = 0;
	private double picscale = 0; // ����СͼƬ�����ԭͼƬ�ı���

	/**
	 * ���캯��
	 * 
	 * @param û�в���
	 */
	public JpegTool() {
		this.isInitFlag = false;
	}

	/**
	 * ˽�к������������еĲ���
	 * 
	 * @param û�в���
	 * @return û�з��ز���
	 */
	private void resetJpegToolParams() {
		this.picscale = 0;
		this.smallpicwidth = 0;
		this.smallpicheight = 0;
		this.isInitFlag = false;
	}

	/**
	 * @param scale
	 *            ������Ӱͼ�������Դͼ��Ĵ�С������ 0.5
	 * @throws JpegToolException
	 */
	public void SetScale(double scale) throws JpegToolException {
		if (scale <= 0) {
			throw new JpegToolException(" ���ű�������Ϊ 0 �͸����� ");
		}
		this.resetJpegToolParams();
		this.picscale = scale;
		this.isInitFlag = true;
	}

	/**
	 * @param smallpicwidth
	 *            ������Ӱͼ��Ŀ��
	 * @throws JpegToolException
	 */
	public void SetSmallWidth(int smallpicwidth) throws JpegToolException {
		if (smallpicwidth <= 0) {
			throw new JpegToolException(" ��ӰͼƬ�Ŀ�Ȳ���Ϊ 0 �͸����� ");
		}
		this.resetJpegToolParams();
		this.smallpicwidth = smallpicwidth;
		this.isInitFlag = true;
	}

	/**
	 * @param smallpicheight
	 *            ������Ӱͼ��ĸ߶�
	 * @throws JpegToolException
	 */

	public void SetSmallHeight(int smallpicheight) throws JpegToolException {
		if (smallpicheight <= 0) {
			throw new JpegToolException(" ��ӰͼƬ�ĸ߶Ȳ���Ϊ 0 �͸����� ");
		}
		this.resetJpegToolParams();
		this.smallpicheight = smallpicheight;
		this.isInitFlag = true;
	}

	/**
	 * ���ش�ͼƬ·��
	 */
	public String getpic_big_pathfilename() {
		return this.pic_big_pathfilename;
	}

	/**
	 * ����СͼƬ·��
	 */
	public String getpic_small_pathfilename() {
		return this.pic_small_pathfilename;
	}

	public int getsrcw() {
		return this.pic_big_width;
	}

	public int getsrch() {
		return this.pic_big_height;
	}

	/**
	 * ����Դͼ�����Ӱͼ��
	 * 
	 * @param pic_big_pathfilename
	 *            Դͼ���ļ���������·������ windows �� C:\\pic.jpg �� Linux ��
	 *            /home/abner/pic/pic.jpg ��
	 * @param pic_small_pathfilename
	 *            ���ɵ���Ӱͼ���ļ���������·������ windows �� C:\\pic_small.jpg �� Linux ��
	 *            /home/abner/pic/pic_small.jpg ��
	 * @throws JpegToolException
	 */
	public void doFinal(String pic_big_pathfilename,
			String pic_small_pathfilename) throws JpegToolException {
		if (!this.isInitFlag) {
			throw new JpegToolException(" �������û�г�ʼ���� ");
		}
		if (pic_big_pathfilename == null || pic_small_pathfilename == null) {
			throw new JpegToolException(" �����ļ�����·��Ϊ�գ� ");
		}
		if ((!pic_big_pathfilename.toLowerCase().endsWith("jpg"))
				&& (!pic_big_pathfilename.toLowerCase().endsWith("jpeg"))) {
			throw new JpegToolException(" ֻ�ܴ��� JPG/JPEG �ļ��� ");
		}
		if ((!pic_small_pathfilename.toLowerCase().endsWith("jpg"))
				&& !pic_small_pathfilename.toLowerCase().endsWith("jpeg")) {
			throw new JpegToolException(" ֻ�ܴ��� JPG/JPEG �ļ��� ");
		}
		this.pic_big_pathfilename = pic_big_pathfilename;
		this.pic_small_pathfilename = pic_small_pathfilename;
		int smallw = 0;
		int smallh = 0;
		// �½�ԴͼƬ�����ɵ�СͼƬ���ļ�����
		File fi = new File(pic_big_pathfilename);
		File fo = new File(pic_small_pathfilename);
		// ����ͼ��任����
		AffineTransform transform = new AffineTransform();
		// ͨ���������ԴͼƬ�ļ�
		BufferedImage bsrc = null;
		try {
			bsrc = ImageIO.read(fi);
		} catch (IOException ex) {
			throw new JpegToolException(" ��ȡԴͼ���ļ����� ");
		}
		this.pic_big_width = bsrc.getWidth();// ԭͼ��ĳ���
		this.pic_big_height = bsrc.getHeight();// ԭͼ��Ŀ��
		double scale = (double) pic_big_width / pic_big_height;// ͼ��ĳ������
		if (this.smallpicwidth != 0) {// �����趨�Ŀ���������
			smallw = this.smallpicwidth;// �����ɵ�����ͼ��ĳ���
			smallh = (smallw * pic_big_height) / pic_big_width;// �����ɵ�����ͼ��Ŀ��
		} else if (this.smallpicheight != 0) {// �����趨�ĳ���������
			smallh = this.smallpicheight;// �����ɵ�����ͼ��ĳ���
			smallw = (smallh * pic_big_width) / pic_big_height;// �����ɵ�����ͼ��Ŀ��
		} else if (this.picscale != 0) {// �������õ���С��������ͼ��ĳ��Ϳ�
			smallw = (int) ((float) pic_big_width * this.picscale);
			smallh = (int) ((float) pic_big_height * this.picscale);
		} else {
			throw new JpegToolException(" ���������ʼ������ȷ�� ");
		}
		double sx = (double) smallw / pic_big_width;// С/��ͼ��Ŀ�ȱ���
		double sy = (double) smallh / pic_big_height;// С/��ͼ��ĸ߶ȱ���
		transform.setToScale(sx, sy);// ����ͼ��ת���ı���
		// ����ͼ��ת����������
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		// ������Сͼ��Ļ������
		BufferedImage bsmall = new BufferedImage(smallw, smallh,
				BufferedImage.TYPE_3BYTE_BGR);
		// ����Сͼ��
		ato.filter(bsrc, bsmall);
		// ���Сͼ��
		try {
			ImageIO.write(bsmall, "jpeg", fo);
		} catch (IOException ex1) {
			throw new JpegToolException(" д������ͼ���ļ����� ");
		}
	}
}
