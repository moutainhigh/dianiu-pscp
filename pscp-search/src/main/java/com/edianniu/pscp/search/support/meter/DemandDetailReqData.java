/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * 需量明细数据 15分钟内的功率平均值
 * <p>
 * type=1 区间算法
 * type=2 滑差算法
 * </p>
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DemandDetailReqData extends AbstractReportReq implements Serializable{
	private static final long serialVersionUID = 1L;
	private Double power;
	private String date;//yyyyMMdd hh:MM
	private Long time;
	private int type=1;//1 区间算法,2滑差算法
	private int value=15;//1 区间间隔时间，2滑差时间
	
	@Override
	public String getId() {
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+this.getDate()+"#"+this.getType()+"#"+this.getValue();
	}
	public Double getPower() {
		return power;
	}

	public String getDate() {
		return date;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
}
