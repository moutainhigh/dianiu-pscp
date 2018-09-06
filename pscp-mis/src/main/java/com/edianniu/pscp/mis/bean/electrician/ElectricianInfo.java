package com.edianniu.pscp.mis.bean.electrician;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ElectricianInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;//用户真实姓名	 
	private String idCardNo;//身份证号码
	private String idCardFrontImg;//身份证正面照片	 
	private String idCardBackImg;//身份证反面照片	  	 	 
	private List<CertificateImgInfo> certificateImgs;//电工证图片
	
	private int authStatus;//状态 0:认证中,1:认证通过,-1认证失败
	private String remark;//备注	 
	private Integer type;
	
	private Long companyId;//企业ID默认0：社会电工，其他是指企业的电工
	
	private String companyName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}
	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}
	public String getIdCardBackImg() {
		return idCardBackImg;
	}
	public void setIdCardBackImg(String idCardBackImg) {
		this.idCardBackImg = idCardBackImg;
	}
	
	public List<CertificateImgInfo> getCertificateImgs() {
		return certificateImgs;
	}
	public void setCertificateImgs(List<CertificateImgInfo> certificateImgs) {
		this.certificateImgs = certificateImgs;
	}
	public int getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(int authStatus) {
		this.authStatus = authStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }

}
