package com.edianniu.pscp.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edianniu.pscp.cms.dao.MemberRoleMenuDao;
import com.edianniu.pscp.cms.service.MemberRoleMenuService;



/**
 * 会员-角色菜单关系
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:09:17 
 * @version V1.0
 */
@Service("memberRoleMenuService")
public class MemberRoleMenuServiceImpl implements MemberRoleMenuService {
	@Autowired
	private MemberRoleMenuDao memberRoleMenuDao;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		if(menuIdList.size() == 0){
			return ;
		}
		//先删除角色与菜单关系
		memberRoleMenuDao.delete(roleId);
		
		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		memberRoleMenuDao.save(map);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return memberRoleMenuDao.queryMenuIdList(roleId);
	}

}
