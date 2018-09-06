package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * ${comments}
 * 
 * @author wangcheng.li
 * @email wangcheng.li@edianniu.com
 * @date 2017-07-03 15:22:52
 */
public class SmsTemplateEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//$column.comments
	private Long id;
	//$column.comments
	private String msgId;
	//$column.comments
	private String context;
	//$column.comments
	private String createUser;
	//$column.comments
	private Date createTime;
	//$column.comments
	private String modifiedUser;
	//$column.comments
	private Date modifiedTime;
	//$column.comments
	private Integer deleted;
	
	private String msgValue;
	
	
	public String getMsgValue() {
		return msgValue;
	}
	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}
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
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * 获取：${column.comments}
	 */
	public String getContext() {
		return context;
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
