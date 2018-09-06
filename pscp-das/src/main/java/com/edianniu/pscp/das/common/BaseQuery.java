package com.edianniu.pscp.das.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * ClassName: BaseQuery
 * Author: tandingbo
 * CreateTime: 2017-10-17 23:22
 */
public class BaseQuery implements Serializable {
    private static final long serialVersionUID = 8355169386653703576L;
    
    private int offset;
    private int pageSize = Constants.DEFAULT_PAGE_SIZE;
    private List<SortField> sorts=new ArrayList<SortField>();
    public void addSort(String name,String order){
    	SortField sortField=new SortField();
    	sortField.setName(name);
    	sortField.setOrder(order);
    	sorts.add(sortField);
    }
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
	public List<SortField> getSorts() {
		return sorts;
	}
	public void setSorts(List<SortField> sorts) {
		this.sorts = sorts;
	}
	
}
