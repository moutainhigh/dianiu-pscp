/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2018年4月23日 下午3:57:40 
 * @version V1.0
 */
public class ControlDo implements Serializable{
	private static final long serialVersionUID = 1L;
	private AttributeDo attr;
	public AttributeDo getAttr() {
		return attr;
	}
	public void setAttr(AttributeDo attr) {
		this.attr = attr;
	}

}
