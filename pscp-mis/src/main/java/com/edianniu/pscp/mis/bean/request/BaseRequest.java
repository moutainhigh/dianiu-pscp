/**
 * 
 */
package com.edianniu.pscp.mis.bean.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import stc.skymobi.bean.AbstractCommonBean;
import stc.skymobi.bean.json.JSONSignal;

/**
 * @author cyl
 *
 */
public class BaseRequest extends AbstractCommonBean implements JSONSignal {
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}