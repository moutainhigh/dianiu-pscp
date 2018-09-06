/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter;

import java.io.Serializable;

/**
 * 日用电量
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DayElectricReqData extends AbstractReportReq  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String date;//年月日(yyyyMMdd)
	/**有功电量kwh**/
	private Double total;//用电量
	private Double apex;//尖电量
	private Double peak;//峰电量
	private Double flat;//平电量
	private Double valley;//谷电量
	/**无功电量kvar**/
	private Double reactiveTotal;
    @Override
	public String getId() {//主键
		
		return super.getCompanyId()+"#"+super.getMeterId()+"#"+getDate();
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getTotal() {
		return total;
	}

	public Double getApex() {
		return apex;
	}

	public Double getPeak() {
		return peak;
	}
	public Double getFlat() {
		return flat;
	}
	public Double getValley() {
		return valley;
	}
	public Double getReactiveTotal() {
		return reactiveTotal;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public void setApex(Double apex) {
		this.apex = apex;
	}

	public void setPeak(Double peak) {
		this.peak = peak;
	}

	public void setFlat(Double flat) {
		this.flat = flat;
	}

	public void setValley(Double valley) {
		this.valley = valley;
	}

	public void setReactiveTotal(Double reactiveTotal) {
		this.reactiveTotal = reactiveTotal;
	}
}
