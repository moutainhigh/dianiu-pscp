/**
 * 
 */
package com.edianniu.pscp.mis.bean.workorder;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author cyl
 *
 */
public class SocialWorkOrderInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String orderId;
	private String name;
	private String title;
	private String content;
	private String fee;
	private String pubTime;
	private String address;
	private String latitude;
	private String longitude;
	private String distance	;
	public Long getId() {
		return id;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getName() {
		return name;
	}
	public String getFee() {
		return fee;
	}
	public String getPubTime() {
		return pubTime;
	}
	public String getAddress() {
		return address;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getDistance() {
		return distance;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
