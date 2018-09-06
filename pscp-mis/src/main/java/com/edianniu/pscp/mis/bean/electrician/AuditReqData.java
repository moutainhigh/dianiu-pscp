package com.edianniu.pscp.mis.bean.electrician;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 电工认证请求Data
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月14日 上午11:54:21 
 * @version V1.0
 */
public class AuditReqData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long electricianId;//电工数字编号
    List<ElectricianCertificateInfo> certificates;//电工证书
    private Integer authStatus;//状态
    private String remark;//备注
    private String opUser;
	public Long getElectricianId() {
		return electricianId;
	}
	public List<ElectricianCertificateInfo> getCertificates() {
		return certificates;
	}
	public Integer getAuthStatus() {
		return authStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}
	public void setCertificates(List<ElectricianCertificateInfo> certificates) {
		this.certificates = certificates;
	}
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
