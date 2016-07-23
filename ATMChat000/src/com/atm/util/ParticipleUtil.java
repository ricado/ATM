package com.atm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * �ִʵĹ��߰�
 * 
 * @author ye
 *
 */
public class ParticipleUtil {

	public static void main(String[] args) {
		ParticipleUtil util = new ParticipleUtil();
		String[] keys = participleString(" I Lov675e �й�67�� ��� ��ɽ    ");
		for (int i = 0; i < keys.length; i++) {
			System.out.println("��" + i + "���ִ�Ϊ:" + keys[i]);
		}
		// System.out.println("\"��\"�Ƿ��Ǻ���:" + isChinessWord('��'));
		// System.out.println("\"a\"�Ƿ��Ǻ���:" + isChinessWord('a'));
	}

	/**
	 * Ĭ�ϵķִ�,��Ӣ��(��Сд������),����,���Ķ����зִ� �ִʹ���:<br>
	 * 1.Ӣ������֮���пո���ַ�����Ϊһ��Ӣ�ĵ���,��(1)(2)<br>
	 * 2.Ӣ������������֮��û�пո�,Ҳ��,��(3)(4)<br>
	 * 3.����֮��Ŀո񽫻�ȥ�� �ִ�Ч��:<br>
	 * (1)testString(���Ե��ַ���): <b>I love �й��� </b><br>
	 * participleString(�ִʺ��Ч��): <b>[I,love,�й�������] </b><br>
	 * (2)testString(���Ե��ַ���): <b>Ilove �й���</b> <br>
	 * participleString(�ִʺ��Ч��): <b>[Ilove,�й�,����]</b> <br>
	 * (3)testString(���Ե��ַ���): <b>Ilove�й���</b> <br>
	 * participleString(�ִʺ��Ч��): <b>[Ilove,�й�,����]</b><br>
	 * (4)testString(���Ե��ַ���): <b>Ilove64�й���</b> <br>
	 * participleString(�ִʺ��Ч��): <b>[Ilove,64,�й�,����]</b><br>
	 * (5)testString(���Ե��ַ���): <b>Ilove64��34���� ���ɽ��</b> <br>
	 * participleString(�ִʺ��Ч��): <b>[Ilove,64,��,34,����,�˴�,���,��ɽ,ɽ��]</b><br>
	 * �ִʲ���:<br>
	 * 1.ͳһСд 2.���ַ������ַ�������н���
	 * 
	 * @return
	 */
	public static String[] participleString(String keyword) {
		List<String> keyList = new ArrayList<String>();
		// �����ַ���
		String ch_string = "";
		// ͳһСд
		keyword = keyword.toLowerCase();
		// ȥǰ��Ŀո�
		keyword = keyword.trim();
		System.out.println("����������keyword:" + keyword);
		// ����ַ�����
		char[] keyChars = keyword.toCharArray();
		String key = "";
		// ���Ӣ�ﵥ���Լ�����
		for (int i = 0; i < keyChars.length; i++) {
			if (keyChars[i] >= 'a' && keyChars[i] <= 'z') {
				// Ӣ�ĵ���
				int j = i;
				while (keyChars[j] >= 'a' && keyChars[j] <= 'z') {
					key += keyChars[j++];
				}
				keyList.add(key);
				i = j - 1;
			} else if (keyChars[i] >= '0' && keyChars[i] <= '9') {
				// ����
				int j = i;
				while (keyChars[j] >= '0' && keyChars[j] <= '9') {
					key += keyChars[j++];
				}
				keyList.add(key);
				i = j - 1;
			} else if (isChinessWord(keyChars[i])) {
				// ���������һ���������ַ���
				ch_string += keyChars[i];
			}
			key = "";
		}

		// ���ķִ�
		for (int i = 0; i < ch_string.length() - 1; i++) {
			keyList.add(ch_string.substring(i, i + 2));
		}
		if (keyList.size() > 0) {
			return keyList.toArray(new String[keyList.size()]);
		} else {
			return null;
		}
	}

	/**
	 * �ж��Ƿ�cΪ����
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinessWord(char c) {
		if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			return true;
		}
		return false;
	}

	/**
	 * �ж��Ƿ�cΪ���ֻ������ı�����
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS // ���պ�ͳһ��������
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS // ���պ������ַ�
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A// ���պ�ͳһ������������A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION // һ�������,�ж����ĵġ���
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION// ���źͱ��,�ж����ĵġ���
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // ��Ǽ�ȫ���ַ�,�ж����ĵģ���
		) {
			return true;
		}
		return false;
	}
}
