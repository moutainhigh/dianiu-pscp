package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 17:20:37
 */
public class SmsSendLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private String mobile;
	//$column.comments
	private String content;
	//$column.comments
	private String msgId;
	//$column.comments
	private String msgStatus;
	//$column.comments
	private Integer status;
	//$column.comments
	private Integer channelType;
	//$column.comments
	private String err;
	//$column.comments
	private String failDesc;
	//$column.comments
	private Integer subSeq;
	//$column.comments
	private String reportTime;
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
	
	private String msgValue;
	
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
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMsgId() {
		return msgId;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getMsgStatus() {
		return msgStatus;
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
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getChannelType() {
		return channelType;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setErr(String err) {
		this.err = err;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getErr() {
		return err;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getFailDesc() {
		return failDesc;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setSubSeq(Integer subSeq) {
		this.subSeq = subSeq;
	}
	/**
	 * 获取：${column.comments}
	 */
	public Integer getSubSeq() {
		return subSeq;
	}
	/**
	 * 设置：${column.comments}
	 */
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getReportTime() {
		return reportTime;
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
	public String getMsgValue() {
		return msgValue;
	}
	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}
	
	
}
