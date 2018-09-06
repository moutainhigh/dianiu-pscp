/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月23日 下午4:44:05 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.log;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年11月23日 下午4:44:05 
 * @version V1.0
 */
public class NetQueryInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private DefaultAttribute attr;
	private String query="query";
	public DefaultAttribute getAttr() {
		return attr;
	}
	public String getQuery() {
		return query;
	}
	public void setAttr(DefaultAttribute attr) {
		this.attr = attr;
	}
	public void setQuery(String query) {
		this.query = query;
	}

}
