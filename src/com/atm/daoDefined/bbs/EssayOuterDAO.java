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
 * @TODO��EssayOuterʵ������ݲ������󣬶�Ӧ����ͼ��essayOuterView
 * @fileName : com.atm.daoDefined.EssayOuterDAO.java
 * date | author | version |   
 * 2015��7��30�� | Jiong | 1.0 |
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
	   * ��ȡ���ݴ���Ĳ�����ȡ����
	   * @first:��ʼ��ѯ��
	   * @num����ѯ����
	   * @propertyName����ѯ���ݣ�����
	   */
	  public List getSomeEssay(int first,int num,String sort) {
	        log.debug("��ȡ��"+(first+1)+"����"+(num+first)+"����¼ing");
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
	            log.error("��ȡ����ʧ��", re);
	            throw re;
	         }
	  }
	  
	  //��ȡ���ӷ�����ID
	  public String getPublisher(int essayId) {
	        try {
	            String queryString = "select publisherId "+
	            		"from EssayOuter where essayId="+essayId;
	            Query queryObject = getCurrentSession().createQuery(queryString);
	            return queryObject.uniqueResult().toString();
	         } catch (RuntimeException re) {
	            log.error("��ȡ����ʧ��", re);
	            throw re;
	         }
	  }
	  //TODO����ȡ�û�������������
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
	   * ����������ǰ���������
	   */
	  public List getHotEssay(){
		  log.debug("��ȡ������ing");
		  return getSomeEssay(0,5,"replyNum");//����ǰ5����¼
	  }
	  
	  /*
	   * �������µ�ʮ�����ӣ����û�ˢ�´�Ϊ���)
	   */
	  public List getCurrentEssay(int first){
		  log.debug("��ȡ��ǰ���������ӵ�"+(first+1)+"����ʼ");
		  return getSomeEssay(first,10,"publishTime");
	  }
	  
	  /*
	   * �����ֶβ�ѯ
	   * @propertyName:�����ֶ�--------value:�ֶ�ֵ
	   * @first����ѯ��ʼλ��----------sort������ʽ
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
	            log.error("��ȡ����ʧ��", re);
	            throw re;
	         }
	  }
	 /*
	  * �����û�������ʮ������
	  */
	  public List getPublishedEssay(String userId,int first,int num){
		  return getByProperty("publisherId",userId,first,"publishTime",num);
	  }
	  //��ȡĳϵ��������
	 /* 
	   * @first:��ʼ��ѯ��
	   *@ dNo :ϵ�����
	   *
	   */
	  public List getDeptEssay(int first,Object dNo) {
	        log.debug("ϵ��"+dNo+"��ȡ��"+(first+1)+"����¼��ʼ");
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
	            log.error("��ȡ����ʧ��", re);
	            throw re;
	         }
	  }
	  
	  //��ȡ�û���ע���˺ͱ�ǩ������
		 /* 
		   * @first:��ʼ��ѯ��
		   *@ ��ע�˺ͱ�ǩ����
		   *
		   */
		  public List getAttendEssay(int first,List<String> ids,Object labelCondition) {
		        log.debug("��ע���˵����ӣ���ȡ��"+(first+1)+"����¼��ʼ");
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
		        	//��Ϊʹ����find_in_set�����ñ���sql(ȫ���ֶζ�������)
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
		            log.error("��ȡ����ʧ��", re);
		            throw re;
		         }
		  }

	//���ݹؼ�����������
  public List searchPeople(Object condition,int index){
 	try {
	  		String queryString = "select new EssayOuter(essayId,essayType,"+
	  							"title,labName,labColor,someContent,nickname,publishTime,clickGoodNum,"+
	  							"replyNum,publisherId,headImagePath) "+
	  				"from EssayOuter as model where model." +
    	       						"labName like ? or model." +//��ǩ��
    	       						"essayType like ? or model."+//��������
    	       						"title like ? or model."+//����
    	       						"fullContent like ? "+//����
    	       						"order by "+
    	       						"case "+
    	       						"when labName like ? "+
    	       						//��ǩ����ʾ
    	       							"then 1 "+
    	       						"when essayType like ? "+
    	       							"then (length(essayType)-length('"+condition+"')) "+
    	       						"when title like ? "+
    	    	       					"then (length(title)-length('"+condition+"')) "+
    	       						"else "+
    	    	       					"case when (length(fullContent)-length('"+condition+"'))>50 "+
    	    	       					//���ļ�ȥ�ؼ��ʳ�����50���ϵİ�ʱ�䵹��
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

