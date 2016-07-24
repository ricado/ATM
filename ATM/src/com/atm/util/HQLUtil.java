package com.atm.util;

import java.util.Map;

/**
 *返回通用的HQL语句 
 * @author ye
 * @2015.7.31
 */
public class HQLUtil {
	/**
	 * 设计通用的查询语句
	 * @author ye
	 * @param select 想要查询的字段
	 * @param key 约束字段
	 * @param value 约束字段的值
	 * @param table 表
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
