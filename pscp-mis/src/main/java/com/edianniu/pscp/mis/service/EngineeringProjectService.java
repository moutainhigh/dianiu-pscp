package com.edianniu.pscp.mis.service;

import com.edianniu.pscp.mis.domain.EngineeringProject;
import com.edianniu.pscp.mis.domain.PayOrder;

/**
 * ProjectService
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年9月21日 下午5:06:54 
 * @version V1.0
 */
public interface EngineeringProjectService {
	
    public EngineeringProject getById(Long id);
    
    public EngineeringProject getByProjectNo(String projectNo);
    
    public String getRoomIdsById(Long id);
    public int update(EngineeringProject engineeringProject);
    
    public boolean updatePayStatus(PayOrder payOrder);
}
