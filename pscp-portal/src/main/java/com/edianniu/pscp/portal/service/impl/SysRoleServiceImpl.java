package com.edianniu.pscp.portal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.portal.dao.SysRoleDao;
import com.edianniu.pscp.portal.entity.SysRoleEntity;
import com.edianniu.pscp.portal.service.SysRoleMenuService;
import com.edianniu.pscp.portal.service.SysRoleService;
import com.edianniu.pscp.portal.service.SysMemberRoleService;



/**
 * 角色
 * 
 * @author cyl
 * @email yanlin.chen@edianniu.com
 * @date 2017年04月12日 下午17:22:52
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysMemberRoleService sysMemberRoleService;

	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		sysRoleDao.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}

	@Override
	public boolean isExist(Long id, Long companyId) {
		int count=sysRoleDao.getByIdAndCompanyId(id, companyId);
		return count==1?true:false;
	}

}
