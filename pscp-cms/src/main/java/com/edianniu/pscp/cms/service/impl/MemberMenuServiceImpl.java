package com.edianniu.pscp.cms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.cms.dao.MemberMenuDao;
import com.edianniu.pscp.cms.entity.MemberMenuEntity;
import com.edianniu.pscp.cms.service.MemberMenuService;
import com.edianniu.pscp.cms.service.MemberRoleMenuService;
import com.edianniu.pscp.cms.service.MemberService;
import com.edianniu.pscp.cms.utils.Constant.MenuType;


@Service("memberMenuService")
public class MemberMenuServiceImpl implements MemberMenuService {
	@Autowired
	private MemberMenuDao memberMenuDao;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRoleMenuService memberRoleMenuService;
	
	@Override
	public List<MemberMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<MemberMenuEntity> menuList = memberMenuDao.queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<MemberMenuEntity> userMenuList = new ArrayList<>();
		for(MemberMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<MemberMenuEntity> queryNotButtonList() {
		return memberMenuDao.queryNotButtonList();
	}

	@Override
	public List<MemberMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == 1){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = memberService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}
	
	@Override
	public MemberMenuEntity queryObject(Long menuId) {
		return memberMenuDao.queryObject(menuId);
	}

	@Override
	public List<MemberMenuEntity> queryList(Map<String, Object> map) {
		return memberMenuDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return memberMenuDao.queryTotal(map);
	}

	@Override
	public void save(MemberMenuEntity menu) {
		memberMenuDao.save(menu);
	}

	@Override
	public void update(MemberMenuEntity menu) {
		memberMenuDao.update(menu);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] menuIds) {
		memberMenuDao.deleteBatch(menuIds);
	}
	
	/**
	 * 获取所有菜单列表
	 */
	private List<MemberMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<MemberMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<MemberMenuEntity> getMenuTreeList(List<MemberMenuEntity> menuList, List<Long> menuIdList){
		List<MemberMenuEntity> subMenuList = new ArrayList<MemberMenuEntity>();
		
		for(MemberMenuEntity entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
