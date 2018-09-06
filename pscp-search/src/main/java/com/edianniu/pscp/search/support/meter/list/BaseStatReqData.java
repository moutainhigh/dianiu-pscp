/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月8日 下午4:54:15 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月8日 下午4:54:15 
 * @version V1.0
 */
public class BaseStatReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	//source=MONTH_LOG yyyyMM
	//source=DAY_LOG yyyyMMdd
	private String fromDate;
	private String toDate;

	/**
	 * source=DAY_LOG  支持：YEAR|MONTH|DAY 
	 * source=MONTH_LOG 支持：YEAR|MONTH
	 * 
	 */
	private String type;
	private String source;//MONTH_LOG/DAY_LOG
	public String getFromDate() {
		return fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
}
