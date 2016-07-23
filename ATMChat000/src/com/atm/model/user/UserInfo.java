package com.atm.model.user;

import java.sql.Timestamp;



/**
 * UserInfo entity. @author MyEclipse Persistence Tools
 */

public class UserInfo  implements java.io.Serializable {


    // Fields    

     private String userId;
     private Major major;
     private String scNo;
     private String dno;
     private String headImagePath;
     private String sign;
     private String name;
     private String nickname;
     private String sex;
     private String jobTitle;
     private Integer flag;
     private Timestamp offTime;

    // Constructors

    /** default constructor */
    public UserInfo() {
    }

	/** minimal constructor */
    public UserInfo(String userId) {
        this.userId = userId;
    }
    
    /** full constructor */
    public UserInfo(String userId, Major major, String scNo, String dno, String headImagePath, String sign, String name, String nickname, String sex, String jobTitle, Integer flag) {
        this.userId = userId;
        this.major = major;
        this.scNo = scNo;
        this.dno = dno;
        this.headImagePath = headImagePath;
        this.sign = sign;
        this.name = name;
        this.nickname = nickname;
        this.sex = sex;
        this.jobTitle = jobTitle;
        this.flag = flag;
    }

   
    // Property accessors

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Major getMajor() {
        return this.major;
    }
    
    public void setMajor(Major major) {
        this.major = major;
    }

    public String getScNo() {
        return this.scNo;
    }
    
    public void setScNo(String scNo) {
        this.scNo = scNo;
    }

    public String getDno() {
        return this.dno;
    }
    
    public void setDno(String dno) {
        this.dno = dno;
    }

    public String getHeadImagePath() {
        return this.headImagePath;
    }
    
    public void setHeadImagePath(String headImagePath) {
        this.headImagePath = headImagePath;
    }

    public String getSign() {
        return this.sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return this.nickname;
    }
    
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return this.sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }
    
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getFlag() {
        return this.flag;
    }
    
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

	public Timestamp getOffTime() {
		return offTime;
	}

	public void setOffTime(Timestamp offTime) {
		this.offTime = offTime;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", headImagePath="
				+ headImagePath + ", sign=" + sign + ", name=" + name
				+ ", nickname=" + nickname + ", sex=" + sex + ", jobTitle="
				+ jobTitle + ", flag=" + flag + ", offTime=" + offTime + "]";
	}
   
}