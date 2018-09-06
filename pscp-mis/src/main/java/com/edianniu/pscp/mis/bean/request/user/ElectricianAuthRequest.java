package com.edianniu.pscp.mis.bean.request.user;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.edianniu.pscp.mis.bean.electrician.CertificateImgInfo;
import com.edianniu.pscp.mis.bean.request.TerminalRequest;

import stc.skymobi.bean.json.annotation.JSONMessage;
@JSONMessage(messageCode = 1002011)
public class ElectricianAuthRequest extends TerminalRequest{
	private String userName;//用户真实姓名	 
	private String idCardNo;//身份证号码
	private String idCardFrontImg;//身份证正面照片	 
	private String idCardBackImg;//身份证反面照片	  	 	 
	private List<CertificateImgInfo> certificateImgs;

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
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}
}
