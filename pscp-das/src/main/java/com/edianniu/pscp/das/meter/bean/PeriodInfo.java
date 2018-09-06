/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午2:39:55 
 * @version V1.0
 */
package com.edianniu.pscp.das.meter.bean;

import java.io.Serializable;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2018年1月10日 下午2:39:55
 * @version V1.0
 */
public class PeriodInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long start;
	private Long end;
	private String time;
	
	public boolean include(Long time){
		if(time>=this.start&&time<this.end){
			return true;
		}
		return false;
	}

	public Long getStart() {
		return start;
	}

	public Long getEnd() {
		return end;
	}

	public String getTime() {
		return time;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
