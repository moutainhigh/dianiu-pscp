package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 电工证书
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 18:33:41
 */
public class ElectricianCertificateEntity extends BaseEntity implements Serializable ,Comparator<ElectricianCertificateEntity>{
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private Long electricianId;
	//$column.comments
	private Long certificateId;
	//$column.comments
	private Integer orderNum=0;
	//$column.comments
	private Integer status=0;
	//$column.comments
	private String remark;

	
	//扩展属性，是否选中
	private boolean selected;
	private Integer type;
	
	private String name ;

	/**
	 * 设置：${column.comments}
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setElectricianId(Long electricianId) {
		this.electricianId = electricianId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getElectricianId() {
		return electricianId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCertificateId(Long certificateId) {
		this.certificateId = certificateId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getCertificateId() {
		return certificateId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getStatus() {
		return status;
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
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Override
	public int compare(ElectricianCertificateEntity one, ElectricianCertificateEntity other) {
		return one.getType()-other.getType();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
