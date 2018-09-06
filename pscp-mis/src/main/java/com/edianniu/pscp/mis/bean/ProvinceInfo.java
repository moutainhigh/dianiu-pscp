/**
 * 
 */
package com.edianniu.pscp.mis.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author cyl
 *
 */
public class ProvinceInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String latitude;
	private String longitude;
	private String fistLetter;
	private int sort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getFistLetter() {
		return fistLetter;
	}

	public void setFistLetter(String fistLetter) {
		this.fistLetter = fistLetter;
	}
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
