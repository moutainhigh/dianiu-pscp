/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月23日 下午6:43:42 
 * @version V1.0
 */
package com.edianniu.pscp.sps.bean.user;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月23日 下午6:43:42 
 * @version V1.0
 */
public class UpdateUserReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long uid;
	public Long getUid() {
		return uid;
	}
	public String getTxImg() {
		return txImg;
	}
	public String getNickName() {
		return nickName;
	}
	public Integer getSex() {
		return sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public void setTxImg(String txImg) {
		this.txImg = txImg;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	private String txImg;
	private String nickName;
	private Integer sex;
	private Integer age;
}
