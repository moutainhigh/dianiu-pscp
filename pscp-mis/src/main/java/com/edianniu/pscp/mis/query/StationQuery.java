/**
 * 
 */
package com.edianniu.pscp.mis.query;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * @author cyl
 *
 */
public class StationQuery extends BaseQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;
	private String sorts;
	private Long companyId;
	private Integer type;
	private List<Long> stationIds;
	public String getSorts() {
		return sorts;
	}

	public void setSorts(String sorts) {
		this.sorts = sorts;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Long> getStationIds() {
		return stationIds;
	}

	public void setStationIds(List<Long> stationIds) {
		this.stationIds = stationIds;
	}
}
