/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:31:52 
 * @version V1.0
 */
package com.edianniu.pscp.test.access.bean.netdau;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月12日 下午6:31:52 
 * @version V1.0
 */
public class NetDauCommonRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	private CommonDo common;
	private int contentTag;
	private String jsonContent;
	
	public CommonDo getCommon() {
		return common;
	}
	public void setCommon(CommonDo common) {
		this.common = common;
	}
	public String getJsonContent() {
		return jsonContent;
	}
	public void setJsonContent(String jsonContent) {
		this.jsonContent = jsonContent;
	}
	public int getContentTag() {
		return contentTag;
	}
	public void setContentTag(int contentTag) {
		this.contentTag = contentTag;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
