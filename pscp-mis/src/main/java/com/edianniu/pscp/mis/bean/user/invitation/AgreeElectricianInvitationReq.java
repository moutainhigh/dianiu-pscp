/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:03:05 
 * @version V1.0
 */
package com.edianniu.pscp.mis.bean.user.invitation;

import java.io.Serializable;
import java.util.List;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月27日 上午11:03:05 
 * @version V1.0
 */
public class AgreeElectricianInvitationReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long uid;
	private Long invitationId;
	private String userName;//用户真实姓名	 
	private String idCardNo;//身份证号码
	private String idCardFrontImg;//身份证正面照片	 
	private String idCardBackImg;//身份证反面照片	  	 	 
	private List<CertificateImgInfo> certificateImgs;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getInvitationId() {
		return invitationId;
	}

	public void setInvitationId(Long invitationId) {
		this.invitationId = invitationId;
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
}
