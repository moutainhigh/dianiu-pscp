package com.edianniu.pscp.cms.service;

import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.entity.MemberMenuEntity;


/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:16
 */
public interface MemberMenuService {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	List<MemberMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<MemberMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 */
	List<MemberMenuEntity> getUserMenuList(Long userId);
	
	
	/**
	 * 查询菜单
	 */
	MemberMenuEntity queryObject(Long menuId);
	
	/**
	 * 查询菜单列表
	 */
	List<MemberMenuEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 */
	void save(MemberMenuEntity menu);
	
	/**
	 * 修改
	 */
	void update(MemberMenuEntity menu);
	
	/**
	 * 删除
	 */
	void deleteBatch(Long[] menuIds);
}
