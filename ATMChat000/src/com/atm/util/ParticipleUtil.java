package com.atm.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分词的工具包
 * 
 * @author ye
 *
 */
public class ParticipleUtil {

	public static void main(String[] args) {
		ParticipleUtil util = new ParticipleUtil();
		String[] keys = participleString(" I Lov675e 中国67菜 大好 河山    ");
		for (int i = 0; i < keys.length; i++) {
			System.out.println("第" + i + "个分词为:" + keys[i]);
		}
		// System.out.println("\"中\"是否是汉字:" + isChinessWord('中'));
		// System.out.println("\"a\"是否是汉字:" + isChinessWord('a'));
	}

	/**
	 * 默认的分词,对英文(大小写不敏感),数字,中文都进行分词 分词规则:<br>
	 * 1.英文数字之间有空格则分否则视为一个英文单词,如(1)(2)<br>
	 * 2.英文与中文数字之间没有空格,也分,如(3)(4)<br>
	 * 3.中文之间的空格将会去掉 分词效果:<br>
	 * (1)testString(测试的字符串): <b>I love 中国菜 </b><br>
	 * participleString(分词后的效果): <b>[I,love,中国，国菜] </b><br>
	 * (2)testString(测试的字符串): <b>Ilove 中国菜</b> <br>
	 * participleString(分词后的效果): <b>[Ilove,中国,国菜]</b> <br>
	 * (3)testString(测试的字符串): <b>Ilove中国菜</b> <br>
	 * participleString(分词后的效果): <b>[Ilove,中国,国菜]</b><br>
	 * (4)testString(测试的字符串): <b>Ilove64中国菜</b> <br>
	 * participleString(分词后的效果): <b>[Ilove,64,中国,国菜]</b><br>
	 * (5)testString(测试的字符串): <b>Ilove64中34国菜 大好山河</b> <br>
	 * participleString(分词后的效果): <b>[Ilove,64,中,34,国菜,菜大,大好,好山,山河]</b><br>
	 * 分词步骤:<br>
	 * 1.统一小写 2.对字符串的字符数组进行解析
	 * 
	 * @return
	 */
	public static String[] participleString(String keyword) {
		List<String> keyList = new ArrayList<String>();
		// 中文字符串
		String ch_string = "";
		// 统一小写
		keyword = keyword.toLowerCase();
		// 去前后的空格
		keyword = keyword.trim();
		System.out.println("经过处理后的keyword:" + keyword);
		// 拆成字符数组
		char[] keyChars = keyword.toCharArray();
		String key = "";
		// 辨别英语单词以及数字
		for (int i = 0; i < keyChars.length; i++) {
			if (keyChars[i] >= 'a' && keyChars[i] <= 'z') {
				// 英文单词
				int j = i;
				while (keyChars[j] >= 'a' && keyChars[j] <= 'z') {
					key += keyChars[j++];
				}
				keyList.add(key);
				i = j - 1;
			} else if (keyChars[i] >= '0' && keyChars[i] <= '9') {
				// 数字
				int j = i;
				while (keyChars[j] >= '0' && keyChars[j] <= '9') {
					key += keyChars[j++];
				}
				keyList.add(key);
				i = j - 1;
			} else if (isChinessWord(keyChars[i])) {
				// 将汉字组成一串连续的字符串
				ch_string += keyChars[i];
			}
			key = "";
		}

		// 中文分词
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
	 * 判断是否c为汉字
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
	 * 判断是否c为汉字或者中文标点符号
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS // 中日韩统一表意文字
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS // 中日韩兼容字符
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A// 中日韩统一表意文字扩充A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION // 一般标点符号,判断中文的“号
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION// 符号和标点,判断中文的。号
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS // 半角及全角字符,判断中文的，号
		) {
			return true;
		}
		return false;
	}
}
