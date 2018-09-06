package com.edianniu.pscp.mis.bean.company;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 电工认证请求Data
 *
 * @author yanlin.chen
 * @version V1.0
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月14日 上午11:54:21
 */
public class AuditReqData implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long companyId;//企业ID
    private Integer authStatus;//状态
    private String remark;//备注
    private String opUser;
    /**
     * 身份证号号码
     */
    private String idCardNo;

    public Integer getAuthStatus() {
        return authStatus;
    }

    public String getRemark() {
        return remark;
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

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
