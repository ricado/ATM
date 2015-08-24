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
		Thumbnails.of("F:/image/well.jpg").size(50, 100)
				.toFile("G:/ATM/image/test/well1.jpg");
		System.out.println("change success");
		Thumbnails.of("F:/image/well.jpg").size(2560, 2048)
				.toFile("G:/ATM/image/test/well2.jpg");
		System.out.println("change success");
	}
}
