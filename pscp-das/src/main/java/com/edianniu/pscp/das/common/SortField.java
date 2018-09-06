/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 上午10:30:39 
 * @version V1.0
 */
package com.edianniu.pscp.das.common;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年12月26日 上午10:30:39 
 * @version V1.0
 */
public class SortField implements Serializable{
	private static final long serialVersionUID = 1L;
    private String name;
    private String order;//asc/desc
    public boolean isDesc(){
    	if("desc".equals(order)){
    		return true;
    	}
    	return false;
    }
    public boolean isAsc(){
    	if("asc".equals(order)){
    		return true;
    	}
    	return false;
    }
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
