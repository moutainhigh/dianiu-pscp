/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午5:31:46 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.workorder;

import java.util.List;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月13日 下午5:31:46 
 * @version V1.0
 */
public class SocialWorkOrderQuery  extends BaseQuery{
	private static final long serialVersionUID = 1L;
	private double latitude;
	private double longitude;
	private List<Long> existSocialWorkOrderIds;//已接社会工单过滤
	private String qualifications;//电工资质过滤
	private String sorts;
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String getSorts() {
		return sorts;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	public List<Long> getExistSocialWorkOrderIds() {
		return existSocialWorkOrderIds;
	}
	public void setExistSocialWorkOrderIds(List<Long> existSocialWorkOrderIds) {
		this.existSocialWorkOrderIds = existSocialWorkOrderIds;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
}
