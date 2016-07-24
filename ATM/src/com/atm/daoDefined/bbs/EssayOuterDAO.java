/**
 * 
 */
package com.atm.daoDefined.bbs;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.atm.model.define.bbs.EssayOuter;



/**
 * @TODO：EssayOuter实体的数据操作对象，对应表（视图）essayOuterView
 * @fileName : com.atm.daoDefined.EssayOuterDAO.java
 * date | author | version |   
 * 2015年7月30日 | Jiong | 1.0 |
 */
@Transactional
public class EssayOuterDAO {
	  private static final Logger log = Logger.getLogger(EssayOuterDAO.class);
	  
	  private SessionFactory sessionFactory;
	  
	  public void setSessionFactory(SessionFactory sessionFactory){
	       this.sessionFactory = sessionFactory;
	   }

	  private Session getCurrentSession(){
	    return sessionFactory.getCurrentSession(); 
	  }
	  protected void initDao() {
			//do nothing
	  }
	  /*
	   * 获取根据传入的参数获取帖子
	   * @first:起始查询点
	   * @num：查询条数
	   * @propertyName：查询依据（降序）
	   */
	  public List getSomeEssay(int first,int num,String sort) {
	        log.debug("获取第"+(first+1)+"到第"+(num+first)+"条记录ing");
	        try {
	            String queryString = "select new EssayOuter(essayId,essayType,"+
	            		"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
	            		"replyNum,publisherId,headImagePath) "+
	            		"from EssayOuter order by "
	            			+ sort + " desc";
	            Query queryObject = getCurrentSession().createQuery(queryString);
	   		 	queryObject.setFirstResult(first);
	   		 	queryObject.setMaxResults(num);
	   		 return queryObject.list();
	         } catch (RuntimeException re) {
	            log.error("获取帖子失败", re);
	            throw re;
	         }
	  }
	  
	  //获取帖子发布者ID
	  public String getPublisher(int essayId) {
	        try {
	            String queryString = "select publisherId "+
	            		"from EssayOuter where essayId="+essayId;
	            Query queryObject = getCurrentSession().createQuery(queryString);
	            return queryObject.uniqueResult().toString();
	         } catch (RuntimeException re) {
	            log.error("获取帖子失败", re);
	            throw re;
	         }
	  }
	  //TODO　获取用户发布的帖子数
		public int getPublishedEssayNum(Object userId){
			try {
				String queryString = 
						"select count(essayId)"+
								"from EssayOuter as model where model."
									+"publisherId= ?";
				Query queryObject = getCurrentSession().createQuery(queryString);
				queryObject.setParameter(0, userId);
				return Integer.parseInt(queryObject.uniqueResult().toString());
			} catch (RuntimeException re) {
				throw re;
			}
		}
	  
	  /*
	   * 返回评论数前五的热门贴
	   */
	  public List getHotEssay(){
		  log.debug("获取热门贴ing");
		  return getSomeEssay(0,5,"replyNum");//返回前5条记录
	  }
	  
	  /*
	   * 返回最新的十条帖子（以用户刷新处为起点)
	   */
	  public List getCurrentEssay(int first){
		  log.debug("获取当前最新贴，从第"+(first+1)+"条开始");
		  return getSomeEssay(first,10,"publishTime");
	  }
	  
	  /*
	   * 根据字段查询
	   * @propertyName:条件字段--------value:字段值
	   * @first：查询起始位置----------sort：排序方式
	   */
	  public List getByProperty(String propertyName,Object value,int first,String sort,int num){
		  try {
	            String queryString = 
	            		 		"select new EssayOuter(essayId,essayType,"+
	     	            		"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
	     	            		"replyNum,publisherId,headImagePath) "+
	     	            		"from EssayOuter as model where model."
	            		+  propertyName + "= ? order by model."
	            			+ sort + " desc";
	            Query queryObject = getCurrentSession().createQuery(queryString);
	            queryObject.setParameter(0, value);
	   		 	queryObject.setFirstResult(first);
	   		 	queryObject.setMaxResults(num);
	   		 return queryObject.list();
	         } catch (RuntimeException re) {
	            log.error("获取帖子失败", re);
	            throw re;
	         }
	  }
	 /*
	  * 返回用户发布的十条帖子
	  */
	  public List getPublishedEssay(String userId,int first,int num){
		  return getByProperty("publisherId",userId,first,"publishTime",num);
	  }
	  //获取某系部的帖子
	 /* 
	   * @first:起始查询点
	   *@ dNo :系部编号
	   *
	   */
	  public List getDeptEssay(int first,Object dNo) {
	        log.debug("系部"+dNo+"获取第"+(first+1)+"条记录开始");
	        try {
	            String queryString = "select new EssayOuter(essayId,essayType,"+
	            		"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
	            		"replyNum,publisherId,headImagePath) "+
	            		"from EssayOuter where dNo=? order by publishTime desc";
	            Query queryObject = getCurrentSession().createQuery(queryString);
	            queryObject.setParameter(0, dNo);
	   		 	queryObject.setFirstResult(first);
	   		 	queryObject.setMaxResults(10);
	   		 return queryObject.list();
	         } catch (RuntimeException re) {
	            log.error("获取帖子失败", re);
	            throw re;
	         }
	  }
	  
	  //获取用户关注的人和标签的帖子
		 /* 
		   * @first:起始查询点
		   *@ 关注人和标签集合
		   *
		   */
		  public List getAttendEssay(int first,List<String> ids,Object labelCondition) {
		        log.debug("关注的人的帖子：获取第"+(first+1)+"条记录开始");
		        String condition="";
		        if(ids!=null&&ids.size()>0){
		        	condition += " publisherId in (:userID) ";
		        	if(labelCondition.toString().length()!=0){
			        	condition += " or "+labelCondition;
			        }
		        }else{
		        	condition = labelCondition+"";
		        }
		        try {
		        	//因为使用了find_in_set所以用本地sql(全部字段都加载了)
		            String queryString = "select essayId,essayType,"+
		            		"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
		            		"replyNum,publisherId,headImagePath,department,dNo,labId,content "+
		            		"from atm.essayOuterView where "
		            		+condition+" order by publishTime desc";
		            Query queryObject = getCurrentSession().createSQLQuery(queryString).addEntity(EssayOuter.class);
		            if(ids!=null&&ids.size()>0){
		            	queryObject.setParameterList("userID",ids);
		            }
		   		 	queryObject.setFirstResult(first);
		   		 	queryObject.setMaxResults(10);
		   		 return queryObject.list();
		         } catch (RuntimeException re) {
		            log.error("获取帖子失败", re);
		            throw re;
		         }
		  }

	//根据关键词搜索帖子
  public List searchPeople(Object condition,int index){
 	try {
	  		String queryString = "select new EssayOuter(essayId,essayType,"+
	  							"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
	  							"replyNum,publisherId,headImagePath) "+
	  				"from EssayOuter as model where model." +
    	       						"labName like ? or model." +//标签名
    	       						"essayType like ? or model."+//帖子类型
    	       						"title like ? or model."+//标题
    	       						"fullContent like ? "+//正文
    	       						"order by "+
    	       						"case "+
    	       						"when labName like ? "+
    	       						//标签先显示
    	       							"then 1 "+
    	       						"when essayType like ? "+
    	       							"then (length(essayType)-length('"+condition+"')) "+
    	       						"when title like ? "+
    	    	       					"then (length(title)-length('"+condition+"')) "+
    	       						"else "+
    	    	       					"case when (length(fullContent)-length('"+condition+"'))>50 "+
    	    	       					//正文减去关键词长度在50以上的按时间倒序
    	    	       					"then 100 else (length(fullContent)-length('"+condition+"')) end "+
    	       						"end,publishTime desc";
    	       Query queryObject = getCurrentSession().createQuery(queryString);
    	       queryObject.setParameter(0, "%"+condition+"%");
    	       queryObject.setParameter(1, "%"+condition+"%");
    	       queryObject.setParameter(2, "%"+condition+"%");
    	       queryObject.setParameter(3, "%"+condition+"%");
    	       queryObject.setParameter(4, "%"+condition+"%");
    	       queryObject.setParameter(5, "%"+condition+"%");
    	       queryObject.setParameter(6, "%"+condition+"%");
    	       queryObject.setFirstResult(index);
    	       queryObject.setMaxResults(10);
    		   return queryObject.list();
    	   } catch (RuntimeException re) {
    	        log.error("find by property name failed", re);
    	        throw re;
    	   }
    }
}

