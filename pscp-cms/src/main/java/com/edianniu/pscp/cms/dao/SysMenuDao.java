package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.SysMenuEntity;


/**
 * 菜单管理
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:24:36 
 * @version V1.0
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
}
