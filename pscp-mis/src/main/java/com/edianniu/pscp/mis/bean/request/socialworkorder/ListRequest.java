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
@JSONMessage(messageCode = 1002014)
public final class ListRequest extends TerminalRequest {
	private String latitude;
	private String longitude;
	/**
	 * price[_asc/desc], dis[_asc/desc], 默认为空表示dis,多个逗号隔开
	 */
	private String sorts;
	private int offset;
	private String view;// 视图:list/map

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getSorts() {
		return sorts;
	}

	public int getOffset() {
		return offset;
	}

	

	public String getView() {
		return view;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	
	public void setView(String view) {
		this.view = view;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
