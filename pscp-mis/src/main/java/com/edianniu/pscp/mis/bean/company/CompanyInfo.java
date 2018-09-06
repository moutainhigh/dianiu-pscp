/**
 *
 */
package com.edianniu.pscp.mis.bean.company;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author cyl
 */
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer type;
    private String legalPerson;
    private String businessLicence;
    private String businessLicenceImg;
    private List<CertificateImgInfo> certificateImages;
    private String contacts;// 联系人
    private String mobile;
    private String email;
    private String phone;
    private AddressInfo addressInfo;
    private String website;
    private String applicationLetterFid;
    private Integer authStatus = CompanyAuthStatus.NOTAUTH.getValue();
    private String remark;
    private String userName;
    private String idCardNo;
    private String idCardFrontImg;
    private String idCardBackImg;
    private String organizationCodeImg;
    private Long memberId;
    private Integer memberType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getBusinessLicenceImg() {
        return businessLicenceImg;
    }

    public void setBusinessLicenceImg(String businessLicenceImg) {
        this.businessLicenceImg = businessLicenceImg;
    }

    public List<CertificateImgInfo> getCertificateImages() {
        return certificateImages;
    }

    public void setCertificateImages(List<CertificateImgInfo> certificateImages) {
        this.certificateImages = certificateImages;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getApplicationLetterFid() {
        return applicationLetterFid;
    }

    public void setApplicationLetterFid(String applicationLetterFid) {
        this.applicationLetterFid = applicationLetterFid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

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

    public String getOrganizationCodeImg() {
        return organizationCodeImg;
    }

    public void setOrganizationCodeImg(String organizationCodeImg) {
        this.organizationCodeImg = organizationCodeImg;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.DEFAULT_STYLE);
    }
}
