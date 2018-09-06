package com.edianniu.pscp.portal.dao;

import org.apache.ibatis.annotations.Param;

import com.edianniu.pscp.portal.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:25:36
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	public int getByIdAndCompanyId(@Param("id")Long id,@Param("companyId")Long companyId);
}
