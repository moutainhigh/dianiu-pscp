package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 10:08:40
 */
public class AppCheckUpdateEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private Long appStartVer;
	//$column.comments
	private Long appEndVer;
	//$column.comments
	private Integer appType;
	//$column.comments
	private String appPkg;
	//$column.comments
	private Long appLatestVer;
	//$column.comments
	private String appLatestShowVer;
	//$column.comments
	private Double appLatestSize;
	//$column.comments
	private Integer updateType;
	//$column.comments
	private String updateDesc;
	//$column.comments
	private String appDownloadUrl;
	//$column.comments
	private Integer appDownloadType;

	//$column.comments
	private Integer status;
	//$column.comments
	private String appDownloadMd5;
	
	private Date startTime;
	
	private Date endTime;
	
	private String startTimeConvert;
	
	private String endTimeConvert;
	

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
	public void setAppStartVer(Long appStartVer) {
		this.appStartVer = appStartVer;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getAppStartVer() {
		return appStartVer;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppEndVer(Long appEndVer) {
		this.appEndVer = appEndVer;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Long getAppEndVer() {
		return appEndVer;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getAppType() {
		return appType;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppPkg(String appPkg) {
		this.appPkg = appPkg;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAppPkg() {
		return appPkg;
	}
	public Long getAppLatestVer() {
		return appLatestVer;
	}
	public void setAppLatestVer(Long appLatestVer) {
		this.appLatestVer = appLatestVer;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppLatestShowVer(String appLatestShowVer) {
		this.appLatestShowVer = appLatestShowVer;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAppLatestShowVer() {
		return appLatestShowVer;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppLatestSize(Double appLatestSize) {
		this.appLatestSize = appLatestSize;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Double getAppLatestSize() {
		return appLatestSize;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUpdateType(Integer updateType) {
		this.updateType = updateType;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getUpdateType() {
		return updateType;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getUpdateDesc() {
		return updateDesc;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppDownloadUrl(String appDownloadUrl) {
		this.appDownloadUrl = appDownloadUrl;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAppDownloadUrl() {
		return appDownloadUrl;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setAppDownloadType(Integer appDownloadType) {
		this.appDownloadType = appDownloadType;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getAppDownloadType() {
		return appDownloadType;
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
	public void setAppDownloadMd5(String appDownloadMd5) {
		this.appDownloadMd5 = appDownloadMd5;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getAppDownloadMd5() {
		return appDownloadMd5;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getStartTimeConvert() {
		return startTimeConvert;
	}
	public void setStartTimeConvert(String startTimeConvert) {
		this.startTimeConvert = startTimeConvert;
	}
	public String getEndTimeConvert() {
		return endTimeConvert;
	}
	public void setEndTimeConvert(String endTimeConvert) {
		this.endTimeConvert = endTimeConvert;
	}
	
	
}
