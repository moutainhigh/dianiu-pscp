/**
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:10 
 * @version V1.0
 */
package com.edianniu.pscp.mis.dao;

import com.edianniu.pscp.mis.domain.ElectricianWorkStatusLog;

/**
 * ElectricianWorkStatusLogDao
 * @author yanlin.chen 
 * @email yanlin.chen@edianniu.com
 * @date 2017年4月14日 上午11:24:10 
 * @version V1.0
 */
public interface ElectricianWorkStatusLogDao extends BaseDao<ElectricianWorkStatusLog>{
   public ElectricianWorkStatusLog getLatestByUid(Long uid);
}
