package com.edianniu.pscp.cms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:20:58
 * @version V1.0
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

	private List<Long> menuIdList;

	/**
	 * 设置：备注
	 * 
	 * @param remark
	 *            备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 * 
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

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
