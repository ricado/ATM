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
		Thumbnails.of("F:/image/well.jpg").size(50, 100)
				.toFile("G:/ATM/image/test/well1.jpg");
		System.out.println("change success");
		Thumbnails.of("F:/image/well.jpg").size(2560, 2048)
				.toFile("G:/ATM/image/test/well2.jpg");
		System.out.println("change success");
	}
}
