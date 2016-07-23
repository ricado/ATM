package com.atm.util;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {

	private static final Logger log = 
			LoggerFactory.getLogger(MD5Util.class);
	
	
	/***
	 * MD5��������32λmd5��
	 * @param inStr
	 * @return
	 * @throws Exception
	 */
	public static String md5Encode(String inStr) throws Exception {

		MessageDigest md5 = null;
		 try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			// TODO: handle exception
			log.info(e.toString());
			e.printStackTrace();
			return "";
		}
		 
		 byte[] byteArray = inStr.getBytes("UTF-8");
		 byte[] md5Bytes = md5.digest(byteArray);
		 
		 StringBuffer hexValue = new StringBuffer();
		 for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int)md5Bytes[i])&0xff;
			if(val<16){
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		 return hexValue.toString();
		 
	}
	
    /**
     * ����������
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {
        String str = new String("2");
        System.out.println("ԭʼ��" + str);
        String str1 = md5Encode(str);
        System.out.println("��һ��MD5��" + str1);
        String str2 = md5Encode(str1);
        System.out.println("�ڶ���MD5��" + str2);
        String astr = new String("1");
        System.out.println("ԭʼ��" + astr);
        String astr1 = md5Encode(astr);
        System.out.println("��һ��MD5��" + astr1);
        String astr2 = md5Encode(astr1);
        System.out.println("�ڶ���MD5��" + astr2);
    }
}
