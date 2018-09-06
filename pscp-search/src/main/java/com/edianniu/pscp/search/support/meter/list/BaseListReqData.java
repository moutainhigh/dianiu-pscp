package com.edianniu.pscp.search.support.meter.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.edianniu.pscp.search.support.SortField;
import com.edianniu.pscp.search.support.SortOrder;

/**
 * BaseListReqData
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 上午10:44:45 
 * @version V1.0
 */
public class BaseListReqData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long companyId;
	private String meterId;
	private Integer meterTypes[];//1主线，2楼宇，3设备
	private String subTermCode;//分项编码 B0101
	
	private int offset;
    private Integer pageSize;
    List<SortField> sorts=new ArrayList<SortField>();
    public void addSort(String name,SortOrder sortOrder){
    	SortField sortField=new SortField();
    	sortField.setName(name);
    	sortField.setOrder(sortOrder.toString());
    	sorts.add(sortField);
    }
    public int getOffset() {
		return offset;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public List<SortField> getSorts() {
		return sorts;
	}
	public void setSorts(List<SortField> sorts) {
		this.sorts = sorts;
	}
	public String getMeterId() {
		return meterId;
	}

	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer[] getMeterTypes() {
		return meterTypes;
	}
	public void setMeterTypes(Integer[] meterTypes) {
		this.meterTypes = meterTypes;
	}
	public String getSubTermCode() {
		return subTermCode;
	}
	public void setSubTermCode(String subTermCode) {
		this.subTermCode = subTermCode;
	}
}
