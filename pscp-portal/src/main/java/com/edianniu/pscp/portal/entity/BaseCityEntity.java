package com.edianniu.pscp.portal.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-10 09:47:06
 */
public class BaseCityEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private String name;
	//$column.comments
	private Long provinceId;
	//$column.comments
	private String latitude;
	//$column.comments
	private String longitude;
	//$column.comments
	private Date createTime;
	//$column.comments
	private String createUser;
	//$column.comments
	private Date modifiedTime;
	//$column.comments
	private String modifiedUser;
	//$column.comments
	private Integer deleted;

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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getProvinceId() {
		return provinceId;
	}
	
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Date getModifiedTime() {
		return modifiedTime;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getModifiedUser() {
		return modifiedUser;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getDeleted() {
		return deleted;
	}
}
