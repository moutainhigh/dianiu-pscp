package com.edianniu.pscp.cms.dao;

import java.util.List;

import com.edianniu.pscp.cms.entity.MemberMenuEntity;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:01
 */
public interface MemberMenuDao extends BaseDao<MemberMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<MemberMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<MemberMenuEntity> queryNotButtonList();
}
