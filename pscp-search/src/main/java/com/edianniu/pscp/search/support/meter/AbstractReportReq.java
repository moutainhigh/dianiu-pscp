/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月7日 下午3:41:11 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月7日 下午3:41:11 
 * @version V1.0
 */
public abstract class AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long companyId;//客户ID
	private String companyName;//客户名称
	private String meterId;//采集点ID/仪表ID
	private Integer meterType;//采集点类型1主线，2楼宇，3设备
	private String subTermCode;//分项编码 B0101
	private Long createTime;
	private Long updateTime;
	protected abstract String getId();

	public Long getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getMeterId() {
		return meterId;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getMeterType() {
		return meterType;
	}

	public void setMeterType(Integer meterType) {
		this.meterType = meterType;
	}

	public String getSubTermCode() {
		return subTermCode;
	}

	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}
	
}
