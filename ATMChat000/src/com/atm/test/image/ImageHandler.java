package com.atm.test.image;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ImageHandler {
	public static void main(String[] args) throws IOException {
		// size(宽度, 高度)

		/*
		 * 若图片横比200小，高比300小，不变 若图片横比200小，高比300大，高缩小到300，图片比例不变
		 * 若图片横比200大，高比300小，横缩小到200，图片比例不变 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
		 */
		long time1 = System.currentTimeMillis();
		Thumbnails.of("F:/image/well.jpg").size(50, 100)
				.toFile("G:/myeclipse/apache-tomcat-7.0.27/webapps/ATM/image/headImage/default/default7.jpg");
		long time2 = System.currentTimeMillis();
		System.out.println("change success --" + (time2-time1) + "ms");
		Thumbnails.of("F:/image/well.jpg").size(2560, 2048)
				.toFile("G:/myeclipse/apache-tomcat-7.0.27/webapps/ATM/image/headImage/default/default6.jpg");
		long time3 = System.currentTimeMillis();
		System.out.println("change success --" + (time3-time2) + "ms");
	}
}
