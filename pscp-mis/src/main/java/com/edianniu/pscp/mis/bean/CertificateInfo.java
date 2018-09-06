/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月3日 下午3:49:43 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月3日 下午3:49:43
 * @version V1.0
 */
public class CertificateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Integer type;
	private String remark;
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getType() {
		return type;
	}
	public String getRemark() {
		return remark;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
