/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月25日 下午4:47:00 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.company;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月25日 下午4:47:00 
 * @version V1.0
 */
public class CompanyDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String leader;
	private String contactTel;
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLeader() {
		return leader;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
}
