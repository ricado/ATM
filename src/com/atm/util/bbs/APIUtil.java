package com.atm.util.bbs;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.atm.service.bbs.EssayDeal;

/**
 * @TODO：内部接口调用
 * @fileName : com.atm.util.bbs.APIUtil.java
 * date | author | version |   
 * 2016年11月14日 | Jiong | 1.0 |
 */
public class APIUtil implements ObjectInterface{
	
	//TODO 返回最新收藏
//	{
//	    "collectContent": "JRE英文哦哟哟",
//	    "collectEssayId": 160,
//	    "collectTitle": "horning",
//	    "collectNum": 3
//	}
	public static JSONObject getLastCollectedEssay(String userId){
		JSONObject result = null;
		EssayDeal deal = //只读相关操作
				(EssayDeal) context.getBean("EssayDeal");
		try {
			result =  deal.getLastCollectedEssay(userId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//TODO 返回最新发布
//	{
//	    "publishTitle": "给？",
//	    "publishContent": "呵呵#(exp3)#(exp4)#(exp17)#(exp9)#(exp2)#(exp9)#(exp",
//	    "publishEssayId": 161,
//	    "publishNum": 15
//	}
	public static JSONObject getLastPublishedEssay(String userId){
		JSONObject result = null;
		EssayDeal deal = //只读相关操作
				(EssayDeal) context.getBean("EssayDeal");
		try {
			result =  deal.getLastPublishedEssay(userId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}

