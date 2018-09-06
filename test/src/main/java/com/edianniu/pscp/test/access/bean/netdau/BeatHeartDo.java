/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:26:23 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:26:23
 * @version V1.0
 */
public class BeatHeartDo implements Serializable {
	private static final long serialVersionUID = 1L;
	private DefaultAttrDo attr;
	private String time;
	private String notify;

	public DefaultAttrDo getAttr() {
		return attr;
	}

	public String getTime() {
		return time;
	}

	public void setAttr(DefaultAttrDo attr) {
		this.attr = attr;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getNotify() {
		return notify;
	}

	public void setNotify(String notify) {
		this.notify = notify;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
