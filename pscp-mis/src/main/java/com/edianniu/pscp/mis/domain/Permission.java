/**
 * 
 */
package com.edianniu.pscp.mis.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author cyl
 *
 */
public class Permission implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer pid;
	private String url;
	private String name;
	private boolean active;
	private List<Permission> childern;
	public Integer getId() {
		return id;
	}
	public Integer getPid() {
		return pid;
	}
	public String getUrl() {
		return url;
	}
	public String getName() {
		return name;
	}
	public boolean isActive() {
		return active;
	}
	public List<Permission> getChildern() {
		return childern;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setChildern(List<Permission> childern) {
		this.childern = childern;
	}
}
