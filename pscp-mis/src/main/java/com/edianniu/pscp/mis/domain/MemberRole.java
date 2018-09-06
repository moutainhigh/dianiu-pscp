package com.edianniu.pscp.mis.domain;

/**
 * MemberRole
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年7月17日 下午5:35:05 
 * @version V1.0
 */
public class MemberRole extends BaseDo {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private Long roleId;
	public Long getId() {
		return id;
	}
	public Long getUserId() {
		return userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}
