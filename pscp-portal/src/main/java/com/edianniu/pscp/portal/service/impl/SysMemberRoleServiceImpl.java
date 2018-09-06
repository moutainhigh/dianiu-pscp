package com.edianniu.pscp.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edianniu.pscp.portal.dao.SysMemberRoleDao;
import com.edianniu.pscp.portal.service.SysMemberRoleService;



/**
 *  用户与角色对应关系
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年5月5日 上午10:41:11 
 * @version V1.0
 */
@Service("sysUserRoleService")
public class SysMemberRoleServiceImpl implements SysMemberRoleService {
	@Autowired
	private SysMemberRoleDao sysMemberRoleDao;

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if(roleIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		Map<String,Object> params=new HashMap<>();
		params.put("userId", userId);
		sysMemberRoleDao.delete(params);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		sysMemberRoleDao.save(map);	
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysMemberRoleDao.queryRoleIdList(userId);
	}
}
