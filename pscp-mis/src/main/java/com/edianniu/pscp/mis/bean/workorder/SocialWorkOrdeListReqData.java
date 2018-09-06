package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

public class SocialWorkOrdeListReqData  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long uid;
	private String latitude;
	private String longitude;
	/**
	 * price[_asc/desc], dis[_asc/desc], 默认为空表示dis,多个逗号隔开
	 */
	private String sorts;
	private int offset;
	private String view;// 视图:list/map
	public Long getUid() {
		return uid;
	}
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
	public void setUid(Long uid) {
		this.uid = uid;
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
}
