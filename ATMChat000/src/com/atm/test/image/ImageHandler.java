package com.atm.test.image;

import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

public class ImageHandler {
	public static void main(String[] args) throws IOException {
		// size(���, �߶�)

		/*
		 * ��ͼƬ���200С���߱�300С������ ��ͼƬ���200С���߱�300�󣬸���С��300��ͼƬ��������
		 * ��ͼƬ���200�󣬸߱�300С������С��200��ͼƬ�������� ��ͼƬ���200�󣬸߱�300��ͼƬ��������С����Ϊ200���Ϊ300
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
