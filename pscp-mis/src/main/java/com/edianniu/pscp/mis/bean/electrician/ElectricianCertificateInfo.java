/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月14日 上午11:58:27 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.electrician;

import java.io.Serializable;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月14日 上午11:58:27
 * @version V1.0
 */
public class ElectricianCertificateInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long electricianId;
	private Long certificateId;
	private String name;
	private Integer status=0;
	private Integer orderNum=0;
	private String remark;
	public Long getId() {
		return id;
	}
	public Long getElectricianId() {
		return electricianId;
	}
	public Long getCertificateId() {
		return certificateId;
	}
	public String getName() {
		return name;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}

	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
