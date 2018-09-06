/**
 * 
 */
package com.edianniu.pscp.mis.bean.request.socialworkorder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;

/**
 * @author cyl
 *
 */
@JSONMessage(messageCode = 1002013)
public final class HomeRequest extends TerminalRequest {
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
