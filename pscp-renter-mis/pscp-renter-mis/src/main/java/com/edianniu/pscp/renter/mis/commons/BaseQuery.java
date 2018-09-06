/**
 * 
 */
package com.edianniu.pscp.renter.mis.commons;

import java.io.Serializable;

/**
 * @author cyl
 *
 */
public class BaseQuery implements Serializable{
	private static final long serialVersionUID = 1L;
	private int offset;
	private int pageSize=Constants.DEFAULT_PAGE_SIZE;
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
