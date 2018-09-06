package com.edianniu.pscp.cms.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.edianniu.pscp.cms.dao.SysUserDao;
import com.edianniu.pscp.cms.entity.SysUserEntity;
import com.edianniu.pscp.cms.service.SysUserRoleService;
import com.edianniu.pscp.cms.service.SysUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 系统用户
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年6月16日 下午4:35:02 
 * @version V1.0
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}
	
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		user.setPasswd(new Sha256Hash(user.getPasswd()).toHex());
		sysUserDao.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPasswd())){
			user.setPasswd(null);
		}else{
			user.setPasswd(new Sha256Hash(user.getPasswd()).toHex());
		}		
		sysUserDao.update(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userIds) {
		sysUserDao.deleteBatch(userIds);
	}

	@Override
	public int updatePasswd(Long userId, String passwd, String newPasswd) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("passwd", passwd);
		map.put("newPasswd", newPasswd);
		return sysUserDao.updatePasswd(map);
	}

	@Override
	public SysUserEntity queryByLoginName(String loginName) {
		
		return sysUserDao.queryByLoginName(loginName);
	}

	@Override
	public SysUserEntity queryByMobile(String mobile) {
	
		return sysUserDao.queryByMobile(mobile);
	}

	@Override
	public boolean isExistLoginName(String loginName) {
		
		return sysUserDao.getCountByLoginName(loginName)>0?true:false;
	}

	@Override
	public boolean isExistMobile(String mobile) {
		
		return sysUserDao.getCountByMobile(mobile)>0?true:false;
	}

	@Override
	public SysUserEntity getByUserId(Long userId) {
		return sysUserDao.queryObject(userId);
	}
}
