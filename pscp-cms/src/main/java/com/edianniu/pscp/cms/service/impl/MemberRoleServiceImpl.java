package com.edianniu.pscp.cms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.cms.dao.MemberRoleDao;
import com.edianniu.pscp.cms.entity.MemberRoleEntity;
import com.edianniu.pscp.cms.service.MemberMemberRoleService;
import com.edianniu.pscp.cms.service.MemberRoleMenuService;
import com.edianniu.pscp.cms.service.MemberRoleService;



/**
 * 角色
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:22:52
 */
@Service("memberRoleService")
public class MemberRoleServiceImpl implements MemberRoleService {
	@Autowired
	private MemberRoleDao memberRoleDao;
	@Autowired
	private MemberRoleMenuService memberRoleMenuService;
	@Autowired
	private MemberMemberRoleService memberMemberRoleService;

	@Override
	public MemberRoleEntity queryObject(Long roleId) {
		return memberRoleDao.queryObject(roleId);
	}

	@Override
	public List<MemberRoleEntity> queryList(Map<String, Object> map) {
		return memberRoleDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return memberRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(MemberRoleEntity role) {
		role.setCreateTime(new Date());
		memberRoleDao.save(role);
		
		//保存角色与菜单关系
		memberRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void update(MemberRoleEntity role) {
		memberRoleDao.update(role);
		
		//更新角色与菜单关系
		memberRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		memberRoleDao.deleteBatch(roleIds);
	}

}
