package com.atm.util;

import java.util.Map;

/**
 *����ͨ�õ�HQL��� 
 * @author ye
 * @2015.7.31
 */
public class HQLUtil {
	/**
	 * ���ͨ�õĲ�ѯ���
	 * @author ye
	 * @param select ��Ҫ��ѯ���ֶ�
	 * @param key Լ���ֶ�
	 * @param value Լ���ֶε�ֵ
	 * @param table ��
	 * @return HQL
	 * @2015.7.31
	 */
	public static String findByMap(String[] select,String[] key,
			String[] value,String table){
		String HQL = "select ";
		for(int i = 0;i<select.length;i++){
			HQL += "t." + select[i];
			if(i+1==select.length){
				HQL += " ";
			}else{
				HQL += ",";
			}
		}
		HQL += "from " + table + " as t where ";
		for(int i = 0;i<key.length;i++){
			HQL += "t." + key[i] + "='" + value[i] + "'";
			if(i+1==key.length){
				return HQL;
			}else{
				HQL += " and ";
			}
		}
		return HQL;
	}
}
