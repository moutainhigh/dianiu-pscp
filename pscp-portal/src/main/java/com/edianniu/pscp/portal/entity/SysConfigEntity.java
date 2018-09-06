package com.edianniu.pscp.portal.entity;


/**
 * 系统配置信息
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午9:28:13
 */
public class SysConfigEntity extends BaseEntity{
	private Long id; 
	private String key; 
	private String value; 
	private String remark;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
