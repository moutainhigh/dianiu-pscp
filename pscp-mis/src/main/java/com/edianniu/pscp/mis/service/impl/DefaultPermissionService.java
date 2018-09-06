package com.edianniu.pscp.mis.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.edianniu.pscp.mis.dao.MemberRoleDao;
import com.edianniu.pscp.mis.service.PermissionService;
/**
 * DefaultPermissionService
 * @author yanlin.chen  yanlin.chen@edianniu.com
 * @version V1.0  2018年5月11日 下午4:10:44
 */
@Service
@Repository("permissionService")
public class DefaultPermissionService implements PermissionService {
	@Autowired
    private MemberRoleDao memberRoleDao;
	@Override
	public Set<String> getPermissions(Long uid) {
		Set<String> set=new HashSet<String>();
		List<String> list=memberRoleDao.getPerms(uid);
		for(String perm:list){
			if(StringUtils.isNotBlank(perm)){
				String[] perms=StringUtils.split(perm, ",");
				if(perms!=null){
					for(String p:perms){
						set.add(p);
					}
				}
			}
			
		}
		return set;
	}

}
