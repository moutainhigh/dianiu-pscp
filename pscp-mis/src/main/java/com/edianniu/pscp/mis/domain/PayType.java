/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月4日 下午5:19:19 
 * @version V1.0
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;

/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月4日 下午5:19:19 
 * @version V1.0
 */
public class PayType implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;
    private String type;
    private String name;
    private String description;
    private Integer status;
    private Integer disabled=0;
	public Long getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getDisabled() {
		return disabled;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
}
