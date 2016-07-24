package com.atm.test.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class JpegTool {
	private boolean isInitFlag = false; // 对象是否己经初始化
	private String pic_big_pathfilename = null; // 定义源图片所在的带路径目录的文件名
	private String pic_small_pathfilename = null; // 生成小图片的带存放路径目录的文件名
	private int smallpicwidth = 0; // 定义生成小图片的宽度和高度，给其一个就可以了
	private int smallpicheight = 0;
	private int pic_big_width = 0;
	private int pic_big_height = 0;
	private double picscale = 0; // 定义小图片的相比原图片的比例

	/**
	 * 构造函数
	 * 
	 * @param 没有参数
	 */
	public JpegTool() {
		this.isInitFlag = false;
	}

	/**
	 * 私有函数，重置所有的参数
	 * 
	 * @param 没有参数
	 * @return 没有返回参数
	 */
	private void resetJpegToolParams() {
		this.picscale = 0;
		this.smallpicwidth = 0;
		this.smallpicheight = 0;
		this.isInitFlag = false;
	}

	/**
	 * @param scale
	 *            设置缩影图像相对于源图像的大小比例如 0.5
	 * @throws JpegToolException
	 */
	public void SetScale(double scale) throws JpegToolException {
		if (scale <= 0) {
			throw new JpegToolException(" 缩放比例不能为 0 和负数！ ");
		}
		this.resetJpegToolParams();
		this.picscale = scale;
		this.isInitFlag = true;
	}

	/**
	 * @param smallpicwidth
	 *            设置缩影图像的宽度
	 * @throws JpegToolException
	 */
	public void SetSmallWidth(int smallpicwidth) throws JpegToolException {
		if (smallpicwidth <= 0) {
			throw new JpegToolException(" 缩影图片的宽度不能为 0 和负数！ ");
		}
		this.resetJpegToolParams();
		this.smallpicwidth = smallpicwidth;
		this.isInitFlag = true;
	}

	/**
	 * @param smallpicheight
	 *            设置缩影图像的高度
	 * @throws JpegToolException
	 */

	public void SetSmallHeight(int smallpicheight) throws JpegToolException {
		if (smallpicheight <= 0) {
			throw new JpegToolException(" 缩影图片的高度不能为 0 和负数！ ");
		}
		this.resetJpegToolParams();
		this.smallpicheight = smallpicheight;
		this.isInitFlag = true;
	}

	/**
	 * 返回大图片路径
	 */
	public String getpic_big_pathfilename() {
		return this.pic_big_pathfilename;
	}

	/**
	 * 返回小图片路径
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
	 * 生成源图像的缩影图像
	 * 
	 * @param pic_big_pathfilename
	 *            源图像文件名，包含路径（如 windows 下 C:\\pic.jpg ； Linux 下
	 *            /home/abner/pic/pic.jpg ）
	 * @param pic_small_pathfilename
	 *            生成的缩影图像文件名，包含路径（如 windows 下 C:\\pic_small.jpg ； Linux 下
	 *            /home/abner/pic/pic_small.jpg ）
	 * @throws JpegToolException
	 */
	public void doFinal(String pic_big_pathfilename,
			String pic_small_pathfilename) throws JpegToolException {
		if (!this.isInitFlag) {
			throw new JpegToolException(" 对象参数没有初始化！ ");
		}
		if (pic_big_pathfilename == null || pic_small_pathfilename == null) {
			throw new JpegToolException(" 包含文件名的路径为空！ ");
		}
		if ((!pic_big_pathfilename.toLowerCase().endsWith("jpg"))
				&& (!pic_big_pathfilename.toLowerCase().endsWith("jpeg"))) {
			throw new JpegToolException(" 只能处理 JPG/JPEG 文件！ ");
		}
		if ((!pic_small_pathfilename.toLowerCase().endsWith("jpg"))
				&& !pic_small_pathfilename.toLowerCase().endsWith("jpeg")) {
			throw new JpegToolException(" 只能处理 JPG/JPEG 文件！ ");
		}
		this.pic_big_pathfilename = pic_big_pathfilename;
		this.pic_small_pathfilename = pic_small_pathfilename;
		int smallw = 0;
		int smallh = 0;
		// 新建源图片和生成的小图片的文件对象
		File fi = new File(pic_big_pathfilename);
		File fo = new File(pic_small_pathfilename);
		// 生成图像变换对象
		AffineTransform transform = new AffineTransform();
		// 通过缓冲读入源图片文件
		BufferedImage bsrc = null;
		try {
			bsrc = ImageIO.read(fi);
		} catch (IOException ex) {
			throw new JpegToolException(" 读取源图像文件出错！ ");
		}
		this.pic_big_width = bsrc.getWidth();// 原图像的长度
		this.pic_big_height = bsrc.getHeight();// 原图像的宽度
		double scale = (double) pic_big_width / pic_big_height;// 图像的长宽比例
		if (this.smallpicwidth != 0) {// 根据设定的宽度求出长度
			smallw = this.smallpicwidth;// 新生成的缩略图像的长度
			smallh = (smallw * pic_big_height) / pic_big_width;// 新生成的缩略图像的宽度
		} else if (this.smallpicheight != 0) {// 根据设定的长度求出宽度
			smallh = this.smallpicheight;// 新生成的缩略图像的长度
			smallw = (smallh * pic_big_width) / pic_big_height;// 新生成的缩略图像的宽度
		} else if (this.picscale != 0) {// 根据设置的缩小比例设置图像的长和宽
			smallw = (int) ((float) pic_big_width * this.picscale);
			smallh = (int) ((float) pic_big_height * this.picscale);
		} else {
			throw new JpegToolException(" 对象参数初始化不正确！ ");
		}
		double sx = (double) smallw / pic_big_width;// 小/大图像的宽度比例
		double sy = (double) smallh / pic_big_height;// 小/大图像的高度比例
		transform.setToScale(sx, sy);// 设置图像转换的比例
		// 生成图像转换操作对象
		AffineTransformOp ato = new AffineTransformOp(transform, null);
		// 生成缩小图像的缓冲对象
		BufferedImage bsmall = new BufferedImage(smallw, smallh,
				BufferedImage.TYPE_3BYTE_BGR);
		// 生成小图像
		ato.filter(bsrc, bsmall);
		// 输出小图像
		try {
			ImageIO.write(bsmall, "jpeg", fo);
		} catch (IOException ex1) {
			throw new JpegToolException(" 写入缩略图像文件出错！ ");
		}
	}
}
