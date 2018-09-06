/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;

/**
 * 仪表实时日志查询
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月5日 下午4:45:48 
 * @version V1.0
 */
public class DistinctMeterLogListReqData  extends BaseListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
    private Long startTime;//查询区间
    private Long endTime;//查询区间
    private Integer collapseSize=1;//默认折叠数量
	public Long getStartTime() {
		return startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getCollapseSize() {
		return collapseSize;
	}

	public void setCollapseSize(Integer collapseSize) {
		this.collapseSize = collapseSize;
	}
}
