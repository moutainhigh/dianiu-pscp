/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:37 
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * ElectricianWorkStatusLog
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:37 
 * @version V1.0
 */
public class ElectricianWorkStatusLog extends BaseDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//
	private Long electricianId;
	private Date onlineTime;
	private Date offlineTime;
	private Integer status;
	public Long getId() {
		return id;
	}
	public Long getElectricianId() {
		return electricianId;
	}
	public Date getOnlineTime() {
		return onlineTime;
	}
	public Date getOfflineTime() {
		return offlineTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}
	public void setOnlineTime(Date onlineTime) {
		this.onlineTime = onlineTime;
	}
	public void setOfflineTime(Date offlineTime) {
		this.offlineTime = offlineTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
