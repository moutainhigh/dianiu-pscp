package com.edianniu.pscp.portal.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 上午9:28:13
 */
public class SysRoleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 备注
	 */
	private String remark;
	
	private Long companyId;
	
	private List<Long> menuIdList;
	/**
	 * 设置：
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置：角色名称
	 * @param name 角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：角色名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置：备注
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}
	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
}
