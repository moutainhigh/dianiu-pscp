/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月17日 下午5:58:57 
 * @version V1.0
 */
package com.edianniu.pscp.mis.query;

import com.edianniu.pscp.mis.commons.BaseQuery;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年10月17日 下午5:58:57 
 * @version V1.0
 */
public class ElectricianInvitationQuery extends BaseQuery {
	private static final long serialVersionUID = 1L;
	private Long companyId;
	private String mobile;
    private String name;
    private Integer status;
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
