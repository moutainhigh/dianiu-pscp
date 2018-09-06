/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午2:10:28 
 * @version V1.0
 */
package com.edianniu.pscp.message.meter.bean;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午2:10:28 
 * @version V1.0
 */
public class DemandInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long time;
	private Double power;//功率
	public Long getTime() {
		return time;
	}
	public Double getPower() {
		return power;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public void setPower(Double power) {
		this.power = power;
	}

}
