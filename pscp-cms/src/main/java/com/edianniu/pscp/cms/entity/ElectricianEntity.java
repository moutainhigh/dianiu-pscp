package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



/**
 * 
 * 电工实体类
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-04-14 18:10:04
 */
public class ElectricianEntity extends MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	//电工ID
	private Long id;
	//电工编号
	private String identifier;
	//会员ID
	private Long memberId;
	//电工名字
	private String userName;
	//电工身份证号码
	private String idCardNo;
	//电工身份证照片正面
	private String idCardFrontImg;
	//电工身份证照片反面
	private String idCardBackImg;
	private ImageDo idCardFrontImgDo;
	private ImageDo idCardBackImgDo;
	/**
	 * 电工证图片列表
	 */
	private List<ImageDo> certificateImgList=new ArrayList<>();
	
	//状态
	private Integer authStatus;
	//备注
	private String remark;
	//工作状态
	private Integer workStatus=0;
	//工作时间
	private Long workTime;
	//审核时间
	private Date auditTime;
	//审核用户
	private String auditUser;
	//公司ID
	private Long companyId;
	
	//扩展属性，接收电工各种资质
	private List<ElectricianCertificateEntity> certificates;

	/**
	 * 设置：ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：编号
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	/**
	 * 获取：编号
	 */
	public String getIdentifier() {
		return identifier;
	}
	/**
	 * 设置：memberId
	 */
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	/**
	 * 获取：memberId
	 */
	public Long getMemberId() {
		return memberId;
	}
	/**
	 * 设置：用户姓名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户姓名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：身份证号
	 */
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	/**
	 * 获取：身份证号
	 */
	public String getIdCardNo() {
		return idCardNo;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdCardFrontImg(String idCardFrontImg) {
		this.idCardFrontImg = idCardFrontImg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdCardFrontImg() {
		return idCardFrontImg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setIdCardBackImg(String idCardBackImg) {
		this.idCardBackImg = idCardBackImg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getIdCardBackImg() {
		return idCardBackImg;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getAuthStatus() {
		return authStatus;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getWorkStatus() {
		return workStatus;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setWorkTime(Long workTime) {
		this.workTime = workTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getWorkTime() {
		return workTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getCompanyId() {
		return companyId;
	}
	public ImageDo getIdCardFrontImgDo() {
		return idCardFrontImgDo;
	}
	public ImageDo getIdCardBackImgDo() {
		return idCardBackImgDo;
	}
	public void setIdCardFrontImgDo(ImageDo idCardFrontImgDo) {
		this.idCardFrontImgDo = idCardFrontImgDo;
	}
	public void setIdCardBackImgDo(ImageDo idCardBackImgDo) {
		this.idCardBackImgDo = idCardBackImgDo;
	}
	public void setCertificateImgList(List<ImageDo> certificateImgList) {
		this.certificateImgList = certificateImgList;
	}
	
	public List<ImageDo> getCertificateImgList() {
		return certificateImgList;
	}
	public List<ElectricianCertificateEntity> getCertificates() {
		return certificates;
	}
	public void setCertificates(List<ElectricianCertificateEntity> certificates) {
		this.certificates = certificates;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	
}
