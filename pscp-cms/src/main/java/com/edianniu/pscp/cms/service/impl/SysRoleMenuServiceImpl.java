package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.edianniu.pscp.cms.dao.SysRoleMenuDao;
import com.edianniu.pscp.cms.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 角色与菜单对应关系
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:33:36 
 * @version V1.0
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if(menuIdList.size() == 0){
			return ;
		}
		//先删除角色与菜单关系
		sysRoleMenuDao.delete(roleId);
		
		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		sysRoleMenuDao.save(map);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}

}
